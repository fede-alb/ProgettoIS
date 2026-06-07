package entity;

import database.GestorePersistenza;

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
}
