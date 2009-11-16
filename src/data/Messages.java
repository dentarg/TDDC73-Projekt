package data;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Slightly modified version of standard properties class. Static constants are
 * used are substituted for string literals when selecting properiy names, thus
 * reducing the risk of error, and allowing for autopmatic checking of the code
 * by the compiler.
 * 
 * @author jernlas
 */
public class Messages {

   private static final String         BUNDLE_NAME                         = "data.messages_se";
   private static final ResourceBundle RESOURCE_BUNDLE                     = Utf8ResourceBundle
                                                                                 .getBundle(BUNDLE_NAME);

   public static final String          ABORT                               = "Abort";
   public static final String          ABORT_ALL_SETTINGS_QUERY            = "Abort_all_settings_query";
   public static final String          ABORT_DAY_SETTINGS_QUERY            = "Abort_day_settings_query";
   public static final String          ADD                                 = "Add";
   public static final String          AVAILABLE_INGREDIENTS_INFO          = "Available_ingredients_info";
   public static final String          AVIOD_INGREDIENTS_INFO              = "Aviod_ingredients_info";
   public static final String          AVOIDED_INGREDIENTS                 = "Avoided_ingredients";
   public static final String          CHANGE                              = "Change";
   public static final String          CHANGE_SETTINGS                     = "Change_settings";
   public static final String          CHEAP                               = "cheap";
   public static final String          CLOSE_THIS_WINDOW                   = "Close_this_window_BIG";
   public static final String          CONFIRM_DELETE_USER_QUERY           = "Confirm_delete_user_query";
   public static final String          COOKING_TIME_COLON                  = "Cooking_time_colon";
   public static final String          COST_COLON                          = "Cost_colon";
   public static final String          CREATE_MENU                         = "Create_menu";
   public static final String          CREATE_USER                         = "Create_user";
   public static final String          DELETE_THE_USER                     = "Delete_the_user";
   public static final String          DIFFICULTY_COLON                    = "Difficulty_colon";
   public static final String          DINNER                              = "Dinner";
   public static final String          EXIT                                = "Exit";
   public static final String          EXPENSIVE                           = "expensive";
   public static final String          FINISH_ALL_BUTTON_HTML              = "Finish_all_button_html";
   public static final String          FIRST_USE_INFO                      = "First_use_info";
   public static final String          FOR                                 = "For";
   public static final String          FRIDAY_SHORT                        = "Friday_short";
   public static final String          FROM_THE_MEALPLANNING_SYSTEM        = "from_the_mealplanning_system";
   public static final String          HELP                                = "Help";
   public static final String          HELP_ABOUT_MENU                     = "Help_about_menu";
   public static final String          HELP_OFF                            = "HELP_OFF";
   public static final String          HELP_ON                             = "HELP_ON";
   public static final String          HELPFILE_ERROR                      = "Helpfile_error";
   public static final String          HOUR                                = "hour";
   public static final String          HOURS                               = "hours";
   public static final String          INGREDIENT                          = "ingredient";
   public static final String          INGREDIENT_AVAILABILITY_COLON       = "Ingredient_availability_colon";
   public static final String          INGREDIENTS                         = "Ingredients";
   public static final String          INGREDIENTS_AT_HOME                 = "Ingredients_at_home";
   public static final String          INGREDIENTS_AT_HOME_BIG             = "Ingredients_at_home_BIG";
   public static final String          INGREDIENTS_AT_HOME_TOOLTIP         = "Ingredients_at_home_tooltip";
   public static final String          INGREDIENTS_TO_AVOID                = "Ingredients_to_avoid";
   public static final String          INGREDIENTS_TO_AVOID_BIG            = "Ingredients_to_avoid_big";
   public static final String          INSTRUCTIONS                        = "Instructions";
   public static final String          INSTRUCTIONS_BIG                    = "Instructions_BIG";
   public static final String          INSTRUCTIONS_TOOLTIP                = "Instructions_tooltip";
   public static final String          LAST_MONTH                          = "Last_month";
   public static final String          LEFT_BUTTON_HTML                    = "Left_button_html";
   public static final String          LOG_IN_MESSAGE                      = "Log_in_message";
   public static final String          LOG_IN_NEW_USER                     = "Log_in_new_user";
   public static final String          LOG_OUT                             = "Log_out";
   public static final String          LONG_TIME                           = "long_time";
   public static final String          MAX_MUST_BE_MORE_THAN_MIN           = "Max_must_be_more_than_min";
   public static final String          MEAL_PLAN_INSTRUCTIONS              = "Meal_plan_instructions";
   public static final String          MEALPLANNER_SYSTEM                  = "Mealplanner_system";
   public static final String          MEALS                               = "Meals";
   public static final String          MEALS_BIG                           = "Meals_BIG";
   public static final String          MEDIUM_PRICE                        = "medium_price";
   public static final String          MEDIUM_TIME                         = "medium_time";
   public static final String          MENU                                = "Menu";
   public static final String          MENU_NO                             = "Menu_no";
   public static final String          MINUTE                              = "minute";
   public static final String          MINUTES                             = "minutes";
   public static final String          MONDAY_SHORT                        = "Monday_short";
   public static final String          MY_GRAGE_COLON                      = "My_grade_colon";
   public static final String          NEXT_MONTH                          = "Next_Month";
   public static final String          NO_INGREDIENTS                      = "No_ingredients";
   public static final String          NO_SUBJECTS_STRING                  = "No_subjects_string";
   public static final String          NUMBER_OF_PORTIONS_INFO             = "Number_of_portions_info";
   public static final String          NUTRITION                           = "Nutrition";
   public static final String          NUTRITION_BIG                       = "Nutrition_BIG";
   public static final String          NUTRITION_CALCIUM_COLON             = "Nutrition_calcium_colon";
   public static final String          NUTRITION_CARBS_COLON               = "Nutrition_carbs_colon";
   public static final String          NUTRITION_CHOLESTEROL_COLON         = "Nutrition_cholesterol_colon";
   public static final String          NUTRITION_FAT_COLON                 = "Nutrition_fat_colon";
   public static final String          NUTRITION_PROTEIN_COLON             = "Nutrition_protein_colon";
   public static final String          NUTRITION_SODIUM_COLON              = "Nutrition_sodium_colon";
   public static final String          NUTRITIONS_SETTINGS_INFO            = "Nutritions_settings_info";
   public static final String          OK_BIG                              = "OK_BIG";
   public static final String          OUTLINE                             = "Outline";
   public static final String          OUTLINE_BIG                         = "Outline_BIG";
   public static final String          OUTLINE_TOOLTIP                     = "Outline_tooltip";
   public static final String          PERSONS                             = "Persons";
   public static final String          PERSONS_BIG                         = "Persons_BIG";
   public static final String          PICK_ACTION_BELOW                   = "Pick_action_below";
   public static final String          PICK_MEALS_INFO                     = "Pick_meals_info";
   public static final String          PICK_PERSONS_TO_EAT_INFO            = "Pick_persons_to_eat_info";
   public static final String          PORTIONS                            = "portions";
   public static final String          PREDICTED_GRADE_COLON               = "Predicted_grade_colon";
   public static final String          PRESS_NAMED_BUTTON_LEFT             = "If_you_press_the_button_to_the_left_named";
   public static final String          PRINT_MENU                          = "Print_menu";
   public static final String          PRINT_SHOPPING_LIST                 = "Print_shopping_list";
   public static final String          PROPERTIES                          = "Properties";
   public static final String          QUICK_MENU_EXPLANATION_MESSAGE      = "quick_menu_explanation_message";
   public static final String          READ                                = "Read";
   public static final String          READ_ALL_RECIPES                    = "Read_all_recipes";
   public static final String          READ_LAST_MENU                      = "Read_last_menu";
   public static final String          READ_NEXT_MENU                      = "Read_next_menu";
   public static final String          READ_SHOPPING_LIST                  = "Read_shopping_list";
   public static final String          RECIPIE_COLON                       = "Recipie_colon";
   public static final String          REMOVE                              = "Remove";
   public static final String          REMOVE_DAY_QUERY                    = "Remove_day_query";
   public static final String          RESTRICTIONS                        = "Restrictions";
   public static final String          RESTRICTIONS_BIG                    = "Restrictions_BIG";
   public static final String          RESTRICTIONS_INFO                   = "Restrictions_info";
   public static final String          RIGHT_BUTTON_HTML                   = "Right_button_html";
   public static final String          SATURDAY_SHORT                      = "Saturday_short";
   public static final String          SAVE_BIG                            = "Save_BIG";
   public static final String          SAVE_DAY_SETTINGS_QUERY             = "Save_day_settings_query";
   public static final String          SAVE_GRADE_UPDATE_MENU              = "Save_grade_and_update_menu";
   public static final String          SAVE_MENU                           = "Save_menu";
   public static final String          SAVE_MENU_AND_QUIT_QUERY            = "Save_menu_and_quit_query";
   public static final String          SAVE_SETTINGS_AND_CREATE_MENU       = "Save_settings_and_create_menu";
   public static final String          SAVE_SETTINGS_AND_CREATE_MENU_QUERY = "Save_settings_and_create_menu_query";
   public static final String          SAVE_SETTINGS_FOR_DAY               = "Save_settings_for_this_day";
   public static final String          SEE_PREVIOUS_MENUS                  = "See_previous_menus";
   public static final String          SEE_SELECTED                        = "See_selected";
   public static final String          SETTINGS                            = "Settings";
   public static final String          SETTINGS_EXPLANATION_MESSAGE        = "settings_explanation_message";
   public static final String          SETTINGS_FOR                        = "Settings_for";
   public static final String          SETTINGS_FOR_A_DAY                  = "Settings_for_a_day";
   public static final String          SETTINGS_FOR_A_MEAL                 = "Settings_for_a_meal";
   public static final String          SETTINGS_MENU_BIG                   = "Settings_menu_BIG";
   public static final String          SHOPPING_LIST                       = "Shopping_list";
   public static final String          SHORT_TIME                          = "short_time";
   public static final String          SINGLE_MEAL_INSTRUCTIONS            = "Single_meal_instructions";
   public static final String          SKIP                                = "Skip";
   public static final String          SUNDAY_SHORT                        = "Sunday_short";
   public static final String          TEST                                = "test";
   public static final String          THURSDAY_SHORT                      = "Thursday_short";
   public static final String          TIME_PERIOD                         = "Time_period";
   public static final String          TIME_PERIOD_BIG                     = "Time_period_BIG";
   public static final String          TIME_PERIOD_INFO                    = "Time_period_info";
   public static final String          TIME_PERIOD_TOOLTIP                 = "Time_period_tooltip";
   public static final String          TODAYS_DATE                         = "Todays_date";
   public static final String          TUESDAY_SHORT                       = "Tuesday_short";
   public static final String          UNDO_ALL                            = "Undo_all";
   public static final String          UNDO_SELECTION                      = "Undo_selection";
   public static final String          UNKNOWN                             = "unknown";
   public static final String          USER_NOT_FOUND                      = "User_not_found";
   public static final String          USER_SETTINGS                       = "User_settings";
   public static final String          USER_SETTINGS_BIG                   = "User_settings_BIG";
   public static final String          USER_SETTINGS_INFO                  = "User_settings_info";
   public static final String          USER_SETTINGS_NUTRITION_BIG         = "User_settings_nutrition_BIG";
   public static final String          USER_SETTINGS_PERSONS_BIG           = "User_settings_persons_BIG";
   public static final String          USER_SETTINGS_PICK_MEAL_BIG         = "User_settings_pick_meal_BIG";
   public static final String          USER_SETTINGS_RESTRICTIONS_BIG      = "User_settings_restrictions_BIG";
   public static final String          USER_SETTINGS_TOOLTIP               = "User_settings_tooltip";
   public static final String          WEDNESDAY_SHORT                     = "Wednesday_short";
   public static final String          WELCOME                             = "Welcome";
   public static final String          WRITE_YOUR_NAME_HERE                = "Write_your_name_here";
   public static final String          YOU_HAVE_SELECTED_COLON             = "you_have_selected_colon";

   /**
    * Gets a property string.
    * 
    * @param key
    *           The key for the requested string.
    * @return The string that matches key, or key surrounded by exclamationmarks
    *         if the property is not found.
    */
   public static String getString(String key) {
      try {
         return RESOURCE_BUNDLE.getString(key);
      } catch (MissingResourceException e) {
         System.err.println("Unable to find translation for symbol '" + key
               + "'");
         return '!' + key + '!';
      }
   }

   /**
    * This class has only static members and should not be instantiated.
    */
   private Messages() {
   }
}
