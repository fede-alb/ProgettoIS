package entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Tariffa {
    protected int importo;
    protected PeriodoTariffa periodo;

    public Tariffa() {}

    public Tariffa(int importo, PeriodoTariffa periodo) {
        this.importo = importo;
        this.periodo = periodo;
    }

    public int getImporto() {
        return importo;
    }

    public PeriodoTariffa getPeriodo() {
        return periodo;
    }
}
