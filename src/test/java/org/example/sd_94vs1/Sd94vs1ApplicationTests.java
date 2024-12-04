package org.example.sd_94vs1;

import com.github.javafaker.Faker;
import com.github.slugify.Slugify;
import org.antlr.v4.runtime.misc.LogManager;
import org.example.sd_94vs1.entity.*;
import org.example.sd_94vs1.entity.oder.Order;
import org.example.sd_94vs1.entity.product.*;
import org.example.sd_94vs1.model.enums.UserRole;
import org.example.sd_94vs1.model.request.UpsertReviewRequest;
import org.example.sd_94vs1.repository.*;
import org.example.sd_94vs1.repository.Product.*;
import org.example.sd_94vs1.repository.oder.OrderRepository;
import org.example.sd_94vs1.service.ReviewService;
import org.example.sd_94vs1.service.product.ManufacturerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class Sd94vs1ApplicationTests {

	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ManufacturerService manufacturerService;
	@Autowired
	private ManufacturerRepository manufacturerRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private BlogRepository blogRepository;
	private final Random random = new Random();
	@Autowired
	private ProductTypeRepository productTypeRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private DetailedProductRepository detailedProductRepository;
	@Autowired
	private ShoppingCartRepository shoppingCartRepository;
	@Autowired
	private ShoppingCartProductsRepository shoppingCartProductsRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private ReviewRepository reviewRepository;
	@Autowired
	private ReviewService reviewService;

	@Test
	void contextLoads() {
	}



	@Test
	public void testManufacturerCreation() {
		// Tạo nhà cung cấp
		Manufacturer manufacturer = Manufacturer.builder()
				.manufacturerCode("ma001") // Mã nhà cung cấp
				.name("DJI")              // Tên nhà cung cấp
				.country("Trung Quốc")    // Quốc gia
				.build();

		manufacturerRepository.save(manufacturer);

	}

	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void testAddCategories() {
		// Danh sách các loại sản phẩm
		List<Category> categories = List.of(
				new Category("cat001", "DJI Mavic", new Date(), null, "active", "/img/anh1.jpg"),
				new Category("cat002", "DJI Air", new Date(), null, "active", "/img/anh2.jpg"),
				new Category("cat003", "DJI Mini", new Date(), null, "active", "/img/anh3.jpg"),
				new Category("cat004", "DJI FPV", new Date(), null, "active", "/img/anh4.jpg"),
				new Category("cat005", "Osmo Pocket", new Date(), null, "active", "/img/anh5.jpg"),
				new Category("cat006", "Osmo Action", new Date(), null, "active", "/img/anh6.jpg"),
				new Category("cat007", "Osmo Mobile", new Date(), null, "active", "/img/anh7.jpg"),
				new Category("cat008", "DJI Ronin", new Date(), null, "active", "/img/anh8.jpg"),
				new Category("cat009", "DJI Power", new Date(), null, "active", "/img/anh9.jpg"),
				new Category("cat010", "Sản phẩm khác", new Date(), null, "active", "/img/anh10.jpg")
		);

		// Lưu tất cả các loại sản phẩm vào cơ sở dữ liệu
		categoryRepository.saveAll(categories);

		// Kiểm tra xem số lượng loại sản phẩm đã lưu

	}

	private static String getCharacter(String str) {
		return str.substring(0, 1).toUpperCase();
	}

	public static String generateLinkImage(String name) {
		return "https://placehold.co/200x200?text=" + getCharacter(name);
	}


	@Test
	public void saveProductTypeData() {
		// Thêm dữ liệu cho từng ProductType
		ProductType productType1 = ProductType.builder()
				.productTypeCode("bn001")
				.productTypeName("Bản đơn")
				.description("1 nửa bộ cánh, 1 body, 1 pin, 1 remote, 3 sợi cáp")
				.build();

		ProductType productType2 = ProductType.builder()
				.productTypeCode("bn002")
				.productTypeName("Bản combo")
				.description("1 bộ cánh, 1 body, 3 pin, 1 hub sạc, 1 túi, 3 sợi cáp")
				.build();

		ProductType productType3 = ProductType.builder()
				.productTypeCode("bn003")
				.productTypeName("Bản đơn tay RC")
				.description("1 nửa bộ cánh, 1 body, 1 pin, 1 remote")
				.build();

		ProductType productType4 = ProductType.builder()
				.productTypeCode("bn004")
				.productTypeName("Bản combo tay RC")
				.description("1 bộ cánh, 1 body, 3 pin, 1 hub sạc, 1 túi")
				.build();
		ProductType productType5 = ProductType.builder()
				.productTypeCode("bn005")
				.productTypeName("Bản đơn")
				.description("full phụ kiện như hãng bán")
				.build();
		ProductType productType6 = ProductType.builder()
				.productTypeCode("bn006")
				.productTypeName("Bản combo")
				.description("full phụ kiện như hãng bán")
				.build();
		ProductType productType7 = ProductType.builder()
				.productTypeCode("bn007")
				.productTypeName("Bản đơn tay rc controler")
				.description("full phụ kiện như hãng bán")
				.build();
		ProductType productType8 = ProductType.builder()
				.productTypeCode("bn008")
				.productTypeName("Bản combo tay rc controler")
				.description("full phụ kiện như hãng bán")
				.build();
		ProductType productType9 = ProductType.builder()
				.productTypeCode("bn009")
				.productTypeName("Bản đơn tay rc pro")
				.description("full phụ kiện như hãng bán")
				.build();
		ProductType productType10 = ProductType.builder()
				.productTypeCode("bn010")
				.productTypeName("Bản combo tay rc pro")
				.description("full phụ kiện như hãng bán")
				.build();

		// Lưu vào cơ sở dữ liệu
		productTypeRepository.save(productType1);
		productTypeRepository.save(productType2);
		productTypeRepository.save(productType3);
		productTypeRepository.save(productType4);
		productTypeRepository.save(productType5);
		productTypeRepository.save(productType6);
		productTypeRepository.save(productType7);
		productTypeRepository.save(productType8);
		productTypeRepository.save(productType9);
		productTypeRepository.save(productType10);


		// Xác minh dữ liệu đã lưu

	}
////product
//// trc khi chạy cần phải vào product tạm xóa biến lưu tạm productPrice
@Test
@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
public void mavic3cinebandontaythuong() {
	List<Product> products = List.of(
			// Mavic 3 - Bản đơn
			new Product("mv0001", "Mavic 3 cine", "Flycam DJI Mavic 3 Cine được xem là chiếc flycam quay phim chụp ảnh tốt nhất"
					,
					manufacturerRepository.findById("ma001").orElse(null),
					categoryRepository.findById("cat001").orElse(null),
					new Date(), null, "active", "/img/mavic3cinebandontaythuong.jpg",
					productTypeRepository.findById("bn001").orElse(null))
	);
	productRepository.saveAll(products);
}
@Test
@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
public void mavic3cinebandontayrc() {
	List<Product> products = List.of(
			// Mavic 3 - Bản đơn
			new Product("mv0002", "Mavic 3 cine RC", "Flycam DJI Mavic 3 Cine được xem là chiếc flycam quay phim chụp ảnh tốt nhất"
					,
					manufacturerRepository.findById("ma001").orElse(null),
					categoryRepository.findById("cat001").orElse(null),
					new Date(), null, "active", "/img/mavic3cinebandontayrc.jpg",
					productTypeRepository.findById("bn003").orElse(null))
	);
	productRepository.saveAll(products);
}
@Test
@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
public void mavic3cinebancombotaythuong() {
	List<Product> products = List.of(
			// Mavic 3 - Bản đơn
			new Product("mv0003", "Mavic 3 cine - Bản combo", "Flycam DJI Mavic 3 Cine được xem là chiếc flycam quay phim chụp ảnh tốt nhất"
					,
					manufacturerRepository.findById("ma001").orElse(null),
					categoryRepository.findById("cat001").orElse(null),
					new Date(), null, "active", "/img/mavic3cinebancombotaythuong.jpg",
					productTypeRepository.findById("bn002").orElse(null))
	);
	productRepository.saveAll(products);
}
@Test
@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
public void mavic3cinebancombotayrc() {
	List<Product> products = List.of(
			// Mavic 3 - Bản đơn
			new Product("mv0004", "Mavic 3 cine - Bản combo RC", "Flycam DJI Mavic 3 Cine được xem là chiếc flycam quay phim chụp ảnh tốt nhất"
					,
					manufacturerRepository.findById("ma001").orElse(null),
					categoryRepository.findById("cat001").orElse(null),
					new Date(), null, "active", "/img/mavic3procombotayrc.jpg",
					productTypeRepository.findById("bn004").orElse(null))
	);
	productRepository.saveAll(products);
}
@Test
@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
public void mavic3cinebandontayrcpro() {
	List<Product> products = List.of(
			// Mavic 3 - Bản đơn
			new Product("mv0005", "Mavic 3 cine - đơn rc pro", "Flycam DJI Mavic 3 Cine được xem là chiếc flycam quay phim chụp ảnh tốt nhất"
					,
					manufacturerRepository.findById("ma001").orElse(null),
					categoryRepository.findById("cat001").orElse(null),
					new Date(), null, "active", "/img/mavic3cinebandontaythuong.jpg",
					productTypeRepository.findById("bn009").orElse(null))
	);
	productRepository.saveAll(products);
}
@Test
@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
public void mavic3cinebancombotayrcpro() {
	List<Product> products = List.of(
			// Mavic 3 - Bản đơn
			new Product("mv0006", "Mavic 3 cine - Bản combo RC pro", "Flycam DJI Mavic 3 Cine được xem là chiếc flycam quay phim chụp ảnh tốt nhất"
					,
					manufacturerRepository.findById("ma001").orElse(null),
					categoryRepository.findById("cat001").orElse(null),
					new Date(), null, "active", "/img/mavic3cinebancombotayrcpro.jpg",
					productTypeRepository.findById("bn010").orElse(null))
	);
	productRepository.saveAll(products);
}

	//mavic3 classic
@Test
@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
public void mavic3classicbandontaythuong() {
	List<Product> products = List.of(
			// Mavic 3 - Bản đơn
			new Product("mv0007", "Mavic 3 Classic", "Flycam DJI Mavic 3 Classic  được xem là chiếc flycam quay phim chụp ảnh tốt nhất"
					,
					manufacturerRepository.findById("ma001").orElse(null),
					categoryRepository.findById("cat001").orElse(null),
					new Date(), null, "active", "/img/mavic3Classicbandontaythuong.jpg",
					productTypeRepository.findById("bn001").orElse(null))
	);
	productRepository.saveAll(products);
}
@Test
@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
public void mavic3classicbandontayrc() {
	List<Product> products = List.of(
			// Mavic 3 - Bản đơn
			new Product("mv0008", "Mavic 3 Classic RC", "Flycam DJI Mavic 3 Classic được xem là chiếc flycam quay phim chụp ảnh tốt nhất"
					,
					manufacturerRepository.findById("ma001").orElse(null),
					categoryRepository.findById("cat001").orElse(null),
					new Date(), null, "active", "/img/mavic3Classicbandontayrc.jpg",
					productTypeRepository.findById("bn003").orElse(null))
	);
	productRepository.saveAll(products);
}
@Test
@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
public void mavic3classicbancombotaythuong() {
	List<Product> products = List.of(
			// Mavic 3 - Bản đơn
			new Product("mv0009", "Mavic 3 Classic - Bản combo", "Flycam DJI Mavic 3 Classic được xem là chiếc flycam quay phim chụp ảnh tốt nhất"
					,
					manufacturerRepository.findById("ma001").orElse(null),
					categoryRepository.findById("cat001").orElse(null),
					new Date(), null, "active", "/img/mavic3Classicbancombotaythuong.jpg",
					productTypeRepository.findById("bn002").orElse(null))
	);
	productRepository.saveAll(products);
}
@Test
@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
public void mavic3classicbancombotayrc() {
	List<Product> products = List.of(
			// Mavic 3 - Bản đơn
			new Product("mv0010", "Mavic 3 Classic - Bản combo RC", "Flycam DJI Mavic 3 Classic được xem là chiếc flycam quay phim chụp ảnh tốt nhất"
					,
					manufacturerRepository.findById("ma001").orElse(null),
					categoryRepository.findById("cat001").orElse(null),
					new Date(), null, "active", "/img/mavic3Classicbancombotayrc.jpg",
					productTypeRepository.findById("bn004").orElse(null))
	);
	productRepository.saveAll(products);
}
@Test
@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
public void mavic3classicbandontayrcpro() {
	List<Product> products = List.of(
			// Mavic 3 - Bản đơn
			new Product("mv0011", "Mavic 3 Classic đơn rc pro", "Flycam DJI Mavic 3 Classic được xem là chiếc flycam quay phim chụp ảnh tốt nhất"
					,
					manufacturerRepository.findById("ma001").orElse(null),
					categoryRepository.findById("cat001").orElse(null),
					new Date(), null, "active", "/img/mavic3cinebandontaythuong.jpg",
					productTypeRepository.findById("bn009").orElse(null))
	);
	productRepository.saveAll(products);
}

@Test
@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
public void mavic3classicbancombotayrcpro() {
	List<Product> products = List.of(
			// Mavic 3 - Bản đơn
			new Product("mv0012", "Mavic 3 Classic - Bản combo RC pro", "Flycam DJI Mavic 3 Classic được xem là chiếc flycam quay phim chụp ảnh tốt nhất"
					,
					manufacturerRepository.findById("ma001").orElse(null),
					categoryRepository.findById("cat001").orElse(null),
					new Date(), null, "active", "/img/mavic3cinebancombotayrcpro.jpg",
					productTypeRepository.findById("bn010").orElse(null))
	);
	productRepository.saveAll(products);
}

//mavic3pro
	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void mavic3probandontaythuong() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("mv0013", "Mavic 3 Pro", "Flycam DJI Mavic 3 Pro  được xem là chiếc flycam quay phim chụp ảnh tốt nhất"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat001").orElse(null),
						new Date(), null, "active", "/img/mavic3probandontaythuong.jpg",
						productTypeRepository.findById("bn001").orElse(null))
		);
		productRepository.saveAll(products);
	}
	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void mavic3probandontayrc() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("mv0014", "Mavic 3 pro RC", "Flycam DJI Mavic 3 pro được xem là chiếc flycam quay phim chụp ảnh tốt nhất"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat001").orElse(null),
						new Date(), null, "active", "/img/mavic3probandontayrc.jpg",
						productTypeRepository.findById("bn003").orElse(null))
		);
		productRepository.saveAll(products);
	}
	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void mavic3probancombotaythuong() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("mv0015", "Mavic 3 pro - Bản combo", "Flycam DJI Mavic 3 pro được xem là chiếc flycam quay phim chụp ảnh tốt nhất"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat001").orElse(null),
						new Date(), null, "active", "/img/mavic3probancombotaythuong.jpg",
						productTypeRepository.findById("bn002").orElse(null))
		);
		productRepository.saveAll(products);
	}
	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void mavic3probancombotayrc() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("mv0016", "Mavic 3 pro combo RC", "Flycam DJI Mavic 3 pro được xem là chiếc flycam quay phim chụp ảnh tốt nhất"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat001").orElse(null),
						new Date(), null, "active", "/img/mavic3probancombotayrc.jpg",
						productTypeRepository.findById("bn004").orElse(null))
		);
		productRepository.saveAll(products);
	}
	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void mavic3probandontayrcpro() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("mv0017", "Mavic 3 Classic đơn rc pro", "Flycam DJI Mavic 3 pro được xem là chiếc flycam quay phim chụp ảnh tốt nhất"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat001").orElse(null),
						new Date(), null, "active", "/img/mavic3probandontayrcpro.jpg",
						productTypeRepository.findById("bn009").orElse(null))
		);
		productRepository.saveAll(products);
	}

	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void mavic3proicbancombotayrcpro() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("mv0018", "Mavic 3 pro - combo RC pro", "Flycam DJI Mavic 3 pro được xem là chiếc flycam quay phim chụp ảnh tốt nhất"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat001").orElse(null),
						new Date(), null, "active", "/img/mavic3probancombotayrcpro.jpg",
						productTypeRepository.findById("bn010").orElse(null))
		);
		productRepository.saveAll(products);
	}
