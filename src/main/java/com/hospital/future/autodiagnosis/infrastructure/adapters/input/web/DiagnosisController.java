package com.hospital.future.autodiagnosis.infrastructure.adapters.input.web;

import com.hospital.future.autodiagnosis.application.ports.input.DiagnosisUseCase;
import com.hospital.future.autodiagnosis.domain.Diagnosis;
import com.hospital.future.autodiagnosis.domain.exceptions.NegativeHealthIndexException;
import com.hospital.future.autodiagnosis.infrastructure.adapters.input.web.dto.DiagnosisDTO;
import com.hospital.future.autodiagnosis.infrastructure.adapters.input.web.exceptions.ErrorResponse;
import com.hospital.future.autodiagnosis.infrastructure.documentation.DiagnosisApiDocs;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class DiagnosisController implements DiagnosisApiDocs {
    private final DiagnosisUseCase diagnosisUseCase;

    public DiagnosisController(DiagnosisUseCase diagnosisUseCase) {
        this.diagnosisUseCase = diagnosisUseCase;
    }

    @GetMapping("/diagnosis/{healthIndex}")
    public ResponseEntity<?> diagnose(@PathVariable int healthIndex) {
        try {
            Diagnosis diagnosis = diagnosisUseCase.diagnose(healthIndex);
            List<DiagnosisDTO> diagnosisDTOs = diagnosis.getPathologies().stream()
                    .map(pathology -> new DiagnosisDTO(diagnosis.getHealthIndex(), pathology.getCode(), pathology.getDescription()))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(diagnosisDTOs);
        } catch (NegativeHealthIndexException e) {
            ErrorResponse errorResponse = new ErrorResponse("INVALID_ARGUMENT", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}