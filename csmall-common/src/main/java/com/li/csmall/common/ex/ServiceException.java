package com.li.csmall.common.ex;

import com.li.csmall.common.web.State;

public class ServiceException extends RuntimeException{
    private State state;

    public  ServiceException(State state, String message){
        super(message);
        if(state == null){
            throw new IllegalArgumentException("Error State must need when ServiceException is used");
        }
        this.state = state;
    }

    public State getState(){
        return state;
    }

}
