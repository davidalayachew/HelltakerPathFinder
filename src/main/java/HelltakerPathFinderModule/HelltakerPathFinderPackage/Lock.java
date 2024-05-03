
package HelltakerPathFinderPackage;
   
record
   Lock
      ()
      implements
         InteractiveCell
{

   public Lock toggleSpikes()
   {
   
      return this;
   
   }
   
   @Override
   public String toString()
   {
   
      return "L";
   
   }

}
