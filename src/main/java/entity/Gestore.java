package entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Gestore")
public class Gestore extends Utente {
    public Gestore() {
        super();
    }

    public Gestore(String nome, String cognome, String email, String password, String telefono) {
        super(nome, cognome, email, password, telefono);
    }
}
