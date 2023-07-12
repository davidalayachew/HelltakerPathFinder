package Helltaker;

import java.util.Objects;

record
   BasicCell
      (
         Underneath underneath,
         NonPlayerOccupant nonPlayerOccupant
      )
      implements
         InteractiveCell
{

   public BasicCell
   {
   
      Objects.requireNonNull(underneath);
      Objects.requireNonNull(nonPlayerOccupant);
   
   }
   
   public BasicCell(Underneath underneath)
   {
   
      this(underneath, NonPlayerOccupant.VACANT);
   
   }
   
   public BasicCell(NonPlayerOccupant nonPlayerOccupant)
   {
   
      this(new Underneath(), nonPlayerOccupant);
   
   }
   
   public BasicCell()
   {
   
      this(new Underneath(), NonPlayerOccupant.VACANT);
   
   }
   
   public BasicCell underneath(Underneath underneath)
   {
   
      return new BasicCell(underneath, this.nonPlayerOccupant);
   
   }

   public BasicCell nonPlayerOccupant(NonPlayerOccupant nonPlayerOccupant)
   {
   
      return new BasicCell(this.underneath, nonPlayerOccupant);
   
   }

   public BasicCell toggleSpikes()
   {
   
      final Underneath newUnderneath = this.underneath.toggleSpikes();
   
      return 
         new 
            BasicCell
            (
               newUnderneath, 
               switch (nonPlayerOccupant)
               {
               
                  case BLOCK, VACANT   -> nonPlayerOccupant;
                  case ENEMY
                        -> switch (newUnderneath.floor())
                           {
                           
                              case EMPTY                    -> nonPlayerOccupant;
                              case RETRACTABLE_EMPTY        -> throw new IllegalArgumentException("Enemy should have died by spikes! floor = " + newUnderneath.floor());
                              case SPIKY, RETRACTABLE_SPIKY -> NonPlayerOccupant.VACANT;
                        
                           };
               
               }
            );
   
   }
   
   @Override
   public String toString()
   {
   
      return "" + this.nonPlayerOccupant + this.underneath;
   
   }

}
