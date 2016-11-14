package action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import model.MockHardware;
import wtf.socket.WTFSocketMsg;

import java.lang.reflect.Field;

/**
 * 命令执行基类
 * 提供一些辅组方法
 */
public abstract class BaseAction {

    MockHardware model = new MockHardware();

    public abstract WTFSocketMsg execute(JSONObject body);

    public BaseAction setModel (MockHardware model) {
        this.model = model;
        return this;
    }

    /**
     * 验证参数列表
     *
     * @param body 消息对象
     * @param keys 必含的key
     * @return 错误代码
     */
    ErrCode verifyParams(JSONObject body, String... keys) {

        // 空的params
        if (!body.containsKey("params")) {
            return new ErrCode(ErrCodeType.ERR_FORMAT, "lack attr => <params>");
        }

        JSONArray params = body.getJSONArray("params");

        if (params.isEmpty()) {
            return new ErrCode(ErrCodeType.LACK_PARAM, "lack param => <" + keys[0] + ">");
        }

        for (int i = 0; i < params.size(); i++) {
            JSONObject param = params.getJSONObject(i);
            for (String key : keys) {
                if (!param.containsKey(key)) {
                    return new ErrCode(ErrCodeType.LACK_PARAM, "lack parameter => <" + key + ">");
                }
            }
        }
        return new ErrCode(ErrCodeType.NONE);
    }

    /**
     * 验证参数模版
     *
     * @param body 消息对象
     * @param clazz 消息模板
     * @return 错误代码
     */
    ErrCode verifyParams(JSONObject body, Class<?> clazz) {

        Field fields[] = clazz.getDeclaredFields();
        String keys[] = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            keys[i] = fields[i].getName();
        }
        return verifyParams(body, keys);
    }

    /**
     * 从参数中解析side
     *
     * @param body 消息对象
     * @return side
     */
    int getSide(JSONObject body) {
        return body.getJSONArray("params").getJSONObject(0).getIntValue("side");
    }

    /**
     * 从参数中解析模板对象
     *
     * @param body 消息对象
     * @param clazz 消息模版类
     * @param index params中的下标
     * @return Object
     */
    <T> T getParam(JSONObject body, Class<T> clazz, int index) {
        if (index >= body.getJSONArray("params").size()) {
            return null;
        }
        return JSON.parseObject(body.getJSONArray("params").getString(index), clazz);
    }

    /**
     * 从参数中解析模板对象
     * index 默认为0
     *
     * @param body 消息对象
     * @param clazz 消息模版类
     * @return Object
     */
    <T> T getParam(JSONObject body, Class<T> clazz) {
        return getParam(body, clazz, 0);
    }
}
