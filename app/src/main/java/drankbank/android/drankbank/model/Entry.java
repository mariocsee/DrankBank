package drankbank.android.drankbank.model;

import com.google.firebase.database.Exclude;
import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Veronica on 11/21/16.
 *
 * Stores data for a diary entry.
 */

public class Entry extends SugarRecord implements Serializable {
    private Date date;
    private String alcohol;
    private String rating;
    private String body;

    public Entry() {
    }

    public Entry(String alcohol, Date date, String rating, String body) {
        this.date = date;
        this.alcohol = alcohol;
        this.rating = rating;
        this.body = body;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("alcohol", alcohol);
        result.put("date", date);
        result.put("rating", rating);
        result.put("body", body);

        return result;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}