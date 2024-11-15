package com.pado.inflow.employee.attach.query.controller;

import com.pado.inflow.common.ResponseDTO;
import com.pado.inflow.employee.attach.query.dto.EducationDTO;
import com.pado.inflow.employee.attach.query.service.EducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("EQueryController")
@RequestMapping("/api/employee/educations")
public class EducationController {

    private final EducationService educationService;

    @Autowired
    public EducationController(EducationService educationService) {
        this.educationService = educationService;
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "I'm OK!!";
    }

    // 전 사원의 학력 조회
    @GetMapping("/")
    public ResponseDTO getEducationAll() {
        List<EducationDTO> result = educationService.getEduAll();
        return ResponseDTO.ok(result);
    }

    // 사원 한 명의 학력 조회
    @GetMapping("/{employeeId}")
    public ResponseDTO getEducationOne(@PathVariable("employeeId") Long employeeId) {
        List<EducationDTO> result = educationService.getEduOne(employeeId);
        return ResponseDTO.ok(result);
    }
}
