package com.microservice.authservice.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.SerializationUtils;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

public class SerializableObjectConverter {

    private SerializableObjectConverter() {
        throw new IllegalStateException();
    }

    public static String serialize(OAuth2Authentication object) {
        byte[] bytes = SerializationUtils.serialize(object);
        return Base64.encodeBase64String(bytes);
    }

    public static OAuth2Authentication deserialize(String encodedObject) {
        byte[] bytes = Base64.decodeBase64(encodedObject);
        return (OAuth2Authentication) SerializationUtils.deserialize(bytes);
    }


}
