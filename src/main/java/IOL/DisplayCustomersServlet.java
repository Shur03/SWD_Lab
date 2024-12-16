package IOL;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/displayCustomers")
public class DisplayCustomersServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head><title>Customer List</title></head>");
        out.println("<body>");
        out.println("<h1>Customer Data</h1>");

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT customerID, userName, pass FROM Customer";
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                out.println("<table border='1'>");
                out.println("<tr>");
                out.println("<th>Customer ID</th>");
                out.println("<th>Username</th>");
                out.println("<th>Password</th>");
                out.println("</tr>");

                while (rs.next()) {
                    String customerID = rs.getString("customerID");
                    String userName = rs.getString("userName");
                    String password = rs.getString("pass");
                   
                    out.println("<tr>");
                    out.println("<td>" + customerID + "</td>");
                    out.println("<td>" + userName + "</td>");
                    out.println("<td>" + password + "</td>");
                    
                    out.println("</tr>");
                }

                out.println("</table>");

            }
        } catch (SQLException e) {
            out.println("<p>Error fetching customer data: " + e.getMessage() + "</p>");
            e.printStackTrace();
        }

        out.println("</body>");
        out.println("</html>");
    }
}
