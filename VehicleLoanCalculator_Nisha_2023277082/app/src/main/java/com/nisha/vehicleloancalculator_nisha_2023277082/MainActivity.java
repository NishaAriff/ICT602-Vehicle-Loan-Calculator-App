package com.nisha.vehicleloancalculator_nisha_2023277082;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    private EditText editTextVehiclePrice, editTextDownPayment, editTextLoanPeriod, editTextInterestRate;
    private TextView textViewLoanAmount, textViewTotalInterest, textViewTotalPayment, textViewMonthlyPayment;
    private CardView resultsCard;
    private Button buttonAbout, buttonHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();

        Button buttonCalculate = findViewById(R.id.buttonCalculate);
        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateLoan();
            }
        });

        Button buttonReset = findViewById(R.id.buttonReset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetCalculator();
            }
        });

        buttonAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAboutPage();
            }
        });

        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "You're already on Home page", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initializeViews() {
        editTextVehiclePrice = findViewById(R.id.editTextVehiclePrice);
        editTextDownPayment = findViewById(R.id.editTextDownPayment);
        editTextLoanPeriod = findViewById(R.id.editTextLoanPeriod);
        editTextInterestRate = findViewById(R.id.editTextInterestRate);

        textViewLoanAmount = findViewById(R.id.textViewLoanAmount);
        textViewTotalInterest = findViewById(R.id.textViewTotalInterest);
        textViewTotalPayment = findViewById(R.id.textViewTotalPayment);
        textViewMonthlyPayment = findViewById(R.id.textViewMonthlyPayment);

        resultsCard = findViewById(R.id.resultsCard);
        buttonAbout = findViewById(R.id.buttonAbout);
        buttonHome = findViewById(R.id.buttonHome);
    }

    private void calculateLoan() {
        try {
            double vehiclePrice = Double.parseDouble(editTextVehiclePrice.getText().toString());
            double downPayment = Double.parseDouble(editTextDownPayment.getText().toString());
            int loanPeriod = Integer.parseInt(editTextLoanPeriod.getText().toString());
            double interestRate = Double.parseDouble(editTextInterestRate.getText().toString());

            if (downPayment >= vehiclePrice) {
                Toast.makeText(this, "Down payment must be less than vehicle price!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (loanPeriod <= 0) {
                Toast.makeText(this, "Loan period must be greater than 0!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (interestRate <= 0) {
                Toast.makeText(this, "Interest rate must be greater than 0!", Toast.LENGTH_SHORT).show();
                return;
            }

            double loanAmount = vehiclePrice - downPayment;
            double totalInterest = loanAmount * (interestRate / 100) * loanPeriod;
            double totalPayment = loanAmount + totalInterest;
            double monthlyPayment = totalPayment / (loanPeriod * 12);

            textViewLoanAmount.setText(String.format("RM %.2f", loanAmount));
            textViewTotalInterest.setText(String.format("RM %.2f", totalInterest));
            textViewTotalPayment.setText(String.format("RM %.2f", totalPayment));
            textViewMonthlyPayment.setText(String.format("RM %.2f", monthlyPayment));

            resultsCard.setVisibility(View.VISIBLE);

            Toast.makeText(this, "Calculation completed!", Toast.LENGTH_SHORT).show();

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please fill in all fields with valid numbers!", Toast.LENGTH_SHORT).show();
        }
    }

    private void resetCalculator() {
        editTextVehiclePrice.setText("");
        editTextDownPayment.setText("");
        editTextLoanPeriod.setText("");
        editTextInterestRate.setText("");

        textViewLoanAmount.setText("RM 0.00");
        textViewTotalInterest.setText("RM 0.00");
        textViewTotalPayment.setText("RM 0.00");
        textViewMonthlyPayment.setText("RM 0.00");

        resultsCard.setVisibility(View.GONE);

        Toast.makeText(this, "Calculator reset!", Toast.LENGTH_SHORT).show();
    }

    private void goToAboutPage() {
        Intent intent = new Intent(MainActivity.this, AboutActivity.class);
        startActivity(intent);
    }
}