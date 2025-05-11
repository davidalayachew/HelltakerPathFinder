
package HelltakerPathFinderPackage;
   
public record NoOccupant() implements NonPlayerOccupant
{

	//I turned this from an enum to a static field PURELY because
	// we don't have constant patterns yet.
	public static final NoOccupant NO_OCCUPANT = new NoOccupant();

	@Override
	public String toString()
	{
   
		return "_";
   
	}

}