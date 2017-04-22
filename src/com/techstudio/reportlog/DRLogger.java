package com.techstudio.reportlog;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

/**
 * Created by somnath on 15/8/16.
 */
public class DRLogger {
    public static DRLogger instance=null;

    public static String location = null;
    Calendar currentDay;
    int cDay = 0;
    int cMonth = 0;
    int cYear = 0;

    String logFilename;

    FileWriter logFile;

    Hashtable<String,String> internal2ExternalIdMap = null;
    Hashtable<String,String> externalId2StatusMap = null;

    String temp = null;

    String appendZero(int val, int len) {
        String x = ""+val;
        String blank="000000000000";
        return blank.substring(0,len-x.length())+x;
    }

    DRLogger() {
        long currentDateTime = System.currentTimeMillis();

        Date date = new Date(currentDateTime);
        currentDay = Calendar.getInstance();
        currentDay.setTime(date);
        cYear = currentDay.get(Calendar.YEAR);
        cMonth = currentDay.get(Calendar.MONTH)+1;
        cDay = currentDay.get(Calendar.DAY_OF_MONTH);

        internal2ExternalIdMap = new Hashtable<String, String>();
        externalId2StatusMap = new Hashtable<String, String>();

        logFilename = "DeliveryStatusLog"+cYear+appendZero(cMonth,2)+appendZero(cDay,2)+".csv";

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

    public void mapExternalId2InternalId(String externalId, String internalId) {
        synchronized (this) {
            System.out.println("Start mapExternalId2InternalId");
            String status = externalId2StatusMap.remove(externalId);
            if (status == null) {
                System.out.println("Mapping externalId <"+externalId+"> to internalId "+internalId);
                temp = externalId;
                internal2ExternalIdMap.put(externalId, internalId);
            } else {
                if (status.equalsIgnoreCase("UNDELIV")) {
                    log(internalId, System.currentTimeMillis(), 5);
                }
            }
            System.out.println("End mapExternalId2InternalId");
        }
    }

    public void recvdStatus(String externalId,String status, String err) {
        synchronized (this) {
            System.out.println("Start recvdStatus <"+externalId+"> "+internal2ExternalIdMap.size());
            if (temp!=null) {
                if (externalId.equals(temp)) {
                    System.out.println("IT IS EQUAL");
                } else {
                    System.out.println(">>> " + externalId.length() + "  " + temp.length());
                }
            }
            String internalId = internal2ExternalIdMap.remove(externalId);
            if (internalId == null) {
                System.out.println("Saving externalId <" + externalId + "> and status "+status);
                externalId2StatusMap.put(externalId, status);
            } else {
                System.out.println("internalId " + internalId + " status " + status + " err " + err);
                if (status.equalsIgnoreCase("UNDELIV")) {
                    if (err.equalsIgnoreCase("62")) { //No response from SME - No response to delivery attempts
                        log(internalId, System.currentTimeMillis(), 6);
                    } else
                    if (err.equalsIgnoreCase("60")) { //Congestion - Message could not be delivered due to congestion
                        log(internalId, System.currentTimeMillis(), 7);
                    } else
                    if (err.equalsIgnoreCase("7A")) { //Message in flight - with unknown status
                        log(internalId, System.currentTimeMillis(), 8);
                    } else
                    if (err.equalsIgnoreCase("58")) { //Operator blocked delivery of message
                        log(internalId, System.currentTimeMillis(), 9);
                    } else
                    if (err.equalsIgnoreCase("41")) { //Phone not provisioned for SMS or barred
                        log(internalId, System.currentTimeMillis(), 10);
                    } else
                    if (err.equalsIgnoreCase("43")) { //Phone not provisioned for SMS or barred
                        log(internalId, System.currentTimeMillis(), 10);
                    } else
                    if (err.equalsIgnoreCase("4A")) { //Mobile Number does not exist
                        log(internalId, System.currentTimeMillis(), 11);
                    } else
                    if (err.equalsIgnoreCase("45")) { //Phone not reachable
                        log(internalId, System.currentTimeMillis(), 12);
                    } else {
                        log(internalId, System.currentTimeMillis(), 12);
                    }
                }
            }
            System.out.println("End recvdStatus");
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

        logFilename = "DeliveryStatusLog"+cYear+appendZero(cMonth,2)+appendZero(cDay,2)+".csv";

        File file = new File(location+"/"+logFilename);
        System.out.println("Created "+location+"/"+logFilename);
        try {
            if (file.exists()) {
                logFile = new FileWriter(location+"/"+logFilename, true); //the true will append the new data
            } else {
                logFile = new FileWriter(location+"/"+logFilename);
            }
        }  catch(IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }

        internal2ExternalIdMap.clear();
        externalId2StatusMap.clear();
    }

    public boolean dateHasChanged() {
        long currentDateTime = System.currentTimeMillis();
        Date date = new Date(currentDateTime);
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH);

        //System.out.println(cDay +" - "+ day + "   " + cMonth + " - "+ month + "   cYear "+ cYear + "-" + year);
        if (cDay!=day || cMonth!=(month+1) || cYear!=year) {
            return true;
        }
        return false;
    }


    public static DRLogger getInstance() {
        if (instance==null) {
            instance = new DRLogger();
        }
        return instance;
    }


    private synchronized boolean log(String msgId, long timeStamp, int status) {
        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(timeStamp);
        String sDate= sm.format(date);
        try {
            logFile.write(sDate + "," + msgId + "," +status+"\n");
            logFile.flush();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
