

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

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
	    PrintWriter out= response.getWriter();
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
             else if(id.equals("newCond")){
            	 String conname= request.getParameter("condname");
            	 String conductorId= request.getParameter("condId");
            	 String conpass= request.getParameter("condpass");
            	 
            	 
            	 String associate="insert into conductors values (?, ?, ?);";
                 PreparedStatement st= con.prepareStatement(associate);
                 st.setString(1,conname);
                 st.setString(2,conductorId);
                 st.setString(3,conpass);
                 
                 st.executeUpdate();
                 st.close();
                 out.write("conductor added");
             }
             else if(id.equals("showCondView")){
            	 
            	 
            	 String query="Select name,id from conductors";
                 Statement st= con.createStatement();
                 
                 ResultSet rs= st.executeQuery(query);
                 while(rs.next()){
                	 out.write("<tr><td>"+rs.getString(1)+"</td><td>"
                			+rs.getString(2)+"</td></tr>");	 
                 }
                 st.close();
             }
             else if(id.equals("delCond")){
            	 String conductorId= conid;
            	 Statement st= con.createStatement();
                 Statement stst=con.createStatement();
                 
                 try{
                 st.executeUpdate("delete from conductors where id='"+conductorId+"';");
                 stst.executeUpdate("update busadmin set conid='' where conid='"+conductorId+"';");
                 response.getWriter().write("conductor deleted");
                 }
                 catch(Exception e){
                	 response.getWriter().write("conductor not found");
                 }st.close();
                 stst.close();
                 
             }
            else if(id.equals("print")){
            	response.getWriter().write("<hr size='2px'> <div id='ass' class='section'> <p class='heading'>ASSOCIATE CONDUCTOR</p><div style='display: flex','flex-direction: row'><div style='margin-left: 30'><table id='condview'></table></div>   <div style='margin-left: 50'> BUS NUMBER:  <select id='busNoid' ></select>	 <br /><br/>BUS CONDUCTOR ID:<input id= 'conid' type='text' /> <br /><br/><button id='submitass'>ADD CONDUCTOR</button></div></div> </div><script> $('#submitass').click( function() {  $.get('Sass',{ 'id': 'assign', 'busNo': document.getElementById('busNoid').value, 			'con': document.getElementById('conid').value  	}).done(function(response) {  alert(response); }); });			 $(document).ready(function() {  $.get('Smodify', 		{ 		'id': 'showBusNo' 			} ).done(function(response) {  	$('#busNoid').html(response); }); 	$.get('Sass', 		{ 		'id': 'showCondView' 		} ).done(function(response) {  	$('#condview').html(response); });}); </script>");
            	response.getWriter().write("<hr size='2px'> <div id='ass' class='section'> <p class='heading'>ADD NEW CONDUCTOR</p> CONDUCTOR NAME :<input id= 'condname' type='text' /> <br /><br/> CONDUCTOR ID :<input id= 'condId' type='text' /> <br /><br/> SET PASSWORD :<input id= 'condpass' type='text' /> <br /><br/><button id='submitcon'>ADD CONDUCTOR</button> </div><script>$('#submitcon').click( function() {  $.get('Sass',{ 'id': 'newCond', 'condname': document.getElementById('condname').value, 			'condId': document.getElementById('condId').value,  'condpass': document.getElementById('condpass').value  	}).done(function(response) {  alert(response); }); });</script>");
            	response.getWriter().write("<hr size='2px'> <div id='ass' class='section'> <p class='heading'>DELETE CONDUCTOR</p> CONDUCTOR ID :<input id= 'conId' type='text' /><br /><br/><button id='delcon'>DELETE</button> </div><script>$('#delcon').click( function() {  $.get('Sass',{ 'id': 'delCond', 'con': document.getElementById('conId').value,	}).done(function(response) {  alert(response); }); });</script>");
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
