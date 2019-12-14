import javax.swing.*;

public class StoreClient {
    public static String dbms = "Network";
    public static String path = "localhost:1000";

    IDataAdapter dataAdapter = null;
    private static StoreClient instance = null;

    public static StoreClient getInstance() {
        if (instance == null) {
            instance = new StoreClient(dbms, path);
        }
        return instance;
    }

    private StoreClient(String dbms, String dbfile) {
        if (dbms.equals("Oracle"))
            dataAdapter = new OracleDataAdapter();
        else
        if (dbms.equals("SQLite"))
            dataAdapter = new SQLiteDataAdapter();
        else
        if (dbms.equals("Network"))
            dataAdapter = new NetworkDataAdapter();

        dataAdapter.connect(dbfile);

    }

    public IDataAdapter getDataAdapter() {
        return dataAdapter;
    }

    public void setDataAdapter(IDataAdapter a) {
        dataAdapter = a;
    }


    public void run() {
        LoginUI ui = new LoginUI();
        ui.view.setVisible(true);
    }

    public static void main(String[] args) {
        System.out.println("Hello class!");
        if (args.length > 0) { // having runtime arguments
            dbms = args[0];
            if (args.length == 1) { // do not have 2nd arguments for dbfile
                if (dbms.equals("SQLite")) {
                    JFileChooser fc = new JFileChooser();
                    if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
                        path = fc.getSelectedFile().getAbsolutePath();
                }
                else
                    path = JOptionPane.showInputDialog("Enter address of database server as host:port");
            }
            else
                path = args[1];
        }
        StoreClient.getInstance().run();
    }

}
