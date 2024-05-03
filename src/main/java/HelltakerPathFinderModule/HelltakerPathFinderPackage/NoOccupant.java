
package HelltakerPathFinderPackage;
   
public enum NoOccupant implements NonPlayerOccupant
{

   NO_OCCUPANT,
   ;

   @Override
   public String toString()
   {
   
      return
         switch (this)
         {
         
            case NO_OCCUPANT -> "_";
         
         };
   
   }

}