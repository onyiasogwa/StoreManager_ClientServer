
public class ProductModel {
    public int mProductID;
    public String mName, mVendor;
    public double mPrice, mQuantity;

    public String toString() {
        return String.format("(%d,\"%s\",%f,%f,%s)", mProductID, mName, mPrice, mQuantity,mVendor);
    }

}
