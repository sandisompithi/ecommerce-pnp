package pnp.sandiso.reverside.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import pnp.sandiso.reverside.model.Category;
import pnp.sandiso.reverside.model.Product;
import pnp.sandiso.reverside.model.Response;
import pnp.sandiso.reverside.service.CategoryService;
import pnp.sandiso.reverside.service.ProductService;

import javax.validation.Valid;

import static org.apache.logging.log4j.util.Strings.isBlank;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowedHeaders = "*")
@RestController
@RequestMapping("/api/home")
public class HomeController {
	
	@Autowired 
	private ProductService productService;

	@Autowired
	private final CategoryService categoryService;

	@Autowired
	public HomeController(ProductService productService, CategoryService categoryService) {
		this.productService = productService;
		this.categoryService = categoryService;
	}

	public HomeController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping("/all")
	public List<Product> allAccess() {
		return productService.getProducts();
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public String userAccess() {
		return "User Content.";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Admin Board.";
	}

	@GetMapping("/category/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<Response> getCategoryById(@PathVariable(value = "id") Integer id) {
		Category category = categoryService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(new Response(category, new Date()));
	}

	@GetMapping("/product/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<Response> getProductById(@PathVariable(value = "id") Integer id) {
		Product product1 = productService.geProduct(id);

		return ResponseEntity.status(HttpStatus.OK).body(new Response(product1, new Date()));
	}


	@GetMapping(value = "/category")
	public ResponseEntity<Response> getAllCategories() {
		List<Category> categoryList = categoryService.findAllByOrderByName();
		return ResponseEntity.status(HttpStatus.OK).body(new Response(categoryList, new Date()));
	}

	@GetMapping(value = "/category/product")
	public ResponseEntity<Response> getAllByCategory(@RequestParam(value = "category", required = false) String category) {
		if (category != null && !isBlank(category)){
			Category prod_category = categoryService.findByName(category);
			if (prod_category == null) {
				throw new IllegalArgumentException("Invalid category parameter");
			}
			List<Product> productList = productService.findAllByCategory(prod_category);
			return ResponseEntity.status(HttpStatus.OK).body(new Response(productList, new Date()));
		}

		List<Product> productList = productService.getProducts();
		return ResponseEntity.status(HttpStatus.OK).body(new Response(productList, new Date()));
	}
}
