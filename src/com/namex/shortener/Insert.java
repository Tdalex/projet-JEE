package com.namex.shortener;
 
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

 
public class Insert extends HttpServlet {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Both POST and GET method are allowed to insert new record
     *
     */
 
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
 
    doGet(request, response);
    }
 
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
 
    	Map<String, String> parameters = new HashMap<String, String>();
    	
    	parameters.put("longUrl" , request.getParameter("longUrl"));
    	parameters.put("maxView" , request.getParameter("maxView"));
    	parameters.put("dateStart" , request.getParameter("dateStart"));
    	parameters.put("dateEnd" , request.getParameter("dateEnd"));
    	parameters.put("password" , request.getParameter("password"));
    	
	    request.getSession().setAttribute("a", "a");
	    String a = response.encodeURL(request.getRequestURI());
	    request.getSession().setAttribute("a", "a");
	    System.out.println("->"+request.getRequestURI());
	    System.out.println(a);
	    System.out.println("shortening " + parameters.get("longUrl"));
	    String serverName = request.getServerName();
	    int port = request.getServerPort();
	    String contextPath = request.getContextPath();
	    String shortUrl = null;

	    try {
	        shortUrl = new Logic().getShort(serverName, port, contextPath, parameters);
	    } catch (Exception e) {
	 
	        e.printStackTrace();
	    }
	    System.out.println("short url: " + shortUrl);
	    request.getSession().setAttribute("shortUrl", shortUrl);
	    response.sendRedirect("index.jsp");
    }
}