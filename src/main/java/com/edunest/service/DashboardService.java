package com.edunest.service;

import com.edunest.dto.dashboard.DashboardSummaryResponse;

public interface DashboardService {
    DashboardSummaryResponse getSummary(Integer tenantId);
}
