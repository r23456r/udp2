package com.bat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

public class ThreadImport {
    private String url="jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false&serverTimezone=GMT";
    private String user="root";
    private String password="";
    public Connection getConnect(){
        Connection con = null;
         try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con= DriverManager.getConnection(url, user,password);
        } catch (Exception e) {
            e.printStackTrace();
        }
         return con;
    }
    public void multiThreadImport( final int ThreadNum){
        final CountDownLatch cdl= new CountDownLatch(ThreadNum);
        long starttime=System.currentTimeMillis();
        for(int k=1;k<=ThreadNum;k++){
            new Thread(() -> {
                Connection con=getConnect();
                try {
                    Statement st=con.createStatement();
                    for(int i=1;i<=80/ThreadNum;i++){
                        String uuid= UUID.randomUUID().toString();
                        st.addBatch("insert into demo_table(a,b) values('"+uuid+"','"+uuid+"')");
                        if(i%500==0){
                            st.executeBatch();
                        }
                    }
                    cdl.countDown();
                } catch (Exception e) {
                }finally{
                    try {
                        con.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        try {
            cdl.await();
            long spendtime=System.currentTimeMillis()-starttime;
            System.out.println( ThreadNum+"个线程花费时间:"+spendtime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception {
        ThreadImport ti=new ThreadImport();
        ti.multiThreadImport(1);
        ti.multiThreadImport(5);
        ti.multiThreadImport(8);
        ti.multiThreadImport(10);
        ti.multiThreadImport(20);
        ti.multiThreadImport(40);
        System.out.println("笔记本CPU数:"+Runtime.getRuntime().availableProcessors());
    }

}