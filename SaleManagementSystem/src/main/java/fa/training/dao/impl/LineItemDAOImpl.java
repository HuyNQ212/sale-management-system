package fa.training.dao.impl;

import fa.training.common.DBUtls;
import fa.training.common.SQLCommand;
import fa.training.dao.LineItemDAO;
import fa.training.entities.LineItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LineItemDAOImpl implements LineItemDAO {
    @Override
    public List<LineItem> getAllLineitems() {
        List<LineItem> lineItemList = new ArrayList<>();
        try (
                Connection connection = DBUtls.getConnection();
                Statement statement = connection.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery(SQLCommand.QUERY_GET_ALL_LINEITEM);
            while (resultSet.next()) {
                LineItem lineItem = new LineItem();
                lineItem.setOrderId(resultSet.getInt("order_id"));
                lineItem.setPrice(resultSet.getDouble("price"));
                lineItem.setProductId(resultSet.getInt("product_id"));
                lineItem.setQuantity(resultSet.getInt("quantity"));

                lineItemList.add(lineItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lineItemList;
    }

    @Override
    public List<LineItem> getAllItemsByOrderId(int orderId) {
        List<LineItem> lineItems = new ArrayList<>();
        try (
                Connection connection = DBUtls.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQLCommand.QUERY_GET_ALL_ITEM_BY_ORDER_ID)
        ) {
            preparedStatement.setInt(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                LineItem lineItem = new LineItem();
                lineItem.setOrderId(resultSet.getInt("order_id"));
                lineItem.setPrice(resultSet.getDouble("price"));
                lineItem.setProductId(resultSet.getInt("product_id"));
                lineItem.setQuantity(resultSet.getInt("quantity"));

                lineItems.add(lineItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lineItems;
    }
    // Done

    @Override
    public boolean addLineItem(LineItem item) {
        try (
                Connection connection = DBUtls.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        SQLCommand.QUERY_ADD_LINE_ITEM
                )
        ) {
            preparedStatement.setInt(1, item.getOrderId());
            preparedStatement.setInt(2, item.getProductId());
            preparedStatement.setInt(3, item.getQuantity());
            preparedStatement.setDouble(4, item.getPrice());
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updatePriceFromProductToLineitem() {
        int result = 0;
        try (Connection connection = DBUtls.getConnection();
             Statement statement = connection.createStatement()) {
            result = statement.executeUpdate(SQLCommand.QUERY_UPDATE_PRICE_TO_LINEITEM);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result > 0;
    }

    @Override
    public Double computeOrderTotal(int orderId) {
        Double result = 0d;
        try (
                Connection connection = DBUtls.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQLCommand.QUERY_COMPUTE_ORDER_TOTAL)
        ) {
            preparedStatement.setInt(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    // Done
}
