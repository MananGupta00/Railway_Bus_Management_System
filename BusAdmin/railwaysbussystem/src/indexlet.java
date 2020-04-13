

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;




@WebServlet("/indexlet")
public class indexlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String reqid=request.getParameter("id");
		try{
		Connection con = DatabaseConnection.initializeDatabase(); 
		
		
		HttpSession session=request.getSession(false);  
        if(session!=null){  
        String name=(String)session.getAttribute("name"); 

        PreparedStatement stt = con.prepareStatement("select name from login;");
		ResultSet rss = stt.executeQuery();
		
		while(rss.next()){
			 if(name.equals(rss.getString(1)))
        {
		
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
		else if(reqid.equals("busSearch")) {
			String busNo= request.getParameter("busNo");
			PreparedStatement st = con.prepareStatement("select * from z"
					+busNo+" order by Arrival_time;");
			ResultSet rs = st.executeQuery();
			
		    out.print("<table align='center'>");
			out.print("<tr><td>STOP:</td><td>Fixed Arrival Time</td>"
					+ "<td>Expected Arrival Timer</td></tr>");

			 while(rs.next()){	 
				out.print("<tr><td>"+rs.getString(1)+"</td><td>"+rs.getString(2)+"</td>"
						+ "<td>"+rs.getString(3)+"</td></tr>");
			 }
			 
			 out.print("</table>");
			 st.close();
		}
		else if(reqid.equals("print")){
			out.print("<hr size='2px'><div id='showbus' class = 'section'> <p class='heading'>BUSES</p> 	 <br/> 	<div id='bustab'></div> </div> <hr size='2px'> <div id='showstops' class = 'section'> <p class='heading'>STOPS</p> 	 <br/> 	<div id='stoptab'></div> </div> <hr size='2px'> <div id='showz' class = 'section'> <p class='heading'>Bus Routes</p> <input id='routeSearch' type='text' placeholder='Enter bus number'/> <button type='button' id='busSearch'>Search Bus</button> <br/> 	<div id='searchtab'></div> </div><script> $(document).ready( function() { $.get('indexlet',{ 	'id': 'bus' }).done(function(response) {  $('#bustab').html(response); }); $.get('indexlet',{ 	'id': 'stop' }).done(function(response) { $('#stoptab').html(response); }); }); $('#busSearch').click( function() {  $.get('indexlet',{ 	'id': 'busSearch', 	'busNo': document.getElementById('routeSearch').value} ).done(function(response) {  	$('#searchtab').html(response);  }); }); </script>");
		}
		
	    con.close();
	    out.close();
		}
			 break;
		}
		}
		else
			out.print("USER NAME: <input id='uname' type='text'></input><br/><br/>PASSWORD: <input id='pass' type='password'></input><br/><br/> <button id='sub'>ENTER</button><br/><br/>UNAUTHENTICATED ACCESS ALERT!<script> $(document).on('click', '#sub',function() {$.get('login', 		{ 		'id': 'print', 		'name':document.getElementById('uname').value, 		'password':document.getElementById('pass').value, 			} ).done(function(response) {	$('#main').html(response); }); }); </script>");
        }
		catch(Exception e){
			
			if((e.getMessage().startsWith("Table")&&(e.getMessage().endsWith("doesn't exist")))==false)
			System.out.print(e);
			else
				out.print("Not an added bus");
		}		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
