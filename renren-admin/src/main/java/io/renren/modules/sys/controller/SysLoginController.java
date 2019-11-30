/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.renren.modules.sys.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.github.qcloudsms.httpclient.HTTPException;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import io.renren.common.config.MessageUtils;
import io.renren.common.utils.*;
import io.renren.modules.sys.entity.ReturnCodeEnum;
import io.renren.modules.sys.entity.ReturnResult;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysUserService;
import io.renren.modules.sys.shiro.ShiroUtils;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 登录相关
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月10日 下午1:15:31
 */
@Controller
public class SysLoginController {
    @Autowired
    private Producer producer;

    @Autowired
    SysUserService sysUserService;

    @RequestMapping("captcha.jpg")
    public void captcha(HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        //保存到shiro session
        ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
    }

    /**
     * 登录
     */
    @ResponseBody
    @RequestMapping(value = "/sys/login", method = RequestMethod.POST)
    public ReturnResult login(@ApiParam @RequestBody SysUserEntity user) {
        Map<String, Object> map = new HashMap<>();
        ReturnResult result = new ReturnResult(ReturnCodeEnum.SUCCESS.getCode(), ReturnCodeEnum.SUCCESS.getMessage());
        try {
            Subject subject = ShiroUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
            SysUserEntity us = sysUserService.queryByMobile(user.getUsername());
            if(us.getEndTime().compareTo(CommonUtil.today())>0) {
                subject.login(token);
            }else{
                result.setCode(ReturnCodeEnum.SYSTEM_ERROR.getCode());
                result.setMsg("用户账户已经到期，请及时续费");
            }
            map.put("user", us);
            result.setResult(map);
        } catch (UnknownAccountException e) {
            result.setCode(ReturnCodeEnum.SYSTEM_ERROR.getCode());
            return result;
        } catch (IncorrectCredentialsException e) {
            result.setCode(ReturnCodeEnum.SYSTEM_ERROR.getCode());
            result.setMsg("账号或密码不正确");
            return result;
        } catch (LockedAccountException e) {
            result.setCode(ReturnCodeEnum.SYSTEM_ERROR.getCode());
            result.setMsg("账号已被锁定,请联系管理员");
            return result;
        } catch (AuthenticationException e) {
            result.setCode(ReturnCodeEnum.SYSTEM_ERROR.getCode());
            result.setMsg("账户验证失败");
            return result;
        }

        return result;
    }

