package com.namex.shortener;
 
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
public class CheckPassword extends HttpServlet {
 
	private static final long serialVersionUID = -9209903756712961034L;

	@Override	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
	 
    doGet(request, response);
    }
 
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
 
	    String urlId = request.getParameter("urlId");
	    
	    boolean verified = true;
	    String longUrl = null;

        response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
	    if (urlId != null && !"".equals(urlId)) {
	        try {
	        	verified = new Logic().checkPassword(request.getParameter("password"), urlId);
	        	longUrl  = new Logic().getLongUrl(urlId);
	        } catch (Exception e) {
		        // handling exception here
		        e.printStackTrace();
	        }
	    }        
        System.out.println(verified);
	    out.println("<html><body>");
	    if (verified == false) {
	        // if long url not found, send to index.jsp
	        System.out.println("wrong password");
	        out.println("<p>Wrong password</p>");
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