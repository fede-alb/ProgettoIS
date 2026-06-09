package entity;

import database.GestorePersistenza;

// Verrà usata allo stesso modo di Stabilimento:
// quest'ultima è usata per accedere al DB per prenotazioni, file/ombrelloni, servizi e tariffe
// mentre registroUtenti accede e registra nuovi utenti nel DB
public class RegistroUtenti {
    private static RegistroUtenti istanza;
    private final GestorePersistenza gestorePersistenza;

    private RegistroUtenti() { gestorePersistenza = new GestorePersistenza(); }

    public static RegistroUtenti getInstanza() {
        if (istanza == null) istanza = new RegistroUtenti();
        return istanza;
    }
}
