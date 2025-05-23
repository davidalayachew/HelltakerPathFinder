
package HelltakerPathFinderPackage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.*;

import static HelltakerPathFinderPackage.Cell.*;
import static HelltakerPathFinderPackage.Floor.*;
import static HelltakerPathFinderPackage.NoOccupant.*;

public record Board(List<List<Cell>> board)
{

	public Board
	{
	
		Objects.requireNonNull(board);
	
		if (board.isEmpty())
		{
		
			throw new IllegalArgumentException("Can't have an empty board!");
		
		}
	
		Objects.requireNonNull(board.get(0));
	
		final int numberOfColumns = board.get(0).size();
	
		{
		
			int numberOfPlayers = 0;
			int numberOfGoals = 0;
		
			for (List<Cell> row : board)
			{
			
				Objects.requireNonNull(row);
			
				if (row.size() != numberOfColumns)
				{
				
					throw new IllegalArgumentException("Number of columns are uneven!");
				
				}
			
				for (Cell cell : row)
				{
				
					Objects.requireNonNull(cell);
				
					if (cell instanceof Player)
					{
					
						numberOfPlayers++;
					
					}
					
					else if (cell instanceof Goal)
					{
					
						numberOfGoals++;
					
					}
				
				}
			
			}
		
			if (numberOfPlayers != 1)
			{
			
				throw new IllegalArgumentException("Number of players != 1! numberOfPlayers = " + numberOfPlayers);
			
			}
		
			if (numberOfGoals != 1)
			{
			
				throw new IllegalArgumentException("Number of goals != 1! numberOfGoals = " + numberOfGoals);
			
			}
		
		}
	
		final List<List<Cell>> tempList = new ArrayList<>();
	
		for (List<Cell> row : board)
		{
		
			tempList.add(List.copyOf(row));
		
		}
	
		board = List.copyOf(tempList); //DEEPLY IMMUTABLE
	
	}

	public int numberOfRows()
	{
	
		return this.board.size();
	
	}

	public int numberOfColumns()
	{
	
		return this.board.get(0).size();
	
	}

	private static int totalCountOfCellTypes(Board board, Cell... types)
	{
	
		Objects.requireNonNull(types);
		Objects.requireNonNull(board);
	
		int count = 0;
	
		for (List<Cell> row : board.board())
		{
		
			for (Cell cell : row)
			{
			
				for (Cell type : types)
				{
				
					if (type.equals(cell))
					{
					
						count++;
					
					}
				
				
				}
			
			}
		
		}
	
		return count;
	
	}

	public Cell getCell(Coordinate coordinate)
	{
	
		Objects.requireNonNull(coordinate);
	
		return this.board.get(coordinate.row()).get(coordinate.column());
	
	}

	private Coordinate setCell(Coordinate coordinate, Direction direction, Triple triple)
	{
	
		Objects.requireNonNull(coordinate);
		Objects.requireNonNull(direction);
		Objects.requireNonNull(triple);
	
		if (triple instanceof Unchanged)
		{
		
			return coordinate;
		
		}
	
		final Cell firstCell = triple.first();
		final Cell secondCell = triple.second();
		final Cell thirdCell = triple.third();
	
		record CellCoordinate(Cell cell, Coordinate coordinate)
		{
		
			CellCoordinate
			{
			
				Objects.requireNonNull(cell);
				Objects.requireNonNull(coordinate);
			
			}
		
		}
	
		final BiFunction<CellCoordinate, List<List<Cell>>, List<List<Cell>>> setter =
			(cellCoordinate, oldBoard) ->
			{
			
				final int row = cellCoordinate.coordinate().row();
				final int column = cellCoordinate.coordinate().column();
				final Cell cell = cellCoordinate.cell();
			
				populateNewBoard:
				{
				
					final List<List<Cell>> newBoardContents = new ArrayList<>();
				
					final List<Cell> newRow = new ArrayList<>(oldBoard.get(row));
				
					newRow.set(column, cell);
				
					for (int i = 0; i < oldBoard.size(); i++)
					{
					
						if (i == row)
						{
						
							newBoardContents.add(newRow);
						
						}
						
						else
						{
						
							newBoardContents.add(oldBoard.get(i));
						
						}
					
					}
				
					return newBoardContents;
				
				}
			
			};
	
		final Board newBoard;
	
		generateNewBoardWithAllChangesApplied:
		{
		
		
			final CellCoordinate firstCellCoordinate = new CellCoordinate(firstCell, coordinate);
			final List<List<Cell>> firstBoard = setter.apply(firstCellCoordinate, this.board);
			final CellCoordinate secondCellCoordinate = new CellCoordinate(secondCell, coordinate.next(direction));
			final List<List<Cell>> secondBoard = setter.apply(secondCellCoordinate, firstBoard);
			final CellCoordinate thirdCellCoordinate = new CellCoordinate(thirdCell, coordinate.next(direction).next(direction));
			newBoard = new Board(setter.apply(thirdCellCoordinate, secondBoard));
		
		
		}
	
		if (firstCell instanceof Player)
		{
		
			return new Coordinate(coordinate.row(), coordinate.column(), newBoard);
		
		}
		
		else if (secondCell instanceof Player)
		{
		
			final var temp = coordinate.next(direction);
		
			return new Coordinate(temp.row(), temp.column(), newBoard);
		
		}
		
		else
		{
		
			throw new IllegalStateException("Either the first or second cell should be a player - firstCell = " + firstCell + " secondCell = " + secondCell + " thirdCell = " + thirdCell);
		
		}
	
	}

