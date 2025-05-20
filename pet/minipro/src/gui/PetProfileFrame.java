package gui;

import model.DatabaseConnector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class PetProfileFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    private JTextField nameField;
    private JTextField birthdayField;
    private JTextField speciesField;
    private JTextField breedField;

    public PetProfileFrame() {
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2));
        add(panel);

        
        nameField = new JTextField();
        panel.add(new JLabel("Name:"));
        panel.add(nameField);

        birthdayField = new JTextField();
        panel.add(new JLabel("Birthday:"));
        panel.add(birthdayField);


        speciesField = new JTextField();
        panel.add(new JLabel("Species:"));
        panel.add(speciesField);

        breedField = new JTextField();
        panel.add(new JLabel("Breed:"));
        panel.add(breedField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                savePetProfile();
            }
        });
        panel.add(saveButton);

        JButton viewProfilesButton = new JButton("View Pet Profiles");
        viewProfilesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayPetProfiles();
            }
        });
        panel.add(viewProfilesButton);

        setVisible(true);
    }

    private void displayPetProfiles() {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String query = "SELECT * FROM pets";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                java.util.List<String> petProfiles = new java.util.ArrayList<>();
                while (resultSet.next()) {
                    petProfiles.add("ID: " + resultSet.getInt("id") +
                            ", Name: " + resultSet.getString("name") +
                            ", Birthday: " + resultSet.getDate("birthday") +
                            ", Species: " + resultSet.getString("species") +
                            ", Breed: " + resultSet.getString("breed"));
                }

                String result = String.join("\n", petProfiles);
                JOptionPane.showMessageDialog(this, result, "Pet Profiles", JOptionPane.INFORMATION_MESSAGE);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching Pet Profiles", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void savePetProfile() {
        String name = nameField.getText();
        String species = speciesField.getText();
        String breed = breedField.getText();
        String birthday = birthdayField.getText();


        try (Connection connection = DatabaseConnector.getConnection()) {
            String query = "INSERT INTO pets (name, birthday, species, breed) VALUES (?, STR_TO_DATE(?, '%m/%d/%Y'), ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, birthday);
                preparedStatement.setString(3, species);
                preparedStatement.setString(4, breed);

                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(this, "Pet Profile added successfully");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding Pet Profile", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PetProfileFrame();
            }
        });
    }
}

