package com.edunest.service;

import com.edunest.dto.mobile.StudentDetailResponse;
import com.edunest.entity.ClassMaster;
import com.edunest.entity.ClassSection;
import com.edunest.entity.Student;
import com.edunest.entity.StudentClass;
import com.edunest.entity.Teacher;
import com.edunest.entity.TeacherClass;
import com.edunest.error.CustomException;
import com.edunest.repository.ClassMasterRepository;
import com.edunest.repository.ClassSectionRepository;
import com.edunest.repository.StudentClassRepository;
import com.edunest.repository.StudentRepository;
import com.edunest.repository.TeacherClassRepository;
import com.edunest.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MobileStudentServiceImpl implements MobileStudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    StudentClassRepository studentClassRepository;

    @Autowired
    ClassMasterRepository classMasterRepository;

    @Autowired
    ClassSectionRepository classSectionRepository;

    @Autowired
    TeacherClassRepository teacherClassRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Override
    public StudentDetailResponse getStudentDetailsById(Integer studentId, Integer tenantId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new CustomException("studentId", "Student not found"));

        if (!student.getTenantId().equals(tenantId)) {
            throw new CustomException("studentId", "Student not found");
        }

        StudentDetailResponse response = new StudentDetailResponse();
        response.setStudentId(student.getStudentId());
        response.setAdmissionNo(student.getAdmissionNo());
        response.setUsername(student.getUsername());
        response.setStudentName(buildStudentName(student));
        response.setPhotoUrl(student.getPhotoUrl());

        response.setDateOfBirth(student.getDateOfBirth());
        response.setGender(student.getGender() != null ? String.valueOf(student.getGender()) : null);
        response.setAadharNo(student.getAadharNo());
        response.setEmail(student.getEmail());
        response.setMobileNo(student.getMobileNo());
        response.setIsHostel(student.getIsHostel());

        response.setFatherName(student.getFatherName());
        response.setMotherName(student.getMotherName());
        response.setParentMobile(student.getParentMobile());
        response.setParentEmail(student.getParentEmail());
        response.setParentAadhar(student.getParentAadhar());

        response.setAddress(buildFullAddress(student));

        applyClassPlacement(response, student, tenantId);

        return response;
    }

    private void applyClassPlacement(StudentDetailResponse response, Student student, Integer tenantId) {
        StudentClass studentClass = studentClassRepository
                .findByStudentIdAndTenantId(student.getStudentId(), tenantId)
                .orElse(null);

        if (studentClass == null) {
            return;
        }

        ClassMaster classMaster = classMasterRepository.findById(studentClass.getClassId()).orElse(null);
        String className = classMaster != null ? classMaster.getClassName() : null;

        String sectionName = null;
        if (studentClass.getSectionId() != null) {
            ClassSection classSection = classSectionRepository.findById(studentClass.getSectionId()).orElse(null);
            sectionName = classSection != null ? classSection.getSectionName() : null;
        }

        response.setClassId(studentClass.getClassId());
        response.setClassName(className);
        response.setSectionId(studentClass.getSectionId());
        response.setSectionName(sectionName);
        response.setDisplayClass(
                (className != null && sectionName != null) ? className + " - " + sectionName : className);
        response.setRollNo(studentClass.getRollNo());
        response.setClassTeacherName(
                resolveClassTeacher(studentClass.getClassId(), studentClass.getSectionId(), tenantId));
    }

    private String resolveClassTeacher(Integer classId, Integer sectionId, Integer tenantId) {
        List<TeacherClass> assignments = teacherClassRepository
                .findByClassIdAndSectionIdAndTenantIdAndIsActiveTrue(classId, sectionId, tenantId);

        if (assignments.isEmpty()) {
            return null;
        }

        Teacher teacher = teacherRepository.findById(assignments.get(0).getTeacherId()).orElse(null);
        if (teacher == null) {
            return null;
        }

        String name = ((teacher.getFirstName() != null ? teacher.getFirstName() : "") + " "
                + (teacher.getLastName() != null ? teacher.getLastName() : "")).trim();

        return name.isEmpty() ? teacher.getTeacherName() : name;
    }

    /// Joins the address parts into one Indian-format line, skipping blanks:
    /// "Block A, Sahyadri Apartments, Pune, Maharashtra - 411001"
    private String buildFullAddress(Student student) {
        List<String> parts = new ArrayList<>();

        if (hasText(student.getAddressLine1())) parts.add(student.getAddressLine1().trim());
        if (hasText(student.getCity())) parts.add(student.getCity().trim());
        if (hasText(student.getState())) parts.add(student.getState().trim());

        String address = String.join(", ", parts);

        if (hasText(student.getPostalCode())) {
            address = address.isEmpty()
                    ? student.getPostalCode().trim()
                    : address + " - " + student.getPostalCode().trim();
        }

        return address.isEmpty() ? null : address;
    }

    private boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    private String buildStudentName(Student student) {
        StringBuilder name = new StringBuilder();
        if (student.getFirstName() != null) name.append(student.getFirstName());
        if (student.getLastName() != null) name.append(" ").append(student.getLastName());
        return name.toString().trim();
    }
}
