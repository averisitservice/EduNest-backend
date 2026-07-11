package com.edunest.service;

import com.edunest.dto.classes.ClassListResponse;
import com.edunest.dto.classes.ClassRequest;
import com.edunest.entity.*;
import com.edunest.error.CustomException;
import com.edunest.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    ClassMasterRepository classMasterRepository;

    @Autowired
    ClassSectionRepository classSectionRepository;

    @Autowired
    ClassSubjectRepository classSubjectRepository;

    @Autowired
    ClassFeeRepository classFeeRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    AcademicYearRepository academicYearRepository;

    @Override
    public List<ClassListResponse> getClassList(Integer tenantId) {
        List<ClassMaster> classes = classMasterRepository.findByTenantIdAndIsActiveTrue(tenantId);
        AcademicYear currentYear = academicYearRepository.findByTenantIdAndIsCurrentTrue(tenantId);
        List<ClassListResponse> responseList = new ArrayList<>();

        for (ClassMaster classMaster : classes) {
            List<ClassSection> classSections = classSectionRepository.findByClassIdAndTenantId(classMaster.getClassId(), tenantId);
            List<String> sectionNames = new ArrayList<>();
            for (ClassSection cs : classSections) {
                sectionNames.add(cs.getSectionName());
            }
            List<ClassSubject> classSubjects = classSubjectRepository.findByClassIdAndTenantId(classMaster.getClassId(), tenantId);
            List<String> subjectNames = new ArrayList<>();
            for (ClassSubject cs : classSubjects) {
                Subject subject = subjectRepository.findById(cs.getSubjectId()).orElse(null);
                if (subject != null) {
                    subjectNames.add(subject.getSubjectName());
                }
            }

            ClassFee classFee = null;
            if (currentYear != null) {
                classFee = classFeeRepository.findByClassIdAndAcademicYearIdAndTenantId(classMaster.getClassId(), currentYear.getAcademicYearId(), tenantId);
            }

            ClassListResponse response = new ClassListResponse();
            response.setClassId(classMaster.getClassId());
            response.setClassName(classMaster.getClassName());
            response.setIsActive(classMaster.getIsActive());
            response.setAnnualFee(classFee != null ? classFee.getAnnualFee() : null);
            response.setSections(sectionNames);
            response.setSubjects(subjectNames);
            responseList.add(response);
        }
        return responseList;
    }

    @Override
    public ClassRequest getClassById(Integer classId, Integer tenantId) {
        ClassMaster classMaster = classMasterRepository.findById(classId).orElseThrow(() -> new CustomException("Class", "Class not found"));

        AcademicYear currentYear = academicYearRepository.findByTenantIdAndIsCurrentTrue(tenantId);

        List<ClassSection> classSections = classSectionRepository.findByClassIdAndTenantId(classId, tenantId);
        List<String> sectionNames = new ArrayList<>();
        for (ClassSection cs : classSections) {
            sectionNames.add(cs.getSectionName());
        }

        List<ClassSubject> classSubjects = classSubjectRepository.findByClassIdAndTenantId(classId, tenantId);
        List<Integer> subjectIds = new ArrayList<>();
        for (ClassSubject cs : classSubjects) {
            subjectIds.add(cs.getSubjectId());
        }

        ClassFee classFee = null;
        if (currentYear != null) {
            classFee = classFeeRepository.findByClassIdAndAcademicYearIdAndTenantId(classId, currentYear.getAcademicYearId(), tenantId);
        }

        ClassRequest request = new ClassRequest();
        request.setClassName(classMaster.getClassName());
        request.setAnnualFee(classFee != null ? classFee.getAnnualFee() : null);
        request.setSections(sectionNames);
        request.setSubjectIds(subjectIds);
        return request;
    }

    @Override
    @Transactional
    public boolean saveClass(Integer classId, Integer tenantId, ClassRequest request) {

        boolean isEdit = (classId != null);
        ClassMaster classMaster;

        AcademicYear currentYear = academicYearRepository.findByTenantIdAndIsCurrentTrue(tenantId);
        if (currentYear == null) {
            throw new CustomException("academicYear", "No active academic year found. Please create one first.");
        }
        Integer academicYearId = currentYear.getAcademicYearId();

        if (isEdit) {
            classMaster = classMasterRepository.findById(classId).orElseThrow(() -> new CustomException("classId", "Class not found"));
            classMaster.setClassName(request.getClassName());
        } else {
            if (classMasterRepository.existsByClassNameAndTenantId(request.getClassName(), tenantId)) {
                throw new CustomException("className", "Class already exists");
            }
            classMaster = new ClassMaster();
            classMaster.setTenantId(tenantId);
            classMaster.setClassName(request.getClassName());
            classMaster.setIsActive(true);
        }

        ClassMaster savedClass = classMasterRepository.save(classMaster);
        Integer savedClassId = savedClass.getClassId();

        if (isEdit) {
            List<ClassSection> oldSections = classSectionRepository.findByClassIdAndTenantId(savedClassId, tenantId);
            classSectionRepository.deleteAll(oldSections);
        }

        if (request.getSections() != null && !request.getSections().isEmpty()) {
            for (String sectionName : request.getSections()) {
                ClassSection classSection = new ClassSection();
                classSection.setTenantId(tenantId);
                classSection.setClassId(savedClassId);
                classSection.setSectionName(sectionName);
                classSection.setIsActive(true);

                classSectionRepository.save(classSection);
            }
        }

        if (request.getSubjectIds() != null && !request.getSubjectIds().isEmpty()) {
            if (isEdit) {
                List<ClassSubject> oldSubjects = classSubjectRepository.findByClassIdAndTenantId(savedClassId, tenantId);
                classSubjectRepository.deleteAll(oldSubjects);
            }
            for (Integer subjectId : request.getSubjectIds()) {
                ClassSubject classSubject = new ClassSubject();
                classSubject.setTenantId(tenantId);
                classSubject.setClassId(savedClassId);
                classSubject.setSubjectId(subjectId);
                classSubject.setAcademicYearId(academicYearId);
                classSubject.setIsActive(true);
                classSubjectRepository.save(classSubject);
            }
        }

        if (request.getAnnualFee() != null) {
            ClassFee classFee = classFeeRepository.findByClassIdAndAcademicYearIdAndTenantId(savedClassId, academicYearId, tenantId);
            if (classFee == null) {
                classFee = new ClassFee();
                classFee.setTenantId(tenantId);
                classFee.setClassId(savedClassId);
                classFee.setAcademicYearId(academicYearId);
                classFee.setIsActive(true);
            }
            classFee.setAnnualFee(request.getAnnualFee());
            classFeeRepository.save(classFee);
        }
        return true;
    }


    @Override
    public boolean deleteClass(Integer classId) {
        ClassMaster classMaster = classMasterRepository.findById(classId).orElseThrow(() -> new CustomException("classId", "Class not found"));
        classMaster.setIsActive(false);
        classMasterRepository.save(classMaster);
        return true;
    }
}