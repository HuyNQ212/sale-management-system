package fa.training.dao.impl;

import fa.training.common.DBUtls;
import fa.training.common.SQLCommand;
import fa.training.dao.OrderDAO;
import fa.training.entities.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public List<Order> getAllOrdersByCustomerId(int customerId) {
        List<Order> orderList = new ArrayList<>();

        try (Connection connection = DBUtls.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     SQLCommand.QUERY_GET_ALL_ORDER_BY_CUSTOMER_ID)
        ) {
            preparedStatement.setInt(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getInt("order_id"));
                order.setOrderDate(resultSet.getDate("order_date"));
                order.setCustomerId(resultSet.getInt("customer_id"));
                order.setEmployeeId(resultSet.getInt("employee_id"));
                order.setTotal(resultSet.getDouble("total"));

                orderList.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderList;
    }
    // Done


    @Override
    public boolean addOrder(Order order) {
        try (
                Connection connection = DBUtls.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQLCommand.QUERY_ADD_ORDER)
        ) {
            java.sql.Date sqlDate = new java.sql.Date(order.getOrderDate().getTime());
            preparedStatement.setDate(1, sqlDate);
            preparedStatement.setInt(2, order.getCustomerId());
            preparedStatement.setInt(3, order.getEmployeeId());
            preparedStatement.setDouble(4, order.getTotal());
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // Done

    @Override
    public boolean updateOrderTotal(int orderId) {
        int result = 0;
        try (
                Connection connection = DBUtls.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQLCommand.QUERY_UPDATE_ORDER_TOTAL)
        ) {
            preparedStatement.setInt(1, orderId);
            preparedStatement.setInt(2, orderId);
            result += preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result > 0;
    }

    @Override
    public List<Order> getAllOrder() {
        List<Order> orderList = new ArrayList<>();
        try (Connection connection = DBUtls.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQLCommand.QUERY_GET_ALL_ORDER);
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getInt("order_id"));
                order.setOrderDate(resultSet.getDate("order_date"));
                order.setCustomerId(resultSet.getInt("customer_id"));
                order.setEmployeeId(resultSet.getInt("employee_id"));
                order.setTotal(resultSet.getDouble("total"));
                orderList.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderList;
    }
}
