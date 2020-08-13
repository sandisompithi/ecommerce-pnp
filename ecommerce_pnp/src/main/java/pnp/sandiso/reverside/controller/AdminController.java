package pnp.sandiso.reverside.controller;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pnp.sandiso.reverside.model.Category;
import pnp.sandiso.reverside.model.Product;
import pnp.sandiso.reverside.model.Response;
import pnp.sandiso.reverside.service.CategoryService;
import pnp.sandiso.reverside.service.ProductService;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowedHeaders = "*")
@RestController
@RequestMapping("api/admin")
public class AdminController {
	
	@Autowired 
	private ProductService productService;
	
	@Autowired
	private final CategoryService categoryService;
	
	@Autowired
	public AdminController(ProductService productService, CategoryService categoryService) {
		this.productService = productService;
		this.categoryService = categoryService;
	}
	
	@PostMapping("/add/category")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response> postCategory(@RequestBody Category category) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new Response(categoryService.save(category), new Date()));
	}

	@PostMapping("/add/product")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response> postProduct(@RequestBody Product product) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new Response(productService.save(product), new Date()));
	}

	@PutMapping("/update/product/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response> putProduct(@PathVariable(value = "id") Integer productId,
											   @Valid @RequestBody Product product) throws Exception {
		Product product1 = productService.geProduct(productId);

		product1.setDescription(product.getDescription());
		product1.setPrice(product.getPrice());

		final Product updatedProduct = productService.save(product1);

		return ResponseEntity.status(HttpStatus.OK).body(new Response(updatedProduct, new Date()));
	}

	@DeleteMapping(value = "/delete/product/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response> deleteProduct(@PathVariable(value = "id") Integer id) throws Exception {
		productService.delete(id);
		return ResponseEntity.status(HttpStatus.OK).body(new Response(true, new Date()));
	}

}
