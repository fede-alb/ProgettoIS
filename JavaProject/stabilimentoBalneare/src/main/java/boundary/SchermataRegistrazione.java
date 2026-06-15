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

    public SchermataRegistrazione() {
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
        lblAvviso.setPreferredSize(new Dimension(250, 20));
        lblAvviso.setHorizontalAlignment(SwingConstants.CENTER);
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
        if (GestioneUtentiController.primoAvvio()) {
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

        if (!nome.matches("^[a-zA-Z ]+$")) {
            lblEsito.setText("Nome: caratteri non validi.");
            lblEsito.setForeground(Color.RED);
            return;
        }

        if (!cognome.matches("^[a-zA-Z ]+$")) {
            lblEsito.setText("Cognome: caratteri non validi.");
            lblEsito.setForeground(Color.RED);
            return;
        }

        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            lblEsito.setText("Email: caratteri non validi.");
            lblEsito.setForeground(Color.RED);
            return;
        }

        if (!password.matches("^[a-zA-Z0-9]+$")) {
            lblEsito.setText("Password: caratteri non validi.");
            lblEsito.setForeground(Color.RED);
            return;
        }

        if (!telefono.matches("^[0-9]+$")) {
            lblEsito.setText("Telefono: caratteri non validi.");
            lblEsito.setForeground(Color.RED);
            return;
        }

        if (nome.length() > 40) {
            lblEsito.setText("Nome troppo lungo.");
            lblEsito.setForeground(Color.RED);
            return;
        }

        if (cognome.length() > 40) {
            lblEsito.setText("Cognome troppo lungo.");
            lblEsito.setForeground(Color.RED);
            return;
        }

        if (telefono.length() != 10) {
            lblEsito.setText("Il telefono deve avere esattamente 10 numeri.");
            lblEsito.setForeground(Color.RED);
            return;
        }

        if (email.length() < 15) {
            lblEsito.setText("Email troppo corta.");
            lblEsito.setForeground(Color.RED);
            return;
        }

        if (email.length() > 40) {
            lblEsito.setText("Email troppo lunga.");
            lblEsito.setForeground(Color.RED);
            return;
        }

        if (GestioneUtentiController.emailGiaRegistrata(email)) {
            lblEsito.setText("Email già registrata.");
            lblEsito.setForeground(Color.RED);
            return;
        }

        if (password.length() < 8) {
            lblEsito.setText("Password troppo corta.");
            lblEsito.setForeground(Color.RED);
            return;
        }

        if (password.length() > 20) {
            lblEsito.setText("Password troppo lunga.");
            lblEsito.setForeground(Color.RED);
            return;
        }

        if (password.equals("password")) {
            lblEsito.setText("Davvero questa password?");
            lblEsito.setForeground(Color.RED);
            return;
        }

        boolean esito = GestioneUtentiController.registrazione(
                nome, cognome, email, password, telefono, ruolo
        );

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
}