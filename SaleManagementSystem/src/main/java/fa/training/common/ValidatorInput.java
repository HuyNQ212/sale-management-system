package fa.training.common;

import fa.training.entities.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ValidatorInput {
    public static String inputString(String label) {
        Scanner scanner = new Scanner(System.in);

        System.out.print(label);
        return scanner.nextLine();
    }

    public static String inputString(String label, String pattern, String error) {
        Scanner scanner = new Scanner(System.in);
        String value;
        while (true) {
            System.out.print(label);
            value = scanner.nextLine();
            //regex:
            if (ValidationUtils.isValidPattern(pattern, value)) {
                return value;
            }
            System.out.println(error);
        }
    }

    public static String inputString(String label, String[] list, String error) {
        Scanner scanner = new Scanner(System.in);
        String value;
        while (true) {
            System.out.print(label);
            value = scanner.nextLine();
            for (String s : list) {
                if (s.equals(value)) {
                    return value;
                }
            }
            System.out.println(error);
        }
    }

    public static Integer inputInt(String label, String error) {
        Scanner scanner = new Scanner(System.in);
        Integer value;

        while (true) {
            System.out.print(label);
            try {
                value = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println(error);
            }
        }
        return value;
    }

    public static Double inputDouble(String label, String error) {
        Scanner scanner = new Scanner(System.in);
        Double value;

        while (true) {
            System.out.print(label);
            try {
                value = Double.parseDouble(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println(error);
            }
        }
        return value;
    }

    public static Date inputDate(String label, String error, String pattern) {
        Scanner scanner = new Scanner(System.in);
        Date value;

        while (true) {
            System.out.print(label);
            try {
                value = new SimpleDateFormat(pattern).parse(scanner.nextLine());
                break;
            } catch (ParseException e) {
                System.out.println(error);
            }
        }
        return value;
    }

//    public static void main(String[] args) {
//        LocalDate date = inputDate(
//                "Date("+ DATE_PATTERN +"): ",
//                "Must follow pattern " + DATE_PATTERN,
//                DATE_PATTERN);
//
//    }

    public static String inputString(String label, String error) {
        Scanner scanner = new Scanner(System.in);
        String value;
        while (true) {
            try {
                System.out.print(label);
                value = scanner.nextLine();
                break;
            } catch (Exception e) {
                System.out.println(error);
            }
        }
        return value;
    }

    public static boolean checkDuplicateCustomerId(List<Customer> customerList, int customerId) {
        for (Customer customer : customerList) {
            if (customer.getCustomerId() == customerId) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkDuplicateOrderId(List<Order> orderList, int orderId) {
        for (Order order : orderList) {
            if (order.getOrderId() == orderId) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkDuplicateEmployeeId(List<Employee> employeeList, int employeeId) {
        for (Employee employee : employeeList) {
            if (employee.getEmployeeId() == employeeId) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkDuplicateProductId(List<Product> productList, int productId) {
        for (Product product : productList) {
            if (product.getProductId() == productId) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkContainOrderIdInLineitems(List<LineItem> lineItemList, int orderId) {
        for (LineItem lineItem : lineItemList) {
            if (lineItem.getOrderId() == orderId) {
                return true;
            }
        }
        return false;
    }
}
