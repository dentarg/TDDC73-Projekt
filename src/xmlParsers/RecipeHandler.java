package xmlParsers;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import dataObjects.Recipe;
import dataObjects.requirements.IngredientRequirement;
import dataStorage.RecipeDS;

/**
 * Class used for parsing of recipe data in external format.
 */
public class RecipeHandler implements ContentHandler {
   private RecipeDS                         caller;
   private ArrayList<String>                categoryList;
   private String                           categoryName;
   private boolean                          categoryTag;
   private int                              cost          = -1;
   private boolean                          costTag;
   private IngredientRequirement            currentIngredientRequirement;
   private Recipe                           currentRecipe;
   private String                           currentStructure;
   private boolean                          descriptionTag;
   private StringBuilder                    description;
   private int                              difficulty    = -1;
   private boolean                          difficultyTag;
   @SuppressWarnings("unused")
   private int                              energyContent = -1;
   private float                            ingredientAmount;
   private boolean                          ingredientAmountSet;
   private ArrayList<IngredientRequirement> ingredientList;
   private String                           ingredientName;
   private boolean                          ingredientNameTag;
   private String                           ingredientUnit;
   private String                           recipeName;
   private boolean                          recipeNameTag;
   private int                              time          = -1;

   /**
    * Creates a new RecipeHandler for use by the XMLReader.
    * 
    * @param recipeDS
    *           The calling RecipeDS object, needed for adding recipes to
    *           internal storage.
    */
   public RecipeHandler(RecipeDS recipeDS) {
      currentStructure = "";
      ingredientList = new ArrayList<IngredientRequirement>();
      categoryList = new ArrayList<String>();
      caller = recipeDS;
   }

   /**
    * Parses recipe values such as name, cost, difficulty etc. Called
    * automatically by the parser.
    * 
    * @param ch
    * @param start
    * @param length
    * @throws SAXException
    */
   public void characters(char ch[], int start, int length) throws SAXException {
      if (recipeNameTag) {
         char tempName[] = new char[length];
         for (int index = start; index < start + length; index++) {
            tempName[index - start] = ch[index];
         }
         recipeName += String.copyValueOf(tempName);
      } else if (categoryTag) {
         char tempCategory[] = new char[length];
         for (int index = start; index < start + length; index++) {
            tempCategory[index - start] = ch[index];
         }
         categoryName += String.copyValueOf(tempCategory);
      } else if (difficultyTag) {
         char tempDifficulty[] = new char[length];
         for (int index = start; index < start + length; index++) {
            tempDifficulty[index - start] = ch[index];
         }
         String difficultyString = String.copyValueOf(tempDifficulty);
         if (difficultyString.equalsIgnoreCase("easy")) {
            difficulty = 1;
         } else if (difficultyString.equalsIgnoreCase("medium")) {
            difficulty = 2;
         } else if (difficultyString.equalsIgnoreCase("hard")) {
            difficulty = 3;
         }
      } else if (costTag) {
         char tempCost[] = new char[length];
         for (int index = start; index < start + length; index++) {
            tempCost[index - start] = ch[index];
         }
         String costString = String.copyValueOf(tempCost);
         cost = Integer.parseInt(costString);
      } else if (descriptionTag) {
         char tempDescription[] = new char[length];
         for (int index = start; index < start + length; index++) {
            tempDescription[index - start] = ch[index];
         }
         description.append(String.copyValueOf(tempDescription)).append("\n");

      }

      else if (ingredientNameTag) {
         char tempIngredient[] = new char[length];
         for (int index = start; index < start + length; index++) {
            tempIngredient[index - start] = ch[index];
         }

         ingredientName += String.copyValueOf(tempIngredient);
      }
   }

   public void endDocument() {

   }

