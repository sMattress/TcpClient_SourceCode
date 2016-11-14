package model;

import com.alibaba.fastjson.JSONArray;

public class APIsMsg {

    private Integer flag = null;
    private Integer errCode = null;
    private Integer cmd = null;
    private JSONArray params = null;
    private String version = "1.0";
    private String cause = null;

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    public Integer getCmd() {
        return cmd;
    }

    public void setCmd(Integer cmd) {
        this.cmd = cmd;
    }

    public JSONArray getParams() {
        return params;
    }

    public void setParams(JSONArray params) {
        this.params = params;
    }

    public void addParam(Object param) {
        if (params == null) {
            params = new JSONArray();
        }
        params.add(param);
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
}
