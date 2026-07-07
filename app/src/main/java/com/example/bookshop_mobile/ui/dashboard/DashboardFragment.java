package com.example.bookshop_mobile.ui.dashboard;

import androidx.core.content.ContextCompat;
import android.os.Bundle;
import android.view.LayoutInflater;
import com.google.android.material.button.MaterialButton;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.bookshop_mobile.databinding.FragmentDashboardBinding;
import androidx.recyclerview.widget.GridLayoutManager;
import com.example.bookshop_mobile.R;
import com.example.bookshop_mobile.adapter.ProductAdapter;
import java.util.Arrays;
import java.util.List;

public class DashboardFragment extends Fragment {
    private FragmentDashboardBinding binding;
    private DashboardViewModel dashboardViewModel;
    private ProductAdapter productAdapter;
    private List<String> genres;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        binding = FragmentDashboardBinding.inflate(inflater, container, false);

        setupGenres();
        setupRecyclerView();

        return binding.getRoot();
    }

    private void setupGenres() {
        genres = Arrays.asList(
                "Prose",
                "Novel",
                "Dystopia",
                "Thriller"
        );

        for (String genre : genres) {
            MaterialButton button;
            button = new MaterialButton(getContext());
            button.setText(genre);

            button.setWidth(950);
            button.setStrokeColor(ContextCompat.getColorStateList(getContext(), R.color.additionalBG));
            button.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.screenColor));
            button.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), android.R.color.transparent));
            button.setCornerRadius(16);
            button.setStrokeColorResource(R.color.additionalBG);
            button.setText(genre);
            button.setOnClickListener(v -> {
                dashboardViewModel.filterByGenre(genre);
            });
            binding.genreContainer.addView(button);
        }
    }

    private void setupRecyclerView() {
        productAdapter = new ProductAdapter();
        binding.productsGrid.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.productsGrid.setAdapter(productAdapter);

        dashboardViewModel.getProducts().observe(getViewLifecycleOwner(), products -> {
            productAdapter.submitList(products);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}