public class TestDeck{
   
   //define class variables
   int numDecks;
   int numCardsLeft;
   CardLookUpTable cardsLeft;
   
   public TestDeck(int num_decks){
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
   
   //Given cards seen, what is the prob of drawing a certain card
   double probOfNextCard(int cardNum){
      return ((double)cardsLeft.get(cardNum))/((double)numCardsLeft);
   }
   
   int drawNext() {
      double randTotal = 0.0;
      double rand = Math.random();
            
      for(int i = 1; i<=9; i++) {
         randTotal += probOfNextCard(i);
         if(randTotal > rand) { 
            cardsLeft.put(i, cardsLeft.get(i)-1);
            numCardsLeft--;
            return i;
         }
      
      }
      
      numCardsLeft--;
      cardsLeft.put(10, cardsLeft.get(10)-1);
      return 10;
   }
  
}