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

    @Column(columnDefinition = "VARCHAR(500) NOT NULL COMMENT '상품명'")
    private String name;

    @Column(columnDefinition = "INT NOT NULL COMMENT '가격(원)'")
    private Integer price;

    @Column(columnDefinition = "INT NOT NULL COMMENT '재고'")
    private Integer stock;

    @Column(columnDefinition = "TINYINT(1) NOT NULL DEFAULT 1 COMMENT '활성화여부'") // Boolean 타입과 DB -tinyint 타입간 validate 오류를 막기위해 tinyint(1) 로 지정해야함.
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

    public void updateProduct(String name, Integer price, Integer stock, Boolean isActive) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.isActive = isActive;
    }
}
