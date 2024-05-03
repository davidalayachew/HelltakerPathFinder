
package HelltakerPathFinderPackage;
   
public enum OneOccupant implements NonPlayerOccupant
{
   
   BLOCK,
   ENEMY,
   ;
   
   @Override
   public String toString()
   {
   
      return
         switch (this)
         {
         
            case  BLOCK  -> "B";
            case  ENEMY  -> "E";
         
         };
   
   }

}