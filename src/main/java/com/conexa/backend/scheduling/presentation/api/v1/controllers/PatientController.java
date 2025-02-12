package com.conexa.backend.scheduling.presentation.api.v1.controllers;

import com.conexa.backend.scheduling.application.services.PatientService;
import com.conexa.backend.scheduling.presentation.api.v1.BaseV1Controller;
import com.conexa.backend.scheduling.presentation.api.v1.dtos.requests.PatientRequestDTO;
import com.conexa.backend.scheduling.presentation.api.v1.dtos.responses.PatientResponseDTO;
import com.conexa.backend.scheduling.presentation.api.v1.mappers.PatientMapper;
import com.conexa.backend.scheduling.presentation.api.v1.validators.UpdatePatientValidator;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Patient")
@RestController
@RequiredArgsConstructor
public class PatientController extends BaseV1Controller {

    private final PatientService patientService;
    private final PatientMapper patientMapper;

    /**
     * Creates a new patient.
     *
     * @param request the patient information
     * @return the created patient
     */
    @PostMapping("/patient")
    public ResponseEntity<PatientResponseDTO> createPatient(@Valid @RequestBody PatientRequestDTO request) {
        var patient = patientService.createPatient(patientMapper.toEntity(request));
        return ResponseEntity.ok(patientMapper.toResponseDTO(patient));
    }

    /**
     * Retrieves a patient by ID.
     *
     * @param id the ID of the patient
     * @return the patient with the given ID
     */
    @GetMapping("patient/{id}")
    public ResponseEntity<PatientResponseDTO> getPatientById(@PathVariable Long id) {
        var patient = patientService.getPatientById(id);
        return ResponseEntity.ok(patientMapper.toResponseDTO(patient));
    }

    /**
     * Updates an existing patient.
     *
     * @param id the ID of the patient to update
     * @param request the updated patient information
     * @return the updated patient
     */
    @PutMapping("patient/{id}")
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable Long id, @Valid @RequestBody PatientRequestDTO request) {
        UpdatePatientValidator.validate(request);
        var updatedPatient = patientService.updatePatient(id, patientMapper.toEntity(request));
        return ResponseEntity.ok(patientMapper.toResponseDTO(updatedPatient));
    }

    /**
     * Soft deletes a patient by marking them as inactive.
     *
     * @param id the ID of the patient to delete
     * @return a success message
     */
    @DeleteMapping("patient/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
