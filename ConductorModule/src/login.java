import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

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
			Connection con= DatabaseConnection.initializeDatabase();
			Statement st = con.createStatement();
			 ResultSet rs = st.executeQuery("select id,pass from conductors;");
			 char c='a';
			 while(rs.next()){
				 if(name.equals(rs.getString(1)))
					 if(password.equals(rs.getString(2))){
				       
				        HttpSession session=request.getSession();
				        session.setAttribute("name",name);
				        request.setAttribute("id", "print");
				        RequestDispatcher rd = request.getRequestDispatcher("c_servlet");
				        rd.forward(request, response);
				        break;
				        }
				        
			 }
			 if(c!='b'){
				 out.print("<div class='colmn' id='main_colmn'> <div id='logn' class='model'> <div class='login_page' id='page'> <img src='image.jpg' alt='Avatar' class='avatar' /><br /> <label class='labels'>User Name :</label> <input type='text' placeholder='Enter User_Name' class='inp' id='u_name' required/> <label class='labels'>Password :</label> <input type='password' placeholder='Enter Password' class='inp' id='pass' required/> <button id='submitlog' >Login</button>  </div> </div> </div>");
				 out.print("<br/><br/><center>Credentials invalid!</center>");
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