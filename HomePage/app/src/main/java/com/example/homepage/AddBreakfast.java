package com.example.homepage;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

public class AddBreakfast extends AppCompatActivity {

    private TextView textViewTotalBill;
    private Button addToMonthlyBillButton;

    // Variables for quantity of each item
    private int quantityDabeli = 0;
    private int quantityDosa=0;
    private int quantityKachori = 0;
    private int quantityIdli = 0;
    private int quantityMeduvada= 0;

    private int quantityParatha=0;
    private int quantityPoha=0;
    private int quantitySamosa=0;
    private int quantityUpma=0;

    private int quantityVadapav=0;
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
                onBackPressed();
            }
        });
        // Initialize views
        textViewTotalBill = findViewById(R.id.textViewTotalBill);
        addToMonthlyBillButton = findViewById(R.id.buttonAddMonthlybill);

        // Set click listener for the Add To Monthly Bill button
        addToMonthlyBillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Calculate total bill amount based on the quantity of each item
                double totalBill = (quantityDabeli * 10.00) +
                        (quantityDosa * 40.00) +
                        (quantityKachori * 10.00) +
                        (quantityIdli * 30.00) +
                        (quantityMeduvada * 30)+
                        (quantityParatha* 30)+
                        (quantityPoha* 30)+
                        (quantitySamosa * 10)+
                        (quantityUpma* 25)+
                        (quantityVadapav * 10);
                textViewTotalBill.setText("Total Bill: Rs " + String.format("%.2f", totalBill));

                //Adding bill to the database
                Toast.makeText(AddBreakfast.this,"Added to Customer ID",Toast.LENGTH_SHORT).show();
            }
        });

        // Set click listeners for increase and decrease buttons of each item
        setQuantityButtonListeners();
    }

    private void setQuantityButtonListeners() {
        // Increase and decrease buttons for Eggs
        findViewById(R.id.buttonIncrease).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantityDabeli++;
                updateQuantityTextView(R.id.textViewQuantityDa, quantityParatha);
                updateTotalBill();
            }
        });

        findViewById(R.id.buttonDecrease).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantityDabeli > 0) {
                    quantityDabeli--;
                    updateQuantityTextView(R.id.textViewQuantityDa, quantityDabeli);
                    updateTotalBill();
                }
            }
        });

        // Increase and decrease buttons for Toast
        findViewById(R.id.buttonIncreaseDo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantityDosa++;
                updateQuantityTextView(R.id.textViewQuantityDo, quantityDosa);
                updateTotalBill();
            }
        });

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

        // Increase and decrease buttons for Cereals
        findViewById(R.id.buttonIncreaseK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantityKachori++;
                updateQuantityTextView(R.id.textViewQuantityK, quantityKachori);
                updateTotalBill();
            }
        });

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

        // Increase and decrease buttons for Toast
        findViewById(R.id.buttonIncreaseI).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantityIdli++;
                updateQuantityTextView(R.id.textViewQuantityI, quantityIdli);
                updateTotalBill();
            }
        });

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

        findViewById(R.id.buttonDecreaseM).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantityMeduvada > 0) {
                    quantityMeduvada--;
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

        findViewById(R.id.buttonDecreaseU).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantityUpma> 0) {
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
        // Calculate total bill amount based on the quantity of each item
        double totalBill = (quantityDabeli * 10.00) +
                (quantityDosa * 40.00) +
                (quantityKachori * 10.00) +
                (quantityIdli * 30.00) +
                (quantityMeduvada * 30)+
                (quantityParatha* 30)+
                (quantityPoha* 30)+
                (quantitySamosa * 10)+
                (quantityUpma* 25)+
                (quantityVadapav * 10);
        textViewTotalBill.setText("Total Bill: Rs " + String.format("%.2f", totalBill));
    }
}
