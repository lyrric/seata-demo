package com.github.lyrric.tcc.business.plugin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.lyrric.tcc.business.model.HttpResult;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Created on 2019/3/12.
 * 封装返回数据
 * @author wangxiaodong
 */
@ControllerAdvice(annotations = {RestController.class})
public class ResponseWrapper implements ResponseBodyAdvice<Object> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter,
                                  MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        //避免二次封装
        if(o instanceof HttpResult){
            return o;
        }
        if(o instanceof String){
            try {
                //这里如果直接返回String的话，会报错
                serverHttpResponse.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                return objectMapper.writeValueAsString(HttpResult.success(o));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
        }
        return HttpResult.success(o);
    }
}
