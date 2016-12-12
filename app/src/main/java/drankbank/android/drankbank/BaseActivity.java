package drankbank.android.drankbank;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Veronica on 12/5/16.
 *
 * Based off of Peter Eekler's Base Activity
 */

public class BaseActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;

    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            //progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading...");
        }

        progressDialog.show();
    }

    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public String getUserName() {
        return FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
    }

    public String getUserEmail() {
        return FirebaseAuth.getInstance().getCurrentUser().getEmail();
    }

    /*
    Returns date in month/day/year format (ex. 12/25/2016)
     */
    public String getCurrDate() {
        String currDate = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
        return currDate;
    }
}

