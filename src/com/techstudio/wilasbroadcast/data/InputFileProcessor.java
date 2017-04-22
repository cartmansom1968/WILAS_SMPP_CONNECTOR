package com.techstudio.wilasbroadcast.data;

import com.techstudio.wilasbroadcast.sms.SMSInputParser;
import com.techstudio.wilasbroadcast.sms.SMSMappingFile;

import java.io.*;

public class InputFileProcessor implements InputSource {

  RandomAccessFile inputFile;

  long currentLocation = 0;
  long currentLineNo = 0;

  MessageFactory msgFactory;

  private InputFileProcessor(RandomAccessFile inputFile, String templateFileName, long currentLocation, long currentLineNo) {
    this.inputFile = inputFile;
    this.currentLocation = currentLocation;
    this.currentLineNo = currentLineNo;

    SMSInputParser smsInputParser = new SMSInputParser();
    if (templateFileName != null) {
      SMSMappingFile templateFile = new SMSMappingFile(templateFileName);
      smsInputParser.setTemplateFile(templateFile);
    }
    msgFactory = new MessageFactory(smsInputParser);
  }

  public static InputFileProcessor create(String fileName, String templateFileName, long recoverPt, long currentLineNo) {
    RandomAccessFile inputFile = null;
    try {
      inputFile = new RandomAccessFile(fileName, "r");
      if (recoverPt != 0) {
        inputFile.seek(recoverPt);
      }
    }
    catch (FileNotFoundException e) {
      System.out.println();
      e.printStackTrace();
      return null;
    }
    catch (IOException e) {
      e.printStackTrace();
      return null;
    }

    return new InputFileProcessor(inputFile, templateFileName, recoverPt, currentLineNo);
  }


  public BroadcastRecord getNextMessage() {

    try {
      String line = inputFile.readLine();
      while (line != null) {
        currentLineNo++;
        currentLocation = inputFile.getFilePointer();

        BroadcastRecord msg = msgFactory.createMessage(line, currentLineNo);
        if (msg != null) return msg;
        line = inputFile.readLine();
      }

    }
    catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }




  public static void main(String[] args) throws Exception {


    InputFileProcessor inputFile = InputFileProcessor.create("input/StandardInputFileVariable.txt", "input/IRASFixedSMSTemplate.cfg", 0, 0);

    BroadcastRecord msg = null;

    while ((msg = inputFile.getNextMessage()) != null) {
      System.out.println(msg);
    }



  }

}
