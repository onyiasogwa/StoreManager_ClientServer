
public class HTMLReceiptBuilder implements IReceiptBuilder {
    StringBuilder sb = new StringBuilder();
    @Override
    public void appendHeader(String s) {
        sb.append("<html>\n");

        sb.append("<h1>").append(s).append("</h1>\n");
        //sb.append("\n------------------------------------------\n");
        sb.append("<hr color=\"red\"  width=\"100%\" >\n");
    }

    @Override
    public void appendCustomer(CustomerModel customer) {
        sb.append("<p><b>Customer ID:</b> " + customer.mCustomerID + "</p>");
        sb.append("<p><b>Name:</b> " + customer.mName + "</p>");
        sb.append("<p><b>Phone:</b> " + customer.mPhone + "</p>");
        sb.append("<p><b>Address:</b> " + customer.mAddress + "</p>");
    }

    @Override
    public void appendProduct(ProductModel product) {
        sb.append("<h2>Order information:</h2>\n");
        sb.append("<table style=\"width:50%\" border = \"1\">\n");
        sb.append("<tr>");
        sb.append("<th> ProductID </th>" );
        sb.append("<th> Product Name </th>" );
        sb.append("<th> Price </th>" );
        sb.append("</tr>\n");

        sb.append("<tr>");
        sb.append("<td> " + product.mProductID + " </td>" );
        sb.append("<td> " + product.mName + " </td>" );
        sb.append("<td> " + product.mPrice + " </td>" );
        sb.append("</tr>\n");

        sb.append("</table>\n");
//        sb.append("<hr color=\"red\"  width=\"100%\" >\n");
//        sb.append(String.format("<p>%10s  %20s %8s</p>",
//                "<b>ProductID</b>",
//                "<b>Product Name</b>",
//                "<b>Price</b>"));

//        sb.append("<hr color=\"red\"  width=\"100%\" >\n");

//        sb.append(String.format("<p>%10d  %20s %8.2f</p>",
//                product.mProductID,
//                product.mName,
//                product.mPrice));
//        sb.append("<hr color=\"red\"  width=\"100%\" >\n");

    }

    @Override
    public void appendPurchase(PurchaseModel purchase) {
        sb.append(String.format("<p>                  Subtotal: %8.2f</p>", purchase.mCost));
        sb.append(String.format("<p>                       Tax: %8.2f</p>", purchase.mTax));
        sb.append(String.format("<p>                     TOTAL: %8.2f</p>", purchase.mTotalCost));
    }

    @Override
    public void appendFooter(String s) {
        sb.append("<hr color=\"red\"  width=\"100%\" >\n");
        sb.append("<h2>").append(s).append("</h2>");
        sb.append("\n</html>\n"); // end tag of the whole document!
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}
