package com.riguz.frameworks.springboot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.riguz.frameworks.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {
    private DataStore dataStore = DataStore.instance;
    private ObjectMapper objectMapper = new ObjectMapper();

    private Service service = new Service();

    @GetMapping("/api/v1/{app}")
    public AppInfo getAppInfoV1(@PathVariable String app)
            throws JsonProcessingException, InvalidRequestException, AppNotFoundException {
        return service.getAppInfo(app);
    }

    @GetMapping("/api/v2/{app}")
    public ResponseEntity<AppInfo> getAppInfoV2(@PathVariable String app)
            throws InvalidRequestException, AppNotFoundException {
        AppInfo info = service.getAppInfo(app);

        return ResponseEntity.ok()
                .header("Signature", service.getSign(info))
                .body(info);
    }

    @PostMapping("/api/v1/{app}")
    public ResponseEntity<AppInfo> getAppInfoV1ByPost(@PathVariable String app,
                                                      @RequestBody Request request)
            throws InvalidRequestException, AppNotFoundException {
        AppInfo info = service.getAppInfo(app);

        return ResponseEntity.ok()
                .header("nonce", request.getNonce())
                .body(info);
    }

    @PostMapping("/api/v2/{app}")
    public ResponseEntity<AppInfo> getAppInfoV2ByPost(@PathVariable String app,
                                                      @RequestBody Request request)
            throws InvalidRequestException, AppNotFoundException, JsonProcessingException {
        AppInfo info = service.getAppInfo(app);

        return ResponseEntity.ok()
                .header("Signature", service.getSign(info, request.getNonce()))
                .body(info);
    }
}
