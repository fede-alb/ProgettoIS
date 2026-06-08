package controller;

import dto.OmbrelloneDTO;

import entity.Stabilimento;

import java.util.*;

public class ConfiguraStabilimentoController {

    public static List<Map<OmbrelloneDTO, Boolean>> visualizzaOmbrelloni(Date data) {
        // STUB
        List<Map<OmbrelloneDTO, Boolean>> list = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            list.add(inserisciFilaOmbrelloni(i));
        }
        return list;
    }

    private static Map<OmbrelloneDTO, Boolean> inserisciFilaOmbrelloni(int offset) {
        Map<OmbrelloneDTO, Boolean> fila = new LinkedHashMap<>();
        for(int i = 1 + offset * 10; i <= (offset + 1) * 10;  i++) {
            if(i % 2 == 0) { fila.put(new OmbrelloneDTO(i), true); }
            else { fila.put(new OmbrelloneDTO(i), false); }
        }
        return fila;
    }

    //Per ora commentato, in seguito forse useremo lui
    /*public boolean configuraStabilimento(int nPrimaFila,
                                         int nFilaIntermedia,
                                         int nUltimaFila,
                                         Map<String, Integer> servizi) {
        return Stabilimento.getInstanza()
                .configuraStabilimento(nPrimaFila, nFilaIntermedia, nUltimaFila, servizi);
    }*/

    //Temporaneo, per test
    public boolean configuraStabilimento(int nPrimaFila,
                                         int nFilaIntermedia,
                                         int nUltimaFila,
                                         Map<String, Integer> servizi) {
        return true;
    }
}
