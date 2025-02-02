package src;

import java.util.*;

public class Peli {
    private final Ihminen ihminen;
    private final Tietokone tietokone;
    private final PelinKulku pk;
    private final Scanner sc;

    public Peli() {
        sc = new Scanner(System.in);
        System.out.print("Syötä nimesi: ");
        String nimi = sc.nextLine();

        ihminen = new Ihminen(nimi, sc);
        tietokone = new Tietokone();
        pk = new PelinKulku();
        jaaKortit();
        pelaaRistiSeiska();
    }

    private void jaaKortit() {
        List<Kortit> pakka = luoPakka();
        Collections.shuffle(pakka);

        for (int i = 0; i < pakka.size(); i++) {
            if (i % 2 == 0) {
                ihminen.lisaaKortti(pakka.get(i));
            } else {
                tietokone.lisaaKortti(pakka.get(i));
            }
        }
    }
    
    private void pelaaRistiSeiska() {
        // Tarkistetaan, onko ristiseiska ihmisellä
        Kortit ristiSeiska = etsiRistiSeiska(ihminen.haeKasi());
        Pelaaja aloittavaPelaaja;

        if (ristiSeiska != null) {
            aloittavaPelaaja = ihminen;
        } else {
            // Tarkistetaan, onko ristiseiskan tietokoneella
            ristiSeiska = etsiRistiSeiska(tietokone.haeKasi());
            if (ristiSeiska == null) {
                throw new IllegalStateException("Ristiseiskaa ei löytynyt");
            }
            aloittavaPelaaja = tietokone;
        }

        // Pelataan ristiseiska pöytään automaattisesti
        System.out.println("\nPeli alkaa");
        aloittavaPelaaja.poistaKortti(ristiSeiska);
        pk.lisaaKorttiPinoon(ristiSeiska);
        System.out.println(aloittavaPelaaja.haeNimi() + " pelaa ristiseiskan!");

        // Määritetään seuraavan pelaajan vuoro
        pelaaja = (aloittavaPelaaja == ihminen) ? tietokone : ihminen;
    }

    // Etsitään ristiseiskaa ihmisen ja tietokoneen pakoista
    private Kortit etsiRistiSeiska(List<Kortit> kasi) {
        for (Kortit kortti : kasi) {
            if (kortti.seiska() && kortti.risti()) return kortti;
        }
        return null;
    }
    
    private List<Kortit> luoPakka() {
        List<Kortit> pakka = new ArrayList<>();
        String[] maat = Kortit.haeMaat();
        String[] arvot = Kortit.haeArvot();

        for (String maa : maat) {
            for (String arvo : arvot) {
                pakka.add(new Kortit(maa, arvo));
            }
        }
        return pakka;
    }

    private Pelaaja pelaaja; // Tämänhetkinen pelaaja

    public void pelaa() {
        while (ihminen.onKortteja() && tietokone.onKortteja()) {
            if (pelaaja == ihminen) {
                System.out.println("Sinun vuorosi\n");
            } else {
                System.out.println("Tietokoneen vuoro\n");
            }
            
            Kortit pelattuKortti = pelaaja.pelaaKortti(pk);

            // Pelaajan täytyy varastaa toiselta pelaajalta kortti, jos pelaajalla ei ole korttia, jota pelata
            if (pelattuKortti == null) {
                System.out.println(pelaaja.haeNimi() + " sinun täytyy ohittaa ja varastaa kortti");
                Pelaaja vastustaja = (pelaaja == ihminen) ? tietokone : ihminen;
                Kortit varastettuKortti = pelaaja.varastettavaKortti(vastustaja);
                vastustaja.poistaKortti(varastettuKortti);
                pelaaja.lisaaKortti(varastettuKortti);
                System.out.println(pelaaja.haeNimi() + " varasti kortin " + varastettuKortti);
            } else {
                pelaaja.poistaKortti(pelattuKortti);
                Korttipakka pino = pk.haePino(pelattuKortti.haeMaa());

                // Tarkistetaan, kaadetaanko pakka
                if (pelaaja.kaadetaanko(pino, pelattuKortti)) {
                    pino.kaadaPino();
                    System.out.println(pelaaja.haeNimi() + " kaatoi pinon!");
                }

                pk.lisaaKorttiPinoon(pelattuKortti);
                System.out.println(pelaaja.haeNimi() + " pelasi kortin " + pelattuKortti);
            }

            // Määritetään seuraavan pelaajan vuoro
            pelaaja = (pelaaja == ihminen) ? tietokone : ihminen;
        }

        // Määritetään voittaja: Ensimmäinen pelaaja, joka saa pelattua kaikki korttinsa, voittaa pelin
        String voittaja = ihminen.onKortteja() ? tietokone.haeNimi() : ihminen.haeNimi();
        String haviaja = ihminen.onKortteja() ? ihminen.haeNimi() : tietokone.haeNimi();

        System.out.println("Peli päättyi. " + voittaja + " voitti pelin!");

        PelinTulos tulos = new PelinTulos(voittaja, haviaja);
        tulos.tallennaTulos();
    }
}