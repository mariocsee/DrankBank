package drankbank.android.drankbank;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import drankbank.android.drankbank.data.Drink;

/**
 * Created by mariocsee on 12/12/16.
 */

public class CreateFavoriteActivity extends BaseActivity {
    @BindView(R.id.etNameFav)
    EditText etNameFav;
    @BindView (R.id.etDescrpFav)
    EditText etDescrpFav;

    private Spinner spinTypeFav;

    public static final int REQUEST_DRINK = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_favorite);

        ButterKnife.bind(this);

        // set up spinner
        spinTypeFav = (Spinner) findViewById(R.id.spinTypeFav);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type_array, android.R.layout.simple_dropdown_item_1line);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinTypeFav.setAdapter(adapter);
    }

    @OnClick(R.id.btnAddFav)
    void sendClick() {
        if (!isEntryValid()) {
            return;
        }

        Drink drink = new Drink(etNameFav.getText().toString(), etDescrpFav.getText().toString());
        drink.setIconAndType(spinTypeFav.getSelectedItemPosition());
        String drinkId = FirebaseDatabase.getInstance().getReference().
                child("users").child(getUid()).child("favorites").push().getKey();
        FirebaseDatabase.getInstance().getReference().child("users").child(getUid()).
                child("favorites").child(drinkId).setValue(drink);
        Log.d("TAG_FIREBASE", "Drink successfully added to: " + getUid());

        finish();
    }

    @OnClick(R.id.btnCancelFav)
    void cancelClick() {
        finish();
    }

    private boolean isEntryValid() {
        boolean result = true;
        if (TextUtils.isEmpty(etNameFav.getText().toString())) {
            etNameFav.setError("Required");
            result = false;
        } else {
            etNameFav.setError(null);
        }

        if (TextUtils.isEmpty(etDescrpFav.getText().toString())) {
            etDescrpFav.setError("Required");
            result = false;
        } else {
            etDescrpFav.setError(null);
        }
        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case REQUEST_DRINK:
                Log.d("TAG_ADD", "Detected drink result");
                if (requestCode == RESULT_OK) {
                    //get drink info
                    Bundle bundle = getIntent().getExtras();
                    Drink drink = (Drink) bundle.getSerializable(SearchActivity.KEY_ADD_DRINK);
                    Log.d("TAG_ADD", "Getting drink info for " + drink.getName());
                    etNameFav.setText(drink.getName());
                    etDescrpFav.setText(drink.getDescrp());
                } else {
                    Toast.makeText(this, "Unable to add result", Toast.LENGTH_LONG).show();
                }
        }
    }
}
