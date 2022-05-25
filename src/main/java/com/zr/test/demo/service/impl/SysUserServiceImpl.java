package com.zr.test.demo.service.impl;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zr.test.demo.common.Constant;
import com.zr.test.demo.common.PageInfo;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.component.exception.CustomException;
import com.zr.test.demo.config.enums.ErrorCode;
import com.zr.test.demo.model.dto.*;
import com.zr.test.demo.model.entity.RoleEntity;
import com.zr.test.demo.model.entity.SysUserEntity;
import com.zr.test.demo.model.entity.UserEntity;
import com.zr.test.demo.model.pojo.AuthKey;
import com.zr.test.demo.model.vo.GeneralUserVO;
import com.zr.test.demo.model.vo.StudentVO;
import com.zr.test.demo.model.vo.SysLoginVO;
import com.zr.test.demo.model.vo.SystemUserVO;
import com.zr.test.demo.repository.RoleDaoImpl;
import com.zr.test.demo.repository.RoleMenuDaoImpl;
import com.zr.test.demo.repository.SysUserDaoImpl;
import com.zr.test.demo.repository.UserDaoImpl;
import com.zr.test.demo.service.ISysUserService;
import com.zr.test.demo.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SysUserServiceImpl implements ISysUserService {
    private final SysUserDaoImpl userDao;
    private final UserDaoImpl gUserDao;
    private final RoleDaoImpl roleDao;
    private final RoleMenuDaoImpl roleMenuDao;

    @Autowired
    public SysUserServiceImpl(SysUserDaoImpl userDao, UserDaoImpl gUserDao,
                              RoleDaoImpl roleDao, RoleMenuDaoImpl roleMenuDao) {
        this.userDao = userDao;
        this.gUserDao = gUserDao;
        this.roleDao = roleDao;
        this.roleMenuDao = roleMenuDao;
    }


    @Override
    public Result<SysLoginVO> login(SysLoginDTO loginDTO, HttpServletRequest request) {
        SysUserEntity userEntity = new SysUserEntity();
        SysUserEntity user;
        //校验参数
        userEntity.setUsername(loginDTO.getUsername());
        List<SysUserEntity> list = userDao.selectByEntity(userEntity);
        SysLoginVO vo=new SysLoginVO();

        if (ListUtil.isEmpty(list)) {
            return Result.fail(ErrorCode.SYS_USERNAME_EXIST_ERROR_ERR, "用户名不存在",vo);
        }
        if (list.size() > 1) {
            throw new CustomException(ErrorCode.SEARCH_TERREC_FAIL, "查询错误，请联系管理员");
        }
        user = list.get(0);
        if (!user.getPassword().equals(Md5Util.getMD5(loginDTO.getPassword()))) {
            return Result.fail(ErrorCode.SYS_USER_OR_PWD_ERROR_ERR, "密码错误",vo);
        }
        if (user.getStatus() == 0) {
            return Result.fail(ErrorCode.SYS_ACCOUNT_HAS_BANED_ERR,vo);
        }
        //登录成功后的操作
        String token;
        try {
            //是否是系统用户|userid|time|是否是学生|是否是内网|权限id
            token = DESUtils.encrypt(DESUtils.KEY_DEFALUT, MessageFormat.format("{0}|{1}|{2}|{3}|{4}|{5}",
                    1, user.getId(), TimeUtil.getTime(), 0, 0, user.getRole()));
        } catch (Exception e) {
            throw new CustomException(ErrorCode.SYS_ENCRIPT_ERR, e.getMessage());
        }
        if (token == null) {
            throw new CustomException(ErrorCode.SYS_PARAM_INNER_ERR, "des encrypt key = null");
        }
        try {
            token = URLEncoder.encode(token, "utf-8");
        } catch (Exception e) {
            log.error("key des ulrencode error ：" + e.getMessage(), e);
            throw new CustomException(ErrorCode.SYS_PARAM_INNER_ERR, "key urlencode error");
        }
        AuthKey authKey = new AuthKey();
        authKey.setIntranet(0);
        authKey.setSystem(1);
        authKey.setStudent(0);
        authKey.setRoleId(user.getRole());
        authKey.setUserId(user.getId());
        authKey.setTime(TimeUtil.getTime());
        request.getSession().setAttribute(token, authKey);
        vo.setToken(token);
        vo.setMenu(roleMenuDao.selectMenusByRoleId(user.getRole()));
        return Result.success(vo);
    }

    @Override
    public Result<PageInfo<StudentVO>> queryStudent(StudentDTO user, HttpServletRequest request) {
        IPage<UserEntity> page = gUserDao.selectByPage(user);
        List<UserEntity> list = page.getRecords();
        long total = page.getTotal();
        List<StudentVO> res = new ArrayList<>(list.size());
        list.forEach(l -> {
            StudentVO vo = new StudentVO();
            vo.setId(l.getId());
            vo.setLastLogin(l.getLastLogin());
            vo.setName(l.getName());
            vo.setIntranet(l.getIntranet());
            vo.setSchool(l.getSchool());
            vo.setRegister(l.getRegister());
            vo.setStatus(l.getStatus());
            vo.setStudentNo(l.getStudentNo());
            res.add(vo);
        });
        PageInfo<StudentVO> pageInfo = new PageInfo<>();
        pageInfo.setTotal(ListUtil.isEmpty(res) ? 0 : total);
        pageInfo.setList(res);
        pageInfo.setPage(user.getPage());
        pageInfo.setPageSize(user.getPageSize());
        return Result.success(pageInfo);
    }

    @Override
    public Result<PageInfo<GeneralUserVO>> queryGeneral(GeneralUserDTO user, HttpServletRequest request) {
        UserEntity userEntity = new UserEntity();
        if (!StringUtil.isEmpty(user.getPhone())) {
            userEntity.setPhone(user.getPhone());
        }
        if (user.getStatus() != null) {
            userEntity.setStatus(user.getStatus());
        }
        IPage<UserEntity> page = gUserDao.selectByPage(user);
        List<UserEntity> list = page.getRecords();
        long total = page.getTotal();
        List<GeneralUserVO> res = new ArrayList<>(list.size());
        list.forEach(l -> {
            GeneralUserVO vo = new GeneralUserVO();
            BeanUtils.copyProperties(l, vo);
            res.add(vo);
        });
        PageInfo<GeneralUserVO> pageInfo = new PageInfo<>();
        pageInfo.setTotal(ListUtil.isEmpty(res) ? 0 : total);
        pageInfo.setList(res);
        pageInfo.setPage(user.getPage());
        pageInfo.setPageSize(user.getPageSize());
        return Result.success(pageInfo);
    }

    @Override
    public Result<Object> updateStudent(UpdateStudentDTO user, HttpServletRequest request) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        return Result.success(gUserDao.updateById(userEntity));
    }

    @Override
    public Result<Object> importStudent(MultipartFile file, HttpServletRequest request) {
        ImportParams params = new ImportParams();
        //去掉标题行
        params.setTitleRows(1);
//
//        try {
//            List<Employee> list = ExcelImportUtil.importExcel(file.getInputStream(), student.class, params);
//            list.forEach(employee -> {
//                //民族id  new Nation(employee.getNation().getName()))  这是重写了equals和hashcode方法
//                employee.setNationId(nationList.get(nationList.indexOf(new Nation(employee.getNation().getName()))).getId());
//                //政治面貌id
//                employee.setPoliticId(politicsStatusList.get(politicsStatusList.indexOf(new PoliticsStatus(employee.getPoliticsStatus().getName()))).getId());
//                //部门id
//                employee.setDepartmentId(departmentList.get(departmentList.indexOf(new Department(employee.getDepartment().getName()))).getId());
//                //职称id
//                employee.setJobLevelId(joblevelList.get(joblevelList.indexOf(new Joblevel(employee.getJoblevel().getName()))).getId());
//                //职位id
//                employee.setPosId(positionList.get(positionList.indexOf(new Position(employee.getPosition().getName()))).getId());
//            });
//            if (employeeService.saveBatch(list)){
//                return RespBean.success("导入成功!");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return null;

    }

    @Override
    public Result<Object> deleteGeneral(UpdateStudentDTO user, HttpServletRequest request) {
        return Result.success(gUserDao.deleteById(user.getId()));
    }

    @Override
    public Result<Object> addSystem(SystemUserDTO user, HttpServletRequest request) {
        SysUserEntity userEntity = new SysUserEntity();
        if (StringUtil.isEmpty(user.getName())||StringUtil.isEmpty(user.getUsername())||
                StringUtil.isEmpty(user.getPassword())||user.getRole()==null) {
            throw new CustomException(ErrorCode.SYS_PARAM_ERR);
        }
        userEntity.setName(user.getName());
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(Md5Util.getMD5(user.getPassword()));
        userEntity.setRole(user.getRole());
        userEntity.setStatus(1);
        return Result.success(userDao.insertOne(userEntity));
    }

    @Override
    public Result<PageInfo<SystemUserVO>> querySystem(GeneralUserDTO user, HttpServletRequest request) {
        String token = request.getHeader(Constant.TOKEN);
        AuthKey authKey = (AuthKey) request.getSession().getAttribute(token);
        IPage<SysUserEntity> page = userDao.querySystem(user.getStatus(), authKey.getUserId(), user.getPage(), user.getPageSize());
        List<SysUserEntity> list = page.getRecords();
        long total = page.getTotal();
        List<SystemUserVO> res = new ArrayList<>(list.size());
        List<RoleEntity> roles = roleDao.selectAll();
        if (ListUtil.isEmpty(roles)) {
            throw new CustomException(ErrorCode.SYS_CUSTOM_ERR, "角色表为空");
        }
        Map<String, String> map = roles.stream().collect(Collectors.toMap(e->e.getId().toString(), RoleEntity::getName));
        list.forEach(l -> {
            SystemUserVO vo = new SystemUserVO();
            vo.setId(l.getId());
            vo.setUsername(l.getUsername());
            vo.setName(l.getName());
            vo.setRole(l.getRole());
            vo.setRoleName(Optional.ofNullable(map.get(l.getRole().toString())).orElse(""));
            vo.setStatus(l.getId());
            res.add(vo);
        });
        PageInfo<SystemUserVO> pageInfo = new PageInfo<>();
        pageInfo.setTotal(ListUtil.isEmpty(res) ? 0 : total);
        pageInfo.setList(res);
        pageInfo.setPage(user.getPage());
        pageInfo.setPageSize(user.getPageSize());
        return Result.success(pageInfo);
    }

    @Override
    public Result<Object> updateSystem(SystemUserDTO user, HttpServletRequest request) {
        if (user.getId() == null) {
            throw new CustomException(ErrorCode.SYS_PARAM_ERR);
        }

        SysUserEntity userEntity = new SysUserEntity();
        userEntity.setId(user.getId());
        if (user.getStatus() != null) {
            userEntity.setStatus(user.getStatus());
        } else {
            if (user.getName() == null||user.getUsername() == null||user.getRole()==null) {
                throw new CustomException(ErrorCode.SYS_PARAM_ERR);
            }
            userEntity.setName(user.getName());
            userEntity.setUsername(user.getUsername());
            if (!StringUtil.isEmpty(user.getPassword())) {
                userEntity.setPassword(Md5Util.getMD5(user.getPassword()));
            }
            userEntity.setRole(user.getRole());
        }
        return Result.success(userDao.updateById(userEntity));
    }

    @Override
    public Result<Object> deleteSystem(UserIdDTO user, HttpServletRequest request) {
        return Result.success(userDao.deleteById(user.getId()));
    }

    @Override
    public Result<Object> updateSystemPassword(SystemPasswordDTO user, HttpServletRequest request) {
        String token = request.getHeader(Constant.TOKEN);
        AuthKey authKey = (AuthKey) request.getSession().getAttribute(token);
        SysUserEntity entity = new SysUserEntity();
        entity.setId(authKey.getUserId());
        entity.setPassword(Md5Util.getMD5(user.getPassword()));
        List<SysUserEntity> list = userDao.selectByEntity(entity);
        if (ListUtil.isEmpty(list)) {
            return Result.fail(ErrorCode.SYS_USER_OR_PWD_ERROR_ERR, "密码错误");
        }
        entity.setPassword(Md5Util.getMD5(user.getNewPassword()));
        return Result.success(userDao.updateById(entity));
    }


}
