package com.app.guess.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.guess.dto.response.ApiResponse;
import com.app.guess.dto.response.PrepaymentResponse;
import com.app.guess.service.PaymentService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/prepayment")
    public ResponseEntity<ApiResponse<PrepaymentResponse>> prepayment() {

        var payment = paymentService.prepayment();

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(payment));

    }

    @GetMapping(value = "/result", produces = "text/html")
    public String updatePayment(HttpServletRequest request) {
        var responses = paymentService.updatePayment(request);
        return """
                    <!DOCTYPE html>
                    <html>
                    <head><title>Payment</title></head>
                    <body>
                        <h1>Status: %s</h1>
                        <a href='http://localhost:4173/'>Go back</a>
                    </body>
                    </html>
                """.formatted(responses);
    }

}
