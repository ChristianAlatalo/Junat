package fi.academy;


import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class TrainMain {

    private static final String menuteksti = "\nAnna vaihtoehto:\n"
            + "1: Hae seuraava juna\n"
            + "2: Hae seuraavat asemat\n"
            + "3: Matkalla Turkuun?\n"
            + "0: Lopeta";

    private void kaynnista() {
        Scanner lukija = new Scanner(System.in);

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
                    tulostaReitinJunienLahtoJaSaapumisajat(lahtoAsema, maaraAsema);
                    break;
                case "2":
                    System.out.println("Anna junan numero:");
                    String junaNro1 = lukija.nextLine().trim();
                    tulostaJunareitinKaikkiLahtoJaSaapumisajat(junaNro1);
                    break;
                case "3":
                    System.out.println("Anna junan numero:");
                    String junaNro2 = lukija.nextLine().trim();
                    tulostaEtaisyysNopeus(junaNro2);
                    break;
                case "4":
                    System.out.println("Juna Tylypahkaan lähtee laiturilta 9 3/4!");
                    break;
                case "0":
                    break outerloop;
            }
        }
    }

    // CASE 1
    private static void tulostaReitinJunienLahtoJaSaapumisajat(String lahtoAsema, String maaraAsema) {
        // Tulostaa valitun reitin kaikki junat, ja vain niiden lähtöajan lähtöasemalta sekä saapumisajan määräasemalle.
        // Muodostaa URLin, joka kertoo junat lähtöasemalta määränpäähän.

        String lahtoAsemaLyhyt = palauttaaAsemanNimenLyhytkoodina(lahtoAsema);
        String maaraAsemaLyhyt = palauttaaAsemanNimenLyhytkoodina(maaraAsema);

        if (lahtoAsemaLyhyt.equals("") || (maaraAsemaLyhyt.equals(""))) {
            System.out.println("Reittivalinta on virheellinen");
            return;
        }

            String junaUrl = "https://rata.digitraffic.fi/api/v1/live-trains/station/" + lahtoAsemaLyhyt + "/" + maaraAsemaLyhyt;
            List<Train> junalista = JSON.palauttaaListanJSONsta(junaUrl, Train.class);
            for (Train juna : junalista) {
                int vika = (juna.getTimeTableRows().size() - 1);
                System.out.println("Junanumero: " + juna.getTrainNumber());
                System.out.println("Lähtee asemalta " + lahtoAsema + ", lähtöaika: " + juna.getTimeTableRows().get(0).getScheduledTime());
                System.out.println("Saapuu asemalle " + maaraAsema + ", saapumisaika: " + juna.getTimeTableRows().get(vika).getScheduledTime());
                System.out.println("");
            }
        }

        // CASE 2
        private static void tulostaJunareitinKaikkiLahtoJaSaapumisajat (String junaNro){
            // Tulostaa valitun junan kaikki pysäkit, sekä lähtö- ja saapumisajat niille.
            // Muodostaa URLin, joka kertoo annetun junalinjan vuorot TÄNÄÄN.
            String url = "https://rata.digitraffic.fi/api/v1/trains/" + LocalDate.now() + "/" + junaNro;
            List<Train> junalista = JSON.palauttaaListanJSONsta(url, Train.class);
            for (Train juna : junalista) {
                System.out.println("Junanumero: " + juna.getTrainNumber());
                for (TimeTableRow lista : juna.getTimeTableRows()) {
                    if (lista.isTrainStopping() && lista.getType().equals("ARRIVAL")) {
                        System.out.println("Juna saapuu asemalle " + palauttaaLyhytkoodistaAsemanNimen(lista.getStationShortCode()) + " kello: " + lista.getScheduledTime());
                    } else if (lista.isTrainStopping() && lista.getType().equals("DEPARTURE")) {
                        System.out.println("Juna lähtee asemalta " + palauttaaLyhytkoodistaAsemanNimen(lista.getStationShortCode()) + " kello: " + lista.getScheduledTime());
                    }
                }
            }
        }

        private static void tulostaEtaisyysNopeus (String junaNro){
            String url = "https://rata.digitraffic.fi/api/v1/train-locations/latest/" + junaNro;
            List<Train> junalista = JSON.palauttaaListanJSONsta(url, Train.class);

            try {
                double junanLon = Double.parseDouble(junalista.get(0).getLocation().getCoordinates().get(0).toString());
                double junanLat = Double.parseDouble(junalista.get(0).getLocation().getCoordinates().get(1).toString());
                long etaisyysTurkuun = Math.round(etaisyysTurkuun(junanLat, junanLon));
                if (pysahtyykoTurussa(junaNro) && etaisyysTurkuun > 100) {
                    System.out.println("Juna pysähtyy Turussa. Matkaa jäljellä " + etaisyysTurkuun + "km, Vielä ehtii poistua!");
                } else if (pysahtyykoTurussa(junaNro) && etaisyysTurkuun < 100) {
                    System.out.println("Juna pysähtyy Turussa. Matkaa jäljellä " + etaisyysTurkuun + "km");
                } else if (pysahtyykoTurussa(junaNro) && etaisyysTurkuun < 50) {
                    System.out.println("Juna pysähtyy Turussa. Matkaa jäljellä " + etaisyysTurkuun + "km");
                } else if (pysahtyykoTurussa(junaNro) && etaisyysTurkuun < 10) {
                    System.out.println("Juna pysähtyy Turussa. Matkaa jäljellä " + etaisyysTurkuun + "km, HYPPÄÄ NYT (junan nopeus on " + junalista.get(0).getSpeed() + "km/h");
                } else if (pysahtyykoTurussa(junaNro) && etaisyysTurkuun < 4) {
                    System.out.println("Juna on Turussa. Siirry lähimpään vessaan, avaa \"in case of Turku\"-pakkaus ja nauti syanidikapseli");
                } else {
                    System.out.println("Juna ei pysähdy Turussa, kaikki hyvin!");
                }
            } catch (IndexOutOfBoundsException ex) {
                System.out.println("Junaa ei ole olemassa");
                return;
            }
        }

        private static double etaisyysTurkuun ( double lat1, double lon1){

            Scanner lukija = new Scanner(System.in);

            double lat2 = 60.453832;
            double lon2 = 22.253441;
            double theta = lon1 - lon2;
            double dist = Math.sin((lat1) * (Math.PI / 180.0)) * Math.sin((lat2) * (Math.PI / 180.0)) + Math.cos((lat1) * (Math.PI / 180.0)) * Math.cos((lat2) * (Math.PI / 180.0)) * Math.cos((theta) * (Math.PI / 180.0));
            dist = Math.acos(dist);
            dist = (dist) * (180.0 / Math.PI);
            dist = dist * 60 * 1.1515;
            dist = dist * 1.609344;

            return dist;
        }

        private static boolean pysahtyykoTurussa (String junaNro){
            try {
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
            } catch (IndexOutOfBoundsException ex) {
                return false;
            }
        }

        private static String palauttaaAsemanNimenLyhytkoodina(String asemaNimi) {

            String asemaUrl = "https://rata.digitraffic.fi/api/v1/metadata/stations";
            List<Station> asemaLista = JSON.palauttaaListanJSONsta(asemaUrl, Station.class);
            String asemaLyhyt = "";
            for (Station asema: asemaLista) {
                if (asema.getStationName().toUpperCase().equals(asemaNimi.toUpperCase())) {
                    asemaLyhyt = asema.getStationShortCode();
                    return asemaLyhyt;
                }
            }
            return asemaLyhyt;
        }

        private static String palauttaaLyhytkoodistaAsemanNimen(String asemanLyhytkoodi) {

        String asemaUrl = "https://rata.digitraffic.fi/api/v1/metadata/stations";
            List<Station> asemaLista = JSON.palauttaaListanJSONsta(asemaUrl, Station.class);
            String asemanNimi = "";
            for (Station asema: asemaLista) {
                if (asema.getStationShortCode().toUpperCase().equals(asemanLyhytkoodi.toUpperCase())) {
                    asemanNimi = asema.getStationName();
                    return asemanNimi;
                }
            }
            return asemanNimi;
        }

        public static void main (String[]args) {
            new TrainMain().kaynnista();
        }
    }

