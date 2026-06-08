package dto;

public class OmbrelloneMappaDTO {
    private final int numero;
    private final boolean occupato;

    public OmbrelloneMappaDTO(int numero,  boolean occupato) {
        this.numero = numero;
        this.occupato = occupato;
    }

    public int getNumero() {
        return numero;
    }

    public boolean isOccupato() {
        return occupato;
    }
}
