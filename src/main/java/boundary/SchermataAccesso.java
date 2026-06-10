package boundary;

import controller.GestioneUtentiController;
import entity.Gestore;
import entity.SessioneUtente;
import entity.Utente;

import javax.swing.*;
import java.awt.*;

public class SchermataAccesso {

    private JPanel contentPane;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnAccedi;
    private JButton btnRegistrati;
    private JLabel lblEsito;
    private JLabel lblTitolo;
    private JLabel lblSottotitolo;

    private final GestioneUtentiController controller;

    public SchermataAccesso() {
        controller = new GestioneUtentiController();
        inizializzaComponenti();
        registraEventi();
    }

    private void inizializzaComponenti() {
        contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));

        lblTitolo = new JLabel("Benvenuto");
        lblTitolo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitolo.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblSottotitolo = new JLabel("Stabilimento Balneare - Gruppo 13");
        lblSottotitolo.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelCentro.add(lblTitolo);
        panelCentro.add(Box.createVerticalStrut(8));
        panelCentro.add(lblSottotitolo);
        panelCentro.add(Box.createVerticalStrut(20));

        panelCentro.add(new JLabel("Email"));
        txtEmail = new JTextField(20);
        panelCentro.add(txtEmail);
        panelCentro.add(Box.createVerticalStrut(10));

        panelCentro.add(new JLabel("Password"));
        txtPassword = new JPasswordField(20);
        panelCentro.add(txtPassword);
        panelCentro.add(Box.createVerticalStrut(15));

        btnAccedi = new JButton("Accedi");
        panelCentro.add(btnAccedi);
        panelCentro.add(Box.createVerticalStrut(10));

        btnRegistrati = new JButton("Non hai un account? Registrati");
        panelCentro.add(btnRegistrati);
        panelCentro.add(Box.createVerticalStrut(10));

        lblEsito = new JLabel(" ");
        lblEsito.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentro.add(lblEsito);

        contentPane.add(panelCentro, BorderLayout.CENTER);
    }

    private void registraEventi() {
        btnAccedi.addActionListener(e -> eseguiAccesso());
        btnRegistrati.addActionListener(e -> apriRegistrazione());
    }

    private void eseguiAccesso() {
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            lblEsito.setText("Inserisci email e password.");
            lblEsito.setForeground(Color.RED);
            return;
        }

        Utente utente = controller.accesso(email, password);

        if (utente == null) {
            lblEsito.setText("Credenziali non valide.");
            lblEsito.setForeground(Color.RED);
            return;
        }

        // Salvo l'utente autenticato nella sessione corrente dell'applicazione
        SessioneUtente.impostaUtenteCorrente(utente);

        lblEsito.setText("Accesso effettuato con successo.");
        lblEsito.setForeground(new Color(0, 128, 0));

        Window finestraCorrente = SwingUtilities.getWindowAncestor(contentPane);
        if (finestraCorrente != null) {
            finestraCorrente.dispose();
        }

        //chiama il metodo che sarà presente sulle diverse classi mainFrame
        if (utente instanceof Gestore) {
            MainFrameGestore.apriHomepage();
        } else {
            MainFrameCliente.apriHomepage();
        }
    }

    private void apriRegistrazione() {
        JFrame frame = new JFrame("Registrazione");
        SchermataRegistrazione schermata = new SchermataRegistrazione();
        frame.setContentPane(schermata.getContentPane());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Accesso");
        SchermataAccesso schermata = new SchermataAccesso();
        frame.setContentPane(schermata.getContentPane());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}