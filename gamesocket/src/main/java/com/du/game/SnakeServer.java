package com.du.game;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class SnakeServer {
    int port;
    //List<Socket> clients;
    Map<String,Socket>  clients ;

    ServerSocket server;
    private  static  List<Room> rooms = new ArrayList();


    public static void main(String[] args) {
        new SnakeServer();
    }

    public SnakeServer() {
        try {

            port = 6060;
            //clients = new ArrayList<Socket>();
            clients  = new ConcurrentHashMap();
            server = new ServerSocket(port);

            while (true) {
                Socket socket = server.accept();
                //clients.add(socket);
                Mythread mythread = new Mythread(socket);
                mythread.start();
            }
        } catch (Exception ex) {
        }
    }

class Mythread extends Thread {
    Socket ssocket;
    private BufferedReader br;
    private PrintWriter pw;
    public String msg;

    public Mythread(Socket s) {
        ssocket = s;
    }

    @Override
    public void run() {

        try {
            br = new BufferedReader(new InputStreamReader(ssocket.getInputStream()));
            msg = "欢迎【" + ssocket.getInetAddress() + "】进入聊天室！当前聊天室有【"+ clients.size() + "】人";
            System.out.println(msg);

            while ((msg = br.readLine()) != null) {
                System.out.println(msg+"=---");
                String[] m = msg.split("&&");
                if(m.length>=2){
                    if(m[0].equals("login")){
                        System.out.println(msg);
                        Socket socket = clients.get(m[1]);
                        if(socket != null){
                            msg = m[0]+"&&0&&昵称重复";
                        }else {
                            clients.put(m[1],ssocket);
                            msg = "欢迎"+m[1]+"【" + ssocket.getInetAddress() + "】进入游戏！当前游戏有【" + clients.size() + "】人";
                            sendMsg();
                            msg = m[0]+"&&1&&成功";
                        }
                        sendMsg(ssocket);
                    }else if(Constant.DESTROY.equals((m[0]))){
                        String key = getSocketName();
                        if(!key.equals("")){
                            clients.remove(key,ssocket);
                        }
                    }else if(Constant.CREATE.equals(m[0])){
                        Room room = new Room();
                        room.setPwd(m[1]);
                        room.setOne(ssocket);
                        room.setOneName(getSocketName());
                        room.setFlag(false);
                        room.setOneDirection(DIRECTION);
                        room.setState(1);
                        rooms.add(room);
                        msg = m[0]+"&&1&&成功&&"+room.getOneName()+"加入房间";
                        sendMsg(ssocket);
                    }else if(Constant.WH.equals(m[0])){
                        Room room = getRoom(ssocket);
                        if(room ==null){
                            msg = m[0]+"&&0&&失败&&未找到房间数据";
                        }else {
                            room.setWidth(Integer.valueOf(m[1]));
                            room.setWidth(Integer.valueOf(m[2]));
                            createOne(room);
                        }
                    } else if(Constant.JION.equals(m[0])){
                        List<Room> rs = rooms.stream().filter(a -> a.getState() == 1 && a.getPwd().equals(m[1])).collect(Collectors.toList());
                        Room room;
                        if(rs.size() > 0){
                            room = rs.get(0);
                            if(room.getState() == 2){
                                msg = m[0]+"&&0&&房间被占用";
                            }else {
                                Socket tow  = room.getTow();
                                if (tow == null) {
                                    room.setTow(ssocket);
                                    room.setTowName(getSocketName());
                                    room.setState(2);
                                    room.setFlag(true);
                                    msg = Constant.JIONADD+"&&1&&成功&&"+room.getTowName()+"加入房间";
                                    sendMsg(room.getOne());
                                    msg = m[0]+"&&1&&成功&&"+room.getOneName()+"加入房间&&"+room.getTowName()+"加入房间";
                                    sendMsg(ssocket);
                                    createTow(room);
                                }else {
                                    msg = m[0]+"&&0&&房间被占用";
                                    sendMsg(ssocket);
                                }
                            }
                        }else {
                            msg = m[0]+"&&0&&密码输入错误";
                            sendMsg(ssocket);
                        }

                    }
                }


            }
        } catch (Exception ex) {

        }
    }

    private void createTow(Room room) {
        room.setTowPosition(initTow());
        String msg = Constant.ROUTE+"&&1&&"+JSONUtil.toJson(room.getOnePosition())+"&&2&&"+JSONUtil.toJson(room.getTowPosition())+"&&3&&"+JSONUtil.toJson(room.getSpots());
        sendMsg(room.getOne(),msg);
        sendMsg(room.getTow(),msg);
        CoordinateThread  coordinateThread =  new  CoordinateThread(room);
        coordinateThread.start();
    }

    private void createOne(Room room) {
        room.setOnePosition(initOne());
        room.setSpots(initSpots(room.getWidth(),room.getHeight()));
        String msg = Constant.ROUTE+"&&1&&"+JSONUtil.toJson(room.getOnePosition())+"&&2&&null&&3&&"+JSONUtil.toJson(room.getSpots());
        sendMsg(room.getOne(),msg);
    }

    private String getSocketName() {
        String name = "";
        for (Map.Entry<String,Socket> entry:clients.entrySet()) {
            if(entry.getValue().getPort() == ssocket.getPort()){
                name = entry.getKey();
                break;
            }
        }
        return name;
    }

    private Room getRoom(Socket socket){
        for(Room room:rooms){
            if(room.getOne().getPort() == socket.getPort()){
                return room;
            }
        }
        return null;
    }

    public void sendMsg() {
        try {
            System.out.println(msg);
            for (int i = clients.size() - 1; i >= 0; i--) {
                pw = new PrintWriter(clients.get(i).getOutputStream(), true);
                pw.println(msg);
                pw.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void sendMsg(Socket s) {
        try {
            System.out.println(msg);
            pw = new PrintWriter(s.getOutputStream(), true);
            pw.println(msg);
            pw.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void sendMsg(Socket s,String msg) {
        PrintWriter pw;
        try {

            System.out.println(msg);

            pw = new PrintWriter(s.getOutputStream(), true);
            pw.println(msg);
            pw.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}






    class CoordinateThread extends Thread{
            Room room;

            public CoordinateThread(Room room){
                this.room =room;
            }

            @Override
            public void run() {
                try {
                    while (room.isFlag() && room.getState() == 2){
                      String msg = move(room);
                      sendMsg(room.getTow(),msg);
                      sendMsg(room.getOne(),msg);
                        Thread.sleep(150);
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }

        private String move(Room room) {
            return Constant.ROUTE+"&&1&&"+oneMove(room)+"&&2&&"+towMove(room)+"&&3&&"+room.getSpots();
        }

        private String oneMove(Room room) {
                int[][] coordinate = room.getOnePosition();
            int[] velMax = coordinate[coordinate.length-1];
            int[] vel = new int[4];
            switch (room.getOneDirection()){
                case 1://右
                    vel[0] = velMax[0]+STEP;
                    vel[1] = velMax[1];
                    vel[2] = velMax[2]+STEP;
                    vel[3] = velMax[3];
                    break;
                case 2://左
                    vel[0] = velMax[0]-STEP;
                    vel[1] = velMax[1];
                    vel[2] = velMax[2]-STEP;
                    vel[3] = velMax[3];
                    break;
                case 3://上
                    vel[0] = velMax[0];
                    vel[1] = velMax[1]-STEP;
                    vel[2] = velMax[2];
                    vel[3] = velMax[3]-STEP;
                    break;
                case 4://下
                    vel[0] = velMax[0];
                    vel[1] = velMax[1]+STEP;
                    vel[2] = velMax[2];
                    vel[3] = velMax[3]+STEP;
                    break;
                default:return "";
            }

            for(int x=0;x<coordinate.length-1;x++){
                coordinate[x] = coordinate[x+1];
            }
            coordinate[coordinate.length-1] = vel;

            room.setOnePosition(coordinate);

            return JSONUtil.toJson(coordinate);
        }

        private String towMove(Room room) {
            int[][] coordinate = room.getTowPosition();
            int[] velMax = coordinate[coordinate.length-1];
            int[] vel = new int[4];
            switch (room.getTowDirection()){
                case 1://右
                    vel[0] = velMax[0]+STEP;
                    vel[1] = velMax[1];
                    vel[2] = velMax[2]+STEP;
                    vel[3] = velMax[3];
                    break;
                case 2://左
                    vel[0] = velMax[0]-STEP;
                    vel[1] = velMax[1];
                    vel[2] = velMax[2]-STEP;
                    vel[3] = velMax[3];
                    break;
                case 3://上
                    vel[0] = velMax[0];
                    vel[1] = velMax[1]-STEP;
                    vel[2] = velMax[2];
                    vel[3] = velMax[3]-STEP;
                    break;
                case 4://下
                    vel[0] = velMax[0];
                    vel[1] = velMax[1]+STEP;
                    vel[2] = velMax[2];
                    vel[3] = velMax[3]+STEP;
                    break;
                default:return "";
            }

            for(int x=0;x<coordinate.length-1;x++){
                coordinate[x] = coordinate[x+1];
            }
            coordinate[coordinate.length-1] = vel;

            room.setOnePosition(coordinate);

            return JSONUtil.toJson(coordinate);
        }

        public void sendMsg(Socket s,String msg) {
            PrintWriter pw;
            try {
                System.out.println(msg);
                pw = new PrintWriter(s.getOutputStream(), true);
                pw.println(msg);
                pw.flush();
            } catch (Exception ex) {
            }
        }
    }



    private final static int COUNT = 3;
    private final static int STEP = 40;
    private final static int DIRECTION = 1;
    public int[][] initOne(){
        int[][]  coordinate = new int[COUNT][4];
        for(int x=0;x<coordinate.length;x++){
            coordinate[x][0] = 80+(STEP*x);
            coordinate[x][1] = 80;
            coordinate[x][2] = 120+(STEP*x);
            coordinate[x][3] = 120;
        }
        return coordinate;
    }
    public int[][] initTow(){
        int[][]  coordinate = new int[COUNT][4];
        for(int x=0;x<coordinate.length;x++){
            coordinate[x][0] = 80+(STEP*x);
            coordinate[x][1] = 160;
            coordinate[x][2] = 120+(STEP*x);
            coordinate[x][3] = 200;
        }
        return coordinate;
    }
    public int[][] initSpots(int w,int h){
        int[][]  spots = new int[2][4];
        int x = randomX(w);
        int y = randomY(h,x);
        spots[0] =  new int[]{x,y,x+STEP,y+STEP};
        int x1 = randomX(w);
        int y1 = randomY(h,x1);
        spots[1] =  new int[]{x1,y1,x1+STEP,y1+STEP};
        return spots;
    }

    private int randomX(int w){
        while (true){
            int x = (int)(Math.random()*(w-STEP));
            if(x%STEP == 0 ){
                return x;
            }
        }
    }

    private int randomY(int h,int x){
        while (true){
            int y = (int)(Math.random()*(h-STEP));
            if(y%STEP == 0 && y>=STEP && y != x){
                return y;
            }
        }
    }
}
