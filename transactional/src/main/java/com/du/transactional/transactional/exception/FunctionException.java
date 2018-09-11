package com.du.transactional.transactional.exception;

public class FunctionException extends Exception{
    private String msg ;
    public FunctionException(String msg){
        super(msg);
        this.msg = msg;
    }
    public void exceptionMessage(){
        System.out.println("===========================当前异常错误消息:"+this.msg);
    }
}
