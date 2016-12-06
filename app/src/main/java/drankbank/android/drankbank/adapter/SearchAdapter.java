package drankbank.android.drankbank.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import drankbank.android.drankbank.R;
import drankbank.android.drankbank.data.Drink;

/**
 * Created by Veronica on 12/5/16.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private LayoutInflater layoutInflater;
    private List<Drink> listDrink;
    /*
    Creates view for a single drink display
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvDescrp;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    private List<Drink> searchList;
    private Context context;
    private int lastPosition = -1;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_entry, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public SearchAdapter(List<Drink> drinkList, Context context) {
        layoutInflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.searchList = drinkList;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    /*
    Removes entry from display listB
    */
    public void removeDrink(int position) {
        // remove it from the list
        searchList.remove(position);
        notifyDataSetChanged();
    }

    /*
    Adds entry to display list
    */
    public void addDrink(Drink d) {
        searchList.add(d);
        notifyDataSetChanged();
    }
}