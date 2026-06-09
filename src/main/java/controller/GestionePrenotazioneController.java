package controller;

import database.GestorePersistenza;
import entity.Prenotazione;
import java.util.Calendar;
import java.util.Date;
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


    public List<Prenotazione> consultaPrenotazioniPerData(Date giorno) {

        Calendar cal = Calendar.getInstance();

        cal.setTime(giorno);

        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        Date inizio = cal.getTime();

        cal.add(Calendar.DAY_OF_MONTH, 1);

        Date fine = cal.getTime();

        return gp.cercaPerIntervallo(
                Prenotazione.class,
                "data",
                inizio,
                fine
        );
    }
}