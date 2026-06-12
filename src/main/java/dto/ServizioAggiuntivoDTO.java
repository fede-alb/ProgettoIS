package dto;

public class ServizioAggiuntivoDTO {
    private final Long idServizioAggiuntivo;
    private final String descrizione;
    private final int disponibilita;

    public ServizioAggiuntivoDTO(Long idServizioAggiuntivo, String descrizione, int disponibilita) {
        this.idServizioAggiuntivo = idServizioAggiuntivo;
        this.descrizione = descrizione;
        this.disponibilita = disponibilita;
    }

    public Long getIdServizioAggiuntivo() {
        return idServizioAggiuntivo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public int getDisponibilita() {
        return disponibilita;
    }
}
