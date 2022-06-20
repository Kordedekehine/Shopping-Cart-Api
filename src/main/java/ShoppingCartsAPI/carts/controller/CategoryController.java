package ShoppingCartsAPI.carts.controller;

import ShoppingCartsAPI.carts.api.ApiResponse;
import ShoppingCartsAPI.carts.model.Category;
import ShoppingCartsAPI.carts.services.CategoryService;
import ShoppingCartsAPI.carts.utils.Checker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/getAllCategories")
    public ResponseEntity<List<Category>> getCategories(){
        List<Category> allItems = categoryService.listCategories();
        return new ResponseEntity<>(allItems, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createCategory(@Valid @RequestParam Category category){
        if (Checker.notNull(categoryService.checkCategory(category.getCategoryName()))){
            return new ResponseEntity<>(new ApiResponse(false,"categories already exists"),HttpStatus.CONFLICT);
        }
         categoryService.createCategory(category);
        return new ResponseEntity<>(new ApiResponse(true,"successfully created category"),HttpStatus.OK);
    }

    @PostMapping("/update/{categoryId}")
  public ResponseEntity<ApiResponse> updateCategory(@PathVariable ("categoryId") Integer categoryId,@Valid @RequestParam Category category){
      //check if the category exists
      if (!Checker.notNull(categoryService.checkCategoryById(categoryId))){
          return new  ResponseEntity<>(new ApiResponse(false,"Category does not exist"),HttpStatus.CONFLICT);
      }
      categoryService.updateCategory(categoryId, category);//if the category exists then update it
      return new ResponseEntity<>(new ApiResponse(true,"Updated the Category!"),HttpStatus.OK);
  }

}
