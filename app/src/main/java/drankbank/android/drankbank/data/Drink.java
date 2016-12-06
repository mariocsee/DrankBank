package drankbank.android.drankbank.data;

import drankbank.android.drankbank.model.BeerModel.Datum;

/**
 * Created by Veronica on 12/6/16.
 *
 * Class to hold results from
 */

public class Drink {
    private String name;
    private String type;
    private String descrp;

    public Drink(String name, String descrp) {
        this.name = name;
        this.descrp = descrp;
    }

    public Drink(Datum data) {
        this.name = data.getName();
        this.descrp = data.getDescription();
        this.type = data.getStyle().getCategory().getName();
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
