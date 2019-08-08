
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;
import org.smslib.AGateway;
import org.smslib.GatewayException;
import org.smslib.InboundMessage;
import org.smslib.OutboundMessage;
import org.smslib.SMSLibException;
import org.smslib.Service;
import org.smslib.TimeoutException;
import org.smslib.crypto.AESKey;
import org.smslib.modem.SerialModemGateway;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Admin
 */
public class test2 {

    public void readLastRecievedMessage(final String modem, final String port, final int bitrate, final String donglenum) throws SMSLibException, TimeoutException, GatewayException, IOException, InterruptedException {
        new Thread(new Runnable() {

            public void run() {
                try {
                    List<InboundMessage> msgList;
                    SerialModemGateway gateway = new SerialModemGateway("modem.com4", "COM4", 9600, "", "");
                    gateway.setProtocol(AGateway.Protocols.PDU);
                    gateway.setInbound(true);
                    gateway.setOutbound(true);
                    gateway.setSimPin("0000");
                    String status = Service.getInstance().getServiceStatus().toString();
                    if (status == "STARTED") {
                    } else {
                        Service.getInstance().addGateway(gateway);
                        Service.getInstance().startService();
                    }
                    System.out.println("Modem Information:");
                    System.out.println("  Manufacturer: " + gateway.getManufacturer());
                    System.out.println("  Model: " + gateway.getModel());
                    System.out.println("  Serial No: " + gateway.getSerialNo());
                    System.out.println("  SIM IMSI: " + gateway.getImsi());
                    System.out.println("  Signal Level: " + gateway.getSignalLevel() + " dBm");
                    System.out.println("  Battery Level: " + gateway.getBatteryLevel() + "%");
                    Service.getInstance().getKeyManager().registerKey(donglenum, new AESKey(new SecretKeySpec("0011223344556677".getBytes(), "AES")));
                    msgList = new ArrayList<InboundMessage>();
                    Service.getInstance().readMessages(msgList, InboundMessage.MessageClasses.ALL);
                    //get the last recieved message
                    int i = (msgList.size()) - 1;
                    //print the message
                    System.out.println(msgList.get(i).getText());
                } catch (Exception e) {

                    e.printStackTrace();
                }

            }
        }).start();
    }

    public static void SendMessage(final String modem, final String port, final int bitrate, final String reciepent, final String content) throws Exception {
        new Thread(new Runnable() {

            public void run() {
                try {
                    SerialModemGateway gateway = new SerialModemGateway(modem, port, bitrate, "", "");
                    gateway.setInbound(true);
                    gateway.setOutbound(true);
                    String status = Service.getInstance().getServiceStatus().toString();
                    if (status == "STARTED") {
                    } else {
                        Service.getInstance().addGateway(gateway);
                        Service.getInstance().startService();
                    }
                    System.out.println("Modem Information:");
                    System.out.println("  Manufacturer: " + gateway.getManufacturer());
                    System.out.println("  Model: " + gateway.getModel());
                    System.out.println("  Serial No: " + gateway.getSerialNo());
                    System.out.println("  SIM IMSI: " + gateway.getImsi());
                    System.out.println("  Signal Level: " + gateway.getSignalLevel() + " dBm");
                    System.out.println("  Battery Level: " + gateway.getBatteryLevel() + "%");
                    OutboundMessage msg = new OutboundMessage(reciepent, content);
                    boolean i = Service.getInstance().sendMessage(msg);
                    if (i == false) {
                        JOptionPane.showMessageDialog(null, "Failed to Send Message, \nConfigure Settings \nRestart System \nRetry");
                    } else {
                        JOptionPane.showMessageDialog(null, "Message Sent");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public static void main(String[] args) throws Exception {
        SendMessage("modems@com4", "COM4", 9600, "+ 84945108999", "Xin chào");
    }
}
