package com.zhuanzhuan.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WX {
	
	private static final String APPID = "wx3aa480575ce7c208";
	private static final String APPSECRET = "b9d30ce1a0c3c9690d2f0ff288c6a190";
	
	public String load(String url,String query) throws Exception
    {
        URL restURL = new URL(url);
        /*
         * 锟剿达拷锟斤拷urlConnection锟斤拷锟斤拷实锟斤拷锟斤拷锟角革拷锟斤拷URL锟斤拷锟斤拷锟斤拷协锟斤拷(锟剿达拷锟斤拷http)锟斤拷锟缴碉拷URLConnection锟斤拷 锟斤拷锟斤拷锟斤拷HttpURLConnection
         */
        HttpURLConnection conn = (HttpURLConnection) restURL.openConnection();
        //锟斤拷锟斤拷式
        conn.setRequestMethod("GET");
        //锟斤拷锟斤拷锟角凤拷锟絟ttpUrlConnection锟斤拷锟诫，默锟斤拷锟斤拷锟斤拷锟斤拷锟絫rue; httpUrlConnection.setDoInput(true);
        conn.setDoOutput(true);
        //allowUserInteraction 锟斤拷锟轿� true锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟矫伙拷锟斤拷锟斤拷锟斤拷锟斤拷锟界弹锟斤拷一锟斤拷锟斤拷证锟皆伙拷锟津）碉拷锟斤拷锟斤拷锟斤拷锟叫对达拷 URL 锟斤拷锟叫硷拷椤�
        conn.setAllowUserInteraction(false);

        PrintStream ps = new PrintStream(conn.getOutputStream());
        ps.print(query);

        ps.close();

        BufferedReader bReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String line,resultStr="";

        while(null != (line=bReader.readLine()))
        {
        	resultStr +=line;
        }
        //System.out.println("3412412---"+resultStr);
        bReader.close();

        return resultStr;

    }
    
	public static String getAppId(String code) {
		try {

            WX restUtil = new WX();

            String resultString = restUtil.load(
                    "https://api.weixin.qq.com/sns/jscode2session",
                    "appid=" + APPID + "&secret=" + APPSECRET + "&js_code=" + code + "&grant_type=authorization_code");
            System.out.println("appid" + resultString);
            return resultString;
            
        } catch (Exception e) {

        	System.out.println("WX.java寮傚父");
	        e.printStackTrace();
	
	        System.out.print("閿欒淇℃伅:" + e.getMessage());

        }
		
		return "error";
	}
}
