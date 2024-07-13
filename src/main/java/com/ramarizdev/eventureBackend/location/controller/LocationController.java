package com.ramarizdev.eventureBackend.location.controller;

import com.ramarizdev.eventureBackend.location.entity.Regency;
import com.ramarizdev.eventureBackend.location.entity.RegencyWithParent;
import com.ramarizdev.eventureBackend.location.response.RegencyApiResponse;
import com.ramarizdev.eventureBackend.location.response.RegencyListApiResponse;
import com.ramarizdev.eventureBackend.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/v1/locations")
public class LocationController {
    private final RestTemplate restTemplate;
    private final String apiBaseUrl = "https://idn-area.up.railway.app";

    public LocationController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/regencies")
    public ResponseEntity<Response<List<Regency>>> getAllRegencies(@RequestParam(required = false) String name) {
        String apiUrl = apiBaseUrl + "/regencies";

        if (name != null && !name.isEmpty()) {
            apiUrl += "?name=" + name;
        }

        RegencyListApiResponse response = restTemplate.getForObject(apiUrl, RegencyListApiResponse.class);
        return Response.success("List of regencies fetched", response.getData());
    }

    @GetMapping("/regencies/{code}")
    public ResponseEntity<Response<RegencyWithParent>> getRegencyByCode(@PathVariable String code) {
        String apiUrl = apiBaseUrl + "/regencies/" + code;

        RegencyApiResponse response = restTemplate.getForObject(apiUrl, RegencyApiResponse.class);

        return Response.success("Regency details fetched", response.getData());
    }
}
