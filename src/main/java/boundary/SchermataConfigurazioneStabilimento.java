package boundary;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import controller.ConfiguraStabilimentoController;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class SchermataConfigurazioneStabilimento {
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

    public SchermataConfigurazioneStabilimento() {
        //costruisciUI();
        inizializzaTabellaServizi();
        registraEventi();
        verificaStabilimentoEsistente();
    }

    /* SI PUO' ELIMINARE?
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
    */

    private void registraEventi() {
        btnAggiungiServizio.addActionListener(e -> aggiungiServizio());
        btnEliminaServizio.addActionListener(e -> eliminaServizioSelezionato());
        btnConferma.addActionListener(e -> confermaConfigurazione());
    }

    private void verificaStabilimentoEsistente() {
        if (ConfiguraStabilimentoController.isStabilimentoGiaConfigurato()) {
            disabilitaCampi();
            lblEsito.setText("Lo stabilimento è già stato configurato.");
            lblEsito.setForeground(Color.RED);
        }
    }

    private void disabilitaCampi() {
        txtPrimaFila.setEnabled(false);
        txtFilaIntermedia.setEnabled(false);
        txtUltimaFila.setEnabled(false);
        txtDescrizioneServizio.setEnabled(false);
        txtDisponibilitaServizio.setEnabled(false);
        btnAggiungiServizio.setEnabled(false);
        btnEliminaServizio.setEnabled(false);
        btnConferma.setEnabled(false);
    }

    private void aggiungiServizio() {
        String descrizione = txtDescrizioneServizio.getText().trim();
        String disponibilitaText = txtDisponibilitaServizio.getText().trim();

        if (descrizione.isEmpty()) {
            mostraErrore("Inserisci la descrizione del servizio.");
            return;
        }

        if (disponibilitaText.isEmpty()) {
            mostraErrore("Inserisci la disponibilità del servizio.");
            return;
        }

        int disponibilita;
        try {
            disponibilita = Integer.parseInt(disponibilitaText);
        } catch (NumberFormatException ex) {
            mostraErrore("La disponibilità deve essere un numero intero.");
            return;
        }

        if (disponibilita <= 0) {
            mostraErrore("La disponibilità deve essere maggiore di 0.");
            return;
        }

        for (int i = 0; i < modelRiepilogo.getRowCount(); i++) {
            String descEsistente = modelRiepilogo.getValueAt(i, 0).toString();
            if (descEsistente.equalsIgnoreCase(descrizione)) {
                mostraErrore("Esiste già un servizio con questa descrizione.");
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
            int disp = Integer.parseInt(modelRiepilogo.getValueAt(i, 1).toString());
            servizi.put(desc, disp);
        }

        boolean esito = ConfiguraStabilimentoController.configuraStabilimento(nPrima, nIntermedia, nUltima, servizi);

        if (esito) {
            disabilitaCampi();
            lblEsito.setText("Stabilimento configurato con successo.");
            lblEsito.setForeground(new Color(0, 130, 0));
        } else {
            mostraErrore("Errore durante la configurazione. Lo stabilimento potrebbe essere già configurato.");
        }
    }

    private void mostraErrore(String messaggio) {
        lblEsito.setText(messaggio);
        lblEsito.setForeground(Color.RED);
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    private void inizializzaTabellaServizi() {
        modelRiepilogo = new DefaultTableModel(new String[]{"Descrizione", "Disponibilità"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblRiepilogoServizi.setModel(modelRiepilogo);
        tblRiepilogoServizi.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    /* DA RIMUOVERE
    public static void main(String[] args) {
        JFrame frame = new JFrame("Configura Stabilimento");
        SchermataConfigurazioneStabilimento schermata = new SchermataConfigurazioneStabilimento();
        frame.setContentPane(schermata.getContentPane());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }*/

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(5, 1, new Insets(15, 15, 15, 15), 10, 10));
        contentPane.setPreferredSize(new Dimension(550, 520));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), 8, 8));
        contentPane.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Numero ombrelloni per fila", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JLabel label1 = new JLabel();
        label1.setText("Prima fila");
        panel1.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtPrimaFila = new JTextField();
        panel1.add(txtPrimaFila, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Fila intermedia");
        panel1.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtFilaIntermedia = new JTextField();
        panel1.add(txtFilaIntermedia, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Ultima fila");
        panel1.add(label3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtUltimaFila = new JTextField();
        panel1.add(txtUltimaFila, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Aggiungi Servizio Aggiuntivo", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JLabel label4 = new JLabel();
        label4.setText("Descrizione");
        panel2.add(label4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtDescrizioneServizio = new JTextField();
        panel2.add(txtDescrizioneServizio, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Disponibilità");
        panel2.add(label5, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtDisponibilitaServizio = new JTextField();
        panel2.add(txtDisponibilitaServizio, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        btnAggiungiServizio = new JButton();
        btnAggiungiServizio.setText("Aggiungi servizio");
        btnAggiungiServizio.setToolTipText("null");
        panel2.add(btnAggiungiServizio, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Servizi Aggiuntivi", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel3.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane1.setBorder(BorderFactory.createTitledBorder(null, "", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        tblRiepilogoServizi = new JTable();
        scrollPane1.setViewportView(tblRiepilogoServizi);
        final Spacer spacer1 = new Spacer();
        panel3.add(spacer1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        btnEliminaServizio = new JButton();
        btnEliminaServizio.setText("Elimina Servizio Selezionato");
        btnEliminaServizio.setToolTipText("null");
        panel3.add(btnEliminaServizio, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnConferma = new JButton();
        btnConferma.setText("Conferma Configurazione");
        contentPane.add(btnConferma, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblEsito = new JLabel();
        lblEsito.setText("In attesa...");
        contentPane.add(lblEsito, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

}