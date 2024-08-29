package com.shuishu.face.openvisual.server.bootstrap;

import com.shuishu.face.openvisual.engine.service.SearchEngineService;
import com.shuishu.face.openvisual.engine.service.impl.SearchEngineServiceImpl;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestClientBuilder;
import org.opensearch.client.json.jackson.JacksonJsonpMapper;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.transport.rest_client.RestClientTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;


/**
 * @Author ：谁书-ss
 * @Date   ： 2024-08-24 11:52
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
@Configuration("searchEngineServiceConfig")
public class SearchEngineServiceConfig {
    //日志
    public Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${face.engine.open-search.host:localhost}")
    private String openSearchHost;
    @Value("${face.engine.open-search.port:9200}")
    private Integer openSearchPort;
    @Value("${face.engine.open-search.scheme:https}")
    private String openSearchScheme;
    @Value("${face.engine.open-search.username:admin}")
    private String openSearchUserName;
    @Value("${face.engine.open-search.password:admin}")
    private String openSearchPassword;

    @Bean(name = "searchEngineService")
    public SearchEngineService simpleSearchEngine() {
        //认证参数
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(openSearchUserName, openSearchPassword));
        //ssl设置
        final SSLContext sslContext;
        try {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }}, new SecureRandom());
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            logger.error("create SearchEngine error:", e);
            throw new RuntimeException(e);
        }
        //构建请求
        RestClientBuilder builder = RestClient.builder(new HttpHost(openSearchHost, openSearchPort, openSearchScheme))
                .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
                        .setDefaultCredentialsProvider(credentialsProvider)
                        .setSSLHostnameVerifier((hostname, session) -> true)
                        .setSSLContext(sslContext)
                        .setMaxConnTotal(10)
                        .setMaxConnPerRoute(10)
                );
        RestClientTransport restClientTransport = new RestClientTransport(builder.build(), new JacksonJsonpMapper());
        // 注册搜索引擎Bean，初始化client
        return new SearchEngineServiceImpl(new OpenSearchClient(restClientTransport));
    }

}