//	2pro
	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void mavic2probandontaythuong() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("mv0019", "Mavic 2 Pro", "Flycam DJI Mavic 2 Pro  được xem là chiếc flycam quay phim chụp ảnh tốt nhất"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat001").orElse(null),
						new Date(), null, "active", "/img/mavic2probandontaythuong.jpg",
						productTypeRepository.findById("bn001").orElse(null))
		);
		productRepository.saveAll(products);
	}
	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void mavic2probandontayrc() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("mv0020", "Mavic 2 pro controller", "Flycam DJI Mavic 2 pro được xem là chiếc flycam quay phim chụp ảnh tốt nhất"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat001").orElse(null),
						new Date(), null, "active", "/img/mavic2probandontayrc.jpg",
						productTypeRepository.findById("bn007").orElse(null))
		);
		productRepository.saveAll(products);
	}
	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void mavic2probancombotaythuong() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("mv0021", "Mavic 2 pro - Bản combo", "Flycam DJI Mavic 2 pro được xem là chiếc flycam quay phim chụp ảnh tốt nhất"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat001").orElse(null),
						new Date(), null, "active", "/img/mavic2probancombotaythuong.jpg",
						productTypeRepository.findById("bn002").orElse(null))
		);
		productRepository.saveAll(products);
	}
	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void mavic2probancombotayrc() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("mv0022", "Mavic 2 pro combo controller", "Flycam DJI Mavic 2 pro được xem là chiếc flycam quay phim chụp ảnh tốt nhất"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat001").orElse(null),
						new Date(), null, "active", "/img/mavic2probancombotayrc.jpg",
						productTypeRepository.findById("bn008").orElse(null))
		);
		productRepository.saveAll(products);
	}
//	mavicpro
	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void mavicprobandontaythuong() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("mv0023", "Mavic Pro", "Flycam DJI Mavic 2 Pro  được xem là chiếc flycam quay phim chụp ảnh tốt nhất"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat001").orElse(null),
						new Date(), null, "active", "/img/mavicprobandon.jpg",
						productTypeRepository.findById("bn001").orElse(null))
		);
		productRepository.saveAll(products);
	}

	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void mavicprobancombo() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("mv0024", "Mavic pro - Bản combo", "Flycam DJI Mavic 2 pro được xem là chiếc flycam quay phim chụp ảnh tốt nhất"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat001").orElse(null),
						new Date(), null, "active", "/img/mavicprobancom.jpg",
						productTypeRepository.findById("bn002").orElse(null))
		);
		productRepository.saveAll(products);
	}

	//	detail
	@Test
	@Rollback(false)
	public void createDetailedmavic2pro() {
		// Danh sách mã sản phẩm cần tạo DetailedProduct
		List<String> productCodes = Arrays.asList(
				"mv0019"
		);

		for (String code : productCodes) {
			// Tìm sản phẩm từ mã code
			productRepository.findByProductCode(code).ifPresentOrElse(
					product -> {
						// Nếu tìm thấy sản phẩm, tạo DetailedProduct
						DetailedProduct detailedProduct = DetailedProduct.builder()
								.detailedProductCode("dt" + code) // tạo mã chi tiết duy nhất
								.product(product) // gán sản phẩm vào chi tiết sản phẩm
								.description("DJI Mavic 2 pro\n" +
										"Imaging Above Everything\n" +
										" \n" +
										"Seeing is Believing\n" +
										"4/3 CMOS Hasselblad Camera\n" +
										"5.1K Apple ProRes\n" +
										"Omnidirectional Obstacle Sensing") // mô tả chi tiết gồm tên sản phẩm và loại sản phẩm
								.quantity(100) // số lượng mặc định
								.priceVND(BigDecimal.valueOf(5000000)) // giá mặc định
								.date(new Date()) // ngày tạo
								// ngày tạo
								.build();

						// Lưu vào cơ sở dữ liệu
						detailedProductRepository.save(detailedProduct);
					},
					() -> {
						// Nếu không tìm thấy sản phẩm
						System.out.println("Không tìm thấy sản phẩm với mã " + code);
					}
			);
		}

	}
	@Test
	@Rollback(false)
	public void createDetailedmavic2procombo() {
		// Danh sách mã sản phẩm cần tạo DetailedProduct
		List<String> productCodes = Arrays.asList(
				"mv0020"
		);

		for (String code : productCodes) {
			// Tìm sản phẩm từ mã code
			productRepository.findByProductCode(code).ifPresentOrElse(
					product -> {
						// Nếu tìm thấy sản phẩm, tạo DetailedProduct
						DetailedProduct detailedProduct = DetailedProduct.builder()
								.detailedProductCode("dt" + code) // tạo mã chi tiết duy nhất
								.product(product) // gán sản phẩm vào chi tiết sản phẩm
								.description("DJI Mavic 2 pro\n" +
										"Imaging Above Everything\n" +
										" \n" +
										"Seeing is Believing\n" +
										"4/3 CMOS Hasselblad Camera\n" +
										"5.1K Apple ProRes\n" +
										"Omnidirectional Obstacle Sensing") // mô tả chi tiết gồm tên sản phẩm và loại sản phẩm
								.quantity(100) // số lượng mặc định
								.priceVND(BigDecimal.valueOf(8000000)) // giá mặc định
								.date(new Date()) // ngày tạo
								// ngày tạo
								.build();

						// Lưu vào cơ sở dữ liệu
						detailedProductRepository.save(detailedProduct);
					},
					() -> {
						// Nếu không tìm thấy sản phẩm
						System.out.println("Không tìm thấy sản phẩm với mã " + code);
					}
			);
		}

	}
	@Test
	@Rollback(false)
	public void createDetailedmavic2procombocontroller() {
		// Danh sách mã sản phẩm cần tạo DetailedProduct
		List<String> productCodes = Arrays.asList(
				"mv0021"
		);

		for (String code : productCodes) {
			// Tìm sản phẩm từ mã code
			productRepository.findByProductCode(code).ifPresentOrElse(
					product -> {
						// Nếu tìm thấy sản phẩm, tạo DetailedProduct
						DetailedProduct detailedProduct = DetailedProduct.builder()
								.detailedProductCode("dt" + code) // tạo mã chi tiết duy nhất
								.product(product) // gán sản phẩm vào chi tiết sản phẩm
								.description("DJI Mavic 2 pro\n" +
										"Imaging Above Everything\n" +
										" \n" +
										"Seeing is Believing\n" +
										"4/3 CMOS Hasselblad Camera\n" +
										"5.1K Apple ProRes\n" +
										"Omnidirectional Obstacle Sensing") // mô tả chi tiết gồm tên sản phẩm và loại sản phẩm
								.quantity(100) // số lượng mặc định
								.priceVND(BigDecimal.valueOf(2100000)) // giá mặc định
								.date(new Date()) // ngày tạo
								// ngày tạo
								.build();

						// Lưu vào cơ sở dữ liệu
						detailedProductRepository.save(detailedProduct);
					},
					() -> {
						// Nếu không tìm thấy sản phẩm
						System.out.println("Không tìm thấy sản phẩm với mã " + code);
					}
			);
		}

	}
	@Test
	@Rollback(false)
	public void createDetailedmavic2prodoncontroller() {
		// Danh sách mã sản phẩm cần tạo DetailedProduct
		List<String> productCodes = Arrays.asList(
				"mv0022"
		);

		for (String code : productCodes) {
			// Tìm sản phẩm từ mã code
			productRepository.findByProductCode(code).ifPresentOrElse(
					product -> {
						// Nếu tìm thấy sản phẩm, tạo DetailedProduct
						DetailedProduct detailedProduct = DetailedProduct.builder()
								.detailedProductCode("dt" + code) // tạo mã chi tiết duy nhất
								.product(product) // gán sản phẩm vào chi tiết sản phẩm
								.description("DJI Mavic 2 pro\n" +
										"Imaging Above Everything\n" +
										" \n" +
										"Seeing is Believing\n" +
										"4/3 CMOS Hasselblad Camera\n" +
										"5.1K Apple ProRes\n" +
										"Omnidirectional Obstacle Sensing") // mô tả chi tiết gồm tên sản phẩm và loại sản phẩm
								.quantity(100) // số lượng mặc định
								.priceVND(BigDecimal.valueOf(10000000)) // giá mặc định
								.date(new Date()) // ngày tạo
								// ngày tạo
								.build();

						// Lưu vào cơ sở dữ liệu
						detailedProductRepository.save(detailedProduct);
					},
					() -> {
						// Nếu không tìm thấy sản phẩm
						System.out.println("Không tìm thấy sản phẩm với mã " + code);
					}
			);
		}

	}

	@Test
	@Rollback(false)
	public void createDetailedmavicpro() {
		// Danh sách mã sản phẩm cần tạo DetailedProduct
		List<String> productCodes = Arrays.asList(
				"mv0023"
		);

		for (String code : productCodes) {
			// Tìm sản phẩm từ mã code
			productRepository.findByProductCode(code).ifPresentOrElse(
					product -> {
						// Nếu tìm thấy sản phẩm, tạo DetailedProduct
						DetailedProduct detailedProduct = DetailedProduct.builder()
								.detailedProductCode("dt" + code) // tạo mã chi tiết duy nhất
								.product(product) // gán sản phẩm vào chi tiết sản phẩm
								.description("DJI Mavic pro\n" +
										"Imaging Above Everything\n" +
										" \n" +
										"Seeing is Believing\n" +
										"4/3 CMOS Hasselblad Camera\n" +
										"5.1K Apple ProRes\n" +
										"Omnidirectional Obstacle Sensing") // mô tả chi tiết gồm tên sản phẩm và loại sản phẩm
								.quantity(100) // số lượng mặc định
								.priceVND(BigDecimal.valueOf(5000000)) // giá mặc định
								.date(new Date()) // ngày tạo
								// ngày tạo
								.build();

						// Lưu vào cơ sở dữ liệu
						detailedProductRepository.save(detailedProduct);
					},
					() -> {
						// Nếu không tìm thấy sản phẩm
						System.out.println("Không tìm thấy sản phẩm với mã " + code);
					}
			);
		}

	}
	@Test
	@Rollback(false)
	public void createDetailedmavicprocombo() {
		// Danh sách mã sản phẩm cần tạo DetailedProduct
		List<String> productCodes = Arrays.asList(
				"mv0024"
		);

		for (String code : productCodes) {
			// Tìm sản phẩm từ mã code
			productRepository.findByProductCode(code).ifPresentOrElse(
					product -> {
						// Nếu tìm thấy sản phẩm, tạo DetailedProduct
						DetailedProduct detailedProduct = DetailedProduct.builder()
								.detailedProductCode("dt" + code) // tạo mã chi tiết duy nhất
								.product(product) // gán sản phẩm vào chi tiết sản phẩm
								.description("DJI Mavic pro\n" +
										"Imaging Above Everything\n" +
										" \n" +
										"Seeing is Believing\n" +
										"4/3 CMOS Hasselblad Camera\n" +
										"5.1K Apple ProRes\n" +
										"Omnidirectional Obstacle Sensing") // mô tả chi tiết gồm tên sản phẩm và loại sản phẩm
								.quantity(100) // số lượng mặc định
								.priceVND(BigDecimal.valueOf(6500000)) // giá mặc định
								.date(new Date()) // ngày tạo
								// ngày tạo
								.build();

						// Lưu vào cơ sở dữ liệu
						detailedProductRepository.save(detailedProduct);
					},
					() -> {
						// Nếu không tìm thấy sản phẩm
						System.out.println("Không tìm thấy sản phẩm với mã " + code);
					}
			);
		}

	}




	//	detail
	@Test
	@Rollback(false)
	public void createDetailedmavic3cine() {
		// Danh sách mã sản phẩm cần tạo DetailedProduct
		List<String> productCodes = Arrays.asList(
				"mv0001","mv0002","mv0003","mv0004","mv0005","mv0006"
		);

		for (String code : productCodes) {
			// Tìm sản phẩm từ mã code
			productRepository.findByProductCode(code).ifPresentOrElse(
					product -> {
						// Nếu tìm thấy sản phẩm, tạo DetailedProduct
						DetailedProduct detailedProduct = DetailedProduct.builder()
								.detailedProductCode("dt" + code) // tạo mã chi tiết duy nhất
								.product(product) // gán sản phẩm vào chi tiết sản phẩm
								.description("DJI Mavic 3\n" +
										"Imaging Above Everything\n" +
										" \n" +
										"Seeing is Believing\n" +
										"4/3 CMOS Hasselblad Camera\n" +
										"5.1K Apple ProRes\n" +
										"Omnidirectional Obstacle Sensing") // mô tả chi tiết gồm tên sản phẩm và loại sản phẩm
								.quantity(100) // số lượng mặc định
								.priceVND(BigDecimal.valueOf(5000000)) // giá mặc định
								.date(new Date()) // ngày tạo
								// ngày tạo
								.build();

						// Lưu vào cơ sở dữ liệu
						detailedProductRepository.save(detailedProduct);
					},
					() -> {
						// Nếu không tìm thấy sản phẩm
						System.out.println("Không tìm thấy sản phẩm với mã " + code);
					}
			);
		}

	}
	@Test
	@Rollback(false)
	public void createDetailedmavic3classic() {
		// Danh sách mã sản phẩm cần tạo DetailedProduct
		List<String> productCodes = Arrays.asList(
				"mv0007","mv0008","mv0009","mv0010","mv0011","mv0012"
		);

		for (String code : productCodes) {
			// Tìm sản phẩm từ mã code
			productRepository.findByProductCode(code).ifPresentOrElse(
					product -> {
						// Nếu tìm thấy sản phẩm, tạo DetailedProduct
						DetailedProduct detailedProduct = DetailedProduct.builder()
								.detailedProductCode("dt" + code) // tạo mã chi tiết duy nhất
								.product(product) // gán sản phẩm vào chi tiết sản phẩm
								.description("Hasselblad Camera - Khỏi nguồn cảm hứng\n" +
										"Hassaelblad một trong những thương hiệu camera đứng đầu đến từ Thụy Điển đã thiết kế riêng L2D-20c camera dành riêng cho dòng sp Mavic 3, đưa cảm biến camera 4/3inch Chuyên dụng vào trong 1 thiết kế nhỏ gọn. Với tiêu chuẩn khắc khe nhất của Hasselblad được ứng dụng trong cả phần cứng lẫn phần mêm đã nâng tâm chất lượng hình ảnh đến một tầng cao mới.\n" +
										"\n" +
										"Hasselblad Camera - Khơi nguồn cảm hứng\n" +
										"\n" +
										"Giải pháp màu tự nhiên Hasselblad\n" +
										"Để đảm bảo chất lượng màu sắc tự nhiên như những camera Haselblad khác, L2D-20C phải đáp ứng những tiêu chuản khắc khe nhất và mỗi Pixel ảnh điều phải được điều chính bởi công nghệ HNCS. Với nhiều năm kinh nghiệm đỉnh cao trong nghành của Hasselblad đã mang lại một hệ màu rực rõ và tự nhiên nhất chỉ với việc nhấn nút chụp, cho phép bạn nắm bắt những khoảnh khắc đầy màu sắc của tự nhiên mà không cần phải cài đặt và tinh chỉnh hậu kỳ phức tạp.\n" +
										"\n" +
										"Chất lượng hình ảnh chuyên nghiệp\n" +
										"5.1K/50fps\n" +
										"\n" +
										"Chi tiết hình ảnh sắc nét hơn với Công nghệ Supersampling.\n" +
										"\n" +
										"4K/120fps \n" +
										"\n" +
										"Độ phân giải cao và tốc độ khung hình cao dễ dang mang lại Video chuyển dộng chậm HD.\n" +
										"\n" +
										"10-bit D-Log\n" +
										"\n" +
										"Dễ dàng, linh hoạt hơn trong khi hậu kì.\n" +
										"\n" +
										"HLG\n" +
										"\n" +
										"Cung cấp Dynamic Range lớn hơn phừ hợp cho nhiều thiết bị và các thước phim không cần hiệu chỉnh màu sắc.\n" +
										"\n" +
										"Night Shots\n" +
										"\n" +
										"Chế độ tối ưu cho bối cảnh chụp thiếu sáng như Bình Minh, Hoàng hôn\n" +
										"\n" +
										"Thêm thời gian bay\n" +
										"Pin Mavic 3 classic và Mavic 3 là tương thích với nhau, cung cấp thời lượng bay đến hơn 40 phút") // mô tả chi tiết gồm tên sản phẩm và loại sản phẩm
								.quantity(100) // số lượng mặc định
								.priceVND(BigDecimal.valueOf(5000000)) // giá mặc định
								.date(new Date()) // ngày tạo
								// ngày tạo
								.build();

						// Lưu vào cơ sở dữ liệu
						detailedProductRepository.save(detailedProduct);
					},
					() -> {
						// Nếu không tìm thấy sản phẩm
						System.out.println("Không tìm thấy sản phẩm với mã " + code);
					}
			);
		}

	}
	@Test
	@Rollback(false)
	public void createDetailedmavic3pro() {
		// Danh sách mã sản phẩm cần tạo DetailedProduct
		List<String> productCodes = Arrays.asList(
				"mv0013","mv0014","mv0015","mv0016","mv0017","mv0018"
		);

		for (String code : productCodes) {
			// Tìm sản phẩm từ mã code
			productRepository.findByProductCode(code).ifPresentOrElse(
					product -> {
						// Nếu tìm thấy sản phẩm, tạo DetailedProduct
						DetailedProduct detailedProduct = DetailedProduct.builder()
								.detailedProductCode("dt" + code) // tạo mã chi tiết duy nhất
								.product(product) // gán sản phẩm vào chi tiết sản phẩm
								.description("Đi kèm với DJI RC Pro, DJI Mavic 3 Pro Cine hỗ trợ Apple ProRes có ổ SSD 1TB. Sự lựa chọn cho chụp ảnh trên không chuyên nghiệp.\n" +
										"\n" +
										"Camera Hasselblad CMOS 24MM 4/3\n" +
										"Camera Tele 166MM\n" +
										"Camera Tele tầm trung 70MM\n" +
										"Thời gian bay tối đa 43 phút\n" +
										"Cảm biến chướng ngại vật đa hướng\n" +
										"Truyền video HD 15km\n" +
										"DJI Mavic 3 Series có hiệu suất hình ảnh cấp độ cao tiếp theo của dòng drone Mavic (tiếp nối series DJI Mavic và DJI Mavic 2). Tuy nhiên, DJI không dừng lại ở đó khi tiếp tục nâng cấp hệ thống ba camera của DJI Mavic 3 Pro mở ra một kỷ nguyên mới của máy bay không người lái trang bị máy ảnh phục vụ nhiếp ảnh chuyên nghiệp bằng cách mang ba camera với cảm biến và ống kính có độ dài tiêu cự khác nhau. Được trang bị máy ảnh Hasselblad và máy ảnh tele kép, Mavic 3 Pro là máy bay không người lái ba camera mở ra những góc nhìn chụp mới, cho phép bạn tự do sáng tạo hơn nữa, chụp phong cảnh hấp dẫn, khám phá cách góc nhìn câu chuyện theo một cách mới và tạo ra những kiệt tác điện ảnh.t") // mô tả chi tiết gồm tên sản phẩm và loại sản phẩm
								.quantity(100) // số lượng mặc định
								.priceVND(BigDecimal.valueOf(5000000)) // giá mặc định
								.date(new Date()) // ngày tạo
								// ngày tạo
								.build();

						// Lưu vào cơ sở dữ liệu
						detailedProductRepository.save(detailedProduct);
					},
					() -> {
						// Nếu không tìm thấy sản phẩm
						System.out.println("Không tìm thấy sản phẩm với mã " + code);
					}
			);
		}

	}
