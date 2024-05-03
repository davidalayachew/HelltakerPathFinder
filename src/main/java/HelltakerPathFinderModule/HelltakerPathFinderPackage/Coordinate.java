
package HelltakerPathFinderPackage;
   
import java.util.List;
import java.util.Objects;

public record Coordinate(Point point, Board board)
{

   public Coordinate
   {
   
      Objects.requireNonNull(board);
      Objects.requireNonNull(point);
      
      final int row = point.row();
      final int column = point.column();
      
      if (row < 0 || row >= board.numberOfRows())
      {
      
         throw new IllegalArgumentException("Row is out of bounds! row = " + row);
      
      }
   
      if (column < 0 || column >= board.numberOfColumns())
      {
      
         throw new IllegalArgumentException("Column is out of bounds! column = " + column);
      
      }
   
   }
   
   public Coordinate(int row, int column, Board board)
   {
   
      this(new Point(row, column), board);
   
   }
   
   public int row()
   {
   
      return this.point.row();
   
   }
   
   public int column()
   {
   
      return this.point.column();
   
   }

   public boolean isAtEdge()
   {
   
      return 
         (
            this.row() == 0 
            || 
            this.row() == board.numberOfRows() - 1 
            || 
            this.column() == 0 
            || 
            this.column() == board.numberOfColumns() - 1
         );
   
   }

   public Coordinate next(Direction direction)
   {
   
      return 
         switch (direction)
         {
         
            case NORTH -> new Coordinate(this.row() - 1, this.column(), board);
            case SOUTH -> new Coordinate(this.row() + 1, this.column(), board);
            case EAST  -> new Coordinate(this.row(), this.column() + 1, board);
            case WEST  -> new Coordinate(this.row(), this.column() - 1, board);
         
         }
         ;
   
   }
   
   public boolean isAdjacentTo(Coordinate coordinate)
   {
   
      for (Direction each : Direction.values())
      {
      
         final Coordinate next = this.next(each);
      
         if (next.row() == (coordinate.row()) && next.column() == (coordinate.column()))
         {
         
            return true;
         
         }
      
      }
      
      return false;
   
   }
   
   public static int unobstructedDistance(Coordinate player, Coordinate goal)
   {
   
      final int rowDistance = Math.abs(player.row() - goal.row());
      final int columnDistance = Math.abs(player.column() - goal.column());
      
      final int unobstructedDistance = rowDistance + columnDistance;
      
      return //we only need to stand next to them, not on top of them
         unobstructedDistance - (unobstructedDistance > 0 ? 1 : 0);
      
   }

   public String toString()
   {
   
      return "(" + this.point + " = " + this.board.hashCode() + ")";
   
   }

}
