import java.util.HashMap;

public class Deck{
   
   //define class variables
   int numDecks;
   int cards;
   int numCardsDrawn;
   int numCardsLeft;
   HashMap<Integer, Integer> cardsDrawn;
   HashMap<Integer, Integer> cardsLeft;
   
   public Deck(int num_decks){
      //set all class variable values
      numDecks = num_decks;
      cards = numDecks * 52;
      cardsDrawn = new HashMap<Integer, Integer>();
      cardsLeft = new HashMap<Integer, Integer>();
           
      resetDeck();
   }
   
   //reset all vars
   void resetDeck(){
      numCardsDrawn = 0;
      numCardsLeft = cards;
      cardsDrawn.clear();
      cardsLeft.clear();
      for(int i = 1; i<=13; i++){
         cardsDrawn.put(i, 0);
         cardsLeft.put(i,4*numDecks);
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
   
   //make a deep copy of the deck
   //Probably good for recursively computing
   Deck copyDeck(){
      Deck dup = new Deck(numDecks);
      dup.numCardsDrawn = this.numCardsDrawn;
      dup.numCardsLeft = this.numCardsLeft;
      dup.cardsDrawn = new HashMap<Integer, Integer>(cardsDrawn);
      dup.cardsLeft = new HashMap<Integer, Integer>(cardsLeft);
      
      return dup;
   }
  
}