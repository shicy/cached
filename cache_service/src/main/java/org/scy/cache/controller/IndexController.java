package org.scy.cache.controller;

import org.apache.commons.lang3.StringUtils;
import org.scy.cache.manager.CacheManager;
import org.scy.cache.manager.CacheManagerFactory;
import org.scy.cache.model.CacheModel;
import org.scy.cache.model.CacheModel4Admin;
import org.scy.common.ds.PageInfo;
import org.scy.common.utils.StringUtilsEx;
import org.scy.common.web.controller.BaseController;
import org.scy.common.web.controller.HttpResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by shicy on 2020/01/20.
 */
@Controller
@SuppressWarnings("unused")
public class IndexController extends BaseController {

    private final CacheManager manager = CacheManagerFactory.getInstance();

    // 当前端管理员登录的 Token
    private static String adminToken = "";
    private static long adminTokenTime = 0;

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index() {
        return "web/index.html";
    }

    /**
     * 管理员登录
     * @param name 管理员名称
     * @param password 登录密码
     */
    @RequestMapping(value = "/login/by/admin", method = RequestMethod.POST)
    @ResponseBody
    public Object loginByAdmin(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("name") String name, @RequestParam("password") String password) {
        if (!"shicy".equals(name))
            return HttpResult.error(1001, "账号错误！");
        if (!"admin123".equals(password))
            return HttpResult.error(1002, "密码错误！");
        adminToken = StringUtilsEx.getRandomString(64);
        adminTokenTime = new Date().getTime();
        return HttpResult.ok(adminToken);
    }

    /**
     * 管理员获取缓存信息
     * @param token 管理员Token
     */
    @RequestMapping(value = "/find/by/admin", method = RequestMethod.GET)
    @ResponseBody
    public Object findByAdmin(HttpServletRequest request,
            @RequestParam(value = "token", required = false) String token,
            @RequestParam(value = "keyLike", required = false) String keyLike) {
        Object error = checkAdminToken(token);
        if (error != null)
            return error;

        keyLike = StringUtils.trimToEmpty(keyLike);

        PageInfo pageInfo = PageInfo.create(request);
        pageInfo.setTotal(manager.getTotal(keyLike));

        List<CacheModel4Admin> modelList = new ArrayList<CacheModel4Admin>();
        for (CacheModel model: manager.list(keyLike, pageInfo)) {
            modelList.add(new CacheModel4Admin(model));
        }

        pageInfo.setTotal(103);
        for (int i = pageInfo.getPageStart(); i < pageInfo.getPageEnd(); i++) {
            CacheModel model = new CacheModel();
            model.setKey("key_" + keyLike + "_" + i);
            model.setValue("Value-" + i);
            model.setExpires(1000);
            model.setCreatetime(new Date().getTime());
            modelList.add(new CacheModel4Admin(model));
        }

        return HttpResult.ok(modelList, pageInfo);
    }

    @RequestMapping(value = "/version", method = RequestMethod.GET)
    @ResponseBody
    public Object version() {
        return HttpResult.ok(getAppVersion());
    }

    private Object checkAdminToken(String token) {
        if (this.isDev() && "112233445566".equals(token))
            return null;
        if (StringUtils.isBlank(token) || StringUtils.isBlank(adminToken))
            return HttpResult.error(401, "请登录");
        if (!adminToken.equals(token))
            return HttpResult.error(401, "会话已过期，请重新登录");
        long time = new Date().getTime();
        if (time - adminTokenTime > 10 * 60 * 1000)
            return HttpResult.error(401, "会话已过期，请重新登录");
        adminTokenTime = time;
        return null;
    }

}
