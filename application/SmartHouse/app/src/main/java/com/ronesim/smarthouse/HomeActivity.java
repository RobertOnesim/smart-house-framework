package com.ronesim.smarthouse;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ronesim.smarthouse.home.adapter.RoomListAdapter;
import com.ronesim.smarthouse.home.adapter.util.ClickListener;
import com.ronesim.smarthouse.home.adapter.util.RecyclerTouchListener;
import com.ronesim.smarthouse.home.room.RoomActivity;
import com.ronesim.smarthouse.model.HomeRule;
import com.ronesim.smarthouse.model.Room;
import com.ronesim.smarthouse.remote.APIService;
import com.ronesim.smarthouse.remote.APIUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    private APIService apiService;
    private DrawerLayout drawerLayout;
    private RoomListAdapter adapter;
    private List<Room> roomList = new ArrayList<>();
    private List<HomeRule> homeRulesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "BLE NOT SUPPORTED", Toast.LENGTH_LONG).show();
        }

        setTokenAccess();

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
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.add_room_dialog, null);
                final AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this)
                        .setTitle("Add Room")
                        .setView(dialogView)
                        .setPositiveButton("Add", null)
                        .setNegativeButton(android.R.string.cancel, null)
                        .create();

                final EditText inputRoomName = (EditText) dialogView.findViewById(R.id.roomName);
                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        Button button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // validate name
                                String roomName = inputRoomName.getText().toString();
                                if (roomName.isEmpty()) {
                                    inputRoomName.setError("Room name cannot be empty.");
                                } else {
                                    apiService.addRoom(roomName).enqueue(new Callback<Room>() {
                                        @Override
                                        public void onResponse(Call<Room> call, Response<Room> response) {
                                            adapter.insert(0, response.body());
                                        }

                                        @Override
                                        public void onFailure(Call<Room> call, Throwable t) {
                                            Log.e("Failed", t.getMessage());
                                            Toast.makeText(getBaseContext(), "Failed to add a new room.", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                    alertDialog.dismiss();
                                }
                            }
                        });
                    }
                });
                alertDialog.show();
            }
        });

        // DATA - room list
        apiService.roomList().enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                roomList = response.body();
                setHomePage();
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Failed to get data from server.", Toast.LENGTH_LONG).show();
            }
        });

        // Data - home rules
        apiService.getHomeRulesList().enqueue(new Callback<List<HomeRule>>() {
            @Override
            public void onResponse(Call<List<HomeRule>> call, Response<List<HomeRule>> response) {
                homeRulesList = response.body();
            }

            @Override
            public void onFailure(Call<List<HomeRule>> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Failed to get data from server.", Toast.LENGTH_LONG).show();
            }
        });

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
            case R.id.home_rules:
                setAutomationRules();
                return true;
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private void setHomePage() {
        // Recycler View used to list all the rooms
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(HomeActivity.this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(view.getContext(), RoomActivity.class);
                Gson gson = new Gson();
                intent.putExtra("room", gson.toJson(roomList.get(position)));
                startActivityForResult(intent, 1);
            }

            @Override
            public void onLongClick(View view, final int position) {
                AlertDialog dialog = new AlertDialog.Builder(HomeActivity.this)
                        .setTitle("Remove room")
                        .setMessage("Would you like to delete this room?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Room room = adapter.getRoom(position);
                                apiService.deleteRoom(room.getId()).enqueue(new Callback<Room>() {
                                    @Override
                                    public void onResponse(Call<Room> call, Response<Room> response) {
                                        if (response.code() == 204) { // room deleted - status code returned 204
                                            adapter.remove(position);
                                        } else {
                                            Toast.makeText(HomeActivity.this, "Room id not found.", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Room> call, Throwable t) {
                                        Toast.makeText(HomeActivity.this, "Failed to remove the room.", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create();
                dialog.show();
            }
        }));

        // Adapter
        adapter = new RoomListAdapter(roomList);
        recyclerView.setAdapter(adapter);

        // Animate the Recycler View
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(600);
        recyclerView.setItemAnimator(itemAnimator);
    }

    private void setAutomationRules() {
        final Set<Integer> selectedItems = new HashSet<>();
        CharSequence[] items = processNames();
        boolean[] checkedItems = processValue();
        AlertDialog dialog = new AlertDialog.Builder(HomeActivity.this)
                .setTitle("Set home automation rules")
                .setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int index, boolean isChecked) {
                        if (isChecked) {
                            selectedItems.add(homeRulesList.get(index).getId());
                            homeRulesList.get(index).setSet(true);
                        } else {
                            homeRulesList.get(index).setSet(false);
                            selectedItems.remove(homeRulesList.get(index).getId());
                        }
                    }
                })
                .setPositiveButton("Set rules", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Data - home rules
                        if (selectedItems.isEmpty()) {
                            selectedItems.add(-1);
                        }
                        apiService.setHomeRules(new ArrayList<Integer>(selectedItems)).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(getBaseContext(), "Rules have been set!", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(getBaseContext(), "Failed to set the rules", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();
        dialog.show();

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                apiService.roomList().enqueue(new Callback<List<Room>>() {
                    @Override
                    public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                        roomList = response.body();
                        adapter.setRoomsList(roomList);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<List<Room>> call, Throwable t) {
                        Log.e("Failed", t.getMessage());
                        Toast.makeText(getBaseContext(), "Failed to get data from server.", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }

    private void setTokenAccess() {
        final String MY_PREFS_NAME = "prefsFile";
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String token = prefs.getString("token", null);

        if (token != null && !token.isEmpty())
            apiService = APIUtils.getAPIService(token);
        else
            apiService = APIUtils.getAPIService();
    }

    private CharSequence[] processNames() {
        List<String> items = new ArrayList<>();
        for (HomeRule homerule : homeRulesList) {
            items.add(homerule.getType() + ": " + homerule.getDescription());
        }
        return items.toArray(new CharSequence[items.size()]);
    }

    private boolean[] processValue() {
        boolean[] items = new boolean[homeRulesList.size()];
        int index = 0;
        for (HomeRule homerule : homeRulesList) {
            items[index++] = homerule.isSet();
        }
        return items;
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }
}
