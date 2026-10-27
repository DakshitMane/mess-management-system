package com.example.homepage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import android.content.Intent;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class AddCustomers extends AppCompatActivity {
    EditText editTextName, editTextPhoneNumber, editTextAddress, editTextState, editTextPincode, editTextEmail;
    Button buttonSubmit;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customers);

        editTextName = findViewById(R.id.editTextName);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextState = findViewById(R.id.editTextState);
        editTextPincode = findViewById(R.id.editTextPincode);
        editTextEmail = findViewById(R.id.editTextEmail);
        DBHelper DB = new DBHelper(this);
        Button buttonSubmit = findViewById(R.id.buttonSubmit);
        if (buttonSubmit != null) {
            buttonSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = editTextName.getText().toString();
                    String phoneNumber = editTextPhoneNumber.getText().toString();
                    String address = editTextAddress.getText().toString();
                    String state = editTextState.getText().toString();
                    String pincode = editTextPincode.getText().toString();
                    String email = editTextEmail.getText().toString();

                    SharedPreferences sharedPreferences = getSharedPreferences("customer_data", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    // Generate a unique customer ID
                    int customerId = sharedPreferences.getInt("customer_count", 0) + 1;

                    editor.putString("customer_name_" + customerId, name);
                    editor.putString("customer_phone_" + customerId, phoneNumber);
                    editor.putString("customer_address_" + customerId, address);
                    editor.putString("customer_name_" + customerId, state);
                    editor.putString("customer_name_" + customerId, pincode);
                    editor.putString("customer_name_" + customerId, email);


                    // Increment the customer count
                    editor.putInt("customer_count", customerId);

                    editor.apply();

                    long checkinsertdata = DB.insertUser(name, phoneNumber, address, state, pincode, email);
                    if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phoneNumber)) {
                        Toast.makeText(AddCustomers.this, "Invalid data. Please fill in all fields.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (checkinsertdata != -1) {
                        Toast.makeText(AddCustomers.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(AddCustomers.this, ManageCustomers.class);
                        intent.putExtra("customer_id", customerId);
                        startActivity(intent);

                    } else {
                        Toast.makeText(AddCustomers.this, "New Entry Not Inserted", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}


