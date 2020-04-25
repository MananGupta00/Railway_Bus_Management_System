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

@WebServlet("/c_servlet")
public class c_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");  
        PrintWriter out=response.getWriter();  
            
        
        try {
		
		 
		 HttpSession session=request.getSession();
		 String sId= (String)session.getAttribute("name");
		 if(sId!=null){
			 
			 Connection con = DatabaseConnection.initializeDatabase();
				
				PreparedStatement st = con.prepareStatement("select id,pass,name from conductors;");
				 ResultSet rs = st.executeQuery();
				 char c='a';
			 
			 
		 while(rs.next()){
			 if(sId.equals(rs.getString(1))){
				 	if(request.getParameter("id").equals("print")){
				 		
			        out.print("<div id='all'> <div class='cbox'> <h2 id='head-title'>Conductor-Details</h2> <h3>User Name :</h3><br>"+rs.getString(3)+" </div>");
			        out.print("<div class='cbox'><font size=10 id='head'>WELCOME</font><br > <label for='c_name' class='labels' style='text-align: justify;'>City name :</label> <select id='cities'></select></div>");
			        out.print("<div class='cbox'><button type='button' id='reset'>RESET TIME</button> <div id='show'></div></div> </div><br/><br/><button type='button' id='butt'>Submit</button>");
			        out.print("<script> $(document).ready(function(){ $.get('conductor',{'id':'show', 'conId':'"+rs.getString(1)+"'}).done(function(response){$('#show').html(response);});		$.get('conductor',{'id':'showStop', 'conId':'"+rs.getString(1)+"'}).done(function(response){$('#cities').html(response);});}); 		$('#butt').click(function(){ $.get('conductor',{'id':'arrived', 'conId':'"+rs.getString(1)+"', 'stop': document.getElementById('cities').value }).done(function(response){		$.get('conductor',{'id':'show', 'conId':'"+rs.getString(1)+"'}).done(function(response){$('#show').html(response);}); });});	$('#reset').click(function(){ $.get('conductor',{'id':'reset', 'conId':'"+rs.getString(1)+"' }).done(function(response){ 	$.get('conductor',{'id':'show', 'conId':'"+rs.getString(1)+"'}).done(function(response){$('#show').html(response);}); });});</script>");
				 	}
				 	
			        c='b';
			        break;
			        }
		 }
		 
		 if(c!='b'){  
			 out.print("<div class='colmn' id='main_colmn'> <div id='logn' class='model'> <div class='login_page' id='page'> <img src='image.jpg' alt='Avatar' class='avatar' /><br /> <label class='labels'>User Name :</label> <input type='text' placeholder='Enter User_Name' class='inp' id='u_name' required/> <label class='labels'>Password :</label> <input type='password' placeholder='Enter Password' class='inp' id='pass' required/> <button id='submitlog' >Login</button> </div> </div> </div>");
			 out.print("<center>Unauthenticated Access Alert!</center>");
		 }
		 st.close();
        
          
        out.close();
		 }
		 else
			 out.print("<div class='colmn' id='main_colmn'> <div id='logn' class='model'> <div class='login_page' id='page'> <img src='image.jpg' alt='Avatar' class='avatar' /><br /> <label class='labels'>User Name :</label> <input type='text' placeholder='Enter User_Name' class='inp' id='u_name' required/> <label class='labels'>Password :</label> <input type='password' placeholder='Enter Password' class='inp' id='pass' required/> <button id='submitlog' >Login</button>  </div> </div> </div>");
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
        }
        
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}