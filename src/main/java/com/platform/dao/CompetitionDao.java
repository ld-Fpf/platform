package com.platform.dao;

import com.platform.entity.Competition;

import java.util.List;
import java.util.Map;

/**
 *
 * @author luxx
 * @date 2017/11/16 0016
 */
public interface CompetitionDao {

    List<Competition> findAll();

    void modify(Competition competition);

    void save(Competition competition);

    Competition findOneById(int id);

    List<Competition> findAllByCondition(Map map);

    void delete(int id);

}
