
import java.io.IOException;
import org.smslib.GatewayException;
import org.smslib.Library;
import org.smslib.OutboundMessage;
import org.smslib.SMSLibException;
import org.smslib.Service;
import org.smslib.TimeoutException;
import org.smslib.modem.SerialModemGateway;

public class test {

    //test2 tt = new test2();

    public static void main(String[] args) throws GatewayException, SMSLibException, TimeoutException, IOException, InterruptedException{
        //tt.SendMessage("modem.com4", "COM4", 9600, "+ 84945108999", "Xin chào");
        System.out.println("Example: Send message from a serial gsm modem.");
        System.out.println(Library.getLibraryDescription());
        System.out.println("Version: " + Library.getLibraryVersion());
//		SerialModemGateway gateway = new SerialModemGateway("", "COM5", 9600, "", "");
        SerialModemGateway gateway = new SerialModemGateway("modem@com4", "COM4", 14400, null, null);
//		SerialModemGateway gateway = new SerialModemGateway(id, comPort, baudRate, manufacturer, model)
        gateway.setInbound(true);
        gateway.setOutbound(true);
        Service.getInstance().addGateway(gateway);
        Service.getInstance().startService();
        System.out.println();
        System.out.println("Modem Information:");
        System.out.println("  Manufacturer: " + gateway.getManufacturer());
        System.out.println("  Model: " + gateway.getModel());
        System.out.println("  Serial No: " + gateway.getSerialNo());
        System.out.println("  SIM IMSI: " + gateway.getImsi());
        System.out.println("  Signal Level: " + gateway.getSignalLevel() + " dBm");
        System.out.println("  Battery Level: " + gateway.getBatteryLevel() + "%");
        System.out.println();
        OutboundMessage msg = new OutboundMessage("+84945108999", "Hello from SMSLib!");
        Service.getInstance().sendMessage(msg);
        System.out.println(msg);
        System.out.println("Now Sleeping - Hit <enter> to terminate.");
        System.in.read();
        Service.getInstance().stopService();
    }

}