//air3
	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void ari3bandontaythuong() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("mar0001", "DJI AIR 3", "Flycam DJI air 3 chiệc drone thông minh, nhiều công nghệ mới sóng o4"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat002").orElse(null),
						new Date(), null, "active", "/img/air3bandontaythuong.jpg",
						productTypeRepository.findById("bn001").orElse(null))
		);
		productRepository.saveAll(products);
	}
	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void ari3sbandontaythuong() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("mar0002", "DJI AIR 3s", "Flycam DJI air 3s chiệc drone thông minh vừa ra mắt"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat002").orElse(null),
						new Date(), null, "active", "/img/air3sbandontaythuong.jpg",
						productTypeRepository.findById("bn001").orElse(null))
		);
		productRepository.saveAll(products);
	}

	//	detail
	@Test
	@Rollback(false)
	public void createDetailedair3and3sbandon() {
		// Danh sách mã sản phẩm cần tạo DetailedProduct
		List<String> productCodes = Arrays.asList(
				"mar0001","mar0002"
		);

		for (String code : productCodes) {
			// Tìm sản phẩm từ mã code
			productRepository.findByProductCode(code).ifPresentOrElse(
					product -> {
						// Nếu tìm thấy sản phẩm, tạo DetailedProduct
						DetailedProduct detailedProduct = DetailedProduct.builder()
								.detailedProductCode("dt" + code) // tạo mã chi tiết duy nhất
								.product(product) // gán sản phẩm vào chi tiết sản phẩm
								.description("DJI air 3 or 3s\n" +
										"Imaging Above Everything\n" +
										" \n" +
										"Seeing is Believing\n" +
										"4/3 CMOS Hasselblad Camera\n" +
										"5.1K Apple ProRes\n" +
										"Omnidirectional Obstacle Sensing") // mô tả chi tiết gồm tên sản phẩm và loại sản phẩm
								.quantity(100) // số lượng mặc định
								.priceVND(BigDecimal.valueOf(3000000)) // giá mặc định
								.date(new Date()) // ngày tạo
								// ngày tạo
								.build();

						// Lưu vào cơ sở dữ liệu
						detailedProductRepository.save(detailedProduct);
					},
					() -> {
						// Nếu không tìm thấy sản phẩm
						System.out.println("Không tìm thấy sản phẩm với mã " + code);
					}
			);
		}

	}

	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void ari3bandontayrc() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("mar0003", "DJI AIR 3", "Flycam DJI air 3 chiệc drone thông minh, nhiều công nghệ mới sóng o4"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat002").orElse(null),
						new Date(), null, "active", "/img/air3bandontayrc.jpg",
						productTypeRepository.findById("bn003").orElse(null))
		);
		productRepository.saveAll(products);
	}

	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void ari3sbandontayrc() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("mar0004", "DJI AIR 3s", "Flycam DJI air 3s chiệc drone thông minh vừa ra mắt"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat002").orElse(null),
						new Date(), null, "active", "/img/air3sbandontayrc.jpg",
						productTypeRepository.findById("bn003").orElse(null))
		);
		productRepository.saveAll(products);
	}

	//	detail
	@Test
	@Rollback(false)
	public void createDetailedair3and3sdontayrc() {
		// Danh sách mã sản phẩm cần tạo DetailedProduct
		List<String> productCodes = Arrays.asList(
				"mar0003","mar0004"
		);

		for (String code : productCodes) {
			// Tìm sản phẩm từ mã code
			productRepository.findByProductCode(code).ifPresentOrElse(
					product -> {
						// Nếu tìm thấy sản phẩm, tạo DetailedProduct
						DetailedProduct detailedProduct = DetailedProduct.builder()
								.detailedProductCode("dt" + code) // tạo mã chi tiết duy nhất
								.product(product) // gán sản phẩm vào chi tiết sản phẩm
								.description("DJI air 3 or 3s\n" +
										"Imaging Above Everything\n" +
										" \n" +
										"Seeing is Believing\n" +
										"4/3 CMOS Hasselblad Camera\n" +
										"5.1K Apple ProRes\n" +
										"Omnidirectional Obstacle Sensing") // mô tả chi tiết gồm tên sản phẩm và loại sản phẩm
								.quantity(100) // số lượng mặc định
								.priceVND(BigDecimal.valueOf(7000000)) // giá mặc định
								.date(new Date()) // ngày tạo
								// ngày tạo
								.build();

						// Lưu vào cơ sở dữ liệu
						detailedProductRepository.save(detailedProduct);
					},
					() -> {
						// Nếu không tìm thấy sản phẩm
						System.out.println("Không tìm thấy sản phẩm với mã " + code);
					}
			);
		}

	}
	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void ari3bancombotaythuong() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("mar0005", "DJI AIR 3", "Flycam DJI air 3 chiệc drone thông minh, nhiều công nghệ mới sóng o4"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat002").orElse(null),
						new Date(), null, "active", "/img/air3bandontaythuong.jpg",
						productTypeRepository.findById("bn002").orElse(null))
		);
		productRepository.saveAll(products);
	}
	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void ari3sbancombotaythuong() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("mar0006", "DJI AIR 3s", "Flycam DJI air 3s chiệc drone thông minh vừa ra mắt"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat002").orElse(null),
						new Date(), null, "active", "/img/air3sbandontaythuong.jpg",
						productTypeRepository.findById("bn002").orElse(null))
		);
		productRepository.saveAll(products);
	}

	//	detail
	@Test
	@Rollback(false)
	public void createDetailedair3and3scombo() {
		// Danh sách mã sản phẩm cần tạo DetailedProduct
		List<String> productCodes = Arrays.asList(
				"mar0005","mar0006"
		);

		for (String code : productCodes) {
			// Tìm sản phẩm từ mã code
			productRepository.findByProductCode(code).ifPresentOrElse(
					product -> {
						// Nếu tìm thấy sản phẩm, tạo DetailedProduct
						DetailedProduct detailedProduct = DetailedProduct.builder()
								.detailedProductCode("dt" + code) // tạo mã chi tiết duy nhất
								.product(product) // gán sản phẩm vào chi tiết sản phẩm
								.description("DJI air 3 or 3s\n" +
										"Imaging Above Everything\n" +
										" \n" +
										"Seeing is Believing\n" +
										"4/3 CMOS Hasselblad Camera\n" +
										"5.1K Apple ProRes\n" +
										"Omnidirectional Obstacle Sensing") // mô tả chi tiết gồm tên sản phẩm và loại sản phẩm
								.quantity(100) // số lượng mặc định
								.priceVND(BigDecimal.valueOf(10000000)) // giá mặc định
								.date(new Date()) // ngày tạo
								// ngày tạo
								.build();

						// Lưu vào cơ sở dữ liệu
						detailedProductRepository.save(detailedProduct);
					},
					() -> {
						// Nếu không tìm thấy sản phẩm
						System.out.println("Không tìm thấy sản phẩm với mã " + code);
					}
			);
		}

	}
	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void ari3bancombotayrc() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("mar0007", "DJI AIR 3", "Flycam DJI air 3 chiệc drone thông minh, nhiều công nghệ mới sóng o4"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat002").orElse(null),
						new Date(), null, "active", "/img/air3bandontayrc.jpg",
						productTypeRepository.findById("bn004").orElse(null))
		);
		productRepository.saveAll(products);
	}
	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void ari3sbancombotayrc() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("mar0008", "DJI AIR 3s", "Flycam DJI air 3s chiệc drone thông minh vừa ra mắt"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat002").orElse(null),
						new Date(), null, "active", "/img/air3sbandontayrc.jpg",
						productTypeRepository.findById("bn004").orElse(null))
		);
		productRepository.saveAll(products);
	}

	//	detail
	@Test
	@Rollback(false)
	public void createDetailedair3and3scombotayrc() {
		// Danh sách mã sản phẩm cần tạo DetailedProduct
		List<String> productCodes = Arrays.asList(
				"mar0007","mar0008"
		);

		for (String code : productCodes) {
			// Tìm sản phẩm từ mã code
			productRepository.findByProductCode(code).ifPresentOrElse(
					product -> {
						// Nếu tìm thấy sản phẩm, tạo DetailedProduct
						DetailedProduct detailedProduct = DetailedProduct.builder()
								.detailedProductCode("dt" + code) // tạo mã chi tiết duy nhất
								.product(product) // gán sản phẩm vào chi tiết sản phẩm
								.description("DJI air 3 or 3s\n" +
										"Imaging Above Everything\n" +
										" \n" +
										"Seeing is Believing\n" +
										"4/3 CMOS Hasselblad Camera\n" +
										"5.1K Apple ProRes\n" +
										"Omnidirectional Obstacle Sensing") // mô tả chi tiết gồm tên sản phẩm và loại sản phẩm
								.quantity(100) // số lượng mặc định
								.priceVND(BigDecimal.valueOf(12000000)) // giá mặc định
								.date(new Date()) // ngày tạo
								// ngày tạo
								.build();

						// Lưu vào cơ sở dữ liệu
						detailedProductRepository.save(detailedProduct);
					},
					() -> {
						// Nếu không tìm thấy sản phẩm
						System.out.println("Không tìm thấy sản phẩm với mã " + code);
					}
			);
		}

	}
//	air 2
	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void ari2bandontaythuong() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("mar0009", "DJI AIR 2", "Flycam DJI air 2 chiệc drone thông minh, nhiều công nghệ mới sóng o4"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat002").orElse(null),
						new Date(), null, "active", "/img/air2bandontaythuong.jpg",
						productTypeRepository.findById("bn001").orElse(null))
		);
		productRepository.saveAll(products);
	}
	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void ari2sbandontaythuong() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("mar0010", "DJI AIR 2s", "Flycam DJI air 2s chiệc drone thông minh vừa ra mắt"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat002").orElse(null),
						new Date(), null, "active", "/img/air2sbandontaythuong.jpg",
						productTypeRepository.findById("bn001").orElse(null))
		);
		productRepository.saveAll(products);
	}

	//	detail
	@Test
	@Rollback(false)
	public void createDetailedair2and2sbandon() {
		// Danh sách mã sản phẩm cần tạo DetailedProduct
		List<String> productCodes = Arrays.asList(
				"mar0009","mar0010"
		);

		for (String code : productCodes) {
			// Tìm sản phẩm từ mã code
			productRepository.findByProductCode(code).ifPresentOrElse(
					product -> {
						// Nếu tìm thấy sản phẩm, tạo DetailedProduct
						DetailedProduct detailedProduct = DetailedProduct.builder()
								.detailedProductCode("dt" + code) // tạo mã chi tiết duy nhất
								.product(product) // gán sản phẩm vào chi tiết sản phẩm
								.description("DJI air 2 or 2s\n" +
										"Imaging Above Everything\n" +
										" \n" +
										"Seeing is Believing\n" +
										"4/3 CMOS Hasselblad Camera\n" +
										"5.1K Apple ProRes\n" +
										"Omnidirectional Obstacle Sensing") // mô tả chi tiết gồm tên sản phẩm và loại sản phẩm
								.quantity(100) // số lượng mặc định
								.priceVND(BigDecimal.valueOf(3000000)) // giá mặc định
								.date(new Date()) // ngày tạo
								// ngày tạo
								.build();

						// Lưu vào cơ sở dữ liệu
						detailedProductRepository.save(detailedProduct);
					},
					() -> {
						// Nếu không tìm thấy sản phẩm
						System.out.println("Không tìm thấy sản phẩm với mã " + code);
					}
			);
		}

	}


	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void ari2sbandontayrc() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("mar0011", "DJI AIR 2s", "Flycam DJI air 2s chiệc drone thông minh vừa ra mắt"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat002").orElse(null),
						new Date(), null, "active", "/img/air2sbandontayrc.jpg",
						productTypeRepository.findById("bn007").orElse(null))
		);
		productRepository.saveAll(products);
	}

	//	detail
	@Test
	@Rollback(false)
	public void createDetailedair2and2sdontayrc() {
		// Danh sách mã sản phẩm cần tạo DetailedProduct
		List<String> productCodes = Arrays.asList(
				"mar0011"
		);

		for (String code : productCodes) {
			// Tìm sản phẩm từ mã code
			productRepository.findByProductCode(code).ifPresentOrElse(
					product -> {
						// Nếu tìm thấy sản phẩm, tạo DetailedProduct
						DetailedProduct detailedProduct = DetailedProduct.builder()
								.detailedProductCode("dt" + code) // tạo mã chi tiết duy nhất
								.product(product) // gán sản phẩm vào chi tiết sản phẩm
								.description("DJI air 2s\n" +
										"Imaging Above Everything\n" +
										" \n" +
										"Seeing is Believing\n" +
										"4/3 CMOS Hasselblad Camera\n" +
										"5.1K Apple ProRes\n" +
										"Omnidirectional Obstacle Sensing") // mô tả chi tiết gồm tên sản phẩm và loại sản phẩm
								.quantity(100) // số lượng mặc định
								.priceVND(BigDecimal.valueOf(7000000)) // giá mặc định
								.date(new Date()) // ngày tạo
								// ngày tạo
								.build();

						// Lưu vào cơ sở dữ liệu
						detailedProductRepository.save(detailedProduct);
					},
					() -> {
						// Nếu không tìm thấy sản phẩm
						System.out.println("Không tìm thấy sản phẩm với mã " + code);
					}
			);
		}

	}
	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void ari2bancombotaythuong() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("mar0012", "DJI AIR 2", "Flycam DJI air 2 chiệc drone thông minh, nhiều công nghệ mới sóng o4"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat002").orElse(null),
						new Date(), null, "active", "/img/air2bancombotaythuong.jpg",
						productTypeRepository.findById("bn002").orElse(null))
		);
		productRepository.saveAll(products);
	}
	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void ari2sbancombotaythuong() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("mar0013", "DJI AIR 2s", "Flycam DJI air 2s chiệc drone thông minh vừa ra mắt"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat002").orElse(null),
						new Date(), null, "active", "/img/air2sbandontaythuong.jpg",
						productTypeRepository.findById("bn002").orElse(null))
		);
		productRepository.saveAll(products);
	}

	//	detail
	@Test
	@Rollback(false)
	public void createDetailedair2and2scombo() {
		// Danh sách mã sản phẩm cần tạo DetailedProduct
		List<String> productCodes = Arrays.asList(
				"mar0012","mar0013"
		);

		for (String code : productCodes) {
			// Tìm sản phẩm từ mã code
			productRepository.findByProductCode(code).ifPresentOrElse(
					product -> {
						// Nếu tìm thấy sản phẩm, tạo DetailedProduct
						DetailedProduct detailedProduct = DetailedProduct.builder()
								.detailedProductCode("dt" + code) // tạo mã chi tiết duy nhất
								.product(product) // gán sản phẩm vào chi tiết sản phẩm
								.description("DJI air 2 or 2s\n" +
										"Imaging Above Everything\n" +
										" \n" +
										"Seeing is Believing\n" +
										"4/3 CMOS Hasselblad Camera\n" +
										"5.1K Apple ProRes\n" +
										"Omnidirectional Obstacle Sensing") // mô tả chi tiết gồm tên sản phẩm và loại sản phẩm
								.quantity(100) // số lượng mặc định
								.priceVND(BigDecimal.valueOf(10000000)) // giá mặc định
								.date(new Date()) // ngày tạo
								// ngày tạo
								.build();

						// Lưu vào cơ sở dữ liệu
						detailedProductRepository.save(detailedProduct);
					},
					() -> {
						// Nếu không tìm thấy sản phẩm
						System.out.println("Không tìm thấy sản phẩm với mã " + code);
					}
			);
		}

	}

	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void ari2sbancombotayrc() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("mar0014", "DJI AIR 2s", "Flycam DJI air 2s chiệc drone thông minh vừa ra mắt"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat002").orElse(null),
						new Date(), null, "active", "/img/air2sbancombotayrc.jpg",
						productTypeRepository.findById("bn004").orElse(null))
		);
		productRepository.saveAll(products);
	}

	//	detail
	@Test
	@Rollback(false)
	public void createDetailedair2and2scombotayrc() {
		// Danh sách mã sản phẩm cần tạo DetailedProduct
		List<String> productCodes = Arrays.asList(
				"mar0014"
		);

		for (String code : productCodes) {
			// Tìm sản phẩm từ mã code
			productRepository.findByProductCode(code).ifPresentOrElse(
					product -> {
						// Nếu tìm thấy sản phẩm, tạo DetailedProduct
						DetailedProduct detailedProduct = DetailedProduct.builder()
								.detailedProductCode("dt" + code) // tạo mã chi tiết duy nhất
								.product(product) // gán sản phẩm vào chi tiết sản phẩm
								.description("DJI air 2s\n" +
										"Imaging Above Everything\n" +
										" \n" +
										"Seeing is Believing\n" +
										"4/3 CMOS Hasselblad Camera\n" +
										"5.1K Apple ProRes\n" +
										"Omnidirectional Obstacle Sensing") // mô tả chi tiết gồm tên sản phẩm và loại sản phẩm
								.quantity(100) // số lượng mặc định
								.priceVND(BigDecimal.valueOf(12000000)) // giá mặc định
								.date(new Date()) // ngày tạo
								// ngày tạo
								.build();

						// Lưu vào cơ sở dữ liệu
						detailedProductRepository.save(detailedProduct);
					},
					() -> {
						// Nếu không tìm thấy sản phẩm
						System.out.println("Không tìm thấy sản phẩm với mã " + code);
					}
			);
		}

	}

