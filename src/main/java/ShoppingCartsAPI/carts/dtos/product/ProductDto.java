package ShoppingCartsAPI.carts.dtos.product;

import ShoppingCartsAPI.carts.model.Product;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProductDto {

    private Integer id;
    @NotBlank
    private  String name;
    @NotBlank
    private String imageURL;
    @NotBlank
    private double price;
    @NotBlank
    private String description;
    @NotNull
    private  Integer categoryId;

    //one product is comprises with all this feature
    public ProductDto(Product product) {
        this.setId(getId());
        this.setCategoryId(getCategoryId());
        this.setDescription(getDescription());
        this.setName(getName());
        this.setImageURL(getImageURL());
        this.setPrice(getPrice());
    }

    public ProductDto(Integer id, String name, String imageURL, double price,
                      String description, Integer categoryId) {
        this.id = id;
        this.name = name;
        this.imageURL = imageURL;
        this.price = price;
        this.description = description;
        this.categoryId = categoryId;
    }

    public Integer getId() {
        return id;
    }

    public ProductDto() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
