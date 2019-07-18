package shop.web.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import shop.api.entity.annot.JsonArg;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class JsonPathArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String JSONBODYATTRIBUTE = "JSON_REQUEST_BODY";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(JsonArg.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        try {
            String body = getRequestBody(webRequest);
            if (null != body && body.length() > 0) {
                ObjectMapper json = new ObjectMapper();
                JsonNode root = json.readTree(body);
                JsonNode param = root.get(parameter.getParameterName());
                if (null != param) {
                    ObjectMapper headMapper = new ObjectMapper();
                    Object value = headMapper.treeToValue(param, parameter.getParameterType());
                    return value;
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    private String getRequestBody(NativeWebRequest webRequest) {
        HttpServletRequest req = (HttpServletRequest) webRequest.getNativeRequest();
        String jsonBody = (String) req.getAttribute(JSONBODYATTRIBUTE);
        if (jsonBody == null) {
            try {
                String body = new BufferedReader(new InputStreamReader(req.getInputStream(), StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
                req.setAttribute(JSONBODYATTRIBUTE, body);
                return body;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return jsonBody;
    }
}
