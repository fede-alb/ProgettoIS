package controller;

import dto.FilaMappaDTO;
import dto.OmbrelloneMappaDTO;
import dto.ServizioAggiuntivoDTO;
import entity.*;

import java.util.*;

public class ConfiguraStabilimentoController {

    public static List<FilaMappaDTO> visualizzaOmbrelloni(Date data) {
        // Richiedo i dati Entity dallo Stabilimento
        Stabilimento stabilimento = Stabilimento.getInstanza();
        Map<Fila, Map<Ombrellone, Boolean>> mappa = stabilimento.visualizzaOmbrelloni(data);

        // Costruisco la mappa in DTO da passare al Boundary
        List<FilaMappaDTO> risultatoDTO = new ArrayList<>();
        for (Map.Entry<Fila, Map<Ombrellone, Boolean>> entryFila : mappa.entrySet()) {
            Fila fila = entryFila.getKey();
            FilaMappaDTO filaDTO = new FilaMappaDTO(fila.getPosizione());
            for (Map.Entry<Ombrellone, Boolean> entryOmb : entryFila.getValue().entrySet()) {
                Ombrellone ombrellone = entryOmb.getKey();
                Boolean occupato = entryOmb.getValue();
                filaDTO.addOmbrellone(new OmbrelloneMappaDTO(ombrellone.getNumero(), occupato));
            }
            risultatoDTO.add(filaDTO);
        }
        return risultatoDTO;
    }

    public static boolean configuraStabilimento(int nPrimaFila,
                                         int nFilaIntermedia,
                                         int nUltimaFila,
                                         Map<String, Integer> servizi) {
        return Stabilimento.getInstanza()
                .configuraStabilimento(nPrimaFila, nFilaIntermedia, nUltimaFila, servizi);
    }

    public static List<ServizioAggiuntivoDTO> getServizi() {
        Stabilimento stabilimento = Stabilimento.getInstanza();
        List<ServizioAggiuntivo> servizi = stabilimento.getServizi();
        List<ServizioAggiuntivoDTO> risultatoDTO = new ArrayList<>();
        for(ServizioAggiuntivo servizio : servizi) {
            risultatoDTO.add(new ServizioAggiuntivoDTO(
                    servizio.getId(),
                    servizio.getDescrizione(),
                    servizio.getDisponibilita()
            ));
        }
        return risultatoDTO;
    }

    public boolean isStabilimentoGiaConfigurato() {
        return Stabilimento.getInstanza().isGiaConfigurato();
    }
}
