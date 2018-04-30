import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JackFrame extends JFrame implements ActionListener {

   JPanel jp = new JPanel();
   
   JLabel label = new JLabel();                 // these create labels     
   JLabel label2 = new JLabel();
   JLabel label3 = new JLabel();
   JLabel label4 = new JLabel();                // and in this case output
   
   JTextField tf = new JTextField("1", 20);     // create the input boxes for the card values, 
   JTextField tf2 = new JTextField("1", 20);    // 1 (Ace) is entered by default in each
   JTextField tf3 = new JTextField("1", 20);
   
   JButton deal = new JButton("Set starting hands (then back to console)");
   JButton hit = new JButton("Hit");
   JButton stay = new JButton("Stay");
   
   int[] player = {0, 0, 0};     // Places to put the cards, needs work
   int[] dealer = {0, 0, 0};

   public static void main(String[] args) {
      new JackFrame();
  }

   public JackFrame() {
      super("BlackJack");        //call to super creates the frame, title: BlackJack
      setVisible(true);          //set up the frame
      setResizable(true);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      jp.setLayout(new GridLayout(5,2)); //rows, columns
      setSize(800, 400);
      
      label.setText("Enter value of first card"); 
      label2.setText("Enter value of second card");
      label3.setText("Enter the dealer's showing card");
      label4.setText("Description of Player and Dealer hands");
      
      jp.add(label);             //alternates adding labels and text fields, then a deal
      jp.add(tf);
      jp.add(label2);
      jp.add(tf2);
      jp.add(label3);
      jp.add(tf3);
      jp.add(label4);
      jp.add(deal);
      jp.add(hit);
      jp.add(stay);
      
      deal.addActionListener(this);    // ActionListeners for the buttons
      hit.addActionListener(this);
      stay.addActionListener(this);
      
      add(jp);                   //adds the panel to the JackFrame
          
   }
   
      
   public int sumArray(int[] a) {     //couldn't find a decent array summing method so I wrote one
      int sum = 0;
      for(int i=0; i<a.length; i++) {
         sum = sum + a[i];
      }
      return sum;
   }
   
   public void actionPerformed(ActionEvent e) {
     String name = e.getActionCommand();
     
     if (name.equals("Set starting hands (then back to console)")) {
         player[0] = Integer.parseInt(tf.getText());
         player[1] = Integer.parseInt(tf2.getText());
         dealer[0] = Integer.parseInt(tf3.getText()); 
         label4.setText("You have " + sumArray(player) + ", and the dealer is showing " + dealer[0] + ".");

     }
     
     else if (name.equals("Hit")) {
        System.out.println("Hit has been pressed!");
     }
     
     else if (name.equals("Stay")) {
        System.out.println("You have chosen to stay!");
     }
  }
   
   public int getPlayerCard(int i) {  // these two methods are useful for grabbing specific cards
         return player[i];
   }
   
   public int getDealerCard(int i) {
      return player[i];
   }
   
   public int getPlayerSum() {      // but these two are probably more useful
      return sumArray(player);
   }
   
   public int getDealerSum() {
      return sumArray(dealer);
   }
   
}