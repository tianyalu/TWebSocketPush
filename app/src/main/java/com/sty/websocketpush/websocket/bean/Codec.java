package com.sty.websocketpush.websocket.bean;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sty.websocketpush.websocket.utils.Logger;

/**
 * 服务器响应解析类
 * @Author: tian
 * @UpdateDate: 2020/9/7 9:45 AM
 */
public class Codec {
    private static final String TAG = Codec.class.getSimpleName();
    public static Response decoder(String text) {
        Response response = new Response();
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(text);
        if(element.isJsonObject()) {
            JsonObject obj = (JsonObject) element;
            response.setRespEvent(decoderInt(obj, "resp_event"));
            response.setAction(decoderStr(obj, "action"));
            response.setSeqId(decoderStr(obj, "seq_id"));
            response.setResp(decoderStr(obj, "resp"));
        }
        return response;
    }

    private static int decoderInt(JsonObject obj, String name) {
        int result = -1;
        JsonElement element = obj.get(name);
        if(null != element) {
            try {
                result = element.getAsInt();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private static String decoderStr(JsonObject obj, String name) {
        String result = "";
        JsonElement element = obj.get(name);
        if(null != element) {
            if(element.isJsonPrimitive()) {
                result = element.getAsString();
            }else {
                result = element.getAsJsonObject().toString();
            }
        }
        return result;
    }

    private static String decoderArray(JsonObject obj, String name) {
        String result = "";
        JsonElement element = obj.get(name);
        if(null != element) {
            if(element.isJsonPrimitive()) {
                result = element.getAsString();
            }else {
                result = element.getAsJsonArray().toString();
            }
        }
        return result;
    }

    public static ChildResponse decoderChildResp(String jsonStr) {
        ChildResponse childResponse = new ChildResponse();
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(jsonStr);
        if(element.isJsonObject()) {
            JsonObject jsonObject = (JsonObject) element;
            childResponse.setCode(decoderInt(jsonObject, "code"));
            childResponse.setMsg(decoderStr(jsonObject, "msg"));
            try {
                int dataType = decoderInt(jsonObject, "data_type");
                if(dataType < 0) {
                    dataType = 0; //没有传的话默认是对象
                }
                Logger.d(TAG, "dataType: " + dataType);
                childResponse.setDataType(dataType);
                if (dataType == 0) { //对象
                    childResponse.setData(decoderStr(jsonObject, "data"));
                } else if (dataType == 1) { //数组
                    childResponse.setData(decoderArray(jsonObject, "data"));
                }
            }catch (Exception e) {
                e.printStackTrace();
                Logger.e(TAG, e.getMessage());
            }
        }
        return childResponse;
    }
}
