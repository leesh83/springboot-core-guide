package kevin.springboot.core.guide.service;

import kevin.springboot.core.guide.dto.ProductRequest;
import kevin.springboot.core.guide.dto.ProductResponse;
import kevin.springboot.core.guide.entity.Product;
import kevin.springboot.core.guide.exception.ProductNotFoundException;
import kevin.springboot.core.guide.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    //전체 상품 조회
    @Transactional(readOnly = true)
    public List<ProductResponse> findAllProduct() {
        return productRepository.findAll()
                                .stream()
                                .map(product -> ProductResponse.of(product))
                                .toList();
    }

    //특정 상품 조회
    @Transactional
    public ProductResponse findProductById(Long id) {
        Product product = findById(id);
        return ProductResponse.of(product);
    }

    //상품 입력
    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        Product product = productRepository.save(request.toEntity());
        return ProductResponse.of(product);
    }

    //상품 수정
    @Transactional
    public boolean updateProduct(Long id, ProductRequest request) {
        Product product = findById(id);
        product.updateProduct(request.getName(), request.getPrice(), request.getStock());
        return true;
    }

    //상품 삭제
    @Transactional
    public boolean deleteProduct(Long id) {
        findById(id);
        productRepository.deleteById(id);
        return true;
    }

    private Product findById(Long id) {
        Product product = productRepository.findById(id)
                                           .orElseThrow(() -> new ProductNotFoundException(id));

        return product;
    }
}
