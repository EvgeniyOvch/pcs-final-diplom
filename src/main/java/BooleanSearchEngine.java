import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BooleanSearchEngine implements SearchEngine {
    private final Map<String, List<PageEntry>> totalWords = new HashMap<>();

    public BooleanSearchEngine(File pdfsDir) throws IOException {

        for (File pdf : Objects.requireNonNull(pdfsDir.listFiles())) {
            var doc = new PdfDocument(new PdfReader(pdf));
            int length = doc.getNumberOfPages();
            for (int i = 1; i <= length; i++) {
                var page = doc.getPage(i);
                var text = PdfTextExtractor.getTextFromPage(page);
                var words = text.split("\\P{IsAlphabetic}+");

                Map<String, Integer> freqs = new HashMap<>(); // мапа, где ключом будет слово, а значением - частота
                for (var word : words) { // перебираем слова
                    if (word.isEmpty()) {
                        continue;
                    }
                    word = word.toLowerCase();
                    freqs.put(word, freqs.getOrDefault(word, 0) + 1);
                }
                for (var entry : freqs.entrySet()) {
                    List<PageEntry> searchingResult;
                    if (!totalWords.containsKey(entry.getKey())) {
                        searchingResult = new ArrayList<>();

                    } else {
                        searchingResult = totalWords.get(entry.getKey());
                    }
                    searchingResult.add(new PageEntry(pdf.getName(), i, entry.getValue()));
                    totalWords.put(entry.getKey(), searchingResult);
                }
            }
            // прочтите тут все pdf и сохраните нужные данные,
            // тк во время поиска сервер не должен уже читать файлы
        }
    }

    @Override
    public List<PageEntry> search(String word) {
        List<PageEntry> result = totalWords.get(word);
        // тут реализуйте поиск по слову
        return result;
    }
}
