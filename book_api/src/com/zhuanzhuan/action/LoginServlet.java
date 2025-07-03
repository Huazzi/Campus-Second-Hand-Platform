package com.zhuanzhuan.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.zhuanzhuan.dao.UserDaoImpl;
import com.zhuanzhuan.model.User;
import com.zhuanzhuan.util.DaoFactory;
import com.zhuanzhuan.util.WX;


/**  
 * @Description: 登录Servlet
 * @author Huazzi
 * @date 2025年7月2日 下午
 */  
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String openid = null;
		response.setContentType("application/json;charset=utf-8");
		PrintWriter writer = response.getWriter();
		String code = request.getParameter("code");

		String pattern = "\"openid\":\"(.*)\"";
		Pattern reg = Pattern.compile(pattern);
		
		System.out.println("code" + code);
		if (!(code == null || code.isEmpty())) {
			
			String data = WX.getAppId(code);
			Matcher matcher = reg.matcher(data);
			if (matcher.find()) {
				openid = matcher.group(1);
			}
		}
		
		System.out.println("apid:" + openid);

		// 如果openid不为空，则说明用户已经登录
		if (openid != null) {
			UserDaoImpl userDao = DaoFactory.getUserDao();
			User user = userDao.loadByAppId(openid);
			//
			if (user == null) {
				String nickName = request.getParameter("nickname");
				String head = request.getParameter("head");
				String gender_s = request.getParameter("gender");
				Boolean gender = ("1".equals(gender_s) ? true : false);
				
				user = new User();
				user.setOpenid(openid);
				user.setNickname(nickName);
				user.setHead(head.trim());
				user.setSex(gender);
				user.setCollege("郑州大学");
				
				System.out.println(nickName);
				userDao.add(user);
				user = userDao.loadByAppId(openid);
			}
			System.out.println("用户：" + user.toJson().toString());
			
			writer.write(user.toJson().toString());
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
