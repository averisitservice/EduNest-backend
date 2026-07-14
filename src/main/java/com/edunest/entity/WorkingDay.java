package com.edunest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "working_day", schema = "lookup")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkingDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "working_day_id")
    private Integer workingDayId;

    @Column(name = "tenant_id", nullable = false)
    private Integer tenantId;

    @Column(name = "day_name", nullable = false, length = 20)
    private String dayName;

    @Column(name = "day_order", nullable = false)
    private Integer dayOrder;

    @Column(name = "is_active")
    private Boolean isActive = true;
}