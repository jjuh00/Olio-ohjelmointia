package src;

import java.util.Scanner;
import java.util.List;

public class Ihminen extends Pelaaja {
    private final Scanner sc;
    
    public Ihminen(String nimi, Scanner sc) {
        super(nimi);
        this.sc = sc;
    }

    @Override
    public Kortit pelaaKortti(PelinKulku pk) {
        System.out.println("Kortit kädessäsi:");
        for (int i = 0; i < kasi.size(); i++) {
            System.out.println((i + 1)+ ". " + kasi.get(i));
        }

        List<Kortit> pelattavatKortit = pk.haePelattavatKortit(kasi);
        if (pelattavatKortit.isEmpty()) {
            System.out.println("Et voi pelata korttia. Sinun täytyy ohittaa vuorosi ja varastaa kortti");
            return null;
        }   

        System.out.println("\nPelattavat kortit:");
        for (Kortit kortti : pelattavatKortit) {
            System.out.println("- " + kortti);   
        }

        while (true) {
            System.out.print("Valitse pelattavan kortin arvo (tai kirjoita 0 ohittaaksesi): ");
            int valinta = sc.nextInt();

            if (valinta == 0) return null;

            if (valinta > 0 && valinta <= kasi.size()) {
                Kortit valittuKortti = kasi.get(valinta - 1);
                if (pelattavatKortit.contains(valittuKortti)) {
                    return valittuKortti;
                }
            }
            System.out.println("Epäkelpo valinta");
        }
    }

    @Override
    public boolean kaadetaanko(Korttipakka pino, Kortit kortti) {
        if (!pino.voikoKaataa()) return false;

        Kortit ylinKortti = pino.haeYlinKortti();
        if (ylinKortti == null) return false;

        System.out.println("\nVoit kaataa pakan ympäri");
        if (kortti.kakkonen() && ylinKortti.kolmonen()) {
            System.out.println("Tämän maan ässästä tulee 14, jos kaadat pakan");
        } else if (kortti.kuningas() && ylinKortti.kuningatar()) {
            System.out.println("Tämän maan ässästä tulee 1, jos kaadat pakan");
        }

        System.out.print("Haluatko kaataa pakan? (k/e): ");
        String valinta = sc.next().toLowerCase();
        return valinta.equals("k");
    }

    @Override
    public Kortit varastettavaKortti(Pelaaja vastustaja) {
        List<Kortit> vastustajanKasi = vastustaja.haeKasi();
        if (vastustajanKasi.size() <= 1) {
            throw new IllegalStateException("Vastustajalla ei ole varastettavia kortteja");
        }
        System.out.println("Vastustajan käsi:");
        for (int i = 0; i < vastustajanKasi.size(); i++) {
            System.out.println((i + 1) + ". " + vastustajanKasi.get(i));
        }

        while (true) {
            System.out.print("Syötä varastettavan kortin arvo: ");
            int valinta = sc.nextInt();

            if (valinta > 0 && valinta <= vastustajanKasi.size()) {
                return vastustajanKasi.get(valinta - 1);
            }
            System.out.println("Epäkelpo valinta");
        }
    }
}