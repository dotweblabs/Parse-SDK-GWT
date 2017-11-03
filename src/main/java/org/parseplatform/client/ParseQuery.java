package org.parseplatform.client;

import com.dotweblabs.shape.client.Shape;
import com.google.common.base.Joiner;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.LinkedList;
import java.util.List;

public class ParseQuery extends JSONObject {

    private Where where;
    private Order order;
    private int limit;
    private int skip;

    private List<String> includes;

    public ParseQuery() {
    }

    public ParseQuery(ParseObject object) {
        setClassName(object.getClassName());
    }

    private String getClassName() {
        return get("className").isString().stringValue();
    }

    private void setClassName(String className) {
        put("className", new JSONString(className));
    }

    public ParseQuery order(Order order) {
        this.order = order;
        put("order", order);
        return this;
    }

    public ParseQuery where(Where where){
        this.where = where;
        put("where", where);
        return this;
    }

    public ParseQuery limit(int limit){
        this.limit = limit;
        put("limit", new JSONNumber(limit));
        return this;
    }

    public ParseQuery skip(int skip){
        this.skip = skip;
        put("skip", new JSONNumber(skip));
        return this;
    }

    public ParseQuery include(String fieldName) {
        if(includes == null) {
            includes = new LinkedList<String>();
        }
        includes.add(fieldName);
        return this;
    }

    public void get(String objectId, final ParseAsyncCallback<ParseObject> response) {
        ParseObject ref = new ParseObject(getClassName());
        ref.setObjectId(objectId);
        ref.retrieve(new ParseAsyncCallback<ParseObject>() {
            @Override
            public void onFailure(ParseError error) {
                response.onFailure(error);
            }
            @Override
            public void onSuccess(ParseObject parseObject) {
                response.onSuccess(parseObject);
            }
        });
    }

    public void find(final ParseAsyncCallback<ParseResponse> callback) {
        String stringIncludes = null;
        if(includes != null) {
            stringIncludes = Joiner.on(",").join(includes);
        }
        String className = getClassName();
        String order = "";
        String where = (get("where") != null && get("where").isObject() != null
                && !get("where").isObject().toString().isEmpty()
                && !get("where").isObject().toString().equals("{}")) ? "where=" + URL.encode(get("where").isObject().toString()) : null;
        String limit = null;
        String skip = null;
        if(get("limit") != null) {
            limit = get("limit").isNumber() != null ? "limit=" + ((int)get("limit").isNumber().doubleValue()) : null;
        }
        if(get("skip") != null) {
            skip = get("skip").isNumber() != null ? "skip=" + ((int)get("skip").isNumber().doubleValue()) : null;
        }
        if(get("order") != null && get("order").isArray() != null) {
            order = "order=";
            JSONArray jsonArray = get("order").isArray();
            String arrayString = jsonArray.toString();
            arrayString = arrayString.substring(1, arrayString.length() -1);
            arrayString = arrayString.replaceAll("\"", "");
            order = order + URL.encode(arrayString);
        }
        String queryParams = "";
        // where + order + limit + skip
        if(where != null) {
            if(order != null) {
                if(limit != null) {
                    if(skip != null) {
                        queryParams = "?" + where + "&" + order + "&" + limit + "&" + skip;
                    } else {
                        queryParams = "?" + where + "&" + order + "&" + limit;
                    }
                } else {
                    if(skip != null) {
                        queryParams = "?" + where + "&" + order + "&" + skip;
                    } else {
                        queryParams = "?" + where + "&" + order;
                    }
                }
            } else {
                if(limit != null) {
                    if(skip != null) {
                        queryParams = "?" + where + "&" + limit + "&" + skip;
                    } else {
                        queryParams = "?" + where + "&" + limit;
                    }
                } else {
                    if(skip != null) {
                        queryParams = "?" + where + "&" + skip;
                    } else {
                        queryParams = "?" + where;
                    }
                }
            }
        } else {
            if(order != null) {
                if(limit != null) {
                    if(skip != null) {
                        queryParams = "?" + order + "&" + limit + "&" + skip;
                    } else {
                        queryParams = "?" + order + "&" + limit;
                    }
                } else {
                    if(skip != null) {
                        queryParams = "?" + order + "&" + skip;
                    } else {
                        queryParams = "?" + order;
                    }
                }
            } else {
                if(limit != null) {
                    if(skip != null) {
                        queryParams = "?" + limit + "&" + skip;
                    } else {
                        queryParams = "?" + limit;
                    }
                } else {
                    if(skip != null) {
                        queryParams = "?" + skip;
                    } else {
                        queryParams = "";
                    }
                }
            }
        }
        //logger.info(Parse.SERVER_URL + Parse.CLASSES_URI + className + queryParams);
        String queryUrl = Parse.SERVER_URL + Parse.CLASSES_URI + className + queryParams;
        if(stringIncludes != null && !stringIncludes.isEmpty()) {
            if(queryUrl.equals(Parse.SERVER_URL + Parse.CLASSES_URI + className)) {
                queryUrl = queryUrl + "?include=" + stringIncludes;
            } else {
                queryUrl = queryUrl + "&include=" + stringIncludes;
            }
        }
        Shape.get(queryUrl)
                .header("X-Parse-Application-Id", Parse.X_Parse_Application_Id)
                .header("X-Parse-REST-API-Key", Parse.X_Parse_REST_API_Key)
                .header("X-Parse-Master-Key", Parse.X_Parse_Master_Key)
                .header("X-Parse-Session-Token", Parse.X_Parse_Session_Token)
                .asJson(new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        callback.onFailure(new ParseError(throwable));
                    }
                    @Override
                    public void onSuccess(String s) {
                        if(s != null && !s.isEmpty()) {
                            ParseResponse resp = ParseResponse.parse(s);
                            callback.onSuccess(resp);
                        } else {
                            callback.onFailure(new ParseError(204, "No response"));
                        }
                    }
                });
    }

    public Subscription subscribe() {
        Subscription subscription = new Subscription(getClassName(), where);
        return subscription;
    }
}
