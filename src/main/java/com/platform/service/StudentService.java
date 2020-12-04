package com.platform.service;

import com.platform.dao.StudentDao;
import com.platform.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author luxx
 * @date 2017/11/20 0020
 */
@Service
public class StudentService {

    public Student search(Student findStudent) {

        List<Student> students = studentDao.findAllByCompetitionIdAndNameAndIdentity(findStudent);
        if (students == null || students.size() == 0) {
            return null;
        } else {
            return students.get(0);
        }
    }

    @Autowired(required = false)
    private StudentDao studentDao;
}
