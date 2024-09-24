package sms;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentManagementSystem {

    public static void main(String[] args) {
        // Create Table if not exists
        DatabaseHelper.createTable();

        // Main Window (JFrame)
        JFrame frame = new JFrame("Student Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(5, 1));

        JLabel titleLabel = new JLabel("Student Management System", SwingConstants.CENTER);
        frame.add(titleLabel);

        JButton addButton = new JButton("Add Student");
        JButton viewButton = new JButton("View Students");
        JButton updateButton = new JButton("Update Student");
        JButton deleteButton = new JButton("Delete Student");

        frame.add(addButton);
        frame.add(viewButton);
        frame.add(updateButton);
        frame.add(deleteButton);

        addButton.addActionListener(e -> addStudent());
        viewButton.addActionListener(e -> viewStudents());
        updateButton.addActionListener(e -> updateStudent());
        deleteButton.addActionListener(e -> deleteStudent());

        frame.setVisible(true);
    }

    private static void addStudent() {
        JFrame addFrame = new JFrame("Add Student");
        addFrame.setSize(300, 200);
        addFrame.setLayout(new GridLayout(4, 2));

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField();
        JLabel gradeLabel = new JLabel("Grade:");
        JTextField gradeField = new JTextField();
        JButton saveButton = new JButton("Save");

        addFrame.add(nameLabel);
        addFrame.add(nameField);
        addFrame.add(idLabel);
        addFrame.add(idField);
        addFrame.add(gradeLabel);
        addFrame.add(gradeField);
        addFrame.add(new JLabel());
        addFrame.add(saveButton);

        saveButton.addActionListener(e -> {
            String name = nameField.getText();
            String id = idField.getText();
            String grade = gradeField.getText();

            Student student = new Student(name, id, grade);
            DatabaseHelper.insertStudent(student);

            JOptionPane.showMessageDialog(addFrame, "Student added successfully!");
            addFrame.dispose();
        });

        addFrame.setVisible(true);
    }

    private static void viewStudents() {
        JFrame viewFrame = new JFrame("View Students");
        viewFrame.setSize(400, 300);
        viewFrame.setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        ResultSet rs = DatabaseHelper.getAllStudents();

        try {
            if (!rs.isBeforeFirst()) {
                textArea.setText("No students available.");
            } else {
                while (rs.next()) {
                    textArea.append("ID: " + rs.getString("id") +
                            ", Name: " + rs.getString("name") +
                            ", Grade: " + rs.getString("grade") + "\n");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(textArea);
        viewFrame.add(scrollPane);
        viewFrame.setVisible(true);
    }

    private static void updateStudent() {
        String id = JOptionPane.showInputDialog("Enter student ID to update:");
        String newName = JOptionPane.showInputDialog("Enter new name:");
        String newGrade = JOptionPane.showInputDialog("Enter new grade:");

        Student updatedStudent = new Student(newName, id, newGrade);
        DatabaseHelper.updateStudent(updatedStudent);

        JOptionPane.showMessageDialog(null, "Student updated successfully!");
    }

    private static void deleteStudent() {
        String id = JOptionPane.showInputDialog("Enter student ID to delete:");
        DatabaseHelper.deleteStudent(id);
        JOptionPane.showMessageDialog(null, "Student deleted successfully!");
    }
}
