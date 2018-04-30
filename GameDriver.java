import java.util.Scanner;
import java.util.HashMap;

public class GameDriver{
   
   static Game game;
   float bet = 10;
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
         else System.out.println("Stay - Prob: " + res[0]);
         // System.out.println(getProbStay(game));
         
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
     //  System.out.println(g.deck.numCardsLeft);
      //For testing the decks
//       System.out.println("Stuff:");
//       for(Integer i : g.playersHand){
//          System.out.println(i);
//       }
//       System.out.println();
      if (savedProbs.containsKey(g)) {
         //System.out.println("Got Prob");
         return savedProbs.get(g);
      }
      else{
      
         double[] retArray = new double[2]; //[0] = probability of best move, [1] = the move itself {1 = hit, 2=stay, split, double, surr}
         
         //Calculate probability of winning on move
         //Print average money amount = (won on move * probability of winning) - (money lost * prob losing)
         
         if(game.playerCanMove()){
            //Recursive Case
            
            double hitProb = 0.0;
            double stayProb = 0.0;
            
            if(g.playerCanHit()){
               hitProb = getProbHit(g);
            }
            if(g.playerCanStay()){
               stayProb = getProbStay(g);
            }
            
            if (hitProb > stayProb) retArray = new double[] {hitProb, 1.0};
            else retArray = new double[] {stayProb, 2.0};
            
         }
         else{
            //Base Case - End the Recursion
            retArray = new double[] {0.0, 2.0};
            // return retArray;
         }
         
         //ex.
         //If player's hand is under 21
         
            //If can hit
               //double hitProb = getProbHit();
               //double hitMoney = (hitProb * bet) - ((1 - hitProb) * bet);
               
            //If can stay
               //calculate this
               
            //Calc for other  hand types as wells
         
         
         
         
         // Return array with maximum Money amount as well as the number that corresponds to that move  (use retArray)
         
         
         
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
      //TODO
      // System.out.println()
      // System.out.println("New:");
//       for(Integer i: g.dealersHand){
//          System.out.println(i);
//       }
      
      if(g.dealerMustHit()){
      
         double[] probs = new double[13];
         for(int i =0; i<13; i++){
            probs[i] = g.probOfNextCard(i+1);
         }
                  
         Game[] games = new Game[13];
         for(int i =0; i<13; i++){
            games[i] = g.copyGame();
            
//             //Test
//             System.out.println("Before:");
//             for(Integer j: games[i].dealersHand){
//                System.out.println(j);
//             }
//             int t = i+1;
//             System.out.println("Add to hand: " + t);
//             //Test ^
            
            games[i].addToDealersHand(i+1);
            
//             //Test
//             System.out.println("After:");
//             for(Integer j: games[i].dealersHand){
//                System.out.println(j);
//             }
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
   
//    double[] getProbSplit(){
//       //TODO
//       double[] retArray = new double[2]; //[0] = probability of 1st hand, [1] = probability of 2nd
//       
//       return retArray;
//    }
//    
//    double getProbDouble(){
//       //TODO
//       return 0.0;
//    }
//    
//    double getProbSurrender(){
//       return 0.0;
//    }
  
}