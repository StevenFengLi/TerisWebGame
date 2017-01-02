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

   <!-- <script type="text/javascript" src="js/jquery.min.js"></script>
    <script type="text/javascript">
        var xmlHttp;
        function createXMLHttpRequest() {
            if (window.ActiveObject) {
                window.ActiveXObject("Microsoft.XMLHTTP");
            }
            else if (window.XMLHttpRequest) {
                xmlHttp = new XMLHttpRequest();
            }
        }
        function beginCheck(){
            alert("ok");
            createXMLHttpRequest();
            xmlHttp.onreadystatechange = processor;
            xmlHttp.open("GET","/RankServlet",true);
            xmlHttp.send(null);
        }
        function processor(){
            var responseContext;
            if(xmlHttp.readyState == 4){
                if(xmlHttp.status == 200){
                    var f=xmlHttp.responseText;
                    document.getElementById("rank").value = f;
                    document.write(f);
                    document.form1.submit();
                }
                else
                {
                    alert("您的网络有异常");
                }
            }
        }
        </script>-->
    <body >
                 <h1> 游戏排行榜 </h1>
        <% String rank =request.getParameter("rank");
        JSONObject jo=new JSONObject(rank);
            JSONArray ja=jo.getJSONArray("users"); %>

        <% out.write("rank"+"\t"+"username"+"\t"+"score"); %>
        <% for (int i=0;i<ja.length();i++) {
        JSONObject jotemp = ja.getJSONObject(i);
        out.write((i + 1) + "\t\t" + jotemp.getString("username") + "\t\t" + jotemp.getString("score"));
        jotemp = null;
        }
        %>
                 <form name="form1">
                     <input name="json" type="hidden" id="rank" value="0">
                 </form>

    </body>
</html>



