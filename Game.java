import java.util.ArrayList;
import java.util.Collections;

public class Game{
   
   Deck deck;
   ArrayList<Integer> dealersHand;
   ArrayList<Integer> playersHand;
   public boolean haveSplit = false;
   public boolean haveDoubled = false;
   
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
   
   //clear, set dealer's hand, mark as seen
   void setDealersHand(int cardNum){
      dealersHand.clear();
      dealersHand.add(cardNum);
      deck.cardSeen(cardNum);
   }
   
   //Add a card to the dealers hand and mark as seen
   void addToDealersHand(int card){
      dealersHand.add(card);
      deck.cardSeen(card);
   }
   
   //clear, set player's hand, mark as seen
   void setPlayersHand(int card1, int card2){
      playersHand.clear();
      playersHand.add(card1);
      playersHand.add(card2);
      deck.cardSeen(card1);
      deck.cardSeen(card2);
   }
   
   //Add a card to the player's hand and mark as seen
   void addToPlayersHand(int card){
      playersHand.add(card);
      deck.cardSeen(card);
   }
   
   //Mark a card as seen
   void otherCardSeen(int card){
      deck.cardSeen(card);
   }
   
   //Mark multiple cards as seen
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
   
   //Make a duplicate of this game (deep copy)
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
   
   //return max possible value of hand that is under 21 (Ace = 11 if under 21, else some/all aces = 1)
   int maxValue(ArrayList<Integer> hand){
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
   
   //Returns min possible value of hand (ace = 1)
   int minValue(ArrayList<Integer> hand){
      int totalVal = 0;
      
      int iter = hand.size();
      
      for(int i = 0; i < iter; i++){
         totalVal += cardNumToVal(hand.get(i), true);
         if(totalVal > 21) return totalVal;
      }
      return totalVal;
   }
   
   //If the player is allowed to make a move - check if the players hand is > 21
   boolean playerCanMove(){
      if(minValue(playersHand) < 21){
         return true;
      }
      else{
         return false;
      }
   }
   
   boolean playerCanHit(){
      if(playersHand.size() <= 1) return true;
      else if(haveDoubled && playersHand.size() >= 3) return false;
      else return playerCanMove();
   }
   
   boolean playerCanStay(){
      if(playersHand.size() >= 2) return true;
      else return false;
   }
   
   boolean playerCanSplit(){
      if(playersHand.size() == 2 && playersHand.get(0) == playersHand.get(1)) return true;
      else return false;
   }
   
   boolean playerCanDouble(){
      if(playersHand.size() == 2) return true;
      else return false;
   }
   
   boolean playerCanSurrender(){
      if(playersHand.size() >= 2) return true;
      else return false;
   }
   
   //If the dealer hits or stays
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
   
   //Given number representing card, get the value (ex. King(13) -> 10)
   //aceIsOne determines if ace counts as 1 (true) or 11 (false)
   int cardNumToVal(int cardNum, boolean aceIsOne){
   
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
   
   //Given cards seen, what is the prob of drawing a certain card
   double probOfNextCard(int cardNum){
      int cardTypeLeft = deck.getCardsLeft().get(cardNum);
      int totalCardsLeft = deck.getNumCardsLeft();
      return ((double)cardTypeLeft)/((double)totalCardsLeft);
   }
   
   
   @Override
   public int hashCode(){
      // String str = (this.preL.toLowerCase() + "" + this.preR.toLowerCase());
//       return str.hashCode();
      Collections.sort(playersHand);
      Collections.sort(dealersHand);
      String str = "";
      for(int i: playersHand){
         str += i;
         str += ",";
      }
      str += "p";
      for(int i: dealersHand){
         str += i;
         str += ",";
      }
      str += "d";
      
      if(haveSplit) str += "t";
      else str += "f";
      
      if(haveDoubled) str += "t";
      else str += "f";
      
      return str.hashCode();
   }
   
   public boolean equals(Game o){
      if(haveSplit != o.haveSplit) return false;
      else if(haveDoubled != o.haveDoubled) return false;
      else if(isTwoArrayListsWithSameValues(playersHand, o.playersHand)) return false;
      else if(isTwoArrayListsWithSameValues(dealersHand, o.dealersHand)) return false;
      else return true;
      
   }
   
   public boolean isTwoArrayListsWithSameValues(ArrayList<Integer> list1, ArrayList<Integer> list2)
    {
        //null checking
        if(list1==null && list2==null)
            return true;
        if((list1 == null && list2 != null) || (list1 != null && list2 == null))
            return false;

        if(list1.size()!=list2.size())
            return false;
        for(int itemList1: list1)
        {
            if(!list2.contains(itemList1))
                return false;
        }

        return true;
    }
  
}