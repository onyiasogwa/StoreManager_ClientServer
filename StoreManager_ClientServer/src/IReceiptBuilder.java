
public interface IReceiptBuilder {
    public void appendHeader(String s);
    public void appendCustomer(CustomerModel customer);
    public void appendProduct(ProductModel product);
    public void appendPurchase(PurchaseModel purchase);
    public void appendFooter(String s);

    public String toString();
}
