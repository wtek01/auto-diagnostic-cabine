package com.hospital.future.autodiagnosis.application.services;

import com.hospital.future.autodiagnosis.application.ports.input.DiagnosisUseCase;
import com.hospital.future.autodiagnosis.application.ports.output.DiagnosisRepository;
import com.hospital.future.autodiagnosis.domain.Diagnosis;
import com.hospital.future.autodiagnosis.domain.DiagnosisType;
import com.hospital.future.autodiagnosis.domain.exceptions.NegativeHealthIndexException;

public class DiagnosisService implements DiagnosisUseCase {

    private final DiagnosisRepository diagnosisRepository;

    public DiagnosisService(DiagnosisRepository diagnosisRepository) {
        this.diagnosisRepository = diagnosisRepository;
    }

    @Override
    public Diagnosis diagnose(int healthIndex) {
        if (healthIndex < 0) {
            throw new NegativeHealthIndexException("Health index cannot be negative: " + healthIndex);
        }

        Diagnosis diagnosis = new Diagnosis(healthIndex);

        if (healthIndex % 3 == 0 && healthIndex != 0) {
            diagnosis.addPathology(DiagnosisType.CARDIOLOGY);
        }
        if (healthIndex % 5 == 0 && healthIndex != 0) {
            diagnosis.addPathology(DiagnosisType.TRAUMATOLOGY);
        }
        if (!diagnosis.hasPathology()) {
            diagnosis.addPathology(DiagnosisType.NO_PATHOLOGY);
        }

        diagnosisRepository.save(diagnosis);
        return diagnosis;
    }
}