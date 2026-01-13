package com.app.guess.service.imp;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.app.guess.dto.response.PrepaymentResponse;
import com.app.guess.mapper.UserMapper;
import com.app.guess.service.PaymentService;
import com.app.guess.utils.UserInfo;
import com.app.guess.utils.VnPayUtils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImp implements PaymentService {

    private final UserMapper userMapper;

    private final String successStatue = "00";

    @Value("${vnpay.tmnCode}")
    private String tmnCode;

    @Value("${vnpay.hashSecret}")
    private String hashSecret;

    @Value("${vnpay.payUrl}")
    private String payUrl;

    @Value("${vnpay.returnUrl}")
    private String returnUrl;

    @Override
    public PrepaymentResponse prepayment() {
        String txnRef = UserInfo.getCurrentUserId() + '_'
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        Map<String, String> params = new TreeMap<>();
        params.put("vnp_Version", "2.1.0");
        params.put("vnp_Command", "pay");
        params.put("vnp_TmnCode", tmnCode);
        // VNPay x100
        params.put("vnp_Amount", String.valueOf(10000 * 100));
        params.put("vnp_CurrCode", "VND");
        params.put("vnp_TxnRef", txnRef);
        params.put("vnp_OrderInfo", "Payment");
        params.put("vnp_OrderType", "other");
        params.put("vnp_Locale", "vn");
        params.put("vnp_ReturnUrl", returnUrl);
        params.put("vnp_IpAddr", "127.0.0.1");
        params.put("vnp_CreateDate",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));

        // build query + hash data
        StringBuilder query = new StringBuilder();
        StringBuilder hashData = new StringBuilder();

        for (Map.Entry<String, String> e : params.entrySet()) {

            hashData.append(e.getKey())
                    .append("=")
                    .append(URLEncoder.encode(e.getValue(), StandardCharsets.US_ASCII))
                    .append("&");

            query.append(URLEncoder.encode(e.getKey(), StandardCharsets.US_ASCII))
                    .append("=")
                    .append(URLEncoder.encode(e.getValue(), StandardCharsets.US_ASCII))
                    .append("&");
        }

        hashData.deleteCharAt(hashData.length() - 1);

        String secureHash = VnPayUtils.sign(hashSecret, hashData.toString());
        query.append("vnp_SecureHash=").append(secureHash);
        String paymentUrl = payUrl + "?" + query;
        return new PrepaymentResponse(paymentUrl);
    }

    @Override
    public String updatePayment(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();

        request.getParameterMap().forEach((k, v) -> {
            params.put(k, v[0]);
        });

        String secureHash = params.remove("vnp_SecureHash");
        params.remove("vnp_SecureHashType");

        Map<String, String> sorted = new TreeMap<>(params);

        StringBuilder hashData = new StringBuilder();
        for (Map.Entry<String, String> e : sorted.entrySet()) {
            hashData.append(e.getKey())
                    .append("=")
                    .append(URLEncoder.encode(e.getValue(), StandardCharsets.US_ASCII))
                    .append("&");
        }
        hashData.deleteCharAt(hashData.length() - 1);

        String sign = VnPayUtils.sign(hashSecret, hashData.toString());

        if (!sign.equals(secureHash)) {
            return "Fail";
        }

        String responseCode = params.get("vnp_ResponseCode");
        String transactionStatus = params.get("vnp_TransactionStatus");

        // Payment Success
        if (successStatue.equals(responseCode) && successStatue.equals(transactionStatus)) {
            var txnRef = params.get("vnp_TxnRef").split("_");
            userMapper.payment(txnRef[0]);
            return "Success";
        }

        return "Fail";
    }
}
