package com.ramarizdev.eventureBackend.location.response;


import com.ramarizdev.eventureBackend.location.entity.Regency;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class RegencyListApiResponse {
    private int statusCode;
    private String message;
    private List<Regency> data;
    private Map<String, Object> meta;
}
