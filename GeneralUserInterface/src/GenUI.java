

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.PreparedStatement;


@WebServlet("/GenUI")
public class GenUI extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try{
			PrintWriter out= response.getWriter();
			response.setContentType("text/html");
			Connection con= DatabaseConnection.initializeDatabase();
		String id=request.getParameter("id");
		
		//System.out.println(request.getHeaders("pkn"));
		if(id.equals("showStops")){
			Statement st= con.createStatement();
			ResultSet rs= st.executeQuery("select stop from stops;");
			
			while(rs.next()){
				response.getWriter().write("<option>"+rs.getString(1)+"</option>");
			}
			st.close();
			rs.close();
		}
		else if(id.equals("showStat")){
			Statement st= con.createStatement();
			ResultSet rs= st.executeQuery("select stop from stops where stop LIKE '%station%' or stop LIKE '%Station'	or stop LIKE 'STATION' ;");
			
			while(rs.next()){
				out.write("<option>"+rs.getString(1)+"</option>");
			}
			st.close();
			rs.close();
		}
		else if(id.equals("showBus")){
			//String pnr= request.getParameter("pnr");
			String stop=request.getParameter("stop");
			String time=request.getParameter("time");
			String station=request.getParameter("stat");
			
			String availableBuses[]= new String[100];
			int n=0;
			
			
			//code to find suitable buses
			String querystatmatch="Select BusNo from busadmin where station=?;";
			PreparedStatement st1= con.prepareStatement(querystatmatch);
			st1.setString(1, station);
			
			ResultSet rs= st1.executeQuery();
			
			while(rs.next()){
				String thisBus=rs.getString(1);
				String queryTimematch="Select Expected_Arrival from z"+thisBus+" where stop=?;";
				PreparedStatement ss= con.prepareStatement(queryTimematch);
				ss.setString(1,station);
				ResultSet rss=ss.executeQuery();
				
				while(rss.next()){
					int exhr= Integer.parseInt(rss.getString(1).substring(0, 2));
					int exmin= Integer.parseInt(rss.getString(1).substring(3, 5));
					int hr= Integer.parseInt(time.substring(0, 2));
					int min= Integer.parseInt(time.substring(3, 5));
					if(((hr>exhr)&&(hr<exhr+2))||((hr==exhr)&&(min>exmin)))
					{
						String queryStopmatch="Select stop from z"+thisBus+" where stop=?;";
						PreparedStatement sts= con.prepareStatement(queryStopmatch);
						sts.setString(1,stop);
						ResultSet rsss=sts.executeQuery();
						
						if(rsss.next())
							{availableBuses[n++]=thisBus;
							System.out.println(thisBus);}
					}					
				}
			}
				if(n>0){
					while(n>0)
						out.write("<tr><td>Your Bus= "+availableBuses[--n]+"</td></tr>");
				}
				else
					out.write("NO BUS FOUND");
		}
		}catch(Exception e){e.printStackTrace();}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
