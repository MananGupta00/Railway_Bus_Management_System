

import java.io.IOException;
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

//import DatabaseConnection;


@WebServlet("/Sdelete")
public class Sdelete extends HttpServlet {
  
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
	    response.setCharacterEncoding("UTF-8");
	    
	    try { 
	    	String id=request.getParameter("id");
	    	
			Connection con = DatabaseConnection.initializeDatabase(); 
			
			HttpSession session=request.getSession(false);
			
            if(session!=null){  
            String name=(String)session.getAttribute("name"); 

            PreparedStatement stt = con.prepareStatement("select name from login;");
    		ResultSet rss = stt.executeQuery();
    		
    		while(rss.next()){
    			 if(name.equals(rss.getString(1)))
            {
			if(id.equals("deleteBus")){
			String busno=request.getParameter("busNo");
            String drop="drop table z"+busno+";";
            String delete="delete from busadmin where busno = '"+busno+"' ;";
            Statement st= con.createStatement();
            
            
            try{st.executeUpdate(drop);
            st.executeUpdate(delete);
            st.close();
            
            }
            catch(Exception e){
            	response.getWriter().print("BUS NOT FOUND");	
            }
            response.getWriter().write("deleted bus"); 
			}
			else if(id.equals("deleteStop")){
				String stop=request.getParameter("stopNo");
	            String delete="delete from stops where stop = '"+stop+"' ;";
	            Statement st= con.createStatement();

	            try{
	            st.executeUpdate(delete);
	            st.close();
	            response.getWriter().write("deleted stop");
	            }
	            catch(Exception e){
	            	response.getWriter().print("STOP NOT FOUND");
	            }
				}
			else if(id.equals("print")){
           	 response.getWriter().write("<hr size='2px'> <div id='del' class = 'section'> <p class='heading'>DELETE BUS</p> BUS NUMBER: <input id='busNoid' type='text'/> <br /><br/> <button id='submitdel'>DELETE BUS</button> </div> <hr size='2px'><script> $('#submitdel').click( function() {$.get('Sdelete',{ 'id':'deleteBus', 'busNo': document.getElementById('busNoid').value 			}).done(function(response) {alert(response); }); }); </script>");
           	 response.getWriter().write("<hr size='2px'> <div id='del' class = 'section'> <p class='heading'>DELETE STOP</p> STOP NAME: <input id='stopid' type='text'/> <br /><br/> <button id='stopdel'>DELETE STOP</button> </div> <hr size='2px'><script> $('#stopdel').click( function() {$.get('Sdelete',{ 'id':'deleteStop', 'stopNo': document.getElementById('stopid').value }).done(function(response) {alert(response); }); }); </script>");
			}
			break;
            }
    			 
    		}    		
            }
            else
    		{
            	response.getWriter().print("USER NAME: <input id='uname' type='text'></input><br/><br/>PASSWORD:  <input id='pass' type='password'></input><br/><br/> <button id='sub'>ENTER</button><br/><br/>UNAUTHENTICATED ACCESS ALERT!<script> $(document).on('click', '#sub',function() {$.get('login', 		{ 		'id': 'print', 		'name':document.getElementById('uname').value, 		'password':document.getElementById('pass').value, 			} ).done(function(response) {	$('#main').html(response); }); }); </script>");
    		}
            con.close(); 
            
        } 
        catch (Exception e) { 
            e.printStackTrace(); 
    	    response.getWriter().write(e.getMessage());
        } 
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
