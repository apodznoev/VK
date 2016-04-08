package com.company.units;

import com.company.Constants;
import com.company.entities.Comment;
import com.company.entities.WallPost;
import com.company.transport.RequestHelper;

import java.util.*;

/**
 * @author Андрей
 * @since 8 Apr 2016
 */
public class Wall {
    private final long ownerId;

    public Wall(long ownerId) {
        this.ownerId = ownerId;
    }

    public List<WallPost> getPosts() {
        return RequestHelper.getAllData(
                Constants.BASE_URL + "wall.get?owner_id=" + ownerId,
                stringObjectMap -> new WallPost(ownerId, stringObjectMap));
    }

    public List<Comment> getComments(long postId) {
        return RequestHelper.getAllData(
                Constants.BASE_URL + "wall.getComments?post_id=" + postId + "&preview_length=0",
                stringObjectMap -> new Comment(stringObjectMap));
    }

    public Map<WallPost, List<Comment>> getWallWithComments() {
        Map<WallPost, List<Comment>> result = new TreeMap<>(
                (WallPost o1, WallPost o2) -> -Long.compare(o1.getPostTime(), o2.getPostTime())
        );
        List<WallPost> posts = getPosts();
        List<Long> postsWithComments = new ArrayList<Long>();
        posts.forEach(post -> {
            if (post.getCommentsCount() > 0) {
                postsWithComments.add(post.getPostId());
                result.put(post, new ArrayList<>(post.getCommentsCount()));
            } else {
                result.put(post, Collections.emptyList());
            }
        });

        for(Map.Entry<WallPost, List<Comment>> postWithComments : result.entrySet()) {
            if(postsWithComments.contains(postWithComments.getKey().getPostId())) {
                postWithComments.getValue().addAll(getComments(postWithComments.getKey().getPostId()));
            }
        }

        return result;
    }

    //post id -> all comments under, most recent first
    public Map<Long, List<Comment>> getWallComments() {
        Map<Long, List<Comment>> result = new TreeMap<>();
        long[] postsWithComments =
                getPosts().stream().filter(post -> post.getCommentsCount() > 0).mapToLong(WallPost::getPostId).toArray();
        for(long postWithComments : postsWithComments) {
            result.put(postWithComments, getComments(postWithComments));
        }
        return result;
    }
}
