package org.parseplatform.client;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import elemental.client.Browser;

public class ParseError extends JSONObject {

    private Integer code;

    private String error;

    public ParseError(Integer code, String error) {
        this.code = code;
        this.error = error;
    }

    public ParseError(Throwable t) {
        String message = null;
        if(t != null && t.getMessage() != null) {
            message = t.getMessage();
        }
        if(message != null) {
            JSONValue jsonError = JSONParser.parseStrict(message);
            if(jsonError != null && jsonError.isObject() != null) {
                JSONObject error = jsonError.isObject();
                Double d =  (error.get("code") != null && error.get("code").isNumber() != null)
                        ? error.get("code").isNumber().doubleValue(): null;
                this.code = d.intValue();
                this.error = (error.get("error") != null && error.get("error").isString() != null)
                        ? error.get("error").isString().stringValue() : null;
            }
        }
    }

    public Integer getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public void log() {
        Browser.getWindow().getConsole().log(this.toString());
    }
}
