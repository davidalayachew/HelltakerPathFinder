
package HelltakerPathFinderPackage;
   
import java.util.Objects;

public record CellPair(Cell first, Cell second)
{

   public CellPair
   {
   
      Objects.requireNonNull(first);
      Objects.requireNonNull(second);
   
   }

}
