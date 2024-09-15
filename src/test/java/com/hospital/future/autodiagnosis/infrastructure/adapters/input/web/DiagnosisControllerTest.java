package com.hospital.future.autodiagnosis.infrastructure.adapters.input.web;

import com.hospital.future.autodiagnosis.application.ports.input.DiagnosisUseCase;
import com.hospital.future.autodiagnosis.domain.Diagnosis;
import com.hospital.future.autodiagnosis.domain.DiagnosisType;
import com.hospital.future.autodiagnosis.domain.exceptions.NegativeHealthIndexException;
import com.hospital.future.autodiagnosis.infrastructure.adapters.input.web.dto.DiagnosisDTO;
import com.hospital.future.autodiagnosis.infrastructure.adapters.input.web.exceptions.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DiagnosisControllerTest {

    @Mock
    private DiagnosisUseCase diagnosisUseCase;

    private DiagnosisController diagnosisController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        diagnosisController = new DiagnosisController(diagnosisUseCase);
    }

    @ParameterizedTest
    @CsvSource({
            "3, CARDIOLOGY, CARD, Cardiology",
            "5, TRAUMATOLOGY, TRAUM, Traumatology",
            "15, CARDIOLOGY;TRAUMATOLOGY, CARD;TRAUM, Cardiology;Traumatology",
            "0, NO_PATHOLOGY, NONE, No pathology detected"
    })
    void testValidDiagnosis(int healthIndex, String diagnosisTypes, String expectedCodes, String expectedDescriptions) {
        Diagnosis diagnosis = createDiagnosis(healthIndex, diagnosisTypes.split(";"));
        when(diagnosisUseCase.diagnose(healthIndex)).thenReturn(diagnosis);

        ResponseEntity<?> response = diagnosisController.diagnose(healthIndex);

        assertEquals(200, response.getStatusCodeValue());
        List<DiagnosisDTO> diagnosisDTOs = (List<DiagnosisDTO>) response.getBody();
        assertNotNull(diagnosisDTOs);
        assertEquals(diagnosisTypes.split(";").length, diagnosisDTOs.size());

        String[] codes = expectedCodes.split(";");
        String[] descriptions = expectedDescriptions.split(";");
        for (int i = 0; i < codes.length; i++) {
            assertEquals(codes[i], diagnosisDTOs.get(i).getCode());
            assertEquals(descriptions[i], diagnosisDTOs.get(i).getDescription());
        }
    }

    @ParameterizedTest
    @CsvSource({"-1", "-5", "-10"})
    void testNegativeHealthIndex(int healthIndex) {
        when(diagnosisUseCase.diagnose(healthIndex)).thenThrow(new NegativeHealthIndexException("Negative health index"));

        ResponseEntity<?> response = diagnosisController.diagnose(healthIndex);

        assertEquals(400, response.getStatusCodeValue());
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertNotNull(errorResponse);
        assertEquals("INVALID_ARGUMENT", errorResponse.getCode());
        assertTrue(errorResponse.getMessage().contains("Negative health index"));
    }

    private Diagnosis createDiagnosis(int healthIndex, String[] types) {
        Diagnosis diagnosis = new Diagnosis(healthIndex);
        for (String type : types) {
            diagnosis.addPathology(DiagnosisType.valueOf(type));
        }
        return diagnosis;
    }
}