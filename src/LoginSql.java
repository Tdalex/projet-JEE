

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginSql
 */

@WebServlet("/loginSql")
public class LoginSql extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginSql() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		
		//response.setContentType("text/html");
		PrintWriter out     = response.getWriter();
		String name         = request.getParameter("name");
		String password     = request.getParameter("password");
		
		try {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/jee","root","");
			
			// select
			String strQuery = "SELECT * FROM users WHERE Login = ? and Password = ?;";
			PreparedStatement pst = conn.prepareStatement(strQuery );
			pst.setString(1, name); 
			pst.setString(2, password); 
			ResultSet rsUsers = pst.executeQuery();
			if(rsUsers.next()) {
				session.setAttribute( "name", name );
				request.getRequestDispatcher("/hello.jsp").forward(request, response);
				return;
			}else{
				request.setAttribute( "error", true );
				request.getRequestDispatcher("/form.jsp").forward(request, response);
			}
			rsUsers.close(); 
			conn.close(); 
		}catch(SQLException e) {
			e.printStackTrace();
		} 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
