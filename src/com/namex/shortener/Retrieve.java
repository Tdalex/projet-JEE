package com.namex.shortener;
 
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
public class Retrieve extends HttpServlet {
 
    private static final long serialVersionUID = 1293961717469276130L;
 
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
 
	    String urlId = request.getServletPath();
	 
	    String longUrl = null;
	    String error = null;
	    boolean password = false;
	    
	    if (urlId != null && !"".equals(urlId)) {
	        try {
	        	longUrl  = new Logic().getLongUrl(urlId);
	        	error    = new Logic().getErrorUrl(urlId);
	        	password = new Logic().hasPasswordUrl(urlId);
	        } catch (Exception e) {
		        // handling exception here
		        e.printStackTrace();
	        }
	    }        
	    
	    if (longUrl == null) {
	        // if long url not found, send to index.jsp
	        System.out.println("long url not found, back to index.jsp");
	        response.sendRedirect("index.jsp");
	    } else if (error != null) {
	        // if long url not found, send to index.jsp
	        System.out.println(error);
	        response.sendRedirect("index.jsp");
	    } else if (password) {
	        // ask for password
	        System.out.println("password please");	    	
	    } else {
	        //if long url found, so redirect the browser
	        System.out.println("redirecting to "+longUrl );
	        try {
				new Logic().addViewUrl(urlId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	       response.sendRedirect(longUrl);
	    }
    }
 
}