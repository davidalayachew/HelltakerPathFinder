
package HelltakerPathFinderPackage;
   
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

import static HelltakerPathFinderPackage.NoOccupant.*;
import static HelltakerPathFinderPackage.Floor.*;

public class Boards
{

   private Boards()
   {
   
      throw new UnsupportedOperationException();
   
   }

   private static List<Cell> row(String row)
   {
   
      Objects.requireNonNull(row);
   
      final List<Cell> cells = new ArrayList<>();
   
      final BasicCell emptyBlock = new BasicCell(new Block());
      final BasicCell vacant = new BasicCell(new NoOccupant());
      final BasicCell enemy = new BasicCell(new Enemy());
      final Underneath spikes = new Underneath(SPIKY_FLOOR);
      final Underneath retractedSpikes = new Underneath(RETRACTABLE_EMPTY_FLOOR);
   
      final Player player = new Player(false, false, EMPTY_FLOOR);
   
      for (String each : row.split("\t"))
      {
      
         cells
            .add
            (
               switch (each.toUpperCase())
               {
               
                  case  "B"   -> emptyBlock;
                  case  "B^"  -> emptyBlock.underneath(spikes);
                  case  "BI"  -> emptyBlock.underneath(retractedSpikes);
                  case  "B$"  -> emptyBlock.underneath(new Underneath(Collectible.SECRET));
                  case  "_"   -> vacant;
                  case  "_I"  -> vacant.underneath(retractedSpikes);
                  case  "_O"  -> vacant.underneath(new Underneath(RETRACTABLE_SPIKY_FLOOR));
                  case  "E"   -> enemy;
                  case  "G"   -> new Goal();
                  case  "$"   -> vacant.underneath(new Underneath(Collectible.KEY));
                  case  "L"   -> new Lock();
                  case  "P"   -> player;
                  case  "P^"  -> player.floor(SPIKY_FLOOR);
                  case  "?"   -> vacant.underneath(new Underneath(Collectible.SECRET));
                  case  "^"   -> vacant.underneath(spikes);
                  case  "W"   -> new Wall();
                  default     -> throw new IllegalArgumentException("I don't know how to parse this ---> " + each);
               
               }
            )
            ;
      
      }
   
      return List.copyOf(cells);
   
   }

   public static String print(Board board)
   {
   
      Objects.requireNonNull(board);
   
      String output = "";
   
      for (List<Cell> row : board.board())
      {
      
         for (Cell eachCell : row)
         {
         
            output += print(eachCell) + "\t";
         
         }
      
         output += "\n";
      
      }
   
      return output;
   
   }

   private static String print(Cell cell)
   {
   
      final BiFunction<String, Floor, String> floorSwitch =
         (string, floor) ->
            string
            +
            switch (floor)
            {
               case  EMPTY_FLOOR             -> "";
               case  RETRACTABLE_EMPTY_FLOOR -> "I";
               case  SPIKY_FLOOR             -> "^";
               case  RETRACTABLE_SPIKY_FLOOR -> "O";
            }
            ;
   
      final BiFunction<Collectible, Floor, String> collectibleFloor =
         (collectible, floor) ->
            switch (collectible) 
            {
               case  NONE     -> floorSwitch.apply("", floor);
               case  KEY      -> floorSwitch.apply("$", floor);
               case  SECRET   -> floorSwitch.apply("?", floor);
            }
            ;
   
      return
         switch (cell)
         {
            case  Player p -> floorSwitch.apply(p.toString(), p.floor());
            case  Wall _   -> "W";
            case  Goal _   -> "G";
            case  Lock _   -> "L";
            case  BasicCell(Underneath(var floor, var collectible), var occupant)
               ->
                  switch (occupant) 
                  {
                     case  NoOccupant() -> "_" + collectibleFloor.apply(collectible, floor);
                     case  Block()      -> "B" + collectibleFloor.apply(collectible, floor);
                     case  Enemy()      -> "E" + collectibleFloor.apply(collectible, floor);
                  }
                  ;
         
         }
         ;
   
   }

   private static List<List<Cell>> board(String board)
   {
   
      Objects.requireNonNull(board);
   
      return
         board
            .lines()
            .map(Boards::row)
            .toList()
            ;
   
   }

