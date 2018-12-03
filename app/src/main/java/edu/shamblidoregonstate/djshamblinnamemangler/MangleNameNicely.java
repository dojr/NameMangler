package edu.shamblidoregonstate.djshamblinnamemangler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class MangleNameNicely extends AppCompatActivity {
    private String[] niceNames = {"MANGLED", "WUT", "NOT A REAL LAST NAME", "Smith"};

    private static final String EXTRA_FIRST_NAME = "edu.shamblidoregonstate.djshamblinnamemangler.first_name";
    private static final int REQUEST_PHOTO = 1;
    private Button mReset;
    private Button mRemangle;
    private TextView mMangledName;
    private Button mEmailButton;
    private Button mPhotoButton;
    private ImageView mPhotoView;
    private File mPhotoFile;

    public static Bitmap getScaledBitmap(String path, int destWidth, int destHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;
        int inSampleSize = 1;
        if (srcHeight > destHeight || srcWidth > destWidth) {
            float heightScale = srcHeight / destHeight;
            float widthScale = srcWidth / destWidth;
            inSampleSize = Math.round(heightScale > widthScale ? heightScale :
                    widthScale);
        }
        options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;
        return BitmapFactory.decodeFile(path, options);
    }

    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mPhotoView.setImageDrawable(null);
        } else {
            Bitmap bitmap = getScaledBitmap(
                    mPhotoFile.getPath(), 100, 100);
            mPhotoView.setImageBitmap(bitmap);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mangle);
        File filesDir = MangleNameNicely.this.getFilesDir();
        mPhotoFile = new File(filesDir, "IMG_" + UUID.randomUUID() + ".jpg");

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

        mPhotoButton = findViewById(R.id.add_photo);
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        boolean canTakePhoto = mPhotoFile != null && captureImage.resolveActivity(packageManager) != null;
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = FileProvider.getUriForFile(MangleNameNicely.this,
                        "edu.shamblidoregonstate.djshamblinnamemangler.fileprovider",
                        mPhotoFile);
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                List<ResolveInfo> cameraActivities = MangleNameNicely.this
                        .getPackageManager().queryIntentActivities(captureImage,
                                PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo activity : cameraActivities) {
                    MangleNameNicely.this.grantUriPermission(activity.activityInfo.packageName,
                            uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }
                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });
        mPhotoView = (ImageView) findViewById(R.id.mangle_image);
        updatePhotoView();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        } else if (requestCode == REQUEST_PHOTO) {
            Uri uri = FileProvider.getUriForFile(MangleNameNicely.this,
                    "edu.shamblidoregonstate.djshamblinnhjamemangler.fileprovider",
                    mPhotoFile);
            MangleNameNicely.this.revokeUriPermission(uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            updatePhotoView();
        }
    }


    public static Intent newIntent(Context packageContext, String firstName) {
        Intent intent = new Intent(packageContext, MangleNameNicely.class);
        intent.putExtra(EXTRA_FIRST_NAME, firstName);
        return intent;
    }

}
