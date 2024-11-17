import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Main {

        private static final int MAX_LEVEL = 3;

        public static void main(String[] args) {
                System.out.println("Inserisci l'URL iniziale: ");
                java.util.Scanner inputScanner = new java.util.Scanner(System.in);
                String initialURL = inputScanner.nextLine();
                inputScanner.close();

                // Esegui il crawling in profondità (DFS)
                exploreLinks(new HashSet<>(), initialURL, MAX_LEVEL, 0);
        }

        // Funzione per esplorazione tramite DFS
        public static Set<String> exploreLinks(Set<String> discoveredLinks, String currentPage, int maxLevel, int currentLevel) {

                // Aggiungi l'URL corrente al set dei link già visitati
                discoveredLinks.add(currentPage);
                System.out.println("Livello: " + currentLevel + " | URL: " + currentPage);

                if (maxLevel < 0 || currentLevel < maxLevel) {
                        try {
                                // Recupera il contenuto HTML della pagina corrente
                                Document document = Jsoup.connect(currentPage).get();
                                Elements linkElements = document.select("a[href]");

                                for (Element link : linkElements) {
                                        String nextURL = link.absUrl("href");
                                        // Se il link non è già stato visitato, esploralo ricorsivamente
                                        if (!discoveredLinks.contains(nextURL)) {
                                                discoveredLinks = exploreLinks(discoveredLinks, nextURL, maxLevel, currentLevel + 1);
                                        }
                                }
                        } catch (IOException e) {
                                System.out.println("Errore nell'accesso a: " + currentPage + " | " + e.getMessage());
                        }
                } else {
                        return discoveredLinks;
                }

                return discoveredLinks;
        }
}