USE master;
GO
DROP DATABASE IF EXISTS SMS;
GO
CREATE DATABASE SMS;
GO
USE SMS;
GO
CREATE TABLE dbo.Customer(
	customer_id		INT IDENTITY(1,1) PRIMARY KEY,
	customer_name	NVARCHAR(50)
);
GO
CREATE TABLE Employee(
	employee_id		INT	IDENTITY(1,1) PRIMARY KEY,
	employee_name	NVARCHAR(50),
	salary			FLOAT,
	supervisor_id	INT
);
GO
CREATE TABLE Product(
	product_id		INT	IDENTITY(1,1) PRIMARY KEY,
	product_name	NVARCHAR(50),
	list_price		FLOAT
);
GO
CREATE TABLE Orders(
	order_id		INT	IDENTITY(1,1) PRIMARY KEY,
	order_date		DATE,
	customer_id		INT,
	FOREIGN KEY (customer_id) REFERENCES dbo.Customer(customer_id),
	employee_id		INT,
	FOREIGN KEY (employee_id) REFERENCES dbo.Employee(employee_id),
	total			FLOAT,
	
);
GO
CREATE TABLE LineItem(
	lineitem_id		INT PRIMARY KEY IDENTITY(1,1),
	order_id		INT NOT NULL,
	FOREIGN KEY(order_id) REFERENCES dbo.Orders(order_id),
	product_id		INT,
	FOREIGN KEY(product_id) REFERENCES dbo.Product(product_id),
	quantity		INT,
	price			FLOAT
);
GO

INSERT INTO dbo.Customer
(   customer_name)
VALUES
(   N'Nguyen Van A'),
(   N'Nguyen Van B'),
(   N'Nguyen Van C'),
(   N'Nguyen Van D'),
(   N'Nguyen Van E')
;
GO

INSERT INTO dbo.Employee
(   employee_name,salary,supervisor_id)
VALUES
(   N'Nguyen Van E',4000.0,1),
(   N'Nguyen Van F',1000.0,1),
(   N'Nguyen Van G',1500.0,1),
(   N'Nguyen Van H',800.0,1),
(   N'Nguyen Van I',2500.0,1)
;
GO

INSERT INTO dbo.Product
(product_name,list_price)
VALUES
(N'Product 01',100.0),
(N'Product 02',200.0),
(N'Product 03',250.0),
(N'Product 04',350.0),
(N'Product 05',150.0)
;
GO

INSERT INTO dbo.Orders
(order_date,customer_id,employee_id,total)
VALUES
('2022-03-30',1,2,1000.0),
('2022-03-30',1,3,2050.0),
('2022-03-30',1,4,1550.0),
('2022-03-30',2,2,1000.0),
('2022-03-30',3,5,1000.0),
('2022-03-30',4,5,1000.0),
('2022-03-30',5,3,1000.0)
;
GO

INSERT INTO dbo.LineItem
(order_id,product_id,quantity,price)
VALUES
(1,1,10,100.0),
(1,2,15,100.0),
(2,1,10,205.0),
(3,1,10,155.0),
(4,4,10,100.0),
(5,1,10,100.0),
(6,1,10,100.0),
(7,1,10,100.0)
GO

CREATE FUNCTION udf_PickListPrice(@product_id INT)
RETURNS float AS
BEGIN 
	DECLARE @Price float
	SELECT @Price = list_price  FROM dbo.Product WHERE @product_id = product_id
	RETURN @Price
END 
GO

-- UPDATE dbo.LineItem
-- SET price = (dbo.udf_PickListPrice(product_id))
-- GO
--SELECT * FROM dbo.LineItem;
--GO


/*
	public static String QUERY_GET_ALL_CUSTOMER ="";
    public static String QUERY_GET_ALL_ORDER_BY_CUSTOMER_ID = "";
    public static String QUERY_GET_ALL_ITEM_BY_ORDER_ID = "";
    public static String QUERY_COMPUTE_ORDER_TOTAL = ""; use user defined function
    public static String QUERY_ADD_CUSTOMER = ""; use stored procedure
    public static String QUERY_DELETE_CUSTOMER = ""; use stored procedure
    public static String QUERY_UPDATE_CUSTOMER = ""; use stored procedure
    public static String QUERY_ADD_ORDER = "";
    public static String QUERY_ADD_LINE_ITEM = "";
    public static String QUERY_UPDATE_ORDER_TOTAL = "";
*/

--	public static String QUERY_GET_ALL_CUSTOMER ="";
--SELECT * FROM dbo.Customer;
--GO

