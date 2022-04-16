package com.cy.store.controller;

import com.cy.store.controller.ex.*;
import com.cy.store.entity.User;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.InsertException;
import com.cy.store.service.ex.UsernameDuplicatedException;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//@Controller 控制层
@RestController //@Controller + @ResponseBody
@RequestMapping("users")
public class UserController extends BaseController {
    @Autowired
    private IUserService userService;

    /**
     * 1、接收数据方式：请求处理方法的参数列表设置为pojo类型来接收前端的数据，
     * SpringBoot会将前端的url地址中的参数名和pojo类的属性名进行比较,如果
     * 这两个名称相同，则将值注入到pojo类中对应的属性上
     * */


    @RequestMapping("reg")
    public JsonResult<Void> reg(User user) {
        userService.reg(user);
        return new JsonResult<Void>(OK);
    }

    /**
     * 约定大于配置：开发思想来完成，省略大量的配置甚至注解的编写
     * 2、接收数据方式：请求处理方法的参数列表设置为非pojo类型
     * SpringBoot会直接将请求的参数名和方法的参数名进行比较
     * 如果名称相同则自动完成值得依赖注入
     * */

    @RequestMapping("login")
    public JsonResult<User> login(String username, String password, HttpSession session) {
        User data = userService.login(username, password);
        //向@session对象中完成数据的绑定（session全局的）
        session.setAttribute("uid", data.getUid());
        session.setAttribute("username", data.getUsername());

        //获取session中绑定的数据
        //System.out.println(getUidFromSession(session));
        //System.out.println(getUsernameFromSession(session));

        return new JsonResult<User>(OK, data);
    }
    @RequestMapping("change_password")
    public JsonResult<Void> changePassword(String oldPassword,
                                           String newPassword,
                                           HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changePassword(uid, username, oldPassword, newPassword);
        return new JsonResult<>(OK);
    }
    @RequestMapping("get_by_uid")
    public JsonResult<User> getByUid(HttpSession session) {
        User data = userService.getByUid(getUidFromSession(session));
        return new JsonResult<>(OK, data);
    }
    @RequestMapping("change_info")
    public JsonResult<Void> changeInfo(User user, HttpSession session) {
        //user对象有四部分的数据： username、phone、email、gender
        //uid数据需要再次封装到user对象中
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changeInfo(uid, username, user);
        return new JsonResult<>(OK);
    }

    /**
     * MultipartFile接口是springMVC提供的一个接口，接口包装了获取文件类型的数据，SpringBoot整合了SpringMVC,只需
     * 在处理请求的方法参数列表声明一个参数类型为MultipartFile
     * @param session
     * @param file
     * @return
     */
    /**设置上传文件的最大值*/
    public static final int AVATAR_MAX_SIZE = 10 * 1024 * 1024;
    /**限制上传文件的类型*/
    public static final List<String> AVATAR_TYPE = new ArrayList<>();

    static  {
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/bmp");
        AVATAR_TYPE.add("image/gif");
    }
    @RequestMapping("change_avatar")
    public JsonResult<String> changeAvatar(HttpSession session, @RequestParam("file") MultipartFile file) {
        if(file.isEmpty()) {
            throw new FileEmptyException("文件为空");
        }
        if(file.getSize() > AVATAR_MAX_SIZE) {
            throw new FileSizeException("文件超出限制");
        }
        //判断文件的类型是否是规定的后缀类型
        String contentType = file.getContentType();
        if(!AVATAR_TYPE.contains(contentType)) {
            throw new FileTypeException("文件类型不支持");
        }
        //上传的文件.../upload/文件.png
        String parent = session.getServletContext().getRealPath("upload");
        //File对象指向这个路径
        File dir = new File(parent);
        if(!dir.exists()) { //检测目录是否存在
            dir.mkdir(); //创建目录
        }
        //获取文件名称，UUID工具来生成一个新的字符串作为文件名
        String originalFilename = file.getOriginalFilename();
        System.out.println("originalFilename" + originalFilename);
        int index = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(index);
        String filename = UUID.randomUUID().toString().toUpperCase() + suffix;
        File dest = new File(dir, filename); //是一个空文件

        //参数file中数据写入到这个空文件中
        try {
            file.transferTo(dest); //将file文件中数据写入到dest文件
        } catch (FileStateException e) {
            throw new FileStateException("文件状态异常");
        } catch (IOException e) {
            throw new FileUploadException("文件读写异常");
        }
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);

        String avatar = "/upload/" + filename;

        //返回头像的路径/upload
        userService.changeAvatar(uid, avatar, username);
        //返回用户头像的路径给前端页面，将来用于头像展示使用
        return new JsonResult<>(OK, avatar);
    }



//    @RequestMapping("reg")
//    public JsonResult<Void> reg(User user) {
//        //创建响应对象
//        JsonResult<Void> result = new JsonResult<>();
//        try {
//            userService.reg(user);
//            result.setState(200);
//            result.setMessage("用户注册成功");
//        } catch (UsernameDuplicatedException e) {
//            result.setState(4000);
//            result.setMessage("用户名被占用");
//        } catch (InsertException e) {
//            result.setState(5000);
//            result.setMessage("注册时产生未知的异常");
//        }
//        return result;
//    }


}
