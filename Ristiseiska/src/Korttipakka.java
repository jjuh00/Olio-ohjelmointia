package src;

import java.util.*;

public class Korttipakka {
    private List<Kortit> kortit;
    private final String maa;
    private boolean kaadettu;
    private boolean assaIso;

    public Korttipakka(String maa) {
        this.maa = maa;
        this.kortit = new ArrayList<>();
        this.kaadettu = false;
        assaIso = true; // Oletuksena ässä on 14
    }

    public void lisaaKortti(Kortit kortti) {
        kortit.add(kortti);
        paivitaAssanArvo();
    }

    public void kaadaPino() {
        if (!kaadettu) {
            kaadettu = true;
            Collections.reverse(kortit); // Käännettän korttien järjestys ympäri

            // Päivitä ässän arvo riippuen pelitilanteesta
            Kortit ylinKortti = haeYlinKortti();
            Kortit edellinenKortti = kortit.size() > 1 ? kortit.get(kortit.size() - 2) : null;

            if (ylinKortti != null && edellinenKortti != null) {
                if (ylinKortti.kakkonen() && edellinenKortti.kolmonen()) {
                    // Ässän arvoksi tulee 14, kun pöydällä on kolmonen ja saman maan 2 pelataan, ja pino kaadetaan ympäri
                    assaIso = true; 
                } else if (ylinKortti.kuningas() && edellinenKortti.kuningatar()) {
                    assaIso = false; // Ässän arvoksi tulee 1, kun pakka kaadetaan kuningas-kuningatar tilanteessa
                }
            }
            paivitaAssanArvo();
        }
    }

    private void paivitaAssanArvo() {
        for (Kortit kortti : kortit) {
            if (kortti.haeArvo().equals("ässä")) {
                kortti.assanArvo(assaIso);
            }
        }
    }

    public boolean voikoKaataa() {
        if (kortit.size() < 2 ) return false;

        Kortit ylinKortti = haeYlinKortti();
        Kortit edellinenKortti = kortit.get(kortit.size() - 2);

        // Pakan voi kaataa pelaamalla kakkosen, kun edellinen kortti on kolmonne
        // TAI jos edellinen kortti on kuningatar, ja pelaa kuninkaan
        return (edellinenKortti.kolmonen() && ylinKortti.kakkonen()) ||
                (edellinenKortti.kuningatar() && ylinKortti.kuningas());
    }

    public List<Kortit> haeKortit() {
        return new ArrayList<>(kortit);
    }

    public boolean kaadettu() {
        return kaadettu;
    }

    public Kortit haeYlinKortti() {
        return kortit.isEmpty() ? null : kortit.get(kortit.size() - 1);
    }

    public boolean onkoAssaIso() {
        return assaIso;
    }
}