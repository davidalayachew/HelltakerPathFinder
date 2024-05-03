
package HelltakerPathFinderPackage;
   
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public record DirectionChain(List<DirectionPoint> directions)
{

   public DirectionChain
   {
   
      Objects.requireNonNull(directions);
      
   }
   
   public static DirectionChain empty()
   {
   
      return new DirectionChain(List.of());
   
   }
   
   public DirectionChain add(DirectionPoint direction)
   {
   
      Objects.requireNonNull(direction);
      
      final List<DirectionPoint> newDirections = new ArrayList<>(this.directions);
      newDirections.add(direction);
      
      return new DirectionChain(newDirections);
   
   }

   public String toString()
   {
   
      return 
         "\t"
         +
         this.directions
            .stream()
            .map(each -> each.direction() + "\t row " + each.row() + "\tcolumn " + each.column()).collect(Collectors.joining(" --- "))
            ;
   
   }

}
