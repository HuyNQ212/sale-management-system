package fa.training.dao.impl;

import fa.training.common.DBUtls;
import fa.training.common.SQLCommand;
import fa.training.dao.EmployeeDAO;
import fa.training.entities.Employee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public List<Employee> getAllEmployee() {
        List<Employee> employeeList = new ArrayList<>();
        try (Connection connection = DBUtls.getConnection();
             Statement statement = connection.createStatement();)
        {
            ResultSet resultSet = statement.executeQuery(SQLCommand.QUERY_GET_ALL_EMPLOYEE);
            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setEmployeeId(resultSet.getInt("employee_id"));
                employee.setEmployeeName(resultSet.getString("employee_name"));
                employee.setSalary(resultSet.getDouble("salary"));
                employee.setSupervisorId(resultSet.getInt("supervisor_id"));
                employeeList.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeList;
    }
}
