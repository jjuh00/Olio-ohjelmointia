package src;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Pelaaminen p = new Pelaaminen();
        Random rnd = new Random();

        System.out.println("Tervetuloa pelaamaan!");
        p.kysyVaikeusTaso();

        // Arvotaan satunnainen numero 1 ja 2 välillä
        // 1 = käyttäjä aloitus, 2 = tietokoneen aloitus
        int aloitus = rnd.nextInt(2) + 1;

        if (aloitus == 1) {
            System.out.println("Sinä aloitat!");
            p.pelaa(false);
        } else {
            System.out.println("Tietokone aloittaa!");
            p.pelaa(true);
        }
    }
}
