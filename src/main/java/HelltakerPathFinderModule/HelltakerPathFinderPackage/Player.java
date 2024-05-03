
package HelltakerPathFinderPackage;
   
import java.util.Objects;
   
import static HelltakerPathFinderPackage.Floor.*;

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

   public Player underneath(final Underneath underneath)
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

   public Player floor(final Floor floor)
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
               
                  case  EMPTY_FLOOR             -> EMPTY_FLOOR;
                  case  SPIKY_FLOOR             -> SPIKY_FLOOR;
                  case  RETRACTABLE_EMPTY_FLOOR -> RETRACTABLE_SPIKY_FLOOR;
                  case  RETRACTABLE_SPIKY_FLOOR -> RETRACTABLE_EMPTY_FLOOR;
               
               }
               
            );
   
   }
   
   @Override
   public String toString()
   {
   
      final String output = "P";
   
      if (this.key && this.secret)
      {
      
         return output + "&";
      
      }
      
      else if (this.key)
      {
      
         return output + "$";
      
      }
      
      else if (this.secret)
      {
      
         return output + "?";
      
      }
      
      else
      {
      
         return output;
      
      }
   
   }
   
}
