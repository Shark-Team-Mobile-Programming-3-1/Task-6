package com.example.ecouganda;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.Objects;

public class SettingsFragment extends Fragment {

    public SettingsFragment() {
        super(R.layout.fragment_settings);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText nameEdit = view.findViewById(R.id.edit_name);
        final RadioGroup languageGroup = view.findViewById(R.id.language_group);
        final SwitchMaterial offlineSwitch = view.findViewById(R.id.switch_offline);
        Button saveButton = view.findViewById(R.id.btn_save);

        final SharedPreferences prefs = requireContext()
                .getSharedPreferences("eco_settings", Context.MODE_PRIVATE);

        // Show the saved settings
        if (nameEdit != null) {
            nameEdit.setText(prefs.getString("name", ""));
        }

        String savedLanguage = prefs.getString("language", "English");
        if (languageGroup != null) {
            if (Objects.equals(savedLanguage, "Luganda")) {
                languageGroup.check(R.id.radio_luganda);
            } else if (Objects.equals(savedLanguage, "Runyankore")) {
                languageGroup.check(R.id.radio_runyankore);
            } else {
                languageGroup.check(R.id.radio_english);
            }
        }

        if (offlineSwitch != null) {
            offlineSwitch.setChecked(prefs.getBoolean("offline_sync", false));
        }

        // Save when the button is clicked
        if (saveButton != null) {
            saveButton.setOnClickListener(v -> {
                String language = "English";
                if (languageGroup != null) {
                    int checkedId = languageGroup.getCheckedRadioButtonId();
                    if (checkedId == R.id.radio_luganda) {
                        language = "Luganda";
                    } else if (checkedId == R.id.radio_runyankore) {
                        language = "Runyankore";
                    }
                }

                String nameText = (nameEdit != null && nameEdit.getText() != null)
                        ? nameEdit.getText().toString() : "";
                boolean isOffline = (offlineSwitch != null && offlineSwitch.isChecked());

                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("name", nameText);
                editor.putString("language", language);
                editor.putBoolean("offline_sync", isOffline);
                editor.apply();

                Toast.makeText(requireContext(), R.string.toast_settings_saved, Toast.LENGTH_SHORT).show();
            });
        }
    }
}
