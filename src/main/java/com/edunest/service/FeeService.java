package com.edunest.service;

import com.edunest.dto.fee.FeePaymentRequest;
import com.edunest.dto.fee.FeePaymentResponse;
import com.edunest.dto.fee.FeeStatusResponse;

import java.util.List;

public interface FeeService {

    List<FeeStatusResponse> getFeeStatus(Integer tenantId, Integer classId, Integer sectionId);

    String collectPayment(Integer tenantId, Integer collectedBy, FeePaymentRequest request);

    List<FeePaymentResponse> getPaymentHistory(Integer tenantId, Integer studentId);
}
