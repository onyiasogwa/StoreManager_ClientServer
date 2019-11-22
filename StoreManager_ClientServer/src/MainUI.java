
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainUI {
    public JFrame view;
    public JButton btnManageProduct = new JButton("Manage Products");
    public JButton btnManageCustomer = new JButton("Manage Customers");
    public JButton btnManagePurchase = new JButton("Manage Purchases");

    public MainUI() {
        view = new JFrame();
        view.setTitle("Store Management System");
        view.setSize(2000, 600);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container pane = view.getContentPane();
        pane.setLayout(new BorderLayout());

        JLabel title = new JLabel("STORE MANAGEMENT SYSTEM");
        title.setFont(new Font("Lucida Calligraphy", Font.BOLD, 50));

        JPanel titlePane = new JPanel(new FlowLayout());

        titlePane.add(title);
        pane.add(titlePane, BorderLayout.PAGE_START);

        JPanel buttonPane = new JPanel(new FlowLayout());
        buttonPane.add(btnManageProduct);
        buttonPane.add(btnManageCustomer);
        buttonPane.add(btnManagePurchase);
        pane.add(buttonPane);

        btnManageProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ManageProductUI ui = new ManageProductUI();
                ui.run();
            }
        });
        btnManagePurchase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ManagePurchaseUI ui = new ManagePurchaseUI();
                ui.run();
            }
        });

        btnManageCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ManageCustomerUI ui = new ManageCustomerUI();
                ui.run();
            }
        });
    }
}