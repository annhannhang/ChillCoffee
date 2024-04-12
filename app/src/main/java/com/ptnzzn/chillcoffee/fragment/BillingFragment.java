package com.ptnzzn.chillcoffee.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.ptnzzn.chillcoffee.R;
import com.ptnzzn.chillcoffee.adapter.BillAdapter;
import com.ptnzzn.chillcoffee.dao.BillDAO;
import com.ptnzzn.chillcoffee.modal.Bill;

import java.util.ArrayList;

public class BillingFragment extends Fragment {

    SearchView search_Bill;
    RecyclerView rcv_Bill;
    FloatingActionButton fab_AddNewBill;
    BillDAO dao;
    ArrayList<Bill> list;
    LinearLayout layout;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dao = new BillDAO(getContext());
        list = dao.getAll();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_billing, container, false);
        search_Bill = view.findViewById(R.id.search_Bill);
        rcv_Bill = view.findViewById(R.id.rcv_Bill);
        fab_AddNewBill = view.findViewById(R.id.fab_AddNewBill);
        layout = view.findViewById(R.id.layoutBilling);
        
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcv_Bill.setLayoutManager(layoutManager);
        BillAdapter adapter = new BillAdapter(getContext(), list);
        rcv_Bill.setAdapter(adapter);
        
        fab_AddNewBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_addbill, null);
                builder.setView(view);
                AlertDialog dialog = builder.create();
                EditText edt_AddTableNameBill = view.findViewById(R.id.edt_AddTableNameBill);
                EditText edt_AddHRBill = view.findViewById(R.id.edt_AddHRBill);
                EditText edt_AddTableTimeBill = view.findViewById(R.id.edt_AddTableTimeBill);
                EditText edt_AddTableTotalBill = view.findViewById(R.id.edt_AddTableTotalBill);
                Button btn_AddBill = view.findViewById(R.id.btn_AddBill);

                btn_AddBill.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String idtable = edt_AddTableNameBill.getText().toString().trim();
                        String idhr = edt_AddHRBill.getText().toString().trim();
                        String time = edt_AddTableTimeBill.getText().toString().trim();
                        String total = edt_AddTableTotalBill.getText().toString().trim();
                        if (idtable.isEmpty() || idhr.isEmpty() || time.isEmpty() || total.isEmpty()) {
                            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                        } else {
                            Bill bill = new Bill(idtable, idhr, time, Integer.parseInt(total));
                            if (BillDAO.insert(bill)) {
                                successSnkbar(layout, "Add bill successfully");
                                list.clear();
                                list.addAll(BillDAO.getAll());
                                adapter.notifyDataSetChanged();
                                dialog.dismiss();
                            } else {
                                errorSnkbar(layout, "Add bill failed");
                            }
                        }
                    }
                });
                dialog.show();
            }
        });


        search_Bill.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                list.clear();
                list.addAll(BillDAO.search(query));
                adapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                list.clear();
                list.addAll(BillDAO.search(newText));
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