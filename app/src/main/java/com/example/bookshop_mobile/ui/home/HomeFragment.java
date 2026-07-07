package com.example.bookshop_mobile.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import com.example.bookshop_mobile.databinding.FragmentHomeBinding;
import com.example.bookshop_mobile.adapter.ProductAdapter;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;
    private ProductAdapter productAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        binding.searchField.clearFocus();

        binding.searchField.setOnClickListener(v -> {
            binding.searchField.setFocusable(true);
            binding.searchField.setFocusableInTouchMode(true);
            binding.searchField.requestFocus();
            InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(binding.searchField, InputMethodManager.SHOW_IMPLICIT);
        });

        setupRecyclerView();
        setupSearch();

        return binding.getRoot();
    }

    private void setupRecyclerView() {
        productAdapter = new ProductAdapter();
        binding.productsGrid.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.productsGrid.setAdapter(productAdapter);

        homeViewModel.getProducts().observe(getViewLifecycleOwner(), products -> {
            productAdapter.submitList(products);
        });
    }

    private void setupSearch() {
        binding.searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                homeViewModel.filterProducts(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}