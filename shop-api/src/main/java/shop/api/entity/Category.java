package shop.api.entity;

import shop.api.entity.annot.FieldName;

import java.io.Serializable;
import java.util.Set;

/**
 * Категория
 * @author user
 */
public class Category implements Serializable {

    /**Идентификатор категории*/
    @FieldName(name="id")
    private Long id;
    /**Описание категории*/
    @FieldName(name="description")
    private String description;
    /**Родительская категория*/
    @FieldName(name="parent_category")
    private Long parentId;
    /**Товары категории*/
    private Set<Product> products;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
