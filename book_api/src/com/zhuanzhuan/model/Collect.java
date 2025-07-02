package com.zhuanzhuan.model;

import com.google.gson.JsonObject;

/**
 * @Description: 收藏实体类
 */
public class Collect {
    private int userID;
    private int goodID;

    public Collect() {
    }

    public Collect(int userID, int goodID) {
        this.userID = userID;
        this.goodID = goodID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getGoodID() {
        return goodID;
    }

    public void setGoodID(int goodID) {
        this.goodID = goodID;
    }

    public JsonObject toJson() {
    	JsonObject json = new JsonObject();
    	json.addProperty("userID", userID);
    	json.addProperty("goodID", goodID);
    	return json;
    }
}
