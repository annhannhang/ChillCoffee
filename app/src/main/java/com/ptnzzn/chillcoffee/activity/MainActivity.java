package com.ptnzzn.chillcoffee.activity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.ptnzzn.chillcoffee.R;
import com.ptnzzn.chillcoffee.fragment.BillingFragment;
import com.ptnzzn.chillcoffee.fragment.HRFragment;
import com.ptnzzn.chillcoffee.fragment.HomeFragment;
import com.ptnzzn.chillcoffee.fragment.ProductFragment;
import com.ptnzzn.chillcoffee.fragment.StatiscalFragment;
import com.ptnzzn.chillcoffee.fragment.TableFragment;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    NavigationView nav_view;
    DrawerLayout drawer_layout;
    ImageView img_Toolbar;
    TextView tv_Toolbar;
    HomeFragment homeFragment;
    BillingFragment billingFragment;
    HRFragment hrFragment;
    ProductFragment productFragment;
    StatiscalFragment statiscalFragment;
    TableFragment tableFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        nav_view = findViewById(R.id.nav_view);
        drawer_layout = findViewById(R.id.drawer_layout);
        img_Toolbar = findViewById(R.id.img_Toolbar);
        tv_Toolbar = findViewById(R.id.tv_Toolbar);

        homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, homeFragment).commit();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.open_drawer, R.string.close_drawer);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        drawer_layout.addDrawerListener(toggle);

        nav_view.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_Home) {
                homeFragment = new HomeFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, homeFragment).commit();
                drawer_layout.closeDrawers();
                img_Toolbar.setImageResource(R.drawable.ico_home);
                tv_Toolbar.setText("Home");
            } else if (item.getItemId() == R.id.nav_qlnv) {
                hrFragment = new HRFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, hrFragment).commit();
                drawer_layout.closeDrawers();
                img_Toolbar.setImageResource(R.drawable.ico_qlnv);
                tv_Toolbar.setText("Human resource management");
            } else if (item.getItemId() == R.id.nav_qlsp) {
                productFragment = new ProductFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, productFragment).commit();
                drawer_layout.closeDrawers();
                img_Toolbar.setImageResource(R.drawable.ico_qlsp);
                tv_Toolbar.setText("Product Management");
            } else if (item.getItemId() == R.id.nav_qlhd) {
                billingFragment = new BillingFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, billingFragment).commit();
                drawer_layout.closeDrawers();
                img_Toolbar.setImageResource(R.drawable.ico_qlhd);
                tv_Toolbar.setText("Billing Management");
            } else if (item.getItemId() == R.id.nav_qlban) {
                tableFragment = new TableFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, tableFragment).commit();
                drawer_layout.closeDrawers();
                img_Toolbar.setImageResource(R.drawable.ico_qlban);
                tv_Toolbar.setText("Table Management");
            } else if (item.getItemId() == R.id.nav_thongke) {
                statiscalFragment = new StatiscalFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, statiscalFragment).commit();
                drawer_layout.closeDrawers();
                img_Toolbar.setImageResource(R.drawable.ico_tke);
                tv_Toolbar.setText("Statiscal");
            } else if (item.getItemId() == R.id.nav_changePass) {
                Intent intent = new Intent(MainActivity.this, ChangePassword.class);
                startActivity(intent);
            } else if (item.getItemId() == R.id.nav_Logout) {
                finish();
            }
            return true;
        });

    }
}