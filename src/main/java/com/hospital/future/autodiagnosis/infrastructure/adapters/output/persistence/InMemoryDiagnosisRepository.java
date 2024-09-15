package com.hospital.future.autodiagnosis.infrastructure.adapters.output.persistence;

import com.hospital.future.autodiagnosis.application.ports.output.DiagnosisRepository;
import com.hospital.future.autodiagnosis.domain.Diagnosis;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryDiagnosisRepository implements DiagnosisRepository {
    private final Map<Integer, Diagnosis> storage = new HashMap<>();

    @Override
    public void save(Diagnosis diagnosis) {
        storage.put(diagnosis.getHealthIndex(), diagnosis);
    }

    @Override
    public Diagnosis findByHealthIndex(int healthIndex) {
        return storage.get(healthIndex);
    }
}