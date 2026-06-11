package dto;

import java.util.ArrayList;
import java.util.List;

public class FilaDTO {
    private final int posizione;
    private final List<OmbrelloneDTO> ombrelloni = new ArrayList<>();

    public FilaDTO(int posizione) {
        this.posizione = posizione;
    }

    public int getPosizione() {
        return posizione;
    }

    public List<OmbrelloneDTO> getOmbrelloni() {
        return ombrelloni;
    }

    public void addOmbrellone(OmbrelloneDTO ombrellone) {
        ombrelloni.add(ombrellone);
    }
}
