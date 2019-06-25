package fi.academy;

import java.util.Scanner;

public class JunatMain {

    private static final String menuteksti = "\nAnna vaihtoehto:\n"
            + "1: Hae seuraava juna\n"
            + "2: Hae seuraava asema\n"
            + "3: Olen hukassa?!\n"
            + "0: Lopeta";

    public void kaynnista() {
        Scanner lukija = new Scanner(System.in);

        for (; ;) {
            System.out.println(menuteksti);
            String vastaus = lukija.nextLine().trim();
            if ("1".equals(vastaus)) {
                System.out.println("Valitsit vaihtoehdon 1");
            } else if ("2".equals(vastaus)) {
                System.out.println("Valitsit vaihtoehdon 2");
            } else if ("3".equals(vastaus)) {
                System.out.println("Valitsit vaihtoehdon 3");
            } else if ("0".equals(vastaus)) {
                break;
            }
        }
    }


    public static void main(String[] args) {
       new JunatMain().kaynnista();
       // JSON_pohja_junat.lueJunanJSONData();1


    }

}
