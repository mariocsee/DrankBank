package drankbank.android.drankbank.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Veronica on 11/21/16.
 *
 * Stores data of users. Credit to Peter Eckler.
 */

public class User {
    private String username;
    private String email;
    private List<Drink> favDrinks;
    private List<Drink> todayDrinks;

    public User(){
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.favDrinks = new ArrayList<Drink>();
        this.todayDrinks = new ArrayList<Drink>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Drink> getFavDrinks() {
        return favDrinks;
    }

    public void setFavDrinks(List<Drink> favDrinks) {
        this.favDrinks = favDrinks;
    }

    public List<Drink> getTodayDrinks() {
        return todayDrinks;
    }

    public void setTodayDrinks(List<Drink> todayDrinks) {
        this.todayDrinks = todayDrinks;
    }
}
