package drankbank.android.drankbank;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
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
    @BindView(R.id.etFavName)
    EditText etFavName;
    @BindView (R.id.etFavDescrp)
    EditText etFavDescrp;

    public static final int REQUEST_DRINK = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_favorite);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnAddFavorite)
    void sendClick() {
        if (!isEntryValid()) {
            return;
        }

        Drink drink = new Drink(etFavName.getText().toString(), etFavDescrp.getText().toString());
        String drinkId = FirebaseDatabase.getInstance().getReference().
                child("users").child(getUid()).child("favorites").push().getKey();
        FirebaseDatabase.getInstance().getReference().child("users").child(getUid()).
                child("favorites").child(drinkId).setValue(drink);
        Log.d("TAG_FIREBASE", "Drink successfully added to: " + getUid());

        finish();
    }

    @OnClick(R.id.btnCancel)
    void cancelClick() {
        finish();
    }

    private boolean isEntryValid() {
        boolean result = true;
        if (TextUtils.isEmpty(etFavName.getText().toString())) {
            etFavName.setError("Required");
            result = false;
        } else {
            etFavName.setError(null);
        }

        if (TextUtils.isEmpty(etFavDescrp.getText().toString())) {
            etFavDescrp.setError("Required");
            result = false;
        } else {
            etFavDescrp.setError(null);
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
                    etFavName.setText(drink.getName());
                    etFavDescrp.setText(drink.getDescrp());
                } else {
                    Toast.makeText(this, "Unable to add result", Toast.LENGTH_LONG).show();
                }
        }
    }
}
