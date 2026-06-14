package entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FilaTest {
    private Fila fila;

    @BeforeEach
    void setUp() {
        fila = new Fila();
    }

    @Test
    void inserimentoOmbrelloneIncrementaSize() {
        Ombrellone ombrellone = new Ombrellone();
        fila.aggiungiOmbrellone(ombrellone);
        assertEquals(1, fila.getOmbrelloni().size());
    }

    @Test
    void inserimentoTariffaIncrementaSize() {
        TariffaFila tariffa = new TariffaFila();
        fila.aggiungiTariffa(tariffa);
        assertEquals(1, fila.getTariffe().size());
    }

    @Test
    void inserimentoTerzaTariffaErrore() {
        for(int i = 0; i < 2; i++) {
            TariffaFila tariffa = new TariffaFila();
            fila.aggiungiTariffa(tariffa);
        }
        TariffaFila tariffa = new TariffaFila();
        IllegalStateException eccezione = assertThrows(
                IllegalStateException.class,
                () -> fila.aggiungiTariffa(tariffa)
        );
        assertEquals("Una fila non può avere più di 2 tariffe.", eccezione.getMessage());
    }
}