package service;

import model.Category;
import model.ProductType;

import java.util.List;

public interface IProductTypeService {
    List<ProductType> getAllProductType();

    ProductType getProductTypeById(int id);
}
