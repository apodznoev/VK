package com.company;

import java.util.List;

/**
 * @author Андрей
 * @since 8 Apr 2016
 */
public class Wall {
    private final long ownerId;

    public List<WallPost> getPosts() {
        return RequestHelper.getAllData(
                Constants.BASE_URL + "wall.get?owner_id=" + ownerId,
                stringObjectMap -> new WallPost(ownerId, stringObjectMap));
    }

    public Wall(long ownerId) {
        this.ownerId = ownerId;
    }
}
