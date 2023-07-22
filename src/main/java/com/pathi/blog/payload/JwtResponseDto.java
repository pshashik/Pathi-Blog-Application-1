package com.pathi.blog.payload;

import lombok.Getter;
import lombok.Setter;

public class JwtResponseDto {
    @Getter
    @Setter
    private String accessToken;
    @Getter
    private String type = "Bearer";

    @Setter
    @Getter
    private long jwtExpiryInMs;


}