//	mini

	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void djimini1() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("mn0001", "DJI mini", "Flycam DJI mini chiệc drone thông minh, nhỏ nhẹ"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat003").orElse(null),
						new Date(), null, "active", "/img/djimini1.jpg",
						productTypeRepository.findById("bn001").orElse(null))
		);
		productRepository.saveAll(products);
	}
	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void djimini1combo() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("mn0002", "DJI mini - combo", "Flycam DJI mini chiệc drone thông minh, nhỏ nhẹ"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat003").orElse(null),
						new Date(), null, "active", "/img/djimini1.jpg",
						productTypeRepository.findById("bn002").orElse(null))
		);
		productRepository.saveAll(products);
	}
	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void djiminise() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("mn0003", "DJI mini se", "Flycam DJI mini chiệc drone thông minh, nhỏ nhẹ"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat003").orElse(null),
						new Date(), null, "active", "/img/djiminise.jpg",
						productTypeRepository.findById("bn001").orElse(null))
		);
		productRepository.saveAll(products);
	}
	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void djiminisecombo() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("mn0004", "DJI mini se - combo", "Flycam DJI mini chiệc drone thông minh, nhỏ nhẹ"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat003").orElse(null),
						new Date(), null, "active", "/img/djiminise.jpg",
						productTypeRepository.findById("bn002").orElse(null))
		);
		productRepository.saveAll(products);
	}
	@Test
	@Rollback(false)
	public void createDetailedair1andsedon() {
		// Danh sách mã sản phẩm cần tạo DetailedProduct
		List<String> productCodes = Arrays.asList(
				"mn0001","mn0003"
		);

		for (String code : productCodes) {
			// Tìm sản phẩm từ mã code
			productRepository.findByProductCode(code).ifPresentOrElse(
					product -> {
						// Nếu tìm thấy sản phẩm, tạo DetailedProduct
						DetailedProduct detailedProduct = DetailedProduct.builder()
								.detailedProductCode("dt" + code) // tạo mã chi tiết duy nhất
								.product(product) // gán sản phẩm vào chi tiết sản phẩm
								.description("DJI mini 1, mini se\n" +
										"Imaging Above Everything\n" +
										" \n" +
										"Seeing is Believing\n" +
										"1 CMOS Camera\n" +
										"2k7K Apple ProRes\n" +
										"Omnidirectional Obstacle Sensing") // mô tả chi tiết gồm tên sản phẩm và loại sản phẩm
								.quantity(100) // số lượng mặc định
								.priceVND(BigDecimal.valueOf(4000000)) // giá mặc định
								.date(new Date()) // ngày tạo
								// ngày tạo
								.build();

						// Lưu vào cơ sở dữ liệu
						detailedProductRepository.save(detailedProduct);
					},
					() -> {
						// Nếu không tìm thấy sản phẩm
						System.out.println("Không tìm thấy sản phẩm với mã " + code);
					}
			);
		}

	}

	@Test
	@Rollback(false)
	public void createDetailedair1andsecombo() {
		// Danh sách mã sản phẩm cần tạo DetailedProduct
		List<String> productCodes = Arrays.asList(
				"mn0002","mn0004"
		);

		for (String code : productCodes) {
			// Tìm sản phẩm từ mã code
			productRepository.findByProductCode(code).ifPresentOrElse(
					product -> {
						// Nếu tìm thấy sản phẩm, tạo DetailedProduct
						DetailedProduct detailedProduct = DetailedProduct.builder()
								.detailedProductCode("dt" + code) // tạo mã chi tiết duy nhất
								.product(product) // gán sản phẩm vào chi tiết sản phẩm
								.description("DJI mini 1, mini se\n" +
										"Imaging Above Everything\n" +
										" \n" +
										"Seeing is Believing\n" +
										"1 CMOS Camera\n" +
										"2k7K Apple ProRes\n" +
										"Omnidirectional Obstacle Sensing") // mô tả chi tiết gồm tên sản phẩm và loại sản phẩm
								.quantity(100) // số lượng mặc định
								.priceVND(BigDecimal.valueOf(4500000)) // giá mặc định
								.date(new Date()) // ngày tạo
								// ngày tạo
								.build();

						// Lưu vào cơ sở dữ liệu
						detailedProductRepository.save(detailedProduct);
					},
					() -> {
						// Nếu không tìm thấy sản phẩm
						System.out.println("Không tìm thấy sản phẩm với mã " + code);
					}
			);
		}

	}

	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void djimini2() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("mn0005", "DJI mini 2", "Flycam DJI mini 2 chiệc drone thông minh, nhỏ nhẹ"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat003").orElse(null),
						new Date(), null, "active", "/img/djimini2.jpg",
						productTypeRepository.findById("bn001").orElse(null))
		);
		productRepository.saveAll(products);
	}
	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void djimini2combo() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("mn0006", "DJI mini 2 - combo", "Flycam DJI mini 2 chiệc drone thông minh, nhỏ nhẹ"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat003").orElse(null),
						new Date(), null, "active", "/img/djimini2.jpg",
						productTypeRepository.findById("bn002").orElse(null))
		);
		productRepository.saveAll(products);
	}
	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void djimini2se() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("mn0007", "DJI mini 2 se", "Flycam DJI mini 2se chiệc drone thông minh, nhỏ nhẹ"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat003").orElse(null),
						new Date(), null, "active", "/img/djimini2se.jpg",
						productTypeRepository.findById("bn001").orElse(null))
		);
		productRepository.saveAll(products);
	}
	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void djimini2secombo() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("mn0008", "DJI mini 2 se - combo", "Flycam DJI mini 2se chiệc drone thông minh, nhỏ nhẹ"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat003").orElse(null),
						new Date(), null, "active", "/img/djimini2se.jpg",
						productTypeRepository.findById("bn002").orElse(null))
		);
		productRepository.saveAll(products);
	}

	@Test
	@Rollback(false)
	public void createDetailedair2and2sedon() {
		// Danh sách mã sản phẩm cần tạo DetailedProduct
		List<String> productCodes = Arrays.asList(
				"mn0005","mn0007"
		);

		for (String code : productCodes) {
			// Tìm sản phẩm từ mã code
			productRepository.findByProductCode(code).ifPresentOrElse(
					product -> {
						// Nếu tìm thấy sản phẩm, tạo DetailedProduct
						DetailedProduct detailedProduct = DetailedProduct.builder()
								.detailedProductCode("dt" + code) // tạo mã chi tiết duy nhất
								.product(product) // gán sản phẩm vào chi tiết sản phẩm
								.description("DJI mini 2, mini 2 se\n" +
										"Imaging Above Everything\n" +
										" \n" +
										"Seeing is Believing\n" +
										"2.3 CMOS Camera\n" +
										"4K Apple ProRes\n" +
										"Omnidirectional Obstacle Sensing") // mô tả chi tiết gồm tên sản phẩm và loại sản phẩm
								.quantity(100) // số lượng mặc định
								.priceVND(BigDecimal.valueOf(6500000)) // giá mặc định
								.date(new Date()) // ngày tạo
								// ngày tạo
								.build();

						// Lưu vào cơ sở dữ liệu
						detailedProductRepository.save(detailedProduct);
					},
					() -> {
						// Nếu không tìm thấy sản phẩm
						System.out.println("Không tìm thấy sản phẩm với mã " + code);
					}
			);
		}

	}

	@Test
	@Rollback(false)
	public void createDetailedair2and2secombo() {
		// Danh sách mã sản phẩm cần tạo DetailedProduct
		List<String> productCodes = Arrays.asList(
				"mn0006","mn0008"
		);

		for (String code : productCodes) {
			// Tìm sản phẩm từ mã code
			productRepository.findByProductCode(code).ifPresentOrElse(
					product -> {
						// Nếu tìm thấy sản phẩm, tạo DetailedProduct
						DetailedProduct detailedProduct = DetailedProduct.builder()
								.detailedProductCode("dt" + code) // tạo mã chi tiết duy nhất
								.product(product) // gán sản phẩm vào chi tiết sản phẩm
								.description("DJI mini 2, mini 2 se\n" +
										"Imaging Above Everything\n" +
										" \n" +
										"Seeing is Believing\n" +
										"2.3 CMOS Camera\n" +
										"4 K Apple ProRes\n" +
										"Omnidirectional Obstacle Sensing") // mô tả chi tiết gồm tên sản phẩm và loại sản phẩm
								.quantity(100) // số lượng mặc định
								.priceVND(BigDecimal.valueOf(7900000)) // giá mặc định
								.date(new Date()) // ngày tạo
								// ngày tạo
								.build();

						// Lưu vào cơ sở dữ liệu
						detailedProductRepository.save(detailedProduct);
					},
					() -> {
						// Nếu không tìm thấy sản phẩm
						System.out.println("Không tìm thấy sản phẩm với mã " + code);
					}
			);
		}

	}


	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void djimini3taythuong() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("mn0009", "DJI mini 3", "Flycam DJI mini 3 chiệc drone thông minh, nhỏ nhẹ"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat003").orElse(null),
						new Date(), null, "active", "/img/djimini3.jpg",
						productTypeRepository.findById("bn001").orElse(null))
		);
		productRepository.saveAll(products);
	}
	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void djimini3combotaythuong() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("mn0010", "DJI mini 3 - combo", "Flycam DJI mini 3 chiệc drone thông minh, nhỏ nhẹ"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat003").orElse(null),
						new Date(), null, "active", "/img/djimini3.jpg",
						productTypeRepository.findById("bn002").orElse(null))
		);
		productRepository.saveAll(products);
	}
	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void djimini3tayrc() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("mn0011", "DJI mini 3", "Flycam DJI mini 3 chiệc drone thông minh, nhỏ nhẹ"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat003").orElse(null),
						new Date(), null, "active", "/img/djimini3rc.jpg",
						productTypeRepository.findById("bn003").orElse(null))
		);
		productRepository.saveAll(products);
	}
	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void djimini3combotayrc() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("mn0012", "DJI mini 3 - combo", "Flycam DJI mini 3 chiệc drone thông minh, nhỏ nhẹ"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat003").orElse(null),
						new Date(), null, "active", "/img/djimini3rc.jpg",
						productTypeRepository.findById("bn004").orElse(null))
		);
		productRepository.saveAll(products);
	}

	@Test
	@Rollback(false)
	public void createDetailedairmini3taythuongbandon() {
		// Danh sách mã sản phẩm cần tạo DetailedProduct
		List<String> productCodes = Arrays.asList(
				"mn0009"
		);

		for (String code : productCodes) {
			// Tìm sản phẩm từ mã code
			productRepository.findByProductCode(code).ifPresentOrElse(
					product -> {
						// Nếu tìm thấy sản phẩm, tạo DetailedProduct
						DetailedProduct detailedProduct = DetailedProduct.builder()
								.detailedProductCode("dt" + code) // tạo mã chi tiết duy nhất
								.product(product) // gán sản phẩm vào chi tiết sản phẩm
								.description("DJI mini 3\n" +
										"Imaging Above Everything\n" +
										" \n" +
										"Seeing is Believing\n" +
										"2.3 CMOS Camera\n" +
										"4K Apple ProRes\n" +
										"Omnidirectional Obstacle Sensing") // mô tả chi tiết gồm tên sản phẩm và loại sản phẩm
								.quantity(100) // số lượng mặc định
								.priceVND(BigDecimal.valueOf(7900000)) // giá mặc định
								.date(new Date()) // ngày tạo
								// ngày tạo
								.build();

						// Lưu vào cơ sở dữ liệu
						detailedProductRepository.save(detailedProduct);
					},
					() -> {
						// Nếu không tìm thấy sản phẩm
						System.out.println("Không tìm thấy sản phẩm với mã " + code);
					}
			);
		}

	}
	@Test
	@Rollback(false)
	public void createDetailedairmini3tayrcbandon() {
		// Danh sách mã sản phẩm cần tạo DetailedProduct
		List<String> productCodes = Arrays.asList(
				"mn0010"
		);

		for (String code : productCodes) {
			// Tìm sản phẩm từ mã code
			productRepository.findByProductCode(code).ifPresentOrElse(
					product -> {
						// Nếu tìm thấy sản phẩm, tạo DetailedProduct
						DetailedProduct detailedProduct = DetailedProduct.builder()
								.detailedProductCode("dt" + code) // tạo mã chi tiết duy nhất
								.product(product) // gán sản phẩm vào chi tiết sản phẩm
								.description("DJI mini 3\n" +
										"Imaging Above Everything\n" +
										" \n" +
										"Seeing is Believing\n" +
										"2.3 CMOS Camera\n" +
										"4K Apple ProRes\n" +
										"Omnidirectional Obstacle Sensing") // mô tả chi tiết gồm tên sản phẩm và loại sản phẩm
								.quantity(100) // số lượng mặc định
								.priceVND(BigDecimal.valueOf(1100000)) // giá mặc định
								.date(new Date()) // ngày tạo
								// ngày tạo
								.build();

						// Lưu vào cơ sở dữ liệu
						detailedProductRepository.save(detailedProduct);
					},
					() -> {
						// Nếu không tìm thấy sản phẩm
						System.out.println("Không tìm thấy sản phẩm với mã " + code);
					}
			);
		}

	}

	@Test
	@Rollback(false)
	public void createDetailedairmini3combo() {
		// Danh sách mã sản phẩm cần tạo DetailedProduct
		List<String> productCodes = Arrays.asList(
				"mn0011"
		);

		for (String code : productCodes) {
			// Tìm sản phẩm từ mã code
			productRepository.findByProductCode(code).ifPresentOrElse(
					product -> {
						// Nếu tìm thấy sản phẩm, tạo DetailedProduct
						DetailedProduct detailedProduct = DetailedProduct.builder()
								.detailedProductCode("dt" + code) // tạo mã chi tiết duy nhất
								.product(product) // gán sản phẩm vào chi tiết sản phẩm
								.description("DJI mini 3\n" +
										"Imaging Above Everything\n" +
										" \n" +
										"Seeing is Believing\n" +
										"2.3 CMOS Camera\n" +
										"4 K Apple ProRes\n" +
										"Omnidirectional Obstacle Sensing") // mô tả chi tiết gồm tên sản phẩm và loại sản phẩm
								.quantity(100) // số lượng mặc định
								.priceVND(BigDecimal.valueOf(1100000)) // giá mặc định
								.date(new Date()) // ngày tạo
								// ngày tạo
								.build();

						// Lưu vào cơ sở dữ liệu
						detailedProductRepository.save(detailedProduct);
					},
					() -> {
						// Nếu không tìm thấy sản phẩm
						System.out.println("Không tìm thấy sản phẩm với mã " + code);
					}
			);
		}
	}
		@Test
		@Rollback(false)
		public void createDetailedairmini3donrc() {
			// Danh sách mã sản phẩm cần tạo DetailedProduct
			List<String> productCodes = Arrays.asList(
					"mn0011"
			);

			for (String code : productCodes) {
				// Tìm sản phẩm từ mã code
				productRepository.findByProductCode(code).ifPresentOrElse(
						product -> {
							// Nếu tìm thấy sản phẩm, tạo DetailedProduct
							DetailedProduct detailedProduct = DetailedProduct.builder()
									.detailedProductCode("dt" + code) // tạo mã chi tiết duy nhất
									.product(product) // gán sản phẩm vào chi tiết sản phẩm
									.description("DJI mini 3\n" +
											"Imaging Above Everything\n" +
											" \n" +
											"Seeing is Believing\n" +
											"2.3 CMOS Camera\n" +
											"4 K Apple ProRes\n" +
											"Omnidirectional Obstacle Sensing") // mô tả chi tiết gồm tên sản phẩm và loại sản phẩm
									.quantity(100) // số lượng mặc định
									.priceVND(BigDecimal.valueOf(1100000)) // giá mặc định
									.date(new Date()) // ngày tạo
									// ngày tạo
									.build();

							// Lưu vào cơ sở dữ liệu
							detailedProductRepository.save(detailedProduct);
						},
						() -> {
							// Nếu không tìm thấy sản phẩm
							System.out.println("Không tìm thấy sản phẩm với mã " + code);
						}
				);
			}

		}
		@Test
		@Rollback(false)
		public void createDetailedairmini3comborc() {
			// Danh sách mã sản phẩm cần tạo DetailedProduct
			List<String> productCodes = Arrays.asList(
					"mn0012"
			);

			for (String code : productCodes) {
				// Tìm sản phẩm từ mã code
				productRepository.findByProductCode(code).ifPresentOrElse(
						product -> {
							// Nếu tìm thấy sản phẩm, tạo DetailedProduct
							DetailedProduct detailedProduct = DetailedProduct.builder()
									.detailedProductCode("dt" + code) // tạo mã chi tiết duy nhất
									.product(product) // gán sản phẩm vào chi tiết sản phẩm
									.description("DJI mini 3\n" +
											"Imaging Above Everything\n" +
											" \n" +
											"Seeing is Believing\n" +
											"2.3 CMOS Camera\n" +
											"4 K Apple ProRes\n" +
											"Omnidirectional Obstacle Sensing") // mô tả chi tiết gồm tên sản phẩm và loại sản phẩm
									.quantity(100) // số lượng mặc định
									.priceVND(BigDecimal.valueOf(1300000)) // giá mặc định
									.date(new Date()) // ngày tạo
									// ngày tạo
									.build();

							// Lưu vào cơ sở dữ liệu
							detailedProductRepository.save(detailedProduct);
						},
						() -> {
							// Nếu không tìm thấy sản phẩm
							System.out.println("Không tìm thấy sản phẩm với mã " + code);
						}
				);
			}

		}

	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void djimini4taythuong() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("mn0013", "DJI mini 4", "Flycam DJI mini 4 chiệc drone thông minh, nhỏ nhẹ"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat003").orElse(null),
						new Date(), null, "active", "/img/djimini4.jpg",
						productTypeRepository.findById("bn001").orElse(null))
		);
		productRepository.saveAll(products);
	}
	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void djimini4combotaythuong() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("mn0014", "DJI mini 4 - combo", "Flycam DJI mini 4 chiệc drone thông minh, nhỏ nhẹ"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat003").orElse(null),
						new Date(), null, "active", "/img/djimini4.jpg",
						productTypeRepository.findById("bn002").orElse(null))
		);
		productRepository.saveAll(products);
	}
	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void djimini4tayrc() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("mn0015", "DJI mini 4", "Flycam DJI mini 4 chiệc drone thông minh, nhỏ nhẹ"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat003").orElse(null),
						new Date(), null, "active", "/img/djimini4rc.jpg",
						productTypeRepository.findById("bn003").orElse(null))
		);
		productRepository.saveAll(products);
	}
	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void djimini4combotayrc() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("mn0016", "DJI mini 4 - combo", "Flycam DJI mini 4 chiệc drone thông minh, nhỏ nhẹ"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat003").orElse(null),
						new Date(), null, "active", "/img/djimini4rc.jpg",
						productTypeRepository.findById("bn004").orElse(null))
		);
		productRepository.saveAll(products);
	}

	@Test
	@Rollback(false)
	public void createDetailedairmini4taythuongbandon() {
		// Danh sách mã sản phẩm cần tạo DetailedProduct
		List<String> productCodes = Arrays.asList(
				"mn0013"
		);

		for (String code : productCodes) {
			// Tìm sản phẩm từ mã code
			productRepository.findByProductCode(code).ifPresentOrElse(
					product -> {
						// Nếu tìm thấy sản phẩm, tạo DetailedProduct
						DetailedProduct detailedProduct = DetailedProduct.builder()
								.detailedProductCode("dt" + code) // tạo mã chi tiết duy nhất
								.product(product) // gán sản phẩm vào chi tiết sản phẩm
								.description("DJI mini 4 pro\n" +
										"Imaging Above Everything\n" +
										" \n" +
										"Seeing is Believing\n" +
										"2.3 CMOS Camera\n" +
										"4K Apple ProRes\n" +
										"Omnidirectional Obstacle Sensing") // mô tả chi tiết gồm tên sản phẩm và loại sản phẩm
								.quantity(100) // số lượng mặc định
								.priceVND(BigDecimal.valueOf(14500000)) // giá mặc định
								.date(new Date()) // ngày tạo
								// ngày tạo
								.build();

						// Lưu vào cơ sở dữ liệu
						detailedProductRepository.save(detailedProduct);
					},
					() -> {
						// Nếu không tìm thấy sản phẩm
						System.out.println("Không tìm thấy sản phẩm với mã " + code);
					}
			);
		}

	}
	@Test
	@Rollback(false)
	public void createDetailedairmini4tayrcbandon() {
		// Danh sách mã sản phẩm cần tạo DetailedProduct
		List<String> productCodes = Arrays.asList(
				"mn0014"
		);

		for (String code : productCodes) {
			// Tìm sản phẩm từ mã code
			productRepository.findByProductCode(code).ifPresentOrElse(
					product -> {
						// Nếu tìm thấy sản phẩm, tạo DetailedProduct
						DetailedProduct detailedProduct = DetailedProduct.builder()
								.detailedProductCode("dt" + code) // tạo mã chi tiết duy nhất
								.product(product) // gán sản phẩm vào chi tiết sản phẩm
								.description("DJI mini 4pro\n" +
										"Imaging Above Everything\n" +
										" \n" +
										"Seeing is Believing\n" +
										"2.3 CMOS Camera\n" +
										"4K Apple ProRes\n" +
										"Omnidirectional Obstacle Sensing") // mô tả chi tiết gồm tên sản phẩm và loại sản phẩm
								.quantity(100) // số lượng mặc định
								.priceVND(BigDecimal.valueOf(16500000)) // giá mặc định
								.date(new Date()) // ngày tạo
								// ngày tạo
								.build();

						// Lưu vào cơ sở dữ liệu
						detailedProductRepository.save(detailedProduct);
					},
					() -> {
						// Nếu không tìm thấy sản phẩm
						System.out.println("Không tìm thấy sản phẩm với mã " + code);
					}
			);
		}

	}

	@Test
	@Rollback(false)
	public void createDetailedairmini4combo() {
		// Danh sách mã sản phẩm cần tạo DetailedProduct
		List<String> productCodes = Arrays.asList(
				"mn0015"
		);

		for (String code : productCodes) {
			// Tìm sản phẩm từ mã code
			productRepository.findByProductCode(code).ifPresentOrElse(
					product -> {
						// Nếu tìm thấy sản phẩm, tạo DetailedProduct
						DetailedProduct detailedProduct = DetailedProduct.builder()
								.detailedProductCode("dt" + code) // tạo mã chi tiết duy nhất
								.product(product) // gán sản phẩm vào chi tiết sản phẩm
								.description("DJI mini 4 pro\n" +
										"Imaging Above Everything\n" +
										" \n" +
										"Seeing is Believing\n" +
										"2.3 CMOS Camera\n" +
										"4 K Apple ProRes\n" +
										"Omnidirectional Obstacle Sensing") // mô tả chi tiết gồm tên sản phẩm và loại sản phẩm
								.quantity(100) // số lượng mặc định
								.priceVND(BigDecimal.valueOf(1650000)) // giá mặc định
								.date(new Date()) // ngày tạo
								// ngày tạo
								.build();

						// Lưu vào cơ sở dữ liệu
						detailedProductRepository.save(detailedProduct);
					},
					() -> {
						// Nếu không tìm thấy sản phẩm
						System.out.println("Không tìm thấy sản phẩm với mã " + code);
					}
			);
		}
	}
	@Test
	@Rollback(false)
	public void createDetailedairmini4donrc() {
		// Danh sách mã sản phẩm cần tạo DetailedProduct
		List<String> productCodes = Arrays.asList(
				"mn0016"
		);

		for (String code : productCodes) {
			// Tìm sản phẩm từ mã code
			productRepository.findByProductCode(code).ifPresentOrElse(
					product -> {
						// Nếu tìm thấy sản phẩm, tạo DetailedProduct
						DetailedProduct detailedProduct = DetailedProduct.builder()
								.detailedProductCode("dt" + code) // tạo mã chi tiết duy nhất
								.product(product) // gán sản phẩm vào chi tiết sản phẩm
								.description("DJI mini 4 pro\n" +
										"Imaging Above Everything\n" +
										" \n" +
										"Seeing is Believing\n" +
										"2.3 CMOS Camera\n" +
										"4 K Apple ProRes\n" +
										"Omnidirectional Obstacle Sensing") // mô tả chi tiết gồm tên sản phẩm và loại sản phẩm
								.quantity(100) // số lượng mặc định
								.priceVND(BigDecimal.valueOf(19500000)) // giá mặc định
								.date(new Date()) // ngày tạo
								// ngày tạo
								.build();

						// Lưu vào cơ sở dữ liệu
						detailedProductRepository.save(detailedProduct);
					},
					() -> {
						// Nếu không tìm thấy sản phẩm
						System.out.println("Không tìm thấy sản phẩm với mã " + code);
					}
			);
		}

	}
