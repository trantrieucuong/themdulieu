package org.example.sd_94vs1.repository;

import org.example.sd_94vs1.entity.ShoppingCart;
import org.example.sd_94vs1.entity.ShoppingCartProducts;
import org.example.sd_94vs1.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartProductsRepository extends JpaRepository<ShoppingCartProducts, String> {
    // ShoppingCartProductsRepository.java

        List<ShoppingCartProducts> findByShoppingCart_ShoppingCartCode(String shoppingCartCode);
        @Query("SELECT scp FROM ShoppingCartProducts scp WHERE scp.shoppingCart.shoppingCartCode = :shoppingCartCode and scp.shoppingCart.status=false ")
        List<ShoppingCartProducts> findProductsByShoppingCartCode(String shoppingCartCode);
        List<ShoppingCartProducts> findByShoppingCart(ShoppingCart shoppingCart);

        Optional<ShoppingCartProducts> findByShoppingCartAndProduct(ShoppingCart shoppingCart, Product product);


}
