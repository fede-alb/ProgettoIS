package boundary;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import controller.ConfiguraStabilimentoController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.table.DefaultTableModel;

public class SchermataConfigurazioneStabilimento {

    private JPanel contentPane;
    private JTextField txtPrimaFila;
    private JTextField txtFilaIntermedia;
    private JTextField txtUltimaFila;
    private JTable tblServizi;
    private JButton btnConferma;
    private JLabel lblEsito;

    private ConfiguraStabilimentoController controller;

    public SchermataConfigurazioneStabilimento() {
        controller = new ConfiguraStabilimentoController();

        $$$setupUI$$$();

        DefaultTableModel model = new DefaultTableModel(
                new Object[][]{
                        {"", ""},
                        {"", ""},
                        {"", ""}
                },
                new String[]{
                        "Descrizione", "Disponibilita"
                }
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return String.class;
            }
        };

        tblServizi.setModel(model);

        btnConferma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confermaConfigurazione();
            }
        });
    }

    private void confermaConfigurazione() {
        if (tblServizi.isEditing()) {
            tblServizi.getCellEditor().stopCellEditing();
        }
        String txtP = txtPrimaFila.getText().trim();
        String txtI = txtFilaIntermedia.getText().trim();
        String txtU = txtUltimaFila.getText().trim();

        if (txtP.isEmpty() || txtI.isEmpty() || txtU.isEmpty()) {
            lblEsito.setText("Compila tutti i campi.");
            lblEsito.setForeground(Color.RED);
            return;
        }

        int nPrima;
        int nIntermedia;
        int nUltima;

        try {
            nPrima = Integer.parseInt(txtP);
            nIntermedia = Integer.parseInt(txtI);
            nUltima = Integer.parseInt(txtU);
        } catch (NumberFormatException ex) {
            lblEsito.setText("Inserisci solo numeri interi.");
            lblEsito.setForeground(Color.RED);
            return;
        }

        if (nPrima < 0 || nIntermedia < 0 || nUltima < 0) {
            lblEsito.setText("I numeri degli ombrelloni non possono essere negativi.");
            lblEsito.setForeground(Color.RED);
            return;
        }

        Map<String, Integer> servizi = new LinkedHashMap<>();

        for (int i = 0; i < tblServizi.getRowCount(); i++) {
            Object descObj = tblServizi.getValueAt(i, 0);
            Object dispObj = tblServizi.getValueAt(i, 1);

            String desc = descObj == null ? "" : descObj.toString().trim();
            String disp = dispObj == null ? "" : dispObj.toString().trim();

            if (desc.isEmpty() && disp.isEmpty()) {
                continue;
            }

            if (desc.isEmpty()) {
                lblEsito.setText("Descrizione mancante alla riga " + (i + 1));
                lblEsito.setForeground(Color.RED);
                return;
            }

            if (disp.isEmpty()) {
                lblEsito.setText("Disponibilita mancante per: " + desc);
                lblEsito.setForeground(Color.RED);
                return;
            }

            try {
                servizi.put(desc, Integer.parseInt(disp));
            } catch (NumberFormatException ex) {
                lblEsito.setText("Disponibilita non valida per: " + desc);
                lblEsito.setForeground(Color.RED);
                return;
            }
        }

        boolean esito = controller.configuraStabilimento(nPrima, nIntermedia, nUltima, servizi);

        if (esito) {
            lblEsito.setText("Stabilimento configurato con successo.");
            lblEsito.setForeground(Color.GREEN);
        } else {
            lblEsito.setText("Errore durante la configurazione.");
            lblEsito.setForeground(Color.RED);
        }
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

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(7, 5, new Insets(0, 0, 0, 0), -1, -1));
        final JLabel label1 = new JLabel();
        label1.setText("Numero ombrelloni prima fila");
        contentPane.add(label1, new GridConstraints(0, 0, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        contentPane.add(spacer1, new GridConstraints(1, 1, 6, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        txtPrimaFila = new JTextField();
        txtPrimaFila.setText("");
        contentPane.add(txtPrimaFila, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Numero ombrelloni fila intermedia");
        contentPane.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Numero ombrelloni ultima fila");
        contentPane.add(label3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtFilaIntermedia = new JTextField();
        contentPane.add(txtFilaIntermedia, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        txtUltimaFila = new JTextField();
        contentPane.add(txtUltimaFila, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Servizi Aggiuntivi");
        panel1.add(label4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel1.add(spacer2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        tblServizi = new JTable();
        contentPane.add(tblServizi, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        btnConferma = new JButton();
        btnConferma.setText("Conferma");
        contentPane.add(btnConferma, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblEsito = new JLabel();
        lblEsito.setText("In attesa");
        contentPane.add(lblEsito, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}