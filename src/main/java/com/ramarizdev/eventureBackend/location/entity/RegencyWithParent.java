package com.ramarizdev.eventureBackend.location.entity;

import lombok.Data;

@Data
public class RegencyWithParent {
    private String code;
    private String name;
    private String provinceCode;
    private Parent parent;
}

@Data
class Parent {
    private Province province;
}

@Data
class Province {
    private String code;
    private String name;
}