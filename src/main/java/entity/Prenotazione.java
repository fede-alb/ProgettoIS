package entity;

import jakarta.persistence.*;

import java.util.Set;
import java.time.LocalDate;

@Entity
public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPrenotazione;

    private LocalDate data;
    private int prezzo;
    private StatoPrenotazione stato;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
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

    public Prenotazione(LocalDate data, Ombrellone ombrellone, Set<ServizioAggiuntivo> servizi,  Cliente cliente) {
        this.data = data;
        this.ombrellone = ombrellone;
        this.servizi = servizi;
        this.stato = StatoPrenotazione.PENDING;
        this.prezzo = 0;
        this.cliente = cliente;
    }

    public Long getIdPrenotazione() {
        return idPrenotazione;
    }

    public LocalDate getData() {
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

    public void setStato() {
        this.stato = StatoPrenotazione.PRENOTATA;
    }

    public int getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(int prezzo) {
        this.prezzo = prezzo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    @Override
    public String toString() {
        return "ID: " + idPrenotazione + ", data: " + data + ", stato: " + stato;
    }
}
