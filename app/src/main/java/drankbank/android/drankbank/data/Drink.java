package drankbank.android.drankbank.data;

import android.util.Log;

import java.io.Serializable;

import drankbank.android.drankbank.model.BeerModel.Datum;

/**
 * Created by Veronica on 12/6/16.
 *
 * Class to hold results from
 */

public class Drink implements Serializable {
    private String name;
    private String type;
    private String descrp;

    public Drink(String name, String descrp) {
        this.name = name;
        this.descrp = descrp;
    }

    // Constructor from beer API
    public Drink(Datum data) {
        if (data.getName() != null) {
            this.name = data.getName();

        } else {
            this.name = "No name available";
        }
        if (data.getDescription() != null) {
            this.descrp = data.getDescription();
        } else {
            this.descrp = "No description available";
        }
        try {
            this.type = data.getStyle().getName();
        } catch (NullPointerException e) {
            Log.d("TAG_QUERY", e.getMessage());
            this.type = "No type available";
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescrp() {
        return descrp;
    }

    public void setDescrp(String descrp) {
        this.descrp = descrp;
    }
}
