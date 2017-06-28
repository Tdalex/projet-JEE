package com.namex.shortener;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
 
    public String getShort(String serverName, int port, String contextPath, Map<String, String> parameters) throws Exception {

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
	    	
	        String sqlInsert = "INSERT INTO url_data(long_url, password, start_date, end_date, max_views) VALUES('"
	        + parameters.get("longUrl").trim() + "','"+parameters.get("password")+"',"+start+","+end+","+maxView+")";

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
	        id = getId(parameters.get("longUrl"));
	    }  
	   return "http://" + serverName + ":" + port + contextPath + "/" + id;
    }
 
    public String getLongUrl(String urlId) throws Exception {
	    if (urlId.startsWith("/")) {
	        urlId = urlId.replace("/", "");
	    }
	    String query = "SELECT * FROM url_data where id=" + urlId;
	    String longUrl = null;
	    Connection conn = null;
	    ResultSet rs = null;
	    Statement st = null;
	 
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
 
}