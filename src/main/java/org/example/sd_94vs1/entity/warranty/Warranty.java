package org.example.sd_94vs1.entity.warranty;

import jakarta.persistence.*;
import org.example.sd_94vs1.entity.Inventory;

import java.util.Date;

@Entity
@Table(name = "warranty")
public class Warranty {

    @Id
    @Column(name = "warranty_code", nullable = false, length = 50)
    private String warrantyCode;

    @ManyToOne
    @JoinColumn(name = "inventory_code", referencedColumnName = "inventory_code")
    private Inventory inventory;

    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Column(name = "warranty_status", length = 50)
    private String warrantyStatus;

    @Column(name = "terms", length = 255)
    private String terms;
}