    /**
     * 退出
     */
    @RequestMapping(value = "sys/logout", method = RequestMethod.GET)
    @ResponseBody
    public ReturnResult logout() {
        ReturnResult result = new ReturnResult(ReturnCodeEnum.SUCCESS.getCode(), ReturnCodeEnum.SUCCESS.getMessage());
        ShiroUtils.logout();
        return result;
    }

    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    @Transactional(rollbackFor = {Exception.class}, readOnly = false)
    public ReturnResult doLogin(@ApiParam @RequestBody SysUserEntity user) {
        String passwordmd5 = new Md5Hash("xyj1234567", "2").toString();
        user.setPassword(passwordmd5);
        user.setUsername(user.getUsername());
        Subject subject = SecurityUtils.getSubject();
        Map<String, Object> map = new HashMap<>();
        ReturnResult result = new ReturnResult(ReturnCodeEnum.SUCCESS.getCode(), ReturnCodeEnum.SUCCESS.getMessage());
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), passwordmd5);
        try {
            //map = WxUtil.getSessionKeyOropenid(user.getCode());
            user.setOpenId(map.get("openid").toString());
            String id = UUID.randomUUID().toString().replaceAll("-", "");
            user.setUserId(id);
            //SysUserEntity hasUser = sysUserService.queryByOpenId(map.get("openid").toString());
//            if (hasUser == null) {
//                sysUserService.insert(user);
//                map.put("id", id);
//            } else {
//                map.put("id", hasUser.);
//            }

            subject.login(token);
            result.setResult(map);
            return result;
        } catch (UnknownAccountException e) {
            result.setCode(ReturnCodeEnum.SYSTEM_ERROR.getCode());
            result.setMsg(ReturnCodeEnum.SYSTEM_ERROR.getMessage());
            map.put("status", "账号不存在");
            result.setResult(map);
            return result;
        } catch (IncorrectCredentialsException e) {
            result.setCode(ReturnCodeEnum.SYSTEM_ERROR.getCode());
            result.setMsg(ReturnCodeEnum.SYSTEM_ERROR.getMessage());
            map.put("status", "账号密码错误");
            result.setResult(map);
            return result;
        } catch (AuthenticationException e) {
            result.setCode(ReturnCodeEnum.SYSTEM_ERROR.getCode());
            result.setMsg(ReturnCodeEnum.SYSTEM_ERROR.getMessage());
            map.put("status", "登录异常!请联系管理员!");
            result.setResult(map);
            return result;
        } catch (Exception e) {
            result.setCode(ReturnCodeEnum.SYSTEM_ERROR.getCode());
            result.setMsg(ReturnCodeEnum.SYSTEM_ERROR.getMessage());
            map.put("status", "系统异常!");
            result.setResult(map);
            return result;
        }
    }

    @RequestMapping(value = "/sys/signIn", method = RequestMethod.POST)
    @Transactional(rollbackFor = {Exception.class}, readOnly = false)
    @ResponseBody
    public ReturnResult signIn(@ApiParam @RequestBody SysUserEntity user) {
        Map<String, Object> map = new HashMap<>();
        ReturnResult result = new ReturnResult(ReturnCodeEnum.SUCCESS.getCode(), ReturnCodeEnum.SUCCESS.getMessage());
        Map<String, Object> ret = new HashedMap();
        String tempPass = user.getPassword();
        //验证验证码是否匹配
        String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
        if (!user.getCaptcha().equalsIgnoreCase(kaptcha)) {
            result.setCode(ReturnCodeEnum.SYSTEM_ERROR.getCode());
            result.setMsg("验证码不正确");
            return result;
        }
        System.out.println("-----------------------------收到请求，请求数据为：" + user.getCode() + "-----------------------" + user.getCode());

        //通过code换取网页授权web_access_token
        if (user.getCode() != null || !(user.getCode().equals(""))) {
            String CODE = user.getCode();
            String WebAccessToken = "";
            String openId = "";
            //替换字符串，获得请求URL
            String token = UserInfoUtil.getWebAccess(io.renren.common.config.Constants.PTAPPID, io.renren.common.config.Constants.PSERCRET, CODE);
            System.out.println("----------------------------token为：" + token);
            //通过https方式请求获得web_access_token
            JSONObject jsonObject = WxUtil.httpRequest(token, "GET", null);
            System.out.println("jsonObject------" + jsonObject);
            SysUserEntity userTemp = sysUserService.queryByMobile(user.getMobile());
            if (userTemp == null && null != jsonObject) {
                try {
                    WebAccessToken = jsonObject.getString("access_token");
                    openId = jsonObject.getString("openid");
                    System.out.println("获取access_token成功-------------------------" + WebAccessToken + "----------------" + openId);
                    //-----------------------拉取用户信息...替换字符串，获得请求URL
                    String userMessage = UserInfoUtil.getUserMessage(WebAccessToken, openId);
                    System.out.println(" userMessage===" + userMessage);
                    //通过https方式请求获得用户信息响应
                    JSONObject userMessageJsonObject = WxUtil.httpRequest(userMessage, "GET", null);

                    System.out.println("userMessagejsonObject------" + userMessageJsonObject);

                    if (userMessageJsonObject != null) {
                        try {
                            //用户昵称
                            SysUserEntity utmp = sysUserService.queryByOpenId(userMessageJsonObject.getString("openid"));
                            //获取成果，存入数据库
                            if (utmp == null) {
                                //user.setUsername(userMessageJsonObject.getString("nickname"));
                                user.setNickname(userMessageJsonObject.getString("nickname"));
                                //user.setPassword(new Md5Hash(user.getPassword(), "2").toString());
                                //用户性别
                                user.setUserId(UUID.randomUUID().toString().replaceAll("-", ""));
                                user.setSex(Integer.parseInt(userMessageJsonObject.getString("sex")));
                                user.setStatus(1);
                                user.setProvince(userMessageJsonObject.getString("province"));
                                user.setSubscribetime(userMessageJsonObject.getString("subscribetime"));
                                user.setCity(userMessageJsonObject.getString("city"));
                                user.setHeadimgurl(userMessageJsonObject.getString("headimgurl"));
                                //用户唯一标识
                                user.setOpenId(userMessageJsonObject.getString("openid"));
                                user.setUnionid(userMessageJsonObject.getString("unionid"));
                                user.setEndTime(DateUtils.nextDay());
                                sysUserService.insertUser(user);
                            } else {
                                user = utmp;
                            }

                        } catch (JSONException e) {
                            System.out.println("获取user失败");
                        }
                    }
                } catch (JSONException e) {
                    System.out.println("获取WebAccessToken失败");
                }
            }
            //登陆
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken loginToken = new UsernamePasswordToken(user.getMobile(),tempPass);
            subject.login(loginToken);
        }
        ret.put("user", user);
        map.put("data", ret);
        map.put("status", "success");
        map.put("msg", "send ok");
        result.setResult(map);
        return result;
    }

    @RequestMapping(value = "/sys/changePassword", method = RequestMethod.POST)
    @Transactional(rollbackFor = {Exception.class}, readOnly = false)
    @ResponseBody
    public ReturnResult changePassword(@ApiParam @RequestBody SysUserEntity user) {
        Map<String, Object> map = new HashMap<>();
        ReturnResult result = new ReturnResult(ReturnCodeEnum.SUCCESS.getCode(), ReturnCodeEnum.SUCCESS.getMessage());
        Map<String, Object> ret = new HashedMap();
        //验证验证码是否匹配
        String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
        if (!user.getCaptcha().equalsIgnoreCase(kaptcha)) {
            result.setCode(ReturnCodeEnum.SYSTEM_ERROR.getCode());
            result.setMsg("验证码不正确");
            return result;
        }
        System.out.println("-----------------------------收到请求，请求数据为：" + user.getCode() + "-----------------------" + user.getCode());

        SysUserEntity userTemp = sysUserService.queryByMobile(user.getMobile());
        if (userTemp != null) {
            userTemp.setPassword(ShiroUtils.sha256(user.getPassword(), user.getSalt()));
            //userTemp.setPassword(new Md5Hash(user.getPassword(), "2").toString());
            sysUserService.updateUser(userTemp);
        } else {
            result.setCode(ReturnCodeEnum.SYSTEM_ERROR.getCode());
            result.setMsg("用户不存在");
        }
        ret.put("user", user);
        map.put("data", ret);
        map.put("status", "success");
        map.put("msg", "send ok");
        result.setResult(map);
        return result;
    }

    @RequestMapping(value = "/sys/sendMessage", method = RequestMethod.POST)
    @ResponseBody
    public ReturnResult sendMessage(@RequestBody SysUserEntity user) throws HTTPException {
        //生成文字验证码
        ReturnResult result = new ReturnResult(ReturnCodeEnum.SUCCESS.getCode(), ReturnCodeEnum.SUCCESS.getMessage());
        Map<String, Object> map = new HashedMap();
        SysUserEntity userTemp = sysUserService.queryByMobile(user.getMobile());
        if(userTemp==null||("pass".equals(user.getSalt()))){
            String text = producer.createText();
            ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);
            String[] param = {text, "2"};
            String[] phoneNumbers = {user.getMobile()};
            MessageUtils.sendMessage(true, param, phoneNumbers);
            map.put("status", "success");
            map.put("msg", "send ok");
            result.setResult(map);

        }else{
            result.setCode(ReturnCodeEnum.SYSTEM_ERROR.getCode());
            result.setMsg("该手机号已被注册");
        }
        return result;
    }
}
