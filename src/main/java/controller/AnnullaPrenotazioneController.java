package controller;

import dto.PrenotazioneDTO;
import database.GestorePersistenza;
import entity.Prenotazione;

import java.time.LocalDate;


public class AnnullaPrenotazioneController {

    private final GestorePersistenza gp = new GestorePersistenza();

    public boolean confermaAnnullamentoPrenotazione(PrenotazioneDTO dto) {

        long id = dto.getIdPrenotazione();
        gp.elimina(Prenotazione.class, id);
        System.out.println("[Database]: Prenotazione rimossa dal sistema.");
        return true;
    }
}
