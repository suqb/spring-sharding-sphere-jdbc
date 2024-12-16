package com.suqb.www.exception;

public class NoMoneyException extends RuntimeException
{
    public NoMoneyException()
    {
        super("The KFC Crazy Thursday Need ￥50");
    }
}
