package shop.api.entity;

import shop.api.entity.annot.FieldName;

import java.io.Serializable;
import java.util.Collection;

/**
 * Категория
 *
 * @author user
 */
public class Category implements Serializable {
    /**
     * Идентификатор категории
     */
    @FieldName(name = "id")
    private Long id;
    /**
     * Описание категории
     */
    @FieldName(name = "description")
    private String description;
    /**
     * Описание категории латиница
     */
    @FieldName(name = "lat_name")
    private String latName;
    /**
     * Приоритет
     */
    @FieldName(name = "priority")
    private Integer priority;
    /**
     * Родительская категория
     */
    @FieldName(name = "parent_category")
    private Long parentCategory;
    /**
     * Товары категории
     */
    private Collection<Product> products;
    /**
     * Дочернии категории
     */
    private Collection<Category> childCategories;

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

    public String getLatName() {
        return latName;
    }

    public void setLatName(String latName) {
        this.latName = latName;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Long getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Long parentCategory) {
        this.parentCategory = parentCategory;
    }

    public Collection<Product> getProducts() {
        return products;
    }

    public void setProducts(Collection<Product> products) {
        this.products = products;
    }

    public Collection<Category> getChildCategories() {
        return childCategories;
    }

    public void setChildCategories(Collection<Category> childCategories) {
        this.childCategories = childCategories;
    }
}
