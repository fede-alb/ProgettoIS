package boundary;

import controller.GestionePrenotazioneController;
import entity.Prenotazione;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ElencoPrenotazioniView extends JFrame {

    private JTable table;

    public ElencoPrenotazioniView() {

        setTitle("Elenco Prenotazioni");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] colonne = {
                "ID",
                "Data",
                "Stato",
                "Prezzo"
        };

        DefaultTableModel model =
                new DefaultTableModel(colonne, 0);

        GestionePrenotazioneController controller =
                new GestionePrenotazioneController();

        List<Prenotazione> prenotazioni =
                controller.consultaElencoPrenotazioni();

        for (Prenotazione p : prenotazioni) {

            model.addRow(new Object[]{
                    p.getIdPrenotazione(),
                    p.getData(),
                    p.getStato(),
                    p.getPrezzo()
            });
        }

        table = new JTable(model);

        table.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                int riga = table.getSelectedRow();

                if (riga >= 0) {

                    Long idPrenotazione =
                            ((Number) table.getValueAt(riga, 0))
                                    .longValue();


                    Prenotazione p =
                            controller.consultaPrenotazione(idPrenotazione);

                    new DettaglioPrenotazioneView(p);
                }
            }
        });

        add(new JScrollPane(table));

        setVisible(true);
    }
}

