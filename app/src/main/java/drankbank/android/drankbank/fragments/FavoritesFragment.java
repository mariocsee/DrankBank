package drankbank.android.drankbank.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import drankbank.android.drankbank.CreateEntryActivity;
import drankbank.android.drankbank.CreateFavoriteActivity;
import drankbank.android.drankbank.MainActivity;
import drankbank.android.drankbank.R;
import drankbank.android.drankbank.adapter.FavoritesAdapter;
import drankbank.android.drankbank.data.Drink;

/**
 * Created by mariocsee on 12/12/16.
 */

public class FavoritesFragment extends Fragment {

    private FavoritesAdapter favoritesAdapter;
    private RecyclerView recyclerFavorites;
    private LinearLayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorites, null);
        favoritesAdapter = new FavoritesAdapter(getContext());
        recyclerFavorites = (RecyclerView) rootView.findViewById(R.id.favoriteDrinkList);
        layoutManager = new LinearLayoutManager(getContext());
        setUpRecycler();
        setUpTouch();
        initFavoriteListener();

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fabAddFavorite);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CreateFavoriteActivity.class));
            }
        });

        return rootView;
    }

    private void setUpRecycler() {
        layoutManager.setRecycleChildrenOnDetach(true);
        layoutManager.getChildAt(0);
        recyclerFavorites.setLayoutManager(layoutManager);
        recyclerFavorites.setAdapter(favoritesAdapter);
    }

    private void setUpTouch() {
        ItemTouchHelper.SimpleCallback itemTouchCallback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                favoritesAdapter.removeFavorite(position);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerFavorites);
    }

    private void initFavoriteListener() {
        String path = "users/" + ((MainActivity)getActivity()).getUid() + "/favorites";
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(path);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("TAG_FIREBASE", "Drink fetched: " + dataSnapshot.getKey());
                Drink newDrink = dataSnapshot.getValue(Drink.class);
                Log.d("TAG_FIREBASE", "Drink: " + newDrink.getName());
                favoritesAdapter.addFavorite(newDrink, dataSnapshot.getKey());
                // scroll to top when new item is added
                recyclerFavorites.smoothScrollToPosition(0);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
//                favoritesAdapter.removeEntry();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
