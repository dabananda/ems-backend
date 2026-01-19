package com.dabananda.ems.service.impl;

import com.dabananda.ems.dto.DepartmentDto;
import com.dabananda.ems.entity.Department;
import com.dabananda.ems.exception.ResourceNotFoundException;
import com.dabananda.ems.mapper.DepartmentMapper;
import com.dabananda.ems.repository.DepartmentRepository;
import com.dabananda.ems.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.dabananda.ems.mapper.DepartmentMapper.mapToDepartment;
import static com.dabananda.ems.mapper.DepartmentMapper.mapToDepartmentDto;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private DepartmentRepository departmentRepository;
    
    @Override
    public DepartmentDto createDepartment(DepartmentDto departmentDto) {
        Department department = mapToDepartment(departmentDto);
        departmentRepository.save(department);
        return mapToDepartmentDto(department);
    }

    @Override
    public DepartmentDto updateDepartment(Long id, DepartmentDto departmentDto) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Department not found with id " + id));
        department.setDepartmentName(departmentDto.getDepartmentName());
        department.setDepartmentDescription(departmentDto.getDepartmentDescription());
        departmentRepository.save(department);
        return mapToDepartmentDto(department);
    }

    @Override
    public void deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Department not found with id " + id));
        departmentRepository.delete(department);
    }

    @Override
    public DepartmentDto getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Department not found with id " + id));
        return mapToDepartmentDto(department);
    }

    @Override
    public List<DepartmentDto> getDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream().map(DepartmentMapper::mapToDepartmentDto).collect(Collectors.toList());
    }
}
