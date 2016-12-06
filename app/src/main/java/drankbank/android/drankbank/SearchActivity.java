package drankbank.android.drankbank;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import drankbank.android.drankbank.api.BeerApi;
import drankbank.android.drankbank.model.BeerModel.BeerResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Veronica on 12/5/16.
 *
 * Tutorial: https://inducesmile.com/android/android-how-to-add-search-widget-and-searchview-implementation-in-android-ui/
 */

public class SearchActivity extends BaseActivity{
    ArrayAdapter<String> adapter;
    final public String apiid = "afd6029fc44f55b5425c752789bbb896";
    private BeerApi beerApi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.brewerydb.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        beerApi = retrofit.create(BeerApi.class);

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the options menu from XML; adds items to action bar if present
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.svMenu).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.svMenu:
                onSearchRequested();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*
    Obtain search string to query API
    */
    private void doMySearch(String query) {
        Call<BeerResult> beerCall = beerApi.getBeerName(apiid, query,
                getString(drankbank.android.drankbank.R.string.txt_beer));
        beerCall.enqueue(new Callback<BeerResult>() {
            @Override
            public void onResponse(Call<BeerResult> call, Response<BeerResult> response) {
                Log.d("TAG_QUERY", "Call: " + response.body().getData().get(0).getName());
            }

            @Override
            public void onFailure(Call<BeerResult> call, Throwable t) {
                Log.d("TAG_QUERY", "Call failed: " + t);
            }
        });

        /*
        List<ItemObject> dictionaryObject = databaseObject.searchDictionaryWords(query);
        mSearchAdapter = new SearchAdapter(SearchableActivity.this, dictionaryObject);
        listView.setAdapter(mSearchAdapter);
        */
    }
}
