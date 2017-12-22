package com.shenxin.core.control.controller;


import com.github.pagehelper.PageHelper;
import com.shenxin.core.control.bo.FunctionBO;
import com.shenxin.core.control.constant.ShiroPermissionsConstant;
import com.shenxin.core.control.dto.FunctionDTO;
import com.shenxin.core.control.entity.SysFunctionDAO;
import com.shenxin.core.control.vo.Pagination;
import com.shenxin.core.control.service.IFunctionService;
import com.shenxin.core.control.springUtil.DateEditor;
import com.shenxin.core.control.util.BeanConvert;
import com.shenxin.core.control.util.BeanUtils;
import com.shenxin.core.control.util.ReturnUtil;
import com.shenxin.core.control.vo.FunctionVO;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Date;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("fun")
public class FunctionController {

    @Autowired
    IFunctionService funcService;

    @Autowired
    private Validator validator;

    @Value("${app.profile}")
    private String profiles;

    @RequestMapping("page")
    @RequiresPermissions(ShiroPermissionsConstant.FUNC_QUERY)
    public String doPage(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        FunctionBO form = new FunctionBO();

        int pageCurrent = Integer.parseInt(form.getPageCurrent());
        int pageSize = Integer.parseInt(form.getPageSize());
        int size = funcService.count(form);
        Pagination<FunctionVO> page = new Pagination<>(size, pageCurrent, pageSize);
        PageHelper.startPage(pageCurrent, pageSize);
        List<SysFunctionDAO> userList = funcService.search(form);
        List<FunctionVO> result = BeanUtils.copyList(userList, FunctionVO.class);
        page.addResult(result);
        request.setAttribute("pageUser", page);

        request.setAttribute("rout", form);
        return "fun/listPage";
    }

    @RequestMapping("search")
    @RequiresPermissions(ShiroPermissionsConstant.FUNC_QUERY)
    public String doSelect(@ModelAttribute("rout") FunctionBO form, HttpSession session, HttpServletRequest request,
                           HttpServletResponse response) {

        int pageCurrent = Integer.parseInt(form.getPageCurrent());
        int pageSize = Integer.parseInt(form.getPageSize());
        int size = funcService.count(form);
        Pagination<FunctionVO> page = new Pagination<>(size, pageCurrent, pageSize);
        PageHelper.startPage(pageCurrent, pageSize);
        List<SysFunctionDAO> userList = funcService.search(form);
        List<FunctionVO> result = BeanUtils.copyList(userList, FunctionVO.class);
        page.addResult(result);

        if("dev".equals(profiles)){
            request.setAttribute("pageUser", page);
            return "fun/listPage";
        }else{
            ReturnUtil.retJson(request , response, BeanUtils.object2Json(page));
            return null;
        }
    }


    @RequestMapping("addpage")
    @RequiresPermissions(ShiroPermissionsConstant.FUNC_ADD)
    public String doAddUserPage(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        SysFunctionDAO dao = new SysFunctionDAO();
        request.setAttribute("rout", dao);
        return "fun/addPage";
    }


    @RequestMapping("add")
    @RequiresPermissions(ShiroPermissionsConstant.FUNC_ADD)
    public String doAdd(@ModelAttribute("rout") FunctionDTO dto,BindingResult result, HttpSession session, HttpServletRequest request,
                        HttpServletResponse response) throws Exception {
        BeanUtils.validateJSR303(validator, dto);

        SysFunctionDAO dao = new SysFunctionDAO();
        BeanConvert.copy(dto, dao);
        funcService.insert(dao);
        String succeed = ReturnUtil.succeed();
        ReturnUtil.retJson(response, succeed);
        return null;
    }

    @RequestMapping("delete/{id:.*}")
    @RequiresPermissions(ShiroPermissionsConstant.FUNC_DEL)
    public String deleteUser(@PathVariable String id, HttpServletRequest request, HttpServletResponse response)throws Exception{
        funcService.delete(id);
        String message = ReturnUtil.succeedDel();
        ReturnUtil.retJson(response, message);
        return null;
    }

    @RequestMapping("updatepage/{id:.*}")
    @RequiresPermissions(ShiroPermissionsConstant.FUNC_UP)
    public String doUpdateUserPage(@PathVariable String  id, HttpSession session, HttpServletRequest request,
                                   HttpServletResponse response)throws Exception {
        SysFunctionDAO dao = (SysFunctionDAO)funcService.searchById(id);
        FunctionVO vo = new FunctionVO();
        BeanUtils.copy(dao, vo);
        request.setAttribute("rout", vo);
        return "fun/updatePage";
    }

    @RequestMapping("update")
    @RequiresPermissions(ShiroPermissionsConstant.FUNC_UP)
    public String doUpdate(@ModelAttribute("rout") FunctionDTO dto, HttpSession session, HttpServletRequest request,
                           HttpServletResponse response)throws Exception{
        BeanUtils.validateJSR303(validator, dto);
        SysFunctionDAO dao = new SysFunctionDAO();
        BeanUtils.copy(dto,dao);
        funcService.update(dao);
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
}
