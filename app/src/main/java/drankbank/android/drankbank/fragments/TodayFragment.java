package drankbank.android.drankbank.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import drankbank.android.drankbank.MainActivity;
import drankbank.android.drankbank.R;
import drankbank.android.drankbank.adapter.EntryAdapter;
import drankbank.android.drankbank.data.Drink;

/**
 * Created by Veronica on 12/12/16.
 */

public class TodayFragment extends Fragment {
    private TextView tvDate;
    private TextView tvDrinkCount;

    private EntryAdapter entryAdapter;
    private String curDate;
    private RecyclerView recyclerEntry;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rv = inflater.inflate(R.layout.content_today, null);
        curDate = ((MainActivity) getContext()).getCurrDate();

        tvDate = (TextView) rv.findViewById(R.id.todayDate);
        tvDate.setText(curDate);
        tvDrinkCount = (TextView) rv.findViewById(R.id.todayDrinkCount);
        tvDrinkCount.setText(R.string.drink_count_initial);

        // initiate holder for today's drinks
        entryAdapter = new EntryAdapter(getContext(), curDate);
        recyclerEntry = (RecyclerView) rv.findViewById(R.id.todayDrinkList);
        setUpRecycler();

        initEntryListener();

        return rv;
    }

    private void setUpRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        // most recent drink at top of stack
        layoutManager.setRecycleChildrenOnDetach(true);
        layoutManager.getChildAt(0);
        recyclerEntry.setLayoutManager(layoutManager);
        recyclerEntry.setAdapter(entryAdapter);
    }

    private void initEntryListener() {
        String path = "users/" + ((MainActivity)getActivity()).getUid() + "/today";
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(path);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("TAG_FIREBASE", "Drink fetched: " + dataSnapshot.getKey());
                Drink newDrink = dataSnapshot.getValue(Drink.class);
                Log.d("TAG_FIREBASE", "Drink: " + newDrink.getName());
                entryAdapter.addEntry(newDrink, dataSnapshot.getKey());
                // scroll to top when new item is added
                recyclerEntry.smoothScrollToPosition(0);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
//                entryAdapter.removeEntry();
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
