package com.example.ecouganda;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class ProjectsFragment extends Fragment {

    public ProjectsFragment() {
        super(R.layout.fragment_projects);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listView = view.findViewById(R.id.projects_list);
        if (listView != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    requireContext(), android.R.layout.simple_list_item_1, SampleData.PROJECTS);
            listView.setAdapter(adapter);

            final NavController navController = Navigation.findNavController(view);

            // When a project is clicked, open its details and send the projectId
            listView.setOnItemClickListener((parent, itemView, position, id) -> {
                Bundle args = new Bundle();
                args.putInt("projectId", position + 1);
                navController.navigate(R.id.action_projects_to_detail, args);
            });
        }
    }
}