   public static Chapter voidStrangerLevelB10()
   {
   
      System.out.println("The Level B10 from the game Void Stranger!");
   
      final Board chapter =
         new
            Board
            (
               board
               (
                  """
                  W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	E	E	_	_	_	P	W	W
                  W	W	W	_	_	_	_	_	_	W	W
                  W	W	W	_	_	_	w	w	W	W	W
                  W	W	W	_	_	_	_	_	G	W	W
                  W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	W	W	W	W	W	W	W	W
                  """
               )
            );
   
      return new Chapter(chapter, 26);
   
   }

   public static Chapter chapterNegative1()
   {
   
      System.out.println("Chapter -1!");
   
      final Board chapter =
         new
            Board
            (
               board
               (
                  """
                  W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	E	E	e	e	e	P	W	W
                  W	W	W	_	e	e	e	e	e	W	W
                  W	W	W	_	e	e	w	w	W	W	W
                  W	W	W	_	_	e	e	e	G	W	W
                  W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	W	W	W	W	W	W	W	W
                  """
               )
            );
   
      return new Chapter(chapter, 26);
   
   }

   public static Chapter chapter0()
   {
   
      final Board chapter =
         new
            Board
            (
               board
               (
                  """
                  W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	W	W	W	_	P	W	W	W
                  W	W	W	_	_	_	_	_	W	W	W
                  W	W	W	_	_	_	_	W	W	W	W
                  W	W	_	_	W	W	W	W	W	W	W
                  W	W	_	_	_	_	_	_	W	W	W
                  W	W	_	_	_	_	_	_	G	W	W
                  W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	W	W	W	W	W	W	W	W
                  """
               )
            );
   
      return new Chapter(chapter, 23);
   
   }

   public static Chapter chapter0_1()
   {
   
      final Board chapter =
         new
            Board
            (
               board
               (
                  """
                  W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	W	W	W	_	P	W	W	W
                  W	W	W	_	_	e	_	_	W	W	W
                  W	W	W	_	_	_	_	W	W	W	W
                  W	W	_	_	W	W	W	W	W	W	W
                  W	W	_	_	_	_	_	_	W	W	W
                  W	W	_	_	_	_	_	_	G	W	W
                  W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	W	W	W	W	W	W	W	W
                  """
               )
            );
   
      return new Chapter(chapter, 23);
   
   }

   public static Chapter chapter0_2()
   {
   
      final Board chapter =
         new
            Board
            (
               board
               (
                  """
                  W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	W	W	W	_	P	W	W	W
                  W	W	W	_	_	E	_	_	W	W	W
                  W	W	W	_	E	_	_	W	W	W	W
                  W	W	_	_	W	W	W	W	W	W	W
                  W	W	_	_	_	_	_	_	W	W	W
                  W	W	_	_	_	_	_	_	G	W	W
                  W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	W	W	W	W	W	W	W	W
                  """
               )
            );
   
      return new Chapter(chapter, 23);
   
   }

   public static Chapter chapter0_3()
   {
   
      final Board chapter =
         new
            Board
            (
               board
               (
                  """
                  W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	W	W	W	_	P	W	W	W
                  W	W	W	_	_	E	_	_	W	W	W
                  W	W	W	_	E	_	E	W	W	W	W
                  W	W	_	_	W	W	W	W	W	W	W
                  W	W	_	_	_	_	_	_	W	W	W
                  W	W	_	_	_	_	_	_	G	W	W
                  W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	W	W	W	W	W	W	W	W
                  """
               )
            );
   
      return new Chapter(chapter, 23);
   
   }

   public static Chapter chapter0_4()
   {
   
      final Board chapter =
         new
            Board
            (
               board
               (
                  """
                  W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	W	W	W	_	P	W	W	W
                  W	W	W	_	_	E	_	_	W	W	W
                  W	W	W	_	E	_	E	W	W	W	W
                  W	W	_	_	W	W	W	W	W	W	W
                  W	W	_	B	_	_	_	_	W	W	W
                  W	W	_	_	_	_	_	_	G	W	W
                  W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	W	W	W	W	W	W	W	W
                  """
               )
            );
   
      return new Chapter(chapter, 23);
   
   }

