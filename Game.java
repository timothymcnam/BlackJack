import java.util.HashMap;
import java.lang.Math;

public class Game{
   
   Deck deck;
   Hand dealersHand;
   Hand playersHand;
   static double bet = 10.0;
   static HashMap<Game, double[]> savedProbs = new HashMap<Game, double[]>();
   
   public Game(){
      deck = new Deck(6); //Initiate with the number of different decks being used
      playersHand = new Hand();
      dealersHand = new Hand();
   }
   
   public Game(int decks){
      deck = new Deck(decks); //Initiate with the number of different decks being used
      playersHand = new Hand();
      dealersHand = new Hand();
   }
   
   public Game(Game old){
      deck = old.deck.copyDeck();
      playersHand = old.playersHand.deepcopy();
      dealersHand = old.dealersHand.deepcopy();
   }
   
   //Add a card to the dealers hand and mark as seen
   void addToDealersHand(int card){
      dealersHand.cardIn(card);
      deck.cardSeen(card);
   }
   
   //Add a card to the player's hand and mark as seen
   void addToPlayersHand(int card){
      playersHand.cardIn(card);
      deck.cardSeen(card);
   }
   
   //Add 2 carda to the player's hand and mark as seen
   void addToPlayersHand(int card1, int card2){
      playersHand.cardIn(card1);
      deck.cardSeen(card1);
      playersHand.cardIn(card2);
      deck.cardSeen(card2);
   }
   
   //Mark a card as seen
   void otherCardSeen(int card){
      deck.cardSeen(card);
   }
   
   //Mark multiple cards as seen
   void otherCardSeen(int[] cards){
      for(int card : cards){
         otherCardSeen(card);
      }
   }
   
   void resetHand() {
      playersHand = new Hand();
      dealersHand = new Hand();
      savedProbs.clear();
   }
   
   double[] getProb(int depth){
      
      double[] retArray = new double[2]; //[0] = probability of best move, [1] = the move itself {1 = hit, 2=stay, double, split, surr}
      
      //Calculate probability of winning on move
      //Print average money amount = (won on move * probability of winning) - (money lost * prob losing)
      
      if(playersHand.playerCanMove()){
         //Recursive Case
         
         if (savedProbs.containsKey(this)) {
            return savedProbs.get(this);
         }
         
         double hitProb = 0.0;
         double stayProb = 0.0;
         double doubleProb = 0.0;
         double splitProb = 0.0;
         double surrenderProb = 0.0;
         
         double hitMoney = -10.0 * bet;
         double stayMoney = -10.0 * bet;
         double doubleMoney = -10.0 * bet;
         double splitMoney = -10.0 * bet;
         double surrenderMoney = -10.0 * bet;
         
         if(playersHand.playerCanHit()){
            hitProb = getProbHit(depth);
            hitMoney = (bet * hitProb) - (bet * (1.0 - hitProb));
         }
         if(playersHand.playerCanStay()){
            stayProb = getProbStay();
            stayMoney = (bet * stayProb) - (bet * (1.0 - stayProb));
         }
         if(playersHand.playerCanDouble()){
            doubleProb = getProbDouble();
            doubleMoney = (2.0 * bet * doubleProb) - (2.0 * bet * (1.0 - doubleProb));
         }
         if(playersHand.playerCanSplit() && depth < 3){
            splitProb = getProbSplit(depth);
            splitMoney = (2.0 * bet * splitProb) - (2.0 * bet * (1.0 - splitProb));
         }
         if(playersHand.playerCanSurrender()){
            surrenderProb = 0.0;
            surrenderMoney = -0.5 * bet;
         }
         
         if(hitMoney >= stayMoney && hitMoney >= doubleMoney && hitMoney >= splitMoney && hitMoney >= surrenderMoney) retArray = new double[] {hitProb, 1.0};
         else if(stayMoney >= hitMoney && stayMoney >= doubleMoney && stayMoney >= splitMoney && stayMoney >= surrenderMoney) retArray = new double[] {stayProb, 2.0};
         else if(doubleMoney >= hitMoney && doubleMoney >= stayMoney && doubleMoney >= splitMoney && doubleMoney >= surrenderMoney) retArray = new double[] {doubleProb, 3.0};
         else if(splitMoney >= hitMoney && splitMoney >= stayMoney && splitMoney >= doubleMoney && splitMoney >= surrenderMoney) retArray = new double[] {splitProb, 4.0};
         else if(surrenderMoney >= hitMoney && surrenderMoney >= stayMoney && surrenderMoney >= doubleMoney && surrenderMoney >= splitMoney) retArray = new double[] {surrenderProb, 5.0};
         else retArray = new double[] {stayProb, 2.0};     
         
      }
      else{
         //Base Case - End the Recursion
         retArray = new double[] {0.0, 2.0};
      
      }
      savedProbs.put(this, retArray);
      return retArray;
   }
   
