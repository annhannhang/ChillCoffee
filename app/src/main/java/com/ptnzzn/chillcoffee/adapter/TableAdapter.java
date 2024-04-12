package com.ptnzzn.chillcoffee.adapter;

import android.app.Activity;
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
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.ptnzzn.chillcoffee.R;
import com.ptnzzn.chillcoffee.dao.TableDAO;
import com.ptnzzn.chillcoffee.modal.Table;

import java.util.ArrayList;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.TableViewHolder>{
    Context context;
    ArrayList<Table> list;
    TableDAO dao;

    public TableAdapter(Context context, ArrayList<Table> list) {
        this.context = context;
        this.list = list;
        dao = new TableDAO(context);
    }



    @NonNull
    @Override
    public TableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_table, parent, false);
        return new TableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TableViewHolder holder, int position) {
        Table item = list.get(position);
        holder.tv_TableName.setText(item.getName());
        holder.tv_TableTime.setText(item.getTime());
        holder.tv_TablePrice.setText(item.getPrice() + " VNĐ");
        holder.tv_TableStatus.setText(item.getStatus());
        holder.img_MoreFromTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view1 = LayoutInflater.from(context).inflate(R.layout.dialog_more, null);
                builder.setView(view1);
                AlertDialog dialog = builder.create();

                LinearLayout layout_editProduct = view1.findViewById(R.id.layout_editProduct);
                LinearLayout layout_deleteProduct = view1.findViewById(R.id.layout_deleteProduct);
                Button btn_CancelMoreProduct = view1.findViewById(R.id.btn_CancelMoreProduct);

                layout_editProduct.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                        View view2 = LayoutInflater.from(context).inflate(R.layout.dialog_addtable, null);
                        builder1.setView(view2);
                        AlertDialog dialogEdit = builder1.create();

                        EditText edt_TableAddName = view2.findViewById(R.id.edt_AddTableName);
                        EditText edt_TableAddTime = view2.findViewById(R.id.edt_AddTableTime);
                        EditText edt_TableAddPrice = view2.findViewById(R.id.edt_AddTablePrice);
                        EditText edt_TableAddStatus = view2.findViewById(R.id.edt_AddTableStatus);
                        Button btn_AddTable = view2.findViewById(R.id.btn_AddTable);

                        edt_TableAddName.setText(item.getName());
                        edt_TableAddTime.setText(item.getTime());
                        int price = item.getPrice();
                        edt_TableAddPrice.setText(String.valueOf(price));
                        edt_TableAddStatus.setText(item.getStatus());

                        btn_AddTable.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String name = edt_TableAddName.getText().toString().trim();
                                String time = edt_TableAddTime.getText().toString().trim();
                                int price = Integer.parseInt(edt_TableAddPrice.getText().toString().trim());
                                String status = edt_TableAddStatus.getText().toString().trim();
                                Table table = new Table(item.getId(), name, time, price, status);
                                if (edt_TableAddName.getText().toString().isEmpty() ||
                                        edt_TableAddTime.getText().toString().isEmpty() ||
                                        edt_TableAddPrice.getText().toString().isEmpty() ||
                                        edt_TableAddStatus.getText().toString().isEmpty()) {
                                    Toast.makeText(context, "Please enter all fields", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (TableDAO.update(table)) {
                                        Toast.makeText(context, "Update successfully", Toast.LENGTH_SHORT).show();
                                        list.set(position, table);
                                        notifyDataSetChanged();
                                        dialogEdit.dismiss();
                                        dialog.dismiss();
                                    } else {
                                        Toast.makeText(context, "Update failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
                        dialogEdit.show();
                    }
                });

                layout_deleteProduct.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                        View view2 = LayoutInflater.from(context).inflate(R.layout.dialog_confirmdelete, null);
                        builder1.setView(view2);
                        AlertDialog dialogDelete = builder1.create();

                        Button btn_CancelDeleteProduct = view2.findViewById(R.id.btn_CancelDeleteProduct);
                        Button btn_ConfirmDeleteProduct = view2.findViewById(R.id.btn_ConfirmDeleteProduct);

                        btn_CancelDeleteProduct.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogDelete.dismiss();
                            }
                        });

                        btn_ConfirmDeleteProduct.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                TableDAO.delete(item.getId());
                                list.remove(position);
                                notifyDataSetChanged();
                                dialogDelete.dismiss();
                                dialog.dismiss();
                            }
                        });

                        dialogDelete.show();
                    }
                });

                btn_CancelMoreProduct.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    class TableViewHolder extends RecyclerView.ViewHolder{
        TextView tv_TableName, tv_TableTime, tv_TablePrice, tv_TableStatus;
        ImageView img_MoreFromTable;
        public TableViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_TableName = itemView.findViewById(R.id.tv_TableName);
            tv_TableTime = itemView.findViewById(R.id.tv_TableTime);
            tv_TablePrice = itemView.findViewById(R.id.tv_TablePrice);
            tv_TableStatus = itemView.findViewById(R.id.tv_TableStatus);
            img_MoreFromTable = itemView.findViewById(R.id.img_MoreFromTable);
        }
    }
}
