<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<script src='https://www.google.com/recaptcha/api.js'></script>
</head>
<body>
	<h3>Login</h3>
	<%
        if (session.getAttribute("name") == null) {
    %>
	<form action='userAction' method=POST>
		Enter Your Name <input type='text' name='name'/><br>
		Enter Your password <input type='password' name='password'/><br>
		<button type='submit' name='type' value='login'>login</button>
	</form>
	
	<h3>Register</h3>
	<form action='userAction' method=POST>
		Enter Your Name <input type='text' name='name'/><br>
		Enter Your password <input type='password' name='password'/><br>
		<button type='submit' name='type' value='register'>register</button>
	</form>	
	<h3>Shorten URL</h3>
    <form action="insert" method="GET">
        Long URL: <input type="text" name="longUrl" size="100" /><br> 
        <!--  <div class="g-recaptcha" data-sitekey="6LcWYCkUAAAAAGw-Dr-HXztPiGbGGajgbdOOQhEn"></div>   -->    
        <input type="submit" value="Get Short !" />
    </form>
	<%
        }else{
    %>    
	<h3>Welcome <%=session.getAttribute("name")%></h3>
	<a href='myUrl.jsp'>My Shorten Url</a>
	<form action="userAction" method="POST">
		<button type='submit' name='type' value='logout'>Logout</button>
    </form>
	<h3>Shorten URL</h3>
    <form action="insert" method="GET">
        Long URL: <input type="text" name="longUrl" size="100" /><br> 
        Custom short URL: <input type="text" maxlength='20' name="customShort" size="100" /><br> 
        Max view: <input type="number" name="maxView" size="100" /> <br> 
        Password: <input type="password" name="password" size="100" /><br> 
        Date start (format yyyy-mm-dd): <input type="text" maxlength='10' name="dateStart" size="100" /> <br> 
        Date end (format yyyy-mm-dd): <input type="text" maxlength='10' name="dateEnd" size="100" /> <br> 
        <!--  <div class="g-recaptcha" data-sitekey="6LcWYCkUAAAAAGw-Dr-HXztPiGbGGajgbdOOQhEn"></div>   -->    
        <input type="submit" value="Get Short !" />
    </form>
	<%
        }
    %>
	
    </p>
    <%
        if (session.getAttribute("shortUrl") != null) {
    %>
    Hi, your short url is:
    <br />
    <br />
    <%=session.getAttribute("shortUrl")%>
    <%session.removeAttribute("shortUrl");%>
 
    <%
        }
    %>
</body>
</html>