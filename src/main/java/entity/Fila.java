package entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class Fila {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int posizione;

    @OneToMany(mappedBy = "fila")
    private List<Ombrellone> ombrelloni = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "ref_fila")
    private List<TariffaFila> tariffe = new ArrayList<>();

    public Fila() {}

    public Fila(int posizione,  List<Ombrellone> ombrelloni, List<TariffaFila> tariffe) {
        this.posizione = posizione;
        this.ombrelloni = ombrelloni;
        this.tariffe = tariffe;
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

    public List<TariffaFila> getTariffe() {
        return tariffe;
    }

    public void aggiungiTariffa(TariffaFila tariffa) {
        if(this.tariffe.size() >= 2) {
            throw new IllegalStateException("Una fila non può avere più di 2 tariffe.");
        }
        this.tariffe.add(tariffa);
    }
}
