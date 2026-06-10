package controller;

import database.GestorePersistenza;
import dto.PrenotazioneDTO;
import entity.Prenotazione;
import entity.ServizioAggiuntivo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConsultaPropriePrenotazioniController {
    private final GestorePersistenza gp = new GestorePersistenza();

    public List<PrenotazioneDTO> ottieniPrenotazioniUtente(int idCliente){
        List<PrenotazioneDTO> listaDTO = new ArrayList<>();

        List<Prenotazione> entities = gp.cercaPerCampi(
                Prenotazione.class,
                Map.of("cliente.id", idCliente) // da trovare campo utente
        );

        for(Prenotazione e : entities) {
            String serviziString = convertiServiziInStringa(e.getServizi()); // da capire perché

            PrenotazioneDTO dto = new PrenotazioneDTO(
                    e.getIdPrenotazione(),
                    e.getData(),
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
    private String convertiServiziInStringa(Set<ServizioAggiuntivo> servizi) {
        if (servizi == null || servizi.isEmpty()) return "Nessuno";
        List<String> nomi = new ArrayList<>();
        for (ServizioAggiuntivo s : servizi) {
            nomi.add(s.getDescrizione());
        }
        return String.join(", ", nomi);
    }
    }
