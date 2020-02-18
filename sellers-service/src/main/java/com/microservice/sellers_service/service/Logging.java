package com.microservice.sellers_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.logstash.logback.argument.StructuredArguments.kv;


public class Logging {

    protected final Logger LOGGER;

    public Logging(Class object) {
        LOGGER = LoggerFactory.getLogger(object);
        System.out.print(object);
    }

    public void printInfo(String message) {
        try {
            LOGGER.info(message);
        } catch (Exception e) {
            System.out.println("Logger does not work");
        }
    }

    public void printPar(String end, String user, String ip, String par) {
        try {
            LOGGER.info("{} {} {} {}", kv("ENDPOINT", end), kv("USER", user), kv("IP", ip), kv("PAR", par));
        } catch (Exception e) {
            System.out.println("Logger does not work");
        }
    }

    public void printError(String message) {
        try {
            LOGGER.error(message);
        } catch (Exception e) {
            System.out.println("Logger does not work");
        }
    }

    public void printWarning(String message) {
        try {
            LOGGER.warn(message);
        } catch (Exception e) {
            System.out.println("Logger does not work");
        }
    }
}
