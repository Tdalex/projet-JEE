package com.namex.shortener;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Map;

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
	 
	    String query = "SELECT id FROM url_data WHERE long_url='"+ longUrl.trim() + "'";
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
    
    public Boolean shortExist(String customShort) throws Exception {
	    Connection conn = null;
	    ResultSet rs = null;
	    Statement st = null;
	 
	    String query = "SELECT id FROM url_data WHERE custom_short='"+ customShort.trim() + "'";

	    try {
	        try {
	        conn = getConnection();
	        st = conn.createStatement();
	        rs = st.executeQuery(query);
	        if (rs.next()) {
	            return true;
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
	    return false;
    }
 
    public String getShort(String serverName, int port, String contextPath, Map<String, String> parameters, Integer author) throws Exception {

	    Connection conn    = null;
	    Statement st       = null;
	    String customShort = null;
        String shortUrl    = null;
        
    	// at this point id is null, make it shorter
    	int maxView = 0;
    	try {
		   maxView = Integer.parseInt(parameters.get("maxView"));
		} catch(Exception e){
		}
    	String start = "now()";
    	if(parameters.get("dateStart") != null && !parameters.get("dateStart").isEmpty()){
    		start = "'"+parameters.get("dateStart")+"'";
    	}
    	
    	String end = "null";
    	if(parameters.get("dateEnd") != null && !parameters.get("dateEnd").isEmpty()){
    		end = "'"+parameters.get("dateEnd")+"'";
    	}

	    String password = null;
    	if(parameters.get("password") != null && !parameters.get("password").isEmpty()){
    		password = "'"+parameters.get("password")+"'";
    	}
    	
    	if(parameters.get("customShort") != null && !parameters.get("customShort").isEmpty()){
    		if(false == shortExist(parameters.get("customShort"))){
    			customShort = "'"+parameters.get("customShort").trim()+"'";
    			shortUrl = parameters.get("customShort").trim();
    		}
    	}
    	   	
        String sqlInsert = "INSERT INTO url_data(long_url, password, start_date, end_date, max_views, custom_short, author) VALUES('"
        + parameters.get("longUrl").trim() + "',"+password+","+start+","+end+","+maxView+","+customShort+","+author+")";

        System.out.println(sqlInsert);
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
        if(shortUrl == null){
        	shortUrl = getId(parameters.get("longUrl"));
        }
        
	   return "http://" + serverName + ":" + port + contextPath + "/" + shortUrl;
    }
    
    public Boolean addViewUrl(String id) throws Exception {
	    Connection conn = null;
	    Statement st = null;
	    if (id.startsWith("/")) {
	    	id = id.replace("/", "");
	    }
        String sqlUpdate = "UPDATE url_data SET number_views = number_views + 1 WHERE CAST(id as CHAR(50))= '"+ id +"' OR custom_short = '"+ id +"';";
        System.out.println(sqlUpdate);
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
	    String query = "SELECT long_url FROM url_data where id='"+ urlId+"' OR custom_short = '"+ urlId +"'";
        System.out.println(query);
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
	    return "http://"+longUrl;
    }
    
    public String getErrorUrl(String urlId) throws Exception {
	    if (urlId.startsWith("/")) {
	        urlId = urlId.replace("/", "");
	    }
	    String query = "SELECT * FROM url_data where id='"+ urlId+"' OR custom_short = '"+ urlId +"'";
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
	    String query = "SELECT * FROM url_data where id='"+urlId+"' OR custom_short = '"+ urlId +"'";
	    Connection conn = null;
	    ResultSet rs = null;
	    Statement st = null;

        System.out.println(query);
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
	    String query = "SELECT * FROM url_data where id='"+ urlId+"' OR custom_short = '"+ urlId +"'";
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
    
    public String getUserUrl(String serverName,int port, String contextPath, Integer userID) throws Exception {

        System.out.println(userID);
	    String query 	   = "SELECT * FROM url_data where author=" + userID;
	    Connection conn    = null;
	    ResultSet rs       = null;
	    Statement st       = null;
    	String message     = "";
    	String nbViews     = "";
    	String hasPassword = "";
    	String shorten     = "http://" + serverName + ":" + port + contextPath + "/";
    	
	    try {
	        conn = getConnection();
	        st = conn.createStatement();
	        rs = st.executeQuery(query);
		    
	        while (rs.next()) {	
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

	    		if(rs.getString("custom_short") != null) {
	    			shorten += rs.getString("custom_short");
	    		}else {
	    			shorten += rs.getString("id");	    			
	    		}

    			String end = "";
	    		if(rs.getString("end_date") != null) {
	    			end = rs.getString("end_date");
	    		}
	    		
		    	message     += "<tr><td><a href='http://"+ rs.getString("long_url") +"' >"+ rs.getString("long_url") +"</a></td>";   
		    	message     += "<td><a href='"+shorten+"' >"+shorten+"</a></td>";    
		    	message     += "<td>"+ nbViews  +"</td>";    
		    	message     += "<td>"+ rs.getString("start_date") +"</td>";    
		    	message     += "<td>"+ end +"</td>";    
		    	message     += "<td>"+ hasPassword +"</td>";    
		    	message     += "<td><a href='ModifyShorten.jsp?update="+ rs.getString("id") +"' >Update</a></td>";    
		    	message     += "<td><a href='UpdateShorten?delete="+ rs.getString("id") +"' >Delete</a></td></tr>"; 
		    	shorten     = "http://" + serverName + ":" + port + contextPath + "/";
	        }
	    } finally {
	    }
    	return message;
    }
    
    public String getModifyShorten(String urlID, Integer userID) throws Exception {

	    String query = "SELECT * FROM url_data where author=" + userID +" AND id="+ urlID;
	    Connection conn = null;
	    ResultSet rs = null;
	    Statement st = null;
    	String form = "";
    	String shorten = "";
    	
	    try {
	        conn = getConnection();
	        st = conn.createStatement();
	        rs = st.executeQuery(query);
	     
	        
	        if (rs.next()) {	
	        	if(rs.getString("custom_short") != null) {
	       			shorten += rs.getString("custom_short");
	       		}
	        	   
		        String end = "";
	    		if(rs.getString("end_date") != null) {
	    			end = rs.getString("end_date");
	    		}
	    		
	    		form     += "<input type='hidden' name='update' value='"+ urlID +"' ></input>";       		
	    		form     += "Long URL: <input type='text' name='longUrl' size='100' value='"+ rs.getString("long_url") +"' ></input><br>";  
	    		form     += "Custom short URL: <input type='text' name='customShort' maxlength='20' size='100' value='"+ shorten +"' ></input><br>";   
		    	form     += "Password: <input type='password' name='password' size='100' value='"+ rs.getString("password") +"'></input></br>";      	  	
	    		form     += "Max view: <input type='number' name='maxView' size='100' value='"+ rs.getString("max_views") +"' ></input><br>";   
	    		form     += "Date start (format yyyy-mm-dd): <input type='text' maxlength='10' name='dateStart' size='100' value='"+ rs.getString("start_date") +"' ></input><br>";   
	    		form     += "Date end (format yyyy-mm-dd): <input type='text' maxlength='10' name='dateEnd' size='100' value='"+ end +"' ></input><br>";   
	        }
	    } finally {
	    }
    	return form;
    }
    
    public Boolean deleteUrl(String idUrl, Integer author) throws Exception {
		String sqlInsert = "DELETE FROM url_data WHERE id="+idUrl+" AND author="+author+";";
	    Connection conn = null;
	    Statement st = null;

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
        return true;
    }
    
    public Boolean updateUrl( Map<String, String> parameters, Integer author) throws Exception {
	    Connection conn = null;
	    Statement st = null;
	    String id = parameters.get("id");
	    
	    if (id != null) {
	    	int maxView = 0;
	    	try {
    		   maxView = Integer.parseInt(parameters.get("maxView"));
    		} catch(Exception e){
    		}
	    	
	    	String start = "null";
	    	String customShort = null;
	    	
	    	if(parameters.get("customShort") != null && !parameters.get("customShort").isEmpty()){
	    		if(false == shortExist(parameters.get("customShort"))){
	    			customShort = "'"+parameters.get("customShort").trim()+"'";
	    		}
	    	}
	    	
	    	if(parameters.get("dateStart") != null && !parameters.get("dateStart").isEmpty()){
	    		start = "'"+parameters.get("dateStart")+"'";
	    	}
	    	
	    	String end = "null";
	    	if(parameters.get("dateEnd") != null && !parameters.get("dateEnd").isEmpty()){
	    		end = "'"+parameters.get("dateEnd")+"'";
	    	}
	    	
	    	String sqlUpdate = "UPDATE url_data SET long_url = '"+parameters.get("longUrl").trim()+"', password = '"+parameters.get("password")+"', start_date = "+start+", end_date = "+end+", custom_short = "+customShort+", max_views = "+maxView+" WHERE id="+id+" AND author="+author+";";

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
	    }  
	   return true;
    }
}