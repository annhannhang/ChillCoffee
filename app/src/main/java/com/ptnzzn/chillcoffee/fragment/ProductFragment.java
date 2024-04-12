package com.ptnzzn.chillcoffee.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;

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
import com.google.android.material.snackbar.Snackbar.SnackbarLayout;
import com.ptnzzn.chillcoffee.R;
import com.ptnzzn.chillcoffee.adapter.ProductAdapter;
import com.ptnzzn.chillcoffee.dao.ProductDAO;
import com.ptnzzn.chillcoffee.modal.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductFragment extends Fragment {
    SearchView search_Product;
    RecyclerView rcv_Product;
    FloatingActionButton fab_AddNewProduct;
    ProductDAO dao;
    ArrayList<Product> list;
    LinearLayout layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dao = new ProductDAO(getContext());
        list = new ArrayList<>();
        list = dao.getAll();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        layout = view.findViewById(R.id.layoutProduct);
        search_Product = view.findViewById(R.id.search_Product);
        rcv_Product = view.findViewById(R.id.rcv_Product);
        fab_AddNewProduct = view.findViewById(R.id.fab_AddNewProduct);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcv_Product.setLayoutManager(layoutManager);

        ProductAdapter adapter = new ProductAdapter(getContext(), list);
        rcv_Product.setAdapter(adapter);

        fab_AddNewProduct.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            View view1 = LayoutInflater.from(getContext()).inflate(R.layout.dialog_addproduct, null);
            builder.setView(view1);
            AlertDialog alertDialog = builder.create();

            Button btn_LoadImgProduct = view1.findViewById(R.id.btn_LoadImgProduct);
            Button btn_AddProduct = view1.findViewById(R.id.btn_AddProduct);
            EditText edt_AddLinkImageProduct = view1.findViewById(R.id.edt_AddLinkImageProduct);
            EditText edt_AddNameProduct = view1.findViewById(R.id.edt_AddNameProduct);
            EditText edt_AddPriceProduct = view1.findViewById(R.id.edt_AddPriceProduct);
            ImageView img_AddProduct = view1.findViewById(R.id.img_AddProduct);

            btn_LoadImgProduct.setOnClickListener(v1 -> {
                if (edt_AddLinkImageProduct.getText().toString().isEmpty()) {
                    edt_AddLinkImageProduct.setError("Please enter link image");
                } else {
                    String url = edt_AddLinkImageProduct.getText().toString();
                    Picasso.get().load(url).into(img_AddProduct);
                }
            });

            btn_AddProduct.setOnClickListener(v12 -> {
                String name = edt_AddNameProduct.getText().toString();
                int price = 0;
                if (edt_AddPriceProduct.getText().toString().isEmpty()) {
                    edt_AddPriceProduct.setError("Please enter price");
                } else {
                    price = Integer.parseInt(edt_AddPriceProduct.getText().toString());
                }
                String image = edt_AddLinkImageProduct.getText().toString();
                Product product = new Product(name, price, image);
                if (edt_AddNameProduct.getText().toString().isEmpty()) {
                    edt_AddNameProduct.setError("Please enter name");
                } else if (edt_AddLinkImageProduct.getText().toString().isEmpty()) {
                    edt_AddLinkImageProduct.setError("Please enter link image");
                } else {
                    if (ProductDAO.insert(product)) {
                        list.clear();
                        list.addAll(ProductDAO.getAll());
                        adapter.notifyDataSetChanged();
                        alertDialog.dismiss();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                successSnkbar(layout, "Add product successfully");
                                adapter.notifyDataSetChanged();
                            }
                        }, 1500);
                    } else {
                        errorSnkbar(layout, "Add product failed");
                    }
                }
            });
            alertDialog.show();
        });

        search_Product.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                list.clear();
                list.addAll(ProductDAO.search(query));
                ProductAdapter adapter = new ProductAdapter(getContext(), list);
                rcv_Product.setAdapter(adapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                list.clear();
                list.addAll(ProductDAO.search(newText));
                ProductAdapter adapter = new ProductAdapter(getContext(), list);
                rcv_Product.setAdapter(adapter);
                return false;
            }
        });
        return view;
    }

    private void errorSnkbar(LinearLayout layout, String errorText) {
        final Snackbar snackbar = Snackbar.make(layout, "", Snackbar.LENGTH_SHORT);
        View view = getLayoutInflater().inflate(R.layout.snkbar_error, null);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        @SuppressLint("RestrictedApi") SnackbarLayout snackbarLayout = (SnackbarLayout) snackbar.getView();
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
        @SuppressLint("RestrictedApi") SnackbarLayout snackbarLayout = (SnackbarLayout) snackbar.getView();
        snackbarLayout.setPadding(0, 0, 0, 0);
        TextView tv_Success = view.findViewById(R.id.tv_Success);
        tv_Success.setText(successText);
        snackbarLayout.addView(view, 0);
        snackbar.show();
    }
}