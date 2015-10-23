package leapMotion;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.SerialPort;
import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JOptionPane;

public class PortController{
  private OutputStream serialOut;
  private int rate;
  private String comPort;

  /**
   * Construtor of PortController class
   * @param COMport - Porta COM que será utilizada para enviar os dados para o arduino
   * @param rate - Taxa de transferência da porta serial geralmente é 9600
   */
  public PortController(String COMport, int rate) {
    this.comPort = COMport;
    this.rate = rate;
    this.initialize();
  }     
 
  /**
   * Verifies if the connection with the arduino is ok
   */
  private void initialize() {
    try {
      CommPortIdentifier portId = null;
      try {
        portId = CommPortIdentifier.getPortIdentifier(this.comPort);
      }catch (NoSuchPortException npe) { 
        JOptionPane.showMessageDialog(null, "COM Port not found",
                  "ERROR", JOptionPane.PLAIN_MESSAGE);
      }
      SerialPort port = (SerialPort) portId.open("Comunicação serial", this.rate);
      serialOut = port.getOutputStream();
      port.setSerialPortParams(this.rate, 
                               SerialPort.DATABITS_8,
                               SerialPort.STOPBITS_1,
                               SerialPort.PARITY_NONE); 
    }catch (Exception e) {
      e.printStackTrace();
    }
}

  /**
   * Closes the communication with the serial port
   */
  public void close() {
    try {
        serialOut.close();
    }catch (IOException e) {
      JOptionPane.showMessageDialog(null, "Wasn't able to close the communication.",
                "ERROR", JOptionPane.PLAIN_MESSAGE);
    }
  }

  /**
   * @param option - Value to be sent to serial
   */
  public void enviaDados(int option){
    try {
      serialOut.write(option);
    } catch (IOException ex) {
        JOptionPane.showMessageDialog(null, "Wasn't able to send date ",
                "ERROR", JOptionPane.PLAIN_MESSAGE);
    }
  } 
}