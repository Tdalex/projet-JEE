package com.namex.shortener;
 
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

 
public class UpdateShorten extends HttpServlet {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = -1283348726324710645L;

	/**
     * Both POST and GET method are allowed to insert new record
     *
     */
 
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

	    HttpSession session = request.getSession();
    			
    	if (session.getAttribute("ID") == null) {
			 response.sendRedirect("index.jsp");
        }else{
        	Integer author = (Integer) session.getAttribute("ID");
        	String deleteUrl = request.getParameter("delete");
        	String updateUrl = request.getParameter("update");
        	if(deleteUrl != null) {
		        try {
					new Logic().deleteUrl(deleteUrl, author);
				} catch (Exception e) {
					e.printStackTrace();
				}	
        	}else if(updateUrl != null){
		    	Map<String, String> parameters = new HashMap<String, String>();
		    	
		    	parameters.put("id" , updateUrl);		    	
		    	parameters.put("longUrl" , request.getParameter("longUrl"));
		    	parameters.put("maxView" , request.getParameter("maxView"));
		    	parameters.put("customShort" , request.getParameter("customShort"));
		    	parameters.put("dateStart" , request.getParameter("dateStart"));
		    	parameters.put("dateEnd" , request.getParameter("dateEnd"));
		    	parameters.put("password" , request.getParameter("password"));

			    try {
			        new Logic().updateUrl(parameters, author);
			    } catch (Exception e) {
			        e.printStackTrace();
			    }
        	}
		    response.sendRedirect("myUrl.jsp");
        }
    }
 
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    	doPost(request, response);
    	
    }
}