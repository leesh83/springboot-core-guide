package kevin.springboot.core.guide.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kevin.springboot.core.guide.dto.ProductRequest;
import kevin.springboot.core.guide.dto.ProductResponse;
import kevin.springboot.core.guide.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "상품 API", description = "상품 API 입니다.")
@RequestMapping("/product")
public class ProductApiController {

    private final ProductService productService;

    @Operation(summary = "모든 상품을 조회한다.")
    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAllProduct() {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(productService.findAllProduct());
    }

    @Operation(summary = "단일 상품을 조회한다.")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findAllProduct(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(productService.findProductById(id));
    }

    @Operation(summary = "상품을 등록한다.")
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid ProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(productService.createProduct(request));
    }

    @Operation(summary = "상품을 수정한다.")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id,
                                                         @RequestBody @Valid ProductRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(productService.updateProduct(id, request));
    }

    @Operation(summary = "상품을 삭제한다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteProduct(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(productService.deleteProduct(id));
    }


}
