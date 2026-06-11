package eseguibile;

import boundary.ConsultaPropriePrenotazioni;
import controller.GestionePrenotazioneController;
import entity.Cliente;

import javax.swing.*;

public class MainAnnullaPrenotazione {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            //Creo il contenitore
            JFrame frame = new JFrame("Sistema Gestione Prenotazioni");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            long idClienteCorrente = 2;
            //long idClienteCorrente = GestionePrenotazioneController.getIdClienteCorrente();
            ConsultaPropriePrenotazioni schermataTabella = new ConsultaPropriePrenotazioni(idClienteCorrente);
            frame.setContentPane(schermataTabella.$$$getRootComponent$$$());

            //Configuro la finestra
            frame.pack();
            frame.setSize(900, 450);
            frame.setLocationRelativeTo(null); // Centra la finestra sullo schermo

            frame.setVisible(true);
        });
    }
}