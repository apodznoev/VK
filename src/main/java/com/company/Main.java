package com.company;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Wall wall = new Wall(179402747);
        wall.getPosts();
        System.out.println("_______________________---__________________________");
        Wall myWall = new Wall(554502);
        myWall.getPosts();
    }
}
