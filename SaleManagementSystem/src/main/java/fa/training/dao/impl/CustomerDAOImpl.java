package fa.training.dao.impl;

import fa.training.common.DBUtls;
import fa.training.common.SQLCommand;
import fa.training.dao.CustomerDAO;
import fa.training.entities.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public List<Customer> getAllCustomer() {
        List<Customer> customerList = new ArrayList<>();

        try (Connection connection = DBUtls.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQLCommand.QUERY_GET_ALL_CUSTOMER);) {
            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(resultSet.getInt("customer_id"));
                customer.setCustomerName(resultSet.getString("customer_name"));
                customerList.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerList;
    }
    // DONE

    @Override
    public boolean addCustomer(Customer customer) {
        String calltitle = new String();
        try (Connection connection = DBUtls.getConnection();
             CallableStatement callableStatement = connection.prepareCall(SQLCommand.QUERY_ADD_CUSTOMER);
        ) {
            callableStatement.setString(1, customer.getCustomerName());
            callableStatement.registerOutParameter(2, Types.LONGVARCHAR);

            callableStatement.execute();
            calltitle = callableStatement.getString(2);
//            System.out.println("Status: " + calltitle);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (calltitle.contains("success")) {
            return true;
        } else {
            return false;
        }
    }

    // DONE
    @Override
    public boolean deleteCustomer(int customerId) {
        int result = 0;
        try (
                Connection connection = DBUtls.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQLCommand.QUERY_DELETE_CUSTOMER);
        ) {
            preparedStatement.setInt(1,customerId);
            preparedStatement.setInt(2, customerId);
            preparedStatement.setInt(3,customerId);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    // DONE
    @Override
    public boolean updateCustomer(Customer customer) {
        String statusDatabase = new String();
        try (Connection connection = DBUtls.getConnection();
             CallableStatement callableStatement = connection.prepareCall(SQLCommand.QUERY_UPDATE_CUSTOMER);
        ) {
            callableStatement.setInt(1, customer.getCustomerId());
            callableStatement.setString(2, customer.getCustomerName());
            callableStatement.registerOutParameter(3, Types.LONGVARCHAR);
            callableStatement.execute();

            statusDatabase = callableStatement.getString(3);
//            System.out.println("Status: " + statusDatabase);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (statusDatabase.contains("success")) {
            return true;
        } else return false;
    }
    // DONE
}
