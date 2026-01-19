package com.dabananda.ems.service;

import com.dabananda.ems.dto.DepartmentDto;

import java.util.List;

public interface DepartmentService {
    DepartmentDto createDepartment(DepartmentDto departmentDto);
    DepartmentDto updateDepartment(Long id, DepartmentDto departmentDto);
    void deleteDepartment(Long id);
    DepartmentDto getDepartmentById(Long id);
    List<DepartmentDto> getDepartments();
}
