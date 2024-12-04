package org.example.sd_94vs1.entity.warranty;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "warranty_claim")
public class WarrantyClaim {

    @Id
    @Column(name = "claim_code", nullable = false, length = 50)
    private String claimCode;

    @ManyToOne
    @JoinColumn(name = "warranty_code", referencedColumnName = "warranty_code")
    private Warranty warranty;

    @Column(name = "claim_date")
    @Temporal(TemporalType.DATE)
    private Date claimDate;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "resolution", length = 255)
    private String resolution;

    @Column(name = "claim_status", length = 50)
    private String claimStatus;
}