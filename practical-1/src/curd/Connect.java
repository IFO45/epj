package curd;

import java.sql.*;
import java.util.Scanner;

public class Connect {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/parul", "root", "password");

            
            String createTableQuery = "CREATE TABLE IF NOT EXISTS students (" +
                                      "id INT AUTO_INCREMENT PRIMARY KEY, " +
                                      "name VARCHAR(100) NOT NULL, " +
                                      "age INT NOT NULL)";
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(createTableQuery);
            }
            System.out.print("Connection Successfull\n");
            System.out.print("\n");
            
            System.out.print("Enter student name: ");
            String name = sc.nextLine();
            System.out.print("Enter student age: ");
            int age = sc.nextInt();

            
            String insertQuery = "INSERT INTO students (name, age) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                pstmt.setString(1, name);
                pstmt.setInt(2, age);
                pstmt.executeUpdate();
                System.out.println("✔ Student record inserted successfully!\n");
            }

            
            String selectQuery = "SELECT * FROM students";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(selectQuery)) {

                System.out.println("📋 Students in database:");
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id") +
                                       ", Name: " + rs.getString("name") +
                                       ", Age: " + rs.getInt("age"));
                }
            }

            
            conn.close();

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }

        sc.close();
    }
}
