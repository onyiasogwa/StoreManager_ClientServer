
public class StoreClient {
    IDataAccess mAdapter;
    MainUI mMainUI;

    static StoreClient instance = null;

    public static StoreClient getInstance() {
        if (instance == null) {
            instance = new StoreClient();
            instance.mAdapter = new RemoteDataAdapter("localhost", 10000);
//            instance.mAdapter = new SQLiteDataAdapter("C:\\Users\\onyie\\IdeaProjects\\StoreManager_ClientServer\\src\\store.db");
            instance.mMainUI = new MainUI();
        }
        return instance;
    }

    public IDataAccess getDataAccess() {
        return mAdapter;
    }


    public static void main(String args[]) {
        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                System.out.println(args[i]);
            }
            if (args[0].equals("SQLite"))
                StoreClient.getInstance().mAdapter = new SQLiteDataAdapter(args[1]);
            if (args[0].equals("remote"))
                StoreClient.getInstance().mAdapter = new RemoteDataAdapter(args[1], Integer.parseInt(args[2]));
        }

        StoreClient.getInstance().mMainUI.view.setVisible(true);
    }

}
