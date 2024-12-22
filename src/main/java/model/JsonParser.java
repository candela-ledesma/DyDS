package model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;

public class JsonParser {
    private final Gson gson;

    public JsonParser() {
        this.gson = new Gson();
    }

    public static JsonObject parseJson(Call<String> pageImagesByPageId) throws IOException {
        Response<String> response = pageImagesByPageId.execute();
        if (response.isSuccessful() && response.body() != null) {
            return new Gson().fromJson(response.body(), JsonObject.class);
        } else {
            throw new IOException("Failed to fetch page images: " + response.errorBody().string());
        }
    }

    public LinkedList<Serie> parseSearchResults(String jsonResponse) {
        LinkedList<Serie> searchResults = new LinkedList<>();
        JsonObject query = gson.fromJson(jsonResponse, JsonObject.class)
                .get("query").getAsJsonObject();
        JsonArray jsonResults = query.get("search").getAsJsonArray();

        for (JsonElement element : jsonResults) {
            JsonObject result = element.getAsJsonObject();
            Serie serie = new Serie(
                    result.get("title").getAsString(),
                    result.get("pageid").getAsString(),
                    result.get("snippet").getAsString()
            );
            searchResults.add(serie);
        }

        return searchResults;
    }

    public String parsePageExtract(String jsonResponse, Serie searchResult) {
        JsonObject pages = gson.fromJson(jsonResponse, JsonObject.class)
                .get("query").getAsJsonObject()
                .get("pages").getAsJsonObject();

        Map.Entry<String, JsonElement> firstPage = pages.entrySet().iterator().next();
        JsonObject page = firstPage.getValue().getAsJsonObject();
        JsonElement pageExtract = page.get("extract");

        if (pageExtract == null) {
            return "No Results";
        } else {
            String extract = "<h1>" + searchResult.getTitle() + "</h1>";
            extract += pageExtract.getAsString().replace("\\n", "\n");
            return extract;
        }
    }

    public String parsePageImageUrl(String body, Serie searchResult) {
        // Parseamos el JSON completo
        JsonObject jsonResponse = gson.fromJson(body, JsonObject.class);

        // Navegamos al objeto "pages" dentro de "query"
        JsonObject pages = jsonResponse.get("query").getAsJsonObject()
                .get("pages").getAsJsonObject();

        // Iteramos por las entradas de "pages" para buscar la página que coincide con el ID
        for (Map.Entry<String, JsonElement> entry : pages.entrySet()) {
            JsonObject page = entry.getValue().getAsJsonObject();

            // Comparamos el pageid con el de la Serie
            if (page.get("pageid").getAsString().equals(searchResult.getPageID())) {
                JsonObject thumbnail = page.getAsJsonObject("thumbnail");

                // Verificamos si existe un campo "source" dentro de "thumbnail"
                if (thumbnail != null && thumbnail.has("source")) {
                    return thumbnail.get("source").getAsString();
                }
            }
        }

        // Si no encontramos un thumbnail, retornamos un valor por defecto o indicamos ausencia
        return "No image available";
    }

}