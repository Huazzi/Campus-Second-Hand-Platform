package com.erHuo.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.erHuo.dao.GoodDaoImpl;
import com.erHuo.dao.IGoodDao;
import com.erHuo.model.Good;
import com.erHuo.util.RecommendationService;

/**
 * 推荐系统Servlet
 * 提供各种推荐API接口
 */
@WebServlet("/api/recommendations/*")
public class RecommendationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IGoodDao goodDao = new GoodDaoImpl();

    public RecommendationServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        String pathInfo = request.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                // 默认返回错误信息
                sendError(out, "路劲信息不能为空");
                return;
            }

            // 解析路径信息
            String[] pathParts = pathInfo.split("/");
            if (pathParts.length < 2) {
                sendError(out, "错误的路径信息");
                return;
            }

            // 获取推荐类型
            String recommendationType = pathParts[1];

            // 从请求参数中获取通用参数
            int limit = getIntParameter(request, "limit", 10);
            int offset = getIntParameter(request, "offset", 0);
            Integer userId = getIntParameterOrNull(request, "userId");

            List<Good> recommendations;

            // 根据推荐类型调用相应的推荐服务
            switch (recommendationType) {
                // 基于内容的推荐
                case "content-based":
                    if (userId == null) {
                        sendError(out, "UserID信息不能为空");
                        return;
                    }
                    recommendations = RecommendationService.getContentBasedRecommendations(userId, limit, offset);
                    break;

                // 基于热门的推荐
                case "popular":
                    Integer categoryId = getIntParameterOrNull(request, "categoryId");
                    recommendations = RecommendationService.getPopularRecommendations(userId, categoryId, limit,
                            offset);
                    break;

                // 最新上架推荐
                case "new-arrivals":
                    int days = getIntParameter(request, "days", 7);
                    recommendations = RecommendationService.getNewArrivalsRecommendations(days, userId, limit, offset);
                    break;

                default:
                    sendError(out, "未知的推荐类型: " + recommendationType);
                    return;
            }

            // 如果推荐服务返回的商品不够，从数据库中补充
            if (recommendations.size() < limit) {
                List<Good> additionalGoods = goodDao.findRandomGoods(limit - recommendations.size());
                recommendations.addAll(additionalGoods);
            }

            // 将推荐结果转换为JSON响应
            JsonArray jsonArray = new JsonArray();
            for (Good good : recommendations) {
                jsonArray.add(good.toJson());
            }

            out.print(jsonArray.toString());

        } catch (Exception e) {
            e.printStackTrace();
            sendError(out, "处理get推荐请求失败: " + e.getMessage());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        String pathInfo = request.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                sendError(out, "请求路径不能为空");
                return;
            }

            String[] pathParts = pathInfo.split("/");
            if (pathParts.length < 2) {
                sendError(out, "Invalid action path");
                return;
            }

            String action = pathParts[1];

            // 处理不同类型的POST请求
            switch (action) {
                // 记录用户浏览行为
                case "view":
                    handleRecordView(request, out);
                    break;

                // 记录推荐点击行为
                case "click":
                    handleRecordClick(request, out);
                    break;

                default:
                    sendError(out, "未知的用户行为: " + action);
            }

        } catch (Exception e) {
            e.printStackTrace();
            sendError(out, "处理post请求失败: " + e.getMessage());
        }
    }

    /**
     * 处理记录浏览行为的请求
     */
    private void handleRecordView(HttpServletRequest request, PrintWriter out) {
        int userId = getIntParameter(request, "userId", 0);
        int goodId = getIntParameter(request, "goodId", 0);
        int duration = getIntParameter(request, "duration", 0);

        if (userId <= 0 || goodId <= 0) {
            sendError(out, "userId 和 goodId 不能为空");
            return;
        }

        boolean success = RecommendationService.recordUserView(userId, goodId, duration);

        JsonObject response = new JsonObject();
        response.addProperty("status", success ? "success" : "failed");
        out.print(response.toString());
    }

    /**
     * 处理记录推荐点击的请求
     */
    private void handleRecordClick(HttpServletRequest request, PrintWriter out) {
        int userId = getIntParameter(request, "userId", 0);
        int goodId = getIntParameter(request, "goodId", 0);
        String type = request.getParameter("type");

        if (userId <= 0 || goodId <= 0 || type == null || type.isEmpty()) {
            sendError(out, "不合法的参数: userId, goodId 和 type 不能为空");
            return;
        }

        boolean success = RecommendationService.recordRecommendationClick(userId, goodId, type);

        JsonObject response = new JsonObject();
        response.addProperty("status", success ? "success" : "failed");
        out.print(response.toString());
    }

    /**
     * 发送错误响应
     */
    private void sendError(PrintWriter out, String message) {
        JsonObject error = new JsonObject();
        error.addProperty("error", true);
        error.addProperty("message", message);
        out.print(error.toString());
    }

    /**
     * 获取整型请求参数，如果不存在则返回默认值
     */
    private int getIntParameter(HttpServletRequest request, String name, int defaultValue) {
        String value = request.getParameter(name);
        if (value != null && !value.isEmpty()) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                // 如果不存在则返回默认值
            }
        }
        return defaultValue;
    }

    /**
     * 获取整型请求参数，如果不存在则返回null
     */
    private Integer getIntParameterOrNull(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        if (value != null && !value.isEmpty()) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                // 如果不存在则返回NULL
            }
        }
        return null;
    }
}