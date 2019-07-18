package shop.data;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.SerializationUtils;
import shop.api.entity.*;
import shop.api.inf.IDataAccess;
import shop.data.utils.DataUtils;
import shop.data.utils.EditUtil;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class DataAccessImpl implements IDataAccess {
    private static final Logger logger = LoggerFactory.getLogger(DataAccessImpl.class.getName());

    private static final String GET_CATEGORY_BY_ID = "SELECT * FROM SHOP.CATEGORY_V WHERE ID = ?";

    private static final String GET_CATEGORIES = "SELECT * FROM SHOP.CATEGORY_V ORDER BY PRIORITY";

    private static final String ADD_CATEGORY = "SELECT SHOP.INS_CATEGORY(?, ?, ?, ?)";

    private static final String UPDATE_CATEGORY = "SELECT SHOP.UPD_CATEGORY(?, ?, ?, ?, ?)";

    private static final String DELETE_CATEGORY = "SELECT SHOP.DEL_CATEGORY(?)";

    private static final String GET_PRODUCT_BY_ID = "SELECT * FROM SHOP.PRODUCT_V WHERE PRODUCT_ID = ? AND PRODUCT_INFO_PRODUCT_INFO_ID = ?";

    private static final String GET_IDENTICAL_PRODUCTS_BY_ID = "SELECT * FROM SHOP.PRODUCT_V WHERE PRODUCT_ID = ?";

    private static final String GET_PRODUCTS = "SELECT * FROM SHOP.PRODUCT_V";

    private static final String GET_PRODUCTS_BY_CATEGORY = "SELECT * FROM SHOP.PRODUCT_V WHERE CATEGORY_ID = ? ORDER BY CATEGORY_PRIORITY";

    private static final String ADD_PRODUCT = "SELECT SHOP.INS_PRODUCT(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String ADD_PRODUCT_INFO = "SELECT SHOP.INS_PRODUCT_INFO(?, ?, ?, ?, ?)";

    private static final String UPDATE_PRODUCT = "SELECT SHOP.UPD_PRODUCT(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String DELETE_PRODUCT = "SELECT SHOP.DEL_PRODUCT(?)";

    private static final String DELETE_PRODUCT_INFO = "SELECT SHOP.DEL_PRODUCT_INFO(?, ?)";

    private static final String GET_SUPPLIER_BY_ID = "SELECT * FROM SHOP.SUPPLIER_V WHERE ID = ?";

    private static final String GET_SUPPLIERS = "SELECT * FROM SHOP.SUPPLIER_V";

    private static final String ADD_SUPPLIER = "SELECT SHOP.INS_SUPPLIER(?, ?, ?, ?, ?)";

    private static final String UPDATE_SUPPLIER = "SELECT SHOP.UPD_SUPPLIER(?, ?, ?, ?, ?, ?)";

    private static final String DELETE_SUPPLIER = "SELECT SHOP.DEL_SUPPLIER(?)";

    /**
     * Источник данных
     */
    private ComboPooledDataSource sourceData;

    @Override
    public Category getCategoryById(Long id) throws InvocationTargetException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Category res = null;
        Map<Long, Category> categories = new HashMap<>();
        try (Connection conn = getSourceData().getConnection()) {
            try(PreparedStatement pstm = conn.prepareStatement(GET_CATEGORY_BY_ID)) {
                EditUtil.setLong(pstm, 1, id);
                ResultSet rs = pstm.executeQuery();
                fillCategories(categories, rs);
                if (categories.size() > 0) {
                    res = categories.get(id);
                }
                return res;
            } catch (Exception ex) {
                logger.error("Execute sql " + GET_CATEGORY_BY_ID, ex);
                throw ex;
            } finally {
                if (!conn.getAutoCommit()) {
                    conn.commit();
                }
            }
        } catch (Exception ex) {
            logger.error("Execute sql " + GET_CATEGORY_BY_ID, ex);
            throw ex;
        }
    }

    @Override
    public Collection<Category> getCategories() throws InvocationTargetException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Map<Long, Category> res = new HashMap<>();
        try (Connection conn = getSourceData().getConnection()) {
            try(PreparedStatement pstm = conn.prepareStatement(GET_CATEGORIES)) {
                ResultSet rs = pstm.executeQuery();
                fillCategories(res, rs);
                return res.values();
            } catch (Exception ex) {
                logger.error("Execute sql " + GET_CATEGORIES, ex);
                throw ex;
            } finally {
                if (!conn.getAutoCommit()) {
                    conn.commit();
                }
            }
        } catch (Exception ex) {
            logger.error("Execute sql " + GET_CATEGORIES, ex);
            throw ex;
        }
    }

    private void fillCategories(Map<Long, Category> res, ResultSet rs) throws SQLException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        while (rs.next()) {
            Category category = res.get(rs.getLong("id"));
            if (null == category) {
                category = new Category();
                DataUtils.readObject(category, rs, "");
                category.setChildCategories(new ArrayList<>());

                Category parentCategory = res.get(category.getParentCategory());
                if (null != parentCategory) {
                    parentCategory.getChildCategories().add(category);
                }
            }
            res.put(category.getId(), category);
        }
    }

    @Override
    public Long addCategory(Category category) throws SQLException {
        try (Connection conn = getSourceData().getConnection()) {
            try(PreparedStatement pstm = conn.prepareStatement(ADD_CATEGORY)) {
                EditUtil.setString(pstm, 1, category.getDescription());
                EditUtil.setString(pstm, 2, category.getLatName());
                EditUtil.setInt(pstm, 3, category.getPriority());
                EditUtil.setLong(pstm, 4, category.getParentCategory());
                ResultSet rs = pstm.executeQuery();
                if (rs.next()){
                    Long res = rs.getLong(1);
                    if (rs.wasNull()) {
                        res = null;
                    }
                    return res;
                }
            } catch (Exception ex) {
                logger.error("Execute sql " + ADD_CATEGORY, ex);
                throw ex;
            } finally {
                if (!conn.getAutoCommit()) {
                    conn.commit();
                }
            }
        } catch (Exception ex) {
            logger.error("Execute sql " + ADD_CATEGORY, ex);
            throw ex;
        }
        return null;
    }

    @Override
    public Integer updateCategory(Category category) throws SQLException {
        try (Connection conn = getSourceData().getConnection()) {
            try(PreparedStatement pstm = conn.prepareStatement(UPDATE_CATEGORY)) {
                EditUtil.setLong(pstm, 1, category.getId());
                EditUtil.setString(pstm, 2, category.getDescription());
                EditUtil.setString(pstm, 3, category.getLatName());
                EditUtil.setInt(pstm, 4, category.getPriority());
                EditUtil.setLong(pstm, 5, category.getParentCategory());
                ResultSet rs = pstm.executeQuery();
                if (rs.next()){
                    Integer res = rs.getInt(1);
                    if (rs.wasNull()) {
                        res = null;
                    }
                    return res;
                }
            } catch (Exception ex) {
                logger.error("Execute sql " + UPDATE_CATEGORY, ex);
                throw ex;
            } finally {
                if (!conn.getAutoCommit()) {
                    conn.commit();
                }
            }
        } catch (Exception ex) {
            logger.error("Execute sql " + UPDATE_CATEGORY, ex);
            throw ex;
        }
        return null;
    }

    @Override
    public Integer deleteCategory(Long id) throws SQLException {
        try (Connection conn = getSourceData().getConnection()) {
            try(PreparedStatement pstm = conn.prepareStatement(DELETE_CATEGORY)) {
                EditUtil.setLong(pstm, 1, id);
                ResultSet rs = pstm.executeQuery();
                if (rs.next()){
                    Integer res = rs.getInt(1);
                    if (rs.wasNull()) {
                        res = null;
                    }
                    return res;
                }
            } catch (Exception ex) {
                logger.error("Execute sql " + DELETE_CATEGORY, ex);
                throw ex;
            } finally {
                if (!conn.getAutoCommit()) {
                    conn.commit();
                }
            }
        } catch (Exception ex) {
            logger.error("Execute sql " + DELETE_CATEGORY, ex);
            throw ex;
        }
        return null;
    }

    @Override
    public Product getProductById(Long id, String sku) throws InvocationTargetException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Product res = null;
        Map<String, Product> products = new HashMap<>();
        try (Connection conn = getSourceData().getConnection()) {
            try(PreparedStatement pstm = conn.prepareStatement(GET_PRODUCT_BY_ID)) {
                EditUtil.setLong(pstm, 1, id);
                EditUtil.setString(pstm, 2, sku);
                ResultSet rs = pstm.executeQuery();
                fillProducts(products, rs);
                if (products.size() > 0) {
                    res = products.get(id + sku);
                }
                return res;
            } catch (Exception ex) {
                logger.error("Execute sql " + GET_PRODUCT_BY_ID, ex);
                throw ex;
            } finally {
                if (!conn.getAutoCommit()) {
                    conn.commit();
                }
            }
        } catch (Exception ex) {
            logger.error("Execute sql " + GET_PRODUCT_BY_ID, ex);
            throw ex;
        }
    }

    @Override
    public Collection<Product> getIdenticalProductsById(Long id) throws InvocationTargetException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Map<String, Product> res = new HashMap<>();
        try (Connection conn = getSourceData().getConnection()) {
            try(PreparedStatement pstm = conn.prepareStatement(GET_IDENTICAL_PRODUCTS_BY_ID)) {
                EditUtil.setLong(pstm, 1, id);
                ResultSet rs = pstm.executeQuery();
                fillProducts(res, rs);
                return res.values();
            } catch (Exception ex) {
                logger.error("Execute sql " + GET_IDENTICAL_PRODUCTS_BY_ID, ex);
                throw ex;
            } finally {
                if (!conn.getAutoCommit()) {
                    conn.commit();
                }
            }
        } catch (Exception ex) {
            logger.error("Execute sql " + GET_IDENTICAL_PRODUCTS_BY_ID, ex);
            throw ex;
        }
    }

    @Override
    public Collection<Product> getProducts() throws InvocationTargetException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Map<String , Product> res = new HashMap<>();
        try (Connection conn = getSourceData().getConnection()) {
            try(PreparedStatement pstm = conn.prepareStatement(GET_PRODUCTS)) {
                ResultSet rs = pstm.executeQuery();
                fillProducts(res, rs);
                return res.values();
            } catch (Exception ex) {
                logger.error("Execute sql " + GET_PRODUCTS, ex);
                throw ex;
            } finally {
                if (!conn.getAutoCommit()) {
                    conn.commit();
                }
            }
        } catch (Exception ex) {
            logger.error("Execute sql " + GET_PRODUCTS, ex);
            throw ex;
        }
    }

    @Override
    public Collection<Product> getProductsByCategory(Long categoryId) throws InvocationTargetException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Map<String, Product> res = new HashMap<>();
        try (Connection conn = getSourceData().getConnection()) {
            try(PreparedStatement pstm = conn.prepareStatement(GET_PRODUCTS_BY_CATEGORY)) {
                EditUtil.setLong(pstm, 1, categoryId);
                ResultSet rs = pstm.executeQuery();
                fillProducts(res, rs);
                return res.values();
            } catch (Exception ex) {
                logger.error("Execute sql " + GET_PRODUCTS_BY_CATEGORY, ex);
                throw ex;
            } finally {
                if (!conn.getAutoCommit()) {
                    conn.commit();
                }
            }
        } catch (Exception ex) {
            logger.error("Execute sql " + GET_PRODUCTS_BY_CATEGORY, ex);
            throw ex;
        }
    }

    private void fillProducts(Map<String, Product> res, ResultSet rs) throws SQLException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Map<Long, Category> categories = new HashMap<>();
        while (rs.next()) {
            Product product = res.get(rs.getLong("product_id") + rs.getString("product_info_sku"));
            if (null == product) {
                product = new Product();
                product.setCategories(new ArrayList<>());
                DataUtils.readObject(product, rs, "product_");
                DataUtils.readObject(product, rs, "product_info_");
                product.setProductQuantity(new ArrayList<>());
                res.put(product.getId() + product.getSku(), product);
            }
            ProductQuantity productQuantity = new ProductQuantity();
            DataUtils.readObject(productQuantity, rs, "product_quantity_");
            product.getProductQuantity().add(productQuantity);

            if (null != rs.getObject("category_id")) {
                Category category = categories.get(rs.getLong("category_id"));
                if (null == category) {
                    category = new Category();
                    DataUtils.readObject(category, rs, "category_");
                    category.setChildCategories(new ArrayList<>());
                    category.setProducts(new ArrayList<>());

                    Category parentCategory = categories.get(category.getParentCategory());
                    if (null != parentCategory) {
                        parentCategory.getChildCategories().add(category);
                    }
                    categories.put(category.getId(), category);
                }
                product.getCategories().add(category);
            }
        }
    }

    @Override
    public Long addProduct(Product product) throws SQLException {
        return null;
    }

    @Override
    public Integer addProductInfo(Product product) throws SQLException {
        return null;
    }

    @Override
    public Integer updateProduct(Product product) throws SQLException {
        return null;
    }

    @Override
    public Integer deleteProduct(Long id) throws SQLException {
        return null;
    }

    @Override
    public Integer deleteProductInfo(Long id, String sku) throws SQLException {
        return null;
    }

    /*    @Override
    public Long addProduct(Product product) throws SQLException {
        try (Connection conn = getSourceData().getConnection()) {
            try(PreparedStatement pstm = conn.prepareStatement(ADD_PRODUCT)) {
                EditUtil.setString(pstm, 1, product.getName());
                EditUtil.setString(pstm, 2, product.getDescription());
                EditUtil.setFloat(pstm, 3, product.getPrice());
                EditUtil.setString(pstm, 4, product.getComposition());
                EditUtil.setInt(pstm, 5, product.getExternalId());
                EditUtil.setString(pstm, 6, product.getMaterial());
                EditUtil.setLong(pstm, 7, null != product.getSupplier() ? product.getSupplier().getId() : null);
                EditUtil.setString(pstm, 8, product.getSku());
                EditUtil.setFloat(pstm, 9, product.getQuantity());
                EditUtil.setFloat(pstm, 10, product.getSize());
                EditUtil.setString(pstm, 11, product.getColor());
                ResultSet rs = pstm.executeQuery();
                if (rs.next()){
                    Long res = rs.getLong(1);
                    if (rs.wasNull()) {
                        res = null;
                    }
                    return res;
                }
            } catch (Exception ex) {
                logger.error("Execute sql " + ADD_PRODUCT, ex);
                throw ex;
            } finally {
                if (!conn.getAutoCommit()) {
                    conn.commit();
                }
            }
        } catch (Exception ex) {
            logger.error("Execute sql " + ADD_PRODUCT, ex);
            throw ex;
        }
        return null;
    }

    @Override
    public Integer addProductInfo(Product product) throws SQLException {
        try (Connection conn = getSourceData().getConnection()) {
            try(PreparedStatement pstm = conn.prepareStatement(ADD_PRODUCT_INFO)) {
                EditUtil.setLong(pstm, 1, product.getId());
                EditUtil.setString(pstm, 2, product.getSku());
                EditUtil.setFloat(pstm, 3, product.getQuantity());
                EditUtil.setFloat(pstm, 4, product.getSize());
                EditUtil.setString(pstm, 5, product.getColor());
                ResultSet rs = pstm.executeQuery();
                if (rs.next()){
                    Integer res = rs.getInt(1);
                    if (rs.wasNull()) {
                        res = null;
                    }
                    return res;
                }
            } catch (Exception ex) {
                logger.error("Execute sql " + ADD_PRODUCT_INFO, ex);
                throw ex;
            } finally {
                if (!conn.getAutoCommit()) {
                    conn.commit();
                }
            }
        } catch (Exception ex) {
            logger.error("Execute sql " + ADD_PRODUCT_INFO, ex);
            throw ex;
        }
        return null;
    }

    @Override
    public Integer updateProduct(Product product) throws SQLException {
        try (Connection conn = getSourceData().getConnection()) {
            try(PreparedStatement pstm = conn.prepareStatement(UPDATE_PRODUCT)) {
                EditUtil.setLong(pstm, 1, product.getId());
                EditUtil.setString(pstm, 2, product.getName());
                EditUtil.setString(pstm, 3, product.getDescription());
                EditUtil.setFloat(pstm, 4, product.getPrice());
                EditUtil.setString(pstm, 5, product.getComposition());
                EditUtil.setInt(pstm, 6, product.getExternalId());
                EditUtil.setString(pstm, 7, product.getMaterial());
                EditUtil.setLong(pstm, 8, null != product.getSupplier() ? product.getSupplier().getId() : null);
                EditUtil.setString(pstm, 9, product.getSku());
                EditUtil.setFloat(pstm, 10, product.getQuantity());
                EditUtil.setFloat(pstm, 11, product.getSize());
                EditUtil.setString(pstm, 12, product.getColor());
                ResultSet rs = pstm.executeQuery();
                if (rs.next()){
                    Integer res = rs.getInt(1);
                    if (rs.wasNull()) {
                        res = null;
                    }
                    return res;
                }
            } catch (Exception ex) {
                logger.error("Execute sql " + UPDATE_PRODUCT, ex);
                throw ex;
            } finally {
                if (!conn.getAutoCommit()) {
                    conn.commit();
                }
            }
        } catch (Exception ex) {
            logger.error("Execute sql " + UPDATE_PRODUCT, ex);
            throw ex;
        }
        return null;
    }

    @Override
    public Integer deleteProduct(Long id) throws SQLException {
        try (Connection conn = getSourceData().getConnection()) {
            try(PreparedStatement pstm = conn.prepareStatement(DELETE_PRODUCT)) {
                EditUtil.setLong(pstm, 1, id);
                ResultSet rs = pstm.executeQuery();
                if (rs.next()){
                    Integer res = rs.getInt(1);
                    if (rs.wasNull()) {
                        res = null;
                    }
                    return res;
                }
            } catch (Exception ex) {
                logger.error("Execute sql " + DELETE_PRODUCT, ex);
                throw ex;
            } finally {
                if (!conn.getAutoCommit()) {
                    conn.commit();
                }
            }
        } catch (Exception ex) {
            logger.error("Execute sql " + DELETE_PRODUCT, ex);
            throw ex;
        }
        return null;
    }

    @Override
    public Integer deleteProductInfo(Long id, String sku) throws SQLException {
        try (Connection conn = getSourceData().getConnection()) {
            try(PreparedStatement pstm = conn.prepareStatement(DELETE_PRODUCT_INFO)) {
                EditUtil.setLong(pstm, 1, id);
                EditUtil.setString(pstm, 2, sku);
                ResultSet rs = pstm.executeQuery();
                if (rs.next()){
                    Integer res = rs.getInt(1);
                    if (rs.wasNull()) {
                        res = null;
                    }
                    return res;
                }
            } catch (Exception ex) {
                logger.error("Execute sql " + DELETE_PRODUCT_INFO, ex);
                throw ex;
            } finally {
                if (!conn.getAutoCommit()) {
                    conn.commit();
                }
            }
        } catch (Exception ex) {
            logger.error("Execute sql " + DELETE_PRODUCT_INFO, ex);
            throw ex;
        }
        return null;
    }*/

    @Override
    public Supplier getSupplierById(Long id) throws InvocationTargetException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Supplier res = null;
        Map<Long, Supplier> suppliers = new HashMap<>();
        try (Connection conn = getSourceData().getConnection()) {
            try(PreparedStatement pstm = conn.prepareStatement(GET_SUPPLIER_BY_ID)) {
                EditUtil.setLong(pstm, 1, id);
                ResultSet rs = pstm.executeQuery();
                fillSuppliers(suppliers, rs);
                if (suppliers.size() > 0) {
                    res = suppliers.get(id);
                }
                return res;
            } catch (Exception ex) {
                logger.error("Execute sql " + GET_SUPPLIER_BY_ID, ex);
                throw ex;
            } finally {
                if (!conn.getAutoCommit()) {
                    conn.commit();
                }
            }
        } catch (Exception ex) {
            logger.error("Execute sql " + GET_SUPPLIER_BY_ID, ex);
            throw ex;
        }
    }

    @Override
    public Collection<Supplier> getSuppliers() throws InvocationTargetException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Map<Long, Supplier> res = new HashMap<>();
        try (Connection conn = getSourceData().getConnection()) {
            try(PreparedStatement pstm = conn.prepareStatement(GET_SUPPLIERS)) {
                ResultSet rs = pstm.executeQuery();
                fillSuppliers(res, rs);
                return res.values();
            } catch (Exception ex) {
                logger.error("Execute sql " + GET_SUPPLIERS, ex);
                throw ex;
            } finally {
                if (!conn.getAutoCommit()) {
                    conn.commit();
                }
            }
        } catch (Exception ex) {
            logger.error("Execute sql " + GET_SUPPLIERS, ex);
            throw ex;
        }
    }

    private void fillSuppliers(Map<Long, Supplier> res, ResultSet rs) throws SQLException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        while (rs.next()) {
            Supplier supplier = res.get(rs.getLong("id"));
            if (null == supplier) {
                supplier = new Supplier();
                DataUtils.readObject(supplier, rs, "");
                res.put(supplier.getId(), supplier);
            }
        }
    }

    @Override
    public Long addSupplier(Supplier supplier) throws SQLException {
        try (Connection conn = getSourceData().getConnection()) {
            try(PreparedStatement pstm = conn.prepareStatement(ADD_SUPPLIER)) {
                EditUtil.setString(pstm, 1, supplier.getName());
                EditUtil.setString(pstm, 2, supplier.getDescription());
                EditUtil.setString(pstm, 3, supplier.getAddress());
                EditUtil.setString(pstm, 4, supplier.getPhone());
                EditUtil.setString(pstm, 5, supplier.getEmail());
                ResultSet rs = pstm.executeQuery();
                if (rs.next()){
                    Long res = rs.getLong(1);
                    if (rs.wasNull()) {
                        res = null;
                    }
                    return res;
                }
            } catch (Exception ex) {
                logger.error("Execute sql " + ADD_SUPPLIER, ex);
                throw ex;
            } finally {
                if (!conn.getAutoCommit()) {
                    conn.commit();
                }
            }
        } catch (Exception ex) {
            logger.error("Execute sql " + ADD_SUPPLIER, ex);
            throw ex;
        }
        return null;
    }

    @Override
    public Integer updateSupplier(Supplier supplier) throws SQLException {
        try (Connection conn = getSourceData().getConnection()) {
            try(PreparedStatement pstm = conn.prepareStatement(UPDATE_SUPPLIER)) {
                EditUtil.setLong(pstm, 1, supplier.getId());
                EditUtil.setString(pstm, 2, supplier.getName());
                EditUtil.setString(pstm, 3, supplier.getDescription());
                EditUtil.setString(pstm, 4, supplier.getAddress());
                EditUtil.setString(pstm, 5, supplier.getPhone());
                EditUtil.setString(pstm, 6, supplier.getEmail());
                ResultSet rs = pstm.executeQuery();
                if (rs.next()){
                    Integer res = rs.getInt(1);
                    if (rs.wasNull()) {
                        res = null;
                    }
                    return res;
                }
            } catch (Exception ex) {
                logger.error("Execute sql " + UPDATE_SUPPLIER, ex);
                throw ex;
            } finally {
                if (!conn.getAutoCommit()) {
                    conn.commit();
                }
            }
        } catch (Exception ex) {
            logger.error("Execute sql " + UPDATE_SUPPLIER, ex);
            throw ex;
        }
        return null;
    }

    @Override
    public Integer deleteSupplier(Long id) throws SQLException {
        try (Connection conn = getSourceData().getConnection()) {
            try(PreparedStatement pstm = conn.prepareStatement(DELETE_SUPPLIER)) {
                EditUtil.setLong(pstm, 1, id);
                ResultSet rs = pstm.executeQuery();
                if (rs.next()){
                    Integer res = rs.getInt(1);
                    if (rs.wasNull()) {
                        res = null;
                    }
                    return res;
                }
            } catch (Exception ex) {
                logger.error("Execute sql " + DELETE_SUPPLIER, ex);
                throw ex;
            } finally {
                if (!conn.getAutoCommit()) {
                    conn.commit();
                }
            }
        } catch (Exception ex) {
            logger.error("Execute sql " + DELETE_SUPPLIER, ex);
            throw ex;
        }
        return null;
    }

    public ComboPooledDataSource getSourceData() {
        return sourceData;
    }

    public void setSourceData(ComboPooledDataSource sourceData) {
        this.sourceData = sourceData;
    }
}
