package com.shuishu.face.openvisual.engine.service;

import com.shuishu.face.openvisual.engine.model.MapParam;
import org.opensearch.client.opensearch.core.SearchResponse;

import java.util.List;
import java.util.Map;


/**
 * @Author ：谁书-ss
 * @Date   ： 2024-08-24 10:20
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
public interface SearchEngineService {

    Object getEngine();

    boolean exist(String collectionName);

    boolean dropCollection(String collectionName);

    Boolean createCollection(String collectionName, MapParam param);

    boolean insertVector(String collectionName, String sampleId, String faceId, float[] vectors);

    boolean deleteVectorByKey(String collectionName, String faceId);

    boolean deleteVectorByKey(String collectionName, List<String> faceIds);

    SearchResponse<Map<String, Object>> search(String collectionName, float[][] features, String algorithm, int topK);

    float searchMinScoreBySampleId(String collectionName, String sampleId,float[] feature, String algorithm);

    float searchMaxScoreBySampleId(String collectionName, String sampleId,float[] feature, String algorithm);
}
