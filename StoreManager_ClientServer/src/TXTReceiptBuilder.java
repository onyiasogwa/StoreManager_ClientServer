
public class TXTReceiptBuilder implements IReceiptBuilder {
    StringBuilder sb = new StringBuilder();
    @Override
    public void appendHeader(String s) {
        sb.append(s);
        sb.append("\n------------------------------------------\n");
    }

    @Override
    public void appendCustomer(CustomerModel customer) {
        sb.append("Customer ID: " + customer.mCustomerID + "\n");
        sb.append("Name: " + customer.mName + "\n");
        sb.append("Phone: " + customer.mPhone + "\n");
        sb.append("Address: " + customer.mAddress + "\n");
    }

    @Override
    public void appendProduct(ProductModel product) {
        sb.append("Order information: \n------------------------------------------------\n");
        sb.append(String.format("%10s  %20s %8s\n",
                "ProductID",
                "Product Name",
                "Price"));
        sb.append("------------------------------------------------\n");

        sb.append(String.format("%10d  %20s %8.2f\n",
                product.mProductID,
                product.mName,
                product.mPrice));
        sb.append("------------------------------------------------\n");
    }

    @Override
    public void appendPurchase(PurchaseModel purchase) {
        sb.append(String.format("                  Subtotal: %8.2f\n", purchase.mCost));
        sb.append(String.format("                       Tax: %8.2f\n", purchase.mTax));
        sb.append(String.format("                     TOTAL: %8.2f\n", purchase.mTotalCost));
    }

    @Override
    public void appendFooter(String s) {
        sb.append(s);
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}
