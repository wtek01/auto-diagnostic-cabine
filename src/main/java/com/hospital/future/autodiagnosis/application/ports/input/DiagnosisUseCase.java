package com.hospital.future.autodiagnosis.application.ports.input;

import com.hospital.future.autodiagnosis.domain.Diagnosis;

public interface DiagnosisUseCase {
    Diagnosis diagnose(int healthIndex);
}