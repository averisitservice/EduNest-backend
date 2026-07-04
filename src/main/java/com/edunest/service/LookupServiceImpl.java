package com.edunest.service;

import com.edunest.entity.ClassMaster;
import com.edunest.entity.EmploymentType;
import com.edunest.entity.Role;
import com.edunest.entity.Subject;
import com.edunest.repository.ClassMasterRepository;
import com.edunest.repository.EmploymentTypeRepository;
import com.edunest.repository.RoleRepository;
import com.edunest.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LookupServiceImpl implements LookupService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    EmploymentTypeRepository employmentTypeRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    ClassMasterRepository classMasterRepository;

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findByRoleIdNot(1);
    }

    @Override
    public List<EmploymentType> getAllEmployType() {
        return employmentTypeRepository.findAll();
    }

    @Override
    public List<Subject> getAllSubject(int tenantId) {
        return subjectRepository.findByTenantId(tenantId);
    }

    @Override
    public List<ClassMaster> getAllClassMaster(int tenantId) {
        return classMasterRepository.findByTenantId(tenantId);
    }

}
