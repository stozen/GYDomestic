<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'client.jsp' starting page</title>
    
	<!-- <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page"> -->
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="scripts/jquery-2.0.3.js"></script>
	<script type="text/javascript">
	$(function(){
	    $("#send").click(function(){
	    	var jsondata = $("#jsondata").val();
	    	var jsonobj = JSON.parse(jsondata);
	    	
	    	var callback = function (data) {
	    		$("#result").html(JSON.stringify(data));
	    	};
	    	
			$.postJSON('<%=basePath%>user/test', jsonobj, callback);
	
			});
		});
	
		$.postJSON = function(url, data, callback) {
			return jQuery.ajax({
				'type' : 'POST',
				'url' : url,
				'contentType' : 'application/json',
				'data' : JSON.stringify(data),
				'dataType' : 'json',
				'success' : callback
			});
		};
	</script>
  </head>
  
  <body>
  		JSON对象
		<br>
		<textarea id="jsondata" cols="60" rows="5">
			{"id":2,"gender":0,"mobile":"15072370640","password":"123456","accessToken":"fd6bf3dd3cca4b0ca7c9099447994dba"}
		</textarea><br>
	
		<button id="send">POST</button><br>

		<!-- <font id="result" color="red"></font> -->
</body>
</html>
