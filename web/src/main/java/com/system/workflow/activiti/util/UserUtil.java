package com.system.workflow.activiti.util;

import javax.servlet.http.HttpSession;

import org.activiti.engine.identity.User;

import com.system.util.common.Consts;

/**
 * 用户工具类
 *
 */
public class UserUtil {

    public static final String USER = Consts.userkey;

    /**
     * 设置用户到session
     *
     * @param session
     * @param user
     */
    public static void saveUserToSession(HttpSession session, User user) {
        session.setAttribute(USER, user);
    }

    /**
     * 从Session获取当前用户信息
     *
     * @param session
     * @return
     */
    public static User getUserFromSession(HttpSession session) {
        Object attribute = session.getAttribute(USER);
        return attribute == null ? null : (User) attribute;
    }

}
