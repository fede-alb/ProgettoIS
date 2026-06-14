package controller;

import dto.FilaDTO;
import entity.*;
import dto.UtenteDTO;
import dto.PrenotazioneDTO;
import dto.SessioneUtente;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
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

    public static List<PrenotazioneDTO> consultaElencoPrenotazioniDTO() {
        List<Prenotazione> prenotazioni = Stabilimento.getIstanza().ottieniPrenotazioni();

        return prenotazioni.stream()
                .map(GestionePrenotazioneController::convertiInDTO)
                .toList();
    }

    public static List<PrenotazioneDTO> consultaPrenotazioniPerDataDTO(LocalDate giorno) {
        List<Prenotazione> prenotazioni = Stabilimento.getIstanza().ottieniPrenotazioniPerData(giorno);

        return prenotazioni.stream()
                .map(GestionePrenotazioneController::convertiInDTO)
                .toList();
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
        Prenotazione e = Stabilimento.getIstanza().ottieniPrenotazione(idPrenotazione);
        String serviziString = convertiServiziInStringa(e.getServizi());
        FilaDTO filaDTO = new FilaDTO(e.getOmbrellone().getFila().getPosizione());

        return new PrenotazioneDTO(
                e.getIdPrenotazione(),
                e.getData(),
                e.getCliente().getNome(),
                e.getCliente().getCognome(),
                filaDTO,
                e.getOmbrellone().getNumero(),
                serviziString,
                e.getPrezzo(),
                e.getStato().toString()
        );
    }

    public static List<PrenotazioneDTO> ottieniPrenotazioniUtente(long idCliente){
        List<PrenotazioneDTO> listaDTO = new ArrayList<>();
        List<Prenotazione> prenotazioni = Stabilimento.getIstanza().ottieniPrenotazioniCliente(idCliente);

        for(Prenotazione e : prenotazioni) {
            String serviziString = convertiServiziInStringa(e.getServizi());
            FilaDTO filaDTO = new FilaDTO(e.getOmbrellone().getFila().getPosizione());

            PrenotazioneDTO dto = new PrenotazioneDTO(
                    e.getIdPrenotazione(),
                    e.getData(),
                    e.getCliente().getNome(),
                    e.getCliente().getCognome(),
                    filaDTO,
                    e.getOmbrellone().getNumero(),
                    serviziString,
                    e.getPrezzo(),
                    e.getStato().toString()
            );
            listaDTO.add(dto);
        }
        return listaDTO;
    }

    private static String convertiServiziInStringa(Set<ServizioAggiuntivo> servizi) {
        if (servizi == null || servizi.isEmpty()) return "Nessuno";
        List<String> nomi = new ArrayList<>();
        for (ServizioAggiuntivo s : servizi) {
            nomi.add(s.getDescrizione());
        }
        return String.join(", ", nomi);
    }

    public static boolean confermaAnnullamentoPrenotazione(PrenotazioneDTO dto) {
        long id = dto.getIdPrenotazione();
        return Stabilimento.getIstanza().annullaPrenotazione(id);
    }

    private static PrenotazioneDTO convertiInDTO(Prenotazione e) {
        String serviziString = convertiServiziInStringa(e.getServizi());
        FilaDTO filaDTO = new FilaDTO(e.getOmbrellone().getFila().getPosizione());

        return new PrenotazioneDTO(
                e.getIdPrenotazione(),
                e.getData(),
                e.getCliente().getNome(),
                e.getCliente().getCognome(),
                filaDTO,
                e.getOmbrellone().getNumero(),
                serviziString,
                e.getPrezzo(),
                e.getStato().toString()
        );
    }
}