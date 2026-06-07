package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class Ombrellone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int numero;

    @ManyToOne
    @JoinColumn(name = "fila")
    private Fila fila;

    public Ombrellone() {}

    public Ombrellone(int numero, Fila fila) {
        this.numero = numero;
        this.fila = fila;
    }

    public int getNumero() {
        return numero;
    }

    public Fila getFila() {
        return fila;
    }
}
