package com.zhuanzhuan.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zhuanzhuan.model.Good;

/**
 * 推荐系统服务类
 * 负责与Python推荐服务通信
 */
public class RecommendationService {

    // 推荐系统API基础URL
    private static final String RECOMMENDATION_API_BASE_URL = "http://localhost:8000";

    // 推荐类型常量
    public static final String RECOMMENDATION_TYPE_CONTENT = "content-based";
    public static final String RECOMMENDATION_TYPE_POPULAR = "popular";
    public static final String RECOMMENDATION_TYPE_NEW = "new-arrivals";

    /**
     * 获取基于内容的推荐
     * 
     * @param userId 用户ID
     * @param limit  返回数量
     * @param offset 偏移量
     * @return 推荐商品列表
     */
    public static List<Good> getContentBasedRecommendations(int userId, int limit, int offset) {
        StringBuilder urlBuilder = new StringBuilder(RECOMMENDATION_API_BASE_URL + "/recommendations/content-based");
        urlBuilder.append("?user_id=").append(userId); // 添加用户ID参数
        urlBuilder.append("&limit=").append(limit); // 添加返回数量参数
        urlBuilder.append("&offset=").append(offset); // 添加偏移量参数

        String response = sendGetRequest(urlBuilder.toString());
        return parseGoodsFromResponse(response);
    }

    /**
     * 获取热门商品推荐
     * 
     * @param userId     用户ID（可选）
     * @param categoryId 类别ID（可选）
     * @param limit      返回数量
     * @param offset     偏移量
     * @return 推荐商品列表
     */
    public static List<Good> getPopularRecommendations(Integer userId, Integer categoryId, int limit, int offset) {
        StringBuilder urlBuilder = new StringBuilder(RECOMMENDATION_API_BASE_URL + "/recommendations/popular");
        urlBuilder.append("?limit=").append(limit);
        urlBuilder.append("&offset=").append(offset);

        if (userId != null) {
            urlBuilder.append("&user_id=").append(userId);
        }

        if (categoryId != null) {
            urlBuilder.append("&category_id=").append(categoryId);
        }

        String response = sendGetRequest(urlBuilder.toString());
        return parseGoodsFromResponse(response);
    }

    /**
     * 获取新上架商品推荐
     * 
     * @param days   最近几天
     * @param userId 用户ID（可选）
     * @param limit  返回数量
     * @param offset 偏移量
     * @return 推荐商品列表
     */
    public static List<Good> getNewArrivalsRecommendations(int days, Integer userId, int limit, int offset) {
        StringBuilder urlBuilder = new StringBuilder(RECOMMENDATION_API_BASE_URL + "/recommendations/new-arrivals");
        urlBuilder.append("?days=").append(days);
        urlBuilder.append("&limit=").append(limit);
        urlBuilder.append("&offset=").append(offset);

        if (userId != null) {
            urlBuilder.append("&user_id=").append(userId);
        }

        String response = sendGetRequest(urlBuilder.toString());
        return parseGoodsFromResponse(response);
    }

    /**
     * 记录用户浏览行为
     * 
     * @param userId       用户ID
     * @param goodId       商品ID
     * @param viewDuration 浏览时长（秒）
     * @return 是否成功
     */
    public static boolean recordUserView(int userId, int goodId, int viewDuration) {
        String url = RECOMMENDATION_API_BASE_URL + "/user-views";

        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("user_id", userId);
        requestBody.addProperty("good_id", goodId);
        requestBody.addProperty("view_duration", viewDuration);

        String response = sendPostRequest(url, requestBody.toString());

        if (response != null) {
            // 修复：使用正确的JsonParser方法
            JsonObject jsonResponse = new JsonParser().parse(response).getAsJsonObject();
            return "success".equals(jsonResponse.get("status").getAsString());
        }

        return false;
    }

    /**
     * 记录推荐点击事件
     * 
     * @param userId             用户ID
     * @param goodId             商品ID
     * @param recommendationType 推荐类型
     * @return 是否成功
     */
    public static boolean recordRecommendationClick(int userId, int goodId, String recommendationType) {
        String url = RECOMMENDATION_API_BASE_URL + "/recommendations/click";

        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("user_id", userId);
        requestBody.addProperty("recommended_good_id", goodId);
        requestBody.addProperty("recommendation_type", recommendationType);

        String response = sendPostRequest(url, requestBody.toString());

        if (response != null) {
            JsonObject jsonResponse = new JsonParser().parse(response).getAsJsonObject();
            return "success".equals(jsonResponse.get("status").getAsString());
        }

        return false;
    }

    /**
     * 解析响应中的商品信息
     * 
     * @param response JSON响应字符串
     * @return 商品列表
     */
    private static List<Good> parseGoodsFromResponse(String response) {
        List<Good> goods = new ArrayList<>();

        if (response == null) {
            return goods;
        }

        try {
            JsonArray jsonArray = new JsonParser().parse(response).getAsJsonArray();

            for (JsonElement element : jsonArray) {
                JsonObject goodJson = element.getAsJsonObject();

                Good good = new Good();
                good.setId(goodJson.get("id").getAsInt());
                good.setGoodname(goodJson.get("goodname").getAsString());
                good.setPrice(goodJson.get("price").getAsDouble());
                good.setDescription(goodJson.get("description").getAsString());

                // 可选字段需要检查是否存在
                if (goodJson.has("image") && !goodJson.get("image").isJsonNull()) {
                    good.setImages(goodJson.get("image").getAsString());
                }

                if (goodJson.has("view_count") && !goodJson.get("view_count").isJsonNull()) {
                    good.setViewCount(goodJson.get("view_count").getAsInt());
                }

                if (goodJson.has("category_id") && !goodJson.get("category_id").isJsonNull()) {
                    good.setCategoryId(goodJson.get("category_id").getAsInt());
                }

                if (goodJson.has("collect") && !goodJson.get("collect").isJsonNull()) {
                    good.setCollectNum(goodJson.get("collect").getAsInt());
                }

                // 设置其他统计字段的默认值
                if (goodJson.has("inquiry_count") && !goodJson.get("inquiry_count").isJsonNull()) {
                    good.setInquiryCount(goodJson.get("inquiry_count").getAsInt());
                }

                if (goodJson.has("location") && !goodJson.get("location").isJsonNull()) {
                    good.setLocation(goodJson.get("location").getAsString());
                }

                goods.add(good);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return goods;
    }

    /**
     * 发送POST请求
     * 
     * @param urlStr   URL字符串
     * @param jsonBody 请求体JSON字符串
     * @return 响应字符串
     */
    private static String sendPostRequest(String urlStr, String jsonBody) {
        HttpURLConnection conn = null;
        StringBuilder response = new StringBuilder();

        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();

            if (responseCode >= 200 && responseCode < 300) {
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                }
                return response.toString();
            } else {
                System.err.println("POST请求失败，响应码：" + responseCode);
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    /**
     * 发送GET请求
     * 
     * @param urlStr URL字符串
     * @return 响应字符串
     */
    private static String sendGetRequest(String urlStr) {
        HttpURLConnection conn = null;
        StringBuilder response = new StringBuilder();

        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            int responseCode = conn.getResponseCode();

            if (responseCode >= 200 && responseCode < 300) {
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                }
                return response.toString();
            } else {
                System.err.println("GET请求失败，响应码：" + responseCode);
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}