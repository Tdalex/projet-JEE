<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
</head>
<body>
    <form action="insert" method="GET">
        Long URL: <input type="text" name="longUrl" size="100" /><br> 
        Max view: <input type="text" name="maxView" size="100" /> <br> 
        Password: <input type="password" name="password" size="100" /><br> 
        Date start (format yyyy-mm-dd): <input type="text" name="dateStart" size="100" /> <br> 
        Date end (format yyyy-mm-dd): <input type="text" name="dateEnd" size="100" /> 
        
        <input type="submit" value="Get Short !" />
 
    </form>
    </p>
    <%
        if (session.getAttribute("shortUrl") != null) {
    %>
    Hi, your short url is:
    <br />
    <br />
    <%=session.getAttribute("shortUrl")%>
 
    <%
        }
    %>
</body>
</html>