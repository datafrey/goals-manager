package com.datafrey.goalsmanager.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import com.datafrey.goalsmanager.R;
import com.datafrey.goalsmanager.main.aboutfragment.AboutFragment;
import com.datafrey.goalsmanager.main.archivefragment.ArchiveFragment;
import com.datafrey.goalsmanager.main.goalsfragment.GoalsFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar actionBar;
    private DrawerLayout drawer;
    private NavigationView navigationView;

    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        setupActionBar();
        setupDrawer();
        setupNavigationView();

        if (savedInstanceState == null) {
            onNavigationItemSelected(navigationView.getMenu().getItem(0));
        }
    }

    private void setupActionBar() {
        actionBar = findViewById(R.id.actionBarToolbar);
        setSupportActionBar(actionBar);

        viewModel.getActionBarTitle().observe(this,
                title -> Objects.requireNonNull(getSupportActionBar()).setTitle(title));
        viewModel.getActionBarHasShadow().observe(this,
                hasShadow -> Objects.requireNonNull(getSupportActionBar()).setElevation(hasShadow ? 4f : 0f));
    }

    private void setupDrawer() {
        drawer = findViewById(R.id.mainDrawerLayout);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, drawer, actionBar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private void setupNavigationView() {
        navigationView = findViewById(R.id.drawerNavigationView);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.goalsMenuItem:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                        new GoalsFragment()).commit();
                viewModel.goalsFragmentOpened();
                break;
            case R.id.archiveMenuItem:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                        new ArchiveFragment()).commit();
                viewModel.archiveFragmentOpened();
                break;
            case R.id.aboutMenuItem:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                        new AboutFragment()).commit();
                viewModel.aboutFragmentOpened();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}