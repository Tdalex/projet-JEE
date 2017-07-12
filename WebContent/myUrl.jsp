<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.namex.shortener.Logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
</head>
<body>
	<h3>My Shorten Url</h3>
	<%
        if (session.getAttribute("name") == null) {
			 response.sendRedirect("index.jsp");
        }else{
        	Integer id = (Integer) session.getAttribute("ID");
	        String serverName = request.getServerName();
		    int port = request.getServerPort();
		    String contextPath = request.getContextPath();
    %>    
	<a href='index.jsp'>Home</a>
	<form action="userAction" method="POST">
		<button type='submit' name='type' value='logout'>Logout</button>
    </form>
    <table border="1">
	    <tr>
		    <th>Url</th>
		    <th>Shorten</th>
		    <th>Views</th>
		    <th>Start date</th>
		    <th>End date</th>
		    <th>Has password</th>
		    <th>Modify</th>
		    <th>Delete</th>
	    </tr>
	    <%=new Logic().getUserUrl(serverName, port, contextPath, id)%>
    </table>
	<%
        }
    %>
	
</body>
</html>