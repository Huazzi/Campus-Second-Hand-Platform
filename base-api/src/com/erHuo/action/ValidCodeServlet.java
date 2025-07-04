package com.erHuo.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.erHuo.util.ValidCodeUtil;

/**
 * ���Ͷ�����֤������
 * ��Ҫ��request������
 * 	validcode��Ҫ���͸��û�����֤��
 * 	mobliephone��������֤����ֻ���
 */
@WebServlet("/ValidCodeServlet")
public class ValidCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// ��ȡ����
		String validCode = request.getParameter("validcode");
		if(validCode == null)
			validCode = String.valueOf((int)(Math.random() * 1000000));
		String mobilePhone = request.getParameter("mobilephone");
		
		System.out.println("������֤�룺" + mobilePhone);
		// ����֤����ӵ�Session
		HttpSession session = request.getSession();
		session.setAttribute("rand", validCode);
		// ȡ�÷��ͽ��
		String restult = ValidCodeUtil.sendSMS(validCode, mobilePhone);
		// �������
		System.out.println("result:" + restult);
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		writer.print(restult);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
