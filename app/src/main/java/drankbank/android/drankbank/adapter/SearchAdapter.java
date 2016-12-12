package drankbank.android.drankbank.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import drankbank.android.drankbank.R;
import drankbank.android.drankbank.SearchActivity;
import drankbank.android.drankbank.ShowDrinkActivity;
import drankbank.android.drankbank.data.Drink;

/**
 * Created by Veronica on 12/5/16.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    /*
    Creates view for a single drink display
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvType;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvType = (TextView) itemView.findViewById(R.id.tvType);
            itemView.setClickable(true);
        }
    }

    private LayoutInflater layoutInflater;
    private List<Drink> searchList;
    private Context context;

    public SearchAdapter(List<Drink> drinkList, Context context) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.searchList = drinkList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_search, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    /*
    Sets information to display on view from associated search data
    */
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvName.setText(searchList.get(position).getName());
        holder.tvType.setText(searchList.get(position).getType());
        // allows whole row to be clicked
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // clicking row will start new activity that displays beers
                ((SearchActivity) context).handleDrinkClick(position);
            }
        });
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

    public Drink getDrink(int position) {
        return searchList.get(position);
    }
}