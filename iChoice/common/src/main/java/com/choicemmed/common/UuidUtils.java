package com.choicemmed.common;

import java.util.UUID;

public class UuidUtils {

    public static String getUuid() {
        return UUID.randomUUID().toString();
    }
}
