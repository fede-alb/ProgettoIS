package eseguibile;

import boundary.SchermataAccesso;

import javax.swing.*;

public class MainApplicazione {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Accesso");
            SchermataAccesso schermata = new SchermataAccesso();
            frame.setContentPane(schermata.getContentPane());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}