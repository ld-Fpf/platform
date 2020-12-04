package com.platform.dao;

import com.platform.entity.UploadRecord;

import java.util.List;
import java.util.Map;

/**
 *
 * @author luxx
 * @date 2017/11/16 0016
 */
public interface UploadRecordDao {

    List<UploadRecord> findAll();

    List<UploadRecord> findAllByPage(Map map);

    void delete(long id);

    void save(UploadRecord uploadRecord);
}