   public static Chapter chapter0_5()
   {
   
      final Board chapter =
         new
            Board
            (
               board
               (
                  """
                  W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	W	W	W	_	P	W	W	W
                  W	W	W	_	_	E	_	_	W	W	W
                  W	W	W	_	E	_	E	W	W	W	W
                  W	W	_	_	W	W	W	W	W	W	W
                  W	W	_	B	_	_	_	_	W	W	W
                  W	W	_	B	_	_	_	_	G	W	W
                  W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	W	W	W	W	W	W	W	W
                  """
               )
            );
   
      return new Chapter(chapter, 23);
   
   }

   public static Chapter chapter0_6()
   {
   
      final Board chapter =
         new
            Board
            (
               board
               (
                  """
                  W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	W	W	W	_	P	W	W	W
                  W	W	W	_	_	E	_	_	W	W	W
                  W	W	W	_	E	_	E	W	W	W	W
                  W	W	_	_	W	W	W	W	W	W	W
                  W	W	_	B	_	_	_	_	W	W	W
                  W	W	_	B	_	B	_	_	G	W	W
                  W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	W	W	W	W	W	W	W	W
                  """
               )
            );
   
      return new Chapter(chapter, 23);
   
   }

   public static Chapter chapter1()
   {
   
      final Board chapter =
         new
            Board
            (
               board
               (
                  """
                  W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	W	W	W	_	P	W	W	W
                  W	W	W	_	_	E	_	_	W	W	W
                  W	W	W	_	E	_	E	W	W	W	W
                  W	W	_	_	W	W	W	W	W	W	W
                  W	W	_	B	_	_	B	_	W	W	W
                  W	W	_	B	_	B	_	_	G	W	W
                  W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	W	W	W	W	W	W	W	W
                  """
               )
            );
   
      return new Chapter(chapter, 23);
   
   }

   public static Chapter chapter2()
   {
   
      final Board chapter =
         new
            Board
            (
               board
               (
                  """
                  W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	_	_	_	_	W	W	W	W
                  W	W	W	E	W	^	^	_	_	W	W
                  W	W	_	^	W	W	B^	B^	B	W	W
                  W	W	_	_	W	W	_	^	_	W	W
                  W	W	P	_	W	W	_	E	_	W	W
                  W	W	W	W	W	W	G	_	E	W	W
                  W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	W	W	W	W	W	W	W	W
                  """
               )
            );
   
      return new Chapter(chapter, 24);
   
   }

   public static Chapter chapter3()
   {
   
      final Board chapter =
         new
            Board
            (
               board
               (
                  """
                  W	W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	W	W	W	W	G	_	W	W	W
                  W	W	W	W	W	W	W	W	L	W	W	W
                  W	W	W	W	_	^	^	_	_	P	W	W
                  W	W	W	W	^	W	^	W	_	_	W	W
                  W	W	W	W	_	_	E	^	^	W	W	W
                  W	W	$	W	^	W	^	W	_	W	W	W
                  W	W	_	_	_	_	_	E	_	W	W	W
                  W	W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	W	W	W	W	W	W	W	W	W
                  """
               )
            );
   
      return new Chapter(chapter, 32);
   
   }

   public static Chapter chapter4()
   {
   
      final Board chapter =
         new
            Board
            (
               board
               (
                  """
                  W	W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	W	W	W	W	W	W	W	W	W
                  W	W	P	W	$	_	B	W	W	W	W	W
                  W	W	_	B	^	B^	_	L	_	W	W	W
                  W	W	B	_	B	_	B	B	_	G	W	W
                  W	W	_	B	_	B	_	B	B$	_	W	W
                  W	W	W	_	B	_	B	_	W	W	W	W
                  W	W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	W	W	W	W	W	W	W	W	W
                  """
               )
            );
   
      return new Chapter(chapter, 23);
   
   }

