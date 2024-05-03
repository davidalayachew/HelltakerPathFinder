
package HelltakerPathFinderPackage;
   
enum
   Floor
{

   EMPTY_FLOOR,
   SPIKY_FLOOR,
   RETRACTABLE_EMPTY_FLOOR,
   RETRACTABLE_SPIKY_FLOOR,
   ;
   
   public boolean isSpiky()
   {
   
      return 
         switch (this)
         {
         
            case  EMPTY_FLOOR, RETRACTABLE_EMPTY_FLOOR   -> false;
            case  SPIKY_FLOOR, RETRACTABLE_SPIKY_FLOOR   -> true;
         
         };
   
   }
   
   @Override
   public String toString()
   {
   
      return 
         switch (this)
         {
         
            case  EMPTY_FLOOR             -> "";
            case  SPIKY_FLOOR             -> "^";
            case  RETRACTABLE_EMPTY_FLOOR -> "I";
            case  RETRACTABLE_SPIKY_FLOOR -> "O";
         
         };
   
   }
   
}
