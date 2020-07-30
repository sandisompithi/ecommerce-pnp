package pnp.sandiso.reverside.service;

import pnp.sandiso.reverside.model.Category;

import java.util.List;

public interface CategoryService {
    Category save(Category category);
    Category findById(Integer id);
    Category findByName(String name);
    List<Category> findAllByOrderByName();
}