//	mini3pro
	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void djimini3protaythuong() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("mn0017", "DJI mini 3 pro", "Flycam DJI mini 3pro chiệc drone thông minh, nhỏ nhẹ"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat003").orElse(null),
						new Date(), null, "active", "/img/djimini3pro.jpg",
						productTypeRepository.findById("bn001").orElse(null))
		);
		productRepository.saveAll(products);
	}
	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void djimini3procombotaythuong() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("mn0018", "DJI mini 3 pro - combo", "Flycam DJI mini 3 pro chiệc drone thông minh, nhỏ nhẹ"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat003").orElse(null),
						new Date(), null, "active", "/img/djimini3pro.jpg",
						productTypeRepository.findById("bn002").orElse(null))
		);
		productRepository.saveAll(products);
	}
	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void djimini3protayrc() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("mn0019", "DJI mini 3 pro", "Flycam DJI mini 3 pro chiệc drone thông minh, nhỏ nhẹ"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat003").orElse(null),
						new Date(), null, "active", "/img/djimini3prorc.jpg",
						productTypeRepository.findById("bn003").orElse(null))
		);
		productRepository.saveAll(products);
	}
	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void djimini3procombotayrc() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("mn0020", "DJI mini 3 pro - combo", "Flycam DJI mini 3 pro chiệc drone thông minh, nhỏ nhẹ"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat003").orElse(null),
						new Date(), null, "active", "/img/djimini3prorc.jpg",
						productTypeRepository.findById("bn004").orElse(null))
		);
		productRepository.saveAll(products);
	}

	@Test
	@Rollback(false)
	public void createDetailedairmini3protaythuongbandon() {
		// Danh sách mã sản phẩm cần tạo DetailedProduct
		List<String> productCodes = Arrays.asList(
				"mn0017"
		);

		for (String code : productCodes) {
			// Tìm sản phẩm từ mã code
			productRepository.findByProductCode(code).ifPresentOrElse(
					product -> {
						// Nếu tìm thấy sản phẩm, tạo DetailedProduct
						DetailedProduct detailedProduct = DetailedProduct.builder()
								.detailedProductCode("dt" + code) // tạo mã chi tiết duy nhất
								.product(product) // gán sản phẩm vào chi tiết sản phẩm
								.description("DJI mini 3 pro\n" +
										"Imaging Above Everything\n" +
										" \n" +
										"Seeing is Believing\n" +
										"2.3 CMOS Camera\n" +
										"4K Apple ProRes\n" +
										"Omnidirectional Obstacle Sensing") // mô tả chi tiết gồm tên sản phẩm và loại sản phẩm
								.quantity(100) // số lượng mặc định
								.priceVND(BigDecimal.valueOf(14500000)) // giá mặc định
								.date(new Date()) // ngày tạo
								// ngày tạo
								.build();

						// Lưu vào cơ sở dữ liệu
						detailedProductRepository.save(detailedProduct);
					},
					() -> {
						// Nếu không tìm thấy sản phẩm
						System.out.println("Không tìm thấy sản phẩm với mã " + code);
					}
			);
		}

	}
	@Test
	@Rollback(false)
	public void createDetailedairmini3protayrcbandon() {
		// Danh sách mã sản phẩm cần tạo DetailedProduct
		List<String> productCodes = Arrays.asList(
				"mn0018"
		);

		for (String code : productCodes) {
			// Tìm sản phẩm từ mã code
			productRepository.findByProductCode(code).ifPresentOrElse(
					product -> {
						// Nếu tìm thấy sản phẩm, tạo DetailedProduct
						DetailedProduct detailedProduct = DetailedProduct.builder()
								.detailedProductCode("dt" + code) // tạo mã chi tiết duy nhất
								.product(product) // gán sản phẩm vào chi tiết sản phẩm
								.description("DJI mini 3 pro\n" +
										"Imaging Above Everything\n" +
										" \n" +
										"Seeing is Believing\n" +
										"2.3 CMOS Camera\n" +
										"4K Apple ProRes\n" +
										"Omnidirectional Obstacle Sensing") // mô tả chi tiết gồm tên sản phẩm và loại sản phẩm
								.quantity(100) // số lượng mặc định
								.priceVND(BigDecimal.valueOf(16500000)) // giá mặc định
								.date(new Date()) // ngày tạo
								// ngày tạo
								.build();

						// Lưu vào cơ sở dữ liệu
						detailedProductRepository.save(detailedProduct);
					},
					() -> {
						// Nếu không tìm thấy sản phẩm
						System.out.println("Không tìm thấy sản phẩm với mã " + code);
					}
			);
		}

	}

	@Test
	@Rollback(false)
	public void createDetailedairmini3procombo() {
		// Danh sách mã sản phẩm cần tạo DetailedProduct
		List<String> productCodes = Arrays.asList(
				"mn0019"
		);

		for (String code : productCodes) {
			// Tìm sản phẩm từ mã code
			productRepository.findByProductCode(code).ifPresentOrElse(
					product -> {
						// Nếu tìm thấy sản phẩm, tạo DetailedProduct
						DetailedProduct detailedProduct = DetailedProduct.builder()
								.detailedProductCode("dt" + code) // tạo mã chi tiết duy nhất
								.product(product) // gán sản phẩm vào chi tiết sản phẩm
								.description("DJI mini 3 pro\n" +
										"Imaging Above Everything\n" +
										" \n" +
										"Seeing is Believing\n" +
										"2.3 CMOS Camera\n" +
										"4 K Apple ProRes\n" +
										"Omnidirectional Obstacle Sensing") // mô tả chi tiết gồm tên sản phẩm và loại sản phẩm
								.quantity(100) // số lượng mặc định
								.priceVND(BigDecimal.valueOf(1650000)) // giá mặc định
								.date(new Date()) // ngày tạo
								// ngày tạo
								.build();

						// Lưu vào cơ sở dữ liệu
						detailedProductRepository.save(detailedProduct);
					},
					() -> {
						// Nếu không tìm thấy sản phẩm
						System.out.println("Không tìm thấy sản phẩm với mã " + code);
					}
			);
		}
	}
	@Test
	@Rollback(false)
	public void createDetailedairmini3prodonrc() {
		// Danh sách mã sản phẩm cần tạo DetailedProduct
		List<String> productCodes = Arrays.asList(
				"mn0020"
		);

		for (String code : productCodes) {
			// Tìm sản phẩm từ mã code
			productRepository.findByProductCode(code).ifPresentOrElse(
					product -> {
						// Nếu tìm thấy sản phẩm, tạo DetailedProduct
						DetailedProduct detailedProduct = DetailedProduct.builder()
								.detailedProductCode("dt" + code) // tạo mã chi tiết duy nhất
								.product(product) // gán sản phẩm vào chi tiết sản phẩm
								.description("DJI mini 3 pro\n" +
										"Imaging Above Everything\n" +
										" \n" +
										"Seeing is Believing\n" +
										"2.3 CMOS Camera\n" +
										"4 K Apple ProRes\n" +
										"Omnidirectional Obstacle Sensing") // mô tả chi tiết gồm tên sản phẩm và loại sản phẩm
								.quantity(100) // số lượng mặc định
								.priceVND(BigDecimal.valueOf(19500000)) // giá mặc định
								.date(new Date()) // ngày tạo
								// ngày tạo
								.build();

						// Lưu vào cơ sở dữ liệu
						detailedProductRepository.save(detailedProduct);
					},
					() -> {
						// Nếu không tìm thấy sản phẩm
						System.out.println("Không tìm thấy sản phẩm với mã " + code);
					}
			);
		}

	}

