import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class Parantessjekker {

    /**
     * Leser kode fra fil
     * @param filnavn
     * @return
     */

    public static String lesKodeFraFil(String filnavn) {
        StringBuilder kode = new StringBuilder();

        try (BufferedReader kodeAnalyser = new BufferedReader(new FileReader(filnavn))) {
            String linje;
            while ((linje = kodeAnalyser.readLine()) != null) {
                kode.append(linje).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return kode.toString();
    }

    /**
     * Sjekker om paranteser er riktig
     * @param kode
     * @return
     */
    public static String[] sjekkParanteser(String kode) {
        Stack<Character> stack = new Stack<>();
        String[] feilMeldinger = new String[kode.length()];
        int feilMeldingIndex = 0;
        int linjeNummer = 1;

        for (int i = 0; i < kode.length(); i++) {
            char tegn = kode.charAt(i);

            if (tegn == '\n') {
                linjeNummer++;
            }

            if (tegn == '(' || tegn == '[' || tegn == '{') {
                stack.push(tegn);
            } else if (tegn == ')' || tegn == ']' || tegn == '}') {
                if (stack.isEmpty()) {
                    feilMeldinger[feilMeldingIndex++] = "Feil: " + tegn + " - Mangler tilsvarende åpning på linje " + linjeNummer;
                } else {
                    char sisteParantes = stack.pop();
                    if (!parantesDefinerer(sisteParantes, tegn)) {
                        feilMeldinger[feilMeldingIndex++] = "Feil: " + sisteParantes + " og " + tegn + " passer ikke sammen på linje " + linjeNummer;
                    }
                }
            }
        }

        while (!stack.isEmpty()) {
            char sisteParantes = stack.pop();
            feilMeldinger[feilMeldingIndex++] = "Feil oppdaget: " + sisteParantes + " - Mangler avsluttende " + matchendeParantes(sisteParantes) + " på linje " + linjeNummer;
        }

        return trimNulls(feilMeldinger);
    }

    /**
     * Sjekker om paranteser er riktig
     * @param a
     * @param b
     * @return
     */
    public static boolean parantesDefinerer(char a, char b) {
        return (a == '(' && b == ')') ||
                (a == '[' && b == ']') ||
                (a == '{' && b == '}');

    }

    /**
     * Matcher paranteser
     * @param parantes
     * @return
     */

    public static char matchendeParantes(char parantes) {
        if (parantes == '(') {
            return ')';
        } else if (parantes == '[') {
            return ']';
        } else {
            return '}';
        }
    }


    /**
     * Fjerner nuller
     * @param array
     * @return
     */
    public static String[] trimNulls(String[] array) {
        int nullCount = 0;
        for (String s : array) {
            if (s == null) {
                nullCount++;
            }
        }

        String[] resultat = new String[array.length - nullCount];
        int index = 0;
        for (String s : array) {
            if (s != null) {
                resultat[index++] = s;
            }
        }

        return resultat;
    }


    /**
     * Main metode
     * @param args
     */
    public static void main(String[] args) {
        String filnavn = "TestUtenFeil.java";
        String kode = lesKodeFraFil(filnavn);
        String[] feilMeldinger = sjekkParanteser(kode);

        if (feilMeldinger.length == 0) {
            System.out.println("Koden har riktig nøstingsstruktur.");
        } else {
            for (String melding : feilMeldinger) {
                System.out.println(melding);
            }
        }
    }
}
