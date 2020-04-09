

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection; 
import java.sql.PreparedStatement; 
import java.sql.Statement;


@WebServlet("/Sadd")
public class Sadd extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
	    response.setCharacterEncoding("UTF-8");
	    
		try { 
			String busno=request.getParameter("busNo");
			String conid=request.getParameter("con");
			String station=request.getParameter("stat");
			String stop=request.getParameter("stop");
			
            Connection con = DatabaseConnection.initializeDatabase(); 
            
            
            if(request.getParameter("id").equals("bus"))
            {
            PreparedStatement st = con.prepareStatement("insert into busadmin values(?, ?, ?)"); 
            st.setString(1, ""+busno); 
            st.setString(2, conid);
            st.setString(3, station);
            
            
            String busstop="create table " +"z"+ busno+ 
            		" (stop varchar(100) primary key,Arrival_time time,Expected_Arrival time);";
            Statement st1= con.createStatement();
            
            st.executeUpdate();
            st1.executeUpdate(busstop);
            
            st.close();
            st1.close();
            con.close(); 

    	    response.getWriter().write("added bus: "+busno);
    	    }
            else if(request.getParameter("id").equals("stop")){
                PreparedStatement st = con.prepareStatement("insert into stops values(?)"); 
                st.setString(1, stop); 
                
                st.executeUpdate();
                st.close();
                con.close();
        	    response.getWriter().write("added stop: "+stop);
            }
        } 
        catch (Exception e) { 
            e.printStackTrace(); 
    	    response.getWriter().write(e.getMessage());
        } 

    }
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	//}

}
