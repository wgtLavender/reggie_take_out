package com.queen.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.queen.common.R;
import com.queen.common.util.BaseContext;
import com.queen.domain.Employee;
import com.queen.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * 员工信息 前端控制器
 * </p>
 *
 * @author wgt
 * @since 2022-11-26
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {


    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest httpServletRequest, @RequestBody Employee employee) {
        //1. 密码加密  2 状态是否正常
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee one = employeeService.getOne(lambdaQueryWrapper);
        //2 员工是否存在
        if (null == one) {
            return R.error("非法用户!");
        }
// 密码校验
        if (!one.getPassword().equals(password)) {
            return R.error("非法用户!");
        }
        // 用户状态校验
        if (one.getStatus() == 0) {
            return R.error("用户被禁用!");
        }
        //3 存放session
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("employee", one.getId());
        BaseContext.setThreadLocal(one.getId());
        return R.success(one);
    }

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest httpServletRequest) {

        Object employee = httpServletRequest.getSession().getAttribute("employee");
        System.out.println(employee.toString());
        httpServletRequest.getSession().removeAttribute("employee");
        return R.success("退出成功！");
    }

    /**
     * @param employee
     * @param request
     * @return com.queen.common.R<java.lang.String>
     * @description 添加员工
     * @author
     * @date 2022/11/27 15:25
     */
    @PostMapping
    public R<String> add(@RequestBody Employee employee, HttpServletRequest request) {

        //取创建人
        HttpSession session = request.getSession();
//        long employee1 = (long) request.getSession().getAttribute("employee");
        long employee1 = (long) request.getSession().getAttribute("employee");
        BaseContext.setThreadLocal(employee1);
        String password = DigestUtils.md5DigestAsHex("123456".getBytes());
        employee.setPassword(password);
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setCreateUser(employee1);
//        employee.setUpdateUser(employee1);

        boolean save = employeeService.save(employee);

        return save ? R.success("添加成功") : R.error("添加失败");

    }

    @GetMapping("/page")
    public R page(@RequestParam("page") int page,@RequestParam("pageSize") int pageSize,String name) {
        Page<Employee> pages = new Page<>(page, pageSize);

        System.out.println(name);
//        QueryWrapper<Employee> wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null,Employee::getName,name);
        queryWrapper.orderByDesc(Employee::getName);
        Page<Employee> employeePage = employeeService.page(pages, queryWrapper);


        return R.success(employeePage);
    }
    @PutMapping
    public R updateStaus(@RequestBody Employee employee,HttpServletRequest request) {

        Long employee1 = (Long) request.getSession().getAttribute("employee");
        BaseContext.setThreadLocal(employee1);
        boolean b = employeeService.updateById(employee);
        return b?R.success("更新成功"):R.error("更新失败");

    }
}

