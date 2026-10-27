package com.example.homepage;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class MarkAttendance extends AppCompatActivity {

    private Button btnPresentLunch;
    private Button btnAbsentLunch;
    private Button btnPresentDinner;
    private Button btnAbsentDinner;
    private EditText editTextCustomerId;

    private boolean isLunchTime;
    private boolean isDinnerTime;

    private boolean isPresentLunch; // Flag to track if present for lunch is marked
    private boolean isPresentDinner; // Flag to track if present for dinner is marked

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance);

        // Initialize views
        btnPresentLunch = findViewById(R.id.btnPresentLunch);
        btnAbsentLunch = findViewById(R.id.btnAbsentLunch);
        btnPresentDinner = findViewById(R.id.btnPresentDinner);
        btnAbsentDinner = findViewById(R.id.btnAbsentDinner);

        // Assuming R.id.your_text_input_layout is the ID of your TextInputLayout
        TextInputLayout textInputLayout = findViewById(R.id.editTextCustomerId);
        editTextCustomerId = textInputLayout.getEditText();  // Correct way to get EditText from TextInputLayout

        // Initialize shared preferences
        SharedPreferences sharedPref = getSharedPreferences("AttendanceStatus", Context.MODE_PRIVATE);
        isPresentLunch = sharedPref.getBoolean("isPresentLunch", false);
        isPresentDinner = sharedPref.getBoolean("isPresentDinner", false);

        // Set click listeners for marking attendance buttons
        btnPresentLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markAttendance("Lunch", true);
            }
        });

        btnAbsentLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markAttendance("Lunch", false);
            }
        });

        btnPresentDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markAttendance("Dinner", true);
            }
        });

        btnAbsentDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markAttendance("Dinner", false);
            }
        });

        // Get the current time
        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        // Check if it's lunch or dinner time
        isLunchTime = (hourOfDay >= 11 && hourOfDay < 15); // 11pm to 3pm
        isDinnerTime = (hourOfDay >= 19 && hourOfDay < 22); // 7pm to 10pm

        // Disable buttons if not in lunch or dinner time
        if (!isLunchTime) {
            btnPresentLunch.setEnabled(false);
            btnAbsentLunch.setEnabled(false);
        }
        if (!isDinnerTime) {
            btnPresentDinner.setEnabled(false);
            btnAbsentDinner.setEnabled(false);
        }
    }

    private void markAttendance(String type, boolean isPresent) {
        if (editTextCustomerId == null || TextUtils.isEmpty(editTextCustomerId.getText())) {
            Toast.makeText(this, "Please enter a valid Customer ID", Toast.LENGTH_SHORT).show();
            return;
        }

        String customerId = editTextCustomerId.getText().toString().trim();

        if (TextUtils.isEmpty(customerId)) {
            Toast.makeText(this, "Please enter a valid Customer ID", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update customer's account balance
        updateCustomerBalance(customerId, isPresent, type);
    }

    private void updateCustomerBalance(String customerId, boolean isPresent, String type) {
        DatabaseReference customerRef = FirebaseDatabase.getInstance().getReference("Customer").child(customerId);
        customerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Customer customer = snapshot.getValue(Customer.class);
                    double updatedBalance = customer.getAccountBalance();

                    if (isPresent) {
                        updatedBalance += 50.00; // Add Rs 50 for present
                    } else {
                        updatedBalance -= 50.00; // Deduct Rs 50 for absence
                    }

                    // Update customer's account balance in Firebase
                    customer.setAccountBalance(updatedBalance);
                    customerRef.setValue(customer)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    String message = isPresent ? type + " attendance marked and charged Rs 50" : type + " attendance marked and deducted Rs 50";
                                    Toast.makeText(MarkAttendance.this, message, Toast.LENGTH_SHORT).show();
                                    clearInputFields();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MarkAttendance.this, "Failed to update customer's account: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(MarkAttendance.this, "Customer with ID " + customerId + " does not exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MarkAttendance.this, "Error retrieving customer data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearInputFields() {
        editTextCustomerId.setText("");
    }
}

