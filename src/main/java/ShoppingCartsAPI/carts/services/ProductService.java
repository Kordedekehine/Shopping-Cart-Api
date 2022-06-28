package ShoppingCartsAPI.carts.services;

import ShoppingCartsAPI.carts.dtos.product.ProductDto;
import ShoppingCartsAPI.carts.exceptions.CustomException;
import ShoppingCartsAPI.carts.exceptions.ProductNotExistException;
import ShoppingCartsAPI.carts.model.Category;
import ShoppingCartsAPI.carts.model.Product;
import ShoppingCartsAPI.carts.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public List<ProductDto> listProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = new ArrayList<>();
        for(Product product : products) {
            ProductDto productDto = getDtoFromProduct(product);
            productDtos.add(productDto);
        }
        return productDtos;
    }

    public static ProductDto getDtoFromProduct(Product product){
        ProductDto productDto = new ProductDto(product);
        return productDto;
    }

    public static Product getProductFromDto(ProductDto productDto, Category category){
    Product products = new Product(productDto,category);
    return products;
    }

    public void addProduct(ProductDto productDto,Category category){
        Product product = getProductFromDto(productDto,category);
        productRepository.save(product);
    }

    public void updateProduct(Integer productId,ProductDto productDto,Category category){
        Product product = getProductFromDto(productDto,category);
        product.setId(productId);
        productRepository.save(product);
    }

    public Product getProductById(Integer productId) throws ProductNotExistException{
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (!optionalProduct.isPresent()){
            throw new ProductNotExistException("Product id is invalid " + productId);
        }
        return optionalProduct.get();
    }

//    public void deleteProduct(Integer productId) {
//        Product delProduct = getProductById(productId);
//        if (delProduct == null){
//            throw new ProductNotExistException("Product does not exist " + productId);
//        }
//        productRepository.deleteById(delProduct);
//    }

    public void deleteProduct(Integer productId,ProductDto productDto) throws CustomException {
        Optional<Product> product = productRepository.findById(productId);

        if (product.isEmpty()){
          throw new CustomException("Product does not exist");
        }
        productRepository.deleteById(productId);
    }
}
