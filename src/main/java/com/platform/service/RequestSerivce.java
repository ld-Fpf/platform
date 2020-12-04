package com.platform.service;

import com.platform.dao.CompetitionDao;
import com.platform.entity.Competition;
import com.platform.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by luxx on 2017/11/17 0017.
 */
@Service
public class RequestSerivce {

    public List<Competition> findCompetitionByRequest(HttpServletRequest request, Map map) throws ParseException {
        String startStr = request.getParameter("start");
        String endStr = request.getParameter("end");
        String name = request.getParameter("name");
        String validStr = request.getParameter("valid");

        Date start = null, end = null;
        if (startStr != null && !"".equals(startStr)) {
            start = DateUtil.dateFormat.parse(startStr);
        }
        if (endStr != null && !"".equals(endStr)) {
            end = DateUtil.dateFormat.parse(endStr);
        }
        if (start != null) {
            if (end == null) {
                end = DateUtil.tomorrow();
            } else {
                end = DateUtil.tomorrow(end);
            }
        }
        map.put("start", start);
        map.put("end", end);

        if (validStr != null && !"".equals(validStr) && !"所有".equals(validStr)) {
            map.put("valid", Boolean.parseBoolean(validStr));
        }
        if (name != null && !"".equals(name)) {
            map.put("name", "%" + name + "%");
        }
        return competitionDao.findAllByCondition(map);
    }

    @Autowired(required = false)
    private CompetitionDao competitionDao;
}
