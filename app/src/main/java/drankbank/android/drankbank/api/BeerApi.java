package drankbank.android.drankbank.api;

import drankbank.android.drankbank.model.BeerModel.BeerResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Veronica on 12/6/16.
 *
 * API Key: afd6029fc44f55b5425c752789bbb896
 *
 * Example call: http://api.brewerydb.com/v2/search?key=MYKEY&q=BeerName&type=Beer
 * http://api.brewerydb.com/v2/search?key=afd6029fc44f55b5425c752789bbb896&q=Boy&type=beer
 */

public interface BeerApi {
    @GET("/v2/search")
    Call<BeerResult> getBeerName(@Query("key") String apiid,
                                 @Query("q") String name,
                                 @Query("type") String type);
}
