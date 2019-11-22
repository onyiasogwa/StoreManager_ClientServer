import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageCustomerUI {
    public JFrame view;
    public JTextField txtCustomerID = new JTextField(20);
    public JTextField txtName = new JTextField(20);
    public JTextField txtAddress = new JTextField(20);
    public JTextField txtPhone = new JTextField(20);

    public JButton btnLoad = new JButton("Load");
    public JButton btnSave = new JButton("Save");


    public ManageCustomerUI() {
        view = new JFrame();
        view.setTitle("Manage Customers");
        view.setSize(600, 400);
        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Container pane = view.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));

        JPanel buttonPane = new JPanel(new FlowLayout());
        buttonPane.add(btnLoad);
        buttonPane.add(btnSave);
        pane.add(buttonPane);

        JPanel line1 = new JPanel(new FlowLayout());
        line1.add(new JLabel("CustomerID:"));
        line1.add(txtCustomerID);
        pane.add(line1);

        JPanel line2 = new JPanel(new FlowLayout());
        line2.add(new JLabel("Name:"));
        line2.add(txtName);
        pane.add(line2);

        JPanel line3 = new JPanel(new FlowLayout());
        line3.add(new JLabel("Address:"));
        line3.add(txtAddress);
        pane.add(line3);

        JPanel line4 = new JPanel(new FlowLayout());
        line4.add(new JLabel("Phone:"));
        line4.add(txtPhone);
        pane.add(line4);

        btnLoad.addActionListener(new LoadButtonListener());
        btnSave.addActionListener(new SaveButtonListener());

    }

    public void run() {
        view.setVisible(true);
    }

    private class LoadButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            CustomerModel customerModel = new CustomerModel();
            String s = txtCustomerID.getText();
            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "CustomerID could not be EMPTY!!!");
                return;
            }
            try {
                customerModel.mCustomerID = Integer.parseInt(s);
            }
            catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "CustomerID is INVALID (not a number)!!!");
                return;
            }
            IDataAccess adapter = StoreClient.getInstance().getDataAccess();
            customerModel = adapter.loadCustomer(customerModel.mCustomerID);
            if (customerModel == null) {
                JOptionPane.showMessageDialog(null,
                        "Customer does NOT exist!");
                return;
            }
            txtName.setText(customerModel.mName);
            txtAddress.setText(customerModel.mAddress);
            txtPhone.setText(customerModel.mPhone);
        }
    }

    class SaveButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            CustomerModel customerModel = new CustomerModel();

            String s = txtCustomerID.getText();

            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "CustomerId could not be EMPTY!!!");
                return;
            }
            try {
                customerModel.mCustomerID = Integer.parseInt(s);
            }
            catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "CustomerId is INVALID (not a number)!!!");
                return;
            }

            s = txtName.getText();
            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "Customer Name could not be EMPTY!!!");
                return;
            }
            customerModel.mName = s;

            s = txtAddress.getText();
            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "Address could not be EMPTY!!!");
                return;
            }

            customerModel.mAddress = s;

            s = txtPhone.getText();
            if (s.length() != 10) {
                JOptionPane.showMessageDialog(null,
                        "Invalid Phone Number (Format: 3344444444) !!!");
                return;
            }
            customerModel.mPhone = s;

            IDataAccess adapter = StoreClient.getInstance().getDataAccess();
            if (adapter.saveCustomer(customerModel))
                JOptionPane.showMessageDialog(null,
                        "Customer is saved successfully!");
            else {
                System.out.println(adapter.getErrorMessage());
            }

        }
    }
}
