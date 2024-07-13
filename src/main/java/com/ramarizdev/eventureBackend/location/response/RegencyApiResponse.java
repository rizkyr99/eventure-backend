package com.ramarizdev.eventureBackend.location.response;

import com.ramarizdev.eventureBackend.location.entity.Regency;
import com.ramarizdev.eventureBackend.location.entity.RegencyWithParent;
import lombok.Data;

@Data
public class RegencyApiResponse {
    private String statusCode;
    private String message;
    private RegencyWithParent data;
}

