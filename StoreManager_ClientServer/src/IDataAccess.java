public interface IDataAccess {
    public static final int CONNECTION_OPEN_OK = 1;
    public static final int CONNECTION_OPEN_FAILED = 2;

    public static final int PRODUCT_SAVE_OK = 101;
    public static final int PRODUCT_SAVE_FAILED = 102;
    public static final int PRODUCT_SAVE_DUPLICATE = 103;

    public static final int PRODUCT_LOAD_OK = 201;
    public static final int PRODUCT_LOAD_FAILED = 202;
    public static final int PRODUCT_LOAD_ID_NOT_FOUND = 203;

    public static final int CUSTOMER_LOAD_OK = 301;
    public static final int CUSTOMER_LOAD_FAILED = 302;
    public static final int CUSTOMER_LOAD_ID_NOT_FOUND = 303;

    public static final int CUSTOMER_SAVE_OK = 401;
    public static final int CUSTOMER_SAVE_FAILED = 402;
    public static final int CUSTOMER_SAVE_ID_NOT_FOUND = 403;

    public static final int ORDER_LOAD_OK = 501;
    public static final int ORDER_LOAD_FAILED = 502;
    public static final int ORDER_LOAD_ID_NOT_FOUND = 503;

    public static final int ORDER_SAVE_OK = 601;
    public static final int ORDER_SAVE_FAILED = 602;
    public static final int ORDER_SAVE_ID_NOT_FOUND = 603;

    public boolean connect(String path);
    public boolean disconnect();
    public int getErrorCode();
    public String getErrorMessage();

    public ProductModel loadProduct(int id);
    public boolean saveProduct(ProductModel product);

    public CustomerModel loadCustomer(int mProductID);

    public boolean saveCustomer(CustomerModel customerModel);

    public PurchaseModel loadPurchase(Integer mPurchaseID);

    public boolean savePurchase(PurchaseModel purchaseModel);
//    public boolean saveCustomer(CustomerModel product);

}
