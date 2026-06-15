package entity;

import jakarta.persistence.*;

@Entity
public class Ombrellone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int numero;

    @ManyToOne
    @JoinColumn(name = "fila")
    private Fila fila;

    public Ombrellone() {}

    public Ombrellone(Fila fila) {
        this.fila = fila;
    }

    public int getNumero() {
        return numero;
    }

    public Fila getFila() {
        return fila;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ombrellone ombrellone = (Ombrellone) o;
        return numero == ombrellone.numero;
    }

    @Override
    public int hashCode() {
        return numero;
    }
}
