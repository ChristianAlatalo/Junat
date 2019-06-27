package fi.academy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

class JSON {

    static List palauttaaListanJSONsta(String osoite, Class luokka) {
        try {
            URL url = new URL(osoite);
            ObjectMapper mapper = new ObjectMapper();
            CollectionType tarkempiListanTyyppi = mapper.getTypeFactory().constructCollectionType(ArrayList.class, luokka);
            return mapper.readValue(url, tarkempiListanTyyppi);

        } catch (Exception ex) {
            return new ArrayList();
        }
    }
}
