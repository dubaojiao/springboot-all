package com.du;

import java.util.HashMap;

/**
 * @Title
 * @ClassName Main
 * @Author jsb_pbk
 * @Date 2019/1/8
 */
public class Main {
    public static void main(String[] args) {

        MyHashMap<String,String> myHashMap = new MyHashMap<>();
        System.out.println(myHashMap.put("曹操","曹操"));
        System.out.println(myHashMap.put("周瑜","周瑜"));
        System.out.println(myHashMap.put("刘备","刘备"));
        System.out.println(myHashMap.put("赵云","赵云"));
        System.out.println(myHashMap.put("吕布","吕布"));
        System.out.println(myHashMap.put("吕布","老师"));

        System.out.println(String.format("size is %s",myHashMap.size()));

        System.out.println(String.format("key='吕布' is %s",myHashMap.get("吕布")));

        System.out.println(String.format("key='周瑜' is %s",myHashMap.get("周瑜")));

        System.out.println(String.format("key='曹操' is %s",myHashMap.get("曹操")));
    }
}
