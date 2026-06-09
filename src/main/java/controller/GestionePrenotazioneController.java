package controller;

import database.GestorePersistenza;
import entity.Prenotazione;

import java.util.List;
import java.util.Map;

public class GestionePrenotazioneController {

    private final GestorePersistenza gp = new GestorePersistenza();

    public List<Prenotazione> consultaElencoPrenotazioni() {
        return gp.cercaPerCampi(
                Prenotazione.class,
                Map.of()
        );
    }

    public Prenotazione consultaPrenotazione(Long idPrenotazione) {
        return gp.trovaPerId(
                Prenotazione.class,
                idPrenotazione
        );
    }
}