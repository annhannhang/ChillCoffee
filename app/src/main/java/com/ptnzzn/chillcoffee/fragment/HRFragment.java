package com.ptnzzn.chillcoffee.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.ptnzzn.chillcoffee.R;
import com.ptnzzn.chillcoffee.adapter.HRAdapter;
import com.ptnzzn.chillcoffee.dao.HRDAO;
import com.ptnzzn.chillcoffee.modal.HR;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HRFragment extends Fragment {
    SearchView search_HR;
    RecyclerView rcv_HR;
    FloatingActionButton fab_AddNewHR;
    HRDAO dao;
    ArrayList<HR> list;
    LinearLayout layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dao = new HRDAO(getContext());
        list = dao.getAll();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_h_r_mana, container, false);
        search_HR = view.findViewById(R.id.search_HR);
        rcv_HR = view.findViewById(R.id.rcv_HR);
        fab_AddNewHR = view.findViewById(R.id.fab_AddNewHR);
        layout = view.findViewById(R.id.layoutHR);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcv_HR.setLayoutManager(layoutManager);
        HRAdapter adapter = new HRAdapter(getContext(), list);
        rcv_HR.setAdapter(adapter);

        fab_AddNewHR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_addhr, null);
                builder.setView(view);
                AlertDialog dialog = builder.create();

                EditText edt_AddNameHR = view.findViewById(R.id.edt_AddNameHR);
                EditText edt_AddNumberPhoneHR = view.findViewById(R.id.edt_AddNumberPhoneHR);
                EditText edt_AddAddressHR = view.findViewById(R.id.edt_AddAddressHR);
                EditText edt_AddLinkImageHR = view.findViewById(R.id.edt_AddLinkImageHR);
                Button btn_AddHR = view.findViewById(R.id.btn_AddHR);
                Button btn_LoadImgHR = view.findViewById(R.id.btn_LoadImgHR);
                ImageView img_AddHR = view.findViewById(R.id.img_AddHR);

                btn_LoadImgHR.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (edt_AddLinkImageHR.getText().toString().isEmpty()) {
                            edt_AddLinkImageHR.setError("Please enter link image");
                        } else {
                            String url = edt_AddLinkImageHR.getText().toString();
                            Picasso.get().load(url).into(img_AddHR);
                        }
                    }
                });

                btn_AddHR.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = edt_AddNameHR.getText().toString();
                        String phone = edt_AddNumberPhoneHR.getText().toString();
                        String address = edt_AddAddressHR.getText().toString();
                        String image = edt_AddLinkImageHR.getText().toString();
                        HR item = new HR(name, phone, address, image);

                        if (edt_AddNameHR.getText().toString().isEmpty()) {
                            edt_AddNameHR.setError("Please enter name");
                        } else if (edt_AddNumberPhoneHR.getText().toString().isEmpty()) {
                            edt_AddNumberPhoneHR.setError("Please enter number phone");
                        } else if (edt_AddAddressHR.getText().toString().isEmpty()) {
                            edt_AddAddressHR.setError("Please enter address");
                        } else {
                            if (HRDAO.insert(item)) {
                                list.clear();
                                list.addAll(HRDAO.getAll());
                                adapter.notifyDataSetChanged();
                                dialog.dismiss();
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        successSnkbar(layout, "Add product successfully");
                                    }
                                }, 1500);
                            } else {
                                dialog.dismiss();
                                errorSnkbar(layout, "Add HR failed");
                            }
                        }
                    }
                });
                dialog.show();
            }
        });

        search_HR.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                list.clear();
                list.addAll(HRDAO.search(query));
                adapter.notifyDataSetChanged();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                list.clear();
                list.addAll(HRDAO.search(newText));
                adapter.notifyDataSetChanged();
                return false;
            }
        });

        return view;
    }

    private void errorSnkbar(LinearLayout layout, String errorText) {
        final Snackbar snackbar = Snackbar.make(layout, "", Snackbar.LENGTH_SHORT);
        View view = getLayoutInflater().inflate(R.layout.snkbar_error, null);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        @SuppressLint("RestrictedApi") Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackbarLayout.setPadding(0, 0, 0, 0);
        TextView tv_Error = view.findViewById(R.id.tv_Error);
        tv_Error.setText(errorText);
        snackbarLayout.addView(view, 0);
        snackbar.show();
    }

    private void successSnkbar(LinearLayout layout, String successText) {
        final Snackbar snackbar = Snackbar.make(layout, "", Snackbar.LENGTH_SHORT);
        View view = getLayoutInflater().inflate(R.layout.snkbar_success, null);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        @SuppressLint("RestrictedApi") Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackbarLayout.setPadding(0, 0, 0, 0);
        TextView tv_Success = view.findViewById(R.id.tv_Success);
        tv_Success.setText(successText);
        snackbarLayout.addView(view, 0);
        snackbar.show();
    }
}