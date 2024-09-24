package sms;
import java.sql.*;

public class DatabaseHelper {
    private static final String URL = "jdbc:sqlite:students.db";

    // Create connection to the SQLite database
    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    // Create table if not exists
    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS students ("
                   + "id TEXT PRIMARY KEY, "
                   + "name TEXT NOT NULL, "
                   + "grade TEXT NOT NULL);";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Insert a new student
    public static void insertStudent(Student student) {
        String sql = "INSERT INTO students(id, name, grade) VALUES(?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, student.getId());
            pstmt.setString(2, student.getName());
            pstmt.setString(3, student.getGrade());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Get all students
    public static ResultSet getAllStudents() {
        String sql = "SELECT * FROM students";
        ResultSet rs = null;

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return rs;
    }

    // Update a student
    public static void updateStudent(Student student) {
        String sql = "UPDATE students SET name = ?, grade = ? WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getGrade());
            pstmt.setString(3, student.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Delete a student
    public static void deleteStudent(String id) {
        String sql = "DELETE FROM students WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
