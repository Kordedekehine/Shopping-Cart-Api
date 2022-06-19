package ShoppingCartsAPI.carts.services;

import ShoppingCartsAPI.carts.model.Category;
import ShoppingCartsAPI.carts.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CategoryService {

    @Autowired
     CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> listCategories(){
        return categoryRepository.findAll();
    }

    public void createCategory(Category category){
        categoryRepository.save(category);
    }

   public void findCategory(String categoryName){
        categoryRepository.findByCategoryName(categoryName);
   }

   public void updateCategory(Integer categoryID,Category newCategory){
       Category category = categoryRepository.findById(categoryID).get();
       category.setCategoryName(newCategory.getCategoryName());
       category.setDescription(newCategory.getDescription());
       category.setProducts(newCategory.getProducts());
       category.setImageUrl(newCategory.getImageUrl());

       categoryRepository.save(category);
   }
}