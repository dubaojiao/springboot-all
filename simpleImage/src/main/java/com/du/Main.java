package com.du;

import com.du.tools.PersonalPoster;
import com.du.tools.SimpleImage;

/**
 * @Title
 * @ClassName Main
 * @Author jsb_pbk
 * @Date 2019/1/17
 */
public class Main {
    public static void main(String[] args) {

        try {
            PersonalPoster.generate("www.baidu.com",0,"15559709268","都包角","事务部经理","D:\\test\\bus\\head.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