   public static Chapter chapter5()
   {
   
      final Board chapter =
         new
            Board
            (
               board
               (
                  """
                  W	W	W	W	W	W	W	W	W	w
                  W	W	W	W	W	W	W	W	W	w
                  W	W	W	W	W	_	G	W	W	w
                  W	W	W	W	_	L	B$	_	W	w
                  W	W	P	W	_I	_	B	_	W	w
                  W	W	_	W	_	_I	_	_I	W	w
                  W	W	E	W	B	B	B	B	W	w
                  W	W	_I	_	_I	_	_	_I	W	w
                  W	W	W	W	W	W	W	$	W	w
                  W	W	W	W	W	W	W	W	W	w
                  W	W	W	W	W	W	W	W	W	w
                  """
               )
            );
   
      return new Chapter(chapter, 23);
   
   }

   public static Chapter chapter6()
   {
   
      final Board chapter =
         new
            Board
            (
               board
               (
                  """
                  W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	_	P	_	W	W	W	W	W
                  W	W	W	B	B	B	W	W	W	W	W
                  W	W	_	_	_	$	W	W	W	W	W
                  W	W	W	_I	BI	_	_	W	W	W	W
                  W	W	W	E	W	B	B	_	_	W	W
                  W	W	W	_	_	B	_	E	W	W	W
                  W	W	W	W	W	W	L	B$	_	W	W
                  W	W	W	W	W	W	_	G	W	W	W
                  W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	W	W	W	W	W	W	W	W
                  """
               )
            );
   
      return new Chapter(chapter, 43);
   
   }

   public static Chapter chapter7()
   {
   
      final Board chapter =
         new
            Board
            (
               board
               (
                  """
                  W	W	W	W	W	W	W	W	W	W
                  W	W	W	W	W	W	W	W	W	W
                  W	W	W	W	W	G	_	W	W	W
                  W	W	W	W	W	_	L	_	W	W
                  W	W	_	$	W	B	B	B	W	W
                  W	W	E	B	_	E	B	_	W	W
                  W	W	_	W	E	_	_	P	W	W
                  W	W	_O	W	W	_I	W	W	W	W
                  W	W	_I	_O	_I	_O	W	W	W	W
                  W	W	W	W	W	W	W	W	W	W
                  W	W	W	W	W	W	W	W	W	W
                  """
               )
            );
   
      return new Chapter(chapter, 32);
   
   }

   public static Chapter chapter8()
   {
   
      final Board chapter =
         new
            Board
            (
               board
               (
                  """
                  W	W	W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	W	W	W	W	W	W	W	W	W	W
                  W	W	_	W	W	W	G	W	W	W	_	W	W
                  W	W	_	_	_	_	E	_	_	_	_	W	W
                  W	W	_	_	_	_	E	_	_	_	_	W	W
                  W	W	_	_	W	_	E	_	W	_	_	W	W
                  W	W	_	_	_	_	E	_	_	_	_	W	W
                  W	W	_	_	W	_	E	_	W	_	_	W	W
                  W	W	_	_	_	_	E	_	_	_	_	W	W
                  W	W	W	_	W	_	E	_	W	_	W	W	W
                  W	W	W	_	_	_	E	_	_	_	W	W	W
                  W	W	W	_	W	_	_	_	W	_	W	W	W
                  W	W	W	_	_	_	P	_	_	_	W	W	W
                  W	W	W	W	_	_	_	_	_	W	W	W	W
                  W	W	W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	W	W	W	W	W	W	W	W	W	W
                  """
               )
            );
   
      return new Chapter(chapter, 12);
   
   }

   public static Chapter chapter9()
   {
   
      final Board chapter =
         new
            Board
            (
               board
               (
                  """
                  W	W	W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	W	W	W	G	W	W	W	W	W	W
                  W	W	W	W	W	_	_	_	W	W	W	W	W
                  W	W	W	W	W	B	L	B	W	W	W	W	W
                  W	W	W	B	W	B	_	_	W	_	W	W	W
                  W	W	B	_	_	B	B	B	_	_	$	W	W
                  W	W	_	B	B	B	_	_	B	B	_	W	W
                  W	W	W	P	_	B	_	_	B	_	W	W	W
                  W	W	W	W	W	W	W	W	W	W	W	W	W
                  W	W	W	W	W	W	W	W	W	W	W	W	W
                  """
               )
            );
   
      return new Chapter(chapter, 33);
   
   }

}
