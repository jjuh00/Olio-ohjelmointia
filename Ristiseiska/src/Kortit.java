package src;

public class Kortit implements Comparable<Kortit>{
    private static final String[] maat = {"Risti", "Ruutu", "Hertta", "Pata"};
    private static final String[] arvot = {
        "ässä", "kakkonen", "kolmonen", "nelonen", "viitonen", "kuutonen", "seiska",
         "kahdeksan", "yhdeksän", "kymmenen", "jätkä", "kuningatar", "kuningas"
    };
    private static final int[] numero_arvot = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};

    private final String maa;
    private final String arvo;
    private boolean onkoAssaIso;

    public Kortit(String maa, String arvo) {
        if (!onkoOikeaMaa(maa) || !onkoOikeaArvo(arvo)) {
            throw new IllegalArgumentException("Väärä maa tai arvo");
        }
        this.maa = maa;
        this.arvo = arvo;
        this.onkoAssaIso = false;
    }

    private boolean onkoOikeaMaa(String maa) {
        for (String oikeaMaa : maat) {
            if (oikeaMaa.equals(maa)) return true;
        }
        return false;
    }

    private boolean onkoOikeaArvo(String arvo) {
        for (String oikeaArvo : arvot) {
            if (oikeaArvo.equals(arvo)) return true;
        }
        return false;
    }

    public String haeMaa() { return maa; }
    public String haeArvo() { return arvo; }
    public void assanArvo(boolean isoAssa) { onkoAssaIso = isoAssa; }

    public static String[] haeMaat() { return maat.clone(); }
    public static String[] haeArvot() { return arvot.clone(); }

    public int haeNumeroArvo() {
        if (arvo.equals("ässä")) {
            return onkoAssaIso ? 14 : 1;
        }
        for (int i = 0; i < arvot.length; i++) {
            if (arvot[i].equals(arvo)) {
                return numero_arvot[i];
            }
        }
        return 0; // Ei pitäisi koskaan tapahtua
    }

    @Override
    public String toString() {
        return maa + arvo;
    }

    @Override
    public int compareTo(Kortit muu) {
        // Vertaillaan maita ensin
        int maaVertailu = haeMaaIndeksi(this.maa) - haeMaaIndeksi(muu.maa);
        if (maaVertailu != 0) { return maaVertailu; }
        // Jos maat ovat samat, vertaile arvoja
        return Integer.compare(this.haeNumeroArvo(), muu.haeNumeroArvo());
    }

    private int haeMaaIndeksi(String maa) {
        for (int i = 0; i < maat.length; i++) {
            if (maat[i].equals(maa)) return i;
        }
        return -1; // Ei pitäisi koskaan tapahtua
    }

    // Apumetodeja tukemaan pelin logiikkaa
    public boolean seiska() {
        return arvo.equals("seiska");
    }

    public boolean risti() {
        return maa.equals("Risti");
    }

    public boolean kakkonen() {
        return arvo.equals("kakkonen");
    }

    public boolean kolmonen() {
        return arvo.equals("kolmonen");
    }

    public boolean kuningatar() {
        return arvo.equals("kuningatar");
    }

    public boolean kuningas() {
        return arvo.equals("kuningas");
    }
}