package kevin.springboot.core.guide.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kevin.springboot.core.guide.entity.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductRequest {

    @NotBlank(message = "상품명은 필수값 입니다.")
    @Schema(description = "상품명", example = "짜장면")
    private String name;

    @NotNull(message = "상품가격은 필수값 입니다.")
    @Schema(description = "상품가격(원)", example = "5000")
    private Integer price;

    @NotNull(message = "상품재고는 필수값 입니다.")
    @Schema(description = "상품 재고", example = "5")
    private Integer stock;

    @Builder
    public ProductRequest(String name, Integer price, Integer stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public Product toEntity() {
        return Product.builder()
                      .name(name)
                      .price(price)
                      .stock(stock)
                      .build();
    }
}
