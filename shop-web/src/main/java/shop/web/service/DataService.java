package shop.web.service;

import shop.api.entity.Category;
import shop.api.entity.Product;
import shop.api.entity.Supplier;
import shop.api.inf.IDataAccess;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Collection;

public class DataService {

    private IDataAccess dataAccess;

    public Category getCategoryById(Long id) throws InvocationTargetException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        return dataAccess.getCategoryById(id);
    }

    public Collection<Category> getCategories() throws InvocationTargetException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        return dataAccess.getCategories();
    }

    public Long addCategory(Category category) throws SQLException {
        return dataAccess.addCategory(category);
    }

    public Integer updateCategory(Category category) throws SQLException {
        return dataAccess.updateCategory(category);
    }

    public Integer deleteCategory(Long id) throws SQLException {
        return dataAccess.deleteCategory(id);
    }

    public Product getProductById(Long id, String sku) throws InvocationTargetException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        return dataAccess.getProductById(id, sku);
    }

    public Collection<Product> getIdenticalProductsById(Long id) throws InvocationTargetException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        return dataAccess.getIdenticalProductsById(id);
    }

    public Collection<Product> getProducts() throws InvocationTargetException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        return dataAccess.getProducts();
    }

    public Collection<Product> getProductsByCategory(Long categoryId) throws InvocationTargetException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        return dataAccess.getProductsByCategory(categoryId);
    }

    public Long addProduct(Product product) throws SQLException {
        return dataAccess.addProduct(product);
    }

    public Integer addProductInfo(Product product) throws SQLException {
        return dataAccess.addProductInfo(product);
    }

    public Integer updateProduct(Product product) throws SQLException {
        return dataAccess.updateProduct(product);
    }

    public Integer deleteProduct(Long id) throws SQLException {
        return dataAccess.deleteProduct(id);
    }

    public Integer deleteProductInfo(Long id, String sku) throws SQLException {
        return dataAccess.deleteProductInfo(id, sku);
    }

    public Supplier getSupplierById(Long id) throws InvocationTargetException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        return dataAccess.getSupplierById(id);
    }

    public Collection<Supplier> getSuppliers() throws InvocationTargetException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        return dataAccess.getSuppliers();
    }

    public Long addSupplier(Supplier supplier) throws SQLException {
        return dataAccess.addSupplier(supplier);
    }

    public Integer updateSupplier(Supplier supplier) throws SQLException {
        return dataAccess.updateSupplier(supplier);
    }

    public Integer deleteSupplier(Long id) throws SQLException {
        return dataAccess.deleteSupplier(id);
    }

    public IDataAccess getDataAccess() {
        return dataAccess;
    }

    public void setDataAccess(IDataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }
}
