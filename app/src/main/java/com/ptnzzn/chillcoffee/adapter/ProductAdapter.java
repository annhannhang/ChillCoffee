package com.ptnzzn.chillcoffee.adapter;

import android.annotation.SuppressLint;
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
import com.ptnzzn.chillcoffee.dao.ProductDAO;
import com.ptnzzn.chillcoffee.modal.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{
    Context context;
    ArrayList<Product> list;
    ProductDAO dao;

    public ProductAdapter(Context context, ArrayList<Product> list) {
        this.context = context;
        this.list = list;
        dao = new ProductDAO(context);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product item = list.get(position);

        holder.tv_NameProduct.setText(item.getName());
        holder.tvPriceProduct.setText(item.getPrice() +"đ");

        int imgId = context.getResources().getIdentifier(
                list.get(position).getImage(),"drawable",
                context.getPackageName());
        holder.img_Product.setImageResource(imgId);

        if (list.get(position).getImage().startsWith("http://")||
                list.get(position).getImage().startsWith("https://")){
            Picasso.get().load(list.get(position).getImage()).into(holder.img_Product);
        }
        holder.img_MoreFromProduct.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_more, null);
            builder.setView(view);
            AlertDialog dialog123 = builder.create();

            LinearLayout layout_editProduct = view.findViewById(R.id.layout_editProduct);
            LinearLayout layout_deleteProduct = view.findViewById(R.id.layout_deleteProduct);
            Button btn_CancelMoreProduct = view.findViewById(R.id.btn_CancelMoreProduct);

            layout_editProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //dialog add but edit
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    View view = LayoutInflater.from(context).inflate(R.layout.dialog_addproduct, null);
                    builder.setView(view);
                    AlertDialog dialog = builder.create();

                    Button btn_LoadImgProduct = view.findViewById(R.id.btn_LoadImgProduct);
                    Button btn_AddProduct = view.findViewById(R.id.btn_AddProduct);
                    EditText edt_AddLinkImageProduct = view.findViewById(R.id.edt_AddLinkImageProduct);
                    EditText edt_AddNameProduct = view.findViewById(R.id.edt_AddNameProduct);
                    EditText edt_AddPriceProduct = view.findViewById(R.id.edt_AddPriceProduct);
                    ImageView img_AddProduct = view.findViewById(R.id.img_AddProduct);

                    edt_AddNameProduct.setText(item.getName());
                    int priceInt = item.getPrice();
                    edt_AddPriceProduct.setText(String.valueOf(priceInt));
                    edt_AddLinkImageProduct.setText(item.getImage());

                    btn_LoadImgProduct.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (edt_AddLinkImageProduct.getText().toString().isEmpty()) {
                                edt_AddLinkImageProduct.setError("Please enter link image");
                            } else {
                                String url = edt_AddLinkImageProduct.getText().toString();
                                Picasso.get().load(url).into(img_AddProduct);
                            }
                        }
                    });

                    btn_AddProduct.setOnClickListener(new View.OnClickListener() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onClick(View v) {
                            String name = edt_AddNameProduct.getText().toString();
                            int price = 0;
                            if (edt_AddPriceProduct.getText().toString().isEmpty()) {
                                edt_AddPriceProduct.setError("Please enter price");
                            } else {
                                price = Integer.parseInt(edt_AddPriceProduct.getText().toString());
                            }
                            String image = edt_AddLinkImageProduct.getText().toString();
                            Product product = new Product(item.getId(), name, price, image);
                            if (edt_AddNameProduct.getText().toString().isEmpty()) {
                                edt_AddNameProduct.setError("Please enter name");
                            } else if (edt_AddLinkImageProduct.getText().toString().isEmpty()) {
                                edt_AddLinkImageProduct.setError("Please enter link image");
                            } else {
                                if (ProductDAO.update(product)) {
                                    list.clear();
                                    list.addAll(ProductDAO.getAll());
                                    notifyDataSetChanged();
                                    dialog.dismiss();
                                    dialog123.dismiss();
                                    Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                    dialog.show();
                }
            });

            layout_deleteProduct.setOnClickListener(new View.OnClickListener() {
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
                            if (ProductDAO.delete(item.getId())) {
                                list.clear();
                                list.addAll(ProductDAO.getAll());
                                notifyDataSetChanged();
                                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                dialog123.dismiss();
                            } else {
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                dialog123.dismiss();
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
                    dialog123.dismiss();
                }
            });

            dialog123.show();
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder{
        TextView tv_NameProduct, tvPriceProduct;
        ImageView img_Product, img_MoreFromProduct;

        public ProductViewHolder(View view) {
            super(view);
            tv_NameProduct = view.findViewById(R.id.tv_NameProduct);
            tvPriceProduct = view.findViewById(R.id.tvPriceProduct);
            img_Product = view.findViewById(R.id.img_Product);
            img_MoreFromProduct = view.findViewById(R.id.img_MoreFromProduct);
        }
    }

}