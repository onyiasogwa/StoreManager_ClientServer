import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageProductUI {
    public JFrame view;
    public JTextField txtProductID = new JTextField(20);
    public JTextField txtName = new JTextField(20);
    public JTextField txtPrice = new JTextField(20);
    public JTextField txtQuantity = new JTextField(20);
    public JTextField txtVendor = new JTextField(20);

    public JButton btnLoad = new JButton("Load");
    public JButton btnSave = new JButton("Save");


    public ManageProductUI() {
        view = new JFrame();
        view.setTitle("Manage Products");
        view.setSize(600, 400);
        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Container pane = view.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));

        JPanel buttonPane = new JPanel(new FlowLayout());
        buttonPane.add(btnLoad);
        buttonPane.add(btnSave);
        pane.add(buttonPane);

        JPanel line = new JPanel(new FlowLayout());
        line.add(new JLabel("ProductID:"));
        line.add(txtProductID);
        pane.add(line);

        line = new JPanel(new FlowLayout());
        line.add(new JLabel("Name:"));
        line.add(txtName);
        pane.add(line);

        line = new JPanel(new FlowLayout());
        line.add(new JLabel("Price:"));
        line.add(txtPrice);
        pane.add(line);

        line = new JPanel(new FlowLayout());
        line.add(new JLabel("Quantity:"));
        line.add(txtQuantity);
        pane.add(line);

        line = new JPanel(new FlowLayout());
        line.add(new JLabel("Vendor:"));
        line.add(txtVendor);
        pane.add(line);

        btnLoad.addActionListener(new LoadButtonListener());
        btnSave.addActionListener(new SaveButtonListener());

    }

    public void run() {
        view.setVisible(true);
    }

    private class LoadButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            ProductModel product = new ProductModel();
            String s = txtProductID.getText();
            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "ProductID could not be EMPTY!!!");
                return;
            }
            try {
                product.mProductID = Integer.parseInt(s);
            }
            catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "ProductID is INVALID (not a number)!!!");
                return;
            }
            IDataAccess adapter = StoreClient.getInstance().getDataAccess();
            product = adapter.loadProduct(product.mProductID);
            if (product == null) {
                JOptionPane.showMessageDialog(null,
                        "Product does NOT exist!");
                return;
            }
            txtName.setText(product.mName);
            txtPrice.setText(Double.toString(product.mPrice));
            txtQuantity.setText(Double.toString(product.mQuantity));
            txtVendor.setText(product.mVendor);
        }
    }

    class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            ProductModel product = new ProductModel();
            String s = txtProductID.getText();
            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "ProductID could not be EMPTY!!!");
                return;
            }
            try {
                product.mProductID = Integer.parseInt(s);
            }
            catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "ProductID is INVALID (not a number)!!!");
                return;
            }

            s = txtName.getText();
            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "Product Name could not be EMPTY!!!");
                return;
            }
            product.mName = s;

            s = txtPrice.getText();
            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "Price could not be EMPTY!!!");
                return;
            }

            try {
                product.mPrice = Double.parseDouble(s);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "Price is INVALID (not a number)!!!");
                return;
            }

            s = txtQuantity.getText();
            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "Quantity could not be EMPTY!!!");
                return;
            }

            try {
                product.mQuantity = Double.parseDouble(s);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "Quantity is INVALID (not a number)!!!");
                return;
            }

            s = txtVendor.getText();
            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "Vendor could not be EMPTY!!!");
                return;
            }
            product.mVendor = s;

            IDataAccess adapter = StoreClient.getInstance().getDataAccess();
            if (adapter.saveProduct(product))
                JOptionPane.showMessageDialog(null,
                        "Product is saved successfully!");
            else {
                System.out.println(adapter.getErrorMessage());
            }
        }
    }
}