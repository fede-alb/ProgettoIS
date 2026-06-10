package entity;

public class SessioneUtente {

    private static Utente utenteCorrente;

    public static void impostaUtenteCorrente(Utente utente) {
        utenteCorrente = utente;
    }

    public static Utente getUtenteCorrente() {
        return utenteCorrente;
    }

    public static Long getIdUtenteCorrente() {
        if (utenteCorrente == null) return null;
        return utenteCorrente.getIdUtente();
    }

    public static boolean isLoggato() {
        return utenteCorrente != null;
    }

    //Comodo
    public static Cliente getClienteCorrente() {
        if (utenteCorrente instanceof Cliente) {
            return (Cliente) utenteCorrente;
        }
        return null;
    }

    public static void logout() {
        utenteCorrente = null;
    }
}