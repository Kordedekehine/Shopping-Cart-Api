package ShoppingCartsAPI.carts.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

//each category of the goods and services

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(name = "category_name")
    private  String categoryName;


    private String description;


    private  String imageUrl;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
   private Set<Product> products;

    public Category() {
    }

    public Category( String categoryName,  String description) {
        this.categoryName = categoryName;
        this.description = description;
    }

    public Category( String categoryName,String description, String imageUrl) {
        this.categoryName = categoryName;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User {category id=" + id + "," +
                " category name='" + categoryName + "'," +
                " description='" + description + "'}";
    }
}
