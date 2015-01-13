import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class NotificationWindow extends JFrame implements ActionListener
{
   JPanel panel = new JPanel();
   JTextArea label = new JTextArea(1, 50);
   JButton exit = new JButton("Okay");

   NotificationWindow( String title, String message )
   {
      super( title );

      //Sets layout for main panel
      panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

      label.setText(message);       //Adds the desired message to the TextArea
      label.setEditable(false);             //Sets editable to false so the user cannot change the message whilst in the game
      label.setBackground(null);            //Sets the background to null so it looks like the message is part of the frame
      label.setLineWrap(true);              //In case the message goes beyond the size of the frame the rest of the message is pushed below
      label.setWrapStyleWord(true);

      //Adds components to the main panel
      panel.add(label);
      panel.add(exit);

      //Aligns components in the center
      exit.setAlignmentX(Component.CENTER_ALIGNMENT);

      //Adds panel to the frame
      add( panel );

      //Action Listener for the button
      exit.addActionListener(this);

      //Set operations
      setSize( 400, 175 );
      setVisible(true);
      setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
      setLocationRelativeTo(null);

   }
   /**
    * Action Performed method that disposes of the notification window
    */
   public void actionPerformed(ActionEvent evt) {

      dispose();

   }

}
