package com.example.ecouganda;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DashboardFragment extends Fragment {

    public DashboardFragment() {
        super(R.layout.fragment_dashboard);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button alertButton = view.findViewById(R.id.btn_test_alert);
        if (alertButton != null) {
            alertButton.setOnClickListener(v -> {
                // Send an urgent alert for report 205
                AlertHelper.sendAlert(requireContext(), "205", getString(R.string.alert_message_sample));
                Toast.makeText(requireContext(), R.string.toast_alert_sent,
                        Toast.LENGTH_SHORT).show();
            });
        }
    }
}
