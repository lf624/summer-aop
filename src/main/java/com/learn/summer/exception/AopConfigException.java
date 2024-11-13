package com.learn.summer.exception;

public class AopConfigException extends NestedRuntimeException{
    public AopConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public AopConfigException(Throwable cause) {
        super(cause);
    }

    public AopConfigException(String message) {
        super(message);
    }

    public AopConfigException() {
        super();
    }
}
