package com.hospital.future.autodiagnosis.application.ports.output;

import com.hospital.future.autodiagnosis.domain.Diagnosis;

public interface DiagnosisRepository {
    void save(Diagnosis diagnosis);
    Diagnosis findByHealthIndex(int healthIndex);
}