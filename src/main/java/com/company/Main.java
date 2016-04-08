package com.company;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        Wall wall = new Wall(179402747);
        List<WallPost> posts = wall.getPosts();
        posts.stream()
                .filter(WallPost::isOriginalPost)
                .forEach(post -> {
                    System.out.println(
                            "Post. Likes:" + post.getLikesCount() +
                                    ", reposts:" + post.getRepostsCount() +
                                    ", text:" + post.getUserText());
                });
        System.out.println("_______________________---__________________________");
        Wall myWall = new Wall(554502);
        myWall.getPosts();
    }
}
