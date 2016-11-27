package com.example.tangcan0823.chart_test;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("MTI");
        setSupportActionBar(mToolbar);



        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        setupDrawerContent(mNavigationView);

        setUpProfileImage();
        switchTohitori();

            }

    private void switchTohitori() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new HitoriFragment()).commit();
        mToolbar.setTitle("子供");
    }
    private void switchTotomo() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new TomoFragment()).commit();
        mToolbar.setTitle("友達");
    }

    private void switchToData() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new DataFragment()).commit();
        mToolbar.setTitle("データ");
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {

                            case R.id.navigation_item_hitori:
                                switchTohitori();
                                break;

                            case R.id.navigation_item_tomo:
                                switchTotomo();
                                break;

                            case R.id.navigation_item_data:
                                switchToData();
                                break;

                        }
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    private void setUpProfileImage() {
        View headerView = mNavigationView.inflateHeaderView(R.layout.navigation_header);
        View profileView = headerView.findViewById(R.id.profile_image);
        if (profileView != null) {
            profileView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switchTohitori();
                    mToolbar.setTitle("MTI");
                    mDrawerLayout.closeDrawers();
                }
            });
        }

    }


}