package com.baiyx.wfwbitest.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baiyx.wfwbitest.dao.UserDao;
import com.baiyx.wfwbitest.entity.*;
import com.baiyx.wfwbitest.service.UserService;
import com.baiyx.wfwbitest.utils.*;
import com.baomidou.dynamic.datasource.annotation.DS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: 白宇鑫
 * @Date: 2021/6/30 上午 11:32
 * @Description:
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao UserDao;

    //测试多数据源配置注解@DS
    @Override
    // @DS("slave_1")
    @Async("asyncServiceExecutor")
    public List<User> findAll() {
        List<User> users = UserDao.findAll();
//        System.out.println("=============测试定时任务查询信息并且输出开始==============");
//        System.out.println("users" + users);
//        System.out.println("=============测试定时任务查询信息并且输出结束==============");
        return users;
    }

    @Override
    public R insertOne(User user) {
        User u = new User();
        // 因为此处使用的findByName方法先查询数据是否存在,
        // 所以UserController的insertOne入口处为@Decrypt(description = "findByName")
        u = UserDao.findByName(user.getUsername());
        if (u == null) {
            UserDao.insertOne(user);
            return R.ok("ok",user);
        } else {
            return R.error("已存在编号为" + u.toString() + "的数据,请重新插入...");
        }
    }

    @Override
    public void deleteByName(QueryRequestVo queryRequestVo) {
        User u = new User();
        u = UserDao.findByName(queryRequestVo.getUser().getUsername());
        if (u != null) {
            UserDao.deleteByName(queryRequestVo.getUser().getUsername());
        } else {
            throw new RuntimeException("删除的数据不存在,请重新输入...");
        }
    }

    @Override
    public void updateOne(QueryRequestVo queryRequestVo) {
        User u = new User();
        u = UserDao.findById(queryRequestVo.getUser().getId());
        if (u != null) {
            UserDao.updateOne(queryRequestVo.getUser());
        } else {
            throw new RuntimeException("该条数据不存在,请重新输入...");
        }
    }

    @Override
    public R findById(QueryRequestVo queryRequestVo) {
        User u = UserDao.findById(queryRequestVo.getUser().getId());
        if(u == null){
            return R.error("查询失败",u);
        }
        return R.ok("查询成功",u);
    }

    @Override
    public User findByName(QueryRequestVo queryRequestVo) {
        return UserDao.findByName(queryRequestVo.getUser().getUsername());
    }

    @Override
    public List<User> findByTime(QueryRequestVo queryRequestVo) {
        List<User> users = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startTime = simpleDateFormat.parse("1990-01-01");
            Date endTime = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            if (queryRequestVo.getKssj() != null && queryRequestVo.getJssj() != null) {
                startTime = queryRequestVo.getKssj();
                endTime = queryRequestVo.getJssj();
                users = UserDao.findByTime(startTime, endTime);
            } else {
                users = UserDao.findAll();
            }
            return users;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public RowConvertColUtil.ConvertData RowConvertCol(QueryRequestVo queryRequestVo) {
        List<User> users = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startTime = simpleDateFormat.parse("1993-01-01");
            Date endTime = simpleDateFormat.parse("1995-12-31");
            if (queryRequestVo.getKssj() != null && queryRequestVo.getJssj() != null) {
                startTime = queryRequestVo.getKssj();
                endTime = queryRequestVo.getJssj();
                users = UserDao.findByTime(startTime, endTime);
            } else {
                users = UserDao.findAll();
            }
            String[] fixedColumn = {"id", "username", "address", "sex", "birthday"};
            String[] fixedColumnName = {"序号", "姓名", "地址", "性别", "生日"};
            RowConvertColUtil.ConvertData lists = RowConvertColUtil.doConvertReturnObj(users, "username", fixedColumn, "sex", true, fixedColumnName, "0");
            return lists;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //批量插入
    @Override
    public void insertAll(List<User> userList) {
        UserDao.insertAll(userList);
    }

    //测试MacUtil工具类和批量查询
    @Override
    public Map getIPorMACaddress(HttpServletRequest request) throws Exception {
        Map<String,String> address = new HashMap<String,String>();
        ClientMsg RequestMsg = MacUtil.getRequestMsg(request);
        String IpAddr =  MacUtil.getIpAddr(request);
        String addr = MacUtil.getAddress(IpAddr);
        //String macAddress1 = MacUtil.getMACAddress(InetAddress.getByName("www.baidu.com"));
        String macAddress2 = MacUtil.getMACAddress(InetAddress.getByName(IpAddr));
        String macAddress3 = MacUtil.getMACAddress(InetAddress.getLocalHost());
        //String macAddress4 = MacUtil.getMACAddress(InetAddress.getLoopbackAddress());
        address.put("RequestMsg", RequestMsg.toString());
        address.put("IpAddr", IpAddr);
        address.put("macAddress-getByName", macAddress2);
        address.put("macAddress-getLocalHost",macAddress3);
        return address;
    }

    @Override
    // @DS("slave_1")
    public ResultMsg removeESC() {
        JSONObject[] jsonObjects = ReadTXTtoJsonObjUtil.readTXTtoObj("");
        List<Projbase> projbaseList = null;
        ArrayList projIds = null;
        ArrayList ywhs = null;
        ArrayList projId = null;
        ResultMsg resultMsg = new ResultMsg();
        //存放推送的业务号
        if (jsonObjects != null && jsonObjects.length != 0){
            projbaseList = new ArrayList<>(jsonObjects.length);
            projIds = new ArrayList(jsonObjects.length);
            ywhs = new ArrayList(jsonObjects.length);
            for (int i = 0; i < jsonObjects.length; i++) {
                if(jsonObjects[i] != null && !"".equals(jsonObjects[i])){
                    Projbase projbase = new Projbase();
                    projbase.setYwh(jsonObjects[i].getString("projId"));
                    projbase.setProjId(jsonObjects[i].getString("projId"));
                    projbase.setJsonObj(jsonObjects[i].toJSONString());
                    projbase.setCjsj(null);
                    projbase.setMatterCode(jsonObjects[i].getString("matterCode"));
                    projbase.setProjectName(jsonObjects[i].getString("projectName"));
                    projbase.setGmtApply(jsonObjects[i].getString("gmtApply"));
                    projbase.setApplyName(jsonObjects[i].getJSONObject("affFormInfo").getString("sqrname"));
                    projbase.setApplyCardNo(jsonObjects[i].getJSONObject("affFormInfo").getString("zjh"));
                    projbase.setBdcqzh("");
                    projbase.setBdcdjzmh("");
                    projbase.setZl("");
                    projbase.setAreaCode(jsonObjects[i].getString("areaCode"));
                    projbase.setZt(666);
                    projbaseList.add(projbase);
                    projIds.add(jsonObjects[i].getString("projId"));
                }
            }
            projId = UserDao.findByprojbase(projIds);
            if (projId != null && projId.size() !=0 ) {
                // 筛选出来已存在的ywh存放到一个list
                for (int i = 0; i < projId.toArray().length; i++) {
                    if (projIds.contains(projId.toArray()[i])) {
                        ywhs.add(projId.toArray()[i]);
                    }
                }
                // projbaseList中已存在的ywh进行剔除后在推送.
                for (int i = 0; i < ywhs.size(); i++) {
                    for (int k = 0; k < projbaseList.size(); k++) {
                        if (projbaseList.get(k).getProjId().equals(ywhs.get(i))) {
                                projbaseList.remove(k);
                        }
                    }
                }
            }
        }
        if(projbaseList != null && projbaseList.size() != 0){
            UserDao.removeESC(projbaseList);
            //调办结接口
            StringBuffer stringBuffer = ToInterface.interfaceUtil("https://govbdctj.zjzwfw.gov.cn:7079/api/right/ignoreOuth/house/callback/fillhousefinish",projIds.toString(),"POST");
            JSONObject sbObj = (JSONObject) JSON.parse(stringBuffer.toString());
            resultMsg.setStatus(sbObj.getString("status"));
            resultMsg.setMsg(sbObj.getString("msg"));
            resultMsg.setData(sbObj.getString("data"));
            resultMsg.setCode(sbObj.getString("code"));
            resultMsg.setStackTrace(sbObj.getString("stackTrace"));
            resultMsg.setRequestId(sbObj.getString("requestId"));
            resultMsg.setSuccess(sbObj.getString("success"));
            if(ywhs != null && ywhs.size() != 0){
                resultMsg.setMessage("这些数据: " + StrSpliceUtils.strSplice(ywhs) + " 已存在projbase表中!");
            }else{
                resultMsg.setMessage(sbObj.getString("message"));
            }
            return resultMsg;
        }else{
            resultMsg.setStatus("89757");
            resultMsg.setMsg("不存在可推的数据或数据已存在projbase表中!!!");
            resultMsg.setData("");
            resultMsg.setCode("-1");
            resultMsg.setStackTrace("stackTrace");
            resultMsg.setRequestId("123456789");
            resultMsg.setSuccess("false");
            if(ywhs != null && ywhs.size() != 0){
                resultMsg.setMessage("这些数据: " + StrSpliceUtils.strSplice(ywhs) + " 已存在projbase表中!");
            }else{
                resultMsg.setMessage("不存在可推的数据或数据已存在projbase表中!!!");
            }
            return resultMsg;
        }
    }
}