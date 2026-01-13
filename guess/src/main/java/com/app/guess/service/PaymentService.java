package com.app.guess.service;

import com.app.guess.dto.response.PrepaymentResponse;

import jakarta.servlet.http.HttpServletRequest;

public interface PaymentService {
    PrepaymentResponse prepayment();

    String updatePayment(HttpServletRequest request);
}
