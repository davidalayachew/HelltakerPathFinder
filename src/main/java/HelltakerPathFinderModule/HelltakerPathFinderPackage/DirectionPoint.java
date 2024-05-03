
package HelltakerPathFinderPackage;
   
import java.util.Objects;

public record DirectionPoint(Direction direction, int row, int column)
{

   public DirectionPoint
   {
   
      Objects.requireNonNull(direction);
   
   }

}
