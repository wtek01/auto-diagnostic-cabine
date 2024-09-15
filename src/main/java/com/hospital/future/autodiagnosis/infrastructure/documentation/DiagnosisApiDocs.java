package com.hospital.future.autodiagnosis.infrastructure.documentation;

import com.hospital.future.autodiagnosis.infrastructure.adapters.input.web.dto.DiagnosisDTO;
import com.hospital.future.autodiagnosis.infrastructure.adapters.input.web.exceptions.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface DiagnosisApiDocs {

    @Operation(summary = "Get diagnosis based on health index")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful diagnosis",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DiagnosisDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid health index",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<?> diagnose(@PathVariable int healthIndex);
}