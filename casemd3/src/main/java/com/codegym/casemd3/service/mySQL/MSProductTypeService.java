package service.mySQL;

import model.Category;
import model.ProductType;
import service.IProductTypeService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MSProductTypeService extends  DBContext implements IProductTypeService {
    private static final String SELLECT_ALL_PRODUCT_TYPE = "SELECT * FROM product_type;";
    private static final String SELLECT_PRODUCT_TYPE_BY_ID = "SELECT * FROM product_type where id = ?";
    @Override
    public List<ProductType> getAllProductType() {
        List<ProductType> productTypes = new ArrayList<>();
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELLECT_ALL_PRODUCT_TYPE);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                ProductType c = getProductTypeFromRs(rs);
                productTypes.add(c);
            }

        } catch (SQLException e) {
            printSQLException(e);
        }
        return productTypes;
    }
    private ProductType getProductTypeFromRs(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");

        ProductType productType = new ProductType(id, name);
        return productType;

    }

    @Override
    public ProductType getProductTypeById(int id) {
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELLECT_PRODUCT_TYPE_BY_ID);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                ProductType c = getProductTypeFromRs(rs);
                return c;
            }

        } catch (SQLException e) {
            printSQLException(e);
        }
        return null;
    }
}
