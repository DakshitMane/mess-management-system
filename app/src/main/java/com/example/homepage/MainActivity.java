package com.example.homepage;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {
private CardView buttonNavigate;
private CardView CustomerDetails;

private CardView AddBreakfast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        buttonNavigate = findViewById(R.id.mark);
        buttonNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MarkAttendance.class);
                startActivity(intent);
            }
        });

        CustomerDetails = findViewById(R.id.customers);
        CustomerDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CustomerDetails.class);
                startActivity(intent);
            }
        });

        AddBreakfast = findViewById(R.id.AddBreakfast);
        AddBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddBreakfast.class);
                startActivity(intent);
            }
        });

    }
}
