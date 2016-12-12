package drankbank.android.drankbank;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
 * Created by Veronica on 11/21/16.
 */

public class CreateEntryActivity extends BaseActivity {
    @BindView(R.id.etEntryName)
    EditText etEntryName;
    @BindView (R.id.etEntryDescrp)
    EditText etEntryDescrp;

    private TextView tvDate;
    private Spinner spinType;

    public static final int REQUEST_DRINK = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_entry);

        ButterKnife.bind(this);

        tvDate = (TextView) findViewById(R.id.entryDate);
        tvDate.setText(getCurrDate());

        // set up spinner
        spinType = (Spinner) findViewById(R.id.spinType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type_array, android.R.layout.simple_dropdown_item_1line);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinType.setAdapter(adapter);
    }

    /*@OnTouch(R.id.etName)
    boolean startSearch() {
        Intent intentShowSearch = new Intent();
        intentShowSearch.setClass(this, SearchActivity.class);
        intentShowSearch.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivityForResult(intentShowSearch, REQUEST_DRINK);
        return true;
    }*/

    /*
    Adds drink to firebase user's database
     */
    @OnClick(R.id.btnAddEntry)
    void sendClick() {
        if (!isEntryValid()) {
            return;
        }
        // pushes drinks to database of current date (MM dd yyyy format)
        Drink d = new Drink(etEntryName.getText().toString(), etEntryDescrp.getText().toString());
        // get list index of added drink
        String drinkId = FirebaseDatabase.getInstance().getReference().
                child("users").child(getUid()).child("today").push().getKey();
        FirebaseDatabase.getInstance().getReference().child("users").child(getUid()).
                child("today").child(drinkId).setValue(d);
        Log.d("TAG_FIREBASE", "Drink successfully added to: " + getUid());

        finish();
    }

    @OnClick(R.id.btnCancel)
    void cancelClick() {
        finish();
    }

    private boolean isEntryValid() {
        boolean result = true;
        if (TextUtils.isEmpty(etEntryName.getText().toString())) {
            etEntryName.setError("Required");
            result = false;
        } else {
            etEntryName.setError(null);
        }

        if (TextUtils.isEmpty(etEntryDescrp.getText().toString())) {
            etEntryDescrp.setError("Required");
            result = false;
        } else {
            etEntryDescrp.setError(null);
        }
        return result;
    }

    /*
    Handles result from search
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case REQUEST_DRINK:
                Log.d("TAG_ADD", "Detected drink result");
                if (requestCode == RESULT_OK) {
                    //get drink info
                    Bundle b = getIntent().getExtras();
                    Drink d = (Drink) b.getSerializable(SearchActivity.KEY_ADD_DRINK);
                    Log.d("TAG_ADD", "Getting drink info for " + d.getName());
                    etEntryName.setText(d.getName());
                    etEntryDescrp.setText(d.getDescrp());
                } else {
                    Toast.makeText(this, "Unable to add result", Toast.LENGTH_LONG).show();
                }
        }
    }
}
