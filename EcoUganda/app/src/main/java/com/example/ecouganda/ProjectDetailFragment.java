package com.example.ecouganda;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProjectDetailFragment extends Fragment {

    public ProjectDetailFragment() {
        super(R.layout.fragment_project_detail);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView titleText = view.findViewById(R.id.detail_title);
        TextView infoText = view.findViewById(R.id.detail_info);

        final int projectId = (getArguments() != null) ? getArguments().getInt("projectId", 0) : 0;

        if (titleText != null) {
            if (projectId >= 1 && projectId <= SampleData.PROJECTS.length) {
                titleText.setText(SampleData.PROJECTS[projectId - 1]);
            } else {
                titleText.setText(R.string.unknown_project);
            }
        }

        if (infoText != null) {
            infoText.setText(getString(R.string.project_id_info, projectId));
        }
    }
}
