package src;

import org.junit.jupiter.api.*;
import java.util.*;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;

public class Testi {
    private PelinKulku pk;
    private Kortit ristiSeiska;
    private Kortit ristiKuutonen;
    private Kortit ristiKasi;
    private Kortit ristiYsi;
    private Kortit ristiKymppi;
    private Kortit ristiJatka;
    private Kortit ristiKuningatar;
    private Kortit ristiKuningas;
    private Kortit ristiViitonen;
    private Kortit ristiNelonen;
    private Kortit ristiKolmonen;
    private Kortit ristiKakkonen;
    private Kortit ristiAssa;

    @BeforeEach
    void setUp() {
        pk = new PelinKulku();
        ristiSeiska = new Kortit("Risti", "seiska");
        ristiKuutonen = new Kortit("Risti", "kuutonen");
        ristiKasi = new Kortit("Risti", "kahdeksan");
        ristiYsi = new Kortit("Risti", "yhdeksän");
        ristiKymppi = new Kortit("Risti", "kymmenen");
        ristiJatka = new Kortit("Risti", "jätkä");
        ristiKuningatar = new Kortit("Risti", "kuningatar");
        ristiKuningas = new Kortit("Risti", "kuningas");
        ristiViitonen = new Kortit("Risti", "viitonen");
        ristiNelonen = new Kortit("Risti", "nelonen");
        ristiKolmonen = new Kortit("Risti", "kolmonen");
        ristiKakkonen = new Kortit("Risti", "kakkonen");
        ristiAssa = new Kortit("Risti", "ässä");
    }

    @Test
    void luoKortit() {
        assertThrows(IllegalArgumentException.class, () -> new Kortit("VääräMaa", "seiska"));
        assertThrows(IllegalArgumentException.class, () -> new Kortit("Risti", "VääräArvo"));
    }

    @Test
    void pelinAloitus() {
        List<Kortit> pelattavatKortit = pk.haePelattavatKortit(Arrays.asList(ristiSeiska));
        assertTrue(pelattavatKortit.contains(ristiSeiska));
    }

    @Test
    void ristiSeiskanJalkeen() {
        // Testataan ristiseiskan jälkeen pelattavat kortit
        pk.lisaaKorttiPinoon(ristiSeiska);

        List<Kortit> kasi = Arrays.asList(ristiKuutonen, ristiKasi); // Käsi, ristikasi
        List<Kortit> pelattavatKortit = pk.haePelattavatKortit(kasi);

        assertTrue(pelattavatKortit.contains(ristiKuutonen));
        assertTrue(pelattavatKortit.contains(ristiKasi));
    }

    @Test
    void normaaliPelinKulku() {
        // Määritetään pelin tilanne aloituksen jälkeen
        pk.lisaaKorttiPinoon(ristiSeiska);
        pk.lisaaKorttiPinoon(ristiKuutonen);

        List<Kortit> kasi = Arrays.asList(ristiViitonen, ristiKasi);
        List<Kortit> pelattavatKortit = pk.haePelattavatKortit(kasi);

        assertTrue(pelattavatKortit.contains(ristiViitonen));
        assertTrue(pelattavatKortit.contains(ristiKasi));
    }

    @Test
    void pakanKaato() {
        Korttipakka pino = pk.haePino("Risti");
        pk.lisaaKorttiPinoon(ristiKolmonen);

        // Pakan kaato 2-3 tilanteessa
        assertTrue(pk.voiKaataa("Risti", ristiKakkonen));
        assertFalse(pk.voiKaataa("Risti", ristiNelonen));

        // Pakan kaato kuningas-kuningatar tilanteessa
        pk = new PelinKulku();
        pk.lisaaKorttiPinoon(ristiKuningatar);

        assertTrue(pk.voiKaataa("Risti", ristiKuningas));
        assertFalse(pk.voiKaataa("Risti", ristiJatka));
    }

    @Test
    void assanArvo() {
        // Testataan ässän arvon muutos
        Korttipakka pino = new Korttipakka("Risti");
        pino.lisaaKortti(ristiKolmonen);
        pino.lisaaKortti(ristiKakkonen);

        // Ässä on oletuksena 14
        assertTrue(pino.onkoAssaIso());

        pino.kaadaPino();

        // Ässä pysyy 14:nä, kun pakka kaadetaan 2-3 tilanteessa
        assertTrue(pino.onkoAssaIso());

        // Ässästä tulee 1, kun pakka kaadetaan kuningas-kuningatar tilanteessa
        pino = new Korttipakka("Risti");
        pino.lisaaKortti(ristiKuningatar);
        pino.lisaaKortti(ristiKuningas);

        pino.kaadaPino();

        assertFalse(pino.onkoAssaIso());
    }

    @Test
    void kortinVarastaminen() {
        // Pelaajien luominen testausta varten
        Scanner sc = new Scanner(System.in);
        Ihminen ihminen = new Ihminen("Testaaja", sc);
        Tietokone tietokone = new Tietokone();

        // Annetaan pelaajilla kortit
        ihminen.lisaaKortti(ristiKuningas);
        tietokone.lisaaKortti(ristiAssa);
        tietokone.lisaaKortti(ristiKakkonen);

        // Testataan, että varastaminen onnistuu
        assertDoesNotThrow(() -> ihminen.varastettavaKortti(tietokone));

        // Poistetaan tietokoneelta kortit (jolloin tietokoneelle jää 1 kortti)
        tietokone.poistaKortti(ristiKakkonen);

        // Testataan, että vastustajalta ei voi varastaa, jos vastustajalla on vain 1 kortti
        assertThrows(IllegalStateException.class, () -> ihminen.varastettavaKortti(tietokone)); 
    }

    @Test
    void pelinLoppu() {
        Scanner sc = new Scanner(System.in);
        Ihminen ihminen = new Ihminen("Testaaja", sc);

        // Testataan, että peli huomaa, kun pelaajalla ei ole enää kortteja jäljellä
        assertFalse(ihminen.onKortteja());

        ihminen.lisaaKortti(ristiAssa);
        assertTrue(ihminen.onKortteja());

        ihminen.poistaKortti(ristiAssa);
        assertFalse(ihminen.onKortteja());
    }

    @Test
    void tulostenTallennus() {
        PelinTulos tulos = new PelinTulos("Voittaja", "Häviäjä");
        tulos.tallennaTulos();

        // Tarkistetaan, että tulostiedosto on luotu
        File tulosTiedosto = new File("tulokset.txt");
        assertTrue(tulosTiedosto.exists());

        // Tarkistetaan, että tiedosto sisältää tekstiä
        assertTrue(tulosTiedosto.length() > 0);
    }
}
