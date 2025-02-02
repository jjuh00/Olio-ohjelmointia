package src;

import java.io.*;
import java.time.LocalDateTime;

public class PelinTulos {
    private final String voittaja;
    private final String haviaja;
    private final LocalDateTime aika;

    public PelinTulos(String voittaja, String haviaja) {
        this.voittaja = voittaja;
        this.haviaja = haviaja;
        this.aika = LocalDateTime.now();
    }

    public void tallennaTulos() {
        // Tallennetaan pelin tulokset tiedostoon
        try (FileWriter fw = new FileWriter("tulokset.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw)) {
                pw.println(aika);
                pw.println("Voittaja: " + voittaja);
                pw.println("Häviäjä: " + haviaja);
        } catch (IOException e) {
            System.out.println("Tapahtui virhe tuloksia tallennettaessa: " + e.getMessage());
        }
    }
}