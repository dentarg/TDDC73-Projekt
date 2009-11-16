package data;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Properties class meant for keeping track of the files used by the
 * application.
 * 
 * @author jernlas
 */
public class Settings {
   private static final String         BUNDLE_NAME     = "data.Settings";            //$NON-NLS-1$
   private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
                                                             .getBundle(BUNDLE_NAME);

   /**
    * Gets a filepath.
    * 
    * @param key
    *           The key for the requested file path.
    * @return The string that matches key, or key surrounded by exclamationmarks
    *         if the property is not found.
    */
   public static String getFilePath(String key) {
      try {
         return RESOURCE_BUNDLE.getString(key);
      } catch (MissingResourceException e) {
         return '!' + key + '!';
      }
   }

   /**
    * This class has only static members and should not be instantiated.
    */
   private Settings() {
   }
}
