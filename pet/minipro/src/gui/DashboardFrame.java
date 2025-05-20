package gui;



import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashboardFrame extends JFrame {
	private static final long serialVersionUID = 1L;

    private JButton viewHealthRecordsButton;
    private JButton petProfileButton;

    public DashboardFrame() {
        setTitle("Dashboard");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        viewHealthRecordsButton = new JButton("View Health Records");
        petProfileButton = new JButton("Pet Profile");

        viewHealthRecordsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openHealthRecordsFrame();
            }
        });

        petProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openPetProfileFrame();
            }
        });

        panel.add(viewHealthRecordsButton);
        panel.add(petProfileButton);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void openHealthRecordsFrame() {
        HealthRecordsFrame healthRecordsFrame = new HealthRecordsFrame();
        healthRecordsFrame.setVisible(true);
    }

    private void openPetProfileFrame() {
        PetProfileFrame petProfileFrame = new PetProfileFrame();
        petProfileFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DashboardFrame();
            }
        });
    }
}
