import com.google.gson.Gson;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class StoreServer {
    static Gson gson = new Gson();
    static String dbfile = "C:\\Users\\onyie\\IdeaProjects\\StoreManager_ClientServer\\src\\store.db";
    static IDataAccess dao = new SQLiteDataAdapter(dbfile);

    public static void main(String[] args) {
        int port = 10000;
        try {
            ServerSocket server = new ServerSocket(port);
            while (true) {
                try {
                    Socket pipe = server.accept();
                    Scanner in = new Scanner(pipe.getInputStream());
                    PrintWriter out = new PrintWriter(pipe.getOutputStream(), true);

                    String msg = in.nextLine();
                    MessageModel request = gson.fromJson(msg, MessageModel.class);
                    String res = gson.toJson(process(request));
                    out.println(res);
                    System.out.println("Response to client: " + res);
                    pipe.close(); // close this socket!!!
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
            server.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static MessageModel process(MessageModel request) {
        if (request.code == MessageModel.GET_PRODUCT) {
            int id = Integer.parseInt(request.data);
            System.out.println("GET command from Client with id = " + id);
            ProductModel model = dao.loadProduct(id);
            if (model == null)
                return new MessageModel(IDataAccess.PRODUCT_LOAD_ID_NOT_FOUND, null);
            else
                return new MessageModel(IDataAccess.PRODUCT_LOAD_OK,gson.toJson(model));
        }
        if (request.code == MessageModel.PUT_PRODUCT) {
            ProductModel model = gson.fromJson(request.data, ProductModel.class);
            System.out.println("PUT command from Client with product = " + model);
            boolean saved = dao.saveProduct(model);
            if (saved) // save successfully!
                return new MessageModel(IDataAccess.PRODUCT_SAVE_OK, null);
            else
                return new MessageModel(IDataAccess.PRODUCT_SAVE_FAILED, null);
        }
        if (request.code == MessageModel.GET_CUSTOMER) {
            int id = Integer.parseInt(request.data);
            System.out.println("GET command from Client with id = " + id);
            CustomerModel model = dao.loadCustomer(id);
            if (model == null)
                return new MessageModel(IDataAccess.CUSTOMER_LOAD_ID_NOT_FOUND, null);
            else
                return new MessageModel(IDataAccess.CUSTOMER_LOAD_OK,gson.toJson(model));
        }
        if (request.code == MessageModel.PUT_CUSTOMER) {
            CustomerModel customerModel = gson.fromJson(request.data, CustomerModel.class);
            System.out.println("PUT command from Client with customer = " + customerModel);
            boolean saved = dao.saveCustomer(customerModel);
            if (saved) // save successfully!
                return new MessageModel(IDataAccess.CUSTOMER_SAVE_OK, null);
            else
                return new MessageModel(IDataAccess.CUSTOMER_SAVE_FAILED, null);
        }
        if (request.code == MessageModel.GET_ORDER) {
            int id = Integer.parseInt(request.data);
            System.out.println("GET command from Client with id = " + id);
            PurchaseModel model = dao.loadPurchase(id);
            if (model == null)
                return new MessageModel(IDataAccess.ORDER_LOAD_ID_NOT_FOUND, null);
            else
                return new MessageModel(IDataAccess.ORDER_LOAD_OK,gson.toJson(model));
        }
        if (request.code == MessageModel.PUT_ORDER) {
            PurchaseModel purchaseModel = gson.fromJson(request.data, PurchaseModel.class);
            System.out.println("PUT command from Client with purchase = " + purchaseModel);
            boolean saved = dao.savePurchase(purchaseModel);
            if (saved) // save successfully!
                return new MessageModel(IDataAccess.ORDER_SAVE_OK, null);
            else
                return new MessageModel(IDataAccess.ORDER_SAVE_FAILED, null);
        }
        return null;
    }
}