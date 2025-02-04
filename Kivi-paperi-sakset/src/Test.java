
package src;

// Halutessasi (eli jos haluat ajaan tämän testin) ota tämä pois kokonaan kommenteista
// HUOM! Katso .vscode/settings.json -tiedosto ensiksi

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.beans.Transient;

public class Test {
    private Pelaaminen peli;
    private Liikkeet liikkeet;

    @BeforeEach
    void setUp() {
        peli = new Pelaaminen();
        liikkeet = new Liikkeet();
    }

    @Test
    void haeTietokoneenLiike_helppo() {
        
    }
}

