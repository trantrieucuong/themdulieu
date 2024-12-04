package org.example.sd_94vs1.service;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.example.sd_94vs1.entity.ShoppingCart;
import org.example.sd_94vs1.entity.ShoppingCartProducts;
import org.example.sd_94vs1.entity.User;
import org.example.sd_94vs1.entity.product.DetailedProduct;
import org.example.sd_94vs1.repository.Product.DetailedProductRepository;
import org.example.sd_94vs1.repository.Product.ProductRepository;
import org.example.sd_94vs1.repository.ShoppingCartProductsRepository;
import org.example.sd_94vs1.repository.ShoppingCartRepository;
import org.example.sd_94vs1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private HttpSession session;
    @Autowired
    private DetailedProductRepository detailedProductRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ShoppingCartProductsRepository shoppingCartProductsRepository;
    // Hàm tạo shoppingCartCode ngẫu nhiên

    // Giả sử phương thức tạo mã giỏ hàng
//    private String generateShoppingCartCode() {
//        // Sinh mã giỏ hàng theo yêu cầu (có thể dùng UUID hoặc logic khác)
//        return "sc" + (int) (Math.random() * 10000);
//    }
//    public ShoppingCart createShoppingCart(Boolean status) {
//        // Lấy user hiện tại từ session
//        User user = (User) session.getAttribute("currentUser");
//
//        // Tạo mới ShoppingCart
//        ShoppingCart shoppingCart = ShoppingCart.builder()
//                .shoppingCartCode(generateShoppingCartCode())
//                .user(user)
//                .status(status)
//                .build();
//
//        return shoppingCartRepository.save(shoppingCart);
//    }
    public List<ShoppingCart> getAllShoppingCartOfCurrentUser() {
        User user = (User) session.getAttribute("currentUser");
        return shoppingCartRepository.findByUser_UserCode(user.getUserCode(), Sort.by("createdAt").descending());
    }
    public List<ShoppingCartProducts> getProductsByCart(String shoppingCartCode) {
        // Fetch the products for the shopping cart
        List<ShoppingCartProducts> products = shoppingCartRepository.findProductsByShoppingCartCode(shoppingCartCode);

        // Fetch priceVND from DetailedProduct for each product in the shopping cart
        for (ShoppingCartProducts product : products) {
            DetailedProduct detailedProduct = detailedProductRepository.findByProduct_ProductCode(product.getProduct().getProductCode());
            if (detailedProduct != null) {
//                product.getProduct().setPriceVND(detailedProduct.getPriceVND());
            }
        }

        return products;
    }
// tim user
    public ShoppingCart findByUser(User user) {
        Optional<ShoppingCart> shoppingCart = shoppingCartRepository.findByUser(user);
        return shoppingCart.orElse(null);  // Trả về null nếu không tìm thấy giỏ hàng
    }

    // Phương thức cập nhật trạng thái giỏ hàng
    @Transactional
    public void updateShoppingCartStatus(String shoppingCartCode, boolean status) {
        Optional<ShoppingCart> shoppingCart = shoppingCartRepository.findByShoppingCartCode(shoppingCartCode);
        if (shoppingCart.isPresent()) {
            ShoppingCart cart = shoppingCart.get();
            cart.setStatus(status);  // Cập nhật trạng thái
            shoppingCartRepository.save(cart);  // Lưu giỏ hàng đã cập nhật
            System.out.println("Trạng thái giỏ hàng đã được cập nhật: " + shoppingCartCode);
        } else {
            // Giỏ hàng không tồn tại
            System.out.println("Giỏ hàng không tồn tại: " + shoppingCartCode);
        }
    }






    public List<ShoppingCart> getAllShoppingCartsOfCurrentUser() {
        User user = (User) session.getAttribute("currentUser");
        return shoppingCartRepository.findByUser_UserCode(user.getUserCode(), Sort.by("createdAt").descending());
    }

    public ShoppingCart createShoppingCart(User user) {
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .shoppingCartCode(generateRandomShoppingCartCode())
                .user(user)
                .status(false) // Giả sử giỏ hàng ban đầu là active
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
        return shoppingCartRepository.save(shoppingCart);
    }

    // Phương thức để tạo mã giỏ hàng ngẫu nhiên
    private String generateRandomShoppingCartCode() {
        int randomNum = (int) (Math.random() * 10000);
        return "GH" + String.format("%04d", randomNum);
    }

    public ShoppingCart findOrCreateShoppingCart(User user) {
        // Tìm giỏ hàng chưa thanh toán của người dùng
        Optional<ShoppingCart> optionalCart = shoppingCartRepository.findByUserAndStatus(user, false);

        return optionalCart.orElseGet(() -> createShoppingCart(user)); // Tạo mới nếu không có giỏ hàng chưa thanh toán
    }
}
