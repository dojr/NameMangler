package edu.shamblidoregonstate.djshamblinnamemangler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main extends AppCompatActivity {

    private Button mButtonMangle;
    private EditText mFirstNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirstNameText = (EditText) findViewById(R.id.first_name_text);

        mButtonMangle = (Button) findViewById(R.id.button_mangle);
        mButtonMangle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFirstNameText.getText().length() <= 0) {
                    Toast.makeText(Main.this, "Name cannot be empty", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    Intent MangleName = new Intent(Main.this, MangleName.class);
                    MangleName.putExtra("FirstName", mFirstNameText.getText().toString());

                    startActivity(MangleName);
                }
            }
        });


    }
}