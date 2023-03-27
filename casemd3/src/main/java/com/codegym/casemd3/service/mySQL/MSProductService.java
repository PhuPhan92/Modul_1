package service.mySQL;

import model.Product;
import service.IProductService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MSProductService extends DBContext implements IProductService {
    private static final String SELLECT_ALL_PRODUCT = "SELECT * FROM `product`;";
    private static final String FIND_PRODUCT_BY_ID = "SELECT * FROM `product` where id = ?;";
    private static final String INSERT_PRODUCT = "INSERT INTO `product` ( `name`, `price`, `description`, `image`,`category_id`,`type_id`,`quantity`) VALUES (?,?,?,?,?,?,?)";
    private static final String DELETE_PRODUCT_BY_ID = "DELETE FROM `product` WHERE (`id` = ?);";
    private static final String EDIT_PRODUCT = "UPDATE `product` SET `name` = ?, `price` = ?, `description` = ?, `image` = ?, `category_id` = ?, `type_id` = ?, `quantity` = ? WHERE (`id` = ?);";
    private static final String CHECK_IMAGE_EXIST = "SELECT * FROM `product` where image = ?;";
    private static final String SELLECT_ALL_PRODUCT_BY_CATEGORY_ID = "SELECT * FROM `product` where category_id = ?";
    private static final String SELLECT_ALL_PRODUCT_BY_TYPE_ID = "SELECT * FROM `product` where type_id = ?";


    @Override
    public List<Product> getAllProduct() {
        List<Product> products = new ArrayList<>();
        Connection connection = getConnection();
        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(SELLECT_ALL_CUSTOMER);

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(SELLECT_ALL_PRODUCT);
            while (rs.next()) {
                Product c = getProductFromRs(rs);
                products.add(c);
            }
            connection.close();
        } catch (SQLException e) {
            printSQLException(e);
        }
        return products;
    }

    private Product getProductFromRs(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        double price = rs.getDouble("price");
        String image = rs.getString("image");
        int idCategory = rs.getInt("category_id");
        int idType = rs.getInt("type_id");
        int quantyti = rs.getInt("quantity");

        //long id, String name, double price, String description, String image, Date createAt, int idCategory
        Product product = new Product(id, name, price, description, image, idCategory,idType,quantyti);
        return product;

    }

    @Override
    public Product findProductById(Long id) {
        Connection connection = getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_PRODUCT_BY_ID);
            preparedStatement.setLong(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Product product = getProductFromRs(rs);
                return product;
            }

            connection.close();
        } catch (SQLException e) {
            printSQLException(e);
        }
        return null;
    }

    @Override
    public void editProduct(Product product) {
        Connection connection = getConnection();

        try {
            //"UPDATE `customer` SET `name` = ?, `createdat` = ?,
            // `address` = ?, `image` =?, `idcustomertype` = ? WHERE (`id` = ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(EDIT_PRODUCT);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.setString(4, product.getImage());
            preparedStatement.setInt(5, product.getIdCategory());
            preparedStatement.setInt(6, product.getIdType());
            preparedStatement.setInt(7, product.getQuantity());
            preparedStatement.setLong(8, product.getId());
            preparedStatement.executeUpdate();

            connection.close();
        } catch (SQLException sqlException) {
            printSQLException(sqlException);
        }
    }

    @Override
    public void deleteProductById(Long id) {
        Connection connection = getConnection();
        //DELETE FROM customer` WHERE (`id` = ?)
        try {
            PreparedStatement ps = connection.prepareStatement(DELETE_PRODUCT_BY_ID);
            ps.setLong(1, id);

            ps.executeUpdate();
            // DELETE FROM customer` WHERE (`id` = 3)
            System.out.println("deleteCustomerById: " + ps);
        } catch (SQLException sqlException) {
            printSQLException(sqlException);
        }

    }

    @Override
    public void createProduct(Product product) {
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT);

//           private static final String INSERT_PRODUCT = "INSERT INTO `product` ( `name`, `price`, `description`, `image`, `category_id`) VALUES (?, ?,?, ?, ? )";
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.setString(4, product.getImage());
            preparedStatement.setInt(5, product.getIdCategory());
            preparedStatement.setInt(6, product.getIdType());
            preparedStatement.setInt(7, product.getQuantity());

            preparedStatement.executeUpdate();
            System.out.println("createCustomer: " + preparedStatement);

        } catch (SQLException sqlException) {
            printSQLException(sqlException);
        }
    }

    @Override
    public boolean checkImageExists(String image) {
        Connection connection = getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(CHECK_IMAGE_EXIST);
            preparedStatement.setString(1, image);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                // khỏi cần đọc dòng này ra vì chỉ cần trả ra true/false
                return true;
            }

            connection.close();
        } catch (SQLException e) {
            printSQLException(e);
        }
        return false;
    }

    @Override
    public List<Product> getAllProductByCategoryId(int idCategory) {
        List<Product> products = new ArrayList<>();
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELLECT_ALL_PRODUCT_BY_CATEGORY_ID);
            preparedStatement.setInt(1, idCategory);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Product c = getProductFromRs(rs);
                products.add(c);
            }
            connection.close();
        } catch (SQLException e) {
            printSQLException(e);
        }
        return products;
    }

    @Override
    public List<Product> getAllProductByTypeId(int idType) {
        List<Product> products = new ArrayList<>();
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELLECT_ALL_PRODUCT_BY_TYPE_ID);
            preparedStatement.setInt(1, idType);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Product c = getProductFromRs(rs);
                products.add(c);
            }
            connection.close();
        } catch (SQLException e) {
            printSQLException(e);
        }
        return products;
    }
}
