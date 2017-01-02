<%--
  Created by IntelliJ IDEA.
  User: 87485
  Date: 2016/12/11
  Time: 16:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="org.json.JSONObject" %>
<%@ page import="org.json.JSONArray" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
<head>
    <base href="<%=basePath%>">
    <title>My JSP 'Rank.jsp' starting page</title>
    <meta http-equiv="content-type" content="'text/html;charset=gb2312">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <style type="text/css">
        table{
            width:300px;
            margin-top: 0px;
            margin-right: auto;
            margin-bottom: 0px;
            margin-left: auto;
            text-align: center;
            background-color: #808080;
            font-size: 15pt;
        }
        td{
            padding: 5px;
            background-color: #FFFFFF;
        }
    </style>
</head>
<body >
<h1 align="center"> 游戏排行榜 </h1>
<table  border="1" cellpadding="0" cellspacing="1" bordercolor="#96A353" >
<% String ulStr=(String) request.getAttribute("userlistStr");
    JSONObject jo=new JSONObject(ulStr);
    JSONArray ja=jo.getJSONArray("users");%>
 <tr><th>排名</th>
     <th>用户名</th>
     <th>分数</th>
 </tr>
<% for (int i=0;i<ja.length();i++) {
    JSONObject jotemp = ja.getJSONObject(i);%>
    <tr><td><% out.print(( i + 1 )); %></td>
         <td><% out.print( jotemp.getString("username")); %></td>
        <td><% out.print( jotemp.getString("score")); %></td>
    </tr>
    <%
    jotemp = null;
}
%>
</body>
</html>




