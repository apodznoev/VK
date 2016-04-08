package com.company;

import java.util.List;
import java.util.Map;

/**
 * @author Андрей
 * @since 8 Apr 2016
 */
public class WallPost {
    private final long userId;
    private final Map<String, Object> postData;
    private static final String POST_TYPE = "post";
    private static final String REPOST_TYPE = "copy";

    public WallPost(long userId, Map<String, Object> postElements) {
        this.userId = userId;
        this.postData = postElements;
    }

    public long getPostId() {
        return (long) postData.get("id");
    }

    public long getAuthorId() {
        return (long) postData.get("from_id");
    }
    public long getPostTime() {
        return (long) postData.get("date");
    }

    public boolean isOriginalPost() {
         return postData.get("post_type").equals(POST_TYPE);
    }

    public String getUserText() {
        return postData.containsKey("copy_text") ? (String) postData.get("copy_text") : getOriginalText();
    }

    public String getOriginalText() {
        return (String) postData.get("text");
    }

    public int getCommentsCount() {
        return (int) ((Map<String,Object>) postData.get("comments")).get("count");
    }

    public int getLikesCount() {
        return (int) ((Map<String,Object>) postData.get("likes")).get("count");
    }

    public int getRepostsCount() {
        return (int) ((Map<String,Object>) postData.get("reposts")).get("count");
    }

    public List<Object> getAttachments(){
        return (List<Object>) postData.get("attachments");
    }

//    geo - если в записи содержится информация о местоположении, то она будет представлена в данном поле. Более подробная информация представлена на странице Описание поля geo
//    signer_id - если запись была опубликована от имени группы и подписана пользователем, то в поле содержится идентификатор её автора
//    copy_owner_id - если запись является копией записи с чужой стены, то в поле содержится идентификатор владельца стены у которого была скопирована запись
//    copy_post_id - если запись является копией записи с чужой стены, то в поле содержится идентификатор скопированной записи на стене ее владельца
//    copy_text
}
