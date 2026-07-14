package com.ticket.controller.admin;

import com.ticket.dto.AdminLoginDTO;
import com.ticket.result.Result;
import com.ticket.service.AdminService;
import com.ticket.vo.AdminLoginVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    /**
     * 管理员登录。
     */
    @PostMapping("/login")
    public Result<AdminLoginVO> login(@RequestBody AdminLoginDTO adminLoginDTO) {
        return Result.success(adminService.login(adminLoginDTO));
    }
}