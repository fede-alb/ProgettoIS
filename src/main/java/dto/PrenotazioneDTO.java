package dto;

import entity.Fila;
import entity.Ombrellone;


import java.time.LocalDate;

public class PrenotazioneDTO {
    private final long idPrenotazione;
    private final LocalDate dataPrenotazione;
   // private final String nomeCliente;
    //private final String cognomeCliente;
    private final Fila fila;
    private final int posto;
    private final String serviziAggiuntivi;
    private final int costo;
    private final String stato;

    //definisco il costruttore
    //,String nomeCliente,String cognomeCliente,
    public PrenotazioneDTO(long idPrenotazione, LocalDate dataPrenotazione,  Fila fila, int posto, String serviziAggiuntivi, int costo, String stato) {
        this.idPrenotazione = idPrenotazione;
        this.dataPrenotazione = dataPrenotazione;
       // this.nomeCliente = nomeCliente;
        //this.cognomeCliente = cognomeCliente;
        this.fila = fila;
        this.posto = posto;
        this.serviziAggiuntivi = serviziAggiuntivi;
        this.costo = costo;
        this.stato = stato;
    }

    //definisco i metodi get
    public long getIdPrenotazione() {
        return idPrenotazione;
    }
    public LocalDate getDataPrenotazione() {
        return dataPrenotazione;
    }
    public Fila getFila() {return fila;}
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
    //public String getNomeCliente() { return nomeCliente; }
    //public String getCognomeCliente() { return cognomeCliente; }

}
