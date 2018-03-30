package org.parseplatform.client.where;

import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

public class TextParameter extends JSONObject {

    JSONObject search = new JSONObject();

    public TextParameter() {
        put("$search", search);
    }

    public TextParameter(String term) {
        put("$search", search);
        setTerm(term);
    }

    public void setTerm(String term) {
        JSONObject jsonObject = get("$search").isObject();
        jsonObject.put("$term", new JSONString(term));
        put("$search", jsonObject);
    }

    public void setLanguage(String language) {
        JSONObject jsonObject = get("$search").isObject();
        jsonObject.put("$language", new JSONString(language));
        put("$search", jsonObject);
    }

    public void setCaseSensitive(boolean isCaseSensitive) {
        JSONObject jsonObject = get("$search").isObject();
        jsonObject.put("$caseSensitive", JSONBoolean.getInstance(isCaseSensitive));
        put("$search", jsonObject);
    }

    public void setDiacriticSensitive(boolean isDiacriticSensitive) {
        JSONObject jsonObject = get("$search").isObject();
        jsonObject.put("$diacriticSensitive", JSONBoolean.getInstance(isDiacriticSensitive));
        put("$search", jsonObject);
    }

}
