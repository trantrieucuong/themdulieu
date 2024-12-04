package org.example.sd_94vs1.entity;

import jakarta.persistence.*;
import org.example.sd_94vs1.entity.product.DetailedProduct;
import org.example.sd_94vs1.entity.product.Product;

import java.util.Date;

@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @Column(name = "inventory_code", nullable = false, length = 50)
    private String inventoryCode;

    @ManyToOne
    @JoinColumn(name = "product_code", referencedColumnName = "product_code")
    private Product product;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "id_detailed_product", referencedColumnName = "detailed_product_code")
    private DetailedProduct detailedProduct;

    @Column(name = "imei", length = 50)
    private String imei;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "date_received")
    @Temporal(TemporalType.DATE)
    private Date dateReceived;

    @Column(name = "date_updated")
    @Temporal(TemporalType.DATE)
    private Date dateUpdated;
}