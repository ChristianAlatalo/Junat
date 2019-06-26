package fi.academy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JunatMain {

    private static final String menuteksti = "\nAnna vaihtoehto:\n"
            + "1: Hae seuraava juna\n"
            + "2: Hae seuraava asema\n"
            + "3: Matkalla Turkuun?\n"
            + "0: Lopeta";

    private void kaynnista() {
        Scanner lukija = new Scanner(System.in);

        outerloop:
        for (; ;) {
            System.out.println(menuteksti);
            String vastaus = lukija.nextLine().trim();

            switch (vastaus) {
                case "1":
                    System.out.println("Anna lähtöaseman kirjainkoodi:");
                    String lahtoAsema = lukija.nextLine();
                    System.out.println("Anna määränpään kirjainkoodi:");
                    String maaraAsema = lukija.nextLine();
                    tulostaSeuraavaJuna(lahtoAsema, maaraAsema);
                    break;
                case "2":
                    System.out.println("Anna junan numero:");
                    String junaNro1 = lukija.nextLine();
                    tulostaSeuraavaAsema(junaNro1);
                case "3":
                    System.out.println("Anna junan numero:");
                    String junaNro2 = lukija.nextLine();
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

    private static void tulostaSeuraavaJuna(String lahtoAsema, String maaraAsema) {
        // Tämä osa muodostaa URLin, joka kertoo junat lähtöasemalta määränpäähän.
        String url = "https://rata.digitraffic.fi/api/v1/live-trains/station/" + lahtoAsema + "/" + maaraAsema;
        List<Juna> junalista = JSONjunat.lueJunanJSONData(url);
        for (Juna juna: junalista) {
            System.out.println("Junanumero: " + juna.getTrainNumber());
        }
    }


// Tämä osa muodostaa URLin, joka kertoo annetun junalinjan vuorot TÄNÄÄN.

    private static void tulostaSeuraavaAsema(String junaNro) {
            String url = "https://rata.digitraffic.fi/api/v1/trains/" + LocalDate.now() + "/" + junaNro;
            System.out.println(url);
            List<Juna> junalista = JSONjunat.lueJunanJSONData(url);
            for (Juna juna: junalista) {
                System.out.println("Aseman kirjainkoodi: " + juna.getStationShortCode());
            }
        }

    private static void tulostaEtaisyysNopeus(String junaNro) {
        String url = "https://rata.digitraffic.fi/api/v1/train-locations/latest/" + junaNro;
        List<Juna> junalista = JSONjunat.lueJunanJSONData(url);
        for (Juna juna: junalista) {
            System.out.println("Koordinaattisi ovat " + juna.getLocation().getCoordinates() + " ja nopeutesi on " + juna.getSpeed() + "km/h:ssa.");
        }
    }

    private static void matkallaTurkuun() {

        Scanner lukija = new Scanner(System.in);

            /*System.out.println("Syötä junanumero");
            int junaNro = Integer.valueOf(lukija.nextLine());*/


            double lat1 = 60.192059;
            double lon1 = 24.945831;
            double lat2 = 60.454510;
            double lon2 = 22.264824;

            double theta = lon1 - lon2;
            double dist = Math.sin((lat1)* (Math.PI / 180.0)) * Math.sin((lat2)* (Math.PI / 180.0)) + Math.cos((lat1)* (Math.PI / 180.0)) * Math.cos((lat2)* (Math.PI / 180.0)) * Math.cos((theta)* (Math.PI / 180.0));
            dist = Math.acos(dist);
            dist = (dist)* (180.0 / Math.PI);
            dist = dist * 60 * 1.1515;
            dist = dist * 1.609344;

            System.out.println(dist);

        }


    public static void main(String[] args) {
       new JunatMain().kaynnista();
    }
}
