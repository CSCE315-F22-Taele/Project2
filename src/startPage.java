import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class startPage implements ActionListener{
    // DEFINING MAIN J OBJECTS USED
    Color primary = new Color(0x2A2A72);
    Color buttonColor = new Color(0xEF3054);
    Font guiFont = new Font("Impact",Font.PLAIN,20);
    JButton server = new JButton("Server");
    JButton manager = new JButton("Manager");
    //Create frame
    JFrame frame = new JFrame();
    startPage(){
    
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,1000);
        frame.setLayout(null);
        frame.setResizable(false);

        manager.setBackground(buttonColor);
        server.setBackground(buttonColor);
        manager.setBounds(0,100, 200, 200);
        server.setBounds(200,100, 200, 200);
        manager.setFont(guiFont);
        server.setFont(guiFont);

        frame.add(manager);
        frame.add(server);

        manager.addActionListener(this);
        server.addActionListener(this);

        frame.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==manager){
            new managerMain();
        }

        if(e.getSource()==server){
            new serverMain();
        }
        
    }
   
}
