package fa.training.dao.impl;

import fa.training.common.DBUtls;
import fa.training.common.SQLCommand;
import fa.training.dao.ProductDAO;
import fa.training.entities.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {
    @Override
    public List<Product> getAllProduct() {
        List<Product> productList = new ArrayList<>();
        try (Connection connection = DBUtls.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQLCommand.QUERY_GET_ALL_PRODUCT);
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("product_id"));
                product.setProductName(resultSet.getString("product_name"));
                product.setListPrice(resultSet.getDouble("list_price"));
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }
}
