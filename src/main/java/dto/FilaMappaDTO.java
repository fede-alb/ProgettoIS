package dto;

import java.util.ArrayList;
import java.util.List;

public class FilaMappaDTO {
    private final int posizione;
    private final List<OmbrelloneMappaDTO> ombrelloni = new ArrayList<>();

    public FilaMappaDTO(int posizione) {
        this.posizione = posizione;
    }

    public int getPosizione() {
        return posizione;
    }

    public List<OmbrelloneMappaDTO> getOmbrelloni() {
        return ombrelloni;
    }

    public void addOmbrellone(OmbrelloneMappaDTO ombrellone) {
        ombrelloni.add(ombrellone);
    }
}
