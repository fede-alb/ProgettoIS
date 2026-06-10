package boundary;

import controller.ConfiguraStabilimentoController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class SchermataConfigurazioneStabilimento {

    private final ConfiguraStabilimentoController controller;

    private JPanel contentPane;
    private JTextField txtPrimaFila;
    private JTextField txtFilaIntermedia;
    private JTextField txtUltimaFila;
    private JTextField txtDescrizioneServizio;
    private JTextField txtDisponibilitaServizio;
    private JButton btnAggiungiServizio;
    private JButton btnEliminaServizio;
    private JButton btnConferma;
    private JLabel lblEsito;
    private JTable tblRiepilogoServizi;
    private DefaultTableModel modelRiepilogo;

    //Costruttore della schermata
    public SchermataConfigurazioneStabilimento() {
        controller = new ConfiguraStabilimentoController();
        costruisciUI();
        registraEventi();
        verificaStabilimentoEsistente();
    }

    private void costruisciUI() {
        contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        //Sezione ombrelloni
        JPanel panelOmbrelloni = new JPanel(new GridLayout(3, 2, 8, 8));
        panelOmbrelloni.setBorder(BorderFactory.createTitledBorder("Numero ombrelloni per fila"));

        panelOmbrelloni.add(new JLabel("Prima fila"));
        txtPrimaFila = new JTextField();
        panelOmbrelloni.add(txtPrimaFila);

        panelOmbrelloni.add(new JLabel("Fila intermedia"));
        txtFilaIntermedia = new JTextField();
        panelOmbrelloni.add(txtFilaIntermedia);

        panelOmbrelloni.add(new JLabel("Ultima fila"));
        txtUltimaFila = new JTextField();
        panelOmbrelloni.add(txtUltimaFila);

        //Sezione inserimento servizio
        JPanel panelInserimento = new JPanel(new GridLayout(2, 2, 8, 8));
        panelInserimento.setBorder(BorderFactory.createTitledBorder("Aggiungi servizio aggiuntivo"));

        panelInserimento.add(new JLabel("Descrizione"));
        txtDescrizioneServizio = new JTextField();
        panelInserimento.add(txtDescrizioneServizio);

        panelInserimento.add(new JLabel("Disponibilita"));
        txtDisponibilitaServizio = new JTextField();
        panelInserimento.add(txtDisponibilitaServizio);

        btnAggiungiServizio = new JButton("Aggiungi servizio");

        JPanel panelInserimentoWrapper = new JPanel(new BorderLayout(8, 8));
        panelInserimentoWrapper.add(panelInserimento, BorderLayout.CENTER);
        panelInserimentoWrapper.add(btnAggiungiServizio, BorderLayout.SOUTH);

        //Tabella riepilogo servizi
        modelRiepilogo = new DefaultTableModel(new String[]{"Descrizione", "Disponibilita"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblRiepilogoServizi = new JTable(modelRiepilogo);
        tblRiepilogoServizi.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollRiepilogo = new JScrollPane(tblRiepilogoServizi);
        scrollRiepilogo.setPreferredSize(new Dimension(350, 130));

        btnEliminaServizio = new JButton("Elimina servizio selezionato");

        JPanel panelRiepilogo = new JPanel(new BorderLayout(8, 8));
        panelRiepilogo.setBorder(BorderFactory.createTitledBorder("Servizi aggiunti"));
        panelRiepilogo.add(scrollRiepilogo, BorderLayout.CENTER);
        panelRiepilogo.add(btnEliminaServizio, BorderLayout.SOUTH);

        //Sezione servizi (inserimento + riepilogo)
        JPanel panelServizi = new JPanel(new BorderLayout(10, 10));
        panelServizi.add(panelInserimentoWrapper, BorderLayout.NORTH);
        panelServizi.add(panelRiepilogo, BorderLayout.CENTER);

        //Bottone conferma + etichetta esito
        btnConferma = new JButton("Conferma configurazione");
        lblEsito = new JLabel(" ");

        JPanel panelSud = new JPanel(new BorderLayout(8, 8));
        panelSud.add(btnConferma, BorderLayout.NORTH);
        panelSud.add(lblEsito, BorderLayout.SOUTH);

        //Assemblaggio
        JPanel panelCentro = new JPanel(new BorderLayout(10, 10));
        panelCentro.add(panelOmbrelloni, BorderLayout.NORTH);
        panelCentro.add(panelServizi, BorderLayout.CENTER);

        contentPane.add(panelCentro, BorderLayout.CENTER);
        contentPane.add(panelSud, BorderLayout.SOUTH);
    }

    private void registraEventi() {
        btnAggiungiServizio.addActionListener(e -> aggiungiServizio());
        btnEliminaServizio.addActionListener(e -> eliminaServizioSelezionato());
        btnConferma.addActionListener(e -> confermaConfigurazione());
    }

    private void verificaStabilimentoEsistente() {
        if (controller.isStabilimentoGiaConfigurato()) {
            bloccaUI("Lo stabilimento e' gia' stato configurato.");
        }
    }

    private void bloccaUI(String messaggio) {
        txtPrimaFila.setEnabled(false);
        txtFilaIntermedia.setEnabled(false);
        txtUltimaFila.setEnabled(false);
        txtDescrizioneServizio.setEnabled(false);
        txtDisponibilitaServizio.setEnabled(false);
        btnAggiungiServizio.setEnabled(false);
        btnEliminaServizio.setEnabled(false);
        btnConferma.setEnabled(false);
        lblEsito.setText(messaggio);
        lblEsito.setForeground(Color.RED);
    }

    private void aggiungiServizio() {
        String descrizione = txtDescrizioneServizio.getText().trim();
        String disponibilitaText = txtDisponibilitaServizio.getText().trim();

        if (descrizione.isEmpty()) {
            mostraErrore("Inserisci la descrizione del servizio.");
            return;
        }

        if (disponibilitaText.isEmpty()) {
            mostraErrore("Inserisci la disponibilita del servizio.");
            return;
        }

        int disponibilita;
        try {
            disponibilita = Integer.parseInt(disponibilitaText);
        } catch (NumberFormatException ex) {
            mostraErrore("La disponibilita deve essere un numero intero.");
            return;
        }

        if (disponibilita <= 0) {
            mostraErrore("La disponibilita deve essere maggiore di 0.");
            return;
        }

        for (int i = 0; i < modelRiepilogo.getRowCount(); i++) {
            String descEsistente = modelRiepilogo.getValueAt(i, 0).toString();
            if (descEsistente.equalsIgnoreCase(descrizione)) {
                mostraErrore("Esiste gia' un servizio con questa descrizione.");
                return;
            }
        }

        modelRiepilogo.addRow(new Object[]{descrizione, disponibilita});
        txtDescrizioneServizio.setText("");
        txtDisponibilitaServizio.setText("");
        lblEsito.setText("Servizio aggiunto.");
        lblEsito.setForeground(new Color(0, 130, 0));
    }

    private void eliminaServizioSelezionato() {
        int rigaSelezionata = tblRiepilogoServizi.getSelectedRow();

        if (rigaSelezionata == -1) {
            mostraErrore("Seleziona un servizio dalla tabella per eliminarlo.");
            return;
        }

        modelRiepilogo.removeRow(rigaSelezionata);
        lblEsito.setText("Servizio rimosso.");
        lblEsito.setForeground(new Color(0, 130, 0));
    }

    private void confermaConfigurazione() {
        String txtP = txtPrimaFila.getText().trim();
        String txtI = txtFilaIntermedia.getText().trim();
        String txtU = txtUltimaFila.getText().trim();

        if (txtP.isEmpty() || txtI.isEmpty() || txtU.isEmpty()) {
            mostraErrore("Compila tutti i campi del numero di ombrelloni.");
            return;
        }

        int nPrima, nIntermedia, nUltima;

        try {
            nPrima = Integer.parseInt(txtP);
            nIntermedia = Integer.parseInt(txtI);
            nUltima = Integer.parseInt(txtU);
        } catch (NumberFormatException ex) {
            mostraErrore("Inserisci solo numeri interi per gli ombrelloni.");
            return;
        }

        if (nPrima <= 0 || nIntermedia <= 0 || nUltima <= 0) {
            mostraErrore("Il numero di ombrelloni deve essere maggiore di 0.");
            return;
        }

        Map<String, Integer> servizi = new LinkedHashMap<>();

        for (int i = 0; i < modelRiepilogo.getRowCount(); i++) {
            String desc = modelRiepilogo.getValueAt(i, 0).toString();
            int disp = (int) modelRiepilogo.getValueAt(i, 1);
            servizi.put(desc, disp);
        }

        boolean esito = controller.configuraStabilimento(nPrima, nIntermedia, nUltima, servizi);

        if (esito) {
            lblEsito.setText("Stabilimento configurato con successo.");
            lblEsito.setForeground(new Color(0, 130, 0));
            bloccaUI("Stabilimento configurato. Non e' possibile riconfigurare.");
        } else {
            mostraErrore("Errore durante la configurazione. Lo stabilimento potrebbe essere gia' configurato.");
        }
    }

    private void mostraErrore(String messaggio) {
        lblEsito.setText(messaggio);
        lblEsito.setForeground(Color.RED);
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Configura Stabilimento");
        SchermataConfigurazioneStabilimento schermata = new SchermataConfigurazioneStabilimento();
        frame.setContentPane(schermata.getContentPane());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}