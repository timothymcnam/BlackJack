public class Deck{
   
   //define class variables
   int numDecks;
   int numCardsLeft;
   CardLookUpTable cardsLeft;
   
   public Deck(int num_decks){
      //set all class variable values
      numDecks = num_decks;
      cardsLeft = new CardLookUpTable(10);
      resetDeck();
   }
   
   //reset all vars
   void resetDeck(){
      numCardsLeft = numDecks * 52;
      for(int i = 1; i<=9; i++){
         cardsLeft.put(i,4*numDecks);
      }
      cardsLeft.put(10,4*4*numDecks);
   }
   
   //mark cards that have been seen
   void cardSeen(int card){
      numCardsLeft--;
      int oldValSeen = cardsLeft.get(card);
      cardsLeft.put(card, oldValSeen - 1);
   }
   
   //Given cards seen, what is the prob of drawing a certain card
   double probOfNextCard(int cardNum){
      return ((double)cardsLeft.get(cardNum))/((double)numCardsLeft);
   }
   
   //make a deep copy of the deck
   Deck copyDeck(){
      Deck dup = new Deck(numDecks);
      dup.numCardsLeft = this.numCardsLeft;
      // dup.cardsLeft = new HashMap<Integer, Integer>(cardsLeft);
      
      for(int i = 1; i<=10; i++){
         dup.cardsLeft.put(i,cardsLeft.get(i));
      }
      
      return dup;
   }
  
}