
import java.io.IOException;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




@WebServlet("/conductor")
public class conductor extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
PrintWriter out = response.getWriter();  
        
		String id=request.getParameter("id");    
		String conId=request.getParameter("conId");
 
		          
		try{  
		
		Connection con = DatabaseConnection.initializeDatabase();
		if(id.equals("show")){
			PreparedStatement st = con.prepareStatement("select busno from busadmin where conid='"+conId+"';");
			 ResultSet rs = st.executeQuery();
			 
			 
			 while(rs.next()){
				 String busNo=rs.getString(1);
				 String query2="Select * from z"+busNo+";";
				 out.print("<table align='center'>");
				 out.print("<tr><td>STOP:</td><td>Fixed Time</td>"
							+ "<td>Expected Timer</td></tr>");
				 Statement st2= con.createStatement();
				 ResultSet rs2 = st2.executeQuery(query2);
				 
				 while(rs2.next()){	 
						out.print("<tr><td>"+rs2.getString(1)+"</td><td>"+rs2.getString(2)+"</td>"
								+ "<td>"+rs2.getString(3)+"</td></tr>");
					 }
				 out.print("</table>");
				 st2.close();
			 }
			 
			 st.close();
			}
			else if(id.equals("showStop")){
			PreparedStatement st = con.prepareStatement("select busno from busadmin where conid='"+conId+"';");
			 ResultSet rs = st.executeQuery();
			 
			 
			 while(rs.next()){
				 String busNo=rs.getString(1);
				 String query2="Select stop from z"+busNo+";";
				 
				 Statement st2= con.createStatement();
				 ResultSet rs2 = st2.executeQuery(query2);
				 
				 while(rs2.next()){	 
						out.print("<option>"+rs2.getString(1)+"</option>");
					 }
				 st2.close();
			 }
			 
			 st.close();
			}
			else if(id.equals("arrived")){
				
				String stop=request.getParameter("stop");
				PreparedStatement st = con.prepareStatement("select busno from busadmin where conid='"+conId+"';");
				 ResultSet rs = st.executeQuery();
			// change time	 
				  LocalDateTime now = LocalDateTime.now();  
				 int hrcur=Integer.parseInt(now.toString().substring(11,13));  
				 int mincur=Integer.parseInt(now.toString().substring(14,16));
				 int seccur=Integer.parseInt(now.toString().substring(17,19));
					 
					 if(seccur>30)
						 mincur++;
					 
				 while(rs.next()){
					 String busNo=rs.getString(1);
					 String query2="select * from z"+busNo+" where stop='"+stop+"';";
					 
					 Statement st2= con.createStatement();
					 ResultSet rs2 = st2.executeQuery(query2);
					 
					 while(rs2.next()){	 
						 String exp= rs2.getString(3);
						 int hrexp= Integer.parseInt(exp.substring(0, 2));
						 int minexp=Integer.parseInt(exp.substring(3, 5));
						 int checkhr=hrexp;
						 int checkmin=minexp;
						 int hrfactor = hrexp- hrcur;
						 int minfactor= minexp- mincur;
						      
						 String query3="select * from z"+busNo+";";
						 
						 Statement st3= con.createStatement();
						 ResultSet rs3 = st3.executeQuery(query3);
						 
						 while(rs3.next())
						 {exp= rs3.getString(3);
						  hrexp= Integer.parseInt(exp.substring(0, 2));
						 minexp=Integer.parseInt(exp.substring(3, 5));
							 if((hrexp>checkhr)||((hrexp==checkhr)&&(minexp>=checkmin)))
								 break;
						 }
						 //loop
						 do{
							 String stopname=rs3.getString(1);							 
							  exp= rs3.getString(3);
							  hrexp= Integer.parseInt(exp.substring(0, 2));
							 minexp=Integer.parseInt(exp.substring(3, 5));
							 						 		 
						 minexp=minexp-minfactor;
						 if(minexp<0)
							 minexp=60+minexp;
						 else if(minexp>60)
							 {minexp=minexp-60;
							 hrexp++;}
						
						 hrexp=(hrexp-hrfactor)%24;	
						 if(hrexp<0)
							 hrexp=hrexp+24;
						 
						 
						 Statement change= con.createStatement();
						 change.executeUpdate("update z"+busNo+" set Expected_Arrival='"+hrexp+":"+minexp+":00' where stop='"+stopname+"'");
						 }while(rs3.next());
						 //loop ends
						 
						 st3.close();
						 }
					 st2.close();
				
				 }				 
				 st.close();				 
				}
			else if(id.equals("reset")){
				Statement st= con.createStatement();
				ResultSet rs=st.executeQuery("select busNo from busAdmin where conid='"+conId+"'");
				if(rs.next())
				st.executeUpdate("update z"+rs.getString(1)+" set Expected_arrival= Arrival_time;");
			st.close();
			rs.close();
			}
		      con.close();    
		}catch (Exception e2) {
			e2.printStackTrace();
			}  
		         
		out.close(); 
	}

}