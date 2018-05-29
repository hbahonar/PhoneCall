package ir.hbahonar.phonecall;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private AppCompatActivity context;
    private int REQUEST_CALL_PERMISSION=1;
    private String number="0123456789";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context=this;

        findViewById(R.id.btnCallWithoutPermission).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //In this method there is no need permission in androidmanifest.xml
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        findViewById(R.id.btnCallWithPermission).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //to use this method, add <uses-permission android:name="android.permission.CALL_PHONE"/> permission in androidmanifest.xml
                if (Build.VERSION.SDK_INT >= 23) {
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PERMISSION);
                    }
                } else {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == REQUEST_CALL_PERMISSION) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);//we checked permission befor, then don't worry about the red line
                //
            }
        }
    }
}
