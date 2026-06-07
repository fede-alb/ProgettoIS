package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class TariffaServizio extends Tariffa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long idTariffaServizio;

    public TariffaServizio() {
        super();
    }

    public TariffaServizio(int importo, PeriodoTariffa periodo) {
        super(importo, periodo);
    }
}
