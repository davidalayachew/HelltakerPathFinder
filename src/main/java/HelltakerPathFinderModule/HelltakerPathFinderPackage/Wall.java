
package HelltakerPathFinderPackage;
   
record
   Wall
      ()
      implements
         NonPlayer
{

   public Wall toggleSpikes()
   {
   
      return this;
   
   }
   
   @Override
   public String toString()
   {
   
      return "W";
   
   }

}
