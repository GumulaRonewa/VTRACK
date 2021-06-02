package sample;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
/*
im tired i will be back in two hour ,run the code to rember where i left it off
* */
public class blah {
    static int[][] actualPixels;

    static int pixelNumber = 2;
    static double tolerance = 1.0;

    static ArrayList<disk> diskArrayList = new ArrayList<>(0); //list of disks identified
    static ArrayList<nonDisk> nonDiskArrayList = new ArrayList<>(0); //list of non disks
    List<Integer> checked=new ArrayList<Integer>();
    LinkedList listofpN = new LinkedList();

    public int Process(File file){
        int[][] array;
        int[] ping = new int[6];
        if (file != null) {
            array= Pixel(file);


            actualPixels=array;
            for (int i = 1; i < array.length; i++) {
                for (int j = 1; j < array[0].length; j++) {
                    if (actualPixels[i][j] == 1) {

                        ping = findCentre(j, i); //finds centre of any shape that's hit
                        int yt=ping[0];
                        int yb=ping[1];
                        int xr=ping[3];
                        int xl=ping[2];
                        flipper(ping[4],ping[5], pixelNumber,xl,xr,yb,yt);

                        pixelNumber++;
                    }
                }
            }


            BufferedImage frameGiven=new  BufferedImage(actualPixels[0].length,actualPixels.length,BufferedImage.TYPE_3BYTE_BGR);
            for (int i = 0; i < actualPixels.length; i++) {
                for (int j = 0; j < actualPixels[0].length; j++) {
                    if(actualPixels[i][j]==9999){
                        Color green=new Color(0,255,0);
                        int rgb=green.getRGB();
                        frameGiven.setRGB(j,i,rgb);
                    }
                    else if(actualPixels[i][j]>1 &&actualPixels[i][j]<9999){
                        Color green=new Color(255,255,255);
                        int rgb=green.getRGB();
                        frameGiven.setRGB(j,i,rgb);
                    }
                    else{
                        Color green=new Color(0,0,0);
                        int rgb=green.getRGB();
                        frameGiven.setRGB(j,i,rgb);
                    }
                }
            }
            File outFile=new File("boundingbox.png");
            try {
                ImageIO.write(frameGiven,"png",outFile);
                System.out.println("sucess hopefully");
            } catch (IOException e) {
                System.out.println("error");

                e.printStackTrace();
            }
        }


        return 1;
    }


    public static int[] findCentre(int xpos, int ypos) {
        int[] loc=new int[6];
        loc[0]=ypos-1;
        int xwork = xpos;
        int ywork = ypos;
        //horizontal motion
        int xcount = 0;
        while (actualPixels[ywork][xwork] == 1) {
            xcount = xcount + actualPixels[ywork][xwork];
            xwork++;
        }
        xwork = xcount / 2 + xpos; //finds x-coordinate centre

        int ycount = 0;
        while (actualPixels[ywork][xwork] == 1 && ywork < 499) {
            ycount = ycount + actualPixels[ywork][xwork];
            ywork++;
        }
        loc[1]=(ypos+ycount);
        int newx=xwork;
        ywork = ycount / 2 + ypos; //finds y-coordinate centre
        xcount=0;
        while (actualPixels[ywork][xwork] == 1) {
            xcount = xcount + actualPixels[ywork][xwork];
            xwork++;
            try{
                if(actualPixels[ywork][xwork]==0){
                    if(actualPixels[ywork+1][xwork]==1) {

                        ywork++;
                    }
                }
            }
            catch (Exception e){
                break;
            }
        }
        loc[2]=newx-xcount;
        loc[3]=newx+xcount;
        loc[4]=newx;
        loc[5]=ywork;
        return  loc;
    }
    public static void flipper(int xc, int yc, int flipID,int xl,int xr,int yb,int yt) {

        try {
            if (actualPixels[yc][xc] == 0) {

                if ((yc >= yt && yc <= yb) & (xc == xl || xc == xr)) {
                    actualPixels[yc][xc] = 9999;

                    flipper(xc, yc - 1, flipID, xl, xr, yb, yt);
                    flipper(xc, yc + 1, flipID, xl, xr, yb, yt);

                } else if ((xc >= xl && xc <= xr) & (yc == yb || yc == yt)) {
                    actualPixels[yc][xc] = 9999;

                    flipper(xc - 1, yc, flipID, xl, xr, yb, yt);
                    flipper(xc + 1, yc, flipID, xl, xr, yb, yt);

                }
            }
            if (actualPixels[yc][xc] == 1) {
                actualPixels[yc][xc] = flipID;
                flipper(xc - 1, yc + 1, flipID,xl,xr,yb,yt);
                flipper(xc, yc + 1, flipID,xl,xr,yb,yt);
                flipper(xc + 1, yc + 1, flipID,xl,xr,yb,yt);
                flipper(xc - 1, yc, flipID,xl,xr,yb,yt);
                flipper(xc + 1, yc, flipID,xl,xr,yb,yt);
                flipper(xc - 1, yc - 1, flipID,xl,xr,yb,yt);
                flipper(xc, yc - 1, flipID,xl,xr,yb,yt);
                flipper(xc + 1, yc - 1, flipID,xl,xr,yb,yt);
            }
        }
        catch (Exception e){}
    }
    public static int[][] Pixel(File input) {
        try {

            BufferedImage image = ImageIO.read(input);
            int width = image.getWidth();
            int height = image.getHeight();
            int [][] pixels= new int[height][width];
            //posibbly use parallel programming
            double cuttoff=58.12;
            boolean changed=false;
            for(int i=0; i<height; i++) {

                for(int j=0; j<width; j++) {

                    Color c = new Color(image.getRGB(j, i));
                    int value=c.getRed()+c.getGreen()+ c.getBlue();
                    value /=3;
                    if(value>=20)
                        pixels[i][j]=1;
                    else
                        pixels[i][j]=0;


                }
            }
            return pixels;

        } catch (Exception e) {}
        return new  int[0][0];
    }

}
