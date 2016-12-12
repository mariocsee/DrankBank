package drankbank.android.drankbank.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import drankbank.android.drankbank.BaseActivity;
import drankbank.android.drankbank.R;
import drankbank.android.drankbank.data.Drink;
import drankbank.android.drankbank.data.Entry;

/**
 * Created by Veronica on 12/1/16.
 * Adapter to handle list of today's drinks.
 */

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.ViewHolder> {
    /*
    Creates view for a single drink display
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvDescrp;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvDescrp = (TextView) itemView.findViewById(R.id.tvDescrp);
        }
    }

    private List<Drink> entryList;
    private DatabaseReference drinksRef;
    private Context context;
    private int lastPosition = -1;
    private String currDate;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_search, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public EntryAdapter(Context context, String currDate) {
        this.entryList = new ArrayList<Drink>();
        this.context = context;
        this.currDate = currDate;

        drinksRef = FirebaseDatabase.getInstance().getReference(currDate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvName.setText(entryList.get(position).getName());
        holder.tvDescrp.setText(entryList.get(position).getType());

        setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return entryList.size();
    }

    /*
    Removes entry from display listB
    */
    public void removeEntry(int position) {
        // remove it from the list
        entryList.remove(position);
        notifyDataSetChanged();
    }

    /*
    Adds entry to display list
    */
    public void addEntry(Drink d) {
        entryList.add(d);
        notifyDataSetChanged();
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context,
                    android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}