   /**
    * Resets parser flags and structure at the end of an element. Adds
    * ingredient to list when one has been fully parsed. Adds recipe to storage
    * when one has been fully parsed. Called automatically by the parser.
    * 
    * @param namespaceURI
    * @param localName
    * @param qName
    * @throws SAXException
    */
   @SuppressWarnings("unchecked")
   public void endElement(String namespaceURI, String localName, String qName)
         throws SAXException {
      if (currentStructure.equalsIgnoreCase("rlrecipename")) {
         recipeNameTag = false;
      } else if (currentStructure.equalsIgnoreCase("rlrecipecategory")) {
         categoryList.add(categoryName);
         categoryTag = false;
      } else if (currentStructure.equalsIgnoreCase("rlrecipedifficulty")) {
         difficultyTag = false;
      } else if (currentStructure.equalsIgnoreCase("rlrecipecost")) {
         costTag = false;
      } else if (currentStructure
            .equalsIgnoreCase("rlrecipedescriptionprologparagraph")) {
         descriptionTag = false;
      } else if (currentStructure
            .equalsIgnoreCase("rlrecipeingredientsingredientrequirementitemname")) {
         ingredientNameTag = false;
      } else if (currentStructure
            .equalsIgnoreCase("rlrecipeingredientsingredientitemname")) {
         // Niclas 20060106 St�d f�r v8 formatet
         ingredientNameTag = false;
      } else if (currentStructure
            .equalsIgnoreCase("rlrecipeingredientsingredientrequirement")) {
         if (ingredientAmountSet) {
            if (ingredientUnit == null) {
               ingredientUnit = "st";
               // XXX: fulhack
            }
            currentIngredientRequirement = new IngredientRequirement(
                  ingredientName, ingredientAmount, ingredientUnit);
            ingredientAmountSet = false;
         } else {
            currentIngredientRequirement = new IngredientRequirement(
                  ingredientName, 0, "st");
            // xxx: st är dåligt
         }
         ingredientList.add(currentIngredientRequirement);
      } else if (currentStructure
            .equalsIgnoreCase("rlrecipeingredientsingredient")) {
         // Niclas 20060106 St�d f�r v8 formatet
         if (ingredientAmountSet) {
            if (ingredientUnit == null) {
               ingredientUnit = "st";
               // todo: fix this
            }
            currentIngredientRequirement = new IngredientRequirement(
                  ingredientName, ingredientAmount, ingredientUnit);
            ingredientAmountSet = false;
         } else {
            currentIngredientRequirement = new IngredientRequirement(
                  ingredientName, 0, "g");
         }
         ingredientList.add(currentIngredientRequirement);
      }

      currentStructure = currentStructure.split(qName + "\\z").length != 0 ? currentStructure
            .split(qName + "\\z")[0]
            : null;

      if (currentStructure != null && currentStructure.equalsIgnoreCase("rl")) {

         ArrayList<String> catList = (ArrayList<String>) categoryList.clone();
         ArrayList<IngredientRequirement> ingrList = (ArrayList<IngredientRequirement>) ingredientList
               .clone();
         currentRecipe = new Recipe(recipeName, catList, ingrList, difficulty,
               cost, time, description.toString());

         caller.add(currentRecipe);
         currentRecipe = null;
         recipeName = null;
         ingredientList.clear();
         categoryList.clear();
         difficulty = -1;
         cost = -1;
         time = -1;
         energyContent = -1;
      }
   }

   public void endPrefixMapping(String prefix) throws SAXException {

   }

   public void ignorableWhitespace(char ch[], int start, int length)
         throws SAXException {

   }

   public void processingInstruction(String target, String data)
         throws SAXException {

   }

   public void setDocumentLocator(Locator locator) {

   }

   public void skippedEntity(String name) throws SAXException {

   }

   public void startDocument() {

   }

   /**
    * Sets the correct parser flag at the beginning of an element. Called
    * automatically by the parser.
    * 
    * @param namespaceURI
    * @param localName
    * @param qName
    * @param atts
    * @throws org.xml.sax.SAXException
    */
   public void startElement(String namespaceURI, String localName,
         String qName, Attributes atts) throws SAXException {
      currentStructure += qName;
      if (currentStructure.equalsIgnoreCase("rlrecipename")) {
         recipeNameTag = true;
         recipeName = "";
         description = new StringBuilder();
      } else if (currentStructure.equalsIgnoreCase("rlrecipecategory")) {
         categoryTag = true;
         categoryName = "";
      } else if (currentStructure.equalsIgnoreCase("rlrecipedifficulty")) {
         difficultyTag = true;
      } else if (currentStructure.equalsIgnoreCase("rlrecipecost")) {
         costTag = true;
      } else if (currentStructure.equalsIgnoreCase("rlrecipetotaltime")) {
         String hourString = atts.getValue("hour");
         String minuteString = atts.getValue("min");
         time = Integer.parseInt(hourString) * 60
               + Integer.parseInt(minuteString);
      } else if (currentStructure
            .equalsIgnoreCase("rlrecipedescriptionprologparagraph")) {
         descriptionTag = true;
      } else if (currentStructure
            .equalsIgnoreCase("rlrecipeingredientsingredientrequirementitemname")) {
         ingredientNameTag = true;
         ingredientName = "";
      } else if (currentStructure
            .equalsIgnoreCase("rlrecipeingredientsingredientitemname")) {
         // Niclas 20060106 St�d f�r v8 formatet
         ingredientNameTag = true;
         ingredientName = "";
      } else if (currentStructure
            .equalsIgnoreCase("rlrecipeingredientsingredientrequirementamountquantity")) {
         ingredientAmount = Float.parseFloat(atts.getValue("mean"));
         ingredientAmountSet = true;
      } else if (currentStructure
            .equalsIgnoreCase("rlrecipeingredientsingredientamountquantity")) {
         // Niclas 20060106 St�d f�r v8 formatet
         ingredientAmount = Float.parseFloat(atts.getValue("mean"));
         ingredientAmountSet = true;
      } else if (currentStructure
            .equalsIgnoreCase("rlrecipeingredientsingredientrequirementamountmeasure")) {
         ingredientUnit = atts.getValue("unit");
      } else if (currentStructure
            .equalsIgnoreCase("rlrecipeingredientsingredientamountmeasure")) {
         // Niclas 20060106 St�d f�r v8 formatet
         ingredientUnit = atts.getValue("unit");
      }
   }

   public void startPrefixMapping(String prefix, String uri)
         throws SAXException {

   }
}
