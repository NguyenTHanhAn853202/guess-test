package com.app.guess.service;

import com.app.guess.dto.request.GuessRequest;
import com.app.guess.dto.response.GuessResponse;

public interface GuessService {
    GuessResponse guess(GuessRequest request);
}
