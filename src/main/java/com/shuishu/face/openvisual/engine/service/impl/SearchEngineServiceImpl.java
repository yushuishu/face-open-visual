package com.shuishu.face.openvisual.engine.service.impl;

import com.shuishu.face.openvisual.config.exception.BusinessException;
import com.shuishu.face.openvisual.engine.conf.Constant;
import com.shuishu.face.openvisual.engine.model.*;
import com.shuishu.face.openvisual.engine.service.SearchEngineService;
import org.opensearch.client.json.JsonData;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.FieldValue;
import org.opensearch.client.opensearch._types.InlineScript;
import org.opensearch.client.opensearch._types.Result;
import org.opensearch.client.opensearch._types.Script;
import org.opensearch.client.opensearch._types.mapping.KeywordProperty;
import org.opensearch.client.opensearch._types.mapping.Property;
import org.opensearch.client.opensearch._types.mapping.TypeMapping;
import org.opensearch.client.opensearch._types.query_dsl.MatchAllQuery;
import org.opensearch.client.opensearch._types.query_dsl.Query;
import org.opensearch.client.opensearch._types.query_dsl.QueryBuilders;
import org.opensearch.client.opensearch.core.*;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.opensearch.client.opensearch.core.bulk.BulkOperation;
import org.opensearch.client.opensearch.core.search.*;
import org.opensearch.client.opensearch.indices.*;
import org.opensearch.client.opensearch.indices.ExistsRequest;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @Author ：谁书-ss
 * @Date ： 2024-08-24 10:20
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
public class SearchEngineServiceImpl implements SearchEngineService {
    private OpenSearchClient client;
    private MapParam params = new MapParam();

    public SearchEngineServiceImpl(OpenSearchClient client) {
        this(client, null);
    }

    public SearchEngineServiceImpl(OpenSearchClient client, MapParam params) {
        this.client = client;
        if (null != params) {
            this.params = params;
        }
    }

    @Override
    public Object getEngine() {
        return this.client;
    }

