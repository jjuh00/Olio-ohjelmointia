package src;

import java.util.*;

public class PelinKulku {
    private Map<String, Korttipakka> pinot;
    private boolean peliAloitettu;
    private boolean ristiSeiskaPelattu;

    public PelinKulku() {
        pinot = new HashMap<>();
        for (String maa : Kortit.haeMaat()) {
            pinot.put(maa, new Korttipakka(maa));
        }
        peliAloitettu = false;
        ristiSeiskaPelattu = false;
    }

    public Korttipakka haePino(String maa) {
        return pinot.get(maa);
    }

    public void lisaaKorttiPinoon(Kortit kortti) {
        pinot.get(kortti.haeMaa()).lisaaKortti(kortti);
        if (kortti.seiska() && kortti.risti()) {
            ristiSeiskaPelattu = true;
            peliAloitettu = true;
        }
    }

    public List<Kortit> haePelattavatKortit(List<Kortit> kasi) {
        List<Kortit> pelattavatKortit = new ArrayList<>();

        // Pelin aloitus ristiseiskalla
        if (!ristiSeiskaPelattu) {
            for (Kortit kortti : kasi) {
                if (kortti.seiska() && kortti.risti()) {
                    pelattavatKortit.add(kortti);
                    return pelattavatKortit;
                }
            }
            return pelattavatKortit;
        }

        // Seuraava vuoro ristiseiskan jälkeen
        // Pelaaja voi pelata muiden maiden seiskoja tai ristikutosen
        if (ristiSeiskaPelattu && ekaVuoro()) {
            for (Kortit kortti : kasi) {
                if (kortti.seiska() || (kortti.risti() && kortti.haeNumeroArvo() == 6)) {
                    pelattavatKortit.add(kortti);
                }
            }
            return pelattavatKortit;
        }

        // Normaali pelinkulku
        for (Kortit kortti : kasi) {
            if (onkoPelattava(kortti)) {
                pelattavatKortit.add(kortti);
            }
        }

        return pelattavatKortit;
    }

    private boolean ekaVuoro() {
        // Tarkista, onko vain ristiseiska pöydässä
        boolean vainRistiSeiska = true;
        for (String maa : Kortit.haeMaat()) {
            Korttipakka pino = pinot.get(maa);
            List<Kortit> kortit = pino.haeKortit();
            if (maa.equals("Risti")) {
                if (kortit.size() != 1 || !kortit.get(0).seiska()) {
                    vainRistiSeiska = false;
                }
            } else if (!kortit.isEmpty()) {
                vainRistiSeiska = false;
            }
        }
        return vainRistiSeiska;
    }

    private boolean onkoPelattava(Kortit kortti) {
        Korttipakka pino = pinot.get(kortti.haeMaa());
        List<Kortit> maaPino = pino.haeKortit();

        // Seiskan voi pelata aina, jos saman maan korttia ei ole pelattu
        if (kortti.seiska() && !onkoSeiska(kortti.haeMaa())) return true;

        // Vain seiskalla voi aloittaa, jos saman maan kortteja ei ole pelattu
        if (maaPino.isEmpty()) return false; 
        
        int korttiArvo = kortti.haeNumeroArvo();
        Set<Integer> pelattavatArvot = new HashSet<>();

        // Hae oikea ylin kortti, jos pino on kaadettu ympäri
        Kortit ylinKortti = pino.haeYlinKortti();
        if (ylinKortti != null) {
            int arvo = ylinKortti.haeNumeroArvo();
            // Lisää vierekkäiset arvot pelattaviksi arvoiksi
            pelattavatArvot.add(arvo + 1);
            pelattavatArvot.add(arvo - 1);
        }

        return pelattavatArvot.contains(korttiArvo);
    }

    private boolean onkoSeiska(String maa) {
        Korttipakka pino = pinot.get(maa);
        for (Kortit kortti : pino.haeKortit()) {
            if (kortti.seiska()) return true;
        }
        return false;
    }

    public boolean voiKaataa(String maa, Kortit pelattavaKortti) {
        Korttipakka pino = pinot.get(maa);
        if (pino == null || pino.haeKortit().isEmpty()) return false;

        Kortit ylinKortti = pino.haeYlinKortti();
        if (ylinKortti == null) return false;

        // Tarkistetaan, onko tilanne jossa pakan voi kaataa:
        // Pakan voi kaataa, jos pöydässä on kolmonen ja pelaajalla on saman maan kakkonen
        if (ylinKortti.kolmonen() && pelattavaKortti.kakkonen() &&
            ylinKortti.haeMaa().equals(pelattavaKortti.haeMaa())) {
                return true;
            }

        // Pakan voi kaataa, jos pöydässä on kuningatar ja pelaajalla on saman maan kuningas
        if (ylinKortti.kuningatar() && pelattavaKortti.kuningas() &&
            ylinKortti.haeMaa().equals(pelattavaKortti.haeMaa())) {
                return true;
            }

        return false;
    }

    public void tulostaTilanne() {
        System.out.println("\nTilanne:");
        for (String maa : Kortit.haeMaat()) {
            System.out.print(maa + ": ");
            Korttipakka pino = pinot.get(maa);
            List<Kortit> kortit = pino.haeKortit();
            if (kortit.isEmpty()) {
                System.out.println("Tyhjä");
            } else {
                System.out.println(kortit.stream()
                    .map(Kortit::toString)
                    .reduce((a, b) -> a + ", " + b)
                    .get());
                if (pino.kaadettu()) {
                    System.out.print(" (KAADETTU) ");
                }
                System.out.println();
            }
        }
        System.out.println();
    }
}