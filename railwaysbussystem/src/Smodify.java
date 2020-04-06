

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




@WebServlet("/Smodify")
public class Smodify extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
	    response.setCharacterEncoding("UTF-8");
	    
	    String id=request.getParameter("id");
		try { 
			 Connection con = DatabaseConnection.initializeDatabase();
			 String busno=request.getParameter("busNo");
			 String routeStop=request.getParameter("route");
			 String stop=request.getParameter("stop");
			 String fat=request.getParameter("FAT");
			 
			 if(id.equals("add")){
				 PreparedStatement st = con.prepareStatement("insert into z"+busno+" values(?,?,?);");
				 st.setString(1,stop);
				 st.setString(2,fat);
				 st.setString(3,fat);
			 
				 st.executeUpdate();
				 st.close();
				 response.getWriter().write("added");
			 }
			 
			 else if(id.equals("del")){
				 //delete stop in database
				 PreparedStatement st = con.prepareStatement("delete from z"+busno+" where stop='"+routeStop+"';");
				 st.executeUpdate();
				 st.close();
				 response.getWriter().write("deleted");			
			 }
			 
			 else if(id.equals("show")){
				 //retrieve data from database and respond to
				 PreparedStatement st = con.prepareStatement("select * from "+busno+";");
				 ResultSet rs = st.executeQuery();
				 
				 while(rs.next()){
					 response.getWriter().write(rs.getRow());
				 }
				 st.close();
			 }
			 
			 con.close();
        } 
        catch (Exception e) { 
            e.printStackTrace(); 
    	    response.getWriter().write(e.getMessage());
        } 
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
