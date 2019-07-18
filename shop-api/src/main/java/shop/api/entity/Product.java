package shop.api.entity;

import shop.api.entity.annot.FieldName;

import java.io.Serializable;
import java.util.Collection;

public class Product implements Serializable {
    /**
     * Идентификатор товара
     */
    @FieldName(name = "id")
    private Long id;
    /**
     * Наименование
     */
    @FieldName(name = "name")
    private String name;
    /**
     * Описание
     */
    @FieldName(name = "description")
    private String description;
    /**
     * Состав
     */
    @FieldName(name = "composition")
    private String composition;
    /**
     * Внешний идентификатор товара
     */
    @FieldName(name = "external_id")
    private Integer externalId;
    /**
     * Материал
     */
    @FieldName(name = "material")
    private String material;
    /**
     * Идентификатор доп. свойств
     */
    @FieldName(name = "product_info_id")
    private Long productInfoId;
    /**
     * Артикул
     */
    @FieldName(name = "sku")
    private String sku;
    /**
     * Цвет
     */
    @FieldName(name = "color")
    private String color;
    /**
     * Цена
     */
    @FieldName(name = "price")
    private Float price;
    /**
     * Количество размеров
     */
    private Collection<ProductQuantity> productQuantity;
    /**
     * Поставщик
     */
    private Supplier supplier;
    /**
     * Категории товара
     */
    private Collection<Category> categories;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public Integer getExternalId() {
        return externalId;
    }

    public void setExternalId(Integer externalId) {
        this.externalId = externalId;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public Long getProductInfoId() {
        return productInfoId;
    }

    public void setProductInfoId(Long productInfoId) {
        this.productInfoId = productInfoId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Collection<ProductQuantity> getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Collection<ProductQuantity> productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Collection<Category> getCategories() {
        return categories;
    }

    public void setCategories(Collection<Category> categories) {
        this.categories = categories;
    }
}
