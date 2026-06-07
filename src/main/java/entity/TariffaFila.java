package entity;

import jakarta.persistence.*;

@Entity
public class TariffaFila extends Tariffa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long idTariffaFila;

    public TariffaFila() {
        super();
    }

    public TariffaFila(int importo, PeriodoTariffa periodo) {
        super(importo, periodo);
    }
}
