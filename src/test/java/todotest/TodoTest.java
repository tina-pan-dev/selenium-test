package todotest;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.ChromeDriverManager;

public class TodoTest {

	private WebDriver driver;
    private String todoUrl = "http://todomvc.com/";
    private String framework = "Ember.js";

    public void goToTodoHomepage() {
        driver.get(todoUrl);
    }

    public void openSelectedFrameworkApp() {
        driver.findElement(By.linkText(framework)).click();
    }

    @BeforeClass
    public void testSetUp() {
        ChromeDriverManager.getInstance().setup();
        driver = new ChromeDriver();
        goToTodoHomepage();
        openSelectedFrameworkApp();
    }

    @Test(priority = 1)
    public void addTodoItems() {
        // add a todo item
        driver.findElement(By.id("new-todo")).sendKeys("Get milk" + Keys.ENTER);  
    }

    @Test(priority = 2)
    public void completeTodoItem() {
        // click on checkbox to mark item as complete
        driver.findElement(By.id("new-todo")).sendKeys("Get milk" + Keys.ENTER);  
        driver.findElement(By.xpath("(//li//input[@type='checkbox'])[1]")).click();    
    }

    @Test(priority = 3)
    public void reactivateCompletedTodo() {
        // click on checkbox to mark item as incomplete
        driver.findElement(By.xpath("(//li//input[@type='checkbox'])[1]")).click();
    }

    @Test(priority = 4)
    public void filterVisibleTodosByCompletedState() {
        // filter by "Completed"
        driver.findElement(By.linkText("Completed")).click();

        // reset selection
        driver.findElement(By.linkText("All")).click();
    }

    @Test(priority = 5)
    public void removeSingleTodoFromList() {
    	
        // remove single todo and check that number of items is reduced by 1
        int numItemsBefore = driver.findElements(By.xpath("//ul[@id='todo-list']//li")).size();
        driver.findElement(By.xpath("(//button[@class='destroy'])[1]")).click();
        int numItemsAfter = driver.findElements(By.xpath("//ul[@id='todo-list']//li")).size();
        
        Assert.assertTrue(numItemsAfter - numItemsBefore == -1);
        
    }
    
    @Test(priority = 6)
    public void completeAllTodos() {
        // click on top left checkbox to mark all as completed
        driver.findElement(By.id("toggle-all")).click();
    }

    @Test(priority = 7)
    public void clearAllCompletedTodos() {
        // click on Clear Completed button to clear all completed items
    	driver.findElement(By.id("clear-completed")).click();
    }
    
    // NOTE: this test will fail as noted in README
    // This is an example of an implementation that could work if the label element were editable
   @Test()
   public void editTodoItem(priority = 8) {

       Actions action = new Actions(driver);
       WebElement firstItem;
       
       // add new todo item
       driver.findElement(By.id("new-todo")).sendKeys("Get milk" + Keys.ENTER);
       firstItem = driver.findElement(By.xpath("(//li[@class='ember-view']//label)[1]"));
       
       // edit new item content
       action.doubleClick(firstItem).perform();
       firstItem.sendKeys(Keys.CONTROL + "A");      
       firstItem.sendKeys("I'M EDITING THIS ITEM" + Keys.ENTER);    
       
       // confirm that edited content is saved
       Assert.assertEquals("I'M EDITING THIS ITEM", firstItem.getText());
   
   }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
