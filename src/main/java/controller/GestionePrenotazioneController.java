package controller;

import entity.*;
import dto.UtenteDTO;
import dto.PrenotazioneDTO;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Set;

public class GestionePrenotazioneController {
    public static long getIdClienteCorrente() {
        UtenteDTO utenteCorrente = SessioneUtente.getUtenteCorrente();

        if (utenteCorrente != null && "Cliente".equalsIgnoreCase(utenteCorrente.getRuolo())) {
            return utenteCorrente.getIdUtente();
        }

        return 0;
    }

    public static List<Prenotazione> consultaElencoPrenotazioni() {
        return gp.cercaPerCampi(Prenotazione.class, Map.of());
    }

    public static List<PrenotazioneDTO> consultaElencoPrenotazioniDTO() {
        List<Prenotazione> prenotazioni = consultaElencoPrenotazioni();

        return prenotazioni.stream()
                .map(this::convertiInDTO)
                .toList();
    }

    public static List<PrenotazioneDTO> consultaPrenotazioniPerDataDTO(LocalDate giorno) {
        List<Prenotazione> prenotazioni = consultaPrenotazioniPerData(giorno);

        return prenotazioni.stream()
                .map(this::convertiInDTO)
                .toList();
    }

    public static Prenotazione consultaPrenotazione(Long idPrenotazione) {
        return gp.trovaPerId(
                Prenotazione.class,
                idPrenotazione
        );
    }


    public static List<Prenotazione> consultaPrenotazioniPerData(LocalDate giorno) {
        return gp.cercaPerCampi(
                Prenotazione.class,
                Map.of("data", giorno)
        );
    }

    public static int effettuaPrenotazione(Date data, int idOmbrellone, List<Integer> idServizi, long idCliente) {
        Stabilimento stabilimento = Stabilimento.getIstanza();
        Ombrellone ombrellone = stabilimento.getOmbrelloneByID(idOmbrellone);
        List<ServizioAggiuntivo> servizi = stabilimento.getServiziByID(idServizi);
        RegistroUtenti registro = RegistroUtenti.getIstanza();
        Cliente cliente = (Cliente) registro.getUtente(idCliente);

        return stabilimento.effettuaPrenotazione(data, ombrellone, servizi, cliente);
    }


    public static PrenotazioneDTO consultaPrenotazioneDTO(Long idPrenotazione) {
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
        public static List<PrenotazioneDTO> ottieniPrenotazioniUtente(long idCliente){
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

        private static String convertiServiziInStringa(Set<ServizioAggiuntivo> servizi) {  //da capire come risolvere il problema query lazy
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

    public static boolean confermaAnnullamentoPrenotazione(PrenotazioneDTO dto) {
        long id = dto.getIdPrenotazione();
        return Stabilimento.getIstanza().annullaPrenotazione(id);
    }

    private static PrenotazioneDTO convertiInDTO(Prenotazione e) {
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