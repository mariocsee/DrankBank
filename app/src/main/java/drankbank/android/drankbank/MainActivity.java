package drankbank.android.drankbank;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import drankbank.android.drankbank.fragments.FavoritesFragment;
import drankbank.android.drankbank.fragments.TodayFragment;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private View hView;
    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    //private CalendarView calendarView;
    private TextView tvEmail;
    private TextView tvUserName;
    private FirebaseUser user;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        // switching views

/*        calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/
        setUpUI();
        if (savedInstanceState == null) {
            bottomNavigationView.getMenu().performIdentifierAction(R.id.action_today_view, 0);
        }
    }

    private void setUpUI() {
        setUpToolBar();
        setUpBottomNav();
        setUpDrawer();
        //setUpRecyclerView();
    }

    private void setUpDrawer() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        hView = navigationView.getHeaderView(0);

        tvEmail = (TextView) hView.findViewById(R.id.tvEmail);
        tvUserName = (TextView) hView.findViewById(R.id.tvUserName);

        user = FirebaseAuth.getInstance().getCurrentUser();

        tvUserName.setText(user.getDisplayName());
        tvEmail.setText(user.getEmail());

    }

    /*
    Set up toolbar with clickable icon to open drawer
     */
    private void setUpToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
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
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                Class fragmentClass = null;

                switch (item.getItemId()) {
                    case R.id.action_today_view:
                        // prevents item from being re-clicked
                        Log.d("TAG_FRAG", "TODAY");
                        fragmentClass = TodayFragment.class;
                        if (!isFragmentPresent("FRAG_TODAY")) {
        /*                    try {
                                fragment = (Fragment) fragmentClass.newInstance();
                            } catch (InstantiationException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }*/
/*
                            fragmentManager = getSupportFragmentManager();
*/
                        }
                        break;
                    case R.id.action_card_view:
                        Log.d("TAG_FRAG", "FAVS");
                        fragmentClass = FavoritesFragment.class;
                        if (!isFragmentPresent("FRAG_FAVORITES")) {
/*                            fragmentManager.beginTransaction()
                                    .replace(R.id.main_placeholder, new FavoritesFragment(), "FRAG_FAVORITES")
                                    .commit();*/
                        }
                        break;
//                    case R.id.action_search_view:
//                        Toast.makeText(MainActivity.this, "SEARCH VIEW", Toast.LENGTH_SHORT).show();
//                        return true;
                    case R.id.action_calendar_view:
                        Toast.makeText(MainActivity.this, "CALENDAR VIEW", Toast.LENGTH_SHORT).show();
                    case R.id.action_graph_view:
                        Toast.makeText(MainActivity.this, "GRAPH VIEW", Toast.LENGTH_SHORT).show();
                }
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.main_placeholder, fragment)
                        .commit();
                return true;
            }
        });
    }

    /*
    Checks if fragment is already present
     */
    private boolean isFragmentPresent (String tag) {
        Fragment frag = getSupportFragmentManager().findFragmentByTag(tag);
        if (frag instanceof TodayFragment
                || frag instanceof FavoritesFragment)  {
            return true;
        } else {
            return false;
        }
    }

    /*
    Handles when back button is pressed
     */
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
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
        switch (item.getItemId()) {
//            case R.id.nav_list:
                //handle camera action
//                break;
            case R.id.nav_favorites:

                break;
//            case R.id.nav_settings:
//                break;
            case R.id.nav_search:
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
