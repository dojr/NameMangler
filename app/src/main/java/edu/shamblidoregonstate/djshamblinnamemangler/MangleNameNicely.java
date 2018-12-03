package edu.shamblidoregonstate.djshamblinnamemangler;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MangleNameNicely extends AppCompatActivity {
    private String[] niceNames = {"MANGLED", "WUT", "NOT A REAL LAST NAME", "Smith"};
    private String[] rudeNames = {"SUCKS", "STUPID", "DUMB", "NOT COOL PERSON", "UNCOOL"};

    private static final String EXTRA_FIRST_NAME = "edu.shamblidoregonstate.djshamblinnamemangler.first_name";

    private Button mReset;
    private Button mRemangle;
    private TextView mMangledName;
    private Button mEmailButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mangle);

        final String firstName = getIntent().getStringExtra(EXTRA_FIRST_NAME);

        final String mangledName = firstName + ' ' + niceNames[(int) (Math.random() * ((niceNames.length)))];

        mMangledName = (TextView) findViewById(R.id.text_name_mangle);
        mMangledName.setText(mangledName);

        mRemangle = (Button) findViewById(R.id.button_remangle);
        mRemangle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newMangledName = firstName + ' ' + niceNames[(int) (Math.random() * ((niceNames.length)))];
                mMangledName.setText(newMangledName);
            }
        });

        mReset = (Button) findViewById(R.id.button_reset);
        mReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MangleNameNicely.this, Main.class);
                finish();
            }
        });

        mEmailButton = findViewById(R.id.send_name);
        mEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, mangledName);
                i = Intent.createChooser(i, "Send Email:");
                startActivity(i);
            }
        });
    }

    public static Intent newIntent(Context packageContext, String firstName) {
        Intent intent = new Intent(packageContext, MangleNameNicely.class);
        intent.putExtra(EXTRA_FIRST_NAME, firstName);
        return intent;
    }

}
