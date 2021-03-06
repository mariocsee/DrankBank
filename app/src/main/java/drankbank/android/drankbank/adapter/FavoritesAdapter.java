package drankbank.android.drankbank.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
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
import drankbank.android.drankbank.ShowDrinkActivity;
import drankbank.android.drankbank.data.Drink;
import drankbank.android.drankbank.fragments.TodayFragment;

/**
 * Created by mariocsee on 12/12/16.
 */

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

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

    private List<Drink> favoriteList;
    private List<String> favoriteKeys;
    private DatabaseReference favoriteRef;
    private Context context;
    private int lastPosition = -1;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate((R.layout.row_search), parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public FavoritesAdapter(Context context) {
        this.context = context;
        this.favoriteList = new ArrayList<Drink>();
        this.favoriteKeys = new ArrayList<String>();

        String path = "users/" + ((MainActivity)context).getUid();
        favoriteRef = FirebaseDatabase.getInstance().getReference(path);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvName.setText(favoriteList.get(position).getName());
        holder.tvType.setText(favoriteList.get(position).getType());
        holder.imgDrink.setImageResource(favoriteList.get(position).getIcon());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drink d = favoriteList.get(position);
                Intent intentShow = new Intent(context, ShowDrinkActivity.class);
                intentShow.putExtra(TodayFragment.KEY_SHOW_DRINK, d);
                intentShow.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intentShow);
            }
        });
    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }

    public void addFavorite(Drink drink, String key) {
        favoriteList.add(0, drink);
        favoriteKeys.add(0, key);
        notifyItemInserted(0);
    }

    public void removeFavorite(int position) {
        favoriteList.remove(position);
        favoriteRef.child("favorites").child(favoriteKeys.get(position)).removeValue();
        notifyItemRemoved(position);
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
