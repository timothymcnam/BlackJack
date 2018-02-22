import java.util.HashMap;
import java.util.ArrayList;

public class BlackJack{
   
   //define class variables
   static int numDecks;
   static int cards;
   static int numCardsDrawn;
   static int numCardsLeft;
   static HashMap<Integer, Integer> cardsDrawn;
   static HashMap<Integer, Integer> cardsLeft;
   
   static int dealersHand;
   static ArrayList<Integer> playersHand;
   

   public static void main(String[] args){
      //set all class variable values
      numDecks = 2;
      cards = numDecks * 52;
      numCardsDrawn = 0;
      numCardsLeft = cards;
      cardsDrawn = new HashMap<Integer, Integer>();
      cardsLeft = new HashMap<Integer, Integer>();
      
      //set the key map values for the different cards
      //for map keys, 1 is the ace, 11 is jack, 12 is queen, 13 is king
      for(int i = 1; i<=13; i++){
         cardsDrawn.put(i, 0);
         cardsLeft.put(i,4*numDecks);
      }
      
      
      dealersHand = 0;
      playersHand = new ArrayList<Integer>();
      
   }
   
   //When deck is shuffled call this to reset all variables
   void newGame(){
      numCardsDrawn = 0;
      numCardsLeft = cards;
      cardsDrawn.clear();
      cardsLeft.clear();
      for(int i = 1; i<=13; i++){
         cardsDrawn.put(i, 0);
         cardsLeft.put(i,4*numDecks);
      }
      dealersHand = 0;
      playersHand.clear();
   }
   
   //Set dealer's hand --- usually at when beggining new game
   void setDealersHand(int cardNum){
      dealersHand = cardNum;
   }
   
   //Set player's hand --- usually at when beggining new game
   void setPlayersHand(int card1, int card2){
      playersHand.clear();
      playersHand.add(card1);
      playersHand.add(card2);
   }
   
   //Alternative way to set hand with more than 2 cards
   void setPlayersHand(int[] cards){
      int max = cards.length;
      playersHand.clear();
      for(int i = 0; i<max; i++){
         playersHand.add(cards[i]);
      }
   }
   
   //Add a card to the player's hand
   void addToPlayersHand(int card){
      playersHand.add(card);
   }
   
   //Add multiple cards to the players hand
   void addToPlayersHand(int[] cards){
      int max = cards.length;
      for(int i = 0; i<max; i++){
         playersHand.add(cards[i]);
      }
   }
   
   void cardSeen(int card){
      numCardsDrawn++;
      numCardsLeft--;
      int oldValDrawn = cardsDrawn.get(card);
      cardsDrawn.put(card, oldValDrawn + 1);
      int oldValSeen = cardsLeft.get(card);
      cardsLeft.put(card, oldValSeen + 1);
   }
   
   void cardSeen(int[] cards){
      int max = cards.length;
      for(int i = 0; i<max; i++){
         cardSeen(cards[i]);
      }
   }
   
   void play(){
      
      //Calculate probability of winning on stay
      //Print money won on stay * probability of winning
      
      //Caluculate probability of hit and do same thing
      
      //If we are also considering split, double down, surrender, then calculate those too
      
      //Print out hand that statistically will make the most money
   
   }
  
}