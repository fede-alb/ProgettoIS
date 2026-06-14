package entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrenotazioneTest {
    private Prenotazione prenotazione;

    @BeforeEach
    void setUp() {
        prenotazione = new Prenotazione();
    }

    @Test
    void inserimentoPrezzo() {
        prenotazione.setPrezzo(50);
        assertEquals(50, prenotazione.getPrezzo());
    }

    @Test
    void impostazioneStato() {
        prenotazione.setStato();
        assertEquals(StatoPrenotazione.PRENOTATA, prenotazione.getStato());
    }
}