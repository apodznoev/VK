package com.company;

import com.company.entities.Comment;
import com.company.entities.WallPost;
import com.company.units.Wall;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {
        Wall wall = new Wall(179402747);
        Map<WallPost, List<Comment>> posts = wall.getWallWithComments();
        posts.entrySet()
                .stream()
                .filter(entry -> entry.getKey().isOriginalPost() || !entry.getValue().isEmpty())
                .forEach(postAndComments -> {
                    System.out.println(
                            "Post. Likes:" + postAndComments.getKey().getLikesCount() +
                                    ", reposts:" + postAndComments.getKey().getRepostsCount() +
                                    ", text:" + postAndComments.getKey().getUserText() +
                                    ", comments:" + postAndComments.getValue());
                });
        System.out.println("_______________________---__________________________");
//        Wall myWall = new Wall(554502);
//        myWall.getPosts();
    }
}
