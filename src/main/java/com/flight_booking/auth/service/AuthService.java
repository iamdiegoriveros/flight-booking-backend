package com.flight_booking.auth.service;

import com.flight_booking.auth.dto.AuthRequestDto;
import com.flight_booking.auth.dto.AuthResponseDto;
import org.springframework.security.core.Authentication;

public interface AuthService {

    AuthResponseDto login(AuthRequestDto authRequestDto);
}
