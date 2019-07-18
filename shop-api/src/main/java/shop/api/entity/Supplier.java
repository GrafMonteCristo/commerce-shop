package shop.api.entity;

import shop.api.entity.annot.FieldName;

import java.io.Serializable;
import java.util.Collection;

public class Supplier implements Serializable {
    /**
     * Идентификатор поставщика
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
     * Адрес
     */
    @FieldName(name = "address")
    private String address;
    /**
     * Телефон
     */
    @FieldName(name = "phone")
    private String phone;
    /**
     * e-mail
     */
    @FieldName(name = "email")
    private String email;
    /**
     * Товары поставщика
     */
    private Collection<Product> products;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<Product> getProducts() {
        return products;
    }

    public void setProducts(Collection<Product> products) {
        this.products = products;
    }
}
