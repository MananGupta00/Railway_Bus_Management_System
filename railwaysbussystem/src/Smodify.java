

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
		response.setContentType("text/html");
	    response.setCharacterEncoding("UTF-8");
	    
	    String id=request.getParameter("id");
		try { 
			 Connection con = DatabaseConnection.initializeDatabase();
			 
			 
			 if(id.equals("add")){
				 String busno=request.getParameter("busNo");
				 String stop=request.getParameter("stop");
				 String fat=request.getParameter("FAT");
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
				 String busno=request.getParameter("busNo");
				 String routeStop=request.getParameter("route");
				 PreparedStatement st = con.prepareStatement("delete from z"+busno+" where stop='"+routeStop+"';");
				 st.executeUpdate();
				 st.close();
				 response.getWriter().write("deleted");			
			 }
			 
			 else if(id.equals("show")){
				 //retrieve data from database and respond to
				 String busno=request.getParameter("busNo");
				 PreparedStatement st = con.prepareStatement("select stop,Expected_Arrival from z"+busno+" order by Expected_Arrival;");
				 ResultSet rs = st.executeQuery();
				 
				 while(rs.next()){
					 response.getWriter().write("<option value='"+rs.getString(1)
					 +"'>"+rs.getString(1)+"	"+rs.getString(2)+"</option>");
				 }
				 st.close();
			 }
			 else if(id.equals("showBusNo")){
				 //retrieve data from database and respond to

				 PreparedStatement st = con.prepareStatement("select busno from busadmin;");
				 ResultSet rs = st.executeQuery();
				 
				 while(rs.next()){
					 response.getWriter().write("<option value='"+rs.getString(1)
					 +"'>"+rs.getString(1)+"</option>");
				 }
				 st.close();
			 }
			 else if(id.equals("showStop")){
				 //retrieve data from database and respond to

				 PreparedStatement st = con.prepareStatement("select * from stops;");
				 ResultSet rs = st.executeQuery();
				 
				 while(rs.next()){
					 response.getWriter().write("<option value='"+rs.getString(1)
					 +"'>"+rs.getString(1)+"</option>");
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
