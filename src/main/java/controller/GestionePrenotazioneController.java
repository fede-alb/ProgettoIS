package controller;

import database.GestorePersistenza;
import entity.*;
import dto.UtenteDTO;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class GestionePrenotazioneController {

    private final GestorePersistenza gp = new GestorePersistenza();

    // Ti restituisce quale cliente è loggato in questo momento
    public static long getIdClienteCorrente() {
        UtenteDTO utenteCorrente = SessioneUtente.getUtenteCorrente();

        if (utenteCorrente != null && "Cliente".equalsIgnoreCase(utenteCorrente.getRuolo())) {
            return utenteCorrente.getIdUtente();
        }

        return 0;
    }

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

    public static boolean effettuaPrenotazione(Date data, int idOmbrellone, List<Integer> idServizi, long idCliente) {
        Stabilimento stabilimento = Stabilimento.getIstanza();
        Ombrellone ombrellone = stabilimento.getOmbrelloneByID(idOmbrellone);
        List<ServizioAggiuntivo> servizi = stabilimento.getServiziByID(idServizi);
        RegistroUtenti registro = RegistroUtenti.getIstanza();
        Cliente cliente = (Cliente) registro.getUtente(idCliente);

        return stabilimento.effettuaPrenotazione(data, ombrellone, servizi, cliente);
    }
}