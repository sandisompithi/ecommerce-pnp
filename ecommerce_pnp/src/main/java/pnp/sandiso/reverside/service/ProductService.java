package pnp.sandiso.reverside.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import pnp.sandiso.reverside.model.Category;
import pnp.sandiso.reverside.model.Product;

@Transactional
public interface ProductService {
	
	List<Product> getProducts();
	Product save(Product product);
	Product geProduct(Integer id);
	void delete(Integer id);

	List<Product> findAllByCategory(Category category);
}
