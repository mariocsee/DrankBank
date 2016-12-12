package drankbank.android.drankbank.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import drankbank.android.drankbank.BaseActivity;
import drankbank.android.drankbank.MainActivity;
import drankbank.android.drankbank.R;
import drankbank.android.drankbank.adapter.EntryAdapter;
import drankbank.android.drankbank.data.Entry;
import drankbank.android.drankbank.touch.EntryListTouchHelper;

/**
 * Created by mariocsee on 12/12/16.
 */

public class FragmentCard extends Fragment {

    private RecyclerView recyclerEntry;
    private EntryAdapter entryAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.content_main, null);

        List<Entry> entryList = new ArrayList<>();

        entryAdapter = new EntryAdapter(FragmentCard.this.getContext(), "ADAPTER");

        recyclerEntry = (RecyclerView) rootView.findViewById(R.id.recyclerEntry);
        recyclerEntry.setLayoutManager(new LinearLayoutManager(FragmentCard.this.getContext()));
        recyclerEntry.setAdapter(entryAdapter);

        EntryListTouchHelper touchHelperCallback = new EntryListTouchHelper(
                entryAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(
                touchHelperCallback);
        touchHelper.attachToRecyclerView(recyclerEntry);

        return rootView;
    }
}
