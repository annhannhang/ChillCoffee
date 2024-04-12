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
import com.ptnzzn.chillcoffee.dao.HRDAO;
import com.ptnzzn.chillcoffee.modal.HR;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HRAdapter extends RecyclerView.Adapter<HRAdapter.HRViewHolder>{
    Context context;
    ArrayList<HR> list;
    HRDAO dao;

    public HRAdapter(Context context, ArrayList<HR> list) {
        this.context = context;
        this.list = list;
        dao = new HRDAO(context);
    }

    @NonNull
    @Override
    public HRAdapter.HRViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_hr, parent, false);
        return new HRViewHolder(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull HRAdapter.HRViewHolder holder, int position) {
        HR item = list.get(position);
        holder.tv_NameHR.setText(item.getName());
        holder.tv_NumberPhoneHR.setText(item.getPhone());
        holder.tv_AddressHR.setText(item.getAddress());

        int imgId = context.getResources().getIdentifier(
                list.get(position).getImage(),"drawable",
                context.getPackageName());
        holder.img_HR.setImageResource(imgId);
        if (list.get(position).getImage().startsWith("http://")||
                list.get(position).getImage().startsWith("https://")){
            Picasso.get().load(list.get(position).getImage()).into(holder.img_HR);
        }

        holder.img_MoreFromHR.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_more, null);
            builder.setView(view);
            AlertDialog alertDialog = builder.create();

            LinearLayout layout_editProduct = view.findViewById(R.id.layout_editProduct);
            LinearLayout layout_deleteProduct = view.findViewById(R.id.layout_deleteProduct);
            Button btn_CancelMoreProduct = view.findViewById(R.id.btn_CancelMoreProduct);

            layout_editProduct.setOnClickListener(v1 -> {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                View view1 = LayoutInflater.from(context).inflate(R.layout.dialog_addhr, null);
                builder1.setView(view1);
                AlertDialog dialog = builder1.create();

                EditText edt_AddNameHR = view1.findViewById(R.id.edt_AddNameHR);
                EditText edt_AddNumberPhoneHR = view1.findViewById(R.id.edt_AddNumberPhoneHR);
                EditText edt_AddAddressHR = view1.findViewById(R.id.edt_AddAddressHR);
                EditText edt_AddLinkImageHR = view1.findViewById(R.id.edt_AddLinkImageHR);
                Button btn_LoadImgHR = view1.findViewById(R.id.btn_LoadImgHR);
                Button btn_AddHR = view1.findViewById(R.id.btn_AddHR);
                ImageView img_AddHR = view1.findViewById(R.id.img_AddHR);

                edt_AddNameHR.setText(item.getName());
                edt_AddNumberPhoneHR.setText(item.getPhone());
                edt_AddAddressHR.setText(item.getAddress());
                edt_AddLinkImageHR.setText(item.getImage());

                btn_LoadImgHR.setOnClickListener(v11 -> {
                    if (edt_AddLinkImageHR.getText().toString().isEmpty()) {
                        edt_AddLinkImageHR.setError("Please enter link image");
                    } else {
                        String url = edt_AddLinkImageHR.getText().toString();
                        Picasso.get().load(url).into(img_AddHR);
                    }
                });

                btn_AddHR.setOnClickListener(v112 -> {
                    String name = edt_AddNameHR.getText().toString();
                    String phone = edt_AddNumberPhoneHR.getText().toString();
                    String address = edt_AddAddressHR.getText().toString();
                    String image = edt_AddLinkImageHR.getText().toString();
                    HR item1 = new HR(name, phone, address, image);

                    if (edt_AddNameHR.getText().toString().isEmpty()) {
                        edt_AddNameHR.setError("Please enter name");
                    } else if (edt_AddNumberPhoneHR.getText().toString().isEmpty()) {
                        edt_AddNumberPhoneHR.setError("Please enter number phone");
                    } else if (edt_AddAddressHR.getText().toString().isEmpty()) {
                        edt_AddAddressHR.setError("Please enter address");
                    } else {
                        if (HRDAO.update(item1)) {
                            list.clear();
                            list.addAll(HRDAO.getAll());
                            notifyDataSetChanged();
                            dialog.dismiss();
                            alertDialog.dismiss();
                            Toast.makeText(context, "Updated item", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Can't update this item", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();
            });

            layout_deleteProduct.setOnClickListener(v12 -> {
                AlertDialog.Builder builder123 = new AlertDialog.Builder(context);
                View view12 = LayoutInflater.from(context).inflate(R.layout.dialog_confirmdelete, null);
                builder123.setView(view12);
                AlertDialog dialog = builder123.create();

                Button btn_ConfirmDeleteProduct = view12.findViewById(R.id.btn_ConfirmDeleteProduct);
                Button btn_CancelDeleteProduct = view12.findViewById(R.id.btn_CancelDeleteProduct);

                btn_ConfirmDeleteProduct.setOnClickListener(v121 -> {
                    if (HRDAO.delete(item.getId())) {
                        list.clear();
                        list.addAll(HRDAO.getAll());
                        notifyDataSetChanged();
                        dialog.dismiss();
                        alertDialog.dismiss();
                        Toast.makeText(context, "Deleted item", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Can't delete this item", Toast.LENGTH_SHORT).show();
                    }
                });

                btn_CancelDeleteProduct.setOnClickListener(v1212 -> dialog.dismiss());
                dialog.show();
            });

            btn_CancelMoreProduct.setOnClickListener(v13 -> alertDialog.dismiss());

            alertDialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class HRViewHolder extends RecyclerView.ViewHolder {
        ImageView img_HR ,img_MoreFromHR;
        TextView tv_NameHR, tv_NumberPhoneHR, tv_AddressHR;
        public HRViewHolder(@NonNull View itemView) {
            super(itemView);
            img_HR = itemView.findViewById(R.id.img_HR);
            tv_NameHR = itemView.findViewById(R.id.tv_NameHR);
            tv_NumberPhoneHR = itemView.findViewById(R.id.tv_NumberPhoneHR);
            tv_AddressHR = itemView.findViewById(R.id.tv_AddressHR);
            img_MoreFromHR = itemView.findViewById(R.id.img_MoreFromHR);
        }
    }
}
