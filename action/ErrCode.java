package action;

import com.alibaba.fastjson.annotation.JSONField;

public class ErrCode {

    public ErrCode(String cause) {
        this(0, cause);
    }

    public ErrCode(int errCode) {
        this(errCode, null);
    }

    public ErrCode(int errCode, String cause) {
        this.code = errCode;
        this.cause = cause;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    private String cause;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @JSONField(serialize = false)
    private int code;

}
