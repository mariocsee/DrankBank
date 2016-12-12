package drankbank.android.drankbank.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import drankbank.android.drankbank.MainActivity;
import drankbank.android.drankbank.R;
import drankbank.android.drankbank.SearchActivity;
import drankbank.android.drankbank.ShowDrinkActivity;
import drankbank.android.drankbank.data.Drink;
import drankbank.android.drankbank.fragments.TodayFragment;

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
        private TextView tvType;
        private ImageView imgDrink;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvType = (TextView) itemView.findViewById(R.id.tvType);
            imgDrink = (ImageView) itemView.findViewById(R.id.ivDrink);
        }
    }

    private List<Drink> entryList;
    private List<String> entryKeys;
    private DatabaseReference entryRef;
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
        this.context = context;
        this.currDate = currDate;
        this.entryList = new ArrayList<Drink>();
        this.entryKeys = new ArrayList<String>();

        String path = "users/" + ((MainActivity)context).getUid();
        entryRef = FirebaseDatabase.getInstance().getReference(path);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvName.setText(entryList.get(position).getName());
        holder.tvType.setText(entryList.get(position).getType());
        holder.imgDrink.setImageResource(entryList.get(position).getIcon());

        // click on a row will start a new activity that displays info on it
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drink d = entryList.get(position);
                Intent intentShow = new Intent(context, ShowDrinkActivity.class);
                intentShow.putExtra(TodayFragment.KEY_SHOW_DRINK, d);
                intentShow.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intentShow);
            }
        });
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
        Log.d("TAG_FIREBASE", "Removing: " + entryRef.child("today").
                child(entryKeys.get(position)).getKey());
        entryRef.child("today").child(entryKeys.get(position)).removeValue();
        notifyItemRemoved(position);
    }

    /*
    Adds entry to display list
    */
    public void addEntry(Drink d, String key) {
        entryList.add(0, d);
        entryKeys.add(0, key);
        notifyItemInserted(0);
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context,
                    android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public Drink getEntry(int position) {
        return entryList.get(position);
    }
}
