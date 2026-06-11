package controller;

import database.GestorePersistenza;
import entity.*;
import dto.UtenteDTO;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import dto.PrenotazioneDTO;
import java.util.ArrayList;
import java.util.Set;

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

    public List<PrenotazioneDTO> consultaElencoPrenotazioniDTO() {

        List<Prenotazione> prenotazioni =
                consultaElencoPrenotazioni();

        return prenotazioni.stream()
                .map(this::convertiInDTO)
                .toList();
    }

    public List<PrenotazioneDTO> consultaPrenotazioniPerDataDTO(LocalDate giorno) {

        List<Prenotazione> prenotazioni =
                consultaPrenotazioniPerData(giorno);

        return prenotazioni.stream()
                .map(this::convertiInDTO)
                .toList();
    }

    public Prenotazione consultaPrenotazione(Long idPrenotazione) {
        return gp.trovaPerId(
                Prenotazione.class,
                idPrenotazione
        );
    }


    public List<Prenotazione> consultaPrenotazioniPerData(LocalDate giorno) {

        return gp.cercaPerCampi(
                Prenotazione.class,
                Map.of("data", giorno)
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


    public PrenotazioneDTO consultaPrenotazioneDTO(Long idPrenotazione) {

        Prenotazione e = consultaPrenotazione(idPrenotazione);

        String serviziString = convertiServiziInStringa(e.getServizi());

        return new PrenotazioneDTO(
                e.getIdPrenotazione(),
                e.getData(),
                e.getCliente().getNome(),
                e.getCliente().getCognome(),
                e.getOmbrellone().getFila(),
                e.getOmbrellone().getNumero(),
                serviziString,
                e.getPrezzo(),
                e.getStato().toString()
        );
    }
        public List<PrenotazioneDTO> ottieniPrenotazioniUtente(long idCliente){
            List<PrenotazioneDTO> listaDTO = new ArrayList<>();

            List<Prenotazione> entities = Stabilimento.getIstanza().ottieniPrenotazioniDiCliente(idCliente);

            for(Prenotazione e : entities) {
                String serviziString = convertiServiziInStringa(e.getServizi());

                PrenotazioneDTO dto = new PrenotazioneDTO(
                        e.getIdPrenotazione(),
                        e.getData(),
                        e.getCliente().getNome(),
                        e.getCliente().getCognome(),
                        e.getOmbrellone().getFila(),
                        e.getOmbrellone().getNumero(),
                        serviziString,
                        e.getPrezzo(),
                        e.getStato().toString()
                );
                listaDTO.add(dto);
            }
            return listaDTO;
        }
        private String convertiServiziInStringa(Set<ServizioAggiuntivo> servizi) {  //da capire come risolvere il problema query lazy
            /*
            if (servizi == null || servizi.isEmpty()) return "Nessuno";
            List<String> nomi = new ArrayList<>();
            for (ServizioAggiuntivo s : servizi) {
                nomi.add(s.getDescrizione());
            }
            return String.join(", ", nomi);

             */
            return "nessuno";
        }

    public boolean confermaAnnullamentoPrenotazione(PrenotazioneDTO dto) {

        long id = dto.getIdPrenotazione();

        boolean rimosso = Stabilimento.getIstanza().annullaPrenotazione(id);
        System.out.println("[Database]: Prenotazione rimossa dal sistema.");
        return true;
    }



    private PrenotazioneDTO convertiInDTO(Prenotazione e) {

        String serviziString = convertiServiziInStringa(e.getServizi());

        return new PrenotazioneDTO(
                e.getIdPrenotazione(),
                e.getData(),
                e.getCliente().getNome(),
                e.getCliente().getCognome(),
                e.getOmbrellone().getFila(),
                e.getOmbrellone().getNumero(),
                serviziString,
                e.getPrezzo(),
                e.getStato().toString()
        );
    }
}