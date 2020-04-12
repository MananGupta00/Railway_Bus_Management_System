

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;

 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		 response.setContentType("text/html");  
	        PrintWriter out=response.getWriter();  
	       // request.getRequestDispatcher("link.html").include(request, response);  
	          
	        String name=request.getParameter("name");  
	        String password=request.getParameter("password");  
	          
	        
	        try {
					  
	        	Connection con = DatabaseConnection.initializeDatabase(); 
	            
	        
			PreparedStatement st = con.prepareStatement("select name,pass from login;");
			 ResultSet rs = st.executeQuery();
			 char c='a';
			 while(rs.next()){
				 if(name.equals(rs.getString(1)))
					 if(password.equals(rs.getString(2))){
				       
				        HttpSession session=request.getSession();
				        session.setAttribute("name",name);
				        request.setAttribute("id", "print");
				        RequestDispatcher rd = request.getRequestDispatcher("indexlet");
				        rd.forward(request, response);
				        c='b';
				        break;
				        }
				        
			 }
			 if(c!='b'){
				 out.print("USER NAME: <input id='uname' type='text'></input><br/><br/>PASSWORD:  <input id='pass' type='password'></input><br/><br/> <button id='sub'>ENTER</button><br/>INVALID DATA<script> $(document).on('click', '#sub',function() {$.get('login', 		{ 		'id': 'print', 		'name':document.getElementById('uname').value, 		'password':document.getElementById('pass').value, 			} ).done(function(response) {	$('#main').html(response); }); }); </script>");  
			 }
			 st.close();
	        
	          
	        out.close();
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
