
package HelltakerPathFinderPackage;
   
enum
   Collectible
{

   NONE,
   KEY,
   SECRET,
   ;
   
   @Override
   public String toString()
   {
   
      return 
         switch (this)
         {
         
            case NONE   -> "";
            case KEY    -> "$";
            case SECRET -> "?";
         
         };
   
   }

}