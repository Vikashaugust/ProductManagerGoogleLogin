package singlesignonimpl.project.repository;

import singlesignonimpl.project.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
