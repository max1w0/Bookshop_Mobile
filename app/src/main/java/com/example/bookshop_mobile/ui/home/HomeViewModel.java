package com.example.bookshop_mobile.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.bookshop_mobile.model.Product;
import com.example.bookshop_mobile.R;
import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {
    private final MutableLiveData<List<Product>> products = new MutableLiveData<>();
    private final List<Product> allProducts = new ArrayList<>();

    public HomeViewModel() {
        loadProducts();
    }

    public LiveData<List<Product>> getProducts() {
        return products;
    }

    private void loadProducts() {
        allProducts.add(new Product(1, "Sankya - Zakhar Prilepin", "Prose", 450, R.drawable.image1));
        allProducts.add(new Product(2, "Annihilate - Michel Houellebecq", "Novel", 1380, R.drawable.image2));
        allProducts.add(new Product(3, "Rosemary's Baby - Ira Levin", "Thriller", 920, R.drawable.image3));
        allProducts.add(new Product(4, "Fight Club - Chuck Palahniuk", "Thriller", 410, R.drawable.image4));
        allProducts.add(new Product(5, "A Noble Nest - Ivan Turgenev", "Novel", 670, R.drawable.image5));
        allProducts.add(new Product(6, "Loser's Diary - Eduard", "Prose", 290, R.drawable.image6));
        allProducts.add(new Product(7, "The Day of the Oprichnik - Vladimir Sorokin", "Dystopia", 450, R.drawable.image7));
        allProducts.add(new Product(8, "Shooters - Yuri Mamleev", "Prose", 380, R.drawable.image8));
        allProducts.add(new Product(9, "Bel Ami - Guy de Maupassant", "Novel", 520, R.drawable.image9));
        allProducts.add(new Product(10, "Your Son and Brother - Vasily Shukshin", "Prose", 309, R.drawable.image10));
        products.setValue(new ArrayList<>(allProducts));
    }

    public void filterProducts(String query) {
        if (query.isEmpty()) {
            products.setValue(new ArrayList<>(allProducts));
        } else {
            List<Product> filtered = new ArrayList<>();
            for (Product product : allProducts) {
                if (product.getName().toLowerCase().contains(query.toLowerCase())) {
                    filtered.add(product);
                }
            }
            products.setValue(filtered);
        }
    }
}