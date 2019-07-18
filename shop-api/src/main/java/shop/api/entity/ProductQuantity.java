package shop.api.entity;

import shop.api.entity.annot.FieldName;

import java.io.Serializable;

public class ProductQuantity implements Serializable {
    /**
     * Идентификатор товара
     */
    @FieldName(name = "id")
    private Long id;
    /**
     * Количество товара
     */
    @FieldName(name = "quantity")
    private Integer quantity;
    /**
     * Размер товара
     */
    @FieldName(name = "size")
    private String size;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
