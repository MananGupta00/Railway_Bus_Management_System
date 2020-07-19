

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api")	
public class api extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static String fetch() {
		String out="";
		System.out.println("fetching...");
		try {
		URL url = new URL("https://indianrailways.p.rapidapi.com/index.php?pnr=4436240262");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestProperty("x-rapidapi-host", "indianrailways.p.rapidapi.com");
		conn.setRequestProperty("x-rapidapi-key", "aa825c625amsh2ec4231e4ca33d9p16f572jsn413bcf0ef10d");
		conn.setRequestMethod("GET");
		
		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));

		String output;
		
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			out+=output;
		}
		conn.disconnect();

	  } catch (Exception e) {
		e.printStackTrace();
	  }
		return out;
	}


	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("fetched= "+api.fetch());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
