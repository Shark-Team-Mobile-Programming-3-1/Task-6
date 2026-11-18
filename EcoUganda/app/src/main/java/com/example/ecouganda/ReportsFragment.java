package com.example.ecouganda;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Objects;

public class ReportsFragment extends Fragment {

    public ReportsFragment() {
        super(R.layout.fragment_reports);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final String reportId = (getArguments() != null) ? getArguments().getString("reportId", "none") : "none";

        TextView banner = view.findViewById(R.id.report_banner);
        if (banner != null) {
            if (Objects.equals(reportId, "none")) {
                banner.setVisibility(View.GONE);
            } else {
                banner.setVisibility(View.VISIBLE);
                banner.setText(getString(R.string.report_banner_info, reportId));
            }
        }

        ListView listView = view.findViewById(R.id.districts_list);
        if (listView != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    requireContext(), android.R.layout.simple_list_item_1, SampleData.DISTRICTS);
            listView.setAdapter(adapter);
        }
    }
}