   double getProbHit(int depth){
      double[] probs = new double[10];
      for(int i =0; i<10; i++){
         probs[i] = deck.probOfNextCard(i+1);
      }
      
      Game[] games = new Game[10];
      for(int i =0; i<10; i++){
         games[i] = copyGame();
         games[i].addToPlayersHand(i+1);
      }
      
      double ret = 0.0;
      for(int i =0; i<10; i++){
         if(probs[i]>0.0){
            ret += probs[i]*games[i].getProb(depth+1)[0];
         }
      }
      
      return ret;
   }
   
   double getProbStay(){
      
      if(dealersHand.dealerMustHit()){
      
         double[] probs = new double[10];
         for(int i =0; i<10; i++){
            probs[i] = deck.probOfNextCard(i+1);
         }
                  
         Game[] games = new Game[10];
         for(int i =0; i<10; i++){
            games[i] = copyGame();
            games[i].addToDealersHand(i+1);
         }
         
         double ret = 0.0;
         for(int i =0; i<10; i++){
            if(probs[i]>0.0){
               ret += probs[i]*games[i].getProbStay();
            }
         }
         return ret;
         
      }
      else{
         if(playersHand.minValue() > 21) return 0.0;
         else if(dealersHand.maxValue() > 21) return 1.0;         
         else if(playersHand.maxValue() == 21 && playersHand.numCards == 2) return 1.0;
         else if(playersHand.maxValue() == dealersHand.maxValue()) return 0.5;
         else if(playersHand.maxValue() > dealersHand.maxValue()) return 1.0;
         else return 0.0;
      }
      
   }

   double getProbDouble(){
      double[] probs = new double[10];
      for(int i =0; i<10; i++){
         probs[i] = deck.probOfNextCard(i+1);
      }
      
      Game[] games = new Game[10];
      for(int i =0; i<10; i++){
         games[i] = copyGame();
         games[i].addToPlayersHand(i+1);
         games[i].playersHand.haveDoubled = true;
      }
            
      double ret = 0.0;
      for(int i =0; i<10; i++){
         if(probs[i]>0.0){
            ret += probs[i]*games[i].getProbStay();
         }
      }
      
      return ret;
   }
   
   double getProbSplit(int depth) {
      double[] probs = new double[10];
      for(int i =0; i<10; i++){
         probs[i] = deck.probOfNextCard(i+1);
      }
      
      Game[] games = new Game[10];
      for(int i =0; i<10; i++){
         //TODO this could be more accurate
         games[i] = copyGame();
         games[i].playersHand.total = playersHand.total/2;
         games[i].playersHand.numAces = playersHand.numAces/2;
         games[i].playersHand.numCards = playersHand.numCards/2;
         games[i].playersHand.splitPossible = false;
         games[i].addToPlayersHand(i+1);
         //TODO this line doesn't account for splitting twice
         //games[i].playersHand.splitPossible = false;
      }
            
      double ret = 0.0;
      for(int i =0; i<10; i++){
         if(probs[i]>0.0){
            ret += probs[i]*games[i].getProb(depth+1)[0];
         }
      }
      
      return ret;

   }
   
   double getProbSurrender(){
      return 0.0;
   }

   
   
   
   
   
   
   
   //Make a duplicate of this game (deep copy)
   Game copyGame(){
      return new Game(this);
   }
   
   @Override
   public boolean equals(Object o){
      if (o instanceof Game) {
         return this.equals((Game)o);
      }
      else{
         return false;
      }
   }
   
   public boolean equals(Game o){
      if(! playersHand.equals(o.playersHand))return false;
      else if(! dealersHand.equals(o.dealersHand))return false;
      //TODO make a equals for deck
      else return true;
   }
   
   @Override
   public int hashCode(){
      String str = "";
      str += Integer.toString(playersHand.total);
      str += Integer.toString(playersHand.numAces);
      str += Integer.toString(playersHand.numCards);
      str += Boolean.toString(playersHand.splitPossible);
      str += Boolean.toString(playersHand.haveDoubled);
      
      str += Integer.toString(dealersHand.total);
      str += Integer.toString(dealersHand.numAces);
      str += Integer.toString(dealersHand.numCards);
      str += Boolean.toString(dealersHand.splitPossible);
      str += Boolean.toString(dealersHand.haveDoubled);
      
      str += Integer.toString(deck.numDecks);
      str += Integer.toString(deck.numCardsLeft);
      str += Integer.toString(deck.numCardsLeft);
      
      for(int num : deck.cardsLeft.table) {
         str += Integer.toString(num);
      }
      
      return str.hashCode();
   } 
}

//TODO Bug where you can keep splitting and keep hitting to draw he same card
//Cause Infinite loop