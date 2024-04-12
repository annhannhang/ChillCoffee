package com.ptnzzn.chillcoffee.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.ptnzzn.chillcoffee.R;
import com.ptnzzn.chillcoffee.adapter.TableAdapter;
import com.ptnzzn.chillcoffee.dao.TableDAO;
import com.ptnzzn.chillcoffee.modal.Table;

import java.util.ArrayList;

public class TableFragment extends Fragment {


    SearchView search_Table;
    RecyclerView rcv_Table;
    FloatingActionButton fab_AddNewTable;
    TableDAO dao;
    ArrayList<Table> list;
    LinearLayout layout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dao = new TableDAO(getContext());
        list = dao.getAll();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_table, container, false);
        search_Table = view.findViewById(R.id.search_Table);
        rcv_Table = view.findViewById(R.id.rcv_Table);
        fab_AddNewTable = view.findViewById(R.id.fab_AddNewTable);
        layout = view.findViewById(R.id.ll_TableFragment);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        rcv_Table.setLayoutManager(layoutManager);

        TableAdapter adapter = new TableAdapter(getContext(), list);
        rcv_Table.setAdapter(adapter);

        fab_AddNewTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_addtable, null);
                builder.setView(view);
                AlertDialog alertDialog = builder.create();

                EditText edt_AddTableName = view.findViewById(R.id.edt_AddTableName);
                EditText edt_AddTableTime = view.findViewById(R.id.edt_AddTableTime);
                EditText edt_AddTablePrice = view.findViewById(R.id.edt_AddTablePrice);
                EditText edt_AddTableStatus = view.findViewById(R.id.edt_AddTableStatus);
                Button btn_AddTable = view.findViewById(R.id.btn_AddTable);

                btn_AddTable.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = edt_AddTableName.getText().toString();
                        String time = edt_AddTableTime.getText().toString();
                        int price = Integer.parseInt(edt_AddTablePrice.getText().toString());
                        String status = edt_AddTableStatus.getText().toString();
                        Table item = new Table(name, time, price, status);

                        if (edt_AddTableName.getText().toString().isEmpty()) {
                            edt_AddTableName.setError("Please enter name");
                        } else if (edt_AddTableTime.getText().toString().isEmpty()) {
                            edt_AddTableTime.setError("Please enter time");
                        } else if (edt_AddTablePrice.getText().toString().isEmpty() || price < 0 ) {
                            edt_AddTablePrice.setError("Please enter price");
                        } else if (edt_AddTableStatus.getText().toString().isEmpty()) {
                            edt_AddTableStatus.setError("Please enter status");
                        } else {
                            if (TableDAO.insert(item)) {
                                successSnkbar(layout, "Add table success");
                                list.clear();
                                list.addAll(dao.getAll());
                                adapter.notifyDataSetChanged();
                                alertDialog.dismiss();
                            } else {
                                errorSnkbar(layout, "Add table failed");
                            }
                        }
                    }
                });
                alertDialog.show();
            }
        });

        search_Table.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                list.clear();
                list.addAll(TableDAO.search(query));
                adapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                list.clear();
                list.addAll(TableDAO.search(newText));
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