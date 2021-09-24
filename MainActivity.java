package com.example.shareapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mName;
    private Button mSelect,mSend;
    private DataInfo mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mName = findViewById(R.id.name);
        mSelect = findViewById(R.id.select);
        mSend = findViewById(R.id.send);

        ActivityResultLauncher<Intent> pickFile = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result == null || result.getData() == null) return;
                        mData = new DataInfo(result.getData().getData(),MainActivity.this);
                        updateName();
                    }
                });

        mSelect.setOnClickListener(view -> {
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.setType("*/*");
            pickFile.launch(i);
        });

        mSend.setOnClickListener(view -> {
            if(mData == null) return;

            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType(mData.getType());
            i.putExtra(Intent.EXTRA_STREAM,mData.getUri());

            startActivity(Intent.createChooser(i,"Share File via"));
        });
    }

    private void updateName() {
        mName.setText(mData.getName());
    }

}