--     public static String QUERY_GET_ALL_ORDER_BY_CUSTOMER_ID = "";
--SELECT *
--FROM dbo.Orders
--WHERE customer_id = 1;
--GO


--     public static String QUERY_GET_ALL_ITEM_BY_ORDER_ID = "";
--SELECT * FROM dbo.LineItem 
--WHERE order_id = 1;
--GO

--     public static String QUERY_COMPUTE_ORDER_TOTAL = ""; use user defined function
CREATE FUNCTION udf_ComputeOrder(@lineitem_id INT)
RETURNS float AS
BEGIN 
	DECLARE @Total_Price float
	SELECT @Total_Price = quantity * price
	FROM dbo.LineItem 
	WHERE @lineitem_id = lineitem_id
	RETURN @Total_Price
END 
GO

CREATE FUNCTION udf_ComputeOrderTotal(@order_id INT)
RETURNS FLOAT AS 
BEGIN
	DECLARE @Order_Total FLOAT
	SELECT @Order_Total =  SUM(dbo.udf_ComputeOrder(lineitem_id)) 
	FROM dbo.LineItem
	WHERE @order_id = order_id
	GROUP BY order_id
	RETURN @Order_Total
END
GO


--    public static String QUERY_ADD_CUSTOMER = ""; use stored procedure
CREATE PROCEDURE usp_AddCustomer(
		@customer_name VARCHAR(50),
		@status VARCHAR(20) OUTPUT
)
AS
BEGIN
	INSERT INTO	dbo.Customer(customer_name)
	VALUES		(@customer_name)

	IF (@@ROWCOUNT > 0) 
		BEGIN
			SET	@status = 'add success!';
		END
	ELSE
		BEGIN
		    SET @status = 'add failed!';
		END
END
;
GO

--DECLARE @status VARCHAR(20);
--EXECUTE dbo.usp_AddCustomer @customer_name = 'add by stored procedure',     -- varchar(50)
--                            @status = @status OUTPUT -- varchar(20)
--SELECT @status;
--GO

--     public static String QUERY_DELETE_CUSTOMER = ""; use stored procedure

CREATE or alter PROCEDURE usp_DeleteCustomer(
		@customer_id INT,
		@status VARCHAR(20) OUTPUT
)
AS
BEGIN
    DELETE FROM dbo.Customer WHERE customer_id = @customer_id
	IF(@@ROWCOUNT > 0)
	BEGIN
	    SET @status = 'delete success!';
	END
	ELSE
    BEGIN
        SET @status = 'delete failed!';
    END
END
GO


--DECLARE @status VARCHAR(20);
--EXECUTE dbo.usp_DeleteCustomer @customer_id = 7,        -- int
--                               @status = @status OUTPUT -- varchar(20)
--SELECT @status AS message_out;		
--GO

--SELECT * FROM dbo.Customer;
--GO

--     public static String QUERY_UPDATE_CUSTOMER = ""; use stored procedure
CREATE PROCEDURE usp_UpdateCustomer(
	@customer_id INT,
	@customer_name NVARCHAR(50),
	@status VARCHAR(50) OUTPUT
)
AS
BEGIN
    UPDATE dbo.Customer
	SET customer_name = @customer_name
	WHERE customer_id = @customer_id

	IF(@@ROWCOUNT > 0) 
	BEGIN
	    SET @status = 'update success!';
	END
	ELSE
	BEGIN
	    SET @status = 'update failed!';
	END
END
GO

--DECLARE @status VARCHAR(50);
--EXECUTE dbo.usp_UpdateCustomer @customer_id = 1,        -- int
--                               @customer_name = N'update by stored proceduce',    -- nvarchar(50)
--                               @status = @status OUTPUT -- varchar(50)
--SELECT @status AS message_out;
--GO

--SELECT * FROM dbo.Orders;
--GO

--     public static String QUERY_ADD_ORDER = "";


--INSERT INTO dbo.Orders
--(order_date,customer_id,employee_id,total)
--VALUES ('2022-12-03',3,2,1234)


--     public static String QUERY_ADD_LINE_ITEM = "";

--INSERT INTO dbo.LineItem
--(order_id,product_id,quantity,price)
--VALUES
--(?, ?, ?, ?)

--public static String QUERY_UPDATE_ORDER_TOTAL = "";

-- UPDATE dbo.Orders SET total = dbo.udf_ComputeOrderTotal(4) WHERE order_id = 4;
