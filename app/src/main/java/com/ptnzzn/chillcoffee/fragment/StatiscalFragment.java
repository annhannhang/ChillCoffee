package com.ptnzzn.chillcoffee.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ptnzzn.chillcoffee.R;
import com.ptnzzn.chillcoffee.dao.BillDAO;
import com.ptnzzn.chillcoffee.dao.HRDAO;
import com.ptnzzn.chillcoffee.dao.ProductDAO;
import com.ptnzzn.chillcoffee.dao.TableDAO;

public class StatiscalFragment extends Fragment {
    TextView tv_TotalRevenue, tv_TotalHR, tv_TotalProduct, tv_TotalTable, tv_TotalBill;
    BillDAO billDAO;
    HRDAO hrDAO;
    ProductDAO productDAO;
    TableDAO tableDAO;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        billDAO = new BillDAO(getContext());
        hrDAO = new HRDAO(getContext());
        productDAO = new ProductDAO(getContext());
        tableDAO = new TableDAO(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statiscal, container, false);
        tv_TotalRevenue = view.findViewById(R.id.tv_TotalRevenue);
        tv_TotalHR = view.findViewById(R.id.tv_TotalHR);
        tv_TotalProduct = view.findViewById(R.id.tv_TotalProduct);
        tv_TotalTable = view.findViewById(R.id.tv_TotalTable);
        tv_TotalBill = view.findViewById(R.id.tv_TotalBill);

        tv_TotalRevenue.setText(String.valueOf(BillDAO.getSum()));
        tv_TotalHR.setText(String.valueOf(HRDAO.countHR()));
        tv_TotalProduct.setText(String.valueOf(ProductDAO.countProduct()));
        tv_TotalTable.setText(String.valueOf(TableDAO.countTable()));
        tv_TotalBill.setText(String.valueOf(BillDAO.countBill()));
        return view;
    }
}