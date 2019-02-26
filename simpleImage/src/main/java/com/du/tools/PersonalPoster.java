package com.du.tools;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title
 * @ClassName PersonalPoster
 * @Author jsb_pbk
 * @Date 2019/1/22
 */
public class PersonalPoster {

    private final static String SAVE = "D:\\test\\bus\\t.png";

    public  static List<String> templates =  new ArrayList<>();

    static {
        if(templates == null){
            templates = new ArrayList<>();
        }
        templates.add("D:\\test\\bus\\template1.png");
        templates.add("D:\\test\\bus\\template2.png");
        templates.add("D:\\test\\bus\\template3.png");
    }

    public static String generate(String url,Integer index,String phone,String name,String post,String headPath)  throws Exception{
        //链接二维码
        BufferedImage urlbuffer = ZXingCode.drawQRCode(url);
        //获取模板
        if(index < templates.size()){
            String template = templates.get(index);

            BufferedImage bufferedImage = getBufferedImage(template);

            //得到画笔对象
            Graphics g = bufferedImage.getGraphics();

            BufferedImage bufferedIcon = getBufferedImage(headPath);

            g.drawImage(bufferedIcon,(bufferedImage.getWidth()-229)/2,29,229,229,null);


            if(name != null && !"".equals(name)){

                g.setColor(new Color(0, 0, 203));
                g.setFont(new Font("黑体", Font.BOLD, 60)); // 字体、字型、字号

                int strWidth = g.getFontMetrics().stringWidth(name);

                //g.drawString(name, 270,610); // 画文字
                g.drawString(name, (bufferedImage.getWidth()-strWidth)/2,610); // 画文字
            }

            if(post != null && !"".equals(post)){
                post = "【"+post+"】";
                g.setColor(new Color(0, 0, 203));
                g.setFont(new Font("", Font.BOLD, 35)); // 字体、字型、字号
                int strWidth = g.getFontMetrics().stringWidth(post);
                g.drawString(post, (bufferedImage.getWidth()-strWidth)/2,667); // 画文字
            }


            if(phone != null && !"".equals(phone)){
                phone = "合作电话："+phone;
                g.setColor(new Color(0, 0, 203));
                g.setFont(new Font("黑体", Font.BOLD, 35)); // 字体、字型、字号
                int strWidth = g.getFontMetrics().stringWidth(phone);
                g.drawString(phone, (bufferedImage.getWidth()-strWidth)/2,715); // 画文字
            }


            g.drawImage(urlbuffer,(bufferedImage.getWidth()-260)/2+10,980,260,260,null);

            g.dispose();
            bufferedImage.flush();

            writeImageFile(bufferedImage);

            return "成功";

        }else {
            //模板下标越界
            return "失败";
        }


    }


    /**
     * 获取文件的 BufferedImage
     * @param fileName 文件名称
     * @return 文件的 BufferedImage
     * @throws IOException 文件找不到的异常
     */
    public static BufferedImage getBufferedImage(String fileName) throws IOException {
        File file = new File(fileName);
        return ImageIO.read(file);
    }


    public static void writeImageFile(BufferedImage bi) throws IOException {
        File outputfile = new File(SAVE);
        ImageIO.write(bi, "png", outputfile);
    }

}
