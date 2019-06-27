package fi.academy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
class Station {
    String stationName;
    String stationShortCode;

    public String getStationName() {
        return stationName;
    }

    public String getStationShortCode() {
        return stationShortCode;
    }
}
