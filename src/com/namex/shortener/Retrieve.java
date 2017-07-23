package com.namex.shortener;
 
import java.io.IOException;
import java.io.PrintWriter;

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

        response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
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
	    out.println("<html><body>");
	    if (longUrl == null) {
	        // if long url not found, send to index.jsp
	        System.out.println("long url not found");
	        out.println("<p>long url not found</p>");
	    } else if (error != null) {
	        // if long url not found, send to index.jsp
	        System.out.println(error);
	        out.println("<p>"+error+"</p>");
	    } else if (password != false) {
	        // ask for password
	        System.out.println("password please");
	        out.println("<p>password please</p>"); 
	        out.println("<form action='checkPassword' method='POST'><input name='urlId' type='hidden' value='"+urlId+"'></input><input name='password' type='text'></input><button type='submit'>Continue</button></form>"); 	
	    } else {
	        //if long url found, so redirect the browser
	        System.out.println("redirecting to "+longUrl );
	        out.println("<p>redirecting to "+longUrl+"</p>");
	        try {
				new Logic().addViewUrl(urlId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        response.sendRedirect(longUrl);
	    }
        out.println("<a href='index.jsp'>Back to index</p>");
	    out.println("</body></html>");
    }
 
}