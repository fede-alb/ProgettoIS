package entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;

@Entity
public class Fila {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int posizione;

    @OneToMany(mappedBy = "fila")
    private List<Ombrellone> ombrelloni = new ArrayList<>();

    public Fila() {}

    public Fila(int posizione,  List<Ombrellone> ombrelloni) {
        this.posizione = posizione;
        this.ombrelloni = ombrelloni;
    }

    public int getPosizione() {
        return posizione;
    }

    public List<Ombrellone> getOmbrelloni() {
        return ombrelloni;
    }

    public void aggiungiOmbrellone(Ombrellone ombrellone) {
        this.ombrelloni.add(ombrellone);
    }
}
