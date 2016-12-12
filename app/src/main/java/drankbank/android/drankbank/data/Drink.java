package drankbank.android.drankbank.data;

import android.util.Log;

import java.io.Serializable;

import drankbank.android.drankbank.R;
import drankbank.android.drankbank.model.api.BeerModel.Datum;

/**
 * Created by Veronica on 12/6/16.
 *
 * Class to hold drinks about data
 */

public class Drink implements Serializable {
    private String name;
    private String type;
    private String descrp;
    private int icon = R.drawable.misc_drinks;

    public Drink() {
    }

    public Drink(String name, String descrp) {
        this.name = name;
        this.descrp = descrp;
    }

    // Constructor from beer API
    public Drink(Datum data) {
        try {
            this.name = data.getName();
        } catch (NullPointerException e){
            Log.d("TAG_ERROR", e.getMessage());
            this.name = "No name available";
        }
        try {
            this.descrp = data.getDescription();
        } catch (NullPointerException e) {
            Log.d("TAG_ERROR", e.getMessage());
            this.descrp = "No description available";
        }
        try {
            this.type = data.getStyle().getName();
        } catch (NullPointerException e) {
            Log.d("TAG_ERROR", e.getMessage());
            this.type = "Beer";
        }
        this.icon = R.drawable.beer;
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

    public void setIconAndType(int type) {
        switch (type) {
            case 0:
                this.type = "Beer";
                this.icon = R.drawable.beer;
                break;
            case 1:
                this.type = "Wine";
                this.icon = R.drawable.wine;
                break;
            case 2:
                this.type = "Cocktail";
                this.icon = R.drawable.cocktail;
                break;
            case 3:
                this.type = "Other";
                this.icon = R.drawable.misc_drinks;
                break;
        }
    }

    public int getIcon() {return this.icon;}

    public String getDescrp() {
        return descrp;
    }

    public void setDescrp(String descrp) {
        this.descrp = descrp;
    }
}
