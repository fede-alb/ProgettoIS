package entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPrenotazione;

    private Date data;
    private int prezzo;
    private StatoPrenotazione stato;

    @ManyToMany
    @JoinTable(
            name = "prenotazione_servizio",
            joinColumns = @JoinColumn(name = "idPrenotazione"),
            inverseJoinColumns = @JoinColumn(name = "idServizio")
    )
    private Set<ServizioAggiuntivo> servizi;

    @ManyToOne
    @JoinColumn(name = "ombrellone")
    private Ombrellone ombrellone;

    @ManyToOne
    @JoinColumn(name = "ref_cliente")
    private Cliente cliente;

    public Prenotazione() {}

    public Prenotazione(Date data, Ombrellone ombrellone, Set<ServizioAggiuntivo> servizi,  Cliente cliente) {
        this.data = data;
        this.ombrellone = ombrellone;
        this.servizi = servizi;
        this.stato = StatoPrenotazione.PENDING;
        this.prezzo = 50; // Prezzo casuale, per ora
        // NOTA: Chiamerà il metodo privato calcolaTariffa()
        // calcolaTariffa() { this.prezzo = ...; }
        this.cliente = cliente;
    }

    public Long getIdPrenotazione() {
        return idPrenotazione;
    }

    public Date getData() {
        return data;
    }

    public Ombrellone getOmbrellone() {
        return ombrellone;
    }

    public Set<ServizioAggiuntivo> getServizi() {
        return servizi;
    }

    public StatoPrenotazione getStato() {
        return stato;
    }

    public int getPrezzo() {
        return prezzo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    @Override
    public String toString() {
        return "ID: " + idPrenotazione + ", data: " + data + ", stato: " + stato;
    }
}
