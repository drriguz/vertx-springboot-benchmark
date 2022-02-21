package com.riguz.frameworks.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class Service {
    private DataStore dataStore = DataStore.instance;
    private ObjectMapper objectMapper = new ObjectMapper();

    public AppInfo getAppInfo(String app) {
        if (app == null)
            throw new InvalidRequestException("App id is empty");

        AppInfo info = dataStore.get(app);

        if (info == null)
            throw new AppNotFoundException("App not found:" + app);

        return info;
    }

    public String getSign(AppInfo appInfo) {
        return getSign(toJson(appInfo));
    }

    public String getSign(AppInfo appInfo, String nonce) {
        return getSign(toJson(appInfo) + nonce);
    }

    private String toJson(AppInfo info) {
        try {
            return objectMapper.writeValueAsString(info);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String getSign(String json) {

        return Hashing.sha256()
                .hashString(json, StandardCharsets.UTF_8)
                .toString();
    }
}
