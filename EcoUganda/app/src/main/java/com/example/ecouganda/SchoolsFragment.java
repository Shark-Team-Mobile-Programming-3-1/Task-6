package com.example.ecouganda;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SchoolsFragment extends Fragment {

    public SchoolsFragment() {
        super(R.layout.fragment_schools);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listView = view.findViewById(R.id.schools_list);
        if (listView != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    requireContext(), android.R.layout.simple_list_item_1, SampleData.SCHOOLS);
            listView.setAdapter(adapter);
        }
    }
}
