package com.techstudio.reportlog;

import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.io.*;

/**
 * Created by somnath on 25/4/16.
 */
public class DataLogger {
    public static DataLogger instance=null;

    public static String location = null;
    Calendar currentDay;
    int cDay = 0;
    int cMonth = 0;
    int cYear = 0;

    String logFilename;

    FileWriter logFile;


    String appendZero(int val, int len) {
        String x = ""+val;
        String blank="000000000000";
        return blank.substring(0,len-x.length())+x;
    }

    DataLogger() {
        long currentDateTime = System.currentTimeMillis();

        Date date = new Date(currentDateTime);
        currentDay = Calendar.getInstance();
        currentDay.setTime(date);
        cYear = currentDay.get(Calendar.YEAR);
        cMonth = currentDay.get(Calendar.MONTH)+1;
        cDay = currentDay.get(Calendar.DAY_OF_MONTH);

        logFilename = "BroadcastLog"+cYear+appendZero(cMonth,2)+appendZero(cDay,2)+".csv";

        File file = new File(location+"/"+logFilename);

        try {
            if (file.exists()) {
                logFile = new FileWriter(location+"/"+logFilename, true); //the true will append the new data
            } else {
                logFile = new FileWriter(location+"/"+logFilename);
            }
        }  catch(IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }

    public void reinit() {
        try {
            if (logFile != null) {
                logFile.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        long currentDateTime = System.currentTimeMillis();

        Date date = new Date(currentDateTime);
        currentDay = Calendar.getInstance();
        currentDay.setTime(date);
        cYear = currentDay.get(Calendar.YEAR);
        cMonth = currentDay.get(Calendar.MONTH)+1;
        cDay = currentDay.get(Calendar.DAY_OF_MONTH);

        logFilename = "BroadcastLog"+cYear+appendZero(cMonth,2)+appendZero(cDay,2)+".csv";

        File file = new File(location+"/"+logFilename);

        try {
            if (file.exists()) {
                logFile = new FileWriter(location+"/"+logFilename, true); //the true will append the new data
            } else {
                logFile = new FileWriter(location+"/"+logFilename);
            }
        }  catch(IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }

    public boolean dateHasChanged() {
        long currentDateTime = System.currentTimeMillis();
        Date date = new Date(currentDateTime);
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH);

        if (cDay!=day || cMonth!=(month+1) || cYear!=year) {
            return true;
        }
        return false;
    }


    public static DataLogger getInstance() {
        if (instance==null) {
            instance = new DataLogger();
        }
        return instance;
    }


    public boolean log(String msgId, long timeStamp, String msisdn, String source, String msgBody, int noOfParts, String campaignId, String campaignInstance, long broadcastListIndex, String msgCode, int status) {
        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(timeStamp);
        String sDate= sm.format(date);
        try {
            //logFile.write(sDate + ",\"" + msisdn + "\",\"" + source+"\",\"" +msgBody+"\","+noOfParts+",\""+campaignId+"\",\""+campaignInstance+"\","+broadcastListIndex+",\""+msgCode+"\","+status+"\n");
            logFile.write(sDate + "," + msgId + ","+msisdn + "," + source+","+noOfParts+","+campaignId+","+campaignInstance+","+broadcastListIndex+","+msgCode+","+status+"\n");
            logFile.flush();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
