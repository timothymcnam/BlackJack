public class CardLookUpTable{
   
   int[] table;
   
   public CardLookUpTable (int size) {
      table = new int[size];
   } 
   
   public void put(int index, int val) {
      table[index-1] = val;
   }
   
   public int get(int index) {
      return table[index-1];
   }
}