

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@WebServlet("/Sass")
public class Sass extends HttpServlet {
	
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
	    response.setCharacterEncoding("UTF-8");
		try { 
			String busno="z" + request.getParameter("busNo");
			String conid=request.getParameter("con");
             Connection con = DatabaseConnection.initializeDatabase(); 
             
             String associate="update busadmin set conid=? where busno= ?;";
            PreparedStatement st= con.prepareStatement(associate);
            st.setString(1,conid);
            st.setString(2,busno);
            
            st.executeUpdate();
            
            
            st.close();
            con.close(); 
            
            response.getWriter().write("conductor changed");
        }
        catch (Exception e) { 
            e.printStackTrace(); 
    	    response.getWriter().write(e.getMessage());
        }
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
