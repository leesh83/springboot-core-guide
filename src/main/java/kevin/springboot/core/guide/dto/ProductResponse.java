package kevin.springboot.core.guide.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kevin.springboot.core.guide.entity.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class ProductResponse {
    @Schema(description = "상품id")
    private Long id;

    @Schema(description = "상품명", example = "짜장면")
    private String name;

    @Schema(description = "상품가격(원)", example = "5000")
    private Integer price;

    @Schema(description = "상품 재고", example = "5")
    private Integer stock;

    @Schema(description = "활성화 여부")
    private Boolean isActive;

    @Schema(description = "등록 일자")
    private LocalDateTime createdAt;

    @Schema(description = "수정 일자")
    private LocalDateTime updatedAt;

    @Builder
    public ProductResponse(Long id, String name, Integer price, Integer stock, Boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Builder
    public static ProductResponse of(Product product) {
        return ProductResponse.builder()
                              .id(product.getId())
                              .name(product.getName())
                              .price(product.getPrice())
                              .stock(product.getStock())
                              .isActive(product.getIsActive())
                              .createdAt(product.getCreatedAt())
                              .updatedAt(product.getUpdatedAt())
                              .build();
    }
}
