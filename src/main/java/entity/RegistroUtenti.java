package entity;

import database.GestorePersistenza;

import java.util.List;
import java.util.Map;

// Verrà usata allo stesso modo di Stabilimento:
// quest'ultima è usata per accedere al DB per prenotazioni, file/ombrelloni, servizi e tariffe
// mentre registroUtenti accede e registra nuovi utenti nel DB
public class RegistroUtenti {
    private static RegistroUtenti istanza;
    private final GestorePersistenza gestorePersistenza;

    private RegistroUtenti() { gestorePersistenza = new GestorePersistenza(); }

    public static RegistroUtenti getIstanza() {
        if (istanza == null) istanza = new RegistroUtenti();
        return istanza;
    }

    //Al primo avvio deve essere creato un gestore
    public boolean esisteAlmenoUnGestore() {
        List<Gestore> gestori = gestorePersistenza.cercaPerCampi(Gestore.class, Map.of());
        return gestori != null && !gestori.isEmpty();
    }

    public boolean emailGiaRegistrata(String email) {
        if (email == null || email.isBlank()) {
            return false;
        }

        Utente utente = gestorePersistenza.cercaPrimoPerCampi(
                Utente.class,
                Map.of("email", email.trim())
        );

        return utente != null;
    }

    public Utente accedi(String email, String password) {
        if (email == null || password == null) {
            return null;
        }

        return gestorePersistenza.cercaPrimoPerCampi(
                Utente.class,
                Map.of(
                        "email", email.trim(),
                        "password", password
                )
        );
    }

    public boolean registraUtente(String nome,
                                  String cognome,
                                  String email,
                                  String password,
                                  String telefono,
                                  String ruolo) {

        if (nome == null || cognome == null || email == null || password == null || telefono == null || ruolo == null) {
            return false;
        }

        nome = nome.trim();
        cognome = cognome.trim();
        email = email.trim();
        telefono = telefono.trim();
        ruolo = ruolo.trim();

        if (nome.isEmpty() || cognome.isEmpty() || email.isEmpty() || password.isEmpty() || telefono.isEmpty() || ruolo.isEmpty()) {
            return false;
        }

        if (emailGiaRegistrata(email)) {
            return false;
        }

        boolean esisteGestore = esisteAlmenoUnGestore();

        if (!esisteGestore && !ruolo.equalsIgnoreCase("Gestore")) {
            return false;
        }

        Utente nuovoUtente;

        if (ruolo.equalsIgnoreCase("Gestore")) {
            nuovoUtente = new Gestore(nome, cognome, email, password, telefono);
        } else if (ruolo.equalsIgnoreCase("Cliente")) {
            nuovoUtente = new Cliente(nome, cognome, email, password, telefono);
        } else {
            return false;
        }

        return gestorePersistenza.salva(nuovoUtente);
    }

    public Utente getUtente(long idUtente) {
        return gestorePersistenza.trovaPerId(Utente.class, idUtente);
    }
}
