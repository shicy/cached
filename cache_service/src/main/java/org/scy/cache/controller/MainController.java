package org.scy.cache.controller;

import org.apache.commons.lang3.StringUtils;
import org.scy.cache.manager.CacheManager;
import org.scy.cache.manager.CacheManagerFactory;
import org.scy.cache.model.CacheModel;
import org.scy.common.utils.HttpUtilsEx;
import org.scy.common.web.controller.BaseController;
import org.scy.common.web.controller.HttpResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 存储控制器
 * Created by shicy on 2017/9/10.
 */
@Controller
@SuppressWarnings("all")
public class MainController extends BaseController {

    private final static String STORED = "STORED";
    private final static String NOSTORED = "NOT_STORED";
    private final static String ERROR = "ERROR";

    // 管理类
    private CacheManager manager = CacheManagerFactory.getInstance();

    /**
     * 设置（添加或替换）
     * @return 设置成功返回 STORED，否则返回 ERROR
     */
    @RequestMapping(value = "/set", method = RequestMethod.POST)
    @ResponseBody
    public Object set(@RequestBody CacheModel model) {
        int result = manager.set(model);
        return HttpResult.ok(result > 0 ? STORED : ERROR);
    }

    /**
     * 批量设置（添加或替换）
     * @return 返回数组，每个对象的 STORED 或 ERROR
     */
    @RequestMapping(value = "/setbatch", method = RequestMethod.POST)
    @ResponseBody
    public Object set(@RequestBody CacheModel[] models) {
        if (models != null && models.length > 0) {
            String[] results = new String[models.length];
            for (int i = 0; i < models.length; i++) {
                int result = manager.set(models[i]);
                results[i] = result > 0 ? STORED : ERROR;
            }
            return HttpResult.ok(results);
        }
        return HttpResult.ok();
    }

    /**
     * 设置
     * @return 设置成功返回 STORED，否则返回 ERROR
     */
    @RequestMapping(value = "/set/{key}/{value}", method = RequestMethod.POST)
    @ResponseBody
    public Object set(@PathVariable("key") String key, @PathVariable("value") String value) {
        return set(key, value, 0, 0);
    }

    /**
     * 设置
     * @return 设置成功返回 STORED，否则返回 ERROR
     */
    @RequestMapping(value = "/set/{key}/{value}/{expires}", method = RequestMethod.POST)
    @ResponseBody
    public Object set(@PathVariable("key") String key, @PathVariable("value") String value,
                      @PathVariable("expires") int expires) {
        return set(key, value, expires, 0);
    }

    /**
     * 设置
     * @return 设置成功返回 STORED，否则返回 ERROR
     */
    @RequestMapping(value = "/set/{key}/{value}/{expires}/{flags}", method = RequestMethod.POST)
    @ResponseBody
    public Object set(@PathVariable("key") String key, @PathVariable("value") String value,
                      @PathVariable("expires") int expires, @PathVariable("flags") int flags) {
        if (StringUtils.isBlank(key))
            return HttpResult.error(ERROR);
        int result = manager.set(key, value, expires, flags);
        return HttpResult.ok(result > 0 ? STORED : ERROR);
    }

    /**
     * 批量获取缓存对象，使用“key”参数名，如：/get?key=aa&key=bb&key=cc
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public Object get(HttpServletRequest request) {
        String[] keys = HttpUtilsEx.getStringValues(request, "key");
        List<CacheModel> models = new ArrayList<CacheModel>();
        if (keys != null && keys.length > 0) {
            for (String key: keys) {
                CacheModel model = manager.get(key);
                if (model != null)
                    models.add(model);
            }
        }
        return HttpResult.ok(models);
    }

    /**
     * 获取缓存对象
     */
    @RequestMapping(value = "/get/{key}", method = RequestMethod.GET)
    @ResponseBody
    public Object get(@PathVariable("key") String key) {
        return HttpResult.ok(manager.get(key));
    }

    /**
     * 获取缓存对象
     * @param keys 逗号分隔
     */
    @RequestMapping(value = "/gets/{keys}", method = RequestMethod.GET)
    @ResponseBody
    public Object gets(@PathVariable("keys") String keys) {
        List<CacheModel> models = new ArrayList<CacheModel>();
        String[] _keys = StringUtils.split(keys, ",");
        if (_keys != null && _keys.length > 0) {
            for (String key: _keys) {
                CacheModel model = manager.get(key);
                if (model != null)
                    models.add(model);
            }
        }
        return HttpResult.ok(models);
    }

    /**
     * 获取缓存对象
     */
    @RequestMapping(value = "/get/like/{key}", method = RequestMethod.GET)
    @ResponseBody
    public Object getLike(@PathVariable("key") String key) {
        return HttpResult.ok(manager.getLike(key));
    }

    /**
     * 获取已某个子串开头的缓存对象
     */
    @RequestMapping(value = "/get/start/{key}", method = RequestMethod.GET)
    @ResponseBody
    public Object getStart(@PathVariable("key") String key) {
        return HttpResult.ok(manager.getLikeStart(key));
    }

    /**
     * 获取已某个子串结尾的缓存对象
     */
    @RequestMapping(value = "/get/end/{key}", method = RequestMethod.GET)
    @ResponseBody
    public Object getEnd(@PathVariable("key") String key) {
        return HttpResult.ok(manager.getLikeEnd(key));
    }

    /**
     * 批量删除，使用“key”参数名，如：/get?key=aa&key=bb&key=cc
     * @return 返回被删除的缓存对象
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Object delete(HttpServletRequest request) {
        String[] keys = HttpUtilsEx.getStringValues(request, "key");
        List<CacheModel> models = new ArrayList<CacheModel>();
        if (keys != null && keys.length > 0) {
            for (String key: keys) {
                CacheModel model = manager.delete(key);
                if (model != null)
                    models.add(model);
            }
        }
        return HttpResult.ok(models);
    }

    /**
     * 删除缓存对象
     * @return 返回被删除的对象
     */
    @RequestMapping(value = "/delete/{key}", method = RequestMethod.POST)
    @ResponseBody
    public Object delete(@PathVariable("key") String key) {
        return HttpResult.ok(manager.delete(key));
    }

    /**
     * 删除缓存对象
     * @param keys 逗号分隔
     * @return 返回被删除的对象
     */
    @RequestMapping(value = "/deletes/{keys}", method = RequestMethod.POST)
    @ResponseBody
    public Object deletes(@PathVariable("keys") String keys) {
        String[] _keys = StringUtils.split(keys, ",");
        List<CacheModel> models = new ArrayList<CacheModel>();
        if (_keys != null && _keys.length > 0) {
            for (String key: _keys) {
                CacheModel model = manager.delete(key);
                if (model != null)
                    models.add(model);
            }
        }
        return HttpResult.ok(models);
    }

    /**
     * 删除缓存对象
     * @return 返回被删除的对象
     */
    @RequestMapping(value = "/delete/like/{key}", method = RequestMethod.POST)
    @ResponseBody
    public Object deleteLike(@PathVariable("key") String key) {
        return HttpResult.ok(manager.deleteLike(key));
    }

    /**
     * 删除缓存对象
     * @return 返回被删除的对象
     */
    @RequestMapping(value = "/delete/start/{key}", method = RequestMethod.POST)
    @ResponseBody
    public Object deleteStart(@PathVariable("key") String key) {
        return HttpResult.ok(manager.deleteLikeStart(key));
    }

    /**
     * 删除缓存对象
     * @return 返回被删除的对象
     */
    @RequestMapping(value = "/delete/end/{key}", method = RequestMethod.POST)
    @ResponseBody
    public Object deleteEnd(@PathVariable("key") String key) {
        return HttpResult.ok(manager.deleteLikeEnd(key));
    }

    /**
     * 添加
     * @return 添加成功返回 STORED，如果相应的 key 已经存在则返回 NOT_STORED，如果异常返回 ERROR
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Object add(@RequestBody CacheModel model) {
        int result = manager.add(model);
        return HttpResult.ok(result > 0 ? STORED : (result < 0 ? ERROR : NOSTORED));
    }

    /**
     * 批量添加
     * @return 返回数组，每个对象的 STORED、NOT_STORED 或 ERROR
     */
    @RequestMapping(value = "/addbatch", method = RequestMethod.POST)
    @ResponseBody
    public Object add(@RequestBody CacheModel[] models) {
        if (models != null && models.length > 0) {
            String[] results = new String[models.length];
            for (int i = 0; i < models.length; i++) {
                int result = manager.add(models[i]);
                results[i] = result > 0 ? STORED : (result < 0 ? ERROR : NOSTORED);
            }
            return HttpResult.ok(results);
        }
        return HttpResult.ok();
    }

    /**
     * 添加
     * @return 添加成功返回 STORED，如果相应的 key 已经存在则返回 NOT_STORED，如果异常返回 ERROR
     */
    @RequestMapping(value = "/add/{key}/{value}", method = RequestMethod.POST)
    @ResponseBody
    public Object add(@PathVariable("key") String key, @PathVariable("value") String value) {
        return add(key, value, 0, 0);
    }

    /**
     * 添加
     * @return 添加成功返回 STORED，如果相应的 key 已经存在则返回 NOT_STORED，如果异常返回 ERROR
     */
    @RequestMapping(value = "/add/{key}/{value}/{expires}", method = RequestMethod.POST)
    @ResponseBody
    public Object add(@PathVariable("key") String key, @PathVariable("value") String value,
                      @PathVariable("expires") int expires) {
        return add(key, value, expires, 0);
    }

    /**
     * 添加
     * @return 添加成功返回 STORED，如果相应的 key 已经存在则返回 NOT_STORED，如果异常返回 ERROR
     */
    @RequestMapping(value = "/add/{key}/{value}/{expires}/{flags}", method = RequestMethod.POST)
    @ResponseBody
    public Object add(@PathVariable("key") String key, @PathVariable("value") String value,
                      @PathVariable("expires") int expires, @PathVariable("flags") int flags) {
        if (StringUtils.isBlank(key))
            return HttpResult.error(ERROR);
        int result = manager.add(key, value, expires, flags);
        return HttpResult.ok(result > 0 ? STORED : (result < 0 ? ERROR : NOSTORED));
    }

    /**
     * 更新
     * @return 更新成功返回 STORED，否则返回 NOT_STORED
     */
    @RequestMapping(value = "/replace", method = RequestMethod.POST)
    @ResponseBody
    public Object replace(@RequestBody CacheModel model) {
        int result = manager.update(model);
        return HttpResult.ok(result > 0 ? STORED : NOSTORED);
    }

    /**
     * 批量更新
     * @return 返回数组，每个对象的 STORED 或 NOT_STORED
     */
    @RequestMapping(value = "/replacebatch", method = RequestMethod.POST)
    @ResponseBody
    public Object replace(@RequestBody CacheModel[] models) {
        if (models != null && models.length > 0) {
            String[] results = new String[models.length];
            for (int i = 0; i < models.length; i++) {
                int result = manager.update(models[i]);
                results[i] = result > 0 ? STORED : NOSTORED;
            }
        }
        return HttpResult.ok();
    }

    /**
     * 更新
     * @return 更新成功返回 STORED，否则返回 NOT_STORED
     */
    @RequestMapping(value = "/replace/{key}/{value}", method = RequestMethod.POST)
    @ResponseBody
    public Object replace(@PathVariable("key") String key, @PathVariable("value") String value) {
        return replace(key, value, 0, 0);
    }

    /**
     * 更新
     * @return 更新成功返回 STORED，否则返回 NOT_STORED
     */
    @RequestMapping(value = "/replace/{key}/{value}/{expires}", method = RequestMethod.POST)
    @ResponseBody
    public Object replace(@PathVariable("key") String key, @PathVariable("value") String value,
                          @PathVariable("expires") int expires) {
        return replace(key, value, expires, 0);
    }

    /**
     * 更新
     * @return 更新成功返回 STORED，否则返回 NOT_STORED
     */
    @RequestMapping(value = "/replace/{key}/{value}/{expires}/{flags}", method = RequestMethod.POST)
    @ResponseBody
    public Object replace(@PathVariable("key") String key, @PathVariable("value") String value,
                          @PathVariable("expires") int expires, @PathVariable("flags") int flags) {
        if (StringUtils.isBlank(key))
            return HttpResult.error(ERROR);
        int result = manager.update(key, value, expires, flags);
        return HttpResult.ok(result > 0 ? STORED : NOSTORED);
    }

    /**
     * 追加后面
     * @return 追加成功返回 STORED，否则返回 NOT_STORED
     */
    @RequestMapping(value = "/append", method = RequestMethod.POST)
    @ResponseBody
    public Object append(@RequestBody CacheModel model) {
        if (StringUtils.isNotBlank(model.getKey()) && model.getValue() != null) {
            CacheModel cacheModel = manager.get(model.getKey());
            if (cacheModel != null) {
                Object value = cacheModel.getValue();
                value = (value == null) ? model.getValue() : ("" + value + model.getValue());
                manager.update(model.getKey(), value, model.getExpires(), model.getFlags());
                return HttpResult.ok(STORED);
            }
        }
        return HttpResult.ok(NOSTORED);
    }

    /**
     * 追加后面
     * @return 追加成功返回 STORED，否则返回 NOT_STORED
     */
    @RequestMapping(value = "/append/{key}/{value}", method = RequestMethod.POST)
    @ResponseBody
    public Object append(@PathVariable("key") String key, @PathVariable("value") String value) {
        return append(key, value, 0, 0);
    }

    /**
     * 追加后面
     * @return 追加成功返回 STORED，否则返回 NOT_STORED
     */
    @RequestMapping(value = "/append/{key}/{value}/{expires}", method = RequestMethod.POST)
    @ResponseBody
    public Object append(@PathVariable("key") String key, @PathVariable("value") String value,
                         @PathVariable("expires") int expires) {
        return append(key, value, expires, 0);
    }

    /**
     * 追加后面
     * @return 追加成功返回 STORED，否则返回 NOT_STORED
     */
    @RequestMapping(value = "/append/{key}/{value}/{expires}/{flags}", method = RequestMethod.POST)
    @ResponseBody
    public Object append(@PathVariable("key") String key, @PathVariable("value") String value,
                         @PathVariable("expires") int expires, @PathVariable("flags") int flags) {
        if (StringUtils.isNotBlank(key) && value != null) {
            CacheModel cacheModel = manager.get(key);
            if (cacheModel != null) {
                Object tempValue = cacheModel.getValue();
                tempValue = (tempValue == null) ? value : ("" + tempValue + value);
                manager.update(key, tempValue, expires, flags);
                return HttpResult.ok(STORED);
            }
        }
        return HttpResult.ok(NOSTORED);
    }

    /**
     * 追加前面
     * @return 追加成功返回 STORED，否则返回 NOT_STORED
     */
    @RequestMapping(value = "/prepend", method = RequestMethod.POST)
    @ResponseBody
    public Object prepend(@RequestBody CacheModel model) {
        if (StringUtils.isNotBlank(model.getKey()) && model.getValue() != null) {
            CacheModel cacheModel = manager.get(model.getKey());
            if (cacheModel != null) {
                Object value = cacheModel.getValue();
                value = (value == null) ? model.getValue() : ("" + model.getValue() + value);
                manager.update(model.getKey(), value, model.getExpires(), model.getFlags());
                return HttpResult.ok(STORED);
            }
        }
        return HttpResult.ok(NOSTORED);
    }

    /**
     * 追加前面
     * @return 追加成功返回 STORED，否则返回 NOT_STORED
     */
    @RequestMapping(value = "/prepend/{key}/{value}", method = RequestMethod.POST)
    @ResponseBody
    public Object prepend(@PathVariable("key") String key, @PathVariable("value") String value) {
        return prepend(key, value, 0, 0);
    }

    /**
     * 追加前面
     * @return 追加成功返回 STORED，否则返回 NOT_STORED
     */
    @RequestMapping(value = "/prepend/{key}/{value}/{expires}", method = RequestMethod.POST)
    @ResponseBody
    public Object prepend(@PathVariable("key") String key, @PathVariable("value") String value,
                          @PathVariable("expires") int expires) {
        return prepend(key, value, expires, 0);
    }

    /**
     * 追加前面
     * @return 追加成功返回 STORED，否则返回 NOT_STORED
     */
    @RequestMapping(value = "/prepend/{key}/{value}/{expires}/{flags}", method = RequestMethod.POST)
    @ResponseBody
    public Object prepend(@PathVariable("key") String key, @PathVariable("value") String value,
                          @PathVariable("expires") int expires, @PathVariable("flags") int flags) {
        if (StringUtils.isNotBlank(key) && value != null) {
            CacheModel model = manager.get(key);
            if (model != null) {
                Object tempValue = model.getValue();
                tempValue = (tempValue == null) ? value : ("" + value + tempValue);
                manager.update(key, tempValue, expires, flags);
                return HttpResult.ok(STORED);
            }
        }
        return HttpResult.ok(NOSTORED);
    }

}
