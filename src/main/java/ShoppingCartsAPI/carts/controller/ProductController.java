package ShoppingCartsAPI.carts.controller;

import ShoppingCartsAPI.carts.api.ApiResponse;
import ShoppingCartsAPI.carts.dtos.product.ProductDto;
import ShoppingCartsAPI.carts.model.Category;
import ShoppingCartsAPI.carts.repositories.ProductRepository;
import ShoppingCartsAPI.carts.services.CategoryService;
import ShoppingCartsAPI.carts.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

   @Autowired
    CategoryService categoryService; //note we are navigating through categories to the products

    @GetMapping("/getAllProducts")
    public ResponseEntity<List<ProductDto>> getProduct(){
        List<ProductDto> findProduct = productService.listAllProducts();
        return new ResponseEntity<>(findProduct, HttpStatus.OK);
    }

    @PostMapping("/addProduct")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody ProductDto productDto){
        //list all categories WE Wanna add product to  by the id
        Optional<Category> optionalCategory = categoryService.checkCategoryById(productDto.getCategoryId());
       if (!optionalCategory.isPresent()){
           return new ResponseEntity<>(new ApiResponse(false,"null category"),HttpStatus.CONFLICT);
       }
         Category category = optionalCategory.get();
       productService.addProduct(productDto,category);
       return new ResponseEntity<>(new ApiResponse(true,"product successfully added"),HttpStatus.OK);
    }

    @PostMapping("/update/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable ("ProductId") Integer productId,
                                                     @RequestBody @Valid ProductDto productDto){
    Optional<Category> optionalCategory = categoryService.checkCategoryById(productDto.getCategoryId());
    if (!optionalCategory.isPresent()){
        return new ResponseEntity<>(new ApiResponse(false,"null category"),HttpStatus.CONFLICT);
    }
      Category category = optionalCategory.get();
    productService.updateProduct(productId,productDto,category);
    return new ResponseEntity<>(new ApiResponse(true,"product successfully updated"),HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable ("ProductId") Integer productId,
    @RequestBody @Valid ProductDto productDto){
        Optional<Category> optionalCategory = categoryService.checkCategoryById(productDto.getCategoryId());
        if (!optionalCategory.isPresent()){
            return new ResponseEntity<>(new ApiResponse(false,"null category"),HttpStatus.CONFLICT);
        }
        Category category = optionalCategory.get();
        productService.deleteProduct(productId);
        return new ResponseEntity<>(new ApiResponse(true,"product successfully deleted"),HttpStatus.OK);
    }

}
