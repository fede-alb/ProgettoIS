package boundary;

import dto.PrenotazioneDTO;
import entity.Prenotazione;

import javax.swing.*;
import java.awt.*;

public class DettaglioPrenotazioneView extends JFrame {

    public DettaglioPrenotazioneView(Prenotazione p) {

        setTitle("Dettaglio Prenotazione");
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        panel.add(new JLabel(
                "Cliente: "
                        + p.getCliente().getNome()
                        + " "
                        + p.getCliente().getCognome()
        ));

        panel.add(new JLabel("ID: " + p.getIdPrenotazione()));
        //panel.add(new JLabel("Data: " + p.getDataPrenotazione()));
        panel.add(new JLabel("Stato: " + p.getStato()));
        //panel.add(new JLabel("Prezzo: " + p.getCosto()));
        panel.add(new JLabel("Ombrellone: " + p.getOmbrellone().getNumero()));

        add(panel);

        setVisible(true);
    }
}