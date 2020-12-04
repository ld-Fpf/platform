package com.platform.dao;

import com.platform.entity.Student;

import java.util.List;
import java.util.Map;

/**
 * @author luxx
 */
public interface StudentDao {

    List<Student> findAllByPage(Map map);

    long findSizeByMap(Map map);

    List<Student> findAll();

    List<Student> findAllByCompetitionId(int competitionId);

    List<Student> findAllByCompetitionIdAndNameAndIdentity(Student student);

    List<Student> findAllByCompetitionIdAndState(Map map);

    void modifyOneByState(Student student);

    void save(Student student);

    void delete(long id);

    void modify(Student student);

    void deleteByCompetitionId(int competitionId);

    Student findOneById(long id);
}

