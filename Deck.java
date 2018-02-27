import java.util.HashMap;
import java.util.ArrayList;

public class Deck{
   
   //define class variables
   static int numDecks;
   static int cards;
   static int numCardsDrawn;
   static int numCardsLeft;
   static HashMap<Integer, Integer> cardsDrawn;
   static HashMap<Integer, Integer> cardsLeft;
   
   static ArrayList<Integer> dealersHand;
   static ArrayList<Integer> playersHand;
   
   public Deck(int num_decks){
      //set all class variable values
      numDecks = num_decks;
      cards = numDecks * 52;
      cardsDrawn = new HashMap<Integer, Integer>();
      cardsLeft = new HashMap<Integer, Integer>();
      playersHand = new ArrayList<Integer>();
      dealersHand = new ArrayList<Integer>();
      
      resetAll();
   }
   
   //reset all vars
   void resetAll(){
      numCardsDrawn = 0;
      numCardsLeft = cards;
      cardsDrawn.clear();
      cardsLeft.clear();
      for(int i = 1; i<=13; i++){
         cardsDrawn.put(i, 0);
         cardsLeft.put(i,4*numDecks);
      }
      dealersHand.clear();
      playersHand.clear();
   }
   
   //clear and set dealer's hand
   void setDealersHand(int cardNum){
      dealersHand.clear();
      dealersHand.add(cardNum);
      cardSeen(cardNum);
   }
   
   //clear and set hand with multiple cards
   void setDealersHand(int[] cards){
      int max = cards.length;
      dealersHand.clear();
      for(int i = 0; i<max; i++){
         dealersHand.add(cards[i]);
         cardSeen(cards[i]);
      }
   }
   
   //Add a card to the dealers hand
   void addToDealersHand(int card){
      dealersHand.add(card);
      cardSeen(card);
   }
   
   //Add multiple cards to the dealers hand
   void addToDealersHand(int[] cards){
      int max = cards.length;
      for(int i = 0; i<max; i++){
         dealersHand.add(cards[i]);
         cardSeen(cards[i]);
      }
   }
   
   //Clear and set hand
   void setPlayersHand(int card1, int card2){
      playersHand.clear();
      playersHand.add(card1);
      playersHand.add(card2);
      cardSeen(card1);
      cardSeen(card2);
   }
   
   //Alternative way to set hand with more than 2 cards
   void setPlayersHand(int[] cards){
      int max = cards.length;
      playersHand.clear();
      for(int i = 0; i<max; i++){
         playersHand.add(cards[i]);
         cardSeen(cards[i]);
      }
   }
   
   //Add a card to the player's hand
   void addToPlayersHand(int card){
      playersHand.add(card);
      cardSeen(card);
   }
   
   //Add multiple cards to the players hand
   void addToPlayersHand(int[] cards){
      int max = cards.length;
      for(int i = 0; i<max; i++){
         playersHand.add(cards[i]);
         cardSeen(cards[i]);
      }
   }
   
   //mark cards that have been seen
   void cardSeen(int card){
      numCardsDrawn++;
      numCardsLeft--;
      int oldValDrawn = cardsDrawn.get(card);
      cardsDrawn.put(card, oldValDrawn + 1);
      int oldValSeen = cardsLeft.get(card);
      cardsLeft.put(card, oldValSeen + 1);
   }
   
   //mark multiple seen cards
   void cardSeen(int[] cards){
      int max = cards.length;
      for(int i = 0; i<max; i++){
         cardSeen(cards[i]);
      }
   }
   
   int getNumDecks(){
      return numDecks;
   }
   
   int getNumCards(){
      return cards;
   }
   
   int getNumCardsDrawn(){
      return numCardsDrawn;
   }
   
   int getNumCardsLeft(){
      return numCardsLeft;
   }
   
   HashMap<Integer, Integer> getCardsDrawn(){
      return cardsDrawn;
   }
   
   HashMap<Integer, Integer> getCardsLeft(){
      return cardsLeft;
   }
   
   ArrayList<Integer> getPlayersHand(){
      return playersHand;
   }
   
   ArrayList<Integer> getDealersHand(){
      return dealersHand;
   }
   
   //make a deep copy of the deck
   //Probably good for recursively computing
   Deck copyDeck(){
      Deck dup = new Deck(numDecks);
      dup.numCardsDrawn = this.numCardsDrawn;
      dup.numCardsLeft = this.numCardsLeft;
      dup.cardsDrawn = new HashMap<Integer, Integer>(cardsDrawn);
      dup.cardsLeft = new HashMap<Integer, Integer>(cardsLeft);
      
      dup.playersHand = new ArrayList<Integer>(playersHand.size());
      for(Integer i : playersHand){
         dup.playersHand.add(new Integer(i));
      }
      
      dup.dealersHand = new ArrayList<Integer>(dealersHand.size());
      for(Integer i : dealersHand){
         dup.dealersHand.add(new Integer(i));
      }

      
      return dup;
   }
  
}