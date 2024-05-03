
package HelltakerPathFinderPackage;
   
import java.util.Comparator;

public record Point(int row, int column) implements Comparable<Point>
{

   public static final Comparator<Point> comparator = 
      Comparator
         .comparingInt(Point::row)
         .thenComparingInt(Point::column);
         
   public int compareTo(Point other)
   {
         
      return comparator.compare(this, other);
         
   }
   
   public String toString()
   {
   
      return "(" + this.row + ", " + this.column + ")";
   
   }

}
