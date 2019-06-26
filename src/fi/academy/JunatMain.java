package fi.academy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JunatMain {

    private static final String menuteksti = "\nAnna vaihtoehto:\n"
            + "1: Hae seuraava juna\n"
            + "2: Hae seuraava asema\n"
            + "3: Olen hukassa?!\n"
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
                    String junaNro = lukija.nextLine();
                    tulostaSeuraavaAsema(junaNro);
                case "3":
                    System.out.println("Valitsit vaihtoehdon 3");
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
        // Muodostaa URLin, joka kertoo junat lähtöasemalta määränpäähän.
        String url = "https://rata.digitraffic.fi/api/v1/live-trains/station/" + lahtoAsema + "/" + maaraAsema;
        List<Juna> junalista = JSONjunat.lueJunanJSONData(url);
        for (Juna juna: junalista) {
            System.out.println("Junanumero: " + juna.getTrainNumber());
            for (TimeTableRow lista: juna.getTimeTableRows()) {
                if (lista.isTrainStopping() == true && lista.getType().equals("ARRIVAL")) {
                    System.out.println("Juna saapuu asemalle " + lista.getStationShortCode() + " kello: " + lista.getScheduledTime());
                } else if (lista.isTrainStopping() == true && lista.getType().equals("DEPARTURE")) {
                    System.out.println("Juna lähtee asemalta " + lista.getStationShortCode() + " kello: " + lista.getScheduledTime());
                } else if (lista.isTrainStopping() == false) {
                    System.out.println("Keskity!!! Juna ei pysähdy seuraavalla asemalla.");
                }
            }
        }
    }

    private static void tulostaSeuraavaAsema(String junaNro) {
        // Muodostaa URLin, joka kertoo annetun junalinjan vuorot TÄNÄÄN.

        String url = "https://rata.digitraffic.fi/api/v1/trains/" + LocalDate.now() + "/" + junaNro;
            System.out.println(url);
            List<Juna> junalista = JSONjunat.lueJunanJSONData(url);
            for (Juna juna: junalista) {
                System.out.println("Aseman kirjainkoodi: " + juna.getStationShortCode());
            }
        }


    public static void main(String[] args) {
       new JunatMain().kaynnista();
    }
}
