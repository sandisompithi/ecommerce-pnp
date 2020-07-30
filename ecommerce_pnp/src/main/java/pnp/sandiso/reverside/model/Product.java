package pnp.sandiso.reverside.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Arrays;

@Entity
public class Product {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

	@ManyToOne()
	@JoinColumn(name = "category_id")
	@JsonIgnoreProperties(value = "product")
	private Category category;

	private Byte[] image;
	private String description;
	private float price;
	
	public Product() {}

	public Product(Category category, Byte[] image, String description, float price) {
		this.category = category;
		this.image = image;
		this.description = description;
		this.price = price;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Byte[] getImage() {
		return image;
	}

	public void setImage(Byte[] image) {
		this.image = image;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Product{" +
				"id=" + id +
				", category=" + category +
				", image=" + Arrays.toString(image) +
				", description='" + description + '\'' +
				", price=" + price +
				'}';
	}
}
