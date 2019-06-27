package fi.academy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
class Location {
    String type;
    List<Double> coordinates;

    public String getType() {
        return this.type;
    }

    public List getCoordinates() {
        return this.coordinates;
    }
}
