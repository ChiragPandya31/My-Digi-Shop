package com.example.e_commerce.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.e_commerce.R;
import com.example.e_commerce.databinding.ItemCartBinding;

import com.example.e_commerce.model.Product;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.model.Item;
import com.hishd.tinycart.util.TinyCartHelper;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.cartviewHolder> {

    Context context;
    ArrayList<Product> products;
    CartListnear cartListnear;
    Cart cart;



    public interface CartListnear {
        public void onQuantityChanged();
    }

    public CartAdapter(Context context, ArrayList<Product> products, CartListnear cartListnear) {
        this.context = context;
        this.products = products;
        this.cartListnear = cartListnear;
        cart = TinyCartHelper.getCart();

    }

    @NonNull
    @Override
    public cartviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new cartviewHolder(LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull cartviewHolder holder, int position) {
        Product product = products.get(position);

        // set the product details in the view holder
        Glide.with(context)
                .load(product.getImage())
                .into(holder.binding.image);
        holder.binding.name.setText(product.getName());
        holder.binding.price.setText("₹" + product.getPrice());
        holder.binding.quaninty.setText(product.getQuantity() + "  item(s)");

        // set the remove button click listener
        holder.binding.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // remove the item from the cart
                cart.removeItem(product);
                // remove the item from the list of products in the adapter
                products.remove(product);
                // notify the adapter about the change in the data set
                notifyDataSetChanged();
                // notify the cart listener about the change in the cart
                cartListnear.onQuantityChanged();
                // show a toast message to confirm the removal
                Toast.makeText(context, product.getName() + " removed from cart", Toast.LENGTH_SHORT).show();
            }
        });
        // set the plus button click listener
        // set the plus button click listener
        holder.binding.plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = product.getQuantity();
                quantity++;
                if (quantity > product.getStock()) {
                    Toast.makeText(context, "Max Stock available " + product.getStock(), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    product.setQuantity(quantity);

                }
                notifyDataSetChanged();
                cart.updateItem(product, product.getQuantity());
                cartListnear.onQuantityChanged();
            }
        });


        holder.binding.minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = product.getQuantity();
                if (quantity > 1)
                    quantity--;
                product.setQuantity(quantity);
                notifyDataSetChanged();
                cart.updateItem(product, product.getQuantity());
                cartListnear.onQuantityChanged();
            }
        });
    }





    @Override
    public int getItemCount() {
        return products.size();
    }

    public class cartviewHolder extends RecyclerView.ViewHolder {

        ItemCartBinding binding;
        public cartviewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemCartBinding.bind(itemView);
        }
    }
}
