package drankbank.android.drankbank.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
    private TextView tvComment;

    private EntryAdapter entryAdapter;
    private LinearLayoutManager layoutManager;
    private String curDate;
    private RecyclerView recyclerEntry;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        curDate = ((MainActivity) getContext()).getCurrDate();
        entryAdapter = new EntryAdapter(getActivity(), curDate);

        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setRecycleChildrenOnDetach(true);
        layoutManager.setStackFromEnd(true);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
<<<<<<< HEAD
        View rv = inflater.inflate(R.layout.fragment_today, null);
        curDate = ((MainActivity) getContext()).getCurrDate();

        tvComment = (TextView) rv.findViewById(R.id.todayComment);
        tvDate = (TextView) rv.findViewById(R.id.todayDate);
        tvDate.setText(curDate);
        tvDrinkCount = (TextView) rv.findViewById(R.id.todayDrinkCount);
        tvDrinkCount.setText(R.string.drink_count_initial);
=======
        View rv = inflater.inflate(R.layout.content_today, null);
>>>>>>> origin/master

        // initiate holder for today's drinks
        entryAdapter = new EntryAdapter(getContext(), curDate);
        recyclerEntry = (RecyclerView) rv.findViewById(R.id.todayDrinkList);
        setUpRecycler();

        tvDate = (TextView) rv.findViewById(R.id.todayDate);
        tvDate.setText(curDate);
        tvDrinkCount = (TextView) rv.findViewById(R.id.todayDrinkCount);
        tvDrinkCount.setText(R.string.drink_count_initial);

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

/*        EntryListTouchHelper touchHelperCallback = new EntryListTouchHelper(entryAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(touchHelperCallback);
        touchHelper.attachToRecyclerView(recyclerEntry);*/

        // For whatever reason, this works but not the code above??
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
                entryAdapter.removeEntry(position);

                updateDrinkNum(entryAdapter.getItemCount());
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerEntry);
    }

    /*
    Listens for changes
     */
    private void initEntryListener() {
        // get's what stored for the user that day
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
                // updates number of drinks
                updateDrinkNum(entryAdapter.getItemCount());
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

    /*
    Updates drink number and comment
     */
    private void updateDrinkNum(int count) {
        tvDrinkCount.setText(getString(R.string.drink_count, count));
        if (count == 0) {
            tvComment.setText(getString(R.string.drink_count_initial));
        } else if (count <= 3) {
            tvComment.setText(getString(R.string.drink_count_good));
        } else if (count <= 6) {
            tvComment.setText(getString(R.string.drink_count_okay));
        } else {
            tvComment.setText(getString(R.string.drink_count_bad));
        }
    }
}
