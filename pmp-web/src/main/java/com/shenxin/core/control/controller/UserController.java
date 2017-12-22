package com.shenxin.core.control.controller;


import com.github.pagehelper.PageHelper;
import com.shenxin.core.control.bo.UserBO;
import com.shenxin.core.control.constant.ShiroPermissionsConstant;
import com.shenxin.core.control.constant.SystemConstant;
import com.shenxin.core.control.dto.UserDTO;
import com.shenxin.core.control.entity.SysUserDAO;
import com.shenxin.core.control.vo.Pagination;
import com.shenxin.core.control.service.IUserService;
import com.shenxin.core.control.shiro.MonitorRealm;
import com.shenxin.core.control.springUtil.DateEditor;
import com.shenxin.core.control.util.BeanConvert;
import com.shenxin.core.control.util.BeanUtils;
import com.shenxin.core.control.util.ReturnUtil;
import com.shenxin.core.control.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    IUserService userService;

    @Autowired
    private Validator validator;

    @Value("${app.profile}")
    private String profiles;

    @RequestMapping("page")
    @RequiresPermissions(ShiroPermissionsConstant.USER_QUERY)
    public String doPage(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        UserBO form = new UserBO();

        int pageCurrent = Integer.parseInt(form.getPageCurrent());
        int pageSize = Integer.parseInt(form.getPageSize());
        int size = userService.count(form);
        Pagination<UserVO> page = new Pagination<>(size, pageCurrent, pageSize);
        PageHelper.startPage(pageCurrent, pageSize);
        List<SysUserDAO> userList = userService.search(form);
        List<UserVO> result = BeanUtils.copyList(userList, UserVO.class);
        page.addResult(result);
        request.setAttribute("pageUser", page);

        request.setAttribute("rout", form);
        return "user/listPage";
    }

    @RequestMapping("search")
    @RequiresPermissions(ShiroPermissionsConstant.USER_QUERY)
    public String doSelect(@ModelAttribute("rout") UserBO form, HttpSession session, HttpServletRequest request,
                           HttpServletResponse response) {

        int pageCurrent = Integer.parseInt(form.getPageCurrent());
        int pageSize = Integer.parseInt(form.getPageSize());
        int size = userService.count(form);
        Pagination<UserVO> page = new Pagination<>(size, pageCurrent, pageSize);
        PageHelper.startPage(pageCurrent, pageSize);
        List<SysUserDAO> userList = userService.search(form);
        List<UserVO> result = BeanUtils.copyList(userList, UserVO.class);
        page.addResult(result);

        if ("dev".equals(profiles)) {
            request.setAttribute("pageUser", page);
            return "user/listPage";
        } else {
            ReturnUtil.retJson(request, response, BeanUtils.object2Json(page));
            return null;
        }
    }


    @RequestMapping("addpage")
    @RequiresPermissions(ShiroPermissionsConstant.USER_ADD)
    public String doAddUserPage(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        SysUserDAO dao = new SysUserDAO();
        request.setAttribute("rout", dao);
        return "user/addPage";
    }


    @RequestMapping("add")
    @RequiresPermissions(ShiroPermissionsConstant.USER_ADD)
    public String doAdd(@ModelAttribute("rout") UserDTO dto, BindingResult result, HttpSession session, HttpServletRequest request,
                        HttpServletResponse response) throws Exception {
        BeanUtils.validateJSR303(validator, dto);

        SysUserDAO dao = new SysUserDAO();
        BeanConvert.copy(dto, dao);
        userService.insert(dao);
        String succeed = ReturnUtil.succeed();
        ReturnUtil.retJson(response, succeed);
        return null;
    }

    @RequestMapping("delete/{id:.*}")
    @RequiresPermissions(ShiroPermissionsConstant.USER_DEL)
    public String deleteUser(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        userService.delete(id);
        String message = ReturnUtil.succeedDel();
        ReturnUtil.retJson(response, message);
        return null;
    }

    @RequestMapping("updatepage/{id:.*}")
    @RequiresPermissions(ShiroPermissionsConstant.USER_UP)
    public String doUpdateUserPage(@PathVariable String id, HttpSession session, HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        SysUserDAO dao = (SysUserDAO) userService.searchById(id);
        UserVO vo = new UserVO();
        BeanUtils.copy(dao, vo);
        request.setAttribute("rout", vo);
        return "user/updatePage";
    }

    @RequestMapping("update")
    @RequiresPermissions(ShiroPermissionsConstant.USER_UP)
    public String doUpdate(@ModelAttribute("rout") UserDTO dto, HttpSession session, HttpServletRequest request,
                           HttpServletResponse response) throws Exception {
        BeanUtils.validateJSR303(validator, dto);
        SysUserDAO dao = new SysUserDAO();
        BeanUtils.copy(dto, dao);
        userService.update(dao);
        String succeed = ReturnUtil.succeed();
        ReturnUtil.retJson(response, succeed);
        return null;
    }

    @RequiresPermissions(ShiroPermissionsConstant.USER_UP)
    @RequestMapping(value = "goToChangepwdPage", method = RequestMethod.GET)
    public String goToChangepwdPage(HttpServletRequest request) {
        Subject currentUser = SecurityUtils.getSubject();
        MonitorRealm.ShiroUser shiroUser = (MonitorRealm.ShiroUser) currentUser.getPrincipal();
        request.setAttribute("user", shiroUser.getUser());
        return "user/changepwd";
    }

    @RequiresPermissions(ShiroPermissionsConstant.USER_UP)
    @RequestMapping(value = "changepwd", method = RequestMethod.POST)
    public Map changepwd(HttpServletRequest request, HttpServletResponse response) {
        String succeed = ReturnUtil.succeed();
        try {
            String userId = request.getParameter("userid");
            String oldpassword = request.getParameter("oldpassword");
            String pass = request.getParameter("pass");
            SysUserDAO userDAO = (SysUserDAO) userService.searchById(userId);
            if (SystemConstant.ROOT.equals(userDAO.getName()) || oldpassword.equals(userDAO.getPwd())) {
                userDAO.setPwd(pass);
                userService.update(userDAO);
            } else {
                succeed = ReturnUtil.error("旧密码错误");
            }
        } catch (Exception e) {
            succeed = ReturnUtil.error("重置密码错误");
            log.error("重置密码错误", e);
        }
        ReturnUtil.retJson(response, succeed);
        return null;

    }

    @InitBinder
    protected void initBinder(HttpServletRequest request,
                              ServletRequestDataBinder binder) throws Exception {
        String dateFormat = "yyyy-MM-dd";
        binder.registerCustomEditor(Date.class, new DateEditor(dateFormat));
    }

    /**
     * @Author: gaobaozong
     * @Description: 获取所有用户角色
     * @Date: Created in 2017/12/19 - 14:15
     * @param:
     * @return:
     */
    @RequestMapping("authpage/{id:.*}")
    @RequiresPermissions(ShiroPermissionsConstant.USER_AUTHORITY)
    public String doUpAuthPage(@PathVariable String id, HttpSession session, HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
        UserVO vo = new UserVO();
        SysUserDAO dao = (SysUserDAO) userService.searchById(id);
        BeanUtils.copy(dao, vo);
        List func = userService.getRoleByUserId(id);
        request.setAttribute("user", vo);
        request.setAttribute("roles", func);
        return "user/modifyacctoauth";
    }


    /**
     * @Author: gaobaozong
     * @Description: 用户角色关联
     * @Date: Created in 2017/12/19 - 14:12
     * @param:
     * @return:
     */
    @RequestMapping("auth/{uid:.*}/{rid:.*}/{type:.*}")
    @RequiresPermissions(ShiroPermissionsConstant.USER_AUTHORITY)
    public String doUpAuth(@PathVariable String uid, @PathVariable String rid, @PathVariable String type, HttpSession session, HttpServletRequest request,
                           HttpServletResponse response) throws Exception {
        String succeed = ReturnUtil.succeed();
        try {
            if ("op".equalsIgnoreCase(type)) {
                userService.upRoleOnUser(uid, rid, false);
            } else if ("cl".equalsIgnoreCase(type)) {
                userService.upRoleOnUser(uid, rid, true);
            }
        } catch (Exception e) {
            succeed = ReturnUtil.error("操作失败");
        }
        ReturnUtil.retJson(response, succeed);
        return null;
    }
}
