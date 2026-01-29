package com.dabananda.ems.service.impl;

import com.dabananda.ems.dto.EmployeeDto;
import com.dabananda.ems.entity.Department;
import com.dabananda.ems.entity.Employee;
import com.dabananda.ems.exception.ResourceNotFoundException;
import com.dabananda.ems.mapper.EmployeeMapper;
import com.dabananda.ems.repository.DepartmentRepository;
import com.dabananda.ems.repository.EmployeeRepository;
import com.dabananda.ems.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;
    private DepartmentRepository departmentRepository;
    
    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        
        if (employeeDto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(employeeDto.getDepartmentId()).orElseThrow(() -> new ResourceNotFoundException("Department not found: " + employeeDto.getDepartmentId()));
            employee.setDepartment(department);
        }
        
        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));
        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(EmployeeMapper::mapToEmployeeDto).collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail());

        if (employeeDto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(employeeDto.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found: " + employeeDto.getDepartmentId()));
            employee.setDepartment(department);
        }
        
        Employee updatedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(updatedEmployee);
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        employeeRepository.delete(employee);
    }
}
