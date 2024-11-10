package com.example.midterm;
import com.example.midterm.R;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private String input = "";
    private String operator = "";
    private double num1, num2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editTextNumber);

        setNumberClickListener(R.id.button0, "0");
        setNumberClickListener(R.id.button1, "1");
        setNumberClickListener(R.id.button2, "2");
        setNumberClickListener(R.id.button3, "3");
        setNumberClickListener(R.id.button4, "4");
        setNumberClickListener(R.id.button5, "5");
        setNumberClickListener(R.id.button6, "6");
        setNumberClickListener(R.id.button7, "7");
        setNumberClickListener(R.id.button8, "8");
        setNumberClickListener(R.id.button9, "9");

        setOperatorClickListener(R.id.buttonAdd, "+");
        setOperatorClickListener(R.id.buttonMinus, "-");
        setOperatorClickListener(R.id.buttonMultiply, "*");
        setOperatorClickListener(R.id.buttonDivide, "/");

        findViewById(R.id.buttonClear).setOnClickListener(view -> clear());
        findViewById(R.id.buttonEquals).setOnClickListener(view -> calculateResult());
    }

    private void setNumberClickListener(int buttonId, final String value) {
        findViewById(buttonId).setOnClickListener(view -> {
            input += value;
            editText.setText(input);
        });
    }

    private void setOperatorClickListener(int buttonId, final String op) {
        findViewById(buttonId).setOnClickListener(view -> {
            if (!input.isEmpty()) {
                num1 = Double.parseDouble(input);
                operator = op;
                input = "";
            }
        });
    }

    private void calculateResult() {
        if (!input.isEmpty()) {
            num2 = Double.parseDouble(input);
            double result = 0;
            switch (operator) {
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                case "/":
                    if (num2 != 0) result = num1 / num2;
                    else editText.setText("Error");
                    break;
            }
            editText.setText(String.valueOf(result));
            input = String.valueOf(result);
        }
    }

    private void clear() {
        input = "";
        operator = "";
        num1 = 0;
        num2 = 0;
        editText.setText("0");
    }
}
