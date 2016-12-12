package drankbank.android.drankbank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import drankbank.android.drankbank.data.Drink;

/**
 * Created by Veronica on 12/7/16.
 */

public class ShowDrinkActivity extends AppCompatActivity {
    @BindView(R.id.ivDrinkIcon)
    ImageView ivDrink;
    @BindView(R.id.tvDrinkName)
    TextView tvName;
    @BindView(R.id.tvDrinkDescrp)
    TextView tvDescrp;

    private Drink drink;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_drink);
        ButterKnife.bind(this);
        //get drink info
        Bundle b = getIntent().getExtras();
        drink = (Drink) b.getSerializable(SearchActivity.KEY_SHOW_DRINK);

        // set values
        tvName.setText(drink.getName());

        tvDescrp.setText(drink.getDescrp());
        ivDrink.setImageResource(drink.getIcon());
    }
}
