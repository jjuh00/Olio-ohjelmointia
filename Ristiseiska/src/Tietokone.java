package src;

import java.util.*;

public class Tietokone extends Pelaaja {
    private final Random rnd;

    public Tietokone() {
        super("Tietokone");
        this.rnd = new Random();
    }

    @Override
    public Kortit pelaaKortti(PelinKulku pk) {
        List<Kortit> pelattavatKortit = pk.haePelattavatKortit(kasi);
        if (pelattavatKortit.isEmpty()) return null;

        // Haetaan mahdolliset liikkeet pelin alkuun
        List<Kortit> seiskat = new ArrayList<>();
        List<Kortit> ristiKutonen = new ArrayList<>();

        for (Kortit kortti : pelattavatKortit) {
            if (kortti.seiska()) {
                seiskat.add(kortti);
            } else if (kortti.risti() && kortti.haeNumeroArvo() == 6) {
                ristiKutonen.add(kortti);
            }
        }

        // Strategia pelin alkuun
        if (!seiskat.isEmpty() || !ristiKutonen.isEmpty()) {
            // Pelaa seiskat alussa
            if (!seiskat.isEmpty()) {
                // Pelaa satunnainen seiska, jos seiskoja on mahdollista pelata
                return seiskat.get(rnd.nextInt(seiskat.size()));
            } else {
                // Pelaa ristikutonen, jos kyseinen kortti on kädessä ja seiskoja ei ole
                return ristiKutonen.get(0);
            }
        }

        return pelattavatKortit.get(rnd.nextInt(pelattavatKortit.size()));
    }

    @Override
    public boolean kaadetaanko(Korttipakka pino, Kortit kortti) {
        if (!pino.voikoKaataa()) return false;

        Kortit ylinKortti = pino.haeYlinKortti();
        if (ylinKortti == null) return false;
        
        if ((kortti.kakkonen() && kortti.kolmonen()) ||
            (kortti.kuningatar() && kortti.kuningas())) {
                // Arvotaan, kaataako tietokone pakan
                return rnd.nextBoolean();
        }
        return false;
    }

    @Override
    public Kortit varastettavaKortti(Pelaaja vastustaja) {
        List<Kortit> vastustajanKasi = vastustaja.haeKasi();
        if (vastustajanKasi.size() <= 1) {
            throw new IllegalStateException("Vastustajalla ei ole varastattevia kortteja");
        }
        return vastustajanKasi.get(rnd.nextInt(vastustajanKasi.size()));
    }
}