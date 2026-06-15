package dto;

public class OmbrelloneDTO {
    private final int numero;
    private final boolean occupato;

    public OmbrelloneDTO(int numero, boolean occupato) {
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
