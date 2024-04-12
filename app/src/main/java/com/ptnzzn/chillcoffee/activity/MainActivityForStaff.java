package com.ptnzzn.chillcoffee.activity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.ptnzzn.chillcoffee.R;
import com.ptnzzn.chillcoffee.fragment.HomeStaffFragment;
import com.ptnzzn.chillcoffee.fragment.ProductFragment;
import com.ptnzzn.chillcoffee.fragment.TableFragment;

public class MainActivityForStaff extends AppCompatActivity {
    Toolbar toolbarStaff;
    ImageView img_ToolbarStaff;
    TextView tv_ToolbarStaff;
    FrameLayout frame_containerStaff;
    NavigationView nav_viewStaff;
    ProductFragment productFragmentStaff;
    TableFragment tableFragmentStaff;
    HomeStaffFragment homeStaffFragment;
    DrawerLayout drawer_layoutStaff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_for_staff);

        toolbarStaff = findViewById(R.id.toolbarStaff);
        img_ToolbarStaff = findViewById(R.id.img_ToolbarStaff);
        tv_ToolbarStaff = findViewById(R.id.tv_ToolbarStaff);
        frame_containerStaff = findViewById(R.id.frame_containerStaff);
        nav_viewStaff = findViewById(R.id.nav_viewStaff);
        drawer_layoutStaff = findViewById(R.id.drawer_layoutStaff);


        productFragmentStaff = new ProductFragment();
        tableFragmentStaff = new TableFragment();
        homeStaffFragment = new HomeStaffFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_containerStaff, homeStaffFragment).commit();

        setSupportActionBar(toolbarStaff);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer_layoutStaff, toolbarStaff, R.string.open_drawer, R.string.close_drawer);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        drawer_layoutStaff.addDrawerListener(toggle);

        nav_viewStaff.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_HomeStaff) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_containerStaff, homeStaffFragment).commit();
                drawer_layoutStaff.closeDrawers();
                img_ToolbarStaff.setImageResource(R.drawable.ico_home);
                tv_ToolbarStaff.setText("Home");
            }
            if (item.getItemId() == R.id.nav_qlspStaff) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_containerStaff, productFragmentStaff).commit();
                drawer_layoutStaff.closeDrawers();
                img_ToolbarStaff.setImageResource(R.drawable.ico_qlsp);
                tv_ToolbarStaff.setText("Product Management");
            }
            if (item.getItemId() == R.id.nav_qlbanStaff) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_containerStaff, tableFragmentStaff).commit();
                drawer_layoutStaff.closeDrawers();
                img_ToolbarStaff.setImageResource(R.drawable.ico_qlban);
                tv_ToolbarStaff.setText("Table Management");
            }
            if (item.getItemId() == R.id.nav_changePassStaff) {
                Intent intent = new Intent(MainActivityForStaff.this, ChangePassword.class);
                startActivity(intent);
            }
            if (item.getItemId() == R.id.nav_LogoutStaff) {
                finish();
            }
            return true;
        });
    }
}