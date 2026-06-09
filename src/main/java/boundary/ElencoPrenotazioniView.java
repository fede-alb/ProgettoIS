package boundary;

import controller.GestionePrenotazioneController;
import entity.Prenotazione;

import java.awt.*;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import com.toedter.calendar.JDateChooser;

public class ElencoPrenotazioniView extends JFrame {

    private JTable table;
    private DefaultTableModel model;

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

        model = new DefaultTableModel(colonne, 0);

        GestionePrenotazioneController controller =
                new GestionePrenotazioneController();

        JPanel pannelloFiltro =
                new JPanel(new FlowLayout());

        JDateChooser dateChooser =
                new JDateChooser();

        dateChooser.setDateFormatString("dd/MM/yyyy");
        dateChooser.setPreferredSize(new Dimension(120, 30));
        dateChooser.getDateEditor()
                .getUiComponent()
                .setFont(new Font("Arial", Font.PLAIN, 14));


        dateChooser.addPropertyChangeListener("date", e -> {

            Date data = dateChooser.getDate();

            if (data != null) {

                aggiornaTabella(
                        controller.consultaPrenotazioniPerData(data)
                );
            }
        });




        JButton btnTutte =
                new JButton("Mostra tutte");


        btnTutte.addActionListener(e -> {

            aggiornaTabella(
                    controller.consultaElencoPrenotazioni()
            );
        });


        btnTutte.addActionListener(e ->

                aggiornaTabella(
                        controller.consultaElencoPrenotazioni()
                )
        );


        pannelloFiltro.add(new JLabel("Data:"));
        pannelloFiltro.add(dateChooser);
        pannelloFiltro.add(btnTutte);

        aggiornaTabella(
                controller.consultaElencoPrenotazioni()
        );

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

        setLayout(new BorderLayout());

        add(pannelloFiltro, BorderLayout.NORTH);

        add(
                new JScrollPane(table),
                BorderLayout.CENTER
        );

        setVisible(true);
    }


    private void aggiornaTabella(
            List<Prenotazione> prenotazioni) {

        model.setRowCount(0);

        for (Prenotazione p : prenotazioni) {

            model.addRow(new Object[]{
                    p.getIdPrenotazione(),
                    p.getData(),
                    p.getStato(),
                    p.getPrezzo()
            });
        }
    }


}

