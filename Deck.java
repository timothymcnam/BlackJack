import java.util.HashMap;

public class Deck{
   
   //define class variables
   int numDecks;
   int cards;
   int numCardsLeft;
   HashMap<Integer, Integer> cardsLeft;
   
   public Deck(int num_decks){
      //set all class variable values
      numDecks = num_decks;
      cards = numDecks * 52;
      cardsLeft = new HashMap<Integer, Integer>();
           
      resetDeck();
   }
   
   //reset all vars
   void resetDeck(){
      numCardsLeft = cards;
      cardsLeft.clear();
      for(int i = 1; i<=13; i++){
         cardsLeft.put(i,4*numDecks);
      }
   }
   
   //mark cards that have been seen
   void cardSeen(int card){
      numCardsLeft--;
      int oldValSeen = cardsLeft.get(card);
      cardsLeft.put(card, oldValSeen - 1);
   }
   
   int getNumCards(){
      return cards;
   }
   
   int getNumCardsLeft(){
      return numCardsLeft;
   }
   
   HashMap<Integer, Integer> getCardsLeft(){
      return cardsLeft;
   }
   
   //make a deep copy of the deck
   //Probably good for recursively computing
   Deck copyDeck(){
      Deck dup = new Deck(numDecks);
      dup.numCardsLeft = this.numCardsLeft;
      dup.cardsLeft = new HashMap<Integer, Integer>(cardsLeft);
      
      return dup;
   }
  
}