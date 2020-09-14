package com.github.lyrric.tcc.common;

public class NoRollbackException extends RuntimeException {
    public NoRollbackException(String msg){
        super(msg);
    }
}
