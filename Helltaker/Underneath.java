package Helltaker;

import java.util.Objects;

record
   Underneath
      (
         Floor floor,
         Collectible collectible
      )
{

   public Underneath
   {
   
      Objects.requireNonNull(floor);
      Objects.requireNonNull(collectible);
   
   }
   
   public Underneath(Collectible collectible)
   {
   
      this(Floor.EMPTY, collectible);
   
   }
   
   public Underneath(Floor floor)
   {
   
      this(floor, Collectible.NONE);
   
   }
   
   public Underneath()
   {
   
      this(Floor.EMPTY, Collectible.NONE);
   
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
               
                  case EMPTY -> Floor.EMPTY;
                  case SPIKY -> Floor.SPIKY;
                  case RETRACTABLE_EMPTY -> Floor.RETRACTABLE_SPIKY;
                  case RETRACTABLE_SPIKY -> Floor.RETRACTABLE_EMPTY;
               
               },
               this.collectible
            );
   
   }
   
   @Override
   public String toString()
   {
   
      return "" + this.collectible + this.floor;
   
   }

}
