package com.vinsguru.sec09;

import com.vinsguru.sec09.controller.DocumentController;
import com.vinsguru.sec09.security.threadlocal.AuthenticationService;
import com.vinsguru.sec09.security.threadlocal.SecurityContextHolder;
import com.vinsguru.util.CommonUtils;

import java.time.Duration;

public class Lec03DocumentAccessWithThreadLocal {

    private static final DocumentController documentController = new DocumentController(SecurityContextHolder::getContext);

    static void main() {

        Thread.ofVirtual().name("admin").start(() -> documentAccessWorkflow(1, "password"));
        Thread.ofVirtual().name("editor").start(() -> documentAccessWorkflow(2, "password"));

        CommonUtils.sleep(Duration.ofSeconds(1));

    }

    private static void documentAccessWorkflow(Integer userId, String password){
        AuthenticationService.loginAndExecute(userId, password, () -> {
            documentController.read();
            documentController.edit();
            documentController.delete();
        });
    }

}
