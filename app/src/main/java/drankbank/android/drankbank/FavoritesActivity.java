package drankbank.android.drankbank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import drankbank.android.drankbank.adapter.EntryAdapter;
import drankbank.android.drankbank.adapter.FavoritesAdapter;

/**
 * Created by mariocsee on 12/12/16.
 */

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView recyclerFavorites;
    private LinearLayoutManager layoutManager;
    private FavoritesAdapter favoritesAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }
}
