package dto;

public class SessioneUtente {
    private static UtenteDTO utenteCorrente;

    private SessioneUtente() {}

    public static void impostaUtenteCorrente(UtenteDTO utente) {
        utenteCorrente = utente;
    }

    public static UtenteDTO getUtenteCorrente() {
        return utenteCorrente;
    }

    public static void pulisciSessione() {
        utenteCorrente = null;
    }

    public static boolean utenteLoggato() {
        return utenteCorrente != null;
    }
}
