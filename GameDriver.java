public class GameDriver{
   
   static Game game;
   float bet = 10;
   
   public static void main(String[] args){
      game = new Game();
      
      int[] f ={0,1,2,0,1};
      System.out.println(maxCostItem(f));
      
      //TODO;
      //We probably want a scanner or some type of input
      //Scan in values
      //edit game hands
      //then call getProb()
      
   }
   
   public static int maxCostItem(int[] items){
   	int maxItem = 0;
   	int curMax = 0;
   	for(int i=0;i<items.length;i++){
      	if(items[i]>curMax)
        	maxItem = items[i];
   	}
   	return maxItem;	 
  }


   
   double[] getProb(){
      double[] retArray = new double[2]; //[0] = probability of best move, [1] = the move itself {1 = hit, 2=stay, split, double, surr}
      
      //Calculate probability of winning on move
      //Print average money amount = (won on move * probability of winning) - (money lost * prob losing)
      
      //ex.
      //If player's hand is under 21
      
         //If can hit
            //double hitProb = getProbHit();
            //double hitMoney = (hitProb * bet) - ((1 - hitProb) * bet);
            
         //If can stay
            //calculate this
            
         //Calc for other  hand types as wells
      
      
      
      
      // Return array with maximum Money amount as well as the number that corresponds to that move  (use retArray)
      
      
      
      
      return retArray;
   }
   
   double getProbHit(){
      //TODO
      return 0.0;
   }
   
   double getProbStay(){
      //TODO
      return 0.0;
   }
   
   double[] getProbSplit(){
      //TODO
      double[] retArray = new double[2]; //[0] = probability of 1st hand, [1] = probability of 2nd
      
      return retArray;
   }
   
   double getProbDouble(){
      //TODO
      return 0.0;
   }
   
   double getProbSurrender(){
      return 0.0;
   }
  
}