import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteDataAdapter implements IDataAccess {
    String url = null;
    Connection conn = null;
    int errorCode = 0;

    public SQLiteDataAdapter(String path) {
        url = "jdbc:sqlite:" + path;
    }

    public boolean connect(String path) {
        try {
            conn = DriverManager.getConnection(url);
            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            errorCode = CONNECTION_OPEN_FAILED;
            return false;
        }

    }

    @Override
    public boolean disconnect() {
        return true;
    }

    @Override
    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMessage() {
        switch (errorCode) {
            case CONNECTION_OPEN_FAILED:
                return "Connection is not opened!";
            case PRODUCT_LOAD_FAILED:
                return "Cannot load the product!";
        }
        ;
        return "OK";
    }

    public ProductModel loadProduct(int productID) {
        ProductModel product = null;
        try {
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Products WHERE ProductId = " + productID);
            if (rs.next()) {
                product = new ProductModel();
                product.mProductID = rs.getInt("ProductId");
                product.mName = rs.getString("Name");
                product.mPrice = rs.getDouble("Price");
                product.mQuantity = rs.getDouble("Quantity");
                product.mVendor = rs.getString("Vendor");
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            errorCode = PRODUCT_LOAD_FAILED;
        }
        return product;
    }

    public boolean saveProduct(ProductModel product) {
        try {
            conn = DriverManager.getConnection(url);
            ProductModel model = loadProduct(product.mProductID);
            Statement stmt = conn.createStatement();
            if (model != null)  // delete this record
                stmt.execute("DELETE FROM Products WHERE ProductId = " + product.mProductID);
            stmt.execute("INSERT INTO Products(ProductId, Name, Price, Quantity, Vendor) " +
                            "VALUES('"+product.mProductID+"','"+product.mName+"','"+product.mPrice+"','"+product.mQuantity+"','"+product.mVendor+"')");
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            errorCode = PRODUCT_LOAD_FAILED;
            return false;
        }
        return true;
    }

    public CustomerModel loadCustomer(int id) {
        try {
            conn = DriverManager.getConnection(url);
            CustomerModel c = new CustomerModel();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Customers WHERE CustomerID = " + id);
            c.mCustomerID = rs.getInt("CustomerID");
            c.mName = rs.getString("Name");
            c.mPhone = rs.getString("Phone");
            c.mAddress = rs.getString("Address");
            stmt.close();
            conn.close();
            return c;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            errorCode = CUSTOMER_LOAD_FAILED;
            return null;
        }
    }

    public boolean saveCustomer(CustomerModel customer) {
        try {
            CustomerModel model = loadCustomer(customer.mCustomerID);
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            if (model != null)  // delete this record
                stmt.execute("DELETE FROM Customers WHERE CustomerId = " + customer.mCustomerID);
            stmt.execute("INSERT INTO Customers (CustomerId, Name, Phone, Address) " +
                    "VALUES('"+customer.mCustomerID+"','"+customer.mName+"','"+customer.mPhone+"','"+customer.mAddress+"')");
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            errorCode = CUSTOMER_LOAD_FAILED;
            return false;
        }
        return true;
    }

    @Override
    public PurchaseModel loadPurchase(Integer mPurchaseID) {
        PurchaseModel purchaseModel = null;
        try {
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Orders WHERE OrderId = " + mPurchaseID);
            if (rs.next()) {
                purchaseModel = new PurchaseModel();
                purchaseModel.mProductID = rs.getInt("ProductId");
                purchaseModel.mCustomerID = rs.getInt("CustomerId");
                purchaseModel.mPrice = rs.getDouble("Price");
                purchaseModel.mQuantity = rs.getInt("Quantity");
                purchaseModel.mTotalCost = rs.getInt("TotalCost");
                purchaseModel.mTax = rs.getInt("TotalTax");
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            errorCode = PRODUCT_LOAD_FAILED;
        }
        return purchaseModel;
    }

    @Override
    public boolean savePurchase(PurchaseModel purchaseModel) {
        try {
            PurchaseModel model = loadPurchase(purchaseModel.mPurchaseID);
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            if (model != null)  // delete this record
                stmt.execute("DELETE FROM Orders WHERE OrderId = " + purchaseModel.mPurchaseID);
            stmt.execute("INSERT INTO Orders (OrderId, CustomerId, ProductId, Quantity, Price, TotalTax, TotalCost, Date) " +
                    "VALUES('"+purchaseModel.mPurchaseID+"','"+purchaseModel.mCustomerID+"','"+purchaseModel.mProductID+"','"+purchaseModel.mQuantity+"','"+purchaseModel.mPrice+"','"+purchaseModel.mTax+"','"+purchaseModel.mTotalCost+"','"+purchaseModel.mDate+"')");
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            errorCode = CUSTOMER_LOAD_FAILED;
            return false;
        }
        return true;
    }

}
