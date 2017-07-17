<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.namex.shortener.Logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<script src='https://www.google.com/recaptcha/api.js'></script>
</head>
<body>
	<h3>Modify Url</h3>
	<%
        if (session.getAttribute("name") == null) {
			 response.sendRedirect("index.jsp");
        }else{
        	String idUrl  = request.getParameter("update");
        	Integer idUser = (Integer) session.getAttribute("ID");
    %>    
	<a href='index.jsp'>Home</a>
	<a href='myUrl.jsp'>My Shorten</a>
	<form action="userAction" method="POST">
		<button type='submit' name='type' value='logout'>Logout</button>
    </form>
    <form action="UpdateShorten" method="POST">
	    <%=new Logic().getModifyShorten(idUrl, idUser)%>    
	    <div class="g-recaptcha" data-sitekey="6LcWYCkUAAAAAGw-Dr-HXztPiGbGGajgbdOOQhEn"></div>
        <input type="submit" value="Update Shorten" />
    </form>
	<%
        }
    %>
	
</body>
</html>