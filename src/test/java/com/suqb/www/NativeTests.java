package com.suqb.www;

public class NativeTests
{

    public static void main(String[] args)
    {
        
        int a = 2499;
        int loop = a % 500 == 0 ? a / 500 : a / 500 + 1;
        for (int i = 0; i < loop; i++)
        {
            System.out.println(1);
        }
    }
}
