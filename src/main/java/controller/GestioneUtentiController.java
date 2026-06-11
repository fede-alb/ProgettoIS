package controller;

import dto.UtenteDTO;
import entity.Gestore;
import entity.RegistroUtenti;
import entity.Utente;

public class GestioneUtentiController {

    public static UtenteDTO accesso(String email, String password) {
        Utente utente = RegistroUtenti.getIstanza().accedi(email, password);

        if (utente == null) {
            return null;
        }

        String ruolo;
        if (utente instanceof Gestore) {
            ruolo = "Gestore";
        } else {
            ruolo = "Cliente";
        }

        return new UtenteDTO(
                utente.getIdUtente(),
                utente.getNome(),
                utente.getCognome(),
                utente.getEmail(),
                utente.getTelefono(),
                ruolo
        );
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