import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Calendar;

public class ManagePurchaseUI {
    public JFrame view;

    public JLabel labPurchaseDate = new JLabel("Purchase Date: ");
    public JLabel labPurchaseID = new JLabel("Purchase ID: ");
    public JLabel labCustomerID = new JLabel("Customer ID: ");
    public JLabel labCustomerName = new JLabel("Customer Name: ");
    public JLabel labProductID = new JLabel("Product ID: ");
    public JLabel labProductName = new JLabel("Product Name: ");
    public JLabel labProductPrice = new JLabel("Product Price: ");
    public JLabel labProductQuantity = new JLabel("Purchase Quantity: ");

    public JTextField txtProductID = new JTextField(10);
    public JTextField txtPurchaseID = new JTextField(10);
    public JTextField txtCustomerID = new JTextField(10);
    public JTextField txtPurchaseDate = new JTextField(10);
    public JTextField txtProductName = new JTextField(20);
    public JTextField txtCustomerName = new JTextField(20);
    public JTextField txtPrice = new JTextField(20);
    public JTextField txtQuantity = new JTextField(20);
    public JTextField txtCost = new JTextField(20);
    public JTextField txtTax = new JTextField(20);
    public JTextField txtTotalCost = new JTextField(20);

    public JButton btnLoad = new JButton("Load");
    public JButton btnSave = new JButton("Save");

    ProductModel product;
    CustomerModel customer;
    PurchaseModel purchase;

    public ManagePurchaseUI() {
        view = new JFrame();
        view.setTitle("Add Product");
        view.setSize(500, 400);
        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel pane = new JPanel();
        pane.setLayout(new FlowLayout());
        pane.add(btnLoad); pane.add(btnSave);
        view.getContentPane().add(pane);

        pane = new JPanel();
        pane.setLayout(new GridLayout(12,2, 0,7));
        view.getContentPane().add(pane);

        pane.add(new JLabel("Purchase Date:")); pane.add(txtPurchaseDate);
        pane.add(labPurchaseID); pane.add(txtPurchaseID);
        pane.add(labCustomerID); pane.add(txtCustomerID);
        pane.add(labCustomerName); pane.add(txtCustomerName);
        pane.add(labProductID); pane.add(txtProductID);
        pane.add(labProductName); pane.add(txtProductName);
        pane.add(labProductPrice); pane.add(txtPrice);
        pane.add(labProductQuantity); pane.add(txtQuantity);
        pane.add(new JLabel("Cost")); pane.add(txtCost);
        pane.add(new JLabel("Tax")); pane.add(txtTax);
        pane.add(new JLabel("Total Cost")); pane.add(txtTotalCost);

        txtCustomerID.addFocusListener(new CustomerLoader());
        txtProductID.addFocusListener(new ProductLoader());
        txtQuantity.addFocusListener(new CostCalculator());

        btnLoad.addActionListener(new LoadButtonListener());
        btnSave.addActionListener(new SaveButtonListener());
    }

    private class LoadButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            PurchaseModel purchaseModel = new PurchaseModel();
            String s = txtPurchaseID.getText();
            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "PurchaseID could not be EMPTY!!!");
                return;
            }
            try {
                purchaseModel.mPurchaseID = Integer.parseInt(s);
            }
            catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "PurchaseID is INVALID (not a number)!!!");
                return;
            }
            IDataAccess adapter = StoreClient.getInstance().getDataAccess();
            purchaseModel = adapter.loadPurchase(purchaseModel.mPurchaseID);
            if (purchaseModel == null) {
                JOptionPane.showMessageDialog(null,
                        "Purchase does NOT exist!");
                return;
            }
            txtCustomerID.setText(String.valueOf(purchaseModel.mCustomerID));
            txtProductID.setText(String.valueOf(purchaseModel.mProductID));
            txtTotalCost.setText(String.valueOf(purchaseModel.mTotalCost));
            txtPrice.setText(String.valueOf(purchaseModel.mPrice));
            txtTax.setText(String.valueOf(purchaseModel.mTax));
            txtQuantity.setText(String.valueOf(purchaseModel.mQuantity));
        }
    }

    private class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            PurchaseModel purchaseModel = new PurchaseModel();

            String s = txtPurchaseDate.getText();
            purchaseModel.mDate = s;

            s = txtPurchaseID.getText();
            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "Purchase ID could not be EMPTY!!!");
                return;
            }
            try {
                purchaseModel.mPurchaseID = Integer.parseInt(s);
            }
            catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "Purchase ID is INVALID (not a number)!!!");
                return;
            }

            s = txtCustomerID.getText();
            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "Customer ID could not be EMPTY!!!");
                return;
            }
            try {
                purchaseModel.mCustomerID = Integer.parseInt(s);
            }
            catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "Customer ID is INVALID (not a number)!!!");
                return;
            }

            s = txtProductID.getText();
            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "Product ID could not be EMPTY!!!");
                return;
            }
            try {
                purchaseModel.mProductID = Integer.parseInt(s);
            }
            catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "Product ID is INVALID (not a number)!!!");
                return;
            }

            s = txtPrice.getText();
            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "Price could not be EMPTY!!!");
                return;
            }

            try {
                purchaseModel.mPrice = Double.parseDouble(s);
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
                purchaseModel.mQuantity = Integer.parseInt(s);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "Quantity is INVALID (not a number)!!!");
                return;
            }

            s = txtTax.getText();
            purchaseModel.mTax = Double.parseDouble(s);

            s = txtTotalCost.getText();
            purchaseModel.mTotalCost = Double.parseDouble(s);

            IDataAccess adapter = StoreClient.getInstance().getDataAccess();
            if (adapter.savePurchase(purchaseModel))
                JOptionPane.showMessageDialog(null,
                        "Purchase is saved successfully!");
            else {
                System.out.println(adapter.getErrorMessage());
            }
        }
    }

    class CostCalculator implements FocusListener  {

        @Override
        public void focusGained(FocusEvent focusEvent) {

        }

        @Override
        public void focusLost(FocusEvent focusEvent) {
            String s = txtQuantity.getText();
            try {
                purchase.mQuantity = Integer.parseInt(s);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid Quantity");
                return;
            }

            if (purchase.mQuantity > product.mQuantity) {
                JOptionPane.showMessageDialog(null, "Purchase Quantity > Available Quantity!");
                return;
            }

            purchase.mCost = purchase.mQuantity * product.mPrice;
            txtCost.setText(Double.toString(purchase.mCost));
            purchase.mTax = purchase.mCost * 0.09;
            txtTax.setText(Double.toString(purchase.mTax));
            purchase.mTotalCost = purchase.mCost + purchase.mTax;
            txtTotalCost.setText(Double.toString(purchase.mTotalCost));
        }
    }

    class ProductLoader implements FocusListener  {

        @Override
        public void focusGained(FocusEvent focusEvent) {

        }

        @Override
        public void focusLost(FocusEvent focusEvent) {
            String s = txtProductID.getText();
            try {
                purchase.mProductID = Integer.parseInt(s);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid ProductID");
                return;
            }
            if (product == null || product.mProductID != purchase.mProductID)
                product = StoreClient.getInstance().getDataAccess().loadProduct(purchase.mProductID);
            if (product == null) {
                JOptionPane.showMessageDialog(null, "No Product with ID = " + purchase.mProductID);
                txtProductName.setText("");
                txtPrice.setText("");
                return;
            }

            txtProductName.setText(product.mName);
            txtPrice.setText(Double.toString(product.mPrice));

        }
    }

    class CustomerLoader implements FocusListener  {

        @Override
        public void focusGained(FocusEvent focusEvent) {

        }

        @Override
        public void focusLost(FocusEvent focusEvent) {
            String s = txtCustomerID.getText();
            try {
                purchase.mCustomerID = Integer.parseInt(s);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid CustomerID");
                return;
            }
            if (customer == null || customer.mCustomerID != purchase.mCustomerID)
                customer = StoreClient.getInstance().getDataAccess().loadCustomer(purchase.mCustomerID);
            if (customer == null) {
                JOptionPane.showMessageDialog(null, "No Product with ID = " + purchase.mCustomerID);
                txtCustomerName.setText("");
                return;
            }

            txtCustomerName.setText(customer.mName);

        }
    }


    public void run() {
        purchase = new PurchaseModel();
        txtPurchaseDate.setText(Calendar.getInstance().getTime().toString());
        txtCustomerName.setEnabled(false);
        txtProductName.setEnabled(false);
        txtCost.setEnabled(false);
        txtTax.setEnabled(false);
        txtTotalCost.setEnabled(false);
        view.setVisible(true);
    }
}
