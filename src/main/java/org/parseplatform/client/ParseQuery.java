package org.parseplatform.client;

/*
File:  ParseQuery.java
Version: 0-SNAPSHOT
Contact: hello@dotweblabs.com
----
Copyright (c) 2018, Dotweblabs Web Technologies
All rights reserved.
Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
    * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
    * Neither the name of Dotweblabs Web Technologies nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

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

/**
 * @author Kerby Martino
 * @since 0-SNAPSHOT
 * @version 0-SNAPSHOT
 */
public class ParseQuery extends JSONObject {

    private Where where;
    private Order order;
    private int limit;
    private int skip;

    private List<String> includes;
    private List<String> keys;

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

    public ParseQuery key(String fieldName) {
        if(keys == null) {
            keys = new LinkedList<String>();
        }
        keys.add(fieldName);
        return this;
    }


    public ParseQuery order(String key) {
        put("order", new JSONString(key));
        return this;
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

    public ParseQuery count(int count) {
        put("count", new JSONNumber(count));
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
        String stringKeys = null;
        if(includes != null) {
            stringIncludes = Joiner.on(",").join(includes);
        }
        if(keys != null && !keys.isEmpty()) {
            stringKeys = Joiner.on(",").join(keys);
        }
        String className = getClassName();
        String order = null;
        String where = (get("where") != null && get("where").isObject() != null
                && !get("where").isObject().toString().isEmpty()
                && !get("where").isObject().toString().equals("{}")) ? "where=" + URL.encode(get("where").isObject().toString()) : null;
        String limit = null;
        String skip = null;
        String count = null;
        if(get("limit") != null) {
            limit = get("limit").isNumber() != null ? "limit=" + ((int)get("limit").isNumber().doubleValue()) : null;
        }
        if(get("count") != null) {
            count = get("count").isNumber() != null ? "count=" + ((int)get("count").isNumber().doubleValue()) : null;
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
        if(get("order") != null && get("order").isString() != null){
            order = "order=" + get("order").isString().stringValue();
        }
        String queryParams = "";
        // where + order + limit + skip
        if(where != null) {
            if(order != null) {
                if(limit != null) {
                    if(skip != null) {
                        if(count != null) {
                            queryParams = "?" + where + "&" + order + "&" + limit + "&" + skip + "&" + count;
                        } else {
                            queryParams = "?" + where + "&" + order + "&" + limit + "&" + skip;
                        }
                    } else {
                        if(count != null) {
                            queryParams = "?" + where + "&" + order + "&" + limit  + "&" + count;
                        } else {
                            queryParams = "?" + where + "&" + order + "&" + limit;
                        }
                    }
                } else {
                    if(skip != null) {
                        if(count != null) {
                            queryParams = "?" + where + "&" + order + "&" + skip + "&" + count;
                        } else {
                            queryParams = "?" + where + "&" + order + "&" + skip;
                        }
                    } else {
                        if(count != null) {
                            queryParams = "?" + where + "&" + order + "&" + count;
                        } else {
                            queryParams = "?" + where + "&" + order;
                        }
                    }
                }
            } else {
                if(limit != null) {
                    if(skip != null) {
                        if(count != null) {
                            queryParams = "?" + where + "&" + limit + "&" + skip  + "&" + count;
                        } else {
                            queryParams = "?" + where + "&" + limit + "&" + skip;
                        }
                    } else {
                        if(count != null) {
                            queryParams = "?" + where + "&" + limit + "&" + count;
                        } else {
                            queryParams = "?" + where + "&" + limit;
                        }
                    }
                } else {
                    if(skip != null) {
                        if(count != null) {
                            queryParams = "?" + where + "&" + skip + "&" + count;
                        } else {
                            queryParams = "?" + where + "&" + skip;
                        }
                    } else {
                        if(count != null) {
                            queryParams = "?" + where + "&" + count;
                        } else {
                            queryParams = "?" + where;
                        }
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
            if(count != null) {
                if(queryParams.contains("?")) {
                    queryParams = queryParams + "&count=1";
                } else {
                    queryParams = queryParams + "?count=1";
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
        if(stringKeys != null && !stringKeys.isEmpty()) {
            if(queryUrl.equals(Parse.SERVER_URL + Parse.CLASSES_URI + className)) {
                queryUrl = queryUrl + "?keys=" + stringKeys;
            } else {
                queryUrl = queryUrl + "&keys=" + stringKeys;
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
                            try {
                                ParseResponse resp = new ParseResponse(s);
                                callback.onSuccess(resp);
                            } catch (Exception e) {
                                callback.onFailure(new ParseError(e));
                            }
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
