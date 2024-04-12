package com.ptnzzn.chillcoffee.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ptnzzn.chillcoffee.R;
import com.ptnzzn.chillcoffee.dao.BillDAO;
import com.ptnzzn.chillcoffee.modal.Bill;

import java.util.ArrayList;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillViewHolder>{
    Context context;
    ArrayList<Bill> list;
    BillDAO dao;

    public BillAdapter(Context context, ArrayList<Bill> list) {
        this.context = context;
        this.list = list;
        dao = new BillDAO(context);
    }

    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_bill, parent, false);
        return new BillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillViewHolder holder, int position) {
        Bill item = list.get(position);
        holder.tv_idTableBill.setText(item.getIdtable());
        holder.tv_idHRBill.setText(item.getIdhr());
        holder.tv_TimeBill.setText(item.getTime());
        holder.tv_TotalBill.setText(item.getTotal() + " VNĐ");

        holder.img_MoreFromBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view = LayoutInflater.from(context).inflate(R.layout.dialog_more, null);
                builder.setView(view);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                LinearLayout layout_editProduct = view.findViewById(R.id.layout_editProduct);
                LinearLayout layout_deleteProduct = view.findViewById(R.id.layout_deleteProduct);
                Button btn_CancelMoreProduct = view.findViewById(R.id.btn_CancelMoreProduct);

                layout_editProduct.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        View view = LayoutInflater.from(context).inflate(R.layout.dialog_addbill, null);
                        builder.setView(view);
                        AlertDialog dialog = builder.create();

                        EditText edt_AddTableNameBill = view.findViewById(R.id.edt_AddTableNameBill);
                        EditText edt_AddHRBill = view.findViewById(R.id.edt_AddHRBill);
                        EditText edt_AddTableTimeBill = view.findViewById(R.id.edt_AddTableTimeBill);
                        EditText edt_AddTableTotalBill = view.findViewById(R.id.edt_AddTableTotalBill);
                        Button btn_AddBill = view.findViewById(R.id.btn_AddBill);

                        edt_AddTableNameBill.setText(item.getIdtable());
                        edt_AddHRBill.setText(item.getIdhr());
                        edt_AddTableTimeBill.setText(item.getTime());
                        edt_AddTableTotalBill.setText(item.getTotal() + "");

                        btn_AddBill.setOnClickListener(new View.OnClickListener() {
                            @SuppressLint("NotifyDataSetChanged")
                            @Override
                            public void onClick(View v) {
                                String idtable = edt_AddTableNameBill.getText().toString();
                                String idhr = edt_AddHRBill.getText().toString();
                                String time = edt_AddTableTimeBill.getText().toString();
                                int total = Integer.parseInt(edt_AddTableTotalBill.getText().toString());
                                Bill bill = new Bill(item.getId(), idtable, idhr, time, total);
                                if (BillDAO.update(bill)){
                                    list.clear();
                                    list.addAll(BillDAO.getAll());
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Update success", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    alertDialog.dismiss();
                                }else {
                                    Toast.makeText(context, "Update fail", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        dialog.show();
                    }
                });

                layout_deleteProduct.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        View view = LayoutInflater.from(context).inflate(R.layout.dialog_confirmdelete, null);
                        builder.setView(view);
                        AlertDialog dialog = builder.create();

                        Button btn_ConfirmDeleteProduct = view.findViewById(R.id.btn_ConfirmDeleteProduct);
                        Button btn_CancelDeleteProduct = view.findViewById(R.id.btn_CancelDeleteProduct);

                        btn_ConfirmDeleteProduct.setOnClickListener(new View.OnClickListener() {
                            @SuppressLint("NotifyDataSetChanged")
                            @Override
                            public void onClick(View v) {
                                if (BillDAO.delete(item.getId())){
                                    list.clear();
                                    list.addAll(BillDAO.getAll());
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Delete success", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    alertDialog.dismiss();
                                }else {
                                    Toast.makeText(context, "Delete fail", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        btn_CancelDeleteProduct.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                    }
                });

                btn_CancelMoreProduct.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class BillViewHolder extends RecyclerView.ViewHolder{
        TextView tv_idTableBill, tv_idHRBill, tv_TimeBill, tv_TotalBill;
        ImageView img_MoreFromBill;
        public BillViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_idTableBill = itemView.findViewById(R.id.tv_idTableBill);
            tv_idHRBill = itemView.findViewById(R.id.tv_idHRBill);
            tv_TimeBill = itemView.findViewById(R.id.tv_TimeBill);
            tv_TotalBill = itemView.findViewById(R.id.tv_TotalBill);
            img_MoreFromBill = itemView.findViewById(R.id.img_MoreFromBill);
        }
    }
}