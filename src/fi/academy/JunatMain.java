package fi.academy;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class JunatMain {

    private static final String menuteksti = "\nAnna vaihtoehto:\n"
            + "1: Hae seuraava juna\n"
            + "2: Hae seuraava asema\n"
            + "3: Olen hukassa?!\n"
            + "0: Lopeta";

    public void kaynnista() {
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
                    String junaNro = lukija.nextLine();
                    tulostaSeuraavaAsema(junaNro);
                case "3":
                    System.out.println("Valitsit vaihtoehdon 3");
                    break;
                case "0":
                    break outerloop;
            }
        }
    }

    public static void tulostaSeuraavaJuna(String lahtoAsema, String maaraAsema) {
        // Tämä osa muodostaa URLin, joka kertoo junat lähtöasemalta määränpäähän.
        String ekaUrl = "https://rata.digitraffic.fi/api/v1/live-trains/station/" + lahtoAsema + "/" + maaraAsema;
        List<Juna> junat = JSON_pohja_junat.lueJunanJSONData(ekaUrl);
        for (Juna juna: junat) {
            System.out.println("Junanumero: " + juna.getTrainNumber());
        }
    }


// Tämä osa muodostaa URLin, joka kertoo annetun junalinjan vuorot TÄNÄÄN.

        public static void tulostaSeuraavaAsema(String junaNro) {
            String tokaUrl = "https://rata.digitraffic.fi/api/v1/trains/" + LocalDate.now() + "/" + junaNro;
            System.out.println(tokaUrl);
            List<Juna> junat = JSON_pohja_junat.lueJunanJSONData(tokaUrl);
            for (Juna juna: junat) {
                System.out.println("Aseman kirjainkoodi: " + juna.getStationShortCode());
            }
        }


    public static void main(String[] args) {
       new JunatMain().kaynnista();
       //System.out.println(JSON_pohja_junat.lueJunanJSONData("https://rata.digitraffic.fi/api/v1/live-trains/station/HKI/LH"));


    }

}