//	fpv

	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void djifpvflas() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("fpv0001", "DJI FPV FLASH", "Drone góc nhìn thứ nhất, bay đầm khoẻ, cảm xúc"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat004").orElse(null),
						new Date(), null, "active", "/img/djifpvflas.jpg",
						productTypeRepository.findById("bn005").orElse(null))
		);
		productRepository.saveAll(products);
	}
	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void djifpvflascombo() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("fpv0002", "DJI FPV FLASH combo", "Drone góc nhìn thứ nhất, bay đầm khoẻ, cảm xúc"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat004").orElse(null),
						new Date(), null, "active", "/img/djifpvflas.jpg",
						productTypeRepository.findById("bn006").orElse(null))
		);
		productRepository.saveAll(products);
	}
	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void djifpvavata1() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("fpv0003", "DJI FPV avata ", "Drone góc nhìn thứ nhất, bay đầm khoẻ, cảm xúc"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat004").orElse(null),
						new Date(), null, "active", "/img/djifpvavata.jpg",
						productTypeRepository.findById("bn005").orElse(null))
		);
		productRepository.saveAll(products);
	}
	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void djifpvavata1combo() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("fpv0004", "DJI FPV avata combo", "Drone góc nhìn thứ nhất, bay đầm khoẻ, cảm xúc"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat004").orElse(null),
						new Date(), null, "active", "/img/djifpvavata.jpg",
						productTypeRepository.findById("bn006").orElse(null))
		);
		productRepository.saveAll(products);
	}
	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void djifpvavata2() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("fpv0005", "DJI FPV avata 2", "Drone góc nhìn thứ nhất, bay đầm khoẻ, cảm xúc"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat004").orElse(null),
						new Date(), null, "active", "/img/djifpvavata2.jpg",
						productTypeRepository.findById("bn005").orElse(null))
		);
		productRepository.saveAll(products);
	}
	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void djifpvavata2combo() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("fpv0006", "DJI FPV avata 2 combo", "Drone góc nhìn thứ nhất, bay đầm khoẻ, cảm xúc"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat004").orElse(null),
						new Date(), null, "active", "/img/djifpvavata2.jpg",
						productTypeRepository.findById("bn006").orElse(null))
		);
		productRepository.saveAll(products);
	}
	@Test
	@Rollback(false) // Để không rollback khi bài kiểm tra kết thúc
	public void kinhgg2() {
		List<Product> products = List.of(
				// Mavic 3 - Bản đơn
				new Product("fpv0007", "DJI Goggles 2", "Kính fpv, chuyền hình ảnh"
						,
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat004").orElse(null),
						new Date(), null, "active", "/img/DJIGoggles2.jpg",
						productTypeRepository.findById("bn005").orElse(null))
		);
		productRepository.saveAll(products);
	}

	@Test
	@Rollback(false)
	public void createDetailedairmdjifpvflas() {
		// Danh sách mã sản phẩm cần tạo DetailedProduct
		List<String> productCodes = Arrays.asList(
				"fpv0001"
		);

		for (String code : productCodes) {
			// Tìm sản phẩm từ mã code
			productRepository.findByProductCode(code).ifPresentOrElse(
					product -> {
						// Nếu tìm thấy sản phẩm, tạo DetailedProduct
						DetailedProduct detailedProduct = DetailedProduct.builder()
								.detailedProductCode("dt" + code) // tạo mã chi tiết duy nhất
								.product(product) // gán sản phẩm vào chi tiết sản phẩm
								.description("DJI fpv flash\n" +
										"Imaging Above Everything\n" +
										" \n" +
										"Seeing is Believing\n" +
										"2.3 CMOS Camera\n" +
										"4 K Apple ProRes\n" +
										"Omnidirectional Obstacle Sensing") // mô tả chi tiết gồm tên sản phẩm và loại sản phẩm
								.quantity(100) // số lượng mặc định
								.priceVND(BigDecimal.valueOf(9000000)) // giá mặc định
								.date(new Date()) // ngày tạo
								// ngày tạo
								.build();

						// Lưu vào cơ sở dữ liệu
						detailedProductRepository.save(detailedProduct);
					},
					() -> {
						// Nếu không tìm thấy sản phẩm
						System.out.println("Không tìm thấy sản phẩm với mã " + code);
					}
			);
		}
	}
	@Test
	@Rollback(false)
	public void createDetailedairmdjifpvflascombo() {
		// Danh sách mã sản phẩm cần tạo DetailedProduct
		List<String> productCodes = Arrays.asList(
				"fpv0002"
		);

		for (String code : productCodes) {
			// Tìm sản phẩm từ mã code
			productRepository.findByProductCode(code).ifPresentOrElse(
					product -> {
						// Nếu tìm thấy sản phẩm, tạo DetailedProduct
						DetailedProduct detailedProduct = DetailedProduct.builder()
								.detailedProductCode("dt" + code) // tạo mã chi tiết duy nhất
								.product(product) // gán sản phẩm vào chi tiết sản phẩm
								.description("DJI fpv flash\n" +
										"Imaging Above Everything\n" +
										" \n" +
										"Seeing is Believing\n" +
										"2.3 CMOS Camera\n" +
										"4 K Apple ProRes\n" +
										"Omnidirectional Obstacle Sensing") // mô tả chi tiết gồm tên sản phẩm và loại sản phẩm
								.quantity(100) // số lượng mặc định
								.priceVND(BigDecimal.valueOf(9000000)) // giá mặc định
								.date(new Date()) // ngày tạo
								// ngày tạo
								.build();

						// Lưu vào cơ sở dữ liệu
						detailedProductRepository.save(detailedProduct);
					},
					() -> {
						// Nếu không tìm thấy sản phẩm
						System.out.println("Không tìm thấy sản phẩm với mã " + code);
					}
			);
		}
	}
	@Test
	@Rollback(false)
	public void createDetailedairmdjifpvavata() {
		// Danh sách mã sản phẩm cần tạo DetailedProduct
		List<String> productCodes = Arrays.asList(
				"fpv0003"
		);

		for (String code : productCodes) {
			// Tìm sản phẩm từ mã code
			productRepository.findByProductCode(code).ifPresentOrElse(
					product -> {
						// Nếu tìm thấy sản phẩm, tạo DetailedProduct
						DetailedProduct detailedProduct = DetailedProduct.builder()
								.detailedProductCode("dt" + code) // tạo mã chi tiết duy nhất
								.product(product) // gán sản phẩm vào chi tiết sản phẩm
								.description("DJI fpv avata\n" +
										"Imaging Above Everything\n" +
										" \n" +
										"Seeing is Believing\n" +
										"2.3 CMOS Camera\n" +
										"4 K Apple ProRes\n" +
										"Omnidirectional Obstacle Sensing") // mô tả chi tiết gồm tên sản phẩm và loại sản phẩm
								.quantity(100) // số lượng mặc định
								.priceVND(BigDecimal.valueOf(13000000)) // giá mặc định
								.date(new Date()) // ngày tạo
								// ngày tạo
								.build();

						// Lưu vào cơ sở dữ liệu
						detailedProductRepository.save(detailedProduct);
					},
					() -> {
						// Nếu không tìm thấy sản phẩm
						System.out.println("Không tìm thấy sản phẩm với mã " + code);
					}
			);
		}
	}
	@Test
	@Rollback(false)
	public void createDetailedairmdjifpvavatacombo() {
		// Danh sách mã sản phẩm cần tạo DetailedProduct
		List<String> productCodes = Arrays.asList(
				"fpv0004"
		);

		for (String code : productCodes) {
			// Tìm sản phẩm từ mã code
			productRepository.findByProductCode(code).ifPresentOrElse(
					product -> {
						// Nếu tìm thấy sản phẩm, tạo DetailedProduct
						DetailedProduct detailedProduct = DetailedProduct.builder()
								.detailedProductCode("dt" + code) // tạo mã chi tiết duy nhất
								.product(product) // gán sản phẩm vào chi tiết sản phẩm
								.description("DJI fpv avata\n" +
										"Imaging Above Everything\n" +
										" \n" +
										"Seeing is Believing\n" +
										"2.3 CMOS Camera\n" +
										"4 K Apple ProRes\n" +
										"Omnidirectional Obstacle Sensing") // mô tả chi tiết gồm tên sản phẩm và loại sản phẩm
								.quantity(100) // số lượng mặc định
								.priceVND(BigDecimal.valueOf(13000000)) // giá mặc định
								.date(new Date()) // ngày tạo
								// ngày tạo
								.build();

						// Lưu vào cơ sở dữ liệu
						detailedProductRepository.save(detailedProduct);
					},
					() -> {
						// Nếu không tìm thấy sản phẩm
						System.out.println("Không tìm thấy sản phẩm với mã " + code);
					}
			);
		}
	}
	@Test
	@Rollback(false)
	public void createDetailedairmdjifpvavata2() {
		// Danh sách mã sản phẩm cần tạo DetailedProduct
		List<String> productCodes = Arrays.asList(
				"fpv0005"
		);

		for (String code : productCodes) {
			// Tìm sản phẩm từ mã code
			productRepository.findByProductCode(code).ifPresentOrElse(
					product -> {
						// Nếu tìm thấy sản phẩm, tạo DetailedProduct
						DetailedProduct detailedProduct = DetailedProduct.builder()
								.detailedProductCode("dt" + code) // tạo mã chi tiết duy nhất
								.product(product) // gán sản phẩm vào chi tiết sản phẩm
								.description("DJI fpv avata 2\n" +
										"Imaging Above Everything\n" +
										" \n" +
										"Seeing is Believing\n" +
										"1 CMOS Camera\n" +
										"4 K Apple ProRes\n" +
										"Omnidirectional Obstacle Sensing") // mô tả chi tiết gồm tên sản phẩm và loại sản phẩm
								.quantity(100) // số lượng mặc định
								.priceVND(BigDecimal.valueOf(13000000)) // giá mặc định
								.date(new Date()) // ngày tạo
								// ngày tạo
								.build();

						// Lưu vào cơ sở dữ liệu
						detailedProductRepository.save(detailedProduct);
					},
					() -> {
						// Nếu không tìm thấy sản phẩm
						System.out.println("Không tìm thấy sản phẩm với mã " + code);
					}
			);
		}
	}
	@Test
	@Rollback(false)
	public void createDetailedairmdjifpvavata2combo() {
		// Danh sách mã sản phẩm cần tạo DetailedProduct
		List<String> productCodes = Arrays.asList(
				"fpv0006"
		);

		for (String code : productCodes) {
			// Tìm sản phẩm từ mã code
			productRepository.findByProductCode(code).ifPresentOrElse(
					product -> {
						// Nếu tìm thấy sản phẩm, tạo DetailedProduct
						DetailedProduct detailedProduct = DetailedProduct.builder()
								.detailedProductCode("dt" + code) // tạo mã chi tiết duy nhất
								.product(product) // gán sản phẩm vào chi tiết sản phẩm
								.description("DJI fpv avata\n" +
										"Imaging Above Everything\n" +
										" \n" +
										"Seeing is Believing\n" +
										"1 CMOS Camera\n" +
										"4 K Apple ProRes\n" +
										"Omnidirectional Obstacle Sensing") // mô tả chi tiết gồm tên sản phẩm và loại sản phẩm
								.quantity(100) // số lượng mặc định
								.priceVND(BigDecimal.valueOf(13000000)) // giá mặc định
								.date(new Date()) // ngày tạo
								// ngày tạo
								.build();

						// Lưu vào cơ sở dữ liệu
						detailedProductRepository.save(detailedProduct);
					},
					() -> {
						// Nếu không tìm thấy sản phẩm
						System.out.println("Không tìm thấy sản phẩm với mã " + code);
					}
			);
		}
	}
	@Test
	@Rollback(false)
	public void kinhgg() {
		// Danh sách mã sản phẩm cần tạo DetailedProduct
		List<String> productCodes = Arrays.asList(
				"fpv0007"
		);

		for (String code : productCodes) {
			// Tìm sản phẩm từ mã code
			productRepository.findByProductCode(code).ifPresentOrElse(
					product -> {
						// Nếu tìm thấy sản phẩm, tạo DetailedProduct
						DetailedProduct detailedProduct = DetailedProduct.builder()
								.detailedProductCode("dt" + code) // tạo mã chi tiết duy nhất
								.product(product) // gán sản phẩm vào chi tiết sản phẩm
								.description("kính gg2 đẹp sóng khoẻ") // mô tả chi tiết gồm tên sản phẩm và loại sản phẩm
								.quantity(100) // số lượng mặc định
								.priceVND(BigDecimal.valueOf(9000000)) // giá mặc định
								.date(new Date()) // ngày tạo
								// ngày tạo
								.build();

						// Lưu vào cơ sở dữ liệu
						detailedProductRepository.save(detailedProduct);
					},
					() -> {
						// Nếu không tìm thấy sản phẩm
						System.out.println("Không tìm thấy sản phẩm với mã " + code);
					}
			);
		}
	}

