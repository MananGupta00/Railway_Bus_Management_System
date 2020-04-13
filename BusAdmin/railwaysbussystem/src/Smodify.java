

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;




@WebServlet("/Smodify")
public class Smodify extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
	    response.setCharacterEncoding("UTF-8");
	    
	    String id=request.getParameter("id");
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
			 else if(id.equals("print")){
				 response.getWriter().write("<hr size='2px'>  <div id='mod' class='section'> <p class='heading'>MODIFY STOPS</p> BUS NUMBER:  <select id='busNoid'> 		<option>Select Bus Number</option> </select> <br/> <br/> <br/> <div id='modroute'> <div id='route'> <select id='routeid'> </select><br/> <button id='delbut'>DELETE STOP</button> </div> <div id='routeinfo'> BUS-STOP: <select id='stopid' > </select><br/><br/> ARRIVAL TIME: <input id='FATid' type='time'/><br/><br/> <button id='addbut'>ADD Stop</button> </div> </div> </div><script> function show() { 	$.get('Smodify', 		{ 		'id': 'show', 		'busNo': $('#busNoid').children('option:selected').val(), 			 			} ).done(function(response) {	$('#routeid').html(response); }); } $('#addbut').click( function() {$.get('Smodify',{ 			'id': 'add', 			'busNo': $('#busNoid').children('option:selected').val(), 			'route': $('#routeid').children('option:selected').val(), 			'stop':$('#stopid').children('option:selected'). val(), 			'FAT': document.getElementById('FATid').value} ).done(function(response) {alert(response); });  show(); });$('#delbut').click( function() {$.get('Smodify', 		{ 		'id': 'del', 		'busNo': $('#busNoid').children('option:selected').val(), 			'route': $('#routeid').children('option:selected').val(), 			 			} ).done(function(response) {	show(); alert(response); }); });$('#busNoid').click(function(){show();});$(document).ready(function() {$.get('Smodify', 		{ 		'id': 'showBusNo' 			} ).done(function(response) {	$('#busNoid').html(response); }); $.get('Smodify', 		{ 		'id': 'showStop' 			} ).done(function(response) {	$('#stopid').html(response); }); }); </script>");	
			 }
		        }
				break;
				}
				}
		        else
		        	 response.getWriter().write("USER NAME: <input id='uname' type='text'></input><br/><br/>PASSWORD:  <input id='pass' type='password'></input><br/><br/> <button id='sub'>ENTER</button><br/><br/>UNAUTHENTICATED ACCESS ALERT!<script> $(document).on('click', '#sub',function() {$.get('login', 		{ 		'id': 'print', 		'name':document.getElementById('uname').value, 		'password':document.getElementById('pass').value, 			} ).done(function(response) {	$('#main').html(response); }); }); </script>");
			 
			 con.close();
        } 
        catch (Exception e) { 
            //e.printStackTrace(); 
    	    response.getWriter().write(e.getMessage());
    	    System.out.print(e);
        } 
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
