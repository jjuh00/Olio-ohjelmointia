/*
package src;

// Halutessasi (eli jos haluat ajaan tämän testin) ota tämä pois kokonaan kommenteista
// HUOM! Katso .vscode/settings.json -tiedosto ensiksi

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.beans.Transient;

public class Test {
    private Pelaaminen peli;

    @BeforeEach
    void setUp() {
        peli = new Pelaaminen();
    }

    @Test
    void haeTietokoneenLiike_helppo() {
        peli.asetaVaikeusTaso(1);
        int liike = peli.haeTietokoneenLiike(1);
        assertEquals(3, liike, "Helpon tason pitäisi valita hävivä liike pelaajan valintaa vastaan");
    }

    @Test
    vod haeTietokoneenLiike_keskivaikea() {
        peli.asetaVaikeusTaso(2);
        int liike = peli.haeTietokoneenLiike(1);
        assertEquals(liike >= 1 && liike <= 3, "Keskivaikea tason pitäisi valita satunnainen liike");
    }

    @Test
    void haeTietokoneenLiike_mahdoton() {
        peli.asetaVaikeusTaso(3);
        int liike = peli.haeTietokoneenLiike(1);
        assertEquals(2, liike, "Mahdottoman tason pitäisi valita voittava liike pelaajan valintaa vastaan");
    }

    @Test
    void haeLiike() {
        assertEquals("Kivi", Liikkeet.haeLiikkeenNimi(1));
        assertEquals("Paperi", Liikkeet.haeLiikkeenNimi(2));
        assertEquals("Sakset", Liikkeet.haeLiikkeenNimi(3));
        assertEquals("Tuntematon liike", Liikkeet.haeLiikkeenNimi(4));
    }

    @Test
    void haeVoittava() {
        assertEquals(2, Liikkeet.haeVoittavaLiike(1), "Paperi voittaa kiven");
        assertEquals(3, Liikkeet.haeVoittavaLiike(2), "Sakset voittaa paperin");
        assertEquals(1, Liikkeet.haeVoittavaLiike(3), "Kivi voittaa sakset");
    }

    @Test
    void haeHaviava() {
        assertEquals(3, Liikkeet.haeHaviavaLiike(1), "Sakset häviää kivelle");
        assertEquals(1, Liikkeet.haeHaviavaLiike(2), "Kivi häviää paperille");
        assertEquals(2, Liikkeet.haeHaviavaLiike(3), "Paperi häviää saksille");
    }
}
*/