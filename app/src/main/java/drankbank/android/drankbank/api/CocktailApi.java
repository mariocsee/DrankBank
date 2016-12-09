package drankbank.android.drankbank.api;

import java.util.List;

import drankbank.android.drankbank.model.CocktailModel.CocktailResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Veronica on 12/8/16.
 */

public interface CocktailApi {
    @GET("search.php")
    Call<List<CocktailResult>> searchCocktailName(@Query("s") String name);

    @GET("lookup.php")
    Call<CocktailResult> lookupCocktailName(@Query("i") int id);
}
