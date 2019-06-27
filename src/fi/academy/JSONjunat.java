package fi.academy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
class JSONjunat {

    static List lueJunanJSONData(String osoite) {
        try {
            URL url = new URL(osoite);
            ObjectMapper mapper = new ObjectMapper();
            CollectionType tarkempiListanTyyppi = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Juna.class);
            return mapper.readValue(url, tarkempiListanTyyppi);  // pelkkä List.class ei riitä tyypiksi*/

        } catch (Exception ex) {
            return new ArrayList();
        }
    }
}






