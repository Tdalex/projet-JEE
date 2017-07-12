package com.namex.shortener;
 
import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
public class UserAction extends HttpServlet {
 
	private static final long serialVersionUID = -5372953040696716979L;

	@Override	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

	    HttpSession session = request.getSession();
		if (request.getParameter("type").compareTo("logout") == 0) {
			 session.invalidate();
		     response.sendRedirect("index.jsp");
	    }
		
	    boolean verified = true;
	    Integer ID = 0;
	    if(request.getParameter("name").isEmpty() || request.getParameter("password").isEmpty()){
	        response.sendRedirect("index.jsp");	    	
	    }
	    
        try {
        	verified = new Logic().checkUser(request.getParameter("name"));
        } catch (Exception e) {
	        // handling exception here
	        e.printStackTrace();
        }
	        
	    if (verified == false && request.getParameter("type").compareTo("login") == 0) {
	        try {
				ID = new Logic().connectUser(request.getParameter("name"), request.getParameter("password"));
				if(ID.compareTo(0) != 0){
					session.setAttribute("ID", ID);
				    session.setAttribute("name", request.getParameter("name"));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  	
	        response.sendRedirect("index.jsp");
	    } else if(verified == true && request.getParameter("type").compareTo("register") == 0) {
	    	try {
				ID = new Logic().addUser(request.getParameter("name"), request.getParameter("password"));
				if(ID.compareTo(0) != 0){
					session.setAttribute("ID", ID);
					session.setAttribute("name", request.getParameter("name"));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        response.sendRedirect("index.jsp");
	    } else {
	        response.sendRedirect("index.jsp");
	    }
    }
 
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

		doPost(request, response);
    }
 
}