package shop.api.entity;

import shop.api.entity.annot.FieldName;

import java.io.Serializable;

public class Product implements Serializable {

    /**Идентификатор товара*/
    @FieldName(name="id")
    private Long id;
    /**Артикул*/
    @FieldName(name="sku")
    private String sku;
    /**Наименование*/
    @FieldName(name="name")
    private String name;
    /**Описание*/
    @FieldName(name="description")
    private String description;
    /**Цена*/
    @FieldName(name="price")
    private Float price;
    /**Состав*/
    @FieldName(name="composition")
    private String composition;
    /**Внешний идентификатор товара*/
    @FieldName(name="external_id")
    private Integer externalId;
    /**Материал*/
    @FieldName(name="material")
    private String material;
    /**Внешний идентификатор товара*/
    @FieldName(name="quantity")
    private Float quantity;
    /**Внешний идентификатор товара*/
    @FieldName(name="size")
    private Float size;
    /**Материал*/
    @FieldName(name="color")
    private String color;
    /**Поставщик*/
    private Supplier supplier;
    /**Категория товара*/
    private Category category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
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

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public Float getSize() {
        return size;
    }

    public void setSize(Float size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
