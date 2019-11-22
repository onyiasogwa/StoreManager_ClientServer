import com.google.gson.Gson;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class RemoteDataAdapter implements IDataAccess {

    String host;
    int port;

    Gson gson = new Gson();

    public RemoteDataAdapter(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public boolean connect(String path) {
        return true;
    }

    @Override
    public boolean disconnect() {
        return false;
    }

    @Override
    public int getErrorCode() {
        return 0;
    }

    @Override
    public String getErrorMessage() {
        return null;
    }

    private String netSend(int code, String data) {
        String json = null;
        try {
            Socket link = new Socket(host, port);
            Scanner in = new Scanner(link.getInputStream());
            PrintWriter out = new PrintWriter(link.getOutputStream(), true);
            MessageModel message = new MessageModel(code, data);
            json = gson.toJson(message);
            System.out.println("Sent: " + json);
            out.println(json);
            json = in.nextLine();
            in.close();
            out.close();
            link.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Received: " + json);
        return json;
    }

    @Override
    public ProductModel loadProduct(int id) {
        String json = netSend(MessageModel.GET_PRODUCT, Integer.toString(id));
        MessageModel message = gson.fromJson(json, MessageModel.class);
        if (message.code == IDataAccess.PRODUCT_LOAD_OK)
            return gson.fromJson(message.data, ProductModel.class);
        else
            return null;
    }

    @Override
    public boolean saveProduct(ProductModel product) {
        String json = netSend(MessageModel.PUT_PRODUCT, gson.toJson(product));
        MessageModel message = gson.fromJson(json, MessageModel.class);
        if (message.code == IDataAccess.PRODUCT_SAVE_OK)
            return true;
        else
            return false;
    }

    @Override
    public CustomerModel loadCustomer(int mCustomerID) {
        String json = netSend(MessageModel.GET_CUSTOMER, Integer.toString(mCustomerID));
        MessageModel message = gson.fromJson(json, MessageModel.class);
        if (message.code == IDataAccess.CUSTOMER_LOAD_OK)
            return gson.fromJson(message.data, CustomerModel.class);
        else
            return null;
    }

    @Override
    public boolean saveCustomer(CustomerModel customerModel) {
        String json = netSend(MessageModel.PUT_CUSTOMER, gson.toJson(customerModel));
        MessageModel message = gson.fromJson(json, MessageModel.class);
        if (message.code == IDataAccess.CUSTOMER_SAVE_OK)
            return true;
        else
            return false;
    }

    @Override
    public PurchaseModel loadPurchase(Integer mOrderID) {
        String json = netSend(MessageModel.GET_ORDER, Integer.toString(mOrderID));
        MessageModel message = gson.fromJson(json, MessageModel.class);
        if (message.code == IDataAccess.ORDER_LOAD_OK)
            return gson.fromJson(message.data, PurchaseModel.class);
        else
            return null;
    }

    @Override
    public boolean savePurchase(PurchaseModel purchaseModel) {
        String json = netSend(MessageModel.PUT_ORDER, gson.toJson(purchaseModel));
        MessageModel message = gson.fromJson(json, MessageModel.class);
        if (message.code == IDataAccess.ORDER_SAVE_OK)
            return true;
        else
            return false;
    }
}
