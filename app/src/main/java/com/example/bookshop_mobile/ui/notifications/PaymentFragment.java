package com.example.bookshop_mobile.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.text.TextWatcher;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.example.bookshop_mobile.databinding.FragmentPaymentBinding;

import java.util.regex.Pattern;

public class PaymentFragment extends Fragment {
    private FragmentPaymentBinding binding;
    private static final Pattern CARD_NUMBER_PATTERN = Pattern.compile("^\\d{4} \\d{4} \\d{4} \\d{4}$");
    private static final Pattern EXPIRY_PATTERN = Pattern.compile("^(0[1-9]|1[0-2])/\\d{2}$");
    private static final Pattern CVV_PATTERN = Pattern.compile("^\\d{3}$");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPaymentBinding.inflate(inflater, container, false);

        setupTextWatchers();
        setupPayButton();

        return binding.getRoot();
    }

    private void setupPayButton() {
        binding.btnPay.setOnClickListener(v -> {
            if (validateFields()) {
                Toast.makeText(getContext(), "Payment successful", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(v).popBackStack();
            }
        });
    }

    private void setupTextWatchers() {
        binding.etCardNumber.addTextChangedListener(new TextWatcher() {
            private static final int TOTAL_SYMBOLS = 19;
            private static final int TOTAL_DIGITS = 16;
            private static final int BLOCK_SIZE = 4;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                if (text.length() > 0 && !text.matches("^\\d+( \\d+)*$")) {
                    String formatted = text.replaceAll("[^\\d]", "");
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < formatted.length(); i++) {
                        if (i > 0 && i % BLOCK_SIZE == 0) {
                            sb.append(" ");
                        }
                        sb.append(formatted.charAt(i));
                        if (sb.length() >= TOTAL_SYMBOLS) break;
                    }
                    s.replace(0, s.length(), sb.toString());
                }
            }
        });

        binding.etExpiryDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 2 && before == 0 && !s.toString().contains("/")) {
                    binding.etExpiryDate.setText(s + "/");
                    binding.etExpiryDate.setSelection(3);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private boolean validateFields() {
        String cardNumber = binding.etCardNumber.getText().toString().trim();
        String expiryDate = binding.etExpiryDate.getText().toString().trim();
        String cvv = binding.etCvv.getText().toString().trim();

        if (!CARD_NUMBER_PATTERN.matcher(cardNumber).matches()) {
            binding.etCardNumber.setError("Enter 16 digits");
            return false;
        }

        if (!EXPIRY_PATTERN.matcher(expiryDate).matches()) {
            binding.etExpiryDate.setError("Format MM/YY");
            return false;
        }

        if (!CVV_PATTERN.matcher(cvv).matches()) {
            binding.etCvv.setError("3 digits on the back of the card");
            return false;
        }

        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}