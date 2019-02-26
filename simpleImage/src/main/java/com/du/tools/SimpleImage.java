package com.du.tools;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Title
 * @ClassName SimpleImage
 * @Author jsb_pbk
 * @Date 2019/1/17
 */
public class SimpleImage {

    public static String WATER_IMAGE_URL = "D:\\test\\zf.png";
    public static String SRC = "D:\\test\\src.png";
    public static String SAVE = "D:\\test\\target.png";

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


    public static void imgDealwith(){
        try {
            BufferedImage bufferedImage = getBufferedImage(SRC);
            //得到画笔对象
            Graphics g = bufferedImage.getGraphics();

            BufferedImage bufferedIcon = getBufferedImage(WATER_IMAGE_URL);

            g.drawImage(bufferedIcon,200,200,260,260,null);

            writeImageFile(bufferedImage);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeImageFile(BufferedImage bi) throws IOException {
        File outputfile = new File(SAVE);
        ImageIO.write(bi, "png", outputfile);
    }

}
