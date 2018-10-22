package edu.shamblidoregonstate.djshamblinnamemangler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MangleName extends AppCompatActivity {
    private String[] names = {"MANGLED", "WUT", "NOT A REAL LAST NAME", "Smith"};

    private Button mReset;
    private Button mRemangle;
    private TextView mMangledName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mangle);

        final String firstName = getIntent().getStringExtra("FirstName");

        String mangledName = firstName + ' ' + names[(int)(Math.random() * ((names.length)))];

        mMangledName = (TextView) findViewById(R.id.text_name_mangle);
        mMangledName.setText(mangledName);

        mRemangle = (Button) findViewById(R.id.button_remangle);
        mRemangle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newMangledName = firstName + ' ' + names[(int)(Math.random() * ((names.length)))];
                mMangledName.setText( newMangledName );
            }
        });

        mReset = (Button) findViewById(R.id.button_reset);
        mReset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mMangledName.setText(firstName);
            }
        });



    }
}
