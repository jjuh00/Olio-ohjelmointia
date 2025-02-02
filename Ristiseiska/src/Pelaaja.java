package src;

import java.util.List;
import java.util.ArrayList;

public abstract class Pelaaja {
    protected List<Kortit> kasi; // Käsi
    protected String nimi;

    public Pelaaja(String nimi) {
        this.nimi = nimi;
        this.kasi = new ArrayList<>();
    }

    public void lisaaKortti(Kortit kortti) {
        kasi.add(kortti);
    }

    public void poistaKortti(Kortit kortti) {
        kasi.remove(kortti);
    }

    // haeKäsi
    public List<Kortit> haeKasi() {
        return new ArrayList<>(kasi);
    }

    public String haeNimi() {
        return nimi;
    }

    public boolean onKortteja() {
        return !kasi.isEmpty();
    }

    public abstract Kortit pelaaKortti(PelinKulku pk);
    public abstract Kortit varastettavaKortti(Pelaaja vastustaja);
    public abstract boolean kaadetaanko(Korttipakka pakka, Kortit kortti);
}