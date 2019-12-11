package com.microservice.gatewaycloud;

import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.embedded.netty.SslServerCustomizer;
import org.springframework.boot.web.server.Http2;
import org.springframework.boot.web.server.Ssl;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

@Component
public class NettyWebServerFactorySslCustomizer implements WebServerFactoryCustomizer<NettyReactiveWebServerFactory> {

    @Override
    public void customize(NettyReactiveWebServerFactory serverFactory) {
        Ssl ssl = new Ssl();
        ssl.setEnabled(true);
        ssl.setKeyStore("classpath:gateway.p12");
        ssl.setTrustStore("classpath:eurekaTrust.jks");
        ssl.setKeyAlias("localhost");
        ssl.setKeyPassword("password");
        ssl.setTrustStorePassword("password");
        ssl.setKeyStorePassword("password");
        Http2 http2 = new Http2();
        http2.setEnabled(false);
        serverFactory.addServerCustomizers(new SslServerCustomizer(ssl, http2, null));
        serverFactory.setPort(8088);
    }
}
