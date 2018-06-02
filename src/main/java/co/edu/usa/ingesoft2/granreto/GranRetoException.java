/**
 * 
 */
package co.edu.usa.ingesoft2.granreto;

/**
 * 
 * No cambie nada de esta clase.
 *
 */

public class GranRetoException extends Exception
{
    private static final long serialVersionUID = -3505877527029106577L;

    
    public GranRetoException()
    {
    }

    public GranRetoException(String message)
    {
        super(message);
    }

    public GranRetoException(Throwable cause)
    {
        super(cause);
    }

    public GranRetoException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public GranRetoException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace )
    {
        super( message, cause, enableSuppression, writableStackTrace );
    }
}