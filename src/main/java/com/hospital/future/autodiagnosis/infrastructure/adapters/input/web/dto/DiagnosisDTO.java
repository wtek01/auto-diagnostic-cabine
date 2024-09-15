package com.hospital.future.autodiagnosis.infrastructure.adapters.input.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Diagnosis Data Transfer Object")
public class DiagnosisDTO {
    @Schema(description = "Health index of the patient", example = "15")
    private int healthIndex;

    @Schema(description = "Code of the diagnosis", example = "CARD")
    private String code;

    @Schema(description = "Description of the diagnosis", example = "Cardiology")
    private String description;

    public DiagnosisDTO(int healthIndex, String code, String description) {
        this.healthIndex = healthIndex;
        this.code = code;
        this.description = description;
    }

    // Getters and setters
    public int getHealthIndex() {
        return healthIndex;
    }

    public void setHealthIndex(int healthIndex) {
        this.healthIndex = healthIndex;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}