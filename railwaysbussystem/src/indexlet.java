

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;


@WebServlet("/indexlet")
public class indexlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String reqid=request.getParameter("id");
		try{
		Connection con = DatabaseConnection.initializeDatabase(); 
		PrintWriter out = response.getWriter(); 
		
		if(reqid.equals("bus"))
		{
			PreparedStatement st = con.prepareStatement("select * from busadmin;");
			ResultSet rs = st.executeQuery();
			
		    out.print("<table align='center'>");
			out.print("<tr><td>Bus Number	</td><td>Bus Conductor	</td><td>Railway Station</td></tr>");

			 while(rs.next()){	 
				out.print("<tr><td>"+rs.getString(1)+"</td><td>"+rs.getString(2)+
						"</td><td>"+rs.getString(3)+"</td></tr>");
			 }
			 
			 out.print("</table>");
			 st.close();
			}
		else if(reqid.equals("stop"))
		{
			PreparedStatement st = con.prepareStatement("select * from stops;");
			ResultSet rs = st.executeQuery();
			
		    out.print("<table align='center'>");
			out.print("<tr><td>STOP:</td></tr>");

			 while(rs.next()){	 
				out.print("<tr><td>"+rs.getString(1)+"</td></tr>");
			 }
			 
			 out.print("</table>");
			 st.close();
			}
		
	    con.close();
	    out.close();
		}
		catch(Exception e){
			e.printStackTrace(); 
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
