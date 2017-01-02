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
    <title>My JSP 'rank.jsp' starting page</title>
    <meta http-equiv="content-type" content="'text/html;charset=gb2312">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<body >
<h1> 游戏排行榜 </h1>
<% String ulStr=(String) request.getAttribute("userlistStr");
    JSONObject jo=new JSONObject(ulStr);
    JSONArray ja=jo.getJSONArray("users");

out.write("rank"+"\t"+"username"+"\t"+"score"+"\n");

for (int i=0;i<ja.length();i++) {
    JSONObject jotemp = ja.getJSONObject(i);
    out.write((i + 1) + "\t\t" + jotemp.getString("username") + "\t\t" + jotemp.getString("score")+"\n");
    jotemp = null;
}
%>

</body>
</html>




