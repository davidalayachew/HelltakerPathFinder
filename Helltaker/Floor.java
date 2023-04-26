package Helltaker;

enum
   Floor
{

   EMPTY,
   SPIKY,
   RETRACTABLE_EMPTY,
   RETRACTABLE_SPIKY,
   ;
   
   public boolean isSpiky()
   {
   
      return 
         switch (this)
         {
         
            case EMPTY, RETRACTABLE_EMPTY -> false;
            case SPIKY, RETRACTABLE_SPIKY -> true;
         
         };
   
   }
   
   @Override
   public String toString()
   {
   
      return 
         switch (this)
         {
         
            case EMPTY  -> "";
            case SPIKY  -> "^";
            case RETRACTABLE_EMPTY  -> "I";
            case RETRACTABLE_SPIKY  -> "O";
         
         };
   
   }
   
}
