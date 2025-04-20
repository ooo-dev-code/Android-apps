package com.user.myapplication;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.user.myapplication.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.button.getText().toString().equals("Turn on")) {
                    binding.button.setText(R.string.turnoff);
                    binding.flashImage.setImageResource(R.drawable.on);
                    changeLightState(true);
                } else {
                    binding.button.setText(R.string.turnon);
                    binding.flashImage.setImageResource(R.drawable.off);
                    changeLightState(false);
                }
            }
        });
    }

    private void changeLightState(boolean state) {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            CameraManager cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
            String camId = null;

            try {
                camId = cameraManager.getCameraIdList()[0];
                cameraManager.setTorchMode(camId, state);
            } catch (CameraAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.button.setText(R.string.turnon);
    }
}
