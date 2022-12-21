package com.li.csmall.common.web;

import lombok.Data;

import java.io.Serializable;

@Data
public class JsonResult<T> implements Serializable {
    private Integer state;
    private String message;
    private T data;

    //防止外部使用构造方法 必须使用给出的静态方法
    private JsonResult(){}

    public static JsonResult<Void> ok( ){
//        JsonResult jsonResult = new JsonResult();
//        jsonResult.setState(1);
//        return jsonResult;
        return ok(null);
    }
//    public static final Integer STATE_OK = 1;


    public static <T> JsonResult<T> ok( T data){
        JsonResult<T> jsonResult = new JsonResult<>();
        jsonResult.setState(State.ok.getValue());
        jsonResult.setData(data);
        return jsonResult;
    }

    public static JsonResult<Void> fail(State state, String message){
        JsonResult<Void> jsonResult = new JsonResult<>();
        jsonResult.setState(state.getValue());
        jsonResult.setMessage(message);
        return jsonResult;
    }

}
