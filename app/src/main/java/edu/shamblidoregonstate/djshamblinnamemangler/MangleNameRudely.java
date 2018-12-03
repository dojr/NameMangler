package edu.shamblidoregonstate.djshamblinnamemangler;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MangleNameRudely extends AppCompatActivity {
    private String[] rudeNames = {"SUCKS", "STUPID", "DUMB", "NOT COOL PERSON", "UNCOOL"};

    private static final String EXTRA_FIRST_NAME = "edu.shamblidoregonstate.djshamblinnamemangler.first_name";

    private Button mReset;
    private Button mRemangle;
    private TextView mMangledName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mangle);
        final String firstName = getIntent().getStringExtra(EXTRA_FIRST_NAME);

        String mangledName = firstName + ' ' + rudeNames[(int)(Math.random() * ((rudeNames.length)))];

        mMangledName = (TextView) findViewById(R.id.text_name_mangle);
        mMangledName.setText(mangledName);

        mRemangle = (Button) findViewById(R.id.button_remangle);
        mRemangle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newMangledName = firstName + ' ' + rudeNames[(int)(Math.random() * ((rudeNames.length)))];
                mMangledName.setText( newMangledName );
            }
        });

        mReset = (Button) findViewById(R.id.button_reset);
        mReset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MangleNameRudely.this, Main.class);
                finish();
                }
            });
    }

    public static Intent newIntent(Context packageContext, String firstName) {
        Intent intent = new Intent(packageContext, MangleNameRudely.class);
        intent.putExtra(EXTRA_FIRST_NAME, firstName);
        return intent;
    }
}
