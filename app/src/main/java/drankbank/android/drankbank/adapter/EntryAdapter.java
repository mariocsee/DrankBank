package drankbank.android.drankbank.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import drankbank.android.drankbank.R;
import drankbank.android.drankbank.model.Entry;

/**
 * Created by Veronica on 12/1/16.
 * Adapter to handle list of today's drinks.
 */

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.ViewHolder> {
    /*
    Creates view for a single drink display
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    private List<Entry> entryList;
    private Context context;
    private int lastPosition = -1;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_entry, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public EntryAdapter(List<Entry> entryList, Context context) {
        this.entryList = entryList;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return entryList.size();
    }

    /*
    Removes entry from display list and local DB
    */
    public void removeEntry(int position) {
        // remove it from SugarORM DB
        // entryList.get(position).delete();
        // remove it from the list
        entryList.remove(position);
        notifyDataSetChanged();
    }

    /*
    Adds weather to display list and local DB
    */
    public void addEntry(Entry e) {
        // Saves to SugarORM DB
        //e.save();
        entryList.add(e);
        notifyDataSetChanged();
    }

}
