import java.util.Scanner;
import java.util.HashMap;

public class GameDriver{
   
   static Game game;
   
   public static void main(String[] args){ //Start game with no values (prob of winning)
      game = new Game(6);
      
      //TODO adjust depth
      double[] retArray = game.getProb(10);
      System.out.println(retArray[0]);
      System.out.println(retArray[1]);
   }
   
   
   
   public static void main2(String[] args){ //Input 1 value for player
   
      game = new Game(100000);
      int[] startHandPlayer = {0,0};
      int startHandDealer = 0;
      boolean handActive = true;
      int newCard = 0;
      Scanner sc=new Scanner(System.in);
      
      while(true){
         System.out.println("Your starting hand:");
         int p = sc.nextInt();
         if(p > 11){
            game.addToPlayersHand(p-10);
            game.addToPlayersHand(10);
         }
         else if(p == 11){
            game.addToPlayersHand(5);
            game.addToPlayersHand(6);
         }
         else if(p == 6){
            game.addToPlayersHand(2);
            game.addToPlayersHand(2);
         }
         else{
            game.addToPlayersHand(p-3);
            game.addToPlayersHand(3);
         }
         
         System.out.println("The Dealer's starting hand:");
         int d = sc.nextInt();
         game.addToDealersHand(d);
         
         double[] res = game.getProb(0);
         if(res[1] < 1.5) System.out.println("Hit - Prob: " + res[0]);
         else if(res[1] < 2.5) System.out.println("Stay - Prob: " + res[0]);
         else if(res[1] < 3.5) System.out.println("Double - Prob: " + res[0]);
         else if(res[1] < 4.5) System.out.println("Split - Prob: " + res[0]);
         else if(res[1] < 5.5) System.out.println("Surrender - Prob: " + res[0]);
         else System.out.println("Error - Prob: " + res[0]);
         
         game = new Game(100000);
      }
   }
   
   
   
   
   
   
   
   
   
