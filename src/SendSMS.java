import com.ts.wilasmarketing.messaging.sms.*;
import com.ts.wilasmarketing.messaging.*;
import com.ts.wilasmarketing.base.*;
import com.ts.wilasmarketing.sms.*;
import com.ts.wilasmarketing.*;




public class SendSMS {
    String queueName = "";

    SMSSenderBoxJMSImpl sendBox;
    BaseReceiverBox<SMSMessagingObj> recvBox;
    SMSIntRespReceiverBoxImpl intRecvBox;

    public SendSMS(String queueName, String queueNameMO) {
        try {
            this.queueName = queueName;
            sendBox = new SMSSenderBoxJMSImpl(queueName);
            recvBox = new SMSReceiverBoxImpl(queueNameMO);
            intRecvBox = new SMSIntRespReceiverBoxImpl(Constants.INTERNAL_SMS_RESP_QUEUE);

            System.out.println("Q Started:");
        } catch (Exception e) {
            System.out.println("EXCEPTION LOADING QUEUE " + e);
            System.exit(0);
        }
    }

    public void sendMessage(String msg, String destination, String source) {
        try {
            SMSRecord messageObj = new SMSRecord(
                    100,
                    1,
                    source,
                    destination,
                    msg,
                    "TEST");
            Campaign campaign = new Campaign("IRAS","IRAS1","IRAS");
            messageObj.setCampaign(campaign);
            sendBox.send(messageObj);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void recvMessage() {
        try {
            SMSMessagingObj msg = recvBox.receive();

            if (msg!=null) {
                String tpoa = msg.getSenderAddress();
                String msisdn = msg.getDestinationAddress();
                String msgBody = msg.getBody();
                System.out.println("TPOA - "+ tpoa + " DEST - " + msisdn + " MSG - "+msgBody);
            } else {
                System.out.println("No message received.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void recvInternalResponse() {
        try {
            SMSInternalResponseObj msg = intRecvBox.receive();

            if (msg!=null) {
                String msgCode = msg.getMessageCode();
                String status = msg.getStatus();
                String campaignId = msg.getCampaignID();
                System.out.println("CampaignId - "+ campaignId + " MsgCode - " + msgCode + " Status - "+status);
            } else {
                System.out.println("No response received.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String arg[]) {
        SendSMS sendObj = new SendSMS("SPMT","SPMO");
        if (arg[0].equalsIgnoreCase("send")) {
            if (arg.length>=3) {
                int k = 1;
                if (arg.length==4) {
                    k = Integer.parseInt(arg[3]);
                }
                for (int i=0; i<k; ++i) {
                    sendObj.sendMessage("Testing som@synptify $100 - "+i,arg[1],arg[2]);
                    //sendObj.sendMessage(k+" Hello Lek, Daniel@WILAS.COM and $100 Sylvester, Unicode problem fixed 谢谢. We now auto detect Unicode for a message - in TMMS we need to set it to Unicode. 明天见",arg[1],arg[2]);
                    //sendObj.sendMessage("Test message "+i, arg[1], arg[2]);
                }
                System.out.println("Sent "+k+" !!!!!!!");
                for (int j=0; j<k; ++j) {
                    sendObj.recvInternalResponse();
                }
            } else {
                System.out.println("Command incorrect.");
            }
        } else {
            sendObj.recvMessage();
        }
        System.out.println("Waiting to exit");
        System.exit(0);
    }
}
