package com.edunest.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tenant", schema = "auth")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tenant {

    @Id
    @Column(name = "tenant_id", nullable = false)
    private Integer tenantId;

    @Column(name = "tenant_name", nullable = false, length = 100)
    private String tenantName;

    @Column(name = "school_code", length = 30)
    private String schoolCode;

    @Column(name = "school_banner_url", length = 255)
    private String schoolBannerUrl;

    @Column(name = "domain_name", nullable = false, length = 150)
    private String domainName;

    @Column(name = "contact_name", nullable = false, length = 100)
    private String contactName;

    @Column(name = "contact_email", nullable = false, length = 150)
    private String contactEmail;

    @Column(name = "contact_phone", nullable = false, length = 15)
    private String contactPhone;

    @Column(name = "address_line1", length = 100)
    private String addressLine1;

    @Column(name = "address_line2", length = 100)
    private String addressLine2;

    @Column(name = "city", nullable = false, length = 100)
    private String city;

    @Column(name = "state", nullable = false, length = 100)
    private String state;

    @Column(name = "postal_code", nullable = false, length = 15)
    private String postalCode;

    @Column(name = "logo_url", nullable = false, length = 255)
    private String logoUrl;

    @Column(name = "subscription_expiry", nullable = false)
    private LocalDateTime subscriptionExpiry;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "is_hostel")
    private Boolean isHostel = false;

    @CreationTimestamp
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "created_by", nullable = false, updatable = false)
    private Integer createdBy;

    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedDate;

    @Column(name = "updated_by", nullable = false)
    private Integer updatedBy;

    @Column(name = "single_logo_url", nullable = false, length = 255)
    private String singleLogoUrl;

    @Column(name = "primary_color", nullable = false, length = 100)
    private String primaryColor;

    @Column(name = "favicon_url", length = 100)
    private String faviconUrl;

    @Column(name = "post_secret", length = 30)
    private String postSecret;
}