package pnp.sandiso.reverside.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pnp.sandiso.reverside.model.Category;
import pnp.sandiso.reverside.model.Product;
import pnp.sandiso.reverside.repository.ProductRepository;
import pnp.sandiso.reverside.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepo;

	@Autowired
	public ProductServiceImpl(ProductRepository productRepo) {
		this.productRepo = productRepo;
	}

	@Override
	public List<Product> getProducts() {
		return productRepo.findAll();
	}

	@Override
	public Product save(Product product) {
		return productRepo.save(product);
	}

	@Override
	public Product geProduct(Integer id) {
		return productRepo.findById(id).get();
	}

	@Override
	public void delete(Integer id) {
		productRepo.deleteById(id);
	}

	@Override
	public List<Product> findAllByCategory(Category category) {
		return productRepo.findAllByCategory(category);
	}

}
