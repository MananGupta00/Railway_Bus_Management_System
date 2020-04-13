

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


@WebServlet("/Sadd")
public class Sadd extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    response.setContentType("text/html");  
	        PrintWriter out = response.getWriter(); 
	    
		try { 
			
			Connection con = DatabaseConnection.initializeDatabase(); 
            HttpSession session=request.getSession(false);  
            if(session!=null){  
            String name=(String)session.getAttribute("name"); 

            PreparedStatement stt = con.prepareStatement("select name from login;");
			ResultSet rss = stt.executeQuery();
			
			while(rss.next()){
				 if(name.equals(rss.getString(1)))
            {
         			 
			String id=request.getParameter("id");
			String busno=request.getParameter("busNo");
			String conid=request.getParameter("con");
			String station=request.getParameter("stat");
			String stop=request.getParameter("stop");
			
             
            
            if(id.equals("bus"))
            {
            PreparedStatement st = con.prepareStatement("insert into busadmin values(?, ?, ?)"); 
            st.setString(1, ""+busno); 
            st.setString(2, conid);
            st.setString(3, station);
            
            
            String busstop="create table " +"z"+ busno+ 
            		" (stop varchar(100) not null,Arrival_time time primary key,Expected_Arrival time);";
            Statement st1= con.createStatement();
            
            try {st.executeUpdate();
            st1.executeUpdate(busstop);}
            catch(Exception e){
            	out.print("BUS EXISTS");
            }
            
            st.close();
            st1.close();
            con.close(); 

    	    response.getWriter().write("added bus: "+busno);
    	    
    	    }
            else if(id.equals("stop")){
                PreparedStatement st = con.prepareStatement("insert into stops values(?)"); 
                st.setString(1, stop); 
                
                st.executeUpdate();
                st.close();
                con.close();
        	    response.getWriter().write("added stop: "+stop);
        	    
            }
            
            
			//SERVLET PRINTING HTML
            else if(id.equals("print")){
    	    out.print("<div id='addStop' class='section'> <p class='heading'>Add New Stop</p> Stop Name:<input id='stopid' type='text'/> <br /><br/> <button id='stopbut' type='submit'>ADD Stop</button> </div> <hr size='2px'> <div id='add' class='section'> <p class='heading'>ADD BUSES</p> <div id='info'> <div>BUS NUMBER: <input id='busNoid' name='busNo' type='text'/> </div><br /> <div>BUS CONDUCTOR:<input id='conid' name='con' type='text' /> </div><br /> <div>STATION:		<select id='statid' ></select></div><br /> <button id='submitbus'type='submit'>ADD BUS</button> </div> </div> <hr size='2px'>  <script> $('#stopbut').click( function() {  $.get('Sadd',{ 	'id': 'stop', 	'stop': document.getElementById('stopid').value} ).done(function(response) {  alert(response); }); }); $('#submitbus').click( function() {  $.get('Sadd', 		{ 	'id': 'bus', 	'busNo': document.getElementById('busNoid').value, 	'con': document.getElementById('conid').value, 'stat': document.getElementById('statid').value} ).done(function(response) {  alert(response); }); }); $(document).ready(function() {  $.get('Smodify', 		{ 		'id': 'showStop' 			}).done(function(response) {  	$('#statid').html(response); }); }); </script>");
            System.out.print("printing");
            }
            break;
            }
			 
            } 
            }
            else
            	out.print("USER NAME: <input id='uname' type='text'></input><br/><br/>PASSWORD:  <input id='pass' type='password'></input><br/><br/> <button id='sub'>ENTER</button><br/><br/>UNAUTHENTICATED ACCESS ALERT!<script> $(document).on('click', '#sub',function() {$.get('login', 		{ 		'id': 'print', 		'name':document.getElementById('uname').value, 		'password':document.getElementById('pass').value, 			} ).done(function(response) {	$('#main').html(response); }); }); </script>");
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
