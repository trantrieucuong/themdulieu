package org.example.sd_94vs1.controller;

import org.example.sd_94vs1.entity.product.Category;
import org.example.sd_94vs1.entity.product.Manufacturer;
import org.example.sd_94vs1.entity.product.Product;
import org.example.sd_94vs1.repository.Product.CategoryRepository;
import org.example.sd_94vs1.repository.Product.ManufacturerRepository;
import org.example.sd_94vs1.repository.Product.ProductRepository;
import org.example.sd_94vs1.repository.Product.ProductTypeRepository;
import org.example.sd_94vs1.service.product.CategoryService;
import org.example.sd_94vs1.service.product.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class QLSanPhamController {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ManufacturerRepository manufacturerRepository;
    @Autowired
    ProductTypeRepository productTypeRepository;
    @Autowired
    private CategoryService categoryService;  // Inject CategoryService
    @Autowired
    private ManufacturerService manufacturerService;

    // Lấy danh sách sản phẩm và hiển thị
    @GetMapping("/product")
    public String getProductPage(Model model) {
        // Lấy danh sách sản phẩm từ cơ sở dữ liệu
        List<Product> products = productRepository.findAll();
        // Thêm danh sách sản phẩm vào model với tên "products"
        model.addAttribute("manufacturers", manufacturerService.getAllManufacturers());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("products", products);
        model.addAttribute("product", new Product()); // Thêm đối tượng product cho form thêm mới
        return "admin/user/product"; // Trả về đường dẫn của trang HTML để hiển thị danh sách sản phẩm
    }

    @PostMapping("/add-product")
    public String addProduct(@RequestParam String productCode,
                             @RequestParam String name,
                             @RequestParam String description,
                             @RequestParam String categoryCode,
                             @RequestParam String manufacturerCode,
                             @RequestParam String status,
                             @RequestParam("image") MultipartFile image,
                             RedirectAttributes redirectAttributes) {

        // Kiểm tra và lưu sản phẩm vào cơ sở dữ liệu
        Optional<Category> category = categoryService.getCategoryByCode(categoryCode);
        Optional<Manufacturer> manufacturer = manufacturerService.getManufacturerByCode(manufacturerCode);

        if (category.isEmpty() || manufacturer.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Danh mục hoặc nhà sản xuất không hợp lệ!");
            return "redirect:/admin/add-product";  // Quay lại trang thêm sản phẩm nếu có lỗi
        }

        Product product = new Product();
        product.setProductCode(productCode);
        product.setName(name);
        product.setDescription(description);
        product.setCategory(category.get());
        product.setManufacturer(manufacturer.get());
        product.setStatus(status);

        // Xử lý file upload
        if (!image.isEmpty()) {
            String fileName = image.getOriginalFilename();
            product.setImage(fileName);  // Lưu tên file hoặc xử lý như bạn cần
        }

        // Lưu sản phẩm vào cơ sở dữ liệu
        productRepository.save(product);

        redirectAttributes.addFlashAttribute("success", "Thêm sản phẩm thành công!");
        return "redirect:/admin/product";  // Quay lại trang danh sách sản phẩm
    }


    @PostMapping("/delete-product/{productCode}")
    public String deleteProduct(@PathVariable String productCode, RedirectAttributes redirectAttributes) {
        // Tìm sản phẩm theo mã sản phẩm
        Optional<Product> productOptional = productRepository.findByProductCode(productCode);

        if (productOptional.isPresent()) {
            // Xóa sản phẩm
            productRepository.delete(productOptional.get());
            redirectAttributes.addFlashAttribute("success", "Xóa sản phẩm thành công!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Sản phẩm không tồn tại!");
        }

        return "redirect:/admin/product";  // Quay lại trang sản phẩm
    }


    @PostMapping("/update-product")
    public String updateProduct(
            @RequestParam String productCode,
            @RequestParam String name,
            @RequestParam String manufacturerCode,
            @RequestParam String categoryCode,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) MultipartFile image,
            RedirectAttributes redirectAttributes) {

        // Tìm sản phẩm trong CSDL
        Product product = productRepository.findById(productCode).orElseThrow(() ->
                new IllegalArgumentException("Sản phẩm không tồn tại!")
        );

        // Cập nhật thông tin sản phẩm
        product.setName(name);
        product.setDescription(description);

        // Cập nhật nhà sản xuất và danh mục
        Manufacturer manufacturer = manufacturerRepository.findById(manufacturerCode)
                .orElseThrow(() -> new IllegalArgumentException("Nhà sản xuất không tồn tại!"));
        product.setManufacturer(manufacturer);

        Category category = categoryRepository.findById(categoryCode)
                .orElseThrow(() -> new IllegalArgumentException("Danh mục không tồn tại!"));
        product.setCategory(category);


        product.setEditDate(new Date()); // Cập nhật ngày chỉnh sửa

        // Lưu vào CSDL
        productRepository.save(product);

        // Gửi thông báo thành công
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật sản phẩm thành công!");

        return "redirect:/admin/product";
    }



}




