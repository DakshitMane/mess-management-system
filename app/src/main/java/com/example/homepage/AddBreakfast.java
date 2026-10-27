package com.example.homepage;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddBreakfast extends AppCompatActivity {

    private TextView textViewTotalBill;
    private Button addToMonthlyBillButton;

    // Variables for quantity of each item
    private int quantityDosa = 0;
    private int quantityKachori = 0;
    private int quantityIdli = 0;
    private int quantityMeduvada = 0;

    private int quantityParatha = 0;
    private int quantityPoha = 0;
    private int quantitySamosa = 0;
    private int quantityUpma = 0;

    private int quantityVadapav = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_breakfast);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set the navigation icon click listener
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the back navigation here
                finish();
            }
        });

        // Initialize views
        textViewTotalBill = findViewById(R.id.textViewTotalBill);
        addToMonthlyBillButton = findViewById(R.id.buttonAddMonthlybill);
        EditText editTextCustomerId = findViewById(R.id.editTextCustomerId); // Replace R.id.editTextCustomerId with your actual ID

        // Set click listener for the Add To Monthly Bill button
        addToMonthlyBillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the entered customer ID
                String customerId = editTextCustomerId.getText().toString().trim();

                // Validate if the customer ID is not empty
                if (TextUtils.isEmpty(customerId)) {
                    Toast.makeText(AddBreakfast.this, "Please enter a valid Customer ID", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Calculate total bill amount based on the quantity of each item
                double totalBill = calculateTotalBill();

                // Retrieve customer data from Firebase based on the entered ID
                DatabaseReference customerRef = FirebaseDatabase.getInstance().getReference("Customer").child(customerId);
                customerRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            // Retrieve customer object from Firebase
                            Customer customer = snapshot.getValue(Customer.class);

                            // Update the total breakfast amount for the customer
                            double updatedBreakfastAmount = customer.getTotalBreakfastAmount() + totalBill;
                            customer.setTotalBreakfastAmount(updatedBreakfastAmount);

                            // Update customer data in Firebase
                            customerRef.setValue(customer).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(AddBreakfast.this, "Breakfast amount added to customer's account", Toast.LENGTH_SHORT).show();
                                    // Clear the input fields and reset the quantity
                                    clearInputFields();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AddBreakfast.this, "Failed to update customer's account: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(AddBreakfast.this, "Customer with ID " + customerId + " does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddBreakfast.this, "Error retrieving customer data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // Set quantity button listeners
        setQuantityButtonListeners();
    }

    private void setQuantityButtonListeners() {
        findViewById(R.id.buttonIncreaseDo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantityDosa++;
                updateQuantityTextView(R.id.textViewQuantityDo, quantityDosa);
                updateTotalBill();
            }
        });

        // Implement listeners for other buttons similarly
        // ...

        findViewById(R.id.buttonDecreaseDo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantityDosa > 0) {
                    quantityDosa--;
                    updateQuantityTextView(R.id.textViewQuantityDo, quantityDosa);
                    updateTotalBill();
                }
            }
        });

            findViewById(R.id.buttonIncreaseK).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    quantityKachori++;
                    updateQuantityTextView(R.id.textViewQuantityK, quantityKachori);
                    updateTotalBill();
                }
            });

            // Implement listeners for other buttons similarly
            // ...

            findViewById(R.id.buttonDecreaseK).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (quantityKachori > 0) {
                        quantityKachori--;
                        updateQuantityTextView(R.id.textViewQuantityK, quantityKachori);
                        updateTotalBill();
                    }
                }
            });

            findViewById(R.id.buttonIncreaseI).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    quantityIdli++;
                    updateQuantityTextView(R.id.textViewQuantityI, quantityIdli);
                    updateTotalBill();
                }
            });

            // Implement listeners for other buttons similarly
            // ...

            findViewById(R.id.buttonDecreaseI).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (quantityIdli > 0) {
                        quantityIdli--;
                        updateQuantityTextView(R.id.textViewQuantityI, quantityIdli);
                        updateTotalBill();
                    }
                }
            });

        findViewById(R.id.buttonIncreaseM).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantityMeduvada++;
                updateQuantityTextView(R.id.textViewQuantityM, quantityMeduvada);
                updateTotalBill();
            }
        });

        // Implement listeners for other buttons similarly
        // ...

        findViewById(R.id.buttonDecreaseM).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantityMeduvada > 0) {
                    quantityIdli--;
                    updateQuantityTextView(R.id.textViewQuantityM, quantityMeduvada);
                    updateTotalBill();
                }
            }
        });

        findViewById(R.id.buttonIncreasePa).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantityParatha++;
                updateQuantityTextView(R.id.textViewQuantityPa, quantityParatha);
                updateTotalBill();
            }
        });

        // Implement listeners for other buttons similarly
        // ...

        findViewById(R.id.buttonDecreasePa).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantityParatha > 0) {
                    quantityParatha--;
                    updateQuantityTextView(R.id.textViewQuantityPa, quantityParatha);
                    updateTotalBill();
                }
            }
        });

        findViewById(R.id.buttonIncreasePo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantityPoha++;
                updateQuantityTextView(R.id.textViewQuantityPo, quantityPoha);
                updateTotalBill();
            }
        });

        // Implement listeners for other buttons similarly
        // ...

        findViewById(R.id.buttonDecreasePo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantityPoha > 0) {
                    quantityPoha--;
                    updateQuantityTextView(R.id.textViewQuantityPo, quantityPoha);
                    updateTotalBill();
                }
            }
        });

        findViewById(R.id.buttonIncreaseS).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantitySamosa++;
                updateQuantityTextView(R.id.textViewQuantityS, quantitySamosa);
                updateTotalBill();
            }
        });

        // Implement listeners for other buttons similarly
        // ...

        findViewById(R.id.buttonDecreaseS).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantitySamosa > 0) {
                    quantitySamosa--;
                    updateQuantityTextView(R.id.textViewQuantityS, quantitySamosa);
                    updateTotalBill();
                }
            }
        });

        findViewById(R.id.buttonIncreaseU).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantityUpma++;
                updateQuantityTextView(R.id.textViewQuantityU, quantityUpma);
                updateTotalBill();
            }
        });

        // Implement listeners for other buttons similarly
        // ...

        findViewById(R.id.buttonDecreaseU).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantityUpma > 0) {
                    quantityUpma--;
                    updateQuantityTextView(R.id.textViewQuantityU, quantityUpma);
                    updateTotalBill();
                }
            }
        });

        findViewById(R.id.buttonIncreaseV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantityVadapav++;
                updateQuantityTextView(R.id.textViewQuantityV, quantityVadapav);
                updateTotalBill();
            }
        });

        // Implement listeners for other buttons similarly
        // ...

        findViewById(R.id.buttonDecreaseV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantityVadapav > 0) {
                    quantityVadapav--;
                    updateQuantityTextView(R.id.textViewQuantityV, quantityVadapav);
                    updateTotalBill();
                }
            }
        });
    }




    private void updateQuantityTextView(int textViewId, int quantity) {
        TextView quantityTextView = findViewById(textViewId);
        quantityTextView.setText(String.valueOf(quantity));
    }

    private void updateTotalBill() {
        double totalBill = calculateTotalBill();
        textViewTotalBill.setText("Total Bill: Rs " + String.format("%.2f", totalBill));
    }

    private double calculateTotalBill() {
        // Calculate total bill amount based on the quantity of each item
        double totalBill =
                (quantityDosa * 40.00) +
                        (quantityKachori * 10.00) +
                        (quantityIdli * 30.00) +
                        (quantityMeduvada * 30) +
                        (quantityParatha * 30) +
                        (quantityPoha * 30) +
                        (quantitySamosa * 10) +
                        (quantityUpma * 25) +
                        (quantityVadapav * 10);
        return totalBill;
    }

    private void clearInputFields() {
        // Reset the quantity of each item to zero
        quantityDosa = 0;
        quantityKachori = 0;
        quantityIdli = 0;
        quantityMeduvada = 0;
        quantityParatha = 0;
        quantityPoha = 0;
        quantitySamosa = 0;
        quantityUpma = 0;
        quantityVadapav = 0;

        // Reset quantity text views
        updateQuantityTextView(R.id.textViewQuantityDo, quantityDosa);
        updateQuantityTextView(R.id.textViewQuantityK, quantityKachori);
        updateQuantityTextView(R.id.textViewQuantityI, quantityIdli);
        updateQuantityTextView(R.id.textViewQuantityM, quantityMeduvada);
        updateQuantityTextView(R.id.textViewQuantityPa, quantityParatha);
        updateQuantityTextView(R.id.textViewQuantityPo, quantityPoha);
        updateQuantityTextView(R.id.textViewQuantityS, quantitySamosa);
        updateQuantityTextView(R.id.textViewQuantityU, quantityUpma);
        updateQuantityTextView(R.id.textViewQuantityV, quantityVadapav);
        // Reset other quantity text views as needed

        // Update total bill text view
        updateTotalBill();
    }
}
