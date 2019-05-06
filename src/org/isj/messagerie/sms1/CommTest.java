package org.isj.messagerie.sms1;

import com.sun.comm.Win32Driver;
import gnu.io.RXTXCommDriver;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Formatter;
import javax.comm.CommDriver;
import javax.swing.JOptionPane;
import org.smslib.helper.CommPortIdentifier;
import org.smslib.helper.SerialPort;

public class CommTest {

    private final String _NO_DEVICE_FOUND = "  no device found";
    private final static Formatter _formatter = new Formatter(System.out);
    private CommPortIdentifier portId;
    private Enumeration<CommPortIdentifier> portList;
    private int bauds[] = {9600, 14400, 19200, 28800, 33600, 38400, 56000, 57600, 115200};
    // port debitSupport�
    private String[] modemInfo = {null, null};

    /**
     * Wrapper around {@link CommPortIdentifier#getPortIdentifiers()} to be
     * avoid unchecked warnings.
     */
    private static Enumeration<CommPortIdentifier> getCleanPortIdentifiers() {
        return CommPortIdentifier.getPortIdentifiers();
    }

    public Object getPort() {

        System.out.println("\nInitialisation des driver...");
//        String drivername = "com.sun.comm.Win32Driver";
//        try {
//            CommDriver driver = (CommDriver) Class.forName(drivername).newInstance();
//            driver.initialize();  //error occured
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        //initialisation du driver
        try {
            System.setSecurityManager(null);
            Win32Driver w32Driver = new Win32Driver();
            w32Driver.initialize();
            RXTXCommDriver rXTXCommDriver = new RXTXCommDriver();
            rXTXCommDriver.initialize();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Erreur lors de l'initialisation des driver...","Erreur",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        System.out.println("\nSearching for devices...");
        portList = getCleanPortIdentifiers();
        //System.out.println("\n la valeur de portlist est..."+portList.nextElement());
        int index = 1;
        boolean trouve = false;
        while (portList.hasMoreElements()) {
//            System.out.println("\n passage numero " + index++);
//            System.out.println("\nSearching for devices...");
            portId = portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                _formatter.format("%nFound port: %-5s%n", portId.getName());
                for (int i = 0; i < bauds.length; i++) {
                    SerialPort serialPort = null;
                    _formatter.format("       Trying at %6d...", bauds[i]);

                    try {
                        InputStream inStream;
                        OutputStream outStream;
                        int c;
                        String response;
                        serialPort = portId.open("SMSLibCommTester", 1971);
                        serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN);
                        serialPort.setSerialPortParams(bauds[i], SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                        inStream = serialPort.getInputStream();
                        outStream = serialPort.getOutputStream();
                        serialPort.enableReceiveTimeout(1000);
                        c = inStream.read();
                        while (c != -1) {
                            c = inStream.read();
                        }
                        outStream.write('A');
                        outStream.write('T');
                        outStream.write('\r');
                        Thread.sleep(1000);
                        response = "";
                        StringBuilder sb = new StringBuilder();
                        c = inStream.read();
                        while (c != -1) {
                            sb.append((char) c);
                            c = inStream.read();
                        }
                        response = sb.toString();
                        if (response.indexOf("OK") >= 0) {
                            try {
                                System.out.print("  Getting Info...");
                                outStream.write('A');
                                outStream.write('T');
                                outStream.write('+');
                                outStream.write('C');
                                outStream.write('G');
                                outStream.write('M');
                                outStream.write('M');
                                outStream.write('\r');
                                response = "";
                                c = inStream.read();
                                while (c != -1) {
                                    response += (char) c;
                                    c = inStream.read();
                                }
                                System.out.println(" Found: " + response.replaceAll("\\s+OK\\s+", "").replaceAll("\n", "").replaceAll("\r", ""));
                                modemInfo[0] = portId.getName();
                                modemInfo[1] = Integer.toString(bauds[i]);
                                trouve = true;
                                break;
                            } catch (Exception e) {
                                System.out.println(_NO_DEVICE_FOUND);
                            }
                        } else {
                            System.out.println(_NO_DEVICE_FOUND);
                        }
                    } catch (Exception e) {
                        System.out.print(_NO_DEVICE_FOUND);
                        Throwable cause = e;
                        while (cause.getCause() != null) {
                            cause = cause.getCause();
                        }
                        System.out.println(" (" + cause.getMessage() + ")");
                    } finally {
                        if (serialPort != null) {
                            serialPort.close();
                        }
                    }
                }
                if (trouve) {
                    break;
                }
            }
        }
        return modemInfo;
    }
}
