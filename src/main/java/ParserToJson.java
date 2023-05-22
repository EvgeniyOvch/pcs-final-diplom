import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Collections;
import java.util.List;

public class ParserToJson {
    public String listToJson(List<PageEntry> pageEntryList) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        Collections.sort(pageEntryList);

        return gson.toJson(pageEntryList);
    }
}
