package drankbank.android.drankbank;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import drankbank.android.drankbank.adapter.EntryAdapter;
import drankbank.android.drankbank.data.Entry;
import drankbank.android.drankbank.touch.EntryListTouchHelper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private EntryAdapter entryAdapter;
    private ViewFlipper viewFlipper;
    private CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // switching views
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        setUpUI();
    }

    private void setUpUI() {
        setUpToolBar();
        setUpBottomNav();
        //setUpRecyclerView();
        setUpDrawer();
        setUpFab();
    }

    private void setUpFab() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CreateEntryActivity.class));
            }
        });
    }

    private void setUpDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    /*
    Set up toolbar with clickable icon to open drawer
     */
    private void setUpToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.common_ic_googleplayservices);
        toolbar.setTitle("Drank Bank");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // captures click event from icon
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void setUpBottomNav() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_today_view:
                        Toast.makeText(MainActivity.this, "TODAY VIEW", Toast.LENGTH_SHORT).show();
                        viewFlipper.setDisplayedChild(0);
                        return true;
                    case R.id.action_card_view:
                        Toast.makeText(MainActivity.this, "CARD VIEW", Toast.LENGTH_SHORT).show();
                        viewFlipper.setDisplayedChild(1);
                        return true;
                    case R.id.action_search_view:
                        Toast.makeText(MainActivity.this, "SEARCH VIEW", Toast.LENGTH_SHORT).show();
                        viewFlipper.setDisplayedChild(2);
                        return true;
                    case R.id.action_calendar_view:
                        Toast.makeText(MainActivity.this, "CALENDAR VIEW", Toast.LENGTH_SHORT).show();
                        viewFlipper.setDisplayedChild(3);
                        return true;
                    case R.id.action_graph_view:
                        Toast.makeText(MainActivity.this, "GRAPH VIEW", Toast.LENGTH_SHORT).show();
                        viewFlipper.setDisplayedChild(4);
                        return true;
                }
                return false;
            }
        });
    }

    /*
    Set up recycler view of today's drinks
    private void setUpRecyclerView() {
        List<Entry> entryList = new ArrayList<>();

        entryAdapter = new EntryAdapter(Context context, this);

        RecyclerView recyclerEntry = (RecyclerView) findViewById(
                R.id.recyclerEntry);
        recyclerEntry.setLayoutManager(new LinearLayoutManager(this));
        recyclerEntry.setAdapter(entryAdapter);

        EntryListTouchHelper touchHelperCallback = new EntryListTouchHelper(
                entryAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(
                touchHelperCallback);
        touchHelper.attachToRecyclerView(recyclerEntry);
    }*/

    /*
    Handles when back button is pressed
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*
    Handles selection of toolbar menu items
    */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return true;
        }
    }

    /*
    Handles menu selection from the drawer
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (item.getItemId()) {
            case R.id.nav_list:
                //handle camera action
                break;
            case R.id.nav_favorites:
                break;
            case R.id.nav_settings:
                break;
            case R.id.nav_share:
                startActivity(new Intent(this, SearchActivity.class));
                break;
            case R.id.nav_logout:
                // logs out user
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }
}
