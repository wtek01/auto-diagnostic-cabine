package com.hospital.future.autodiagnosis.domain;

public enum DiagnosisType {
    CARDIOLOGY("CARD", "Cardiology"),
    TRAUMATOLOGY("TRAUM", "Traumatology"),
    NO_PATHOLOGY("NONE", "No pathology detected");

    private final String code;
    private final String description;

    DiagnosisType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() { return code; }
    public String getDescription() { return description; }
}