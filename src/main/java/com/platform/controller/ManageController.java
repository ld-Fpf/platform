package com.platform.controller;

import com.alibaba.fastjson.JSONObject;
import com.platform.dao.CompetitionDao;
import com.platform.dao.StudentDao;
import com.platform.dao.UploadRecordDao;
import com.platform.dao.UserDao;
import com.platform.entity.Competition;
import com.platform.entity.Student;
import com.platform.entity.UploadRecord;
import com.platform.entity.User;
import com.platform.response.Result;
import com.platform.service.ExcelService;
import com.platform.service.RequestSerivce;
import com.platform.service.UserService;
import com.platform.util.ExcelUtil;
import com.platform.util.PageUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;


/**
 * 后台控制
 *
 * @author luxx
 */
@Controller
@RequestMapping("/manage")
public class ManageController {

    @RequestMapping("/exit")
    public void exit(HttpServletRequest request) {
        try {
            System.out.println(request.getSession().getAttribute("user"));
            request.getSession().removeAttribute("user");
            System.out.println(request.getSession().getAttribute("user"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/page/{str}")
    public String page(@PathVariable String str) {
        return "manage/" + str;
    }


    @ResponseBody
    @RequestMapping("/checkModify")
    public void checkModify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String attr = request.getParameter("attr");
        String option = request.getParameter("option");

        Result result;
        if (attr == null || "".equals(attr)) {
            result = new Result(Result.RESPONSE_RESULT_ERROR, "不能为空");
        } else {
            try {
                Object obj = request.getSession().getAttribute("user");
                if (obj == null) {
                    result = new Result(Result.RESPONSE_RESULT_ERROR, "登录已过期");
                } else {
                    User user = (User) obj;
                    switch (option) {
                        case "nick":
                            user.setNick(attr);
                            break;
                        case "name":
                            user.setName(attr);
                            break;
                        case "sex":
                            user.setSex(Boolean.parseBoolean(attr));
                            break;
                        case "password":
                            user.setPassword(attr);
                            break;
                    }
                    userDao.saveUser(user);
                    request.getSession().setAttribute("user", user);
                    result = new Result(Result.RESPONSE_RESULT_SUCCESS, "修改成功");
                }
            } catch (Exception e) {
                e.printStackTrace();
                result = new Result(Result.RESPONSE_RESULT_EXCEPTION, "修改失败");
            }
        }
        JSONObject json = new JSONObject();
        json.put("result", result);
        response.getWriter().write(json.toJSONString());
    }

    @ResponseBody
    @RequestMapping("/modifyTel")
    public void modifyTel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Result result;
        try {
            result = userService.modfiyTel(request);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(Result.RESPONSE_RESULT_EXCEPTION, "系统异常");
        }
        JSONObject json = new JSONObject();
        json.put("result", result);
        response.getWriter().write(json.toJSONString());
    }

    @ResponseBody
    @RequestMapping("/modifyStudent")
    public void modifyStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String serialNo = request.getParameter("serialNo");
        String sex = request.getParameter("sex");
        String grade = request.getParameter("grade");
        String contact = request.getParameter("contact");
        String identity = request.getParameter("identity");
        String school = request.getParameter("school");
        String home = request.getParameter("home");
        String groupNo = request.getParameter("groupNo");
        String teacher = request.getParameter("teacher");
        String source = request.getParameter("source");
        String ticketNo = request.getParameter("ticketNo");
        String roomNo = request.getParameter("roomNo");
        String seatNo = request.getParameter("seatNo");
        String examTime = request.getParameter("examTime");
        String examLocation = request.getParameter("examLocation");
        Student student = new Student(serialNo,name,sex,grade,contact,identity,school
                ,home,groupNo,teacher,source,ticketNo,roomNo,seatNo,examTime,examLocation,0,true);
        Result result;
        try {
            student.setId(Integer.parseInt(id));
            studentDao.modify(student);
            result = new Result(Result.RESPONSE_RESULT_SUCCESS, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(Result.RESPONSE_RESULT_EXCEPTION, "修改失败");
        }
        JSONObject json = new JSONObject();
        json.put("result", result);
        response.getWriter().write(json.toJSONString());
    }

    @ResponseBody
    @RequestMapping("/getCompetitionList")
    public void getCompetitionList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject json = new JSONObject();
        try {
            List<Competition> list = requestSerivce.findCompetitionByRequest(request, new HashMap());
            json.put("rows", list);
            json.put("total", list.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.getWriter().write(json.toJSONString());
    }

    @ResponseBody
    @RequestMapping("/addCompetition")
    public void addCompetition(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject json = new JSONObject();
        Result result;
        try {
            String name = request.getParameter("name");
            boolean valid = Boolean.parseBoolean(request.getParameter("valid"));
            if (name == null || "".equals(name)) {
                result = new Result(Result.RESPONSE_RESULT_ERROR, "竞赛名为空");
            } else {
                Competition competition = new Competition(name, valid);
                competitionDao.save(competition);
                result = new Result(Result.RESPONSE_RESULT_SUCCESS, "添加成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof ParseException) {
                result = new Result(Result.RESPONSE_RESULT_ERROR, "请选择是否开启");
            } else {
                result = new Result(Result.RESPONSE_RESULT_EXCEPTION, "添加失败，系统异常");
            }
        }
        json.put("result", result);
        response.getWriter().write(json.toJSONString());
    }

    @ResponseBody
    @RequestMapping("/modifyCompetition")
    public void modifyCompetition(HttpServletRequest request, HttpServletResponse response, @RequestParam String name, @RequestParam int id) throws IOException {
        JSONObject json = new JSONObject();
        Result result;
        try {
            Competition competition = competitionDao.findOneById(id);
            competition.setName(name);
            competitionDao.modify(competition);
            result = new Result(Result.RESPONSE_RESULT_SUCCESS, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(Result.RESPONSE_RESULT_EXCEPTION, "操作失败，未知异常");
        }
        json.put("result", result);
        response.getWriter().write(json.toJSONString());
    }

    @ResponseBody
    @RequestMapping("/changeCompetitionValid")
    public void changeCompetitionValid(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject json = new JSONObject();
        Result result;
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Competition competition = competitionDao.findOneById(id);
            competition.setValid(!competition.isValid());
            competitionDao.modify(competition);
            result = new Result(Result.RESPONSE_RESULT_SUCCESS, "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(Result.RESPONSE_RESULT_EXCEPTION, "操作失败，系统异常");
        }
        json.put("result", result);
        response.getWriter().write(json.toJSONString());
    }

    @ResponseBody
    @RequestMapping("/getUnsearchStudentList")
    public void getUnsearchStudentList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject json = new JSONObject();
        try {
            Map map = PageUtil.pageMap(request);

            String competitionId = request.getParameter("competitionId");
            if (competitionId != null && !"".equals(competitionId)) {
                map.put("competitionId", Integer.parseInt(competitionId));
            } else {
                map.put("competitionId", "");
            }
            map.put("state", "false");
            String name = request.getParameter("name");
            if (name != null && !"".equals(name)) {
                map.put("name", "%" + name + "%");
            } else {
                map.put("name", "%%");
            }
            List<Student> list = studentDao.findAllByPage(map);
            json.put("rows", list);
            json.put("total", studentDao.findSizeByMap(map));
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.getWriter().write(json.toJSONString());
    }

    @ResponseBody
    @RequestMapping("/getStudentList")
    public void getStudentList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject json = new JSONObject();
        try {
            Map map = PageUtil.pageMap(request);

            String competitionId = request.getParameter("competitionId");
            if (competitionId != null && !"".equals(competitionId) && !"所有".equals(competitionId)) {
                map.put("competitionId", Integer.parseInt(competitionId));
            } else {
                map.put("competitionId", "");
            }
            String name = request.getParameter("name");
            if (name != null && !"".equals(name)) {
                map.put("name", "%" + name + "%");
            } else {
                map.put("name", "%%");
            }
            List<Student> list = studentDao.findAllByPage(map);
            json.put("rows", list);
            json.put("total", studentDao.findSizeByMap(map));
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.getWriter().write(json.toJSONString());
    }

    @ResponseBody
    @RequestMapping("/getUploadRecord")
    public void getUploadRecord(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject json = new JSONObject();
        try {
            List<UploadRecord> list = uploadRecordDao.findAll();
            json.put("rows", list);
            json.put("total", list.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.getWriter().write(json.toJSONString());
    }


    @ResponseBody
    @RequestMapping("/addUser")
    public void addUser(HttpServletRequest request, HttpServletResponse response, @RequestParam String name, @RequestParam String pwd) throws IOException {
        JSONObject json = new JSONObject();
        Result result;
        try {
            User user = new User();
            user.setName(name);
            user.setPassword(pwd);
            userDao.addUser(user);
            result = new Result(Result.RESPONSE_RESULT_SUCCESS, "添加成功,用户ID为：" + user.getId());
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(Result.RESPONSE_RESULT_EXCEPTION, "系统异常");
        }
        json.put("result", result);
        response.getWriter().write(json.toJSONString());
    }

    @ResponseBody
    @RequestMapping("/getUserList")
    public void getUserList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject json = new JSONObject();
        try {
            List<User> list = userDao.findAllUser();
            json.put("rows", list);
            json.put("total", list.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.getWriter().write(json.toJSONString());
    }

    @ResponseBody
    @RequestMapping("/deleteStudent")
    public void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Result result;
        JSONObject json = new JSONObject();
        try {
            String[] idStrs = request.getParameterValues("id");
            for (String idStr : idStrs) {
                studentDao.delete(Long.parseLong(idStr));
            }
            result = new Result(Result.RESPONSE_RESULT_SUCCESS, "删除成功");
        } catch (Exception e) {
            result = new Result(Result.RESPONSE_RESULT_EXCEPTION, "操作失败");
            e.printStackTrace();
        }
        json.put("result", result);
        response.getWriter().write(json.toJSONString());
    }

    @ResponseBody
    @RequestMapping("/deleteCompetition")
    public void deleteCompetition(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject json = new JSONObject();
        Result result;
        try {
            String id = request.getParameter("id");
            competitionDao.delete(Integer.parseInt(id));
            studentDao.deleteByCompetitionId(Integer.parseInt(id));
            result = new Result(Result.RESPONSE_RESULT_SUCCESS, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(Result.RESPONSE_RESULT_EXCEPTION, "操作失败");
        }
        json.put("result", result);
        response.getWriter().write(json.toJSONString());
    }

    @ResponseBody
    @RequestMapping("/deleteUploadRecord")
    public void deleteUploadRecord(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject json = new JSONObject();
        Result result;
        try {
            String[] idStrs = request.getParameterValues("id");
            for (String idStr : idStrs) {
                uploadRecordDao.delete(Long.parseLong(idStr));
            }
            result = new Result(Result.RESPONSE_RESULT_SUCCESS, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(Result.RESPONSE_RESULT_EXCEPTION, "操作失败");
        }
        json.put("result", result);
        response.getWriter().write(json.toJSONString());
    }

    @RequestMapping("/aaa")
    public String aaa(HttpServletRequest request) {
        return "user/aaa";
    }


    @ResponseBody
    @RequestMapping("/download")
    public void download(@RequestParam int competitionId, HttpServletResponse response) throws IOException {
        LinkedHashMap<String, String> headers = new LinkedHashMap<>();
        headers.put("serialNo", "序号");
        headers.put("name", "选手姓名");
        headers.put("sex", "性别");
        headers.put("grade", "年级");
        headers.put("contact", "联系方式");
        headers.put("identity", "身份证号");
        headers.put("school", "就读学校");
        headers.put("home", "住址所在县市区");
        headers.put("groupNo", "参赛组别");
        headers.put("teacher", "指导老师");
        headers.put("source", "来源");
        headers.put("ticketNo", "准考证号");
        headers.put("roomNo", "考场号");
        headers.put("seatNo", "座位号");
        headers.put("examTime", "参赛时间");
        headers.put("examLocation", "考试地点");

        Map map = new HashMap();
        map.put("competitionId", competitionId);
        map.put("state", false);
        List<Student> list = studentDao.findAllByCompetitionIdAndState(map);

        List<JSONObject> records = new ArrayList<>();
        for (Student student : list) {
            JSONObject object = (JSONObject) JSONObject.toJSON(student);
            JSONObject record = new JSONObject();
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                record.put(entry.getKey(), object.get(entry.getKey()));
            }
            records.add(record);
            System.out.println(record.toJSONString());
        }
        HSSFWorkbook wb = ExcelUtil.createExcel(records, headers);

        List<Competition> competitions = competitionDao.findAll();
        String competitionName = "";
        for (Competition competition : competitions) {
            if (competitionId == competition.getId()) {
                competitionName = competition.getName();
                break;
            }
        }
        String filename = "竞赛-" + competitionName + "-未查询同学名单.xls";
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes(), "iso-8859-1"));
        BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
        try {
            wb.write(bos);
        } finally {
            bos.flush();
            bos.close();
            wb.close();
        }
    }

    @ResponseBody
    @RequestMapping("/upload")
    public void upload(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Result result;
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //创建解析数据请求项
        ServletFileUpload upload = new ServletFileUpload(factory);
        String fileName = "文件名获取失败";
        try {
            //开始解析
            List<FileItem> list = upload.parseRequest(request);
            System.out.println(list.size());
            if (list.size() > 2) {
                result = new Result(Result.RESPONSE_RESULT_ERROR, "上传文件过多");
            } else if (list.size() < 2) {
                result = new Result(Result.RESPONSE_RESULT_ERROR, "未上传文件");
            } else {
                int competitionId = Integer.parseInt(list.get(0).getString());

                FileItem item = list.get(1);
                fileName = item.getName();
                String extName = fileName.substring(fileName.lastIndexOf("."));
                if (".xls".equals(extName)) {
                    List<Student> students = new ExcelService().readXls(item, competitionId);
                    for (Student student : students) {
                        studentDao.save(student);
                    }
                    result = new Result(Result.RESPONSE_RESULT_SUCCESS, "上传成功");
                } else if (".xlsx".equals(extName)) {
                    List<Student> students = excelService.readXlsx(item, competitionId);
                    for (Student student : students) {
                        student.setCompetitionId(competitionId);
                        studentDao.save(student);
                    }
                    result = new Result(Result.RESPONSE_RESULT_SUCCESS, "上传成功");
                } else {
                    result = new Result(Result.RESPONSE_RESULT_ERROR, "非excel文件");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(Result.RESPONSE_RESULT_EXCEPTION, "文件解析失败");
        }
        JSONObject json = new JSONObject();
        json.put("result", result);
        try {
            User user = (User) request.getSession().getAttribute("user");
            UploadRecord record = new UploadRecord("", fileName, result.getResult(), result.getMessage(), user.getName());
            uploadRecordDao.save(record);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("/platform/manage/page/upload_record");
    }

    @Autowired(required = false)
    private UserDao userDao;
    @Autowired(required = false)
    private StudentDao studentDao;
    @Autowired(required = false)
    private UploadRecordDao uploadRecordDao;
    @Autowired(required = false)
    private CompetitionDao competitionDao;

    @Autowired
    private UserService userService;
    @Autowired
    private ExcelService excelService;
    @Autowired
    private RequestSerivce requestSerivce;

}
