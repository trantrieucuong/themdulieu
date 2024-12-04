package org.example.sd_94vs1.controller.payment;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jdk.swing.interop.SwingInterOpUtils;
import lombok.AllArgsConstructor;

import org.example.sd_94vs1.config.Config;
import org.example.sd_94vs1.entity.ShoppingCart;
import org.example.sd_94vs1.entity.User;
import org.example.sd_94vs1.repository.ShoppingCartRepository;
import org.example.sd_94vs1.repository.oder.OrderRepository;
import org.example.sd_94vs1.service.ShoppingCartProductsService;
import org.example.sd_94vs1.service.ShoppingCartService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/payment")
@AllArgsConstructor
public class PaymentController {
    //    private final CartService cartService;
    private final ShoppingCartService shoppingCartService;
    private final HttpSession session;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderRepository orderRepository;
    private final ShoppingCartProductsService shoppingCartProductsService;

    @GetMapping("/create_payment")
    public ResponseEntity<String> createPayment(@RequestParam long amount, @RequestParam String id) throws Exception {

        // Nếu đã đăng nhập, tiếp tục với logic thanh toán
        String vnp_Version = Config.vnp_Version;
        String vnp_Command = Config.vnp_Command;
        String vnp_OrderInfo = Config.getRandomNumber(8);
        String orderType = Config.vnp_OrderType;
        String vnp_TxnRef = "thanh toan don hang: " + id;
        String vnp_IpAddr = "14.248.82.236";
        String vnp_TmnCode = Config.vnp_TmnCode;
        long amount_a = amount * 100;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount_a));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        for (String fieldName : fieldNames) {
            String fieldValue = vnp_Params.get(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                hashData.append(fieldName).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII)).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (fieldNames.indexOf(fieldName) < fieldNames.size() - 1) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;

        String htmlResponse = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Redirecting...</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <p>Redirecting to payment gateway...</p>\n" +
                "    <script type=\"text/javascript\">\n" +
                "        window.location.href = \"" + paymentUrl + "\";\n" +
                "    </script>\n" +
                "</body>\n" +
                "</html>";

        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .body(htmlResponse);
    }


//    @GetMapping("/vnpay_return")
//    public ResponseEntity<Void> vnpayReturn(HttpServletRequest request) {
//        Map<String, String> vnp_Params = new HashMap<>();
//        Map<String, String[]> requestParams = request.getParameterMap();
//        for (Map.Entry<String, String[]> entry : requestParams.entrySet()) {
//            vnp_Params.put(entry.getKey(), entry.getValue()[0]);
//        }
//
//        // Get the vnp_SecureHash parameter
//        String vnp_SecureHash = vnp_Params.remove("vnp_SecureHash");
//
//        // Create hash data string
//        StringBuilder hashData = new StringBuilder();
//        vnp_Params.entrySet().stream()
//                .sorted(Map.Entry.comparingByKey())
//                .forEach(entry -> {
//                    String key = entry.getKey();
//                    String value = entry.getValue();
//                    if (value != null && !value.isEmpty()) {
//                        try {
//                            hashData.append(key).append('=').append(URLEncoder.encode(value, StandardCharsets.US_ASCII)).append('&');
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//
//        // Remove the trailing '&' if it exists
//        String hashString = hashData.toString();
//        if (hashString.endsWith("&")) {
//            hashString = hashString.substring(0, hashString.length() - 1);
//        }
//
//        // Compute the secure hash using the provided secret key
//        String computedSecureHash = Config.hmacSHA512(Config.secretKey, hashString);
//
//        HttpHeaders headers = new HttpHeaders();
//        if (computedSecureHash.equals(vnp_SecureHash)) {
//            // Payment successful
//            System.out.println("Thanh toán thành công!");
//
//            // Cập nhật trạng thái giỏ hàng thành TRUE
//            String shoppingCartCode = vnp_Params.get("vnp_TxnRef");
//            System.out.println("Thanh toán đơn hàng: " + shoppingCartCode);

