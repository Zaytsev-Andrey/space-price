package ru.spaceprice.telegram.storage.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.spaceprice.telegram.storage.entity.ProductCardSlider;

@Repository
public interface ProductCardSliderRepository extends CrudRepository<ProductCardSlider, String> {
}
