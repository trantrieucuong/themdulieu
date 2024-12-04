package org.example.sd_94vs1.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.example.sd_94vs1.entity.Blog;
import org.example.sd_94vs1.entity.ShoppingCart;
import org.example.sd_94vs1.entity.ShoppingCartProducts;
import org.example.sd_94vs1.entity.User;
import org.example.sd_94vs1.entity.oder.Order;
import org.example.sd_94vs1.entity.product.*;
import org.example.sd_94vs1.repository.Product.DetailedProductRepository;
import org.example.sd_94vs1.repository.Product.ProductRepository;
import org.example.sd_94vs1.repository.ShoppingCartProductsRepository;
import org.example.sd_94vs1.repository.UserRepository;
import org.example.sd_94vs1.service.BlogService;
import org.example.sd_94vs1.service.DetailedProductService;
import org.example.sd_94vs1.service.ShoppingCartProductsService;
import org.example.sd_94vs1.service.ShoppingCartService;
import org.example.sd_94vs1.service.oder.OrderService;
import org.example.sd_94vs1.service.product.CategoryService;
import org.example.sd_94vs1.service.product.ManufacturerService;
import org.example.sd_94vs1.service.product.ProductService;
import org.example.sd_94vs1.service.product.ProductTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class WebController {

    private final ProductService productService;
    private final ProductTypeService productTypeService;
    private final CategoryService categoryService;
    private final ManufacturerService manufacturerService;
    private final HttpSession session;
    private final BlogService blogService;
    private final ProductRepository productRepository;
    private final DetailedProductRepository detailedProductRepository;
    private final ShoppingCartService shoppingCartService;
    private final ShoppingCartProductsService shoppingCartProductsService;
    private final DetailedProductService detailedProductService;
    private final ShoppingCartProductsRepository shoppingCartProductsRepository;
    private final UserRepository userRepository;
    private final OrderService orderService;

    @GetMapping("/huong-dan-mua-hang")
    public String gethdmuahang() {
        return "web/huong-dan-mua-hang";
    }


    @GetMapping("/")
    public String getHomePage(Model model) {
//        Phân danh mục cớ bản không theo enum
        List<Product> productsmini = productService.findProductsByCodeAndType("mn","bn001");
        List<Product> productsmavic = productService.findProductsByCodeAndType("mv","bn001");
        List<Product> productsfpv = productService.findProductsByCodeAndType("fpv","bn005");
        List<Product>productsins= productService.findProductsByCodeAndType("ins","bn001");
        List<Product>productsmar= productService.findProductsByCodeAndType("mar","bn001");
        List<Product>productsmas= productService.findProductsByCodeAndType("mas","bn001");
        List<Product>productmic= productService.findProductsByCodeAndType("mic","bn005");
        List<Product>productsom= productService.findProductsByCodeAndType("om","bn005");
        List<Product>productsomm= productService.findProductsByCodeAndType("omm","bn005");
        List<Product>productop= productService.findProductsByCodeAndType("op","bn005");
        List<Product>productpt= productService.findProductsByCodeAndType("pt","bn001");
        List<Product>productrn= productService.findProductsByCodeAndType("rn","bn005");

        List<ProductType> productTypes = productTypeService.findAll();
        List<Category> categories = categoryService.findAll();
        List<Manufacturer>manufacturers=manufacturerService.getAll();
        // Lấy 4 bài viết mới nhất
        List<Blog> latestBlogs = blogService.getLatestBlogs(4);

        model.addAttribute("productsmini", productsmini);
        System.out.println(productsmini);
        model.addAttribute("productsmavic", productsmavic);
        model.addAttribute("productsfpv", productsfpv);
        model.addAttribute("productsins", productsins);
        model.addAttribute("productsmar", productsmar);
        model.addAttribute("productsmas", productsmas);
        model.addAttribute("productmic", productmic);
        model.addAttribute("productsom", productsom);
        model.addAttribute("productsomm", productsomm);
        model.addAttribute("productop", productop);
        model.addAttribute("productpt", productpt);
        model.addAttribute("productrn", productrn);
        model.addAttribute("productTypes", productTypes);
        model.addAttribute("categories", categories);
        model.addAttribute("manufacturers", manufacturers);
        model.addAttribute("latestBlogs", latestBlogs);
        return "web/index";
    }

    //    detail
    @GetMapping("/products/{productCode}")
    public String getDetailedProduct(@PathVariable("productCode") String productCode, Model model) {
        // Tìm sản phẩm theo productCode
        Optional<Product> productOpt = productRepository.findByProductCode(productCode);

        if (productOpt.isPresent()) {
            // Tìm chi tiết sản phẩm theo mã sản phẩm
            Optional<DetailedProduct> detailedProductOpt = detailedProductRepository.findByProduct(productOpt.get());

            if (detailedProductOpt.isPresent()) {
                model.addAttribute("detailedProduct", detailedProductOpt.get());
                model.addAttribute("product", productOpt.get());
                return "web/detailproduct"; // Trả về view chi tiết sản phẩm
            } else {
                model.addAttribute("message", "Không tìm thấy chi tiết sản phẩm.");
            }
        } else {
            model.addAttribute("message", "Sản phẩm không tồn tại.");
        }
        return "error"; // Trả về trang lỗi nếu không tìm thấy sản phẩm hoặc chi tiết sản phẩm
    }
    @GetMapping("/dang-ky")
    public String getDangKyPage() {
        User user = (User) session.getAttribute("currentUser"); // Lấy thông tin người dùng trong session
        if (user != null) { // Nếu đăng nhập thì chuyển hướng về trang chủ
            return "redirect:/";
        }
        return "web/dang-ky"; // Nếu chưa đăng nhập thì hiển thị trang đăng ký
    }

    @GetMapping("/dang-nhap")
    public String getDangNhapPage() {
        User user = (User) session.getAttribute("currentUser"); // Lấy thông tin người dùng trong session
        if (user != null) { // Nếu đăng nhập thì chuyển hướng về trang chủ
            return "redirect:/";
        }
        return "web/dang-nhap"; // Nếu chưa đăng nhập thì hiển thị trang đăng nhập
    }
    @PostMapping("/dang-nhap")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        // Xử lý đăng nhập (giả định là bạn đã kiểm tra thông tin đăng nhập ở đây)

        HttpSession session = request.getSession();
        // Lấy URL được lưu trữ trong session trước khi bị chuyển hướng đến trang đăng nhập
        String redirectUrl = (String) session.getAttribute("redirectAfterLogin");
        String refererUrl = request.getHeader("Referer");

        // Xóa URL lưu trữ sau khi sử dụng để tránh redirect không mong muốn trong lần đăng nhập sau
        session.removeAttribute("redirectAfterLogin");

        // Nếu có redirectUrl trong session, dùng redirectUrl; nếu không, kiểm tra refererUrl
        if (redirectUrl != null) {
            return "redirect:" + redirectUrl;
        } else if (refererUrl != null && !refererUrl.contains("/dang-nhap")) {
            return "redirect:" + refererUrl;
        } else {
            return "redirect:/"; // Trang chủ nếu không có URL nào khác
        }

    }







    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam("productCode") String productCode,
                            @RequestParam("amount") int amount,
                            RedirectAttributes redirectAttributes,
                            HttpServletRequest request) {

        // Lấy người dùng từ session
        User user = (User) request.getSession().getAttribute("currentUser");

        // Kiểm tra nếu người dùng chưa đăng nhập, hiển thị thông báo
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Vui lòng đăng nhập để thêm sản phẩm vào giỏ hàng");
            return "redirect:/products";  // Quay lại trang sản phẩm với thông báo lỗi
        }

        // Tìm sản phẩm theo productCode
        Product product = productService.findByProductCode(productCode);
        if (product == null) {
            redirectAttributes.addFlashAttribute("error", "Sản phẩm không tồn tại");
            return "redirect:/products";
        }

        // Kiểm tra nếu giỏ hàng hiện tại của người dùng có trạng thái `status` là `false` (chưa thanh toán)
        ShoppingCart shoppingCart = shoppingCartService.findOrCreateShoppingCart(user);

        // Nếu không có giỏ hàng chưa thanh toán, tạo mới giỏ hàng
        if (shoppingCart == null) {
            shoppingCart = shoppingCartService.createShoppingCart(user);
        }

        // Thêm sản phẩm vào giỏ hàng
        shoppingCartProductsService.addProductToCart(user, product, amount);

        // Cập nhật thông báo thành công
        redirectAttributes.addFlashAttribute("message", "Sản phẩm đã được thêm vào giỏ hàng");
        return "redirect:/own-shoppingcart";  // Chuyển đến trang giỏ hàng của người dùng
    }





    // Xem giỏ hàng của người dùng

    @GetMapping("/own-shoppingcart")
    public String getOwnPage(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        // Kiểm tra người dùng đã đăng nhập chưa
        User user = (User) request.getSession().getAttribute("currentUser");

        if (user == null) {
            // Nếu chưa đăng nhập, chuyển hướng đến trang đăng nhập và thông báo lỗi
            redirectAttributes.addFlashAttribute("error", "Vui lòng đăng nhập để truy cập giỏ hàng");
            return "redirect:/dang-nhap";
        }

        // Lấy tất cả giỏ hàng của người dùng
        List<ShoppingCart> shoppingCarts = shoppingCartService.getAllShoppingCartOfCurrentUser();
        List<ShoppingCartProducts> allShoppingCartProducts = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        // Duyệt qua tất cả giỏ hàng và tính tổng giá trị
        for (ShoppingCart cart : shoppingCarts) {
            List<ShoppingCartProducts> shoppingCartProducts = shoppingCartProductsService.getShoppingCartProductsByCode(cart.getShoppingCartCode());

            for (ShoppingCartProducts shoppingCartProduct : shoppingCartProducts) {
                BigDecimal productPrice = detailedProductService.getProductPriceByCode(shoppingCartProduct.getProduct().getProductCode());

                if (productPrice == null) {
                    productPrice = BigDecimal.ZERO;
                }

                shoppingCartProduct.setPrice(productPrice);
                BigDecimal productTotalPrice = productPrice.multiply(BigDecimal.valueOf(shoppingCartProduct.getAmount()));
                totalAmount = totalAmount.add(productTotalPrice);
            }
            allShoppingCartProducts.addAll(shoppingCartProducts);
        }

        // Thêm giỏ hàng và thông tin tổng vào model
        model.addAttribute("shoppingCarts", shoppingCarts);
        model.addAttribute("shoppingCartProducts", allShoppingCartProducts);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("user", user);

        return "web/shoppingcart";
    }

    // hd oder
    @PostMapping("/add-oder")
    public ResponseEntity<Order> addOrder(@RequestBody Order orderRequest) {


        // Gọi service để thêm mới order
        Order savedOrder = orderService.addOrder(orderRequest);

        // Trả về ResponseEntity chứa đối tượng Order đã lưu
        return ResponseEntity.ok(savedOrder);
    }

//    @PostMapping("/add-oder")
//    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest orderRequest) {
//        Order order = orderService.createOrder(orderRequest);
//        return ResponseEntity.ok(order);
//    }


}
