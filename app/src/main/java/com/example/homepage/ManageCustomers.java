    package com.example.homepage;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.ImageButton;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;
    import com.firebase.ui.database.FirebaseRecyclerOptions;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.database.Query;
    public class ManageCustomers extends AppCompatActivity {

        private RecyclerView recyclerView;
        private CustomerAdapter customerAdapter;
        private DatabaseReference databaseReference;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_manage_customers);

            recyclerView = findViewById(R.id.recyclerViewCustomers);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            ImageButton navigationIcon = findViewById(R.id.navigationIcon);
            navigationIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed(); // Navigate back to previous activity
                }
            });

            // Initialize Firebase database reference
            databaseReference = FirebaseDatabase.getInstance().getReference("Customer");

            // Set up FirebaseRecyclerOptions with the query to fetch all customers
            Query query = databaseReference.orderByChild("id");
            FirebaseRecyclerOptions<Customer> options = new FirebaseRecyclerOptions.Builder<Customer>()
                    .setQuery(query, Customer.class)
                    .build();

            // Set up the customer adapter with the FirebaseRecyclerOptions
            customerAdapter = new CustomerAdapter(options);
            customerAdapter.setOnItemClickListener(new CustomerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Customer customer) {
                    // Empty method body, no action performed
                }
            });

            // Attach the adapter to the RecyclerView
            recyclerView.setAdapter(customerAdapter);
        }

        @Override
        protected void onStart() {
            super.onStart();
            // Start listening for changes in the Firebase database
            customerAdapter.startListening();
        }

        @Override
        protected void onStop() {
            super.onStop();
            // Stop listening for changes when the activity is stopped
            customerAdapter.stopListening();
        }
    }
