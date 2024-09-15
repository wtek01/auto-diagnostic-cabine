package com.hospital.future.autodiagnosis.infrastructure.config;

import com.hospital.future.autodiagnosis.application.ports.input.DiagnosisUseCase;
import com.hospital.future.autodiagnosis.application.ports.output.DiagnosisRepository;
import com.hospital.future.autodiagnosis.application.services.DiagnosisService;
import com.hospital.future.autodiagnosis.infrastructure.adapters.output.persistence.InMemoryDiagnosisRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public DiagnosisRepository diagnosisRepository() {
        return new InMemoryDiagnosisRepository();
    }

    @Bean
    public DiagnosisUseCase diagnosisUseCase(DiagnosisRepository diagnosisRepository) {
        return new DiagnosisService(diagnosisRepository);
    }
}