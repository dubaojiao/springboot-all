package com.du.demo;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

/**
 * @Title
 * @ClassName URLDemo
 * @Author jsb_pbk
 * @Date 2019/1/15
 */
public class URLDemo {

    //爬取的数据存放目录
    private static String savePath = "P:/dangdang_book/";
    private static String savePathUrl = "P:/dangdang_url/";

    //等待爬去的url
    private static List<String>  allwaiturl = new ArrayList<>();

    //已经爬取的url
    private static  List<String> alloverurl = new ArrayList<>();

    public static Map<String,Integer> allurldepth  = new HashMap<>();

    //深度
    private static Integer maxdepth = 2;

    private final static String URL_REGULAR = "^((http|https)://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$";


    private final static String HREF_REGULAR = "<a .*href=.+</a>";

    public static void demo1(String url){
        try {
            URL url1 = new URL(url);
            URLConnection connection = url1.openConnection();
            InputStream inputStream = connection.getInputStream();
            System.out.println(connection.getContentEncoding());

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

            String line = null;

            while ((line = reader.readLine()) != null){
                System.out.println(line);
            }

            reader.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static void demo2(String url,Integer depth){
        Pattern pattern = Pattern.compile(URL_REGULAR);
        Matcher matcher = pattern.matcher(url);
        if(url != null && matcher.matches() && !alloverurl.contains(url) && depth<=maxdepth){
            PrintWriter printWriterUrl = null;
            PrintWriter printWriter = null;
            BufferedReader reader = null;
            System.out.println(url);
            try {
                URL u = new URL(url);
                URLConnection connection = u.openConnection();
                InputStream in = connection.getInputStream();
                 reader = new BufferedReader(new InputStreamReader(in,"GB2312"));
                String line = null;
                Pattern hrefPattern = Pattern.compile(HREF_REGULAR);
                 printWriter  = new PrintWriter(new File(savePath+System.currentTimeMillis()+".txt"), "UTF-8");
                 printWriterUrl  = new PrintWriter(new File(savePathUrl+System.currentTimeMillis()+".txt"), "UTF-8");

                while ((line = reader.readLine()) != null){
                    //System.out.println(line);
                    printWriter.println(line);
                    Matcher m=hrefPattern.matcher(line);
                    while (m.find()){
                        String href=m.group();
                        //<a name="nav2" target="_blank" href="http://category.dangdang.com/all/?category_id=4001132">数码相机</a>
                        href=href.substring(href.indexOf("href="));
                        //href="http://category.dangdang.com/all/?category_id=4001132">数码相机</a>
                        String u1;
                        String u2;
                        if(href.charAt(5) == '\"'){
                            href=href.substring(6);
                             u1 = href.substring(0,href.indexOf("\""));
                             u2 = href.substring(href.indexOf(">")+1,href.indexOf("<"));
                        }else {
                            href=href.substring(5);
                            if(href.indexOf(" ") == -1){
                                u1 = href.substring(0,href.indexOf(">"));
                            }else {
                                u1 = href.substring(0,href.indexOf(" "));
                            }
                             u2 = href.substring(href.indexOf(">")+1,href.indexOf("<"));
                        }
                        //System.out.println(u1);
                        //System.out.println(u2);

                        //http://category.dangdang.com/all/?category_id=4001132">数码相机</a>
                        if(u1.startsWith("https:") || u1.startsWith("http:") || u1.startsWith("/") ){
                                if(!allwaiturl.contains(u1)){
                                    if(u1.startsWith("/")){
                                        u1 = url+u1;
                                    }
                                    if(depth+1 <= maxdepth){
                                        allwaiturl.add(u1);
                                        allurldepth.put(u1,depth+1);
                                    }
                                    printWriterUrl.println(u1+" "+new String(u2.getBytes("utf-8"),"utf-8"));
                                }
                        }

                    }
                }

                alloverurl.add(url);
                System.out.println(url+"网页爬取完成，已爬取数量："+alloverurl.size()+"，剩余爬取数量："+allwaiturl.size());

            }catch (Exception ex){
                ex.printStackTrace();
                alloverurl.add(url);
                System.out.println(url+"网页爬取失败，已爬取数量："+alloverurl.size()+"，剩余爬取数量："+allwaiturl.size());
            }finally {
                if(printWriter != null){
                    printWriter.close();
                }
                if(printWriterUrl != null){
                    printWriterUrl.close();
                }
               if(reader != null){
                   try {
                       reader.close();
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }
            }
        }else {
            System.out.println("未通过检查");
            if(url == null){
                System.out.println(url+"----------------------空的");
            }
            if(!matcher.matches()){
                System.out.println(url+"----------------------不是url");
            }
            if(alloverurl.contains(url)){
                System.out.println(url+"----------------------已经爬取");
            }
            if(depth>maxdepth){
                System.out.println(url+"----------------------太深了");
            }
        }

        if(allwaiturl.size() > 0){
            String newUrl = allwaiturl.get(0);

            allwaiturl.remove(0);

            demo2(newUrl,allurldepth.get(newUrl));
        }
    }

}
