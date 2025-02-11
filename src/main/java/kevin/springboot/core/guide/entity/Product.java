package kevin.springboot.core.guide.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
//@Table(name = "product") //엔티티명과 테이블명이 다를경우 명시
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(50) NOT NULL comment '상품명'")
    private String name;

    @Column(columnDefinition = "INT NOT NULL DEFAULT 0 COMMENT '상품 가격'")
    private Integer price;

    @Column(columnDefinition = "INT NOT NULL DEFAULT 0 COMMENT '상품 재고'")
    private Integer stock;

    @Column(columnDefinition = "TINYINT NOT NULL DEFAULT 1 COMMENT '유효 상품 여부'")
    private Boolean isActive;

    protected Product() {
    }

    @Builder
    public Product(Long id, String name, Integer price, Integer stock, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.isActive = isActive;
    }

    public void updateProduct(String name, Integer price, Integer stock, Boolean isActive){
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.isActive = isActive;
    }
}
