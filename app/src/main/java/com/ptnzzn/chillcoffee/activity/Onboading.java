package com.ptnzzn.chillcoffee.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ptnzzn.chillcoffee.R;
import com.ptnzzn.chillcoffee.adapter.OnboadingAdapter;
import com.ptnzzn.chillcoffee.modal.OnboadingItem;

import java.util.ArrayList;
import java.util.List;

public class Onboading extends AppCompatActivity {
    OnboadingAdapter adapter;
    ViewPager2 vp2_Onboading;
    LinearLayout layout_OnboardingIndicator;
    Button btn_GoToSignUp, btn_GoToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboading);

        vp2_Onboading = findViewById(R.id.vp2_Onboarding);
        layout_OnboardingIndicator = findViewById(R.id.layout_OnboardingIndicator);
        btn_GoToSignUp = findViewById(R.id.btn_GoToSignUp);
        btn_GoToLogin = findViewById(R.id.btn_GoToLogin);

        setupOnboadingItems();
        vp2_Onboading.setAdapter(adapter);

        setupOnboadingIndicator();
        setCurrentOnboadingIndicator(0);

        vp2_Onboading.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                setCurrentOnboadingIndicator(position);
            }
        });

        btn_GoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Onboading.this, Login.class);
                startActivity(intent);
            }
        });

        btn_GoToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Onboading.this, SignUp.class);
                startActivity(intent);
            }
        });
    }

    private void setupOnboadingItems() {
        List<OnboadingItem> list = new ArrayList<>();

        OnboadingItem item1 = new OnboadingItem();
        item1.setImage(R.drawable.onboading1);
        item1.setTitle("Chill Coffee");
        item1.setDescription("Chill Coffee is one of the coolest coffee shops in Bao Loc, with an open, breezy space that makes you feel totally relaxed and at home.");

        OnboadingItem item2 = new OnboadingItem();
        item2.setImage(R.drawable.onboading2);
        item2.setTitle("Bill management");
        item2.setDescription("You can manage your bills super easy, fast and convenient.");

        OnboadingItem item3 = new OnboadingItem();
        item3.setImage(R.drawable.onboading3);
        item3.setTitle("Simple interface");
        item3.setDescription("The simple, user-friendly interface makes managing super easy.");

        list.add(item1);
        list.add(item2);
        list.add(item3);

        adapter = new OnboadingAdapter(list);
    }

    private void setupOnboadingIndicator() {
        ImageView[] indicator = new ImageView[adapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8, 0, 8, 0);
        for (int i = 0; i < indicator.length; i++) {
            indicator[i] = new ImageView(getApplicationContext());
            indicator[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboading_indicator_inactive));
            indicator[i].setLayoutParams(layoutParams);
            layout_OnboardingIndicator.addView(indicator[i]);
        }
    }

    private void setCurrentOnboadingIndicator(int index) {
        int childCount = layout_OnboardingIndicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) layout_OnboardingIndicator.getChildAt(i);
            if (i == index) {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboading_indicator_active));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboading_indicator_inactive));
            }
        }
    }
}