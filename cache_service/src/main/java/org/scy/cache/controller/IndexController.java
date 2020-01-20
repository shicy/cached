package org.scy.cache.controller;

import org.scy.common.web.controller.BaseController;
import org.scy.common.web.controller.HttpResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by shicy on 2020/01/20.
 */
@Controller
@SuppressWarnings("unused")
public class IndexController extends BaseController {

    @RequestMapping(value = "/version", method = RequestMethod.GET)
    @ResponseBody
    public Object index() {
        return HttpResult.ok(getAppVersion());
    }

}
