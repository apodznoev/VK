package com.company;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Андрей
 * @since 8 Apr 2016
 */
public class RequestHelper {

    private static List<Object> getResponseContent(String url) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpget = new HttpGet(url);

            System.out.println("Executing request " + httpget.getRequestLine());

            return httpClient.execute(httpget, response -> {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    ObjectMapper mapper = new ObjectMapper();
                    Map<String, Object> obj = mapper.readValue(EntityUtils.toString(entity), Map.class);
                    return  (List<Object>) obj.get("response");
                } else {
                    throw new RuntimeException("Unexpected response status: " + status);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Unexpected");
        }
    }


    public static <T> List<T> getAllData(String url, Function<Map<String, Object>, T> mapper) {
        List<T> allData = new ArrayList<T>();

        int currentOffset = 0;
        int totalCount = 0;
        List<Object> result;

        do {
            result = RequestHelper.getResponseContent(
                    url + "&count=100&offset=" + currentOffset
            );
            currentOffset += 100;
            totalCount = (int) result.get(0);
            if (totalCount == 0) {
                System.out.println("No data found");
                break;
            }

            for (int i = 1; i < result.size(); i++) {
                allData.add(mapper.apply((Map<String, Object>) result.get(i)));
            }
        }
        while (totalCount > currentOffset);
        return allData;
    }
}
