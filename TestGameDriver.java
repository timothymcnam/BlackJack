import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;

public class TestGameDriver{
   
   static TestDeck deck;
   static Deck playersDeck;
   
   static double money = 1000;
   static double bet = 10;
   
   static final int NUM_DECKS = 2;
   
   static int i = 0;
   
   public static void main(String[] args){
      deck = new TestDeck(NUM_DECKS);
      playersDeck = new Deck(NUM_DECKS);
      
      i = 0;
      
      while(i < 10000){
         while(deck.numCardsLeft > 30){
            playHand();
            i++;
            System.out.println(i);
         }
         deck.resetDeck();
         playersDeck.resetDeck();
      }
   }
   
   public static void playHand() {
      //initial deal
      
      Hand playersHand = new Hand();
      Hand dealersHand = new Hand();
      
      drawNext(playersHand);
      drawNext(playersHand);
      drawNext(dealersHand);
      
      //1 hit, 2 stay, 3 double, 4 split, 5 surrender
      int move = getNextMove(playersHand, dealersHand, playersDeck);
      
      if(move == 1){
         while(move == 1){
            drawNext(playersHand);
            move = getNextMove(playersHand, dealersHand, playersDeck);
            // System.out.println("Hit");
         }
      }
      
      if(move == 2){
         //stay
         drawForDealer(dealersHand);
         money += getWinnings(playersHand, dealersHand);
      }
      else if(move == 3){
         //double
         drawNext(playersHand);
         drawForDealer(dealersHand);
         money += 2.0*getWinnings(playersHand, dealersHand);
      }
      else if(move == 4){
         //split
         System.out.println("Split");
         ArrayList<Hand> hands = splitHand(playersHand, dealersHand);
         drawForDealer(dealersHand);
         for(Hand hand : hands){
            money += getWinnings(hand, dealersHand);
         }
      }
      else if(move == 5){
         //surr
         drawForDealer(dealersHand);
         money -= bet*0.5;
      }
      else{
         System.out.println("Invalid Move");
      }
      System.out.println("Money: " + money);
   }
   
   public static ArrayList<Hand> splitHand(Hand pHand, Hand dHand) {
      ArrayList<Hand> ret = new ArrayList<Hand>();
      
      Hand playersHand1 = new Hand();
      Hand playersHand2 = new Hand();
      Hand dealersHand = dHand;
      
      playersHand1.total = pHand.total/2;
      playersHand2.total = pHand.total/2;
      
      playersHand1.numAces = pHand.numAces/2;
      playersHand2.numAces = pHand.numAces/2;
      
      playersHand1.numCards = pHand.numCards/2;
      playersHand2.numCards = pHand.numCards/2;
      
      drawNext(playersHand1);
      
      //1 hit, 2 stay, 3 double, 4 split, 5 surrender
      int move = getNextMove(playersHand1, dealersHand, playersDeck);
      
      if(move == 1){
         while(move == 1){
            drawNext(playersHand1);
            move = getNextMove(playersHand1, dealersHand, playersDeck);
         }
      }
      
      if(move == 2){
         // Stay
         ret.add(playersHand1);
      }
      else if(move == 3){
         //double
         drawNext(playersHand1);
         ret.add(playersHand1);
         ret.add(playersHand1);
      }
      else if(move == 4){
         //split
         ArrayList<Hand> hands = splitHand(playersHand1, dealersHand);
         for(Hand hand : hands){
            ret.add(hand);
         }
      }
      else if(move == 5){
         //surr
         money -= bet*0.5;
      }
      else{
         System.out.println("Invalid Move after split");
      }
      
      drawNext(playersHand2);
      
      //1 hit, 2 stay, 3 double, 4 split, 5 surrender
      move = getNextMove(playersHand2, dealersHand, playersDeck);
      
      if(move == 1){
         while(move == 1){
            drawNext(playersHand2);
            move = getNextMove(playersHand2, dealersHand, playersDeck);
         }
      }
      
      if(move == 2){
         // Stay
         ret.add(playersHand2);
      }
      else if(move == 3){
         //double
         drawNext(playersHand2);
         ret.add(playersHand2);
         ret.add(playersHand2);
      }
      else if(move == 4){
         //split
         ArrayList<Hand> hands = splitHand(playersHand2, dealersHand);
         for(Hand hand : hands){
            ret.add(hand);
         }
      }
      else if(move == 5){
         //surr
         money -= bet*0.5;
      }
      else{
         System.out.println("Invalid Move after split");
      }
      
      return ret;
   }
   
   public static void drawNext(Hand hand) {
      int nxt = deck.drawNext();
      hand.cardIn(nxt);
      playersDeck.cardSeen(nxt);
   }
   
   public static int getNextMove(Hand player, Hand dealer, Deck pDeck) {
      //Change this method for different uses
      //ex. could get the move from a card counter of from the console
      Game g = new Game(NUM_DECKS);
      g.deck = pDeck;
      g.playersHand = player;
      g.dealersHand = dealer;
      
      System.out.println("Player: " + player.total);
      System.out.println("vs");
      System.out.println("Dealer: " + dealer.total);
      
      double move = g.getProb(0)[1];
      // System.out.println(move);
      System.out.println((int)move);
      return (int)move;
   }
   
   public static double getWinnings(Hand player, Hand dealer) {
      if((player.minValue() == 21 || player.maxValue() == 21) && player.numCards == 2 && (!(dealer.minValue() == 21 || dealer.maxValue() == 21) || dealer.numCards != 2)) return (3.0/2.0)*bet;
      else if(player.minValue() > 21) return -1.0*bet;
      else if(dealer.minValue() > 21) return 1.0*bet;
      else if(player.maxValue() > dealer.maxValue()) return 1.0*bet;
      else if(player.maxValue() == dealer.maxValue()) return 0.0;
      else return -1.0*bet;
   }
   
   public static void drawForDealer(Hand dealer){
      while(dealer.dealerMustHit()){
         drawNext(dealer);
      }
   }
     
}