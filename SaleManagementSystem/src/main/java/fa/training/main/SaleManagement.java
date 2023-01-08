package fa.training.main;

import fa.training.common.ValidatorInput;
import fa.training.dao.*;
import fa.training.dao.impl.*;
import fa.training.entities.*;

import java.util.ArrayList;
import java.util.List;

public class SaleManagement {

    private final CustomerDAO customerDAO = new CustomerDAOImpl();
    private final EmployeeDAO employeeDAO = new EmployeeDAOImpl();
    private final LineItemDAO lineItemDAO = new LineItemDAOImpl();
    private final OrderDAO orderDAO = new OrderDAOImpl();
    private final ProductDAO productDAO = new ProductDAOImpl();

    public void getAllCustomer() {
        System.out.println("-----------------Get all customer----------------");
        List<Customer> customerList = new ArrayList<>();
        customerList = customerDAO.getAllCustomer();
        for (int i = 0; i < customerList.size(); i++) {
            System.out.println(customerList.get(i));
        }
        System.out.println("-------------------------------------------------");
    }

    public void getAllOrders() {
        System.out.println("-------------------Get all orders----------------");
        List<Order> orderList = new ArrayList<>();
        orderList = orderDAO.getAllOrder();
        for (int i = 0; i < orderList.size(); i++) {
            System.out.println(orderList.get(i));
        }
        System.out.println("-------------------------------------------------");
    }

    public void getAllEmployee() {
        System.out.println("-----------------Get all employees---------------");
        List<Employee> employeeList = new ArrayList<>();
        employeeList = employeeDAO.getAllEmployee();
        for (int i = 0; i < employeeList.size(); i++) {
            System.out.println(employeeList.get(i));
        }
        System.out.println("-------------------------------------------------");

    }

    public void getAllLineitem() {
        System.out.println("-----------------Get all lineitems---------------");
        List<LineItem> lineItems = new ArrayList<>();
        lineItems = lineItemDAO.getAllLineitems();
        for (int i = 0; i < lineItems.size(); i++) {
            System.out.println(lineItems.get(i));
        }
        System.out.println("-------------------------------------------------");

    }

    public void getAllProduct() {
        System.out.println("----------------Get all product------------------");
        List<Product> productList = new ArrayList<>();
        productList = productDAO.getAllProduct();
        for (int i = 0; i < productList.size(); i++) {
            System.out.println(productList.get(i));
        }
        System.out.println("-------------------------------------------------");
    }


    public void getAllOrderByCustomerId() {
        System.out.println("----------Get all orders by customer id----------");
        int customerId = ValidatorInput.inputInt(
                "Enter customer id: ",
                "Must be Integer!"
        );
        List<Order> orderList = new ArrayList<>();
        orderList = orderDAO.getAllOrdersByCustomerId(customerId);
        double totalForACustomer = 0;

        if (orderList.isEmpty()) {
            System.out.println("No order by customer id = " + customerId);
        }
        for (int i = 0; i < orderList.size(); i++) {
            System.out.println(orderList.get(i));
            totalForACustomer += orderList.get(i).getTotal();
        }
        System.out.println("Total for customer '" + customerId + "': " + totalForACustomer);
        System.out.println("-------------------------------------------------");
    }

    //Done
    public void addCustomer() {
        System.out.println("--------------------Add customer-----------------");

        String customerName = ValidatorInput.inputString(
                "Enter customer name: "
        );
        Customer customer = new Customer(customerName);
        customerDAO.addCustomer(customer);
        System.out.println("Add success!");
        System.out.println("-------------------------------------------------");
    }

    public void deleteCustomer() {
        System.out.println("-----------------Delete customer-----------------");
        int customerId = ValidatorInput.inputInt(
                "Enter customer id: ",
                "Must be Integer!"
        );
        if (!ValidatorInput.checkDuplicateCustomerId(
                customerDAO.getAllCustomer(), customerId)) {
            System.out.println("Customer id is not exists!");
        } else {
            if (customerDAO.deleteCustomer(customerId)) {
                System.out.println("Delete success!");
            } else {
                System.out.println("Error! Please check!");
            }
        }
        System.out.println("-------------------------------------------------");
    }

    //Done
    public void updateCustomer() {
        System.out.println("-----------------Update customer-----------------");
        int customerId = ValidatorInput.inputInt(
                "Enter customer id: ",
                "Must be Integer"
        );
        if (!ValidatorInput.checkDuplicateCustomerId(customerDAO.getAllCustomer(), customerId)) {
            System.out.println("Customer id is not exists!");
        } else {
            String customerName = ValidatorInput.inputString(
                    "Enter customer name: "
            );
            if (customerDAO.updateCustomer(new Customer(customerId, customerName))) {
                System.out.println("Update success!");
            } else {
                System.out.println("Error! Please check!");
            }
        }
        System.out.println("-------------------------------------------------");
    }

    // Done
    public void getAllItemByOrderId() {
        System.out.println("----------Get all lineitems by order id----------");
        int orderId = ValidatorInput.inputInt(
                "Enter order id: ",
                " Must be Integer!");
        List<LineItem> lineItems = new ArrayList<>();
        lineItems = lineItemDAO.getAllItemsByOrderId(orderId);
        if (lineItems.isEmpty()) {
            System.out.println("No lineitems where order id = " + orderId);
        } else {
            for (int i = 0; i < lineItems.size(); i++) {
                System.out.println(lineItems.get(i));
            }
        }
        System.out.println("-------------------------------------------------");
    }

    // Done
    public void addLineItem() {
        System.out.println("----------------Add lineitem---------------------");
        LineItem lineItem = new LineItem();
        lineItem.setOrderId(ValidatorInput.inputInt(
                "Enter order id: ",
                "Must be Integer!"
        ));
        if (ValidatorInput.checkDuplicateOrderId(orderDAO.getAllOrder(), lineItem.getOrderId())) {
            lineItem.setProductId(ValidatorInput.inputInt(
                    "Enter product id: ",
                    "Must be integer!"
            ));
            if (ValidatorInput.checkDuplicateProductId(productDAO.getAllProduct(), lineItem.getProductId())) {
                lineItem.setQuantity(ValidatorInput.inputInt(
                        "Enter quantity: ",
                        "Must be integer!"
                ));
                lineItem.setPrice(ValidatorInput.inputDouble(
                        "Enter price: ",
                        "Must be double!"
                ));
                if (lineItemDAO.addLineItem(lineItem)) {
                    System.out.println("Add lineitem success!");
                } else {
                    System.out.println("Add error!");
                }
            } else {
                System.out.println("Product id not exists!");
            }
        } else {
            System.out.println("Order id not exists!");
        }

        System.out.println("-------------------------------------------------");
    }
    // Done

    public void addOrder() {
        System.out.println("------------------Add order----------------------");
        Order order = new Order();
        order.setOrderDate(ValidatorInput.inputDate(
                "Enter order date (dd/MM/yyyy): ",
                "Must follow pattern dd/MM/yyyy!",
                "dd/MM/YYYY"
        ));
        order.setCustomerId(ValidatorInput.inputInt(
                "Enter customer id: ",
                "Must be integer!"
        ));
        if (ValidatorInput.checkDuplicateCustomerId(customerDAO.getAllCustomer(), order.getCustomerId())) {
            order.setEmployeeId(ValidatorInput.inputInt(
                    "Enter employee id: ",
                    "Must be integer!"
            ));
            if (ValidatorInput.checkDuplicateEmployeeId(employeeDAO.getAllEmployee(), order.getEmployeeId())) {
                order.setTotal(ValidatorInput.inputDouble(
                        "Enter total: ",
                        "Must be double!"
                ));
                if (orderDAO.addOrder(order)) {
                    System.out.println("Add order success!");
                } else {
                    System.out.println("Add fail!");
                }
            } else {
                System.out.println("Employee id not existed!");
            }
        } else {
            System.out.println("Customer id not existed!");
        }
        System.out.println("-------------------------------------------------");
    }

    // Done
    public void updateOrderTotal() {
        getAllOrders();
        System.out.println("---------------Update order total----------------");
        int orderId = ValidatorInput.inputInt(
                "Enter order id: ",
                "Must be integer!"
        );
        if (ValidatorInput.checkContainOrderIdInLineitems(lineItemDAO.getAllLineitems(), orderId)) {
            if (orderDAO.updateOrderTotal(orderId)) {
                System.out.println("Update success!");
            } else {
                System.out.println("Update fail!");
            }
        } else {
            System.out.println("Order id not exist!");
        }
        System.out.println("-------------------------------------------------");
    }

    // Done
    public void computeOrderTotal() {
        System.out.println("-------------Compute order total-----------------");
        int orderId = ValidatorInput.inputInt(
                "Enter order id: ",
                "Must be Integer!"
        );
        System.out.println("Total order value by order id '" + orderId + "' = " + lineItemDAO.computeOrderTotal(orderId));
        System.out.println("-------------------------------------------------");
    }

    // Done
    public void displayMenu() {
        System.out.println("-----------------------Menu----------------------");
        System.out.println("1. List all.");
        System.out.println("2. List all orders by customer id.");
        System.out.println("3. List all line-items for an order - given order id.");
        System.out.println("4. Compute order total by order id.");
        System.out.println("5. Add a customer to database.");
        System.out.println("6. Delete a customer in the database.");
        System.out.println("7. Update a customer in the database.");
        System.out.println("8. Create an order into the database.");
        System.out.println("9. Create a line-item into the database.");
        System.out.println("10. Update an order total into the database.");
        System.out.println("0. Exit");
        System.out.println();

        System.out.print("Choose function you'd like to do: ");
    }

    public void displayMenuListAll() {
        System.out.println("-------------------Menu list all-----------------");
        System.out.println("1. List all customers.");
        System.out.println("2. List all employees.");
        System.out.println("3. List all line-items");
        System.out.println("4. List all orders.");
        System.out.println("5. List all products.");
        System.out.println("6. Back to Menu.");
        System.out.println();
        System.out.print("Choose function you'd like to do: ");
    }

    public void displayAnything() {
        System.out.println("test to github");
        System.out.println("test something to github");
    }
}