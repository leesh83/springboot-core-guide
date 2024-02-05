package kevin.springboot.core.guide.repository;

import kevin.springboot.core.guide.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsById(Long id);

    long countByName(String name);

    void deleteById(Long id);

    long removeByName(String name); //삭제한 row수 리턴

    List<Product> findTop10ByName(String name);

    Product findByIdIsNot(Long id);

    Product findByisActiveIsTrue();

    Product findByisActiveIsFalse();

    Product findByIdAndName(Long id, String name);

    Product findByIdOrName(Long id, String name);

    List<Product> findByPriceGreaterThan(Long price);

    List<Product> findByPriceGreaterThanEqual(Long price);

    List<Product> findByPriceLessThan(Long price);

    List<Product> findByPriceLessThanEqual(Long price);

    List<Product> findByPriceBetween(Long lowPrice, Long highPrice);

    List<Product> findByNameLike(String name);

    List<Product> findByNameContains(String name);

    List<Product> findByNameStartsWith(String name);

    List<Product> findByNameEndsWith(String name);

    List<Product> findByNameOrderById(String name);

    List<Product> findByNameOrderByIdDesc(String name);

}
