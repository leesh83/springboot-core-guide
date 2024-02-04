package kevin.springboot.core.guide.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
//@Table(name = "product") //엔티티명과 테이블명이 다를경우 명시
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(50) NOT NULL comment '상품명'")
    private String name;

    @Column(columnDefinition = "INT NOT NULL DEFAULT 0 COMMENT '상품 가격'")
    private Integer price;

    @Column(columnDefinition = "INT NOT NULL DEFAULT 0 COMMENT '상품 재고'")
    private Integer stock;

    protected Product() {
    }

    @Builder
    public Product(Long id, String name, Integer price, Integer stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public void updateProduct(String name, Integer price, Integer stock){
        this.name = name;
        this.price = price;
        this.stock = stock;
    }
}
