package drankbank.android.drankbank;

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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
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
        RecyclerView recyclerEntry = (RecyclerView) rv.findViewById(R.id.todayDrinkList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        // most recent drink first
        layoutManager.setRecycleChildrenOnDetach(true);
        layoutManager.setStackFromEnd(true);
        recyclerEntry.setLayoutManager(layoutManager);
        recyclerEntry.setAdapter(entryAdapter);

        initEntryListener();

        return rv;
    }

    private void initEntryListener() {
        String path = "users/" + ((MainActivity)getContext).getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(curDate);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Drink newDrink = dataSnapshot.getValue(Drink.class);
                entryAdapter.addEntry(newDrink, dataSnapshot.getKey());
                Log.d("TAG_FIREBASE", "Drink fetched: " + dataSnapshot.getKey());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("TAG_FIREBASE", "Fetch error: " + databaseError.getMessage());
            }
        });
    }
}
