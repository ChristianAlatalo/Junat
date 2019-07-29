package fi.academy;


import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class TrainMain {

    List<Station> asemalista;

    private static final String menuteksti = "\nAnna vaihtoehto:\n"
            + "1: Hae seuraavat junat\n"
            + "2: Hae junan kulkutiedot\n"
            + "3: Onko junani matkalla Turkuun?\n"
            + "0: Lopeta";

    private void kaynnista() {
        Scanner lukija = new Scanner(System.in);
        String asemaUrl = "https://rata.digitraffic.fi/api/v1/metadata/stations";
        List<Station> asemaLista = JSON.palauttaaListanJSONsta(asemaUrl, Station.class);

        outerloop:
        for (; ; ) {
            System.out.println(menuteksti);
            String vastaus = lukija.nextLine().trim();

            switch (vastaus) {
                case "1":
                    System.out.println("Anna lähtöasema:");
                    String lahtoAsema = lukija.nextLine().trim();
                    System.out.println("Anna määränpää:");
                    String maaraAsema = lukija.nextLine().trim();
                    tulostaaReitinJunienLahtoJaSaapumisajat(lahtoAsema, maaraAsema);
                    break;
                case "2":
                    System.out.println("Anna junan numero:");
                    String junaNro1 = lukija.nextLine().trim();
                    tulostaaJunareitinKaikkiLahtoJaSaapumisajat(junaNro1);
                    break;
                case "3":
                    System.out.println("Anna junan numero:");
                    String junaNro2 = lukija.nextLine().trim();
                    onkoJunaniMatkallaTurkuun(junaNro2);
                    break;
                case "4":
                    System.out.println("Juna Tylypahkaan lähtee laiturilta 9 3/4!");
                    break;
                case "0":

                    System.out.println("\n\n____\n" +
                            "|DD|____T_\n" +
                            "|_ |_____|<\n" +
                            "  @-@-@-oo\\\n\n");
                    break outerloop;
            }
        }
    }

    // CASE 1
    private void tulostaaReitinJunienLahtoJaSaapumisajat(String lahtoAsema, String maaraAsema) {
        // Tulostaa valitun reitin kaikki junat, ja vain niiden lähtöajan lähtöasemalta sekä saapumisajan määräasemalle.
        // Muodostaa URLin, joka kertoo junat lähtöasemalta määränpäähän.

        String lahtoAsemaLyhyt = palauttaaAsemanNimenLyhytkoodina(lahtoAsema);
        String maaraAsemaLyhyt = palauttaaAsemanNimenLyhytkoodina(maaraAsema);

        if (lahtoAsemaLyhyt.equals("") && (maaraAsemaLyhyt.equals(""))) {
            System.out.println("Annettua lähtö- ja määräasemaa ei löytynyt");
            return;
        } else if (lahtoAsemaLyhyt.equals("")) {
            System.out.println("Annettua lähtöasemaa ei löytynyt");
            return;
        } else if (maaraAsemaLyhyt.equals("")) {
            System.out.println("Annettua määränpäätä ei löytynyt");
            return;
        }

        String junaUrl = "https://rata.digitraffic.fi/api/v1/live-trains/station/" + lahtoAsemaLyhyt + "/" + maaraAsemaLyhyt;
        List<Train> junalista = JSON.palauttaaListanJSONsta(junaUrl, Train.class);

        if ((junalista.isEmpty())) {
            System.out.println("Halutulle reitille ei löytynyt junia");
            return;
        }

        for (Train juna : junalista) {
            int vika = (juna.getTimeTableRows().size() - 1);
            System.out.println("Junanumero: " + juna.getTrainNumber());
            String junaSaapuu = String.format("Lähtee asemalta %-15s %-13s %s", lahtoAsema, "lähtöaika:", juna.getTimeTableRows().get(0).getScheduledTime());
            String junaLahtee = String.format("Saapuu asemalle %-15s %-13s %s", maaraAsema, "saapumisaika:", juna.getTimeTableRows().get(vika).getScheduledTime());
            System.out.println(junaSaapuu);
            System.out.println(junaLahtee + "\n");
        }
    }

    // CASE 2
    private void tulostaaJunareitinKaikkiLahtoJaSaapumisajat(String junaNro) {
        // Tulostaa valitun junan kaikki pysäkit, sekä lähtö- ja saapumisajat niille.
        // Muodostaa URLin, joka kertoo annetun junalinjan vuorot TÄNÄÄN.

        if (!(onkoJunaAjossa(junaNro))) {
            System.out.println("Juna " + junaNro + " ei ole ajossa");
            return;
        }

        String url = "https://rata.digitraffic.fi/api/v1/trains/" + LocalDate.now() + "/" + junaNro;
        List<Train> junalista = JSON.palauttaaListanJSONsta(url, Train.class);

        if ((junalista.isEmpty())) {
            System.out.println("Juna " + junaNro + " ei ole liikenteessä.");
            return;
        }
        for (Train juna : junalista) {
            System.out.println("Junanumero: " + juna.getTrainNumber());
            for (TimeTableRow lista : juna.getTimeTableRows()) {
                if (lista.isTrainStopping() && lista.getType().equals("ARRIVAL")) {
                    String saapuuAsemalle = String.format("Saapuu asemalle %-15s %-12s %20s", palauttaaLyhytkoodistaAsemanNimen(lista.getStationShortCode()), "lähtöaika:", lista.getScheduledTime());
                    System.out.println(saapuuAsemalle);
                } else if (lista.isTrainStopping() && lista.getType().equals("DEPARTURE")) {
                    String lahteeAsemalta = String.format("Lähtee asemalta %-15s %-12s %19s", palauttaaLyhytkoodistaAsemanNimen(lista.getStationShortCode()), "saapumisaika:", lista.getScheduledTime());
                    System.out.println(lahteeAsemalta);
                }
            }
        }
    }

    // CASE 3
    private void onkoJunaniMatkallaTurkuun(String junaNro) {

        if (!(onkoJunaAjossa(junaNro))) {
            System.out.println("Juna " + junaNro + " ei ole ajossa.");
            return;
        }

        if (!(pysahtyykoJunaTurussa(junaNro))) {
            System.out.println("Juna " + junaNro + " ei pysähdy Turussa, kaikki hyvin!");
            return;
        }

        String url = "https://rata.digitraffic.fi/api/v1/train-locations/latest/" + junaNro;
        List<Train> junalista = JSON.palauttaaListanJSONsta(url, Train.class);

        if (junalista.isEmpty()) {
            System.out.println("Juna pysähtyy Turussa, mutta ei ole lähtenyt vielä. Ehdit hyvin poistua ennen matkan alkua.");
            return;
        }

        double junaLon = Double.parseDouble(junalista.get(0).getLocation().getCoordinates().get(0).toString());
        double junaLat = Double.parseDouble(junalista.get(0).getLocation().getCoordinates().get(1).toString());
        long etaisyysTurkuun = Math.round(palauttaaEtaisyydenTurkuun(junaLat, junaLon));

        if (etaisyysTurkuun >= 100) {
            System.out.println("Juna pysähtyy Turussa. Matkaa jäljellä " + etaisyysTurkuun + "km, vielä ehtii poistua!\nJunan nopeus on " + junalista.get(0).getSpeed() + "km/h");
        } else if (etaisyysTurkuun < 100 && etaisyysTurkuun >= 50) {
            System.out.println("Juna pysähtyy Turussa. Matkaa jäljellä " + etaisyysTurkuun + "km, nyt kannattaisi pitää kiirettä. Turku häämöttää!\nJunan nopeus on " + junalista.get(0).getSpeed() + "km/h");
        } else if (etaisyysTurkuun < 50 && etaisyysTurkuun >= 10) {
            System.out.println("Juna pysähtyy Turussa. Matkaa jäljellä " + etaisyysTurkuun + "km, nyt tarkkana!!\nJunan nopeus on " + junalista.get(0).getSpeed() + "km/h");
        } else if (etaisyysTurkuun < 10 && etaisyysTurkuun >= 4) {
            System.out.println("Juna pysähtyy Turussa. Matkaa jäljellä " + etaisyysTurkuun + "km, HYPPÄÄ NYT!!!\nJunan nopeus on " + junalista.get(0).getSpeed() + "km/h");
        } else if (etaisyysTurkuun < 4) {
            System.out.println("Juna on Turussa. Siirry rauhallisesti lähimpään vessaan ja lukitse ovi kunnes juna on poistunut Turusta. Älä kerro tapahtuneesta kenellekkään.");
        }
    }

    private boolean onkoJunaAjossa(String junaNro) {
        String url = "https://rata.digitraffic.fi/api/v1/trains/latest/" + junaNro;
        List<Train> junalista = JSON.palauttaaListanJSONsta(url, Train.class);

        if (junalista.isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean pysahtyykoJunaTurussa(String junaNro) {

        String url = "https://rata.digitraffic.fi/api/v1/trains/latest/" + junaNro;
        List<Train> junanAsemaLista = JSON.palauttaaListanJSONsta(url, Train.class);
        int i = 0;
        while (i < junanAsemaLista.get(0).getTimeTableRows().size()) {
            if (junanAsemaLista.get(0).getTimeTableRows().get(i).getStationShortCode().equals("TKU")) {
                return true;
            }
            i++;
        }
        return false;
    }


    private double palauttaaEtaisyydenTurkuun(double lat, double lon) {

        double turkuLat = 60.453832;
        double turkuLon = 22.253441;
        double theta = lon - turkuLon;
        double etaisyysTurusta = Math.sin((lat) * (Math.PI / 180.0)) * Math.sin((turkuLat) * (Math.PI / 180.0)) + Math.cos((lat) * (Math.PI / 180.0)) * Math.cos((turkuLat) * (Math.PI / 180.0)) * Math.cos((theta) * (Math.PI / 180.0));
        etaisyysTurusta = Math.acos(etaisyysTurusta) * 180.0 / Math.PI * 60 * 1.1515 * 1.609344;

        return etaisyysTurusta;
    }


    private String palauttaaAsemanNimenLyhytkoodina(String asemaNimi) {

        String asemaLyhyt = "";
        for (Station asema : this.asemalista) {
            if (asema.getStationName().toUpperCase().equals(asemaNimi.toUpperCase())) {
                asemaLyhyt = asema.getStationShortCode();
                return asemaLyhyt;
            }
        }
        return asemaLyhyt;
    }

    private String palauttaaLyhytkoodistaAsemanNimen(String asemanLyhytkoodi) {

        String asemanNimi = "";
        for (Station asema : this.asemalista) {
            if (asema.getStationShortCode().toUpperCase().equals(asemanLyhytkoodi.toUpperCase())) {
                asemanNimi = asema.getStationName();
                return asemanNimi;
            }
        }
        return asemanNimi;
    }

    private void lataaAsemalista() {
        String asemaUrl = "https://rata.digitraffic.fi/api/v1/metadata/stations";
        this.asemalista = JSON.palauttaaListanJSONsta(asemaUrl, Station.class);
    }

    public static void main(String[] args) {
        TrainMain train = new TrainMain();
        train.lataaAsemalista();
        train.kaynnista();
    }
}

