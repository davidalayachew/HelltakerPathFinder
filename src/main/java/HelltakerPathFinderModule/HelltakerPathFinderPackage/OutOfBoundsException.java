
package HelltakerPathFinderPackage;

public final class OutOfBoundsException extends IndexOutOfBoundsException
{

   public OutOfBoundsException(final long illegalValue, final String comparisonString, final long bound, final String illegalValueName)
   {
   
      super(illegalValueName + " cannot be " + comparisonString + " " + bound + " ! " + illegalValueName + " = " + illegalValue);
   
   }

}
