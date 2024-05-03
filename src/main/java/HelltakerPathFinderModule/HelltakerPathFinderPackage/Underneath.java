
package HelltakerPathFinderPackage;
   
import java.util.Objects;
   
import static HelltakerPathFinderPackage.Floor.*;

record
   Underneath
      (
         Floor floor,
         Collectible collectible
      )
{

   public static final Underneath NOTHING = new Underneath(EMPTY_FLOOR, Collectible.NONE);
   
   public Underneath
   {
   
      Objects.requireNonNull(floor);
      Objects.requireNonNull(collectible);
   
   }
   
   public Underneath(Collectible collectible)
   {
   
      this(EMPTY_FLOOR, collectible);
   
   }
   
   public Underneath(Floor floor)
   {
   
      this(floor, Collectible.NONE);
   
   }
   
   public Underneath collectible(Collectible collectible)
   {
   
      return new Underneath(this.floor, collectible);
   
   }
   
   public Underneath toggleSpikes()
   {
   
      return
         new
            Underneath
            (
               switch (this.floor)
               {
               
                  case  EMPTY_FLOOR             -> EMPTY_FLOOR;
                  case  SPIKY_FLOOR             -> SPIKY_FLOOR;
                  case  RETRACTABLE_EMPTY_FLOOR -> RETRACTABLE_SPIKY_FLOOR;
                  case  RETRACTABLE_SPIKY_FLOOR -> RETRACTABLE_EMPTY_FLOOR;
               
               }
               ,
               this.collectible
            )
            ;
   
   }
   
   @Override
   public String toString()
   {
   
      return "" + this.collectible + this.floor;
   
   }

}
