package shop.api.inf;

import shop.api.entity.Category;
import shop.api.entity.Product;
import shop.api.entity.Supplier;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Collection;

public interface IDataAccess {
    public Category getCategoryById(Long id) throws InvocationTargetException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException;

    public Collection<Category> getCategories() throws InvocationTargetException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException;

    public Long addCategory(Category category) throws SQLException;

    public Integer updateCategory(Category category) throws SQLException;

    public Integer deleteCategory(Long id) throws SQLException;

    public Product getProductById(Long id, String sku) throws InvocationTargetException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException;

    public Collection<Product> getIdenticalProductsById(Long id) throws InvocationTargetException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException;

    public Collection<Product> getProducts() throws InvocationTargetException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException;

    public Collection<Product> getProductsByCategory(Long categoryId) throws InvocationTargetException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException;

    public Long addProduct(Product product) throws SQLException;

    public Integer addProductInfo(Product product) throws SQLException;

    public Integer updateProduct(Product product) throws SQLException;

    public Integer deleteProduct(Long id) throws SQLException;

    public Integer deleteProductInfo(Long id, String sku) throws SQLException;

    public Supplier getSupplierById(Long id) throws InvocationTargetException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException;

    public Collection<Supplier> getSuppliers() throws InvocationTargetException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException;

    public Long addSupplier(Supplier supplier) throws SQLException;

    public Integer updateSupplier(Supplier supplier) throws SQLException;

    public Integer deleteSupplier(Long id) throws SQLException;
}
