import java.util.ArrayList;

public class Game{
   
   Deck deck;
   ArrayList<Integer> dealersHand;
   ArrayList<Integer> playersHand;
   
   public Game(){
      deck = new Deck(1); //Initiate with the number of different decks being used
      playersHand = new ArrayList<Integer>();
      dealersHand = new ArrayList<Integer>();
      
   }
   
   void resetGame(){
      dealersHand.clear();
      playersHand.clear();
      deck.resetDeck();
   }
   
   //clear and set dealer's hand
   void setDealersHand(int cardNum){
      dealersHand.clear();
      dealersHand.add(cardNum);
      deck.cardSeen(cardNum);
   }
   
   //Add a card to the dealers hand
   void addToDealersHand(int card){
      dealersHand.add(card);
      deck.cardSeen(card);
   }
   
   //Clear and set hand
   void setPlayersHand(int card1, int card2){
      playersHand.clear();
      playersHand.add(card1);
      playersHand.add(card2);
      deck.cardSeen(card1);
      deck.cardSeen(card2);
   }
   
   //Add a card to the player's hand
   void addToPlayersHand(int card){
      playersHand.add(card);
      deck.cardSeen(card);
   }
   
   void otherCardSeen(int card){
      deck.cardSeen(card);
   }
   
   //Add multiple cards to the players hand
   void otherCardSeen(int[] cards){
      int max = cards.length;
      for(int i = 0; i<max; i++){
         otherCardSeen(cards[i]);
      }
   }
   
   ArrayList<Integer> getPlayersHand(){
      return playersHand;
   }
   
   ArrayList<Integer> getDealersHand(){
      return dealersHand;
   }
   
   Game copyGame(){
      Game dup = new Game();
      
      dup.playersHand = new ArrayList<Integer>(playersHand.size());
      
      for(Integer i : playersHand){
         dup.playersHand.add(new Integer(i));
      }
      
      dup.dealersHand = new ArrayList<Integer>(dealersHand.size());
      for(Integer i : dealersHand){
         dup.dealersHand.add(new Integer(i));
      }
      
      dup.deck = deck.copyDeck();
      
      return dup;
   }
   
   int maxValue(ArrayList<Integer> hand){
      //return max value under 21
      int numAces = 0;
      
      int iter = hand.size();
      
      for(int i = 0; i < iter; i++){
         if(hand.get(i) == 1) numAces++;
      }
      
      int handCount = minValue(hand); //all ace values are 1
      
      for(int i = 0; i < numAces; i++){
         if(handCount + 10 > 21) return handCount; //While count still under 21, start changing aces to 11's by adding 10
         else handCount += 10;
      }
      return handCount;
   }
   
   int minValue(ArrayList<Integer> hand){
      int totalVal = 0;
      
      int iter = hand.size();
      
      for(int i = 0; i < iter; i++){
         totalVal += cardNumToVal(hand.get(i), true);
         if(totalVal > 21) return totalVal;
      }
      return totalVal;
   }
   
   boolean playerCanHit(){
      if(minValue(playersHand) < 21){
         return true;
      }
      else{
         return false;
      }
   }
   
   boolean dealerMustHit(){
      // dealer must hit on soft 17 
      int handCount = maxValue(dealersHand);
      if(handCount > 17) return false;
      else if(handCount < 17) return true;
      else{
         if(dealersHand.contains(1)) return true; //Check for ace, ace -> soft 17 -> true
         else return false;
      }
   } 
   
   int cardNumToVal(int cardNum, boolean aceIsOne){
      //if true aces count as 1, otherwise 11
      // 1 ace, 2 2, ..., 10 10, 11 jack(10), 12 queen, 13 king
      if(cardNum == 1){
         if(aceIsOne) return 1;
         else return 11;
      }
      else if (cardNum > 1 && cardNum < 11){
         return cardNum;
      }
      else if(cardNum > 10 && cardNum < 14){
         return 10;
      }
      else{
         return -1; //invalid card
      }
   }
   
   double probOfNextCard(int cardNum){
      int cardTypeLeft = deck.getCardsLeft().get(cardNum);
      int totalCardsLeft = deck.getNumCardsLeft();
      return cardTypeLeft/totalCardsLeft;
   }
  
}