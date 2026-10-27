package com.example.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddCustomers extends AppCompatActivity {
    EditText editTextName, editTextPhoneNumber, editTextAddress, editTextState, editTextPincode, editTextEmail;
    Button buttonSubmit;
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customers);

        // Initialize Firebase components
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Customer");

        // Initialize UI elements
        editTextName = findViewById(R.id.editTextName);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextState = findViewById(R.id.editTextState);
        editTextPincode = findViewById(R.id.editTextPincode);
        editTextEmail = findViewById(R.id.editTextEmail);

        buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(v -> {
            // Get user input from EditText fields
            String name = editTextName.getText().toString();
            String phoneNumber = editTextPhoneNumber.getText().toString();
            String address = editTextAddress.getText().toString();
            String state = editTextState.getText().toString();
            String pincode = editTextPincode.getText().toString();
            String email = editTextEmail.getText().toString();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(address) ||
                    TextUtils.isEmpty(state) || TextUtils.isEmpty(pincode) || TextUtils.isEmpty(email)) {
                Toast.makeText(AddCustomers.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            } else {
                // Call method to add customer data to Firebase
                addDataToFirebase(name, phoneNumber, address, state, pincode, email);
            }
        });
    }

    private void addDataToFirebase(String name, String phone, String address, String state, String pincode, String email) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long customerCount = dataSnapshot.getChildrenCount() + 1; // Get the current customer count and increment by 1

                // Create a new Customer object
                Customer customer = new Customer();
                customer.setId(String.valueOf(customerCount)); // Set the ID as the incremented customer count
                customer.setName(name);
                customer.setPhoneNumber(phone);
                customer.setAddress(address);
                customer.setState(state);
                customer.setPincode(pincode);
                customer.setEmail(email);

                // Add the customer to the database
                databaseReference.child(String.valueOf(customerCount)).setValue(customer)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(AddCustomers.this, "Customer added successfully", Toast.LENGTH_SHORT).show();
                            clearInputFields();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(AddCustomers.this, "Failed to add customer: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AddCustomers.this, "Error fetching data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearInputFields() {
        // Clear all EditText fields
        editTextName.setText("");
        editTextPhoneNumber.setText("");
        editTextAddress.setText("");
        editTextState.setText("");
        editTextPincode.setText("");
        editTextEmail.setText("");
    }
}
