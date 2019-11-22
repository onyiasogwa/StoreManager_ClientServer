import java.util.HashMap;
import java.util.Map;

public class CachedDataAdapter { //implements IDataAccess {
    Map<Integer, ProductModel> cachedProducts = new HashMap<>();
    IDataAccess adapter;

    public CachedDataAdapter(IDataAccess adapter) {
        this.adapter = adapter;
    }


//    @Override
    public void connect(String path) {
        this.adapter.connect(path);
    }

//    @Override
    public ProductModel loadProduct(int id) {
        if (cachedProducts.containsKey(id))
            return cachedProducts.get(id);
        else {
            ProductModel product = adapter.loadProduct(id);
            cachedProducts.put(id, product);
            return product;
        }
    }

//    @Override
    public boolean saveProduct(ProductModel product) {
        adapter.saveProduct(product);
        cachedProducts.put(product.mProductID, product);
        return true;
    }
}
