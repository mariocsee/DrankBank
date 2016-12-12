package drankbank.android.drankbank;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import drankbank.android.drankbank.adapter.EntryAdapter;
import drankbank.android.drankbank.data.Drink;

/**
 * Created by mariocsee on 12/9/16.
 */

public class TodayActivity extends BaseActivity {
    //@BindView(R.id.todayDate)
    private TextView tvDate;
    @BindView(R.id.todayDrinkCount)
    TextView tvDrinkCount;

    private EntryAdapter entryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_today);

        ButterKnife.bind(this);

        tvDate = (TextView) findViewById(R.id.todayDate);
        tvDate.setText(getCurrDate());

        // initiate holder for today's drinks
        entryAdapter = new EntryAdapter(getApplicationContext(), getCurrDate());
        setUpRecyclerView();

        initEntryListener();

        entryAdapter.addEntry(new Drink("Test", "testing"));
    }

    private void setUpRecyclerView() {
        RecyclerView recyclerEntry = (RecyclerView) findViewById(R.id.todayDrinkList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // most recent drink first
        layoutManager.setRecycleChildrenOnDetach(true);
        layoutManager.setStackFromEnd(true);
        recyclerEntry.setLayoutManager(layoutManager);
        recyclerEntry.setAdapter(entryAdapter);
    }

    private void initEntryListener() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(getCurrDate());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Drink newDrink = dataSnapshot.getValue(Drink.class);
                entryAdapter.addEntry(newDrink);
                Log.d("TAG_FIREBASE", "Drink fetched: " + newDrink.getName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("TAG_FIREBASE", "Fetch error: " + databaseError.getMessage());
            }
        });
    }
}
