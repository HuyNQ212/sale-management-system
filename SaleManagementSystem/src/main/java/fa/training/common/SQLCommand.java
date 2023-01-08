package fa.training.common;

public class SQLCommand {
    public static final String QUERY_GET_ALL_CUSTOMER = "SELECT * FROM dbo.Customer;";
    public static final String QUERY_GET_ALL_ORDER_BY_CUSTOMER_ID =
            "SELECT *\n" +
                    "FROM dbo.Orders\n" +
                    "WHERE customer_id = ?\n";
    public static final String QUERY_GET_ALL_ITEM_BY_ORDER_ID =
            "SELECT * FROM dbo.LineItem \n" +
                    "WHERE order_id = ?;\n" // 1 ?
            ;
    public static final String QUERY_COMPUTE_ORDER_TOTAL =
            "SELECT dbo.udf_ComputeOrderTotal(?);";
    // @order_id
    public static final String QUERY_ADD_CUSTOMER = "{CALL usp_AddCustomer(?,?)}";
    //@customer_id, @status
    public static final String QUERY_DELETE_CUSTOMER = "DELETE FROM LineItem WHERE order_id IN (SELECT order_id FROM [Orders] WHERE customer_id = ?)"
    +"DELETE FROM [Orders] WHERE customer_id = ?;"
    +"DELETE FROM Customer WHERE customer_id = ?";
    //@customer_id, @status
    public static final String QUERY_UPDATE_CUSTOMER = "{CALL usp_UpdateCustomer(?,?,?)}";
    //@customer_id,@customer_name,@status
    public static final String QUERY_ADD_ORDER =
            "INSERT INTO dbo.Orders\n" +
                    "(order_date,customer_id,employee_id,total)\n" +
                    "VALUES (?,?,?,?)";
    public static final String QUERY_ADD_LINE_ITEM =
            "INSERT INTO dbo.LineItem\n" +
                    "(order_id,product_id,quantity,price)\n" +
                    "VALUES\n" +
                    "(?, ?, ?, ?)";
    public static final String QUERY_UPDATE_PRICE_TO_LINEITEM =
            "UPDATE dbo.LineItem \n" +
                    "SET price = (dbo.udf_PickListPrice(product_id))";
    public static final String QUERY_UPDATE_ORDER_TOTAL =
            "UPDATE dbo.Orders SET total = dbo.udf_ComputeOrderTotal(?) WHERE order_id = ?;";
    public static final String QUERY_GET_ALL_ORDER =
            "SELECT * FROM dbo.Orders";
    public static final String QUERY_GET_ALL_EMPLOYEE =
            "SELECT * FROM dbo.Employee";
    public static final String QUERY_GET_ALL_PRODUCT =
            "SELECT * FROM dbo.Product";
    public static final String QUERY_GET_ALL_LINEITEM =
            "SELECT * FROM dbo.LineItem";
}