    @Override
    public boolean exist(String collectionName) {
        try {
            ExistsRequest existsRequest = new ExistsRequest.Builder().index(List.of(collectionName)).build();
            return this.client.indices().exists(existsRequest).value();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean dropCollection(String collectionName) {
        try {
            DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest.Builder().index(collectionName).build();
            return this.client.indices().delete(deleteIndexRequest).acknowledged();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean createCollection(String collectionName, MapParam param) {
        try {
            // 索引配置
            IndexSettings indexSettings = new IndexSettings.Builder()
                    .numberOfShards(param.getIndexShardsNum().toString())
                    .numberOfReplicas(param.getIndexReplicasNum().toString())
                    .build();
            // mapping
            Map<String, Property> properties = new HashMap<>(5);
            Property sampleIdProperty = Property.of(p -> p.keyword(KeywordProperty.of(k -> k)));
            properties.put(Constant.ColumnNameSampleId, sampleIdProperty);
            Property faceVectorProperty = Property.of(p -> {
                p.keyword(KeywordProperty.of(k -> k));
                p.knnVector(k -> k.dimension(512));
                return p;
            });
            properties.put(Constant.ColumnNameFaceVector, faceVectorProperty);
            TypeMapping typeMapping = new TypeMapping.Builder()
                    .properties(properties)
                    .build();
            // 创建索引请求对象
            CreateIndexRequest createIndexRequest = new CreateIndexRequest.Builder()
                    .index(collectionName)
                    .settings(indexSettings)
                    .mappings(typeMapping)
                    .build();

            // 创建集合
            return this.client.indices().create(createIndexRequest).acknowledged();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean insertVector(String collectionName, String sampleId, String faceId, float[] vectors) {
        try {

            // 数据
            Map<String, Object> dataMap = new HashMap<>(5);
            dataMap.put(Constant.ColumnNameSampleId, sampleId);
            dataMap.put(Constant.ColumnNameFaceVector, vectors);
            // 索引请求对象
            IndexRequest indexRequest = new IndexRequest.Builder<Map<String, Object>>()
                    .index(collectionName)
                    .id(faceId)
                    .document(dataMap)
                    .build();
            // 请求插入数据
            IndexResponse indexResponse = this.client.index(indexRequest);
            // 获取响应
            Result result = indexResponse.result();
            return Objects.equals(Result.Created.jsonValue(), result.jsonValue()) || Objects.equals(Result.Updated.jsonValue(), result.jsonValue());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteVectorByKey(String collectionName, String faceId) {
        try {
            DeleteRequest deleteRequest = new DeleteRequest.Builder().index(collectionName).id(faceId).build();
            DeleteResponse deleteResponse = this.client.delete(deleteRequest);
            Result result = deleteResponse.result();
            return Objects.equals(Result.Deleted.jsonValue(), result.jsonValue());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteVectorByKey(String collectionName, List<String> keyIds) {
        try {
            // 构建删除操作
            List<BulkOperation> bulkOperationList = keyIds.stream().map(faceId -> BulkOperation.of(b -> b.delete(d -> d.index(collectionName).id(faceId)))).collect(Collectors.toList());
            // 创建批量请求对象
            BulkRequest bulkRequest = new BulkRequest.Builder().operations(bulkOperationList).build();
            // 执行批量请求
            BulkResponse bulkResponse = this.client.bulk(bulkRequest);
            // 检查所有操作是否都成功
            return !bulkResponse.errors();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SearchResponse<Map<String, Object>> search(String collectionName, float[][] features, String algorithm, int topK) {
        try {
            List<SearchResponse<Map<String, Object>>> searchResponseList = new ArrayList<>();
            for (float[] feature : features) {
                MatchAllQuery matchAllQuery = QueryBuilders.matchAll().build();
                Map<String, JsonData> paramMap = new HashMap<>(10);
                paramMap.put("field", JsonData.of(Constant.ColumnNameFaceVector));
                paramMap.put("space_type", JsonData.of(algorithm));
                paramMap.put("query_value", JsonData.of(feature));
                InlineScript inlineScript = new InlineScript.Builder()
                        .source("knn_score")
                        .lang("knn")
                        .params(paramMap)
                        .build();
                Script script = Script.of(s -> s.inline(inlineScript));
                Query query = QueryBuilders.scriptScore()
                        .query(q -> q.matchAll(m -> m))
                        .script(script)
                        .build()
                        .toQuery();
                SearchRequest searchRequest = new SearchRequest.Builder()
                        .index(collectionName)
                        .query(query)
                        .size(topK)
                        .source(SourceConfig.of(sc -> sc.fetch(false)))
                        .build();
                searchResponseList.add(this.client.search(searchRequest, (Class<Map<String, Object>>) (Class<?>) Map.class));
            }

            if (searchResponseList.size() != features.length) {
                throw new BusinessException("features.length != responses.size()");
            }

            // 创建最终的搜索结果列表
            List<Hit<Map<String, Object>>> finalHits = new ArrayList<>();
            for (SearchResponse<Map<String, Object>> searchResponse : searchResponseList) {
                finalHits.addAll(searchResponse.hits().hits());
            }
            // 构建HitsMetadata对象
            HitsMetadata<Map<String, Object>> hitsMetadata = new HitsMetadata.Builder<Map<String, Object>>()
                    .hits(finalHits)
                    .total(new TotalHits.Builder().value(finalHits.size()).relation(TotalHitsRelation.Eq).build())
                    .build();
            // 构建SearchResponse对象
            return new SearchResponse.Builder<Map<String, Object>>()
                    .took(0)
                    .timedOut(false)
                    .shards(searchResponseList.get(0).shards())
                    .hits(hitsMetadata)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public float searchMinScoreBySampleId(String collectionName, String sampleId, float[] feature, String algorithm) {
        try {
            // 构建匹配查询，匹配样本 ID
            Query matchQuery = QueryBuilders.match()
                    .field(Constant.ColumnNameSampleId)
                    .query(FieldValue.of(sampleId))
                    .build()
                    .toQuery();

            // 创建脚本参数
            Map<String, JsonData> params = new HashMap<>(10);
            params.put("field", JsonData.of(Constant.ColumnNameFaceVector));
            params.put("space_type", JsonData.of(algorithm));
            params.put("query_value", JsonData.of(feature));

            // 构建内联脚本
            InlineScript inlineScript = new InlineScript.Builder()
                    .source("knn_score")
                    .lang("knn")
                    .params(params)
                    .build();

            // 包装内联脚本
            Script script = Script.of(s -> s.inline(inlineScript));

            // 构建脚本评分查询
            Query scriptScoreQuery = QueryBuilders.scriptScore()
                    .query(matchQuery)
                    .script(script)
                    .build()
                    .toQuery();

            // 创建搜索请求
            SearchRequest searchRequest = new SearchRequest.Builder()
                    .index(collectionName)
                    .query(scriptScoreQuery)
                    .size(10000)
                    .source(SourceConfig.of(sc -> sc.fetch(false)))
                    .build();

            // 发送搜索请求
            SearchResponse<Map<String, Object>> response = this.client.search(searchRequest, (Class<Map<String, Object>>) (Class<?>) Map.class);

            if (response.timedOut()) {
                throw new RuntimeException("get score error!");
            }

            if (response.hits() == null || response.hits().hits() == null) {
                throw new BusinessException("response is null");
            }

            // 提取得分
            List<Double> scores = response.hits().hits().stream().map(Hit::score).filter(Objects::nonNull).toList();
            double minScore = scores.stream().mapToDouble(Double::doubleValue).min().orElse(2f);
            return (float) minScore - 1;
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public float searchMaxScoreBySampleId(String collectionName, String sampleId, float[] feature, String algorithm) {
        try {
            // 构建布尔查询，排除指定的样本 ID
            Query matchQuery = QueryBuilders.match()
                    .field(Constant.ColumnNameSampleId)
                    .query(FieldValue.of(sampleId))
                    .build()
                    .toQuery();
            Query boolQuery = QueryBuilders.bool()
                    .mustNot(matchQuery)
                    .build()
                    .toQuery();

            // 创建脚本参数
            Map<String, JsonData> params = new HashMap<>();
            params.put("field", JsonData.of(Constant.ColumnNameFaceVector));
            params.put("space_type", JsonData.of(algorithm));
            params.put("query_value", JsonData.of(feature));

            // 构建内联脚本
            InlineScript inlineScript = new InlineScript.Builder()
                    .source("knn_score")
                    .lang("knn")
                    .params(params)
                    .build();

            // 包装内联脚本
            Script script = Script.of(s -> s.inline(inlineScript));

            // 构建脚本评分查询
            Query scriptScoreQuery = QueryBuilders.scriptScore()
                    .query(boolQuery)
                    .script(script)
                    .build()
                    .toQuery();

            // 创建搜索请求
            SearchRequest searchRequest = new SearchRequest.Builder()
                    .index(collectionName)
                    .query(scriptScoreQuery)
                    .size(1)
                    .source(SourceConfig.of(sc -> sc.fetch(false)))
                    .build();

            // 发送搜索请求
            SearchResponse<Map<String, Object>> response = this.client.search(searchRequest, (Class<Map<String, Object>>) (Class<?>) Map.class);

            // 检查响应是否超时
            if (response.timedOut()) {
                throw new RuntimeException("get score error!");
            }

            // 从响应中提取得分
            List<Double> scores = response.hits().hits().stream().map(Hit::score).filter(Objects::nonNull).toList();
            // 计算最高得分
            double maxScore = scores.stream().mapToDouble(Double::doubleValue).max().orElse(1f);
            return (float) maxScore - 1;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
