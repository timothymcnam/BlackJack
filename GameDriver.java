import java.util.Scanner;
import java.util.HashMap;

public class GameDriver{
   
   static Game game;
   static float bet = 10;
   static HashMap<Game, double[]> savedProbs;
   
   public static void main(String[] args){
   
      game = new Game();
      savedProbs = new HashMap<Game, double[]>();
      int[] startHandPlayer = {0,0};
      int startHandDealer = 0;
      boolean handActive = true;
      int newCard = 0;
      Scanner sc=new Scanner(System.in);
      
      while(true){
         System.out.println("Your starting hand:");
         System.out.println("Ace = 1, 2 = 2, ...,  King = 13");
         System.out.println("Enter first card value");
         startHandPlayer[0] = sc.nextInt();
         System.out.println("Enter second card value");
         startHandPlayer[1] = sc.nextInt();
         game.setPlayersHand(startHandPlayer[0], startHandPlayer[1]);
         System.out.println("The Dealer's starting hand:");
         System.out.println("Ace = 1, 2 = 2, ...,  King = 13");
         System.out.println("Enter the Dealer's visible card value");
         startHandDealer = sc.nextInt();
         game.setDealersHand(startHandDealer);
         System.out.println("Your starting hand is: " + startHandPlayer[0] + " , " + startHandPlayer[1] + ".");
         System.out.println("The Dealer's visible card is: " + startHandDealer + ".");
         
         double[] res = getProb(game);
         if(res[1] < 1.5) System.out.println("Hit - Prob: " + res[0]);
         else if(res[1] < 2.5) System.out.println("Stay - Prob: " + res[0]);
         else if(res[1] < 3.5) System.out.println("Double - Prob: " + res[0]);
         else if(res[1] < 4.5) System.out.println("Split - Prob: " + res[0]);
         else if(res[1] < 5.5) System.out.println("Surrender - Prob: " + res[0]);
         else System.out.println("Error - Prob: " + res[0]);
         
         handActive = true;
         while (handActive == true) {
            System.out.println("If hit, enter card value received. If stay, enter -1");
            newCard = sc.nextInt();
            if (newCard == -1){
               handActive = false;
            }
            else{
               game.addToPlayersHand(newCard);
               res = getProb(game);
               if(res[1] < 1.5) System.out.println("Hit - Prob: " + res[0]);
               else System.out.println("Stay - Prob: " + res[0]);
            }
         }
         System.out.println("");
      }
   }
   
   static double[] getProb(Game g){
      if (savedProbs.containsKey(g)) {
         return savedProbs.get(g);
      }
      else{
      
         double[] retArray = new double[2]; //[0] = probability of best move, [1] = the move itself {1 = hit, 2=stay, double, split, surr}
         
         //Calculate probability of winning on move
         //Print average money amount = (won on move * probability of winning) - (money lost * prob losing)
         
         if(game.playerCanMove()){
            //Recursive Case
            
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
            
            if(g.playerCanHit()){
               hitProb = getProbHit(g);
               hitMoney = (bet * (float)hitProb) - (bet * (1.0f - (float)hitProb));
            }
            if(g.playerCanStay()){
               stayProb = getProbStay(g);
               stayMoney = (bet * (float)stayProb) - (bet * (1.0f - (float)stayProb));
            }
            if(g.playerCanDouble()){
               doubleProb = getProbDouble(g);
               doubleMoney = (2.0f * bet * (float)doubleProb) - (2 * bet * (1.0f - (float)doubleProb));
            }
            if(g.playerCanSplit()){
               splitProb = -10.0 * bet;
               splitMoney = -10.0 * bet;
            }
            if(g.playerCanSurrender()){
               surrenderProb = 0.0;
               surrenderMoney = -0.5f * bet;
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
         
         savedProbs.put(g, retArray);
         return retArray;
      }
   }
   
   static double getProbHit(Game g){
      //TODO
      double[] probs = new double[13];
      for(int i =0; i<13; i++){
         probs[i] = g.probOfNextCard(i+1);
      }
      
      Game[] games = new Game[13];
      for(int i =0; i<13; i++){
         games[i] = g.copyGame();
         games[i].addToPlayersHand(i+1);
      }
      
      
      // double[] rets = new double[13];
      double ret = 0.0;
      for(int i =0; i<13; i++){
         if(probs[i]>0.0){
            ret += probs[i]*getProb(games[i])[0];
         }
      }
      
      return ret;
   }
   
   static double getProbStay(Game g){
      
      if(g.dealerMustHit()){
      
         double[] probs = new double[13];
         for(int i =0; i<13; i++){
            probs[i] = g.probOfNextCard(i+1);
         }
                  
         Game[] games = new Game[13];
         for(int i =0; i<13; i++){
            games[i] = g.copyGame();
            games[i].addToDealersHand(i+1);
         }
         
         double ret = 0.0;
         for(int i =0; i<13; i++){
            if(probs[i]>0.0){
               ret += probs[i]*getProbStay(games[i]);
            }
         }
         return ret;
         
      }
      else{
         if(g.maxValue(g.playersHand) > 21) return 0.0;
         else if(g.maxValue(g.dealersHand) > 21) return 1.0;
         else if(g.maxValue(g.playersHand) == 21 && g.playersHand.size() == 2 && g.dealersHand.size() > 2) return 1.0;
         else if(g.maxValue(g.playersHand) == g.maxValue(g.dealersHand)) return 0.5;
         else if(g.maxValue(g.playersHand) > g.maxValue(g.dealersHand)) return 1.0;
         else return 0.0;
      }
      
   }

   static double getProbDouble(Game g){
      //TODO
      double[] probs = new double[13];
      for(int i =0; i<13; i++){
         probs[i] = g.probOfNextCard(i+1);
      }
      
      Game[] games = new Game[13];
      for(int i =0; i<13; i++){
         games[i] = g.copyGame();
         games[i].addToPlayersHand(i+1);
         games[i].haveDoubled = true;
      }
            
      double ret = 0.0;
      for(int i =0; i<13; i++){
         if(probs[i]>0.0){
            ret += probs[i]*getProb(games[i])[0];
         }
      }
      
      return ret;
   }
   
   static double getProbSurrender(Game g){
      return 0.0;
   }
  
}