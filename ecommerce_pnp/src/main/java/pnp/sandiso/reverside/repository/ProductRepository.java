package pnp.sandiso.reverside.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pnp.sandiso.reverside.model.Category;
import pnp.sandiso.reverside.model.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAllByCategory(Category category);
}
