
package HelltakerPathFinderPackage;
   
import java.util.Objects;

import static HelltakerPathFinderPackage.NoOccupant.*;
   
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
   
		this(underneath, NoOccupant.NO_OCCUPANT);
   
	}
   
	public BasicCell(NonPlayerOccupant nonPlayerOccupant)
	{
   
		this(Underneath.NOTHING, nonPlayerOccupant);
   
	}
   
	public BasicCell()
	{
   
		this(Underneath.NOTHING, NoOccupant.NO_OCCUPANT);
   
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
				   
						case  Block()      -> nonPlayerOccupant;
						case  NoOccupant() -> nonPlayerOccupant;
						case  Enemy()
						   -> 
								switch (newUnderneath.floor())
								{
								   
									case  EMPTY_FLOOR             -> nonPlayerOccupant;
									case  RETRACTABLE_EMPTY_FLOOR -> throw new IllegalArgumentException("Enemy should have died by spikes! floor = " + newUnderneath.floor());
									case  SPIKY_FLOOR, 
									      RETRACTABLE_SPIKY_FLOOR -> NO_OCCUPANT;
							   
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
