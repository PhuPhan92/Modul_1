package service;


import model.Product;

import java.util.List;

public interface IProductService {
    List<Product> getAllProduct();

    Product findProductById(Long id);
    void editProduct(Product Product);
    void deleteProductById(Long id);

    void createProduct(Product Product);

    boolean checkImageExists(String fileName);
    List<Product> getAllProductByCategoryId(int idCategory);
    List<Product> getAllProductByTypeId(int idType);
}
