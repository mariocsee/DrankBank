package drankbank.android.drankbank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;

import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.OnTouch;
import drankbank.android.drankbank.data.Drink;

/**
 * Created by Veronica on 11/21/16.
 */

public class CreateEntryActivity extends AppCompatActivity {
    @BindView(R.id.etName)
    EditText etName;
    @BindView (R.id.etDescrp)
    EditText etDescrp;

    public static final int REQUEST_DRINK = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_entry);

        ButterKnife.bind(this);
    }

    @OnTouch(R.id.etName)
    void startSearch() {
        Intent intentShowSearch = new Intent();
        intentShowSearch.setClass(this, SearchActivity.class);
        startActivityForResult(intentShowSearch, REQUEST_DRINK);
        return;
    }

    @OnClick(R.id.btnAdd)
    void sendClick() {
        if (!isFormValid()) {
            return;
        }


        /*String key = FirebaseDatabase.getInstance().getReference().child("posts").push().getKey();
        Entry newPost = new Entry(get(), getUserName(), etTitle.getText().toString(),
                etBody.getText().toString());

        FirebaseDatabase.getInstance().getReference().child(key).setValue(newPost);

        Toast.makeText(this, "Post created", Toast.LENGTH_SHORT).show();
        */

        finish();
    }

    private boolean isFormValid() {
        boolean result = true;
        if (TextUtils.isEmpty(etName.getText().toString())) {
            etName.setError("Required");
            result = false;
        } else {
            etName.setError(null);
        }

        if (TextUtils.isEmpty(etDescrp.getText().toString())) {
            etDescrp.setError("Required");
            result = false;
        } else {
            etDescrp.setError(null);
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
                if (requestCode == RESULT_OK) {
                    //get drink info
                    Bundle b = getIntent().getExtras();
                    Drink d = (Drink) b.getSerializable(SearchActivity.KEY_ADD_DRINK);
                    etName.setText(d.getName());
                    etDescrp.setText(d.getDescrp());
                } else {
                    // DO SOMETHING TO SHOW AN ERROR
                }
        }
    }
}
