package stub;

import model.API.WikipediaSearchAPI;
import retrofit2.Call;
import retrofit2.mock.Calls;

public class WikipediaSearchAPIStub implements WikipediaSearchAPI {

    @Override
    public Call<String> searchForTerm(String term) {
        String response = """
            {
                "query": {
                    "search": [
                        {
                            "title": "Breaking Bad",
                            "pageid": "12345",
                            "snippet": "Snippet for Breaking Bad"
                        },
                        {
                            "title": "Better Call Saul",
                            "pageid": "67890",
                            "snippet": "Snippet for Better Call Saul"
                        }
                    ]
                }
            }
            """;

        return Calls.response(response);
    }

    @Override
    public Call<String> getPageImages(String titles) {
        String response = """
            {
                "query": {
                    "pages": {
                        "12345": {
                            "pageid": 12345,
                            "title": "Breaking Bad",
                            "thumbnail": {
                                "source": "https://upload.wikimedia.org/wikipedia/en/6/61/Breaking_Bad_title_card.png"
                            }
                        },
                        "67890": {
                            "pageid": 67890,
                            "title": "Better Call Saul",
                            "thumbnail": {
                                "source": "https://upload.wikimedia.org/wikipedia/en/7/77/Better_Call_Saul_logo.png"
                            }
                        }
                    }
                }
            }
            """;
        return Calls.response(response);
    }
}
