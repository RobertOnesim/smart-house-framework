package com.ronesim.smarthouse;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.ronesim.smarthouse.home.RecyclerViewAdapter;
import com.ronesim.smarthouse.home.Room;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Login system
        //Intent intent = new Intent(this, LoginActivity.class);
        //startActivity(intent);

        // Adding Toolbar to Main screen
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create Navigation drawer and inflate layout
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        // Adding menu icon to Toolbar
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Set behavior of navigation drawer
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    // This method will trigger on item Click of navigation menu
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        // Set item in checked state
                        menuItem.setChecked(true);
                        // TODO ronesim: handle navigation
                        // Closing drawer on item click
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });

        // Adding Floating Action Button to bottom right of main view
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO ronesim handle button
                adapter.insert(0, new Room(2, "Kitchen", "Last action: modified light color", "16m", 2, R.drawable.ic_home));
                System.out.println("pressed");
            }
        });

        // TODO ronesim get data from server
        List<Room> data = new ArrayList<>();
        Room room = new Room(1, "Bedroom", "Last action: modified light color", "2h", 2, R.drawable.ic_home);
        data.add(room);

        // Recycler View used to list all the rooms
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        adapter = new RecyclerViewAdapter(data, getApplication());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Animate the Recycler View
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(itemAnimator);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        // HAndle item selection
        switch (menuItem.getItemId()) {
            case R.id.action_settings:
                System.out.println("Hit Settings");
                return true;
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }
}