	public Cell getNext1(Coordinate coordinate, Direction direction)
	{
	
		Objects.requireNonNull(coordinate);
		Objects.requireNonNull(direction);
	
		return this.getCell(coordinate.next(direction));
	
	}

	private CellPair getNext2(Coordinate coordinate, Direction direction)
	{
	
		Objects.requireNonNull(coordinate);
		Objects.requireNonNull(direction);
	
		return
			new
			   CellPair
			   (
			      this.getCell(coordinate.next(direction)),
			      this.getCell(coordinate.next(direction).next(direction))
			   );
	
	}

	public Coordinate findPlayerCoordinate()
	{
	
		for (int row = 0; row < this.numberOfRows(); row++)
		{
		
			for (int column = 0; column < this.numberOfColumns(); column++)
			{
			
				final Coordinate coordinate = new Coordinate(row, column, this);
			
				final Cell cell = this.getCell(coordinate);
			
				if (cell instanceof Player)
				{
				
					return coordinate;
				
				}
			
			}
		
		}
	
		throw new IllegalStateException();
	
	}


	public CheckSuccessful checkSuccessful()
	{
	
		Coordinate playerCoordinate = null;
		Coordinate goalCoordinate = null;
		Boolean hasUncollectedSecret = null;
	
		for (int row = 0; row < this.numberOfRows(); row++)
		{
		
			for (int column = 0; column < this.numberOfColumns(); column++)
			{
			
				final Coordinate coordinate = new Coordinate(row, column, this);
			
				final Cell cell = this.getCell(coordinate);
			
				if (cell instanceof Player)
				{
				
					playerCoordinate = coordinate;
				
				}
			
				if (cell instanceof Goal)
				{
				
					goalCoordinate = coordinate;
				
				}
			
				hasUncollectedSecret =
					switch (cell)
					{
					
						case Wall()    -> false;
						case Goal()    -> false;
						case Player p  -> false;
						case BasicCell(Underneath(var floor, var collectible), var nonPlayerOccupant)
						      ->
								switch (collectible)
								{
								
									case NONE, KEY -> false;
									case SECRET -> true;
								
								};
						case Lock()    -> false;
					
					};
			
			}
		
		}
	
		if (playerCoordinate == null || goalCoordinate == null || hasUncollectedSecret == null)
		{
		
			throw new IllegalStateException("NULL -- " + playerCoordinate + " -- " + goalCoordinate + " -- " + hasUncollectedSecret);
		
		}
	
		return new CheckSuccessful(playerCoordinate.isAdjacentTo(goalCoordinate), !hasUncollectedSecret);
	
	}

	public boolean hasCollectedAllSecrets()
	{
	
		for (List<Cell> row : this.board)
		{
		
			for (Cell each : row)
			{
			
				final boolean hasUncollectedSecret =
					switch (each)
					{
					
						case Wall()    -> false;
						case Goal()    -> false;
						case Player p  -> false;
						case BasicCell(Underneath(var floor, var collectible), var nonPlayerOccupant)
						      ->
								switch (collectible)
								{
								
									case NONE, KEY -> false;
									case SECRET -> true;
								
								};
						case Lock()    -> false;
					
					};
			
				if (hasUncollectedSecret)
				{
				
					return false;
				
				}
			
			}
		
		}
	
		return true;
	
	}

	public Coordinate findGoalCoordinate()
	{
	
		for (int row = 0; row < this.numberOfRows(); row++)
		{
		
			for (int column = 0; column < this.numberOfColumns(); column++)
			{
			
				final Coordinate coordinate = new Coordinate(row, column, this);
			
				final Cell cell = this.getCell(coordinate);
			
				if (cell instanceof Goal askjdna)
				{
				
					return coordinate;
				
				}
			
			}
		
		}
	
		throw new IllegalStateException();
	
	}

	public Board toggleSpikes()
	{
	
		final List<List<Cell>> newBoard = new ArrayList<>();
	
		for (List<Cell> row : this.board)
		{
		
			final List<Cell> newRow = new ArrayList<>();
		
			for (Cell each : row)
			{
			
				newRow.add(each.toggleSpikes());
			
			}
		
			newBoard.add(newRow);
		
		}
	
		return new Board(newBoard);
	
	}

