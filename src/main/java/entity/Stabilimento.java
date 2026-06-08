package entity;

import database.GestorePersistenza;

import java.util.ArrayList;
import java.util.Map;

public class Stabilimento {
    private static Stabilimento istanza;
    private final GestorePersistenza gestorePersistenza;

    private Stabilimento() {
        gestorePersistenza = new GestorePersistenza();
    }

    public static Stabilimento getInstanza() {
        if(istanza == null) {
            istanza = new Stabilimento();
        }
        return istanza;
    }

    public boolean configuraStabilimento(int nPrimaFila,
                                         int nFilaIntermedia,
                                         int nUltimaFila,
                                         Map<String, Integer> servizi) {

        Fila primaFila = new Fila(1, new ArrayList<>(), new ArrayList<>());
        boolean ok = gestorePersistenza.salva(primaFila);
        if (!ok) return false;

        for (int i = 0; i < nPrimaFila; i++) {
            Ombrellone ombrellone = new Ombrellone(0, primaFila);
            primaFila.aggiungiOmbrellone(ombrellone);
            ok = gestorePersistenza.salva(ombrellone);
            if (!ok) return false;
        }

        Fila filaIntermedia = new Fila(2, new ArrayList<>(), new ArrayList<>());
        ok = gestorePersistenza.salva(filaIntermedia);
        if (!ok) return false;

        for (int i = 0; i < nFilaIntermedia; i++) {
            Ombrellone ombrellone = new Ombrellone(0, filaIntermedia);
            filaIntermedia.aggiungiOmbrellone(ombrellone);
            ok = gestorePersistenza.salva(ombrellone);
            if (!ok) return false;
        }

        Fila ultimaFila = new Fila(3, new ArrayList<>(), new ArrayList<>());
        ok = gestorePersistenza.salva(ultimaFila);
        if (!ok) return false;

        for (int i = 0; i < nUltimaFila; i++) {
            Ombrellone ombrellone = new Ombrellone(0, ultimaFila);
            ultimaFila.aggiungiOmbrellone(ombrellone);
            ok = gestorePersistenza.salva(ombrellone);
            if (!ok) return false;
        }

        for (Map.Entry<String, Integer> entry : servizi.entrySet()) {
            ServizioAggiuntivo servizio = new ServizioAggiuntivo(
                    entry.getKey(),
                    entry.getValue(),
                    new ArrayList<>()
            );
            ok = gestorePersistenza.salva(servizio);
            if (!ok) return false;
        }

        return true;
    }
}
