package com.edunest.service;

import com.edunest.dto.homework.HomeworkRequest;
import com.edunest.dto.homework.HomeworkResponse;
import com.edunest.entity.AcademicYear;
import com.edunest.entity.Homework;
import com.edunest.entity.Subject;
import com.edunest.entity.Teacher;
import com.edunest.error.CustomException;
import com.edunest.repository.AcademicYearRepository;
import com.edunest.repository.HomeworkRepository;
import com.edunest.repository.SubjectRepository;
import com.edunest.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class HomeworkServiceImpl implements HomeworkService {

    @Autowired
    HomeworkRepository homeworkRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    AcademicYearRepository academicYearRepository;

    private AcademicYear getCurrentYear(Integer tenantId) {
        AcademicYear currentYear = academicYearRepository.findByTenantIdAndIsCurrentTrue(tenantId);
        if (currentYear == null) {
            throw new CustomException("academicYear", "No active academic year found");
        }
        return currentYear;
    }

    private String teacherName(Integer teacherId) {
        if (teacherId == null) {
            return null;
        }
        Teacher teacher = teacherRepository.findById(teacherId).orElse(null);
        return teacher != null ? teacher.getTeacherName() : null;
    }

    private String subjectName(Integer subjectId) {
        if (subjectId == null) {
            return null;
        }
        Subject subject = subjectRepository.findById(subjectId).orElse(null);
        return subject != null ? subject.getSubjectName() : null;
    }

    @Override
    public List<HomeworkResponse> getHomeWorkList(Integer tenantId, Integer classId, Integer sectionId, String type) {
        AcademicYear currentYear = getCurrentYear(tenantId);
        String typeFilter = (type != null) ? type : "";

        List<Homework> items = homeworkRepository.findList(tenantId, currentYear.getAcademicYearId(), classId, sectionId, typeFilter);

        List<HomeworkResponse> result = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            Homework h = items.get(i);
            HomeworkResponse response = new HomeworkResponse();
            response.setHomeworkId(h.getHomeworkId());
            response.setClassId(h.getClassId());
            response.setSectionId(h.getSectionId());
            response.setSubjectId(h.getSubjectId());
            response.setSubjectName(subjectName(h.getSubjectId()));
            response.setType(h.getType());
            response.setTitle(h.getTitle());
            response.setDescription(h.getDescription());
            response.setDueDate(h.getDueDate());
            response.setAttachmentUrl(h.getAttachmentUrl());
            response.setCreatedBy(teacherName(h.getCreatedBy()));
            response.setUpdatedBy(teacherName(h.getUpdatedBy()));
            response.setUpdatedDate(h.getUpdatedDate());
            result.add(response);
        }
        return result;
    }

    @Override
    @Transactional
    public boolean saveHomeWork(Integer tenantId, Integer loginTeacherId, HomeworkRequest request) {
        AcademicYear currentYear = getCurrentYear(tenantId);

        if (request.getClassId() == null) {
            throw new CustomException("classId", "Class is required");
        }
        if (request.getTitle() == null || request.getTitle().isBlank()) {
            throw new CustomException("title", "Title is required");
        }

        Homework homework;
        if (request.getHomeworkId() != null) {
            homework = homeworkRepository.findById(request.getHomeworkId())
                    .orElseThrow(() -> new CustomException("homeworkId", "Item not found"));
        } else {
            homework = new Homework();
            homework.setTenantId(tenantId);
            homework.setAcademicYearId(currentYear.getAcademicYearId());
            homework.setIsActive(true);
            homework.setCreatedBy(loginTeacherId);
        }

        homework.setClassId(request.getClassId());
        homework.setSectionId(request.getSectionId());
        homework.setSubjectId(request.getSubjectId());
        homework.setType(request.getType() != null ? request.getType() : "HOMEWORK");
        homework.setTitle(request.getTitle());
        homework.setDescription(request.getDescription());
        homework.setDueDate(request.getDueDate());
        homework.setAttachmentUrl(request.getAttachmentUrl());
        homework.setUpdatedBy(loginTeacherId);
        homework.setUpdatedDate(LocalDateTime.now());
        homeworkRepository.save(homework);
        return true;
    }

    @Override
    public boolean deleteHomeWork(Integer tenantId, Integer homeworkId) {
        Homework homework = homeworkRepository.findById(homeworkId)
                .orElseThrow(() -> new CustomException("homeworkId", "Item not found"));
        homework.setIsActive(false);
        homeworkRepository.save(homework);
        return true;
    }
}
