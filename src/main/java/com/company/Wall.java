package com.company;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.List;
import java.util.Map;

/**
 * @author Андрей
 * @since 8 Apr 2016
 */
public class Wall {
    private final long userId;

    public void getPosts() {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpget = new HttpGet(
                    Constants.BASE_URL + "wall.get?owner_id=" + userId + "&version=5.4&count=100"
            );

            System.out.println("Executing request " + httpget.getRequestLine());

            httpClient.execute(httpget, response -> {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    ObjectMapper mapper = new ObjectMapper();
                    Map obj = mapper.readValue(EntityUtils.toString(entity), Map.class);
                    List<Object> result = (List<Object>) obj.get("response");
                    int totalCount = (int) result.get(0);
                    if (totalCount == 0) {
                        System.out.println("No posts found");
                        return "";
                    }
                    for (int i = 1; i < result.size(); i++) {
                        WallPost post = new WallPost(userId, (Map<String, Object>) result.get(i));
                        if (post.isOriginalPost()) {
                            System.out.println(
                                    "Post. Likes:" + post.getLikesCount() +
                                            ", reposts:" + post.getRepostsCount() +
                                            ", text:" + post.getUserText());
                        }
                    }
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
                return "";
            });
            System.out.println("----------------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Wall(long userId) {
        this.userId = userId;
    }
}
