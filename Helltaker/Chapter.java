package Helltaker;

import java.util.Objects;

public record Chapter (Board board, int steps)
{

   public Chapter
   {
   
      Objects.requireNonNull(board);
   
   }

}
