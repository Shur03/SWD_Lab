package IOL;

import java.io.IOException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import db.DatabaseConnection;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Scheduler for token expiry notifications
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("userName");
        String password = request.getParameter("pass");

        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            response.sendRedirect("login.jsp?error=missing");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Customer WHERE userName = ? AND pass = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, password);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        // User found, generate token
                        String token = generateToken();
                        int tokenExpiryMinutes = 15; // Set token expiry time
                        
                        // Store token in session
                        HttpSession session = request.getSession();
                        session.setAttribute("username", username);
                        session.setAttribute("token", token);

                        // Schedule token expiry notification
                        scheduleTokenExpiryNotification(username, token, tokenExpiryMinutes);

                        // Redirect to dashboard
                        response.sendRedirect("dashboard.jsp");
                    } else {
                        // Invalid credentials
                        response.sendRedirect("login.jsp?error=invalid");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=database");
        }
    }

    private String generateToken() {
        SecureRandom random = new SecureRandom();
        byte[] tokenBytes = new byte[24];
        random.nextBytes(tokenBytes);
        return Base64.getUrlEncoder().encodeToString(tokenBytes);
    }

    private void scheduleTokenExpiryNotification(String username, String token, int expiryMinutes) {
        scheduler.schedule(() -> {
            System.out.println("Token expired for user: " + username);
            // Optionally, implement notification logic (e.g., send email or log message)
            notifyUser(username, "Your session token has expired. Please log in again.");
        }, expiryMinutes, TimeUnit.MINUTES);
    }

    private void notifyUser(String username, String message) {
        // Placeholder for notification logic (e.g., email, SMS, or log)
        System.out.println("Notification to " + username + ": " + message);
    }
}
