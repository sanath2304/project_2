package gui;

import model.DatabaseConnector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class HealthRecordsFrame extends JFrame {
	private static final long serialVersionUID = 1L;

    private JButton viewRecordsButton;
    private JButton addRecordButton;
    private JButton updateRecordButton;
    private JButton deleteRecordButton;

    public HealthRecordsFrame() {
    	
        setTitle("Health Records");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        viewRecordsButton = new JButton("View Records");
        addRecordButton = new JButton("Add Record");
        updateRecordButton = new JButton("Update Record");
        deleteRecordButton = new JButton("Delete Record");

        viewRecordsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewRecords();
            }
        });

        addRecordButton.addActionListener(new ActionListener() {
           
            public void actionPerformed(ActionEvent e) {
                addRecord();
            }
        });

        updateRecordButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                updateRecord();
            }
        });

        deleteRecordButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                deleteRecord();
            }
        });

        panel.add(viewRecordsButton);
        panel.add(addRecordButton);
        panel.add(updateRecordButton);
        panel.add(deleteRecordButton);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void viewRecords() {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String query = "SELECT * FROM health_records ORDER BY id, pet_id";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                List<String> records = new ArrayList<>();
                while (resultSet.next()) {
                    records.add("ID: " + resultSet.getInt("id") +
                            ", Cause: " + resultSet.getString("cause") +
                            ", Date: " + resultSet.getString("date") +
                            ", Prescription: " + resultSet.getString("prescription") +
                            ", Weight: " + resultSet.getDouble("weight") +
                            ", Temperature: " + resultSet.getDouble("temperature") +
                            ", Sleep: " + resultSet.getInt("sleep") +
                            ", Pet ID: " + resultSet.getInt("pet_id"));
                }
                String result = String.join("\n", records);
                JOptionPane.showMessageDialog(this, result, "Health Records", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching Health Records", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
 // Utility method to convert date format
    private String convertDateFormat(String inputDate) {
        try {
            // Parse the input date
            java.util.Date date = new SimpleDateFormat("MM/dd/yyyy").parse(inputDate);
            // Format it to 'yyyy-MM-dd'
            return new SimpleDateFormat("yyyy-MM-dd").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void addRecord() {
        String cause = JOptionPane.showInputDialog(this, "Enter Cause:");
        String date = JOptionPane.showInputDialog(this, "Enter Date: (MM/dd/yyyy) ");
     // Convert the input date to the MySQL format 'yyyy-MM-dd'
        String formattedDate = convertDateFormat(date);
        String prescription = JOptionPane.showInputDialog(this, "Enter Prescription:");
        double weight = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter Weight:"));
        double temperature = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter Temperature:"));
        int sleep = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Sleep:"));
        int petId = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Pet ID:"));

        try (Connection connection = DatabaseConnector.getConnection()) {
            String query = "INSERT INTO health_records (cause, date, prescription, weight, temperature, sleep, pet_id) " +
                    "VALUES (?, STR_TO_DATE(?, '%Y-%m-%d'), ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, cause);
                preparedStatement.setString(2, formattedDate);
                preparedStatement.setString(3, prescription);
                preparedStatement.setDouble(4, weight);
                preparedStatement.setDouble(5, temperature);
                preparedStatement.setInt(6, sleep);
                preparedStatement.setInt(7, petId);

                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(this, "Health Record added successfully");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding Health Record", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateRecord() {
        int recordId = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Health Record ID to update:"));

        try (Connection connection = DatabaseConnector.getConnection()) {
            String query = "SELECT * FROM health_records WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, recordId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String cause = JOptionPane.showInputDialog(this, "Enter Cause:", resultSet.getString("cause"));
                        String date = JOptionPane.showInputDialog(this, "Enter Date:", resultSet.getString("date"));
                        String prescription = JOptionPane.showInputDialog(this, "Enter Prescription:",
                                resultSet.getString("prescription"));
                        double weight = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter Weight:",
                                resultSet.getDouble("weight")));
                        double temperature = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter Temperature:",
                                resultSet.getDouble("temperature")));
                        int sleep = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Sleep:",
                                resultSet.getInt("sleep")));
                        int petId = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Pet ID:",
                                resultSet.getInt("pet_id")));

                        // Perform the update
                        String updateQuery = "UPDATE health_records SET cause = ?, date = ?, prescription = ?, " +
                                "weight = ?, temperature = ?, sleep = ?, pet_id = ? WHERE id = ?";
                        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                            updateStatement.setString(1, cause);
                            updateStatement.setString(2, date);
                            updateStatement.setString(3, prescription);
                            updateStatement.setDouble(4, weight);
                            updateStatement.setDouble(5, temperature);
                            updateStatement.setInt(6, sleep);
                            updateStatement.setInt(7, petId);
                            updateStatement.setInt(8, recordId);

                            updateStatement.executeUpdate();
                            JOptionPane.showMessageDialog(this, "Health Record updated successfully");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Health Record with ID " + recordId + " not found",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating Health Record", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteRecord() {
        int recordId = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Health Record ID to delete:"));

        try (Connection connection = DatabaseConnector.getConnection()) {
            String query = "SELECT * FROM health_records WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, recordId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // Perform the delete
                        String deleteQuery = "DELETE FROM health_records WHERE id = ?";
                        try (PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
                            deleteStatement.setInt(1, recordId);
                            deleteStatement.executeUpdate();
                            JOptionPane.showMessageDialog(this, "Health Record deleted successfully");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Health Record with ID " + recordId + " not found",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting Health Record", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HealthRecordsFrame();
            }
        });
    }
}
