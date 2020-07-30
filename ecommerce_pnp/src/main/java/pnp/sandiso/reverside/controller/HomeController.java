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

	@GetMapping("/mod")
	@PreAuthorize("hasRole('MODERATOR')")
	public String moderatorAccess() {
		return "Moderator Board.";
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

	@PostMapping("/admin/add/category")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response> postCategory(@RequestBody Category category) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new Response(categoryService.save(category), new Date()));
	}

	@PostMapping("/admin/add/product")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response> postProduct(@RequestBody Product product) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new Response(productService.save(product), new Date()));
	}

	@PutMapping("/admin/update/product/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response> putProduct(@PathVariable(value = "id") Integer productId,
											   @Valid @RequestBody Product product) throws Exception {
		Product product1 = productService.geProduct(productId);

		product1.setDescription(product.getDescription());
		product1.setPrice(product.getPrice());

		final Product updatedProduct = productService.save(product1);

		return ResponseEntity.status(HttpStatus.OK).body(new Response(updatedProduct, new Date()));
	}

	@DeleteMapping(value = "/admin/delete/product/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response> deleteProduct(@PathVariable(value = "id") Integer id) throws Exception {
		productService.delete(id);
		return ResponseEntity.status(HttpStatus.OK).body(new Response(true, new Date()));
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
