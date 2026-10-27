package com.example.homepage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

    public class CustomerAdapter extends FirebaseRecyclerAdapter<Customer, CustomerAdapter.CustomerViewHolder> {

    private OnItemClickListener listener;

    public CustomerAdapter(@NonNull FirebaseRecyclerOptions<Customer> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull CustomerViewHolder holder, int position, @NonNull Customer model) {
        holder.bind(model);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onItemClick(getItem(position)); // Pass clicked customer to listener
                }
            }
        });

        // Set onClickListener for "Bill Paid" button
        holder.buttonBillPaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Customer customer = getItem(position);
                    updateBillAndBalance(customer.getId());
                    Toast.makeText(v.getContext(), "Bill Paid for " + customer.getName(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateBillAndBalance(String customerId) {
        DatabaseReference customerRef = FirebaseDatabase.getInstance().getReference("Customer").child(customerId);
        customerRef.child("totalBreakfastAmount").setValue(0);
        customerRef.child("accountBalance").setValue(0);
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_customer, parent, false);
        return new CustomerViewHolder(view);
    }

    public static class CustomerViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewId;
        TextView textViewPhoneNumber;
        TextView textViewTotalBill;
        Button buttonBillPaid;

        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewId = itemView.findViewById(R.id.textViewId);
            textViewPhoneNumber = itemView.findViewById(R.id.textViewPhoneNumber);
            textViewTotalBill = itemView.findViewById(R.id.textViewTotalBill);
            buttonBillPaid = itemView.findViewById(R.id.buttonBillPaid);
        }

        public void bind(Customer customer) {
            textViewName.setText("Name: " + customer.getName());
            textViewId.setText("ID: " + customer.getId());
            textViewPhoneNumber.setText("Phone: " + customer.getPhoneNumber());

            double totalBill = customer.getTotalBreakfastAmount() + customer.getAccountBalance();
            textViewTotalBill.setText("Total Bill: Rs " + totalBill);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Customer customer);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
