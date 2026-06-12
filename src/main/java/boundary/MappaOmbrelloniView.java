package boundary;

import controller.ConfiguraStabilimentoController;

import dto.FilaDTO;
import dto.OmbrelloneDTO;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

public class MappaOmbrelloniView {
    private JPanel panel;
    private JLabel txtData;
    private JSpinner dataSpinner;
    private JButton btnConferma;

    private final boolean isPrenotazione;

    public MappaOmbrelloniView(boolean isPrenotazione) {
        this.isPrenotazione = isPrenotazione;
        $$$setupUI$$$();

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        SpinnerDateModel dateModel = getSpinnerDateModel(cal);
        dataSpinner.setModel(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dataSpinner, "dd/MM/yyyy");
        dataSpinner.setEditor(dateEditor);
        btnConferma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stampaOmbrelloni();
            }
        });
    }

    @NotNull
    private static SpinnerDateModel getSpinnerDateModel(Calendar cal) {
        Date oggi = cal.getTime();

        Calendar calFine = Calendar.getInstance();
        calFine.set(Calendar.MONTH, Calendar.SEPTEMBER);
        calFine.set(Calendar.DAY_OF_MONTH, 30);
        calFine.set(Calendar.HOUR_OF_DAY, 23);
        calFine.set(Calendar.MINUTE, 59);
        calFine.set(Calendar.SECOND, 59);
        calFine.set(Calendar.MILLISECOND, 999);
        Date fineSettembre = calFine.getTime();

        return new SpinnerDateModel(oggi, oggi, fineSettembre, Calendar.DAY_OF_MONTH);
    }

    public JFrame apriMappaOmbrelloniView() {
        JFrame frame = new JFrame("Mappa Ombrelloni");
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
        return frame;
    }

    private void stampaOmbrelloni() {
        Date dataSelezionata = (Date) dataSpinner.getValue();
        List<FilaDTO> file = ConfiguraStabilimentoController.visualizzaOmbrelloni(dataSelezionata);
        mostraPopupMappa(file, dataSelezionata);
    }

    private void mostraPopupMappa(List<FilaDTO> file, Date dataSelezionata) {
        JPanel mapPanel = new JPanel();
        mapPanel.setLayout(new BoxLayout(mapPanel, BoxLayout.Y_AXIS));

        for (FilaDTO fila : file) {
            JPanel rowPanel = new JPanel();
            rowPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
            List<OmbrelloneDTO> ombrelloni = fila.getOmbrelloni();
            for (OmbrelloneDTO ombrellone : ombrelloni) {
                JLabel lblOmbrellone = getLblOmbrellone(ombrellone, dataSelezionata);
                rowPanel.add(lblOmbrellone);
            }
            mapPanel.add(rowPanel);
        }

        JScrollPane scrollPane = new JScrollPane(mapPanel);
        scrollPane.setPreferredSize(new Dimension(1000, 400));
        JOptionPane.showMessageDialog(
                this.panel,
                scrollPane,
                "Mappa Ombrelloni",
                JOptionPane.PLAIN_MESSAGE
        );
    }

    private JLabel getLblOmbrellone(OmbrelloneDTO ombrellone, Date dataSelezionata) {
        JLabel lblOmbrellone = new JLabel("Omb. " + ombrellone.getNumero(), SwingConstants.CENTER);
        lblOmbrellone.setPreferredSize(new Dimension(100, 100));
        lblOmbrellone.setOpaque(true);
        lblOmbrellone.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));

        if (ombrellone.isOccupato()) {
            lblOmbrellone.setBackground(new Color(220, 53, 69));
            lblOmbrellone.setForeground(Color.WHITE);
        } else {
            lblOmbrellone.setBackground(new Color(40, 167, 69));
            lblOmbrellone.setForeground(Color.WHITE);
            if(isPrenotazione) {
                lblOmbrellone.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                lblOmbrellone.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        Window window = SwingUtilities.getWindowAncestor(lblOmbrellone);
                        if (window != null) {
                            window.dispose();
                        }
                        SwingUtilities.getWindowAncestor(panel).dispose();
                        EffettuaPrenotazioneForm formPrenotazione = new EffettuaPrenotazioneForm(dataSelezionata, ombrellone);
                        formPrenotazione.apriEffettuaPrenotazioneForm();
                    }
                });
            }
        }
        return lblOmbrellone;
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel = new JPanel();
        panel.setLayout(new GridLayoutManager(2, 2, new Insets(0, 50, 0, 50), -1, -1));
        panel.setPreferredSize(new Dimension(300, 100));
        txtData = new JLabel();
        txtData.setEnabled(true);
        txtData.setText("Inserire data:");
        panel.add(txtData, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        dataSpinner = new JSpinner();
        panel.add(dataSpinner, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnConferma = new JButton();
        btnConferma.setText("Conferma");
        panel.add(btnConferma, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }
}
