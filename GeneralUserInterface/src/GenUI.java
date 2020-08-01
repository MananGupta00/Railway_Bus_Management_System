

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
			
			String availableBuses[][]= new String[100][3];
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
					
					if(hr<2 && exhr>2)
						hr= hr+24;
					
					int net=(hr-exhr)*60+(min-exmin);

					if((net>0)&&(net<120))
					{
						String queryStopmatch="Select stop,Expected_Arrival from z"+thisBus+" where stop=?;";
						PreparedStatement sts= con.prepareStatement(queryStopmatch);
						sts.setString(1,stop);
						ResultSet rsss=sts.executeQuery();
						
						if(rsss.next())
							{
							availableBuses[n][0]=thisBus;
							availableBuses[n][1]= net+"";
							availableBuses[n++][2]= rsss.getString(2);
							}
					}					
				}
			}
				if(n>0){
					int i=n-1;
					while(i>0){
						for(int j=0;j<i;j++)
						{	int a=Integer.parseInt(availableBuses[j][1]);
							int b=Integer.parseInt(availableBuses[j+1][1]);
							if(a<b)
							{availableBuses[j][1]=b+"";
							availableBuses[j+1][1]=a+"";
							
							String s=availableBuses[j][0];
							availableBuses[j][0]=availableBuses[j+1][0];
							availableBuses[j+1][0]=s;
							
							String s2=availableBuses[j][2];
							availableBuses[j][2]=availableBuses[j+1][2];
							availableBuses[j+1][2]=s2;
							}
						}
						i--;
					}

				    //response.setContentType("application/json");
				   // response.setCharacterEncoding("UTF-8");	
			    PrintWriter pout= response.getWriter();
			   
			    
					pout.append("[");
				while(n>0){
					char a='"';
					
					pout.append("{ "+a+"busName"+a+":"+a+availableBuses[--n][0]+a+","+a+"net"+a+":"+a+availableBuses[n][1]+a+", "+a+"stops"+a+":[");
					String queryInfo="Select stop,Expected_Arrival from z"+availableBuses[n][0]+";";
					Statement getInfo= con.createStatement();
					ResultSet Info= getInfo.executeQuery(queryInfo);
					int f=0;
					while(Info.next()){
						if(f==1)
							pout.append(",");
						f=1;
						
						pout.append("{"+a+"name"+a+":"+a+Info.getString(1)+a);
						pout.append(","+a+"time"+a+": "+a+Info.getString(2)+a+","+a+"status"+a+":");
						
						int nhr=java.time.LocalTime.now().getHour();
						int nmin=java.time.LocalTime.now().getMinute();
						int hr=Integer.parseInt(Info.getString(2).substring(0, 2));
						int min=Integer.parseInt(Info.getString(2).substring(3, 5));
						
						
						if(nhr>20 && hr<6)
							hr=hr+24;
						
						if((nhr>hr)
								||(nhr==hr)
								&& nmin>min)
							pout.append("1");
						else
							pout.append("0");
						pout.append("}");
					}
					pout.append("]}");
					if(n!=0)
						pout.append(",");
				}
				pout.append("]");
				}
				else
					out.append("NO BUS FOUND");
		}
		}catch(Exception e){e.printStackTrace();}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
