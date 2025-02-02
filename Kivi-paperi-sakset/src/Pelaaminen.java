package src;

import java.util.Scanner;
import java.util.Random;

public class Pelaaminen {
    private int vaikeusTaso;
    private Random rnd;
    private Scanner sc;

    public Pelaaminen() {
        this.rnd = new Random();
        this.sc = new Scanner(System.in);
    }

    // Kysytään käyttäjältä vaikeustaso
    public void kysyVaikeusTaso() {
        System.out.print("Valitse vaikeustaso (1=helppo, 2=keskivaikea, 3=mahdoton): ");
        vaikeusTaso = haeVaikeusTaso();
    }

    public void pelaa(boolean tietokoneenAloitus) {
        if (tietokoneenAloitus) {
            // Tietokoneen aloitus, ei ole vielä toisen pelaajan vuoro
            int tietokoneenLiike = haeTietokoneenLiike(-1); 
            System.out.println("Valitsin liikkeen, nyt on sun vuoro!");
            int pelaajanLiike = haePelaajanLiike();
            System.out.println("Valitsit " + Liikkeet.haeLiikkeenNimi(pelaajanLiike));
            System.out.println("Tietokone valitsi liikkeen " + Liikkeet.haeLiikkeenNimi(tietokoneenLiike));
            haeVoittaja(pelaajanLiike, tietokoneenLiike); // Päätellään, kumpi voitti
        } else {
            // Pelaajan aloitus
            int pelaajanLiike = haePelaajanLiike();
            int tietokoneenLiike = haeTietokoneenLiike(pelaajanLiike);
            System.out.println("Valitsit " + Liikkeet.haeLiikkeenNimi(pelaajanLiike));
            System.out.println("Tietokone valitsi liikeen " + Liikkeet.haeLiikkeenNimi(tietokoneenLiike));
            haeVoittaja(pelaajanLiike, tietokoneenLiike); // Päätellään, kumpi voitti
        }
    }

    private int haeVaikeusTaso() {
        while (true) {
            try {
                int taso = sc.nextInt();
                if (taso < 1 || taso > 3) {
                    System.out.println("Valitse vaikeustaso väliltä 1-3: ");
                } else {
                    return taso;
                }
            } catch (Exception ex) {
                System.out.println("Epäkelpo syöte, valitse vaikeustaso väliltä 1-3: ");
                sc.next();
            }
        }
    }

    // Tämä funktio määrittää tietokoneen liikkeen vaikeustason perusteella
    private int haeTietokoneenLiike(int pelaajanLiike) {
        switch (vaikeusTaso) {
            case 1:
                // Jos pelaajan vuoro on ensimmäisenä, pelaaja voittaa
                // Jos tietokoneen vuoro on ensimmäisenä, arvotaan satunnainen liike
                return pelaajanLiike == -1 ? rnd.nextInt(3) + 1 : Liikkeet.haeHaviavaLiike(pelaajanLiike);
            case 2:
                // Keskivaikea taso: Arvotaan satunnainen liike riippumatta siitä, kenen aloitus oli kyseessä
                return rnd.nextInt(3) + 1;
            case 3:
                // Jos pelaajan vuoro on ensimmäisenä, tietokone voittaa
                // Jos tietokoneen vuoro on ensimmäisenä, arvotaan liike
                return pelaajanLiike == -1 ? rnd.nextInt(3) + 1 : Liikkeet.haeVoittavaLiike(pelaajanLiike);
            default:
                throw new RuntimeException("Väärä vaikeustaso");
        }
    }

    private int haePelaajanLiike() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Valitse liike (1=kivi, 2=paperi, 3=sakset): ");
        while (true) {
            try {
                int liike = sc.nextInt();
                if (liike < 1 || liike > 3) {
                    System.out.print("Valitse liike väliltä 1-3: ");
                } else {
                    return liike;
                }
            } catch (Exception ex) {
                System.out.print("Valitse liike väliltä 1-3: ");
                sc.next();
            }
        }
    }

    // Tämä funktio määrittää pelin voittajan
    private void haeVoittaja(int pelaajanLiike, int tietokoneenLiike) {
        if (pelaajanLiike == tietokoneenLiike) {
            System.out.println("Tasapeli!");
        } else if ((pelaajanLiike == 1 && tietokoneenLiike == 3) ||
                    (pelaajanLiike == 2 && tietokoneenLiike == 1) ||
                    (pelaajanLiike == 3 && tietokoneenLiike == 2)) {
            System.out.println("Sinä voitit! Onneksi olkoon");
        } else {
            System.out.println("Hävisit!");
        }
    } 
}
