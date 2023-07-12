package Helltaker;

import java.util.Objects;

record
   Player
      (
         boolean key,
         boolean secret,
         Floor floor
      )
      implements
         Cell
{

   public Player
   {
   
      Objects.requireNonNull(floor);
   
   }

   public Player underneath(Underneath underneath)
   {
   
      Objects.requireNonNull(underneath);
   
      return
         switch (underneath.collectible())
         {
         
            case NONE   -> new Player(this.key, this.secret, underneath.floor());
            case KEY    -> new Player(true, this.secret, underneath.floor());
            case SECRET -> new Player(this.key, true, underneath.floor());
         
         };
   
   }

   public Player floor(Floor floor)
   {
   
      return new Player(this.key, this.secret, floor);
   
   }
   
   public BasicCell leavesBehind()
   {
   
      return new BasicCell(new Underneath(this.floor));
   
   }
   
   public Player toggleSpikes()
   {
   
      return
         this
            .floor
            (
               
               switch (this.floor)
               {
               
                  case EMPTY -> Floor.EMPTY;
                  case SPIKY -> Floor.SPIKY;
                  case RETRACTABLE_EMPTY -> Floor.RETRACTABLE_SPIKY;
                  case RETRACTABLE_SPIKY -> Floor.RETRACTABLE_EMPTY;
               
               }
               
            );
   
   }
   
   @Override
   public String toString()
   {
   
      return 
         switch (this)
         {
         
            case Player(var key, var secret, var floor)
                  -> "P" 
                     + 
                     (key && secret ? "&" : key ? "$" : secret ? "?" : "") 
                     +
                     this.floor; 
         
         };
   
   }
   
}
