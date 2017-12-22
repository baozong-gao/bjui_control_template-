package com.shenxin.core.control.controller;


import com.github.pagehelper.PageHelper;
import com.shenxin.core.control.bo.SysRoleBO;
import com.shenxin.core.control.constant.ShiroPermissionsConstant;
import com.shenxin.core.control.dto.SysRoleDTO;
import com.shenxin.core.control.entity.SysRoleDAO;
import com.shenxin.core.control.vo.Pagination;
import com.shenxin.core.control.service.IRoleService;
import com.shenxin.core.control.springUtil.DateEditor;
import com.shenxin.core.control.util.BeanConvert;
import com.shenxin.core.control.util.BeanUtils;
import com.shenxin.core.control.util.ReturnUtil;
import com.shenxin.core.control.vo.SysRoleVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("role")
public class RoleController {

    @Autowired
    IRoleService service;

    @Autowired
    private Validator validator;

    @Value("${app.profile}")
    private String profiles;

    @RequestMapping("page")
    @RequiresPermissions(ShiroPermissionsConstant.ROLE_QUERY)
    public String doPage(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        SysRoleBO form = new SysRoleBO();

        int pageCurrent = Integer.parseInt(form.getPageCurrent());
        int pageSize = Integer.parseInt(form.getPageSize());
        int size = service.count(form);
        Pagination<SysRoleVO> page = new Pagination<>(size, pageCurrent, pageSize);
        PageHelper.startPage(pageCurrent, pageSize);
        List<SysRoleDAO> userList = service.search(form);
        List<SysRoleVO> result = BeanUtils.copyList(userList, SysRoleVO.class);
        page.addResult(result);
        request.setAttribute("pageUser", page);

        request.setAttribute("rout", form);
        return "role/listPage";
    }

    @RequestMapping("search")
    @RequiresPermissions(ShiroPermissionsConstant.ROLE_QUERY)
    public String doSelect(@ModelAttribute("rout") SysRoleBO form, HttpSession session, HttpServletRequest request,
                           HttpServletResponse response) {

        int pageCurrent = Integer.parseInt(form.getPageCurrent());
        int pageSize = Integer.parseInt(form.getPageSize());
        int size = service.count(form);
        Pagination<SysRoleVO> page = new Pagination<>(size, pageCurrent, pageSize);
        PageHelper.startPage(pageCurrent, pageSize);
        List<SysRoleDAO> userList = service.search(form);
        List<SysRoleVO> result = BeanUtils.copyList(userList, SysRoleVO.class);
        page.addResult(result);

        if("dev".equals(profiles)){
            request.setAttribute("pageUser", page);
            return "role/listPage";
        }else{
            ReturnUtil.retJson(request , response, BeanUtils.object2Json(page));
            return null;
        }
    }


    @RequestMapping("addpage")
    @RequiresPermissions(ShiroPermissionsConstant.ROLE_ADD)
    public String doAddUserPage(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        SysRoleDAO dao = new SysRoleDAO();
        request.setAttribute("rout", dao);
        return "role/addPage";
    }


    @RequestMapping("add")
    @RequiresPermissions(ShiroPermissionsConstant.ROLE_ADD)
    public String doAdd(@ModelAttribute("rout") SysRoleDTO dto,BindingResult result, HttpSession session, HttpServletRequest request,
                        HttpServletResponse response) throws Exception {
        BeanUtils.validateJSR303(validator, dto);

        SysRoleDAO dao = new SysRoleDAO();
        BeanConvert.copy(dto, dao);
        service.insert(dao);
        String succeed = ReturnUtil.succeed();
        ReturnUtil.retJson(response, succeed);
        return null;
    }

    @RequestMapping("delete/{id:.*}")
    @RequiresPermissions(ShiroPermissionsConstant.ROLE_DEL)
    public String deleteUser(@PathVariable String id, HttpServletRequest request, HttpServletResponse response)throws Exception{
        service.delete(id);
        String message = ReturnUtil.succeedDel();
        ReturnUtil.retJson(response, message);
        return null;
    }

    @RequestMapping("updatepage/{id:.*}")
    @RequiresPermissions(ShiroPermissionsConstant.ROLE_UP)
    public String doUpdateUserPage(@PathVariable String  id, HttpSession session, HttpServletRequest request,
                                   HttpServletResponse response)throws Exception {
        SysRoleDAO dao = (SysRoleDAO) service.searchById(id);
        SysRoleVO vo = new SysRoleVO();
        BeanUtils.copy(dao, vo);
        request.setAttribute("rout", vo);
        return "role/updatePage";
    }

    @RequestMapping("update")
    @RequiresPermissions(ShiroPermissionsConstant.ROLE_UP)
    public String doUpdate(@ModelAttribute("rout") SysRoleDTO dto, HttpSession session, HttpServletRequest request,
                           HttpServletResponse response)throws Exception{
        BeanUtils.validateJSR303(validator, dto);
        SysRoleDAO dao = new SysRoleDAO();
        BeanUtils.copy(dto,dao);
        service.update(dao);
        String succeed = ReturnUtil.succeed();
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
      *  @Author: gaobaozong
      *  @Description:  获取所有角色权限
      *  @Date: Created in 2017/12/19 - 14:15
      *  @param:
      *  @return:
      */
    @RequestMapping("authpage/{id:.*}")
    @RequiresPermissions(ShiroPermissionsConstant.ROLE_AUTHORITY)
    public String doUpAuthPage(@PathVariable String  id, HttpSession session, HttpServletRequest request,
                                   HttpServletResponse response)throws Exception {
        SysRoleVO vo = new SysRoleVO();
        SysRoleDAO dao =(SysRoleDAO) service.searchById(id);
        BeanUtils.copy(dao,vo);
        List func = service.getFunctionByRole(id);
        request.setAttribute("roleFuncForm", vo);
        request.setAttribute("role_authorized_all", BeanUtils.object2Json(func));
        return "role/modifyroleresource";
    }


    /**
      *  @Author: gaobaozong
      *  @Description:  权限角色关联
      *  @Date: Created in 2017/12/19 - 14:12
      *  @param:
      *  @return:
      */
    @RequestMapping("authorization")
    @RequiresPermissions(ShiroPermissionsConstant.ROLE_AUTHORITY)
    public String doUpAuth(String roleId, String functionIds, HttpSession session, HttpServletRequest request,
                           HttpServletResponse response)throws Exception{
        String succeed = ReturnUtil.succeed();
        try {
            service.upFunctionOnRole(roleId, Arrays.asList(StringUtils.splitByWholeSeparator(functionIds, ",")));
        }catch (Exception e){
            succeed = ReturnUtil.error("操作失败");
        }
        ReturnUtil.retJson(response, succeed);
        return null;
    }


}
