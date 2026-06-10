package boundary;

import controller.GestioneUtentiController;

import javax.swing.*;
import java.awt.*;

public class SchermataRegistrazione {

    private JPanel contentPane;
    private JTextField txtNome;
    private JTextField txtCognome;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JTextField txtTelefono;
    private JComboBox<String> cmbRuolo;
    private JButton btnRegistrati;
    private JLabel lblEsito;
    private JLabel lblAvviso;

    private final GestioneUtentiController controller;

    public SchermataRegistrazione() {
        controller = new GestioneUtentiController();
        inizializzaComponenti();
        configuraPrimoAvvio();
        registraEventi();
    }

    private void inizializzaComponenti() {
        contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));

        panelCentro.add(new JLabel("Nome"));
        txtNome = new JTextField(20);
        panelCentro.add(txtNome);
        panelCentro.add(Box.createVerticalStrut(8));

        panelCentro.add(new JLabel("Cognome"));
        txtCognome = new JTextField(20);
        panelCentro.add(txtCognome);
        panelCentro.add(Box.createVerticalStrut(8));

        panelCentro.add(new JLabel("Email"));
        txtEmail = new JTextField(20);
        panelCentro.add(txtEmail);
        panelCentro.add(Box.createVerticalStrut(8));

        panelCentro.add(new JLabel("Password"));
        txtPassword = new JPasswordField(20);
        panelCentro.add(txtPassword);
        panelCentro.add(Box.createVerticalStrut(8));

        panelCentro.add(new JLabel("Telefono"));
        txtTelefono = new JTextField(20);
        panelCentro.add(txtTelefono);
        panelCentro.add(Box.createVerticalStrut(8));

        panelCentro.add(new JLabel("Ruolo"));
        cmbRuolo = new JComboBox<>(new String[]{"Cliente", "Gestore"});
        panelCentro.add(cmbRuolo);
        panelCentro.add(Box.createVerticalStrut(10));

        lblAvviso = new JLabel(" ");
        lblAvviso.setForeground(new Color(180, 120, 0));
        panelCentro.add(lblAvviso);
        panelCentro.add(Box.createVerticalStrut(10));

        btnRegistrati = new JButton("Registrati");
        panelCentro.add(btnRegistrati);
        panelCentro.add(Box.createVerticalStrut(10));

        lblEsito = new JLabel(" ");
        panelCentro.add(lblEsito);

        contentPane.add(panelCentro, BorderLayout.CENTER);
    }

    private void configuraPrimoAvvio() {
        if (controller.primoAvvio()) {
            cmbRuolo.setSelectedItem("Gestore");
            cmbRuolo.setEnabled(false);
            lblAvviso.setText("Primo avvio: è necessario registrare prima un gestore.");
        }
    }

    private void registraEventi() {
        btnRegistrati.addActionListener(e -> eseguiRegistrazione());
    }

    private void eseguiRegistrazione() {
        String nome = txtNome.getText().trim();
        String cognome = txtCognome.getText().trim();
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());
        String telefono = txtTelefono.getText().trim();
        String ruolo = (String) cmbRuolo.getSelectedItem();

        if (nome.isEmpty() || cognome.isEmpty() || email.isEmpty() || password.isEmpty() || telefono.isEmpty()) {
            lblEsito.setText("Compila tutti i campi.");
            lblEsito.setForeground(Color.RED);
            return;
        }

        if (!email.contains("@") || !email.contains(".")) {
            lblEsito.setText("Email non valida.");
            lblEsito.setForeground(Color.RED);
            return;
        }

        if (controller.emailGiaRegistrata(email)) {
            lblEsito.setText("Email già registrata.");
            lblEsito.setForeground(Color.RED);
            return;
        }

        boolean esito = controller.registrazione(nome, cognome, email, password, telefono, ruolo);

        if (esito) {
            lblEsito.setText("Registrazione completata con successo.");
            lblEsito.setForeground(new Color(0, 128, 0));
        } else {
            lblEsito.setText("Registrazione non riuscita.");
            lblEsito.setForeground(Color.RED);
        }
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Registrazione");
        SchermataRegistrazione schermata = new SchermataRegistrazione();
        frame.setContentPane(schermata.getContentPane());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}