package com.program.server.service;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

@Service
public class videoService {
    public void requestVideo(String targetUrl) throws URISyntaxException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://example.com/api/resource");

// 设置请求头
        httpGet.setHeader("Authorization", "Bearer YourAccessToken");
        httpGet.setHeader("User-Agent", "YourUserAgent");

// 设置请求参数（可选）
        URIBuilder uriBuilder = new URIBuilder(httpGet.getURI());
        uriBuilder.setParameter("param1", "value1");
        uriBuilder.setParameter("param2", "value2");

        httpGet.setURI(uriBuilder.build());


        try {
            HttpResponse response = (HttpResponse) httpClient.execute(httpGet);

            // 处理响应，可能包含视频流数据
            // 这里可以将响应数据传输给客户端或进行其他处理
        } catch (Exception e) {
            e.printStackTrace();
            // 处理请求失败的情况
        }
    }
}

