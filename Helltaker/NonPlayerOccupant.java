package Helltaker;

enum
   NonPlayerOccupant
{

   VACANT,
   BLOCK,
   ENEMY,
   ;
   
   @Override
   public String toString()
   {
   
      return 
         switch (this)
         {
         
            case VACANT -> "_";
            case BLOCK  -> "B";
            case ENEMY  -> "E";
         
         };
   
   }

}
