package com.example.DoAnJava.services;

import com.example.DoAnJava.DTO.CreateProductDto;
import com.example.DoAnJava.entity.Category;
import com.example.DoAnJava.entity.Product;
import com.example.DoAnJava.entity.ProductType;
import com.example.DoAnJava.repository.ICategoryRepository;
import com.example.DoAnJava.repository.IProductRepository;

import com.example.DoAnJava.repository.IProductTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private ICategoryRepository categoryRepository;

    @Autowired
    private FirebaseService firebaseService;
    @Autowired
    private IProductTypeRepository productTypeRepository;

    static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    public  List<Product> getProductsByCategory(String category){
        return productRepository.findByCategory(category);
    }
    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }
    public Product getProductById(Long id){
        Optional<Product> optional = productRepository.findById(id);
        return  optional.orElse(null);
    }


    public Product updateProduct(CreateProductDto product, Long id){
        Product productSaved = this.productRepository.findById(id).orElse(null);
        productSaved.setName(product.getName());
        productSaved.setDescription(product.getDescription());
        productSaved.setPrice(product.getPrice());
        productSaved.setUnit(product.getUnit());
        var imageThumbnail = this.firebaseService.uploadImages(product.getFile());
        System.out.println("IMAGE THUMB "+ imageThumbnail.get(0));
        productSaved.setUrlImageThumbnail(imageThumbnail.get(0));
        var imageList = this.firebaseService.uploadImages(product.getFiles());
        System.out.println("IMAGE LIST "+imageList);

        productSaved.setImageList(imageList);
        productSaved.setQuantityStock(product.getQuantityStock());
        Category cate = this.categoryRepository.findById(product.getCategory_id()).orElse(null);
        productSaved.setCategory(cate);
        ProductType productType = this.productTypeRepository.findById(product.getProduct_type_id()).orElse(null);
        productSaved.setProductType(productType);
        return productRepository.save(productSaved);
    }
    public Product saveProduct(CreateProductDto product){
        Product productSaved = new Product();
        productSaved.setName(product.getName());
        productSaved.setDescription(product.getDescription());
        productSaved.setPrice(product.getPrice());
        productSaved.setUnit(product.getUnit());
        productSaved.setUrlImageThumbnail(product.getUrlImageThumbnail());
        productSaved.setImageList(product.getImageList());
        productSaved.setQuantityStock(product.getQuantityStock());
        Category cate = this.categoryRepository.findById(product.getCategory_id()).orElse(null);
        productSaved.setCategory(cate);
        ProductType productType = this.productTypeRepository.findById(product.getProduct_type_id()).orElse(null);
        productSaved.setProductType(productType);
        return productRepository.save(productSaved);
    }
    public Product updateSoLuong(Product product, Long id){
        Product productSaved = this.productRepository.findById(id).orElse(null);
        productSaved.setName(product.getName());
        productSaved.setQuantityStock(product.getQuantityStock());
        return productRepository.save(productSaved);
    }
    public void deleteProduct(Long productId){
         productRepository.deleteById(productId);
    }
    public List<Product> searchProducts(String keyword) {
        return productRepository.searchProducts(keyword.toLowerCase());
    }
    public BigDecimal getPriceForProduct(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        return optionalProduct.map(Product::getPrice).orElse(BigDecimal.ZERO);
    }
    public String getProductNameById(Long productId) {
        // Tìm sản phẩm theo ID từ cơ sở dữ liệu
        Optional<Product> optionalProduct = productRepository.findById(productId);

        // Kiểm tra xem sản phẩm có tồn tại không
        if (optionalProduct.isPresent()) {
            // Nếu tồn tại, trả về tên sản phẩm
            return optionalProduct.get().getName();
        } else {
            // Nếu không tồn tại, có thể trả về một giá trị mặc định hoặc xử lý theo cách khác
            return "error";
        }
    }

}
