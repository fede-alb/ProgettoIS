package dto;

import java.time.LocalDate;

public class PrenotazioneDTO {
    private final long idPrenotazione;
    private final LocalDate dataPrenotazione;
    private final String nomeCliente;
    private final String cognomeCliente;
    private final FilaDTO fila;
    private final int posto;
    private final String serviziAggiuntivi;
    private final int costo;
    private final String stato;

    public PrenotazioneDTO(long idPrenotazione, LocalDate dataPrenotazione,String nomeCliente, String cognomeCliente, FilaDTO fila, int posto, String serviziAggiuntivi, int costo, String stato) {
        this.idPrenotazione = idPrenotazione;
        this.dataPrenotazione = dataPrenotazione;
        this.nomeCliente = nomeCliente;
        this.cognomeCliente = cognomeCliente;
        this.fila = fila;
        this.posto = posto;
        this.serviziAggiuntivi = serviziAggiuntivi;
        this.costo = costo;
        this.stato = stato;
    }

    public long getIdPrenotazione() {
        return idPrenotazione;
    }
    public LocalDate getDataPrenotazione() {
        return dataPrenotazione;
    }
    public FilaDTO getFila() {return fila;}
    public int getPosto() {
        return posto;
    }
    public String getServiziAggiuntivi() {return serviziAggiuntivi;}
    public int getCosto() {
        return costo;
    }
    public String getStato() {
        return stato;
    }
    public String getNomeCliente() { return nomeCliente; }
    public String getCognomeCliente() { return cognomeCliente; }
}