@Test
	@Rollback(false)
	public void testAddAdditionalProducts() {

	// DJI Ronin 2
	List<Product> moreProducts = List.of(
			new Product("rn0001", "Ronin 1", "Gimbal chống rung mạnh mẽ, thiết kế dành cho máy quay chuyên nghiệp",
					manufacturerRepository.findById("ma001").orElse(null),
					categoryRepository.findById("cat008").orElse(null),
					new Date(), null, "active", "/img/djironi1.jpg",
					productTypeRepository.findById("bn005").orElse(null)),
			new Product("rn0002", "Ronin mini", "Gimbal chống rung mạnh mẽ, thiết kế dành cho máy quay chuyên nghiệp",
					manufacturerRepository.findById("ma001").orElse(null),
					categoryRepository.findById("cat008").orElse(null),
					new Date(), null, "active", "/img/djironi1.jpg",
					productTypeRepository.findById("bn006").orElse(null)),
			new Product("rn0003", "Ronin 2 mini", "Gimbal chống rung mạnh mẽ, thiết kế dành cho máy quay chuyên nghiệp",
					manufacturerRepository.findById("ma001").orElse(null),
					categoryRepository.findById("cat008").orElse(null),
					new Date(), null, "active", "/img/djironi1.jpg",
					productTypeRepository.findById("bn005").orElse(null)),
			new Product("rn0004", "Ronin 3 mini", "Gimbal chống rung mạnh mẽ, thiết kế dành cho máy quay chuyên nghiệp",
					manufacturerRepository.findById("ma001").orElse(null),
					categoryRepository.findById("cat008").orElse(null),
					new Date(), null, "active", "/img/ronin_s.jpg",
					productTypeRepository.findById("bn006").orElse(null)),
	new Product("rn0005", "Ronin 2", "Gimbal chống rung mạnh mẽ, thiết kế dành cho máy quay chuyên nghiệp",
			manufacturerRepository.findById("ma001").orElse(null),
			categoryRepository.findById("cat008").orElse(null),
			new Date(), null, "active", "/img/djironi2.jpg",
			productTypeRepository.findById("bn005").orElse(null)),

// DJI Ronin 3
			new Product("rn0006", "Ronin 3", "Gimbal chống rung với khả năng ổn định vượt trội, phiên bản mới với công nghệ hiện đại",
					manufacturerRepository.findById("ma001").orElse(null),
					categoryRepository.findById("cat008").orElse(null),
					new Date(), null, "active", "/img/ronin_s.jpg",
					productTypeRepository.findById("bn005").orElse(null)),

// DJI Ronin 3 - Bản combo
			new Product("rn0007", "Ronin 3", "Gimbal chống rung với khả năng ổn định vượt trội, phiên bản combo với đầy đủ phụ kiện",
					manufacturerRepository.findById("ma001").orElse(null),
					categoryRepository.findById("cat008").orElse(null),
					new Date(), null, "active", "/img/ronin_s.jpg",
					productTypeRepository.findById("bn006").orElse(null)),
// DJI Ronin 4 Pro
			new Product("rn0008", "Ronin 4 Pro", "Gimbal chống rung cho máy quay chuyên nghiệp, phiên bản mới với nhiều tính năng nâng cấp",
					manufacturerRepository.findById("ma001").orElse(null),
					categoryRepository.findById("cat008").orElse(null),
					new Date(), null, "active", "/img/ronin_s.jpg",
					productTypeRepository.findById("bn005").orElse(null)),

// DJI Ronin 4 Pro - Bản combo
			new Product("rn0009", "Ronin 4 Pro", "Gimbal chống rung cho máy quay chuyên nghiệp, phiên bản combo với phụ kiện đầy đủ",
					manufacturerRepository.findById("ma001").orElse(null),
					categoryRepository.findById("cat008").orElse(null),
					new Date(), null, "active", "/img/ronin_s.jpg",
					productTypeRepository.findById("bn006").orElse(null))
			);
			productRepository.saveAll(moreProducts);
}
	@Test
	@Rollback(false)
	public void createDetailedProducts1() {
		// Danh sách mã sản phẩm cần tạo DetailedProduct
		List<String> productCodes = Arrays.asList(
				 "rn0001", "rn0002", "rn0003", "rn0004", "rn0005","rn0006","rn0007","rn0008","rn0009" // Thêm các mã mới ở đây
		);

		for (String code : productCodes) {
			// Tìm sản phẩm từ mã code
			productRepository.findByProductCode(code).ifPresentOrElse(
					product -> {
						// Nếu tìm thấy sản phẩm, tạo DetailedProduct
						DetailedProduct detailedProduct = DetailedProduct.builder()
								.detailedProductCode("dt" + code) // tạo mã chi tiết duy nhất
								.product(product) // gán sản phẩm vào chi tiết sản phẩm
								.description("Mô tả chi tiết cho sản phẩm cho máy ảnh tính hợp nhiều công nghệ, connectting ") // mô tả chi tiết gồm tên sản phẩm và loại sản phẩm
								.quantity(100) // số lượng mặc định
								.priceVND(BigDecimal.valueOf(5000000)) // giá mặc định
								.date(new Date()) // ngày tạo
								// ngày tạo
								.build();

						// Lưu vào cơ sở dữ liệu
						detailedProductRepository.save(detailedProduct);
					},
					() -> {
						// Nếu không tìm thấy sản phẩm
						System.out.println("Không tìm thấy sản phẩm với mã " + code);
					}
			);
		}
	}
	@Test
	@Rollback(false)
	public void testAddPocketProducts() {
		// Thêm các sản phẩm Osmo Pocket
		List<Product> pocketProducts = List.of(
				// Osmo Pocket 1
				new Product("op0001", "Osmo Pocket 1", "Máy quay cầm tay nhỏ gọn, tích hợp gimbal chống rung",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat005").orElse(null),
						new Date(), null, "active", "/img/osmopocket1.jpg",
						productTypeRepository.findById("bn005").orElse(null)), // Bản đơn

				// Osmo Pocket 1 - Combo
				new Product("op0002", "Osmo Pocket 1 Combo", "Phiên bản combo với đầy đủ phụ kiện đi kèm",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat005").orElse(null),
						new Date(), null, "active", "/img/osmopocket1.jpg",
						productTypeRepository.findById("bn006").orElse(null)), // Bản combo

				// Osmo Pocket 2
				new Product("op0003", "Osmo Pocket 2", "Phiên bản nâng cấp với khả năng quay video chất lượng cao hơn",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat005").orElse(null),
						new Date(), null, "active", "/img/osmopocket2.jpg",
						productTypeRepository.findById("bn005").orElse(null)), // Bản đơn

				// Osmo Pocket 2 - Combo
				new Product("op0004", "Osmo Pocket 2 Combo", "Phiên bản combo với các phụ kiện hỗ trợ tối đa",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat005").orElse(null),
						new Date(), null, "active", "/img/osmopocket2.jpg",
						productTypeRepository.findById("bn006").orElse(null)), // Bản combo

				// Osmo Pocket 3
				new Product("op0005", "Osmo Pocket 3", "Phiên bản mới nhất với các tính năng tiên tiến",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat005").orElse(null),
						new Date(), null, "active", "/img/osmopocket3.jpg",
						productTypeRepository.findById("bn005").orElse(null)), // Bản đơn

				// Osmo Pocket 3 - Combo
				new Product("op0006", "Osmo Pocket 3 Combo", "Phiên bản combo với phụ kiện cao cấp",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat005").orElse(null),
						new Date(), null, "active", "/img/osmopocket3.jpg",
						productTypeRepository.findById("bn006").orElse(null)) // Bản combo
		);

		// Lưu sản phẩm vào cơ sở dữ liệu
		productRepository.saveAll(pocketProducts);
	}

	@Test
	@Rollback(false)
	public void createDetailedPocketProducts() {
		// Danh sách mã sản phẩm Osmo Pocket cần tạo DetailedProduct
		List<String> pocketProductCodes = Arrays.asList(
				"op0001", "op0002", "op0003", "op0004", "op0005", "op0006"
		);

		for (String code : pocketProductCodes) {
			// Tìm sản phẩm từ mã code
			productRepository.findByProductCode(code).ifPresentOrElse(
					product -> {
						// Nếu tìm thấy sản phẩm, tạo DetailedProduct
						DetailedProduct detailedProduct = DetailedProduct.builder()
								.detailedProductCode("dt" + code) // Tạo mã chi tiết duy nhất
								.product(product) // Gán sản phẩm vào chi tiết sản phẩm
								.description("Chi tiết sản phẩm Osmo Pocket: gọn nhẹ, chống rung và quay video chất lượng cao.") // Mô tả chi tiết
								.quantity(50) // Số lượng mặc định
								.priceVND(BigDecimal.valueOf(7000000)) // Giá mặc định
								.date(new Date()) // Ngày tạo
								.build();

						// Lưu vào cơ sở dữ liệu
						detailedProductRepository.save(detailedProduct);
					},
					() -> {
						// Nếu không tìm thấy sản phẩm
						System.out.println("Không tìm thấy sản phẩm với mã " + code);
					}
			);
		}
	}

	@Test
	@Rollback(false)
	public void testAddPhantomProducts() {
		// Thêm các sản phẩm DJI Phantom
		List<Product> phantomProducts = List.of(
				// Phantom 1 - Đơn
				new Product("pt0001", "Phantom 1", "Dòng drone đầu tiên của DJI, dễ sử dụng và ổn định.",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat010").orElse(null),
						new Date(), null, "active", "/img/phantom1.jpg",
						productTypeRepository.findById("bn001").orElse(null)), // Bản đơn

				// Phantom 1 - Combo
				new Product("pt0002", "Phantom 1 Combo", "Phiên bản combo với đầy đủ phụ kiện hỗ trợ người dùng.",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat010").orElse(null),
						new Date(), null, "active", "/img/phantom1.jpg",
						productTypeRepository.findById("bn002").orElse(null)), // Bản combo

				// Phantom 2 - Đơn
				new Product("pt0003", "Phantom 2", "Drone với thời lượng bay dài hơn và tính năng cải tiến.",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat010").orElse(null),
						new Date(), null, "active", "/img/phantom2.jpg",
						productTypeRepository.findById("bn001").orElse(null)), // Bản đơn

				// Phantom 2 - Combo
				new Product("pt0004", "Phantom 2 Combo", "Phiên bản combo với đầy đủ phụ kiện cho người dùng chuyên nghiệp.",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat010").orElse(null),
						new Date(), null, "active", "/img/phantom2.jpg",
						productTypeRepository.findById("bn002").orElse(null)), // Bản combo

				// Phantom 3 - Đơn
				new Product("pt0005", "Phantom 3", "Dòng drone với camera cải tiến và khả năng quay video 4K.",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat010").orElse(null),
						new Date(), null, "active", "/img/phantom3.jpg",
						productTypeRepository.findById("bn001").orElse(null)), // Bản đơn

				// Phantom 3 - Combo
				new Product("pt0006", "Phantom 3 Combo", "Phiên bản combo với đầy đủ phụ kiện chuyên nghiệp.",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat010").orElse(null),
						new Date(), null, "active", "/img/phantom3.jpg",
						productTypeRepository.findById("bn002").orElse(null)), // Bản combo

				// Phantom 4 - Đơn
				new Product("pt0007", "Phantom 4", "Drone với cảm biến camera vượt trội, quay video 4K.",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat010").orElse(null),
						new Date(), null, "active", "/img/phantom4.jpg",
						productTypeRepository.findById("bn001").orElse(null)), // Bản đơn

				// Phantom 4 - Combo
				new Product("pt0008", "Phantom 4 Combo", "Phiên bản combo với cảm biến hiện đại và phụ kiện cao cấp.",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat010").orElse(null),
						new Date(), null, "active", "/img/phantom4.jpg",
						productTypeRepository.findById("bn002").orElse(null)), // Bản combo

				// Phantom 4 Pro - Đơn
				new Product("pt0009", "Phantom 4 Pro", "Phiên bản cao cấp với cảm biến 1 inch, quay video 4K HDR.",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat010").orElse(null),
						new Date(), null, "active", "/img/phantom4pros.jpg",
						productTypeRepository.findById("bn001").orElse(null)), // Bản đơn

				// Phantom 4 Pro - Combo
				new Product("pt0010", "Phantom 4 Pro Combo", "Phiên bản combo đầy đủ phụ kiện chuyên nghiệp.",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat010").orElse(null),
						new Date(), null, "active", "/img/phantom4pros.jpg",
						productTypeRepository.findById("bn002").orElse(null)) // Bản combo
		);

		// Lưu các sản phẩm vào cơ sở dữ liệu
		productRepository.saveAll(phantomProducts);
	}
	@Test
	@Rollback(false)
	public void createDetailedPhantomProducts() {
		// Danh sách mã sản phẩm DJI Phantom cần tạo DetailedProduct
		List<String> phantomProductCodes = Arrays.asList(
				"pt0001", "pt0002", "pt0003", "pt0004", "pt0005",
				"pt0006", "pt0007", "pt0008", "pt0009", "pt0010"
		);

		for (String code : phantomProductCodes) {
			// Tìm sản phẩm từ mã code
			productRepository.findByProductCode(code).ifPresentOrElse(
					product -> {
						// Nếu tìm thấy sản phẩm, tạo DetailedProduct
						DetailedProduct detailedProduct = DetailedProduct.builder()
								.detailedProductCode("dt" + code) // Tạo mã chi tiết duy nhất
								.product(product) // Gán sản phẩm vào chi tiết sản phẩm
								.description("Chi tiết sản phẩm DJI Phantom: công nghệ tiên tiến, camera ổn định và khả năng bay xa.") // Mô tả chi tiết
								.quantity(30) // Số lượng mặc định
								.priceVND(BigDecimal.valueOf(15000000)) // Giá mặc định
								.date(new Date()) // Ngày tạo
								.build();

						// Lưu vào cơ sở dữ liệu
						detailedProductRepository.save(detailedProduct);
					},
					() -> {
						// Nếu không tìm thấy sản phẩm
						System.out.println("Không tìm thấy sản phẩm với mã " + code);
					}
			);
		}
	}
	@Test
	@Rollback(false)
	public void testAddOsmoMobileProducts() {
		// Thêm các sản phẩm Osmo Mobile
		List<Product> osmoMobileProducts = List.of(
				// Osmo Mobile 4 - Đơn
				new Product("omm0001", "Osmo Mobile 4", "Gimbal chống rung dành cho điện thoại, dễ sử dụng và di động.",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat008").orElse(null),
						new Date(), null, "active", "/img/osmo_mobile.jpg",
						productTypeRepository.findById("bn005").orElse(null)), // Bản đơn

				// Osmo Mobile 4 - Combo
				new Product("omm0002", "Osmo Mobile 4 Combo", "Phiên bản combo với đầy đủ phụ kiện.",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat008").orElse(null),
						new Date(), null, "active", "/img/osmo_mobile.jpg",
						productTypeRepository.findById("bn006").orElse(null)), // Bản combo

				// Osmo Mobile 6 - Đơn
				new Product("omm0003", "Osmo Mobile 6", "Gimbal chống rung với nhiều tính năng cải tiến cho quay video di động.",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat008").orElse(null),
						new Date(), null, "active", "/img/osmo_mobile6.jpg",
						productTypeRepository.findById("bn005").orElse(null)), // Bản đơn

				// Osmo Mobile 6 - Combo
				new Product("omm0004", "Osmo Mobile 6 Combo", "Phiên bản combo đầy đủ phụ kiện.",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat008").orElse(null),
						new Date(), null, "active", "/img/osmo_mobile6.jpg",
						productTypeRepository.findById("bn006").orElse(null)), // Bản combo

				// Osmo Mobile 2 SE - Đơn
				new Product("omm0005", "Osmo Mobile 2 SE", "Gimbal chống rung gọn nhẹ, hỗ trợ quay video ổn định.",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat008").orElse(null),
						new Date(), null, "active", "/img/osmo_mobile.jpg",
						productTypeRepository.findById("bn005").orElse(null)), // Bản đơn

				// Osmo Mobile 2 SE - Combo
				new Product("omm0006", "Osmo Mobile 2 SE Combo", "Phiên bản combo với đầy đủ phụ kiện hỗ trợ quay video.",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat008").orElse(null),
						new Date(), null, "active", "/img/osmo_mobile.jpg",
						productTypeRepository.findById("bn006").orElse(null)) // Bản combo
		);

		// Lưu các sản phẩm vào cơ sở dữ liệu
		productRepository.saveAll(osmoMobileProducts);
	}

	@Test
	@Rollback(false)
	public void createDetailedOsmoMobileProducts() {
		// Danh sách mã sản phẩm Osmo Mobile cần tạo DetailedProduct
		List<String> osmoMobileProductCodes = Arrays.asList(
				"omm0001", "omm0002", "omm0003", "omm0004", "omm0005", "omm0006"
		);

		for (String code : osmoMobileProductCodes) {
			// Tìm sản phẩm từ mã code
			productRepository.findByProductCode(code).ifPresentOrElse(
					product -> {
						// Nếu tìm thấy sản phẩm, tạo DetailedProduct
						DetailedProduct detailedProduct = DetailedProduct.builder()
								.detailedProductCode("dt" + code) // Tạo mã chi tiết duy nhất
								.product(product) // Gán sản phẩm vào chi tiết sản phẩm
								.description("Chi tiết sản phẩm Osmo Mobile: gimbal chống rung, hỗ trợ quay video ổn định.") // Mô tả chi tiết
								.quantity(20) // Số lượng mặc định
								.priceVND(BigDecimal.valueOf(3000000)) // Giá mặc định
								.date(new Date()) // Ngày tạo
								.build();

						// Lưu vào cơ sở dữ liệu
						detailedProductRepository.save(detailedProduct);
					},
					() -> {
						// Nếu không tìm thấy sản phẩm
						System.out.println("Không tìm thấy sản phẩm với mã " + code);
					}
			);
		}
	}
	@Test
	@Rollback(false)
	public void testAddOsmoActionProducts() {
		// Thêm các sản phẩm Osmo Action
		List<Product> osmoActionProducts = List.of(
				// Osmo Action 1 - Đơn
				new Product("om0001", "Osmo Action 1", "Máy quay hành động bền bỉ, chất lượng video 4K.",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat006").orElse(null),
						new Date(), null, "active", "/img/osmo_action1s.jpg",
						productTypeRepository.findById("bn005").orElse(null)), // Bản đơn

				// Osmo Action 1 - Combo
				new Product("om0002", "Osmo Action 1 Combo", "Máy quay hành động với đầy đủ phụ kiện hỗ trợ quay video.",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat006").orElse(null),
						new Date(), null, "active", "/img/osmo_action1s.jpg",
						productTypeRepository.findById("bn006").orElse(null)), // Bản combo

				// Osmo Action 2 - Đơn
				new Product("om0003", "Osmo Action 2", "Máy quay hành động nhỏ gọn, cải tiến hiệu năng quay video.",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat006").orElse(null),
						new Date(), null, "active", "/img/osmo_action2s.jpg",
						productTypeRepository.findById("bn005").orElse(null)), // Bản đơn

				// Osmo Action 2 - Combo
				new Product("om0004", "Osmo Action 2 Combo", "Phiên bản combo với nhiều phụ kiện cho chuyến hành trình của bạn.",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat006").orElse(null),
						new Date(), null, "active", "/img/osmo_action2s.jpg",
						productTypeRepository.findById("bn006").orElse(null)), // Bản combo

				// Osmo Action 3 - Đơn
				new Product("om0005", "Osmo Action 3", "Máy quay hành động với công nghệ chống rung hiện đại.",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat006").orElse(null),
						new Date(), null, "active", "/img/osmo_action3s.jpg",
						productTypeRepository.findById("bn005").orElse(null)), // Bản đơn

				// Osmo Action 3 - Combo
				new Product("om0006", "Osmo Action 3 Combo", "Bản combo với nhiều phụ kiện mở rộng, quay video chuyên nghiệp.",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat006").orElse(null),
						new Date(), null, "active", "/img/osmo_action3s.jpg",
						productTypeRepository.findById("bn006").orElse(null)), // Bản combo

				// Osmo Action 4 - Đơn
				new Product("om0007", "Osmo Action 4", "Máy quay hành động với hiệu năng cao, quay video HDR.",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat006").orElse(null),
						new Date(), null, "active", "/img/osmo_action4s.jpg",
						productTypeRepository.findById("bn005").orElse(null)), // Bản đơn

				// Osmo Action 4 - Combo
				new Product("om0008", "Osmo Action 4 Combo", "Bản combo đầy đủ phụ kiện cho nhu cầu quay video chuyên nghiệp.",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat006").orElse(null),
						new Date(), null, "active", "/img/osmo_action4s.jpg",
						productTypeRepository.findById("bn006").orElse(null)), // Bản combo

				// Osmo Action 4 Pro - Đơn
				new Product("om0009", "Osmo Action 4 Pro", "Máy quay hành động cao cấp, hỗ trợ quay video 8K.",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat006").orElse(null),
						new Date(), null, "active", "/img/osmo_action4s.jpg",
						productTypeRepository.findById("bn005").orElse(null)), // Bản đơn

				// Osmo Action 4 Pro - Combo
				new Product("om0010", "Osmo Action 4 Pro Combo", "Combo đầy đủ phụ kiện cho quay video chất lượng cao.",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat006").orElse(null),
						new Date(), null, "active", "/img/osmo_action4s.jpg",
						productTypeRepository.findById("bn006").orElse(null)), // Bản combo

				// Osmo Action 5 - Đơn
				new Product("om0011", "Osmo Action 5", "Phiên bản nâng cấp với khả năng chống nước vượt trội.",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat006").orElse(null),
						new Date(), null, "active", "/img/osmo_action5.jpg",
						productTypeRepository.findById("bn005").orElse(null)), // Bản đơn

				// Osmo Action 5 - Combo
				new Product("om0012", "Osmo Action 5 Combo", "Combo đầy đủ cho quay phim hành trình mọi điều kiện.",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat006").orElse(null),
						new Date(), null, "active", "/img/osmo_action5.jpg",
						productTypeRepository.findById("bn006").orElse(null)), // Bản combo

				// Osmo Action 5 Pro - Đơn
				new Product("om0013", "Osmo Action 5 Pro", "Máy quay hành động đỉnh cao, tích hợp AI quay tự động.",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat006").orElse(null),
						new Date(), null, "active", "/img/osmo_action5.jpg",
						productTypeRepository.findById("bn005").orElse(null)), // Bản đơn

				// Osmo Action 5 Pro - Combo
				new Product("om0014", "Osmo Action 5 Pro Combo", "Combo với đầy đủ phụ kiện quay phim chuyên nghiệp.",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat006").orElse(null),
						new Date(), null, "active", "/img/osmo_action5.jpg",
						productTypeRepository.findById("bn006").orElse(null)) // Bản combo
		);

		// Lưu các sản phẩm vào cơ sở dữ liệu
		productRepository.saveAll(osmoActionProducts);
	}

	@Test
	@Rollback(false)
	public void createDetailedOsmoActionProducts() {
		// Danh sách mã sản phẩm Osmo Action cần tạo DetailedProduct
		List<String> osmoActionProductCodes = Arrays.asList(
				"om0001", "om0002", "om0003", "om0004", "om0005", "om0006",
				"om0007", "om0008", "om0009", "om0010", "om0011", "om0012",
				"om0013", "om0014"
		);

		for (String code : osmoActionProductCodes) {
			// Tìm sản phẩm từ mã code
			productRepository.findByProductCode(code).ifPresentOrElse(
					product -> {
						// Nếu tìm thấy sản phẩm, tạo DetailedProduct
						DetailedProduct detailedProduct = DetailedProduct.builder()
								.detailedProductCode("dt" + code) // Tạo mã chi tiết duy nhất
								.product(product) // Gán sản phẩm vào chi tiết sản phẩm
								.description("Chi tiết sản phẩm Osmo Action với công nghệ quay video tiên tiến.") // Mô tả chi tiết
								.quantity(50) // Số lượng mặc định
								.priceVND(BigDecimal.valueOf(4000000)) // Giá mặc định
								.date(new Date()) // Ngày tạo
								.build();

						// Lưu vào cơ sở dữ liệu
						detailedProductRepository.save(detailedProduct);
					},
					() -> {
						// Nếu không tìm thấy sản phẩm
						System.err.println("Không tìm thấy sản phẩm mã: " + code);
					}
			);
		}
	}

	@Test
	@Rollback(false)
	public void testAddDJIMicProducts() {
		// Thêm các sản phẩm DJI Mic
		List<Product> djiMicProducts = List.of(
				// DJI Mic 1 - Bản đơn
				new Product("mic0001", "DJI Mic 1", "Micro không dây chất lượng cao cho ghi âm chuyên nghiệp.",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat010").orElse(null),
						new Date(), null, "active", "/img/dji_mic1.jpg",
						productTypeRepository.findById("bn005").orElse(null)), // Bản đơn

				// DJI Mic 1 - Bản combo
				new Product("mic0002", "DJI Mic 1 Combo", "Micro không dây với đầy đủ phụ kiện hỗ trợ ghi âm.",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat010").orElse(null),
						new Date(), null, "active", "/img/dji_mic1.jpg",
						productTypeRepository.findById("bn006").orElse(null)), // Bản combo

				// DJI Mic 2 - Bản đơn
				new Product("mic0003", "DJI Mic 2", "Micro thế hệ mới với công nghệ chống ồn tiên tiến.",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat010").orElse(null),
						new Date(), null, "active", "/img/dji_mic2.jpg",
						productTypeRepository.findById("bn005").orElse(null)), // Bản đơn

				// DJI Mic 2 - Bản combo
				new Product("mic0004", "DJI Mic 2 Combo", "Micro thế hệ mới với đầy đủ phụ kiện hỗ trợ ghi âm.",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat010").orElse(null),
						new Date(), null, "active", "/img/dji_mic2.jpg",
						productTypeRepository.findById("bn006").orElse(null)), // Bản combo

				// DJI Mic Mini - Bản đơn
				new Product("mic0005", "DJI Mic Mini", "Phiên bản nhỏ gọn dành cho người dùng di động.",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat010").orElse(null),
						new Date(), null, "active", "/img/dji_mic_mini.jpg",
						productTypeRepository.findById("bn005").orElse(null)), // Bản đơn

				// DJI Mic Mini - Bản combo
				new Product("mic0006", "DJI Mic Mini Combo", "Phiên bản nhỏ gọn với đầy đủ phụ kiện hỗ trợ.",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat010").orElse(null),
						new Date(), null, "active", "/img/dji_mic_mini.jpg",
						productTypeRepository.findById("bn006").orElse(null)) // Bản combo
		);

		// Lưu các sản phẩm vào cơ sở dữ liệu
		productRepository.saveAll(djiMicProducts);
	}
	@Test
	@Rollback(false)
	public void createDetailedDJIMicProducts() {
		// Danh sách mã sản phẩm DJI Mic cần tạo DetailedProduct
		List<String> djiMicProductCodes = Arrays.asList(
				"mic0001", "mic0002", "mic0003", "mic0004", "mic0005", "mic0006"
		);

		for (String code : djiMicProductCodes) {
			// Tìm sản phẩm từ mã code
			productRepository.findByProductCode(code).ifPresentOrElse(
					product -> {
						// Nếu tìm thấy sản phẩm, tạo DetailedProduct
						DetailedProduct detailedProduct = DetailedProduct.builder()
								.detailedProductCode("dt" + code) // Tạo mã chi tiết duy nhất
								.product(product) // Gán sản phẩm vào chi tiết sản phẩm
								.description("Chi tiết sản phẩm DJI Mic với công nghệ ghi âm không dây hiện đại.") // Mô tả chi tiết
								.quantity(50) // Số lượng mặc định
								.priceVND(BigDecimal.valueOf(7000000)) // Giá mặc định
								.date(new Date()) // Ngày tạo
								.build();

						// Lưu vào cơ sở dữ liệu
						detailedProductRepository.save(detailedProduct);
					},
					() -> {
						// Nếu không tìm thấy sản phẩm
						System.err.println("Không tìm thấy sản phẩm mã: " + code);
					}
			);
		}
	}



	@Test
	@Rollback(false)
	public void testAddDJIInspireProducts() {
		// Thêm các sản phẩm DJI Inspire
		List<Product> djiInspireProducts = List.of(
				// DJI Inspire 1 - Đơn
				new Product("ins0001", "DJI Inspire 1", "Máy bay quay phim chuyên nghiệp thế hệ đầu tiên.",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat003").orElse(null),
						new Date(), null, "active", "/img/inspire1.jpg",
						productTypeRepository.findById("bn001").orElse(null)), // Bản đơn

				// DJI Inspire 1 - Combo
				new Product("ins0002", "DJI Inspire 1 Combo", "Máy bay với đầy đủ phụ kiện hỗ trợ quay phim chuyên nghiệp.",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat003").orElse(null),
						new Date(), null, "active", "/img/inspire1.jpg",
						productTypeRepository.findById("bn002").orElse(null)), // Bản combo

				// DJI Inspire 2 - Đơn
				new Product("ins0003", "DJI Inspire 2", "Phiên bản nâng cấp với hiệu suất quay phim và bay tốt hơn.",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat003").orElse(null),
						new Date(), null, "active", "/img/inspire2s.jpg",
						productTypeRepository.findById("bn001").orElse(null)), // Bản đơn

				// DJI Inspire 2 - Combo
				new Product("ins0004", "DJI Inspire 2 Combo", "Bản combo với đầy đủ phụ kiện cho quay phim chuyên nghiệp.",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat003").orElse(null),
						new Date(), null, "active", "/img/inspire2s.jpg",
						productTypeRepository.findById("bn002").orElse(null)), // Bản combo

				// DJI Inspire 3 - Đơn
				new Product("ins0005", "DJI Inspire 3", "Phiên bản cao cấp nhất, tích hợp công nghệ quay phim 8K.",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat003").orElse(null),
						new Date(), null, "active", "/img/inspire3.jpg",
						productTypeRepository.findById("bn001").orElse(null)), // Bản đơn

				// DJI Inspire 3 - Combo
				new Product("ins0006", "DJI Inspire 3 Combo", "Bản combo chuyên dụng cho quay phim và chụp ảnh chuyên nghiệp.",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat003").orElse(null),
						new Date(), null, "active", "/img/inspire3.jpg",
						productTypeRepository.findById("bn002").orElse(null)) // Bản combo
		);

		// Lưu các sản phẩm vào cơ sở dữ liệu
		productRepository.saveAll(djiInspireProducts);
	}
	@Test
	@Rollback(false)
	public void createDetailedDJIMicProductsin() {
		// Danh sách mã sản phẩm DJI Mic cần tạo DetailedProduct
		List<String> djiMicProductCodes = Arrays.asList(
				"ins0001", "ins0002", "ins0003", "ins0004", "ins0005", "ins0006"
		);

		for (String code : djiMicProductCodes) {
			// Tìm sản phẩm từ mã code
			productRepository.findByProductCode(code).ifPresentOrElse(
					product -> {
						// Nếu tìm thấy sản phẩm, tạo DetailedProduct
						DetailedProduct detailedProduct = DetailedProduct.builder()
								.detailedProductCode("dt" + code) // Tạo mã chi tiết duy nhất
								.product(product) // Gán sản phẩm vào chi tiết sản phẩm
								.description("Chi tiết sản phẩm DJI insp bứt phá.") // Mô tả chi tiết
								.quantity(50) // Số lượng mặc định
								.priceVND(BigDecimal.valueOf(7000000)) // Giá mặc định
								.date(new Date()) // Ngày tạo
								.build();

						// Lưu vào cơ sở dữ liệu
						detailedProductRepository.save(detailedProduct);
					},
					() -> {
						// Nếu không tìm thấy sản phẩm
						System.err.println("Không tìm thấy sản phẩm mã: " + code);
					}
			);
		}
	}

	@Test
	@Rollback(false)
	public void testAddDJIAgrasProducts() {
		// Thêm các sản phẩm DJI Agras
		List<Product> djiAgrasProducts = List.of(
				// DJI Agras T40 - Đơn
				new Product("mas0001", "DJI Agras T40", "Máy bay phun thuốc nông nghiệp tiên tiến, hiệu suất cao.",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat010").orElse(null),
						new Date(), null, "active", "/img/agrast40.jpg",
						productTypeRepository.findById("bn001").orElse(null)), // Bản đơn

				// DJI Agras T40 - Combo
				new Product("mas0002", "DJI Agras T40 Combo", "Bản combo với đầy đủ phụ kiện hỗ trợ phun thuốc nông nghiệp.",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat010").orElse(null),
						new Date(), null, "active", "/img/agrast40.jpg",
						productTypeRepository.findById("bn002").orElse(null)), // Bản combo

				// DJI Agras T50 - Đơn
				new Product("mas0003", "DJI Agras T50", "Phiên bản nâng cấp với công nghệ phun thuốc chính xác hơn.",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat010").orElse(null),
						new Date(), null, "active", "/img/agrast50.jpg",
						productTypeRepository.findById("bn001").orElse(null)), // Bản đơn

				// DJI Agras T50 - Combo
				new Product("mas0004", "DJI Agras T50 Combo", "Bản combo với đầy đủ phụ kiện cho công tác phun thuốc.",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat010").orElse(null),
						new Date(), null, "active", "/img/agrast50.jpg",
						productTypeRepository.findById("bn002").orElse(null)), // Bản combo

				// DJI Agras T30 - Đơn
				new Product("mas0005", "DJI Agras T30", "Máy bay phun thuốc hiệu quả với khả năng chứa nhiều thuốc.",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat010").orElse(null),
						new Date(), null, "active", "/img/agrast30.jpg",
						productTypeRepository.findById("bn001").orElse(null)), // Bản đơn

				// DJI Agras T30 - Combo
				new Product("mas0006", "DJI Agras T30 Combo", "Bản combo với các phụ kiện hỗ trợ phun thuốc và lưu trữ thuốc.",
						manufacturerRepository.findById("ma001").orElse(null),
						categoryRepository.findById("cat010").orElse(null),
						new Date(), null, "active", "/img/agrast30.jpg",
						productTypeRepository.findById("bn002").orElse(null)) // Bản combo
		);

		// Lưu các sản phẩm vào cơ sở dữ liệu
		productRepository.saveAll(djiAgrasProducts);
	}

	@Test
	@Rollback(false)
	public void createDetailedDJIMicProductsins() {
		// Danh sách mã sản phẩm DJI Mic cần tạo DetailedProduct
		List<String> djiMicProductCodes = Arrays.asList(
				"mas0001", "mas0002", "mas0003", "mas0004", "mas0005", "mas0006"
		);

		for (String code : djiMicProductCodes) {
			// Tìm sản phẩm từ mã code
			productRepository.findByProductCode(code).ifPresentOrElse(
					product -> {
						// Nếu tìm thấy sản phẩm, tạo DetailedProduct
						DetailedProduct detailedProduct = DetailedProduct.builder()
								.detailedProductCode("dt" + code) // Tạo mã chi tiết duy nhất
								.product(product) // Gán sản phẩm vào chi tiết sản phẩm
								.description("máy bay nông nghiệp phục vụ ba con nông dân promax") // Mô tả chi tiết
								.quantity(50) // Số lượng mặc định
								.priceVND(BigDecimal.valueOf(7000000)) // Giá mặc định
								.date(new Date()) // Ngày tạo
								.build();

						// Lưu vào cơ sở dữ liệu
						detailedProductRepository.save(detailedProduct);
					},
					() -> {
						// Nếu không tìm thấy sản phẩm
						System.err.println("Không tìm thấy sản phẩm mã: " + code);
					}
			);
		}
	}





}