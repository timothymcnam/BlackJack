public class Hand{
   
   int total; //With aces = 1;
   int numAces;
   int numCards;
   boolean splitPossible = false;
   
   //TODO Do we still need this?
   boolean haveDoubled = false;
   
   
   public Hand () {
      total = 0;
      numAces = 0;
      numCards = 0;
   }
   
   public void cardIn(int card) {
      if(card == total && numCards == 1) {
         splitPossible = true;
      }
      total += card;
      if(card == 1) numAces++;
      numCards++;
   }
   
   public Hand deepcopy() {
      Hand dup = new Hand();
      dup.total = total;
      dup.numAces = numAces;
      dup.numCards = numCards;
      dup.splitPossible = splitPossible;
      dup.haveDoubled = haveDoubled;
      return dup;
   }
   
   public boolean equals(Hand h) {
      if(total == h.total && numAces == h.numAces && numCards == h.numCards && splitPossible == h.splitPossible && haveDoubled == h.haveDoubled) return true;
      else return false;
   }
   
   //return max possible value of hand that is under 21 (Ace = 11 if under 21, else some/all aces = 1)
   int maxValue(){      
      int max = total;
            
      for(int i = 0; i < numAces; i++){
         if(max + 10 > 21) return max; //While count still under 21, start changing aces to 11's by adding 10
         else max += 10;
      }
      return max;
   }
   
   //Returns min possible value of hand (ace = 1)
   int minValue(){
      return total;
   }
   
   // If the player can make any moves
   boolean playerCanMove(){
      if(minValue() <= 21){
         return true;
      }
      else{
         return false;
      }
   }
      
   boolean playerCanHit(){
      if(haveDoubled || minValue() >= 21 || maxValue() == 21) return false;
      else return true;
   }
   
   boolean playerCanStay(){
      if(numCards >= 2) return true;
      else return false;
   }
   
   boolean playerCanSplit(){
      if(numCards == 2 && splitPossible) return true;
      else return false;
   }
   
   boolean playerCanDouble(){
      if(numCards == 2) return true;
      else return false;
   }
   
   boolean playerCanSurrender(){
      //TODO make sure this is the rules
      if(numCards == 2) return true;
      else return false;
   }
   
   //If the dealer hits or stays
   boolean dealerMustHit(){
      // dealer must hit on soft 17 
      int handCount = maxValue();
      if(handCount > 17) return false;
      else if(handCount < 17) return true;
      else{
         if(numAces > 0) return true; //Check for ace, ace -> soft 17 -> true
         else return false;
      }
   } 
} 