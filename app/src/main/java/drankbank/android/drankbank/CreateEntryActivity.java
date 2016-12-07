package drankbank.android.drankbank;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;

import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Veronica on 11/21/16.
 */

public class CreateEntryActivity extends AppCompatActivity {
    @BindView(R.id.etTitle)
    EditText etTitle;
    @BindView (R.id.etBody)
    EditText etBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_entry);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnAdd)
    void sendClick() {
        if (!isFormValid()) {
            return;
        }


        String key = FirebaseDatabase.getInstance().getReference().child("posts").push().getKey();
        /*Entry newPost = new Entry(get(), getUserName(), etTitle.getText().toString(),
                etBody.getText().toString());

        FirebaseDatabase.getInstance().getReference().child(key).setValue(newPost);

        Toast.makeText(this, "Post created", Toast.LENGTH_SHORT).show();
        */
        finish();
    }

    private boolean isFormValid() {
        boolean result = true;
        if (TextUtils.isEmpty(etTitle.getText().toString())) {
            etTitle.setError("Required");
            result = false;
        } else {
            etTitle.setError(null);
        }

        if (TextUtils.isEmpty(etBody.getText().toString())) {
            etBody.setError("Required");
            result = false;
        } else {
            etBody.setError(null);
        }

        return result;
    }
}
