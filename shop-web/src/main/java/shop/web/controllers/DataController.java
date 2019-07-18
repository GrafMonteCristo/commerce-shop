package shop.web.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import shop.api.entity.Category;
import shop.api.entity.Product;
import shop.api.entity.Supplier;
import shop.api.entity.annot.JsonArg;
import shop.web.service.DataService;
import java.util.Collection;

@RequestMapping("/")
public class DataController {

    private DataService dataService;

    @RequestMapping(value = "/getCategory", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getCategoryById(@RequestParam("id") Long id) {
        try {
            Category category = dataService.getCategoryById(id);
            if (null != category) {
                return ResponseEntity.ok(category);
            }
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex);
        }
    }

    @RequestMapping(value = "/getCategories", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getCategories() {
        try {
            Collection<Category> categories = dataService.getCategories();
            if (categories.size() > 0) {
                return ResponseEntity.ok(categories);
            }
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex);
        }
    }

    @RequestMapping(value = "/addCategory", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addCategory(@JsonArg(name = "category") Category category) {
        try {
            Long id = dataService.addCategory(category);
            if (null != id) {
                return ResponseEntity.ok(id);
            }
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex);
        }
    }

    @RequestMapping(value = "/updateCategory", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity updateCategory(@JsonArg(name = "category") Category category) {
        try {
            Integer res = dataService.updateCategory(category);
            if (null != res) {
                return ResponseEntity.ok(res);
            }
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex);
        }
    }

    @RequestMapping(value = "/deleteCategory", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity deleteCategory(@RequestParam("id") Long id) {
        try {
            Integer res = dataService.deleteCategory(id);
            if (null != res) {
                return ResponseEntity.ok(res);
            }
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex);
        }
    }

    @RequestMapping(value = "/getProduct", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getProductById(@RequestParam("id") Long id, @RequestParam("sku") String sku) {
        try {
            Product product = dataService.getProductById(id, sku);
            if (null != product) {
                return ResponseEntity.ok(product);
            }
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex);
        }
    }

    @RequestMapping(value = "/getIdenticalProducts", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getIdenticalProductById(@RequestParam("id") Long id) {
        try {
            Collection<Product> products = dataService.getIdenticalProductsById(id);
            if (products.size() > 0) {
                return ResponseEntity.ok(products);
            }
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex);
        }
    }

    @RequestMapping(value = "/getProducts", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getProducts() {
        try {
            Collection<Product> products = dataService.getProducts();
            if (products.size() > 0) {
                return ResponseEntity.ok(products);
            }
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex);
        }
    }

    @RequestMapping(value = "/getProductsByCategory", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getProductsByCategory(@RequestParam("categoryId") Long categoryId) {
        try {
            Collection<Product> products = dataService.getProductsByCategory(categoryId);
            if (products.size() > 0) {
                return ResponseEntity.ok(products);
            }
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex);
        }
    }

    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addProduct(@JsonArg(name = "product") Product product) {
        try {
            Long id = dataService.addProduct(product);
            if (null != id) {
                return ResponseEntity.ok(id);
            }
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex);
        }
    }

    @RequestMapping(value = "/updateProduct", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity updateProduct(@JsonArg(name = "product") Product product) {
        try {
            Integer res = dataService.updateProduct(product);
            if (null != res) {
                return ResponseEntity.ok(res);
            }
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex);
        }
    }

    @RequestMapping(value = "/deleteProduct", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity deleteProduct(@RequestParam("id") Long id) {
        try {
            Integer res = dataService.deleteProduct(id);
            if (null != res) {
                return ResponseEntity.ok(res);
            }
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex);
        }
    }

    @RequestMapping(value = "/addProductInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addProductInfo(@JsonArg(name = "product") Product product) {
        try {
            Integer res = dataService.addProductInfo(product);
            if (null != res) {
                return ResponseEntity.ok(res);
            }
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex);
        }
    }

    @RequestMapping(value = "/deleteProductInfo", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity deleteProductInfo(@RequestParam("id") Long id, @RequestParam("sku") String sku) {
        try {
            Integer res = dataService.deleteProductInfo(id, sku);
            if (null != res) {
                return ResponseEntity.ok(res);
            }
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex);
        }
    }

    @RequestMapping(value = "/getSupplier", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getSupplierById(@RequestParam("id") Long id) {
        try {
            Supplier supplier = dataService.getSupplierById(id);
            if (null != supplier) {
                return ResponseEntity.ok(supplier);
            }
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex);
        }
    }

    @RequestMapping(value = "/getSuppliers", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getSuppliers() {
        try {
            Collection<Supplier> suppliers = dataService.getSuppliers();
            if (suppliers.size() > 0) {
                return ResponseEntity.ok(suppliers);
            }
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex);
        }
    }

    @RequestMapping(value = "/addSupplier", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addSupplier(@JsonArg(name = "supplier") Supplier supplier) {
        try {
            Long id = dataService.addSupplier(supplier);
            if (null != id) {
                return ResponseEntity.ok(id);
            }
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex);
        }
    }

    @RequestMapping(value = "/updateSupplier", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity updateSupplier(@JsonArg(name = "supplier") Supplier supplier) {
        try {
            Integer res = dataService.updateSupplier(supplier);
            if (null != res) {
                return ResponseEntity.ok(res);
            }
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex);
        }
    }

    @RequestMapping(value = "/deleteSupplier", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity deleteSupplier(@RequestParam("id") Long id) {
        try {
            Integer res = dataService.deleteSupplier(id);
            if (null != res) {
                return ResponseEntity.ok(res);
            }
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex);
        }
    }

    public void setDataService(DataService dataService) {
        this.dataService = dataService;
    }
}
