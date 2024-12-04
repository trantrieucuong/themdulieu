package org.example.sd_94vs1.controller;

import org.example.sd_94vs1.entity.product.Category;
import org.example.sd_94vs1.repository.Product.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class CategoryCodeController {
    @Autowired
    CategoryRepository categoryRepository;

    // Lấy danh sách danh mục và hiển thị
    @GetMapping("/category")
    public String getCategoryPage(Model model) {
        // Lấy danh sách danh mục từ cơ sở dữ liệu
        List<Category> categories = categoryRepository.findAll();
        // Thêm danh sách danh mục vào model với tên "categories"
        model.addAttribute("categories", categories);
        model.addAttribute("category", new Category()); // Thêm đối tượng category cho form thêm mới
        return "admin/user/category"; // Trả về đường dẫn của trang HTML để hiển thị danh sách danh mục
    }


    @PostMapping("/add-category")
    public String themCategory(@RequestParam String categoryCode,
                               @RequestParam String name,
                               @RequestParam(required = false) String img,
                               @RequestParam(required = false) String status,
                               RedirectAttributes redirectAttributes) {
        // Kiểm tra lỗi
        if (categoryCode == null || categoryCode.isBlank()) {
            redirectAttributes.addFlashAttribute("error", "Mã danh mục không được để trống!");
            return "redirect:/admin/add-category"; // Quay lại trang thêm danh mục
        }
        if (name == null || name.isBlank()) {
            redirectAttributes.addFlashAttribute("error", "Tên danh mục không được để trống!");
            return "redirect:/admin/add-category"; // Quay lại trang thêm danh mục
        }

        // Tạo đối tượng Category
        Category category = Category.builder()
                .categoryCode(categoryCode)
                .name(name)
                .img(img)
                .status(status != null ? status : "ACTIVE") // Mặc định trạng thái là ACTIVE
                .date(new Date()) // Ngày tạo hiện tại
                .editDate(new Date()) // Ngày sửa hiện tại
                .build();

        // Thêm danh mục vào cơ sở dữ liệu
        categoryRepository.save(category);

        // Thông báo thành công
        redirectAttributes.addFlashAttribute("successMessage", "Thêm danh mục thành công!");
        return "redirect:/admin/category"; // Chuyển hướng về trang danh sách danh mục
    }


    // Global exception handler
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "errorPage"; // Trang lỗi bạn tạo
    }


    // Delete category by ID
    @PostMapping("/delete-category/{categoryCode}")
    public String deleteCategory(@PathVariable String categoryCode, RedirectAttributes redirectAttributes) {
        try {
            categoryRepository.deleteById(categoryCode);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa danh mục thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi xóa danh mục!");
        }
        return "redirect:/admin/category"; // Redirect back to the category list page
    }


    // Form cập nhật danh mục (get)
    @GetMapping("/update-category/{categoryCode}")
    public String showUpdateCategoryForm(@PathVariable("categoryCode") String categoryCode, Model model) {
        Category category = categoryRepository.findById(categoryCode).orElse(null);
        if (category != null) {
            model.addAttribute("category", category);
            return "category/update";
        }
        model.addAttribute("errorMessage", "Danh mục không tồn tại!");
        return "redirect:/admin/category";
    }

    // Xử lý cập nhật danh mục (post)
    @PostMapping("/update-category")
    public String updateCategory(@RequestParam String categoryCode,
                                 @RequestParam String name,
                                 @RequestParam(required = false) String img,
                                 @RequestParam(required = false) String status,
                                 RedirectAttributes redirectAttributes) {

        Category category = categoryRepository.findById(categoryCode).orElse(null);

        if (category == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Danh mục không tồn tại!");
            return "redirect:/admin/category";
        }

        // Cập nhật thông tin danh mục
        category.setName(name);
        category.setImg(img != null ? img : category.getImg()); // Giữ lại ảnh cũ nếu không có ảnh mới
        category.setStatus(status != null ? status : category.getStatus());
        category.setEditDate(new Date()); // Cập nhật ngày chỉnh sửa

        categoryRepository.save(category);

        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật danh mục thành công!");
        return "redirect:/admin/category";
    }

}
