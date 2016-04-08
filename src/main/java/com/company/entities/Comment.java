package com.company.entities;

import com.company.Util;

import java.util.Map;

/**
 * @author Андрей
 * @since 8 Apr 2016
 */
public class Comment {
    private final Map<String, Object> fields;

    public Comment(Map<String, Object> stringObjectMap) {
        this.fields = stringObjectMap;
    }

    public long getId(){
        return Util.getLong(fields,"cid");
    }

    public long getAuthorId(){
        return Util.getLong(fields,"uid");
    }

    public long getCommentDate(){
        return Util.getLong(fields,"date");
    }

    public boolean isReply() {
        return fields.containsKey("reply_to_uid") && fields.containsKey("reply_to_cid");
    }

    public long getRepliedUserId(){
        return Util.getLong(fields,"reply_to_uid");
    }

    public long getRepliedCommentId(){
        return Util.getLong(fields,"reply_to_cid");
    }

    public String getText(){
        return (String) fields.get("text");
    }

    @Override
    public String toString() {
        return "Comment{" + getText() + '}';
    }
}
