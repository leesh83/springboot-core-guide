package kevin.springboot.core.guide.repository;

import kevin.springboot.core.guide.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
