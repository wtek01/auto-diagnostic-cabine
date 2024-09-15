package com.hospital.future.autodiagnosis.application.services;

import com.hospital.future.autodiagnosis.application.ports.output.DiagnosisRepository;
import com.hospital.future.autodiagnosis.domain.Diagnosis;
import com.hospital.future.autodiagnosis.domain.DiagnosisType;
import com.hospital.future.autodiagnosis.domain.exceptions.NegativeHealthIndexException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DiagnosisServiceTest {

    @Mock
    private DiagnosisRepository diagnosisRepository;

    private DiagnosisService diagnosisService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        diagnosisService = new DiagnosisService(diagnosisRepository);
    }

    @ParameterizedTest
    @CsvSource({
            "3, CARD, Cardiology",
            "5, TRAUM, Traumatology",
            "15, CARD|TRAUM, Cardiology|Traumatology",
            "7, NONE, No pathology detected",
            "0, NONE, No pathology detected"
    })
    void testDiagnose(int healthIndex, String expectedCodesStr, String expectedDescriptionsStr) {
        Diagnosis diagnosis = diagnosisService.diagnose(healthIndex);
        
        String[] expectedCodes = expectedCodesStr.split("\\|");
        String[] expectedDescriptions = expectedDescriptionsStr.split("\\|");
        
        assertEquals(expectedCodes.length, diagnosis.getPathologies().size(), 
                "The number of diagnoses does not match.");
        
        for (int i = 0; i < expectedCodes.length; i++) {
            DiagnosisType pathology = diagnosis.getPathologies().get(i);
            assertEquals(expectedCodes[i], pathology.getCode(), "Incorrect diagnosis code.");
            assertEquals(expectedDescriptions[i], pathology.getDescription(), "Incorrect diagnosis description.");
        }
        
        verify(diagnosisRepository).save(diagnosis);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 4, 7, 8, 11})
    void testNoPathologyCases(int healthIndex) {
        Diagnosis diagnosis = diagnosisService.diagnose(healthIndex);
        assertTrue(diagnosis.getPathologies().contains(DiagnosisType.NO_PATHOLOGY),
                "Health index " + healthIndex + " should result in NO_PATHOLOGY");
        verify(diagnosisRepository).save(diagnosis);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -5, -10, -15})
    void testNegativeHealthIndexCases(int healthIndex) {
        assertThrows(NegativeHealthIndexException.class, () -> diagnosisService.diagnose(healthIndex),
                "Health index " + healthIndex + " should throw NegativeHealthIndexException");
        verify(diagnosisRepository, never()).save(any());
    }

    @Test
    void diagnose_WithZero_ShouldReturnNoPathology() {
        Diagnosis diagnosis = diagnosisService.diagnose(0);
        assertTrue(diagnosis.getPathologies().contains(DiagnosisType.NO_PATHOLOGY));
        verify(diagnosisRepository).save(diagnosis);
    }
}