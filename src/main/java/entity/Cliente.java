package entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("Cliente")
public class Cliente extends Utente {
    @OneToMany(mappedBy = "cliente")
    private final List<Prenotazione> prenotazioni = new ArrayList<>();

    public Cliente() {}

    public Cliente(String nome, String cognome, String email, String password, String telefono) {
        super(nome, cognome, email, password, telefono);
    }

    public List<Prenotazione> getPrenotazioni() {
        return prenotazioni;
    }

    public void aggiungiPrenotazione(Prenotazione prenotazione) {
        this.prenotazioni.add(prenotazione);
    }
}