	public static Coordinate movePlayer(Direction direction, Coordinate coordinate)
	{
	
		Objects.requireNonNull(coordinate);
		Objects.requireNonNull(direction);
	
		final UnaryOperator<Triple> c1IsNotThePlayer       = (triple) -> {throw new IllegalStateException("You can't move a non-player cell!");};
		final UnaryOperator<Triple> playerCanOnlyBeC1      = (triple) -> {throw new IllegalStateException("Player can only occupy the C1 cell!");};
		final UnaryOperator<Triple> moreThan1Player        = (triple) -> {throw new IllegalStateException("You can't have more than 1 player!");};
		final UnaryOperator<Triple> playerCantMove         = (triple) -> triple;
		final UnaryOperator<Triple> playerAlreadyWon       = (triple) -> {throw new IllegalStateException("Why are you trying to move the player if you already won? triple = " + triple);};
	
		final Board updatedBoard = coordinate.board().toggleSpikes();
	
		final CellPair pair = updatedBoard.getNext2(coordinate, direction);
		final Cell c1 = updatedBoard.getCell(coordinate);
		final Cell c2 = pair.first();
		final Cell c3 = pair.second();
	
		record Path(Cell c1, Cell c2, Cell c3) {}
	
		final UnaryOperator<Triple> triple = 
			switch (new Path(c1, c2, c3))
			{	//        | Cell1  | Cell2                                                   | Cell3                                           |
				case Path( NonPlayer _, _, _) -> playerCanOnlyBeC1;
				case Path( _,        Player _,                                                 _                                                ) -> playerCanOnlyBeC1;
				case Path( _,        _,                                                        Player _                                         ) -> playerCanOnlyBeC1;
				case Path( Player _, Wall(),                                                   _                                                ) -> playerCantMove;
				case Path( Player p, Lock(),                                                   _                                                ) when p.key() -> _ -> new Changed(p.leavesBehind(), p.floor(EMPTY_FLOOR), c3);
				case Path( Player p, Lock(),                                                   _                                                ) -> playerCantMove;
				case Path( Player _, Goal(),                                                   _                                                ) -> playerAlreadyWon;
				case Path( Player p, BasicCell(Underneath underneath2, NoOccupant()),          _                                                ) -> _ -> new Changed(p.leavesBehind(), p.underneath(underneath2), c3);
				case Path( Player p, BasicCell(Underneath underneath2, Block block2),          BasicCell(Underneath underneath3, NoOccupant())  ) -> _ -> new Changed(p, new BasicCell(underneath2, new NoOccupant()), new BasicCell(underneath3, block2));
				case Path( Player p, BasicCell(Underneath underneath2, Block()),               BasicCell(Underneath underneath3, Block())       ) -> playerCantMove;
				case Path( Player p, BasicCell(Underneath underneath2, Block()),               BasicCell(Underneath underneath3, Enemy())       ) -> playerCantMove;
				case Path( Player p, BasicCell(Underneath underneath2, Block()),               Wall()                                           ) -> playerCantMove;
				case Path( Player p, BasicCell(Underneath underneath2, Block()),               Lock()                                           ) -> playerCantMove;
				case Path( Player p, BasicCell(Underneath underneath2, Block()),               Goal()                                           ) -> playerCantMove;
				case Path( Player p, BasicCell(Underneath underneath2, Enemy enemy2),          BasicCell(Underneath underneath3, NoOccupant())  ) -> _ -> new Changed(p, new BasicCell(underneath2, new NoOccupant()), new BasicCell(underneath3, enemy2));
				case Path( Player p, BasicCell(Underneath underneath2, Enemy()),               BasicCell(Underneath underneath3, Block())       ) -> _ -> new Changed(p, new BasicCell(underneath2, new NoOccupant()), c3);
				case Path( Player p, BasicCell(Underneath underneath2, Enemy()),               BasicCell(Underneath underneath3, Enemy())       ) -> _ -> new Changed(p, new BasicCell(underneath2, new NoOccupant()), c3);
				case Path( Player p, BasicCell(Underneath underneath2, Enemy()),               Wall()                                           ) -> _ -> new Changed(p, new BasicCell(underneath2, new NoOccupant()), c3);
				case Path( Player p, BasicCell(Underneath underneath2, Enemy()),               Lock()                                           ) -> _ -> new Changed(p, new BasicCell(underneath2, new NoOccupant()), c3);
				case Path( Player p, BasicCell(Underneath underneath2, Enemy()),               Goal()                                           ) -> _ -> new Changed(p, new BasicCell(underneath2, new NoOccupant()), c3);
				// default -> throw new IllegalArgumentException("what is this? -- " + new Path(c1, c2, c3));
			
			}
			;
	
		final Triple original = new Unchanged(c1, c2, c3);
	
		return
			updatedBoard
			   .setCell
			   (
			      coordinate,
			      direction,
			      triple.apply(original)
			   );
	
	}

	public String toString()
	{
	
		return Boards.print(this);
	
	}

}
