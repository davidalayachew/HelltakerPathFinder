
package HelltakerPathFinderPackage;
   
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class Main
{

   private Main()
   {
   
      throw new UnsupportedOperationException();
   
   }

   public static void main(String[] args)
   {
   
      Main.solveAllNonDlcLevels(args);
      //Main.solveSingleLevel(Boards.voidStrangerLevelB10());
   
   }
   
   private static void solveSingleLevel(final Chapter chapter)
   {
   
      System.out.println("Solve single level");
   
      final long start = System.currentTimeMillis();
   
      System.out.print("Single level -- "); solve(chapter);
      final long end = System.currentTimeMillis();
   
      System.out.println("COMPLETION TIME = " + ((end - start)/1000) + "." + ((end - start)%1000));
   
   }

   private static void solveAllNonDlcLevels(final String[] args)
   {
   
      System.out.println("Helltaker");
   
      final long start = System.currentTimeMillis();
   
      System.out.print("Chapter 1 - "); solve(Boards.chapter1());
      System.out.print("Chapter 2 - "); solve(Boards.chapter2());
      System.out.print("Chapter 3 - "); solve(Boards.chapter3());
      System.out.print("Chapter 4 - "); solve(Boards.chapter4());
      System.out.print("Chapter 5 - "); solve(Boards.chapter5());
      System.out.print("Chapter 6 - "); solve(Boards.chapter6());
      System.out.print("Chapter 7 - "); solve(Boards.chapter7());
      System.out.print("Chapter 8 - "); solve(Boards.chapter8());
      System.out.print("Chapter 9 - "); solve(Boards.chapter9());
   
      final long end = System.currentTimeMillis();
   
      System.out.println("TOTAL RUN = " + ((end - start)/1000) + "." + ((end - start)%1000));
   
   }

   public static void solve(Chapter chapter)
   {
   
      final long start = System.currentTimeMillis();
   
      final Result result =
         startRecursive
         (
            chapter.board().findPlayerCoordinate(),
            chapter.board().findGoalCoordinate().point(),
            chapter.steps(),
            DirectionChain.empty(),
            new ConcurrentHashMap<>()
            //new ConcurrentSkipListMap<>()
         )
         ;
   
      final long end = System.currentTimeMillis();
   
      System.out.println(result);
      System.out.println("Took " + ((end - start)/1000) + "." + ((end - start)%1000));
   
   }

   public static Result startRecursive
                                    (
                                       final Coordinate playerCoordinate,
                                       final Point goalPoint, //point instead of coordinate because goals don't move
                                       final int stepsLeft,
                                       final DirectionChain chain,
                                       final Map<Coordinate, Integer> stepsLeftPerPoint
                                    )
   {
   
      final CompletionState completionState = checkSuccess(playerCoordinate.board());
   
      if (completionState.equals(CompletionState.REACHED_GOAL_WITH_ALL_SECRETS) && stepsLeft >= 0)
      {
      
         return new Success(chain);
      
      }
   
      if (completionState.equals(CompletionState.REACHED_GOAL_WITHOUT_ALL_SECRETS))
      {
      
         return new Failure();
      
      }
   
      if (notEnoughStepsLeft(stepsLeft, playerCoordinate.point(), goalPoint))
      {
      
         return new Failure();
      
      }
   
      if (currentPositionIsInefficient(playerCoordinate, stepsLeftPerPoint, stepsLeft))
      {
      
         return new Failure();
      
      }
   
      final List<CompletableFuture<Result>> results = new ArrayList<>();
   
      final var directionLoop =
         Direction.values()
         ;
   
      mainRecursion:
      for (Direction eachDirection : directionLoop)
      {
      
         final Coordinate newPlayerCoordinate = Board.movePlayer(eachDirection, playerCoordinate);
         final int newStepsLeft = stepsLeft - (newPlayerCoordinate.board().getCell(newPlayerCoordinate) instanceof Player playerVar && playerVar.floor().isSpiky() ? 2 : 1);
         final DirectionChain newChain = chain.add(new DirectionPoint(eachDirection, playerCoordinate.row(), playerCoordinate.column()));
      
         results.add(CompletableFuture.completedFuture(startRecursive(newPlayerCoordinate, goalPoint, newStepsLeft, newChain, stepsLeftPerPoint)));
      
         final Result result =
                  results
                     .stream()
                     .filter(CompletableFuture::isDone)
                     .map(CompletableFuture::join)
                     .filter(Success.class::isInstance)
                     .findFirst()
                     .orElseGet(Failure::new)
                     ;
      
         if (result instanceof Success success)
         {
         
            return new Success(success, stepsLeftPerPoint.size());
         
         }
      
      }
   
      return
         results
            .stream()
            .map(CompletableFuture::join)
            .filter(Success.class::isInstance)
            .findFirst()
            .orElse(new Failure())
            ;
   
   }

   public static CompletionState checkSuccess(Board board)
   {
   
      if (board.checkSuccessful() instanceof CheckSuccessful(var atGoal, var gotAllSecrets))
      {
      
         return atGoal && gotAllSecrets ? CompletionState.REACHED_GOAL_WITH_ALL_SECRETS
              : atGoal                  ? CompletionState.REACHED_GOAL_WITHOUT_ALL_SECRETS
              :                           CompletionState.DID_NOT_REACH_GOAL
              ;
      
      }
      
      else
      {
      
         throw new IllegalArgumentException();
      
      }
   
   }

   public static boolean notEnoughStepsLeft(int stepsLeft, Point playerPoint, Point goalPoint)
   {
   
      if (stepsLeft <= 0)
      {
      
         return true;
      
      }
   
      final int rowDelta = playerPoint.row() - goalPoint.row();
      final int columnDelta = playerPoint.column() - goalPoint.column();
   
      final int stepsUntilAdjacentToGoal = rowDelta + columnDelta - 1;
   
      return stepsLeft < stepsUntilAdjacentToGoal;
   
   }

   public static boolean currentPositionIsInefficient(final Coordinate playerCoordinate, final Map<Coordinate, Integer> stepsLeftPerPoint, int stepsLeft)
   {
   
      final Integer newValue =
         stepsLeftPerPoint
            .merge
            (
               playerCoordinate,
               stepsLeft,
               Math::max
            );
   
      return !Integer.valueOf(stepsLeft).equals(newValue);
   
   }

}
