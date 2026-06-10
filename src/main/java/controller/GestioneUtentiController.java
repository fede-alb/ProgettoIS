package controller;

import entity.RegistroUtenti;
import entity.Utente;

public class GestioneUtentiController {

    public Utente accesso(String email, String password) {
        return RegistroUtenti.getIstanza().accedi(email, password);
    }

    public static boolean registrazione(String nome,
                                 String cognome,
                                 String email,
                                 String password,
                                 String telefono,
                                 String ruolo) {
        return RegistroUtenti.getIstanza()
                .registraUtente(nome, cognome, email, password, telefono, ruolo);
    }

    public static boolean primoAvvio() {
        return !RegistroUtenti.getIstanza().esisteAlmenoUnGestore();
    }

    public static boolean emailGiaRegistrata(String email) {
        return RegistroUtenti.getIstanza().emailGiaRegistrata(email);
    }
}