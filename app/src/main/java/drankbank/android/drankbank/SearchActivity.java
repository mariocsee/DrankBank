package drankbank.android.drankbank;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

import drankbank.android.drankbank.adapter.SearchAdapter;
import drankbank.android.drankbank.api.BeerApi;
import drankbank.android.drankbank.data.Drink;
import drankbank.android.drankbank.model.api.BeerModel.BeerResult;
import drankbank.android.drankbank.model.api.BeerModel.Datum;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Veronica on 12/5/16.
 * <p>
 * Tutorial: https://inducesmile.com/android/android-how-to-add-search-widget-and-searchview-implementation-in-android-ui/
 * RETRO Tutorial: https://medium.com/@ocittwo/android-searchview-recyclerview-retrofit-56b588331e19#.1yz6ca5q9
 */

public class SearchActivity extends BaseActivity {
    public static final String KEY_ADD_DRINK = "KEY_ADD_DRINK";
    public static final String KEY_SHOW_DRINK = "KEY_SHOW_DRINK";

    final public String apiid = "afd6029fc44f55b5425c752789bbb896";
    private BeerApi beerApi;
    private SearchAdapter searchAdapter;
    private RecyclerView recyclerSearch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.brewerydb.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        beerApi = retrofit.create(BeerApi.class);

        recyclerSearch = (RecyclerView) findViewById(
                R.id.recyclerSearch);
        recyclerSearch.setLayoutManager(new LinearLayoutManager(this));
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
        searchAdapter = new SearchAdapter(new ArrayList<Drink>(), this);
        Log.d("TAG_QUERY", "Query input: " + query);
        Call<BeerResult> beerCall = beerApi.getBeerName(apiid, query,
                getString(drankbank.android.drankbank.R.string.txt_beer));
        beerCall.enqueue(new Callback<BeerResult>() {
            @Override
            public void onResponse(Call<BeerResult> call, Response<BeerResult> response) {
                // checks for valid input
                if (response.body().getData() != null) {
                    for (Datum result : response.body().getData()) {
                        Log.d("TAG_QUERY", "Successful call: " + result.getName());
                        // new drink from result
                        Drink d = new Drink(result);
                        // add to adapter
                        searchAdapter.addDrink(d);
                        Log.d("TAG_QUERY", "Successfully added: " + result.getName());
                    }
                } else {
                    // Make a toast saying no response?
                }
                recyclerSearch.setAdapter(searchAdapter);
            }

            @Override
            public void onFailure(Call<BeerResult> call, Throwable t) {
                Log.d("TAG_QUERY", "Call failed: " + t);
            }
        });
    }
    /*
    Displays different activity based on search start
     */
    public void handleDrinkClick(int position) {
        Drink d = searchAdapter.getDrink(position);
        if (getCallingActivity() != null) {
            // Check if called by add entry
            if (getCallingActivity().getClassName().equals(CreateEntryActivity.class.getName())) {
                Log.d("TAG_ENTRY", "Adding drink: " + d.getName() + " to "
                        + getCallingActivity().getClassName());
                Intent intentAdd = new Intent();
                intentAdd.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentAdd.putExtra(KEY_ADD_DRINK, d);
                setResult(RESULT_OK, intentAdd);
                Log.d("TAG_ENTRY", "RESULT OK");
                finish();
                //onDestroy();

            }
        } else {
            // if activity not started by entry, then show drink details
            showDrinkActivity(d);
        }
    }


    /*
    Shows information associated with item row
     */
    public void showDrinkActivity(Drink d) {
        Log.d("TAG_DRINK", "Bundling drink: " + d.getName());
        Intent intentShow = new Intent(this, ShowDrinkActivity.class);
        intentShow.putExtra(KEY_SHOW_DRINK, d);
        intentShow.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intentShow);
    }
}
