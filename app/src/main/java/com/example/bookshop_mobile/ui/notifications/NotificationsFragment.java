package com.example.bookshop_mobile.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.bookshop_mobile.R;
import com.example.bookshop_mobile.databinding.FragmentNotificationsBinding;
import com.example.bookshop_mobile.adapter.CartAdapter;
import com.example.bookshop_mobile.model.Product;
import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {
    private FragmentNotificationsBinding binding;
    private CartAdapter cartAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);

        binding.btnCheckout.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
            navController.navigate(R.id.action_navigation_notifications_to_paymentFragment);
        });

        setupCartRecyclerView();

        return binding.getRoot();
    }

    private void setupCartRecyclerView() {
        cartAdapter = new CartAdapter(getCartItems(), position -> {
            cartAdapter.removeItem(position);
            updateTotalPrice();
        });

        binding.cartItems.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.cartItems.setAdapter(cartAdapter);
        updateTotalPrice();
    }

    private List<Product> getCartItems() {
        List<Product> cartItems = new ArrayList<>();
        cartItems.add(new Product(1, "Your Son and Brother - Vasily", "Prose", 309, R.drawable.image11));
        cartItems.add(new Product(2, "The Day of the Oprichnik - Vladimir", "Dystopia", 450, R.drawable.image12));
        cartItems.add(new Product(3, "Bel Ami - Guy de Maupassant", "Novel", 520, R.drawable.image13));
        cartItems.add(new Product(4, "Rosemary's Baby - Ira Levin", "Thriller", 520, R.drawable.image14));
        cartItems.add(new Product(5, "Sankya - Zakhar Prilepin", "Prose", 450, R.drawable.image15));

        return cartItems;
    }

    private void updateTotalPrice() {
        double total = 0;
        for (Product item : cartAdapter.getItems()) {
            total += item.getPrice();
        }
        binding.totalPrice.setText(String.format("Total: %.2f ₽", total));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}