package com.example.pmuny.simple_weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class EnterCityActivity extends AppCompatActivity {
    private TextInputEditText mEditText;
    private TextInputLayout mEditLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_city);

        mEditText = findViewById(R.id.editText);
        MaterialButton button = findViewById(R.id.btnGetWeather);
        mEditLayout = findViewById(R.id.lytEditText);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = mEditText.getText().toString().trim();
                if(!city.isEmpty()){
                    mEditLayout.setError(null);
                    Intent intent = new Intent(EnterCityActivity.this,MainActivity.class);
                    intent.putExtra("city",city);
                    mEditText.setText("");
                    startActivity(intent);
                } else {
                    mEditLayout.setError("Input a city first");
                }

            }
        });
    }

}
