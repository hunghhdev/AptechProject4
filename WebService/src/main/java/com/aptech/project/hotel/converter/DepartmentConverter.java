package com.aptech.project.hotel.converter;

import com.aptech.project.hotel.model.DepartmentDto;
import org.springframework.stereotype.Component;
import com.aptech.project.hotel.entity.Department;

import java.util.LinkedList;
import java.util.List;

@Component
public class DepartmentConverter {

    public DepartmentDto toDepartmentDto(Department department){
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setId(department.getId());
        departmentDto.setDepartmentName(department.getDepartmentName());
        departmentDto.setCreatedBy(department.getCreatedBy());
        departmentDto.setCreatedDate(department.getCreatedDate());

        return departmentDto;
    }

    public List<DepartmentDto> toDepartmentsDto(List<Department> departments){
        List<DepartmentDto> departmentsDto = new LinkedList<>();
        departments.forEach(department -> departmentsDto.add(toDepartmentDto(department)));
        return departmentsDto;
    }

    public Department toDepartment(DepartmentDto departmentDto){
        Department department = new Department();
        department.setId(departmentDto.getId());
        department.setDepartmentName(departmentDto.getDepartmentName());
        return department;
    }
}
