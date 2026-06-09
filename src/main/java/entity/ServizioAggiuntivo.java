package entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ServizioAggiuntivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idServizioAggiuntivo;

    private String descrizione;
    private int disponibilita;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ref_servizio", nullable = false)
    private List<TariffaServizio> tariffe = new ArrayList<>();

    public ServizioAggiuntivo() {}

    public ServizioAggiuntivo(String descrizione,  int disponibilita, List<TariffaServizio> tariffe) {
        this.descrizione = descrizione;
        this.disponibilita = disponibilita;
        this.tariffe = tariffe;
    }

    public Long getId() {
        return idServizioAggiuntivo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public int getDisponibilita() {
        return disponibilita;
    }

    public List<TariffaServizio> getTariffe() {
        return tariffe;
    }

    public void aggiungiTariffa(TariffaServizio tariffa) {
        if(this.tariffe.size() >= 2) {
            throw new IllegalStateException("Un servizio non può avere più di 2 tariffe.");
        }
        this.tariffe.add(tariffa);
    }
}
