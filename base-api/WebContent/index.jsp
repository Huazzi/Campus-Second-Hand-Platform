<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="user" class="com.erHuo.model.User" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>二货来了-API</title>
<style type="text/css">
    .title {
        /*水平居中*/
        text-align: center;
        /*垂直居中*/
        line-height: 100px;
        font-family: "楷体", serif;
    }
    .title span {
        background: linear-gradient(to right, #ec695c, #61c454)
        no-repeat right bottom;
        background-size: 0 3px;
        transition: background-size 0.5s ease-in-out;
        /* border-radius: 3px; */
    }
    .title span:hover {
        background-position-x: left;
        background-size: 100% 3px;
    }
</style>
</head>
<body>
<h1 class="title">
    <span>「贰货来了」API——启动！！！</span>
</h1>
</body>
</html>