   public static void main3(String[] args){ //Loop over top values (No ace)
   
      game = new Game(100000);
      int[] startHandPlayer = {0,0};
      int startHandDealer = 0;
      boolean handActive = true;
      int newCard = 0;
      Scanner sc=new Scanner(System.in);
      
      // while(true){
      for(int p = 8; p<18; p++){
      for(int d = 2; d<11; d++){
         System.out.println("P: " + p);
         // int p = sc.nextInt();
         if(p > 11){
            game.addToPlayersHand(p-10);
            game.addToPlayersHand(10);
         }
         else if(p == 11){
            game.addToPlayersHand(5);
            game.addToPlayersHand(6);
         }
         else if(p == 6){
            game.addToPlayersHand(2);
            game.addToPlayersHand(2);
         }
         else{
            game.addToPlayersHand(p-3);
            game.addToPlayersHand(3);
         }
         
         System.out.println("D: " + d);
         //int d = sc.nextInt();
         game.addToDealersHand(d);
         
         double[] res = game.getProb(0);
         if(res[1] < 1.5) System.out.println("Hit - Prob: " + res[0]);
         else if(res[1] < 2.5) System.out.println("Stay - Prob: " + res[0]);
         else if(res[1] < 3.5) System.out.println("Double - Prob: " + res[0]);
         else if(res[1] < 4.5) System.out.println("Split - Prob: " + res[0]);
         else if(res[1] < 5.5) System.out.println("Surrender - Prob: " + res[0]);
         else System.out.println("Error - Prob: " + res[0]);
         
         game = new Game(100000);
         System.out.println();
      }
      }
   }
   
   
   
   
   public static void main32(String[] args){ //Loop over top values against ace
   
      game = new Game(100000);
      int[] startHandPlayer = {0,0};
      int startHandDealer = 0;
      boolean handActive = true;
      int newCard = 0;
      Scanner sc=new Scanner(System.in);
      
      // while(true){
      for(int p = 8; p<18; p++){
      for(int d = 1; d<2; d++){
         System.out.println("P: " + p);
         // int p = sc.nextInt();
         if(p > 11){
            game.addToPlayersHand(p-10);
            game.addToPlayersHand(10);
         }
         else if(p == 11){
            game.addToPlayersHand(5);
            game.addToPlayersHand(6);
         }
         else if(p == 6){
            game.addToPlayersHand(2);
            game.addToPlayersHand(2);
         }
         else{
            game.addToPlayersHand(p-3);
            game.addToPlayersHand(3);
         }
         
         System.out.println("D: " + d);
         //int d = sc.nextInt();
         game.addToDealersHand(d);
         
         double[] res = game.getProb(0);
         if(res[1] < 1.5) System.out.println("Hit - Prob: " + res[0]);
         else if(res[1] < 2.5) System.out.println("Stay - Prob: " + res[0]);
         else if(res[1] < 3.5) System.out.println("Double - Prob: " + res[0]);
         else if(res[1] < 4.5) System.out.println("Split - Prob: " + res[0]);
         else if(res[1] < 5.5) System.out.println("Surrender - Prob: " + res[0]);
         else System.out.println("Error - Prob: " + res[0]);
         
         game = new Game(100000);
         System.out.println();
      }
      }
   }


   
   
   
   
   
      public static void main4(String[] args){ //Loop over card with ace
   
      game = new Game(100000);
      int[] startHandPlayer = {0,0};
      int startHandDealer = 0;
      boolean handActive = true;
      int newCard = 0;
      Scanner sc=new Scanner(System.in);
      
      // while(true){
      for(int p = 2; p<=8; p++){
      for(int d = 2; d<11; d++){
         System.out.println("P: " + p);
         // int p = sc.nextInt();
        
            game.addToPlayersHand(1);
            game.addToPlayersHand(p);
         
         System.out.println("D: " + d);
         //int d = sc.nextInt();
         game.addToDealersHand(d);
         
         double[] res = game.getProb(0);
         if(res[1] < 1.5) System.out.println("Hit - Prob: " + res[0]);
         else if(res[1] < 2.5) System.out.println("Stay - Prob: " + res[0]);
         else if(res[1] < 3.5) System.out.println("Double - Prob: " + res[0]);
         else if(res[1] < 4.5) System.out.println("Split - Prob: " + res[0]);
         else if(res[1] < 5.5) System.out.println("Surrender - Prob: " + res[0]);
         else System.out.println("Error - Prob: " + res[0]);
         
         game = new Game(100000);
      }
      }
   }
   
   
   public static void main42(String[] args){ //Loop over card with ace vs ace
   
      game = new Game(100000);
      int[] startHandPlayer = {0,0};
      int startHandDealer = 0;
      boolean handActive = true;
      int newCard = 0;
      Scanner sc=new Scanner(System.in);
      
      // while(true){
      for(int p = 2; p<=8; p++){
      for(int d = 1; d<2; d++){
         System.out.println("P: " + p);
         // int p = sc.nextInt();
        
            game.addToPlayersHand(1);
            game.addToPlayersHand(p);
         
         System.out.println("D: " + d);
         //int d = sc.nextInt();
         game.addToDealersHand(d);
         
         double[] res = game.getProb(0);
         if(res[1] < 1.5) System.out.println("Hit - Prob: " + res[0]);
         else if(res[1] < 2.5) System.out.println("Stay - Prob: " + res[0]);
         else if(res[1] < 3.5) System.out.println("Double - Prob: " + res[0]);
         else if(res[1] < 4.5) System.out.println("Split - Prob: " + res[0]);
         else if(res[1] < 5.5) System.out.println("Surrender - Prob: " + res[0]);
         else System.out.println("Error - Prob: " + res[0]);
         
         game = new Game(100000);
      }
      }
   }
   
   
   public static void main5(String[] args){ //Loop over for split
   
      game = new Game(100000);
      int[] startHandPlayer = {0,0};
      int startHandDealer = 0;
      boolean handActive = true;
      int newCard = 0;
      Scanner sc=new Scanner(System.in);
      
      // while(true){
      for(int p = 1; p<=10; p++){
      for(int d = 1; d<=10; d++){
         System.out.println("P: " + p);
         // int p = sc.nextInt();
        
            game.addToPlayersHand(p);
            game.addToPlayersHand(p);
         
         System.out.println("D: " + d);
         //int d = sc.nextInt();
         game.addToDealersHand(d);
         
         double[] res = game.getProb(0);
         if(res[1] < 1.5) System.out.println("Hit - Prob: " + res[0]);
         else if(res[1] < 2.5) System.out.println("Stay - Prob: " + res[0]);
         else if(res[1] < 3.5) System.out.println("Double - Prob: " + res[0]);
         else if(res[1] < 4.5) System.out.println("Split - Prob: " + res[0]);
         else if(res[1] < 5.5) System.out.println("Surrender - Prob: " + res[0]);
         else System.out.println("Error - Prob: " + res[0]);
         
         game = new Game(100000);
      }
      }
   }

   
   
   
   
   
   
      
   public static void mainNormal(String[] args){ //Full Driver
   
      game = new Game(6);
      int[] startHandPlayer = {0,0};
      int startHandDealer = 0;
      boolean handActive = true;
      int newCard = 0;
      Scanner sc=new Scanner(System.in);
      
      while(true){
         System.out.println("Your starting hand:");
         System.out.println("Ace = 1, 2 = 2, ...,  King = 13");
         System.out.println("Enter first card value");
         startHandPlayer[0] = sc.nextInt();
         System.out.println("Enter second card value");
         startHandPlayer[1] = sc.nextInt();
         game.addToPlayersHand(startHandPlayer[0], startHandPlayer[1]);
         System.out.println("The Dealer's starting hand:");
         System.out.println("Ace = 1, 2 = 2, ...,  King = 13");
         System.out.println("Enter the Dealer's visible card value");
         startHandDealer = sc.nextInt();
         game.addToDealersHand(startHandDealer);
         System.out.println("Your starting hand is: " + startHandPlayer[0] + " , " + startHandPlayer[1] + ".");
         System.out.println("The Dealer's visible card is: " + startHandDealer + ".");
         
         double[] res = game.getProb(0);
         if(res[1] < 1.5) System.out.println("Hit - Prob: " + res[0]);
         else if(res[1] < 2.5) System.out.println("Stay - Prob: " + res[0]);
         else if(res[1] < 3.5) System.out.println("Double - Prob: " + res[0]);
         else if(res[1] < 4.5) System.out.println("Split - Prob: " + res[0]);
         else if(res[1] < 5.5) System.out.println("Surrender - Prob: " + res[0]);
         else System.out.println("Error - Prob: " + res[0]);
         
         handActive = true;
         while (handActive == true) {
            System.out.println("If hit, enter card value received. If stay, enter -1");
            newCard = sc.nextInt();
            if (newCard == -1){
               handActive = false;
            }
            else{
               game.addToPlayersHand(newCard);
               res = game.getProb(0);
               if(res[1] < 1.5) System.out.println("Hit - Prob: " + res[0]);
               else System.out.println("Stay - Prob: " + res[0]);
            }
         }
         game.resetHand();
         System.out.println("");
      }
   }
   
     
}