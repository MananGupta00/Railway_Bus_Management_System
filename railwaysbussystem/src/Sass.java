

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



@WebServlet("/Sass")
public class Sass extends HttpServlet {
	
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
	    response.setCharacterEncoding("UTF-8");
		try { 
			String busno= request.getParameter("busNo");
			String conid=request.getParameter("con");
			String id= request.getParameter("id");
             Connection con = DatabaseConnection.initializeDatabase(); 
             
             
             HttpSession session=request.getSession(false);  
             if(session!=null){  
             String name=(String)session.getAttribute("name"); 

             PreparedStatement stt = con.prepareStatement("select name from login;");
     		ResultSet rss = stt.executeQuery();
     		
     		while(rss.next()){
     			 if(name.equals(rss.getString(1)))
             {
             if(id.equals("assign"))
             {
             String associate="update busadmin set conid=? where busno= ?;";
            PreparedStatement st= con.prepareStatement(associate);
            st.setString(1,conid);
            st.setString(2,busno);
            
            st.executeUpdate();
            st.close();
            response.getWriter().write("conductor changed");
             }
             else if(id.equals("print")){
            	 response.getWriter().write("<hr size='2px'> <div id='ass' class='section'> <p class='heading'>ASSOCIATE CONDUCTOR</p> BUS NUMBER: <select id='busNoid' ></select> <br /><br/>BUS CONDUCTOR:<input id= 'conid' type='text' /> <br /><br/><button id='submitass'>ADD CONDUCTOR</button> </div><script> $('#submitass').click( function() {  $.get('Sass',{ 'id': 'assign', 'busNo': document.getElementById('busNoid').value, 			'con': document.getElementById('conid').value  	}).done(function(response) {  alert(response); }); }); $(document).ready(function() {  $.get('Smodify', 		{ 		'id': 'showBusNo' 			} ).done(function(response) {  	$('#busNoid').html(response); }); }); </script>");
             }
            break;
             }}
     		}
             else{
            	 response.getWriter().write("USER NAME: <input id='uname' type='text'></input><br/><br/>PASSWORD:  <input id='pass' type='password'></input><br/><br/> <button id='sub'>ENTER</button><br/><br/>UNAUTHENTICATED ACCESS ALERT!<script> $(document).on('click', '#sub',function() {$.get('login', 		{ 		'id': 'print', 		'name':document.getElementById('uname').value, 		'password':document.getElementById('pass').value, 			} ).done(function(response) {	$('#main').html(response); }); }); </script>");
             }
             con.close();
        }
        catch (Exception e) { 
            e.printStackTrace(); 
    	    response.getWriter().write(e.getMessage());
        }
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
