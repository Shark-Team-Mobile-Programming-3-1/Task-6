package com.example.ecouganda;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;      // null on tablets (drawer is always open there)
    private NavigationView navigationView;
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;

    // true only when the activity is created fresh (not after rotation)
    private boolean openDrawerForDeepLink = false;

    // asks the user for permission to show notifications
    private final ActivityResultLauncher<String> permissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(),
                    isGranted -> {
                        // Permission result callback
                    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Android picks layout/activity_main.xml (phone) or layout-w600dp/activity_main.xml (tablet)
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);   // null on tablets
        navigationView = findViewById(R.id.nav_view);
        MaterialToolbar toolbar = findViewById(R.id.toolbar);

        // savedInstanceState is null only on a fresh start, not after rotation.
        openDrawerForDeepLink = (savedInstanceState == null);

        // 1. Get the NavController from the NavHostFragment
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (fragment instanceof NavHostFragment) {
            NavHostFragment navHostFragment = (NavHostFragment) fragment;
            navController = navHostFragment.getNavController();

            // 2. The five drawer screens are top-level (they show a hamburger, not a back arrow)
            appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.dashboardFragment,
                    R.id.projectsFragment,
                    R.id.schoolsFragment,
                    R.id.reportsFragment,
                    R.id.settingsFragment)
                    .setOpenableLayout(drawerLayout)
                    .build();

            // 3. Connect the toolbar and the drawer to the NavController.
            if (toolbar != null) {
                NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
            }
            if (navigationView != null) {
                NavigationUI.setupWithNavController(navigationView, navController);
            }

            // 4. Extra rules when the destination changes
            navController.addOnDestinationChangedListener((controller, destination, arguments) -> {

                // A project detail screen is not in the drawer, so keep "Projects" highlighted
                if (destination.getId() == R.id.projectDetailFragment && navigationView != null) {
                    navigationView.setCheckedItem(R.id.projectsFragment);
                }

                // Opened from a notification: open the drawer (phones only)
                if (destination.getId() == R.id.reportsFragment && openDrawerForDeepLink
                        && drawerLayout != null && arguments != null) {
                    String reportId = arguments.getString("reportId", "none");
                    if (!Objects.equals(reportId, "none")) {
                        drawerLayout.openDrawer(GravityCompat.START);
                        openDrawerForDeepLink = false;
                    }
                }
            });
        }

        askNotificationPermission();
    }

    // Android 13 and above need permission before showing notifications
    private void askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            int permission = ContextCompat.checkSelfPermission(
                    this, Manifest.permission.POST_NOTIFICATIONS);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }
    }

    // Back arrow in the toolbar (used on the project detail screen)
    @Override
    public boolean onSupportNavigateUp() {
        return (navController != null && NavigationUI.navigateUp(navController, appBarConfiguration))
                || super.onSupportNavigateUp();
    }
}
