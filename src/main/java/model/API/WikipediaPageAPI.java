package model.API;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WikipediaPageAPI {

  @GET("api.php?format=json&action=query&prop=extracts&exlimit=1&exintro=1")
  Call<String> getExtractByPageID(@Query("pageids") String term);

  @GET("api.php?action=query&prop=pageimages&format=json&piprop=original&pilicense=any")
  Call<String> getPageImagesByPageId(@Query("pageids") int pageid);

}
