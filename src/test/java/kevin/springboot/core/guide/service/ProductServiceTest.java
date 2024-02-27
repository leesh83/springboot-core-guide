package kevin.springboot.core.guide.service;

import kevin.springboot.core.guide.dto.ProductRequest;
import kevin.springboot.core.guide.dto.ProductResponse;
import kevin.springboot.core.guide.entity.Product;
import kevin.springboot.core.guide.exception.ProductNotFoundException;
import kevin.springboot.core.guide.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

//mock 객체 선언 방법. @ExtendWith(MockitoExtension.class) 와 @Mock 애노테이션을 이용해 mock 객체를 만들 수 있다.
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    //ProductService 에서 의존하고 있는 객체를 mock 으로 생성
    @Mock
    private ProductRepository productRepository;

    //mock 객체를 주입해준다.
    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("전체 상품 조회 검증")
    void findAllProductTest() {
        //given
        Long id = 1L;
        String name = "테스트";
        Integer price = 1500;
        Integer stock = 1500;

        Product product = createProduct(id, name, price, stock);

        given(productRepository.findAll()).willReturn(List.of(product));

        //when
        List<ProductResponse> responses = productService.findAllProduct();

        //then
        Assertions.assertEquals(responses.get(0).getId(), id);
        Assertions.assertEquals(responses.get(0).getName(), name);
        Assertions.assertEquals(responses.get(0).getPrice(), price);
        Assertions.assertEquals(responses.get(0).getStock(), stock);
    }

    @Test
    @DisplayName("단일 상품 조회 검증")
    void findProductByIdTest() {
        //given
        Long id = 1L;
        String name = "테스트";
        Integer price = 1500;
        Integer stock = 1500;

        Product product = createProduct(id, name, price, stock);

        given(productRepository.findById(id)).willReturn(Optional.of(product));

        //when
        ProductResponse response = productService.findProductById(id);

        //then
        Assertions.assertEquals(response.getId(), id);
        Assertions.assertEquals(response.getName(), name);
        Assertions.assertEquals(response.getPrice(), price);
        Assertions.assertEquals(response.getStock(), stock);
    }

    @Test
    @DisplayName("상품 등록 검증")
    void createProductTest() {
        //given
        Long id = 1L;
        String name = "테스트";
        Integer price = 1500;
        Integer stock = 1500;

        ProductRequest request = createProductRequest(name, price, stock);

        Product product = createProduct(id, name, price, stock);

        given(productRepository.save(any())).willReturn(product);

        //when
        ProductResponse response = productService.createProduct(any(), request);

        //then
        Assertions.assertEquals(response.getId(), id);
        Assertions.assertEquals(response.getName(), name);
        Assertions.assertEquals(response.getPrice(), price);
        Assertions.assertEquals(response.getStock(), stock);
    }

    @Test
    @DisplayName("상품 수정 검증")
    void updateProductTest() {
        //given
        Long id = 1L;
        String name = "테스트";
        Integer price = 1500;
        Integer stock = 1500;

        ProductRequest request = createProductRequest(name, price, stock);

        Product product = createProduct(id, name, price, stock);

        given(productRepository.findById(id)).willReturn(Optional.of(product));

        //when
        Boolean response = productService.updateProduct(id, request);

        //then
        Assertions.assertEquals(response, true);
    }

    @Test
    @DisplayName("상품 삭제 검증")
    void deleteProductTest() {
        //given
        Long id = 1L;
        String name = "테스트";
        Integer price = 1500;
        Integer stock = 1500;

        Product product = createProduct(id, name, price, stock);

        given(productRepository.findById(id)).willReturn(Optional.of(product));

        //when
        Boolean response = productService.deleteProduct(id);

        //then
        Assertions.assertEquals(response, true);
        verify(productRepository).deleteById(id);
    }

    @Test
    @DisplayName("단일 상품 조회 시 id에 해당하는 상품 없을 시 ProductNotFoundException 검증")
    void findProductByIdExceptionTest() {
        //given
        Long id = 1L;

        given(productRepository.findById(id)).willReturn(Optional.ofNullable(null));

        //when & then
        assertThrows(ProductNotFoundException.class, () -> productService.findProductById(id));
    }

    private ProductRequest createProductRequest(String name, Integer price, Integer stock) {
        return ProductRequest.builder()
                             .name(name)
                             .price(price)
                             .stock(stock)
                             .build();
    }

    private Product createProduct(Long id, String name, Integer price, Integer stock) {
        return Product.builder()
                      .id(id)
                      .name(name)
                      .price(price)
                      .stock(stock)
                      .build();
    }

}