//
//            headers.setLocation(URI.create("http://localhost:8080/"));
//            return new ResponseEntity<>(headers, HttpStatus.FOUND);
//        } else {
//            // Hash validation failed
//            System.out.println("Hash validation failed.");
//
//            // Redirect to homepage (localhost:8080)
//            headers.setLocation(URI.create("http://localhost:8080/"));
//            return new ResponseEntity<>(headers, HttpStatus.FOUND);
//        }
//    }

    @GetMapping("/vnpay_return")
    public ResponseEntity<Void> vnpayReturn(HttpServletRequest request) {
        Map<String, String> vnp_Params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();

        // Lấy các tham số từ request và đưa vào map
        for (Map.Entry<String, String[]> entry : requestParams.entrySet()) {
            vnp_Params.put(entry.getKey(), entry.getValue()[0]);
        }

        // Lấy vnp_SecureHash từ request và loại bỏ khỏi map
        String vnp_SecureHash = vnp_Params.remove("vnp_SecureHash");

        // Tạo chuỗi hash data từ các tham số đã sắp xếp
        StringBuilder hashData = new StringBuilder();
        vnp_Params.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    if (value != null && !value.isEmpty()) {
                        try {
                            hashData.append(key).append('=').append(URLEncoder.encode(value, StandardCharsets.US_ASCII)).append('&');
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

        // Xóa ký tự '&' cuối cùng (nếu có)
        String hashString = hashData.toString();
        if (hashString.endsWith("&")) {
            hashString = hashString.substring(0, hashString.length() - 1);
        }

        // Tính toán Secure Hash để xác minh
        String computedSecureHash = Config.hmacSHA512(Config.secretKey, hashString);

        HttpHeaders headers = new HttpHeaders();

        if (computedSecureHash.equals(vnp_SecureHash)) {
            // Lấy mã giỏ hàng từ tham số vnp_TxnRef
            String rawShoppingCartCode = vnp_Params.get("vnp_TxnRef");
            String shoppingCartCode;

            // Kiểm tra và tách mã nếu có tiền tố
            if (rawShoppingCartCode != null && rawShoppingCartCode.contains(":")) {
                shoppingCartCode = rawShoppingCartCode.split(":")[1].trim(); // Lấy phần sau dấu ":"
            } else {
                shoppingCartCode = rawShoppingCartCode; // Nếu không có tiền tố, giữ nguyên
            }

            System.out.println("Mã giỏ hàng sau khi xử lý: " + shoppingCartCode);

            // Kiểm tra mã giỏ hàng có hợp lệ không
            if (shoppingCartCode == null || shoppingCartCode.isEmpty()) {
                System.out.println("Mã giỏ hàng không hợp lệ.");
                headers.setLocation(URI.create("http://localhost:8080/"));
                return new ResponseEntity<>(headers, HttpStatus.FOUND);
            }

            // Cập nhật trạng thái giỏ hàng và đơn hàng trong database
            Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findById(shoppingCartCode);
            if (optionalShoppingCart.isPresent()) {
                ShoppingCart shoppingCart = optionalShoppingCart.get();

                // Cập nhật trạng thái giỏ hàng
                shoppingCart.setStatus(true);
                shoppingCart.setUpdatedAt(new Date());
                shoppingCartRepository.save(shoppingCart);

                System.out.println("Cập nhật trạng thái thành công cho giỏ hàng: " + shoppingCartCode);
            } else {
                System.out.println("Không tìm thấy giỏ hàng với mã: " + shoppingCartCode);
            }

            // Cập nhật trạng thái đơn hàng
            int updatedRows = orderRepository.updateOrderStatus(shoppingCartCode);
            if (updatedRows > 0) {
                System.out.println("Cập nhật trạng thái đơn hàng thành công.");
            } else {
                System.out.println("Không tìm thấy đơn hàng cần cập nhật.");
            }

            // Chuyển hướng về trang chủ
            headers.setLocation(URI.create("http://localhost:8080/"));
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        } else {
            // Trường hợp xác minh hash thất bại
            System.out.println("Hash validation failed.");
            headers.setLocation(URI.create("http://localhost:8080/"));
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        }
    }


}
