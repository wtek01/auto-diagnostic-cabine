package com.hospital.future.autodiagnosis.domain;

import java.util.ArrayList;
import java.util.List;

public class Diagnosis {
    private final int healthIndex;
    private final List<DiagnosisType> pathologies;

    public Diagnosis(int healthIndex) {
        this.healthIndex = healthIndex;
        this.pathologies = new ArrayList<>();
    }

    public void addPathology(DiagnosisType pathology) {
        this.pathologies.add(pathology);
    }

    public boolean hasPathology() {
        return !pathologies.isEmpty();
    }

    public int getHealthIndex() {
        return healthIndex;
    }

    public List<DiagnosisType> getPathologies() {
        return new ArrayList<>(pathologies);
    }
}