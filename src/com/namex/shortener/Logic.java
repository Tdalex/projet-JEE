package com.namex.shortener;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.servlet.http.HttpSession;

import java.util.Date;
 
public class Logic {
    public Connection getConnection() throws IllegalAccessException, ClassNotFoundException, SQLException {
	    //adjust it
	    Class.forName("com.mysql.jdbc.Driver");
	    String url = "jdbc:mysql://localhost:3306/url_shortener";
	    Connection conn = DriverManager.getConnection(url, "root", "");
	    return conn;
    }
 
    public String getId(String longUrl) throws Exception {
	    Connection conn = null;
	    ResultSet rs = null;
	    Statement st = null;
	 
	    String query = "SELECT id FROM url_data WHERE long_url='"
	        + longUrl.trim() + "'";
	    String id = null;
	    try {
	        try {
	        conn = getConnection();
	        st = conn.createStatement();
	        rs = st.executeQuery(query);
	        if (rs.next()) {
	            id = rs.getString("id");
	        }
	        } finally {
	 
	        if (rs != null) {
	            rs.close();
	        }
	        if (st != null) {
	            st.close();
	        }
	        if (conn != null) {
	            conn.close();
	        }
	        }
	    } catch (Exception e) {
	        throw e;
	    }
	    return id;
    }
 
    public String getShort(String serverName, int port, String contextPath, Map<String, String> parameters, Integer author) throws Exception {

	    Connection conn = null;
	    Statement st = null;
	    String id = getId(parameters.get("longUrl"));// check if URL has been shorten already
	    if (id != null) {
	        // if id is not null, this link has been shorten already.
	        // nothing to do
	    } else {
	        // at this point id is null, make it shorter
	    	int maxView = 0;
	    	try {
    		   maxView = Integer.parseInt(parameters.get("maxView"));
    		} catch(Exception e){
    		}
	    	String start = "null";
	    	if(parameters.get("dateStart") != null && !parameters.get("dateStart").isEmpty()){
	    		start = "'"+parameters.get("dateStart")+"'";
	    	}
	    	
	    	String end = "null";
	    	if(parameters.get("dateEnd") != null && !parameters.get("dateEnd").isEmpty()){
	    		end = "'"+parameters.get("dateEnd")+"'";
	    	}
	    	
	        String sqlInsert = "INSERT INTO url_data(long_url, password, start_date, end_date, max_views, author) VALUES('"
	        + parameters.get("longUrl").trim() + "','"+parameters.get("password")+"',"+start+","+end+","+maxView+","+author+")";

	        try {
		        conn = getConnection();
		        st = conn.createStatement();
		        st.execute(sqlInsert);
		        } finally {
		        if (st != null) {
		            st.close();
		        }
		        if (conn != null) {
		            conn.close();
		        }
	        }
	        // after we insert the record, we obtain the ID as identifier of our
	        // new short link
	        id = getId(parameters.get("longUrl"));
	    }  
	   return "http://" + serverName + ":" + port + contextPath + "/" + id;
    }
    
    public Boolean addViewUrl(String id) throws Exception {
	    Connection conn = null;
	    Statement st = null;
	    if (id.startsWith("/")) {
	    	id = id.replace("/", "");
	    }
        String sqlUpdate = "UPDATE url_data SET number_views = number_views + 1 WHERE url_data.id = "+ id +";";

        try {
	        conn = getConnection();
	        st = conn.createStatement();
	        st.execute(sqlUpdate);
	        } finally {
	        if (st != null) {
	            st.close();
	        }
	        if (conn != null) {
	            conn.close();
	        }
        }
	   return true;
    }
 
    public String getLongUrl(String urlId) throws Exception {
	    if (urlId.startsWith("/")) {
	        urlId = urlId.replace("/", "");
	    }
	    String query = "SELECT long_url FROM url_data where id=" + urlId;
	    Connection conn = null;
	    ResultSet rs = null;
	    Statement st = null;
	    String longUrl = null;
	 
	    try {
	        conn = getConnection();
	        st = conn.createStatement();
	        rs = st.executeQuery(query);
	        
	        if (rs.next()) {
	        	longUrl = rs.getString("long_url");		    	
	        }
	    } finally {
	 
	        if (rs != null) {
	        	rs.close();
	        }
	        if (st != null) {
	        	st.close();
	        }
	        if (conn != null) {
	        	conn.close();
	        }
	    } 
	    return longUrl;
    }
    
    public String getErrorUrl(String urlId) throws Exception {
	    if (urlId.startsWith("/")) {
	        urlId = urlId.replace("/", "");
	    }
	    String query = "SELECT * FROM url_data where id=" + urlId;
	    Connection conn = null;
	    ResultSet rs = null;
	    Statement st = null;
    	String dateStart   = null;
    	String isEnabled   = null;
    	String dateEnd     = null;
    	Integer numberViews = -1;
    	Integer maxView     = -1;
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	    try {
	        conn = getConnection();
	        st = conn.createStatement();
	        rs = st.executeQuery(query);
	        
	        if (rs.next()) {	    	
		    	dateStart   = rs.getString("start_date");
		    	isEnabled   = rs.getString("is_enabled");
		    	dateEnd     = rs.getString("end_date");
		    	numberViews = new Integer(rs.getString("number_views"));
		    	maxView     = new Integer(rs.getString("max_views"));
	        }
	    } finally {
	 
	        if (rs != null) {
	        	rs.close();
	        }
	        if (st != null) {
	        	st.close();
	        }
	        if (conn != null) {
	        	conn.close();
	        }

		    if (new Integer(isEnabled).compareTo(1) > 0) {
		        // if url not enabled, send to index.jsp
		        System.out.println("url disabled, back to index");
		        return "url disabled, back to index";
		    }
		    
		    if (dateStart != null) {
		        // if startdate > today send to index.jsp
		    	Date today = new Date();
		        Date start = dateFormat.parse(dateStart);
		    	if ( start.compareTo(today) > 0) {
			        System.out.println("too soon, back to index");
			        return "too soon, back to index";
		    	}
		    }

		    if (!maxView.equals(0) && numberViews.compareTo(maxView) > 0) {
		        // if too much view send to index.jsp
		        System.out.println("no more link, back to index");
		        return "no more link, back to index";
		        
		    }

		    if (dateEnd != null) {
		        // if endDate < today send to index.jsp
		    	Date today = new Date();
		        Date end = dateFormat.parse(dateEnd);
		    	if (today.compareTo(end) > 0) {
			        System.out.println("too late, back to index");
			        return "too late, back to index";
		    	}
		    }
	    }
	 
	    return null;
    }
    
    public boolean hasPasswordUrl(String urlId) throws Exception {
	    if (urlId.startsWith("/")) {
	        urlId = urlId.replace("/", "");
	    }
	    String query = "SELECT * FROM url_data where id=" + urlId;
	    Connection conn = null;
	    ResultSet rs = null;
	    Statement st = null;

	    try {
	        conn = getConnection();
	        st = conn.createStatement();
	        rs = st.executeQuery(query);
	        
	        if (rs.next()) {
	        	if(rs.getString("password") != null && !rs.getString("password").isEmpty()){
	        		return true;		    	
	        	}
	        }
	    } finally {
	 
	        if (rs != null) {
	        	rs.close();
	        }
	        if (st != null) {
	        	st.close();
	        }
	        if (conn != null) {
	        	conn.close();
	        }
	    } 
	    return false;
    }
    
    public boolean checkPassword(String password, String urlId) throws Exception {
	    if (urlId.startsWith("/")) {
	        urlId = urlId.replace("/", "");
	    }
	    String query = "SELECT * FROM url_data where id=" + urlId;
	    Connection conn = null;
	    ResultSet rs = null;
	    Statement st = null;

	    try {
	        conn = getConnection();
	        st = conn.createStatement();
	        rs = st.executeQuery(query);
	        
	        if (rs.next()) {
	        	if(rs.getString("password") != null && !rs.getString("password").isEmpty()){
	        		if(password.compareTo(rs.getString("password")) != 0){
	        			return false;
	        		}
	        	}
	        }
	    } finally {
	 
	        if (rs != null) {
	        	rs.close();
	        }
	        if (st != null) {
	        	st.close();
	        }
	        if (conn != null) {
	        	conn.close();
	        }
	    } 
	    return true;
    }
    
    public Integer connectUser(String name, String password) throws Exception {
    	//select
		String query = "SELECT * FROM user WHERE username = '"+name+"' and password = '"+password+"';";
	    Connection conn = null;
	    ResultSet rs = null;
	    Statement st = null;

	    try {
	        conn = getConnection();
	        st = conn.createStatement();
	        rs = st.executeQuery(query);
	        
	        if (rs.next()) {
	        	return rs.getInt("id_user");
	        }
	    } finally {
	 
	        if (rs != null) {
	        	rs.close();
	        }
	        if (st != null) {
	        	st.close();
	        }
	        if (conn != null) {
	        	conn.close();
	        }
	    } 
	    return 0;
    }
    
    public Integer addUser(String name, String password) throws Exception {
		String sqlInsert = "INSERT INTO user (username, password) VALUES ('"+name+"', '"+password+"');";
	    Connection conn = null;
	    Statement st = null;

        try {
	        conn = getConnection();
	        st = conn.createStatement();
	        st.execute(sqlInsert);
	        
	        return connectUser(name, password);
        } finally {
	        if (st != null) {
	            st.close();
	        }
	        if (conn != null) {
	            conn.close();
	        }
        }
    }
    
    public boolean checkUser(String name) throws Exception {
	  
	    String query = "SELECT * FROM user where username='" + name +"'";
	    Connection conn = null;
	    ResultSet rs = null;
	    Statement st = null;

	    try {
	        conn = getConnection();
	        st = conn.createStatement();
	        rs = st.executeQuery(query);
	        
	        if (rs.next()) {
    			return false;
	        }
	    } finally {
	 
	        if (rs != null) {
	        	rs.close();
	        }
	        if (st != null) {
	        	st.close();
	        }
	        if (conn != null) {
	        	conn.close();
	        }
	    } 
	    return true;
    }
    
    public String getUserUrl(Integer userID) throws Exception {

        System.out.println(userID);
	    String query = "SELECT * FROM url_data where author=" + userID;
	    Connection conn = null;
	    ResultSet rs = null;
	    Statement st = null;
    	String message = "";
    	String nbViews = "";
    	String hasPassword = "";
    	String isEnabled = "";
    	
	    try {
	        conn = getConnection();
	        st = conn.createStatement();
	        rs = st.executeQuery(query);
	        
	        if (rs.next()) {	
	    		if (new Integer(rs.getInt("max_views")).compareTo(0) == 0) {
	    			nbViews = rs.getString("number_views");
	    		}else {
	    			nbViews = rs.getString("number_views") + "/" + rs.getString("max_views");
	    		}
	    		
	    		if (rs.getString("password") == null | rs.getString("password").length() == 0) {
	    			hasPassword = "no";
	    		}else {
	    			hasPassword = "yes";
	    		}

	    		if (new Integer(rs.getInt("is_enabled")).compareTo(0) != 0) {
	    			isEnabled = "yes";
	    		}else {
	    			isEnabled = "no";
	    		}
	    		
		    	message     += "<tr><td>"+ rs.getString("long_url") +"</td>";   
		    	message     += "<td>"+ rs.getString("id") +"</td>";    
		    	message     += "<td>"+ nbViews  +"</td>";    
		    	message     += "<td>"+ rs.getString("start_date") +"</td>";    
		    	message     += "<td>"+ rs.getString("end_date") +"</td>";    
		    	message     += "<td>"+ hasPassword +"</td>";    
		    	message     += "<td>"+ isEnabled +"</td>";   
		    	message     += "<td>Update</td>";    
		    	message     += "<td>Delete</td></tr>";     	  	
	        }
	    } finally {
	    }
    	return message;
    }
}