package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class BurgerTest {

    private Burger burger;
    private Bun bunMock;
    private Ingredient ingredient1;
    private Ingredient ingredient2;
    private Ingredient ingredient3;

    @Before
    public void setUp() {
        burger = new Burger();
        bunMock = Mockito.mock(Bun.class);
        ingredient1 = new Ingredient(IngredientType.FILLING, "cutlet", 100);
        ingredient2 = new Ingredient(IngredientType.SAUCE, "hot sauce", 100);
        ingredient3 = new Ingredient(IngredientType.FILLING, "dinosaur", 200);
    }

    @Test
    public void testSetBuns() {
        burger.setBuns(bunMock);
        assertEquals(bunMock, burger.bun);
    }

    @Test
    public void testAddIngredientWithNull() {
        burger.setBuns(bunMock);
        int initialSize = burger.ingredients.size();
        burger.addIngredient(null);
        assertEquals(initialSize + 1, burger.ingredients.size());
        assertNull(burger.ingredients.get(initialSize));
    }

    @Test
    public void testAddIngredientsAndListSize() {
        burger.setBuns(bunMock);
        burger.addIngredient(ingredient1);
        burger.addIngredient(ingredient2);
        burger.addIngredient(ingredient3);
        assertEquals(3, burger.ingredients.size());
        assertTrue(burger.ingredients.contains(ingredient1));
        assertTrue(burger.ingredients.contains(ingredient2));
        assertTrue(burger.ingredients.contains(ingredient3));
    }

    @Test
    public void testRemoveIngredient() {
        burger.setBuns(bunMock);
        burger.addIngredient(ingredient1);
        burger.addIngredient(ingredient2);
        burger.addIngredient(ingredient3);

        burger.removeIngredient(1);

        assertEquals(2, burger.ingredients.size());
        assertEquals(ingredient1, burger.ingredients.get(0));
        assertEquals(ingredient3, burger.ingredients.get(1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveIngredientWithInvalidIndex() {
        burger.setBuns(bunMock);
        burger.addIngredient(ingredient1);
        burger.removeIngredient(5);
    }

    @Test
    public void testMoveIngredient() {
        burger.setBuns(bunMock);
        burger.addIngredient(ingredient1);
        burger.addIngredient(ingredient2);
        burger.addIngredient(ingredient3);

        burger.moveIngredient(2, 0);

        assertEquals(ingredient3, burger.ingredients.get(0));
        assertEquals(ingredient1, burger.ingredients.get(1));
        assertEquals(ingredient2, burger.ingredients.get(2));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testMoveIngredientWithInvalidSourceIndex() {
        burger.setBuns(bunMock);
        burger.addIngredient(ingredient1);
        burger.moveIngredient(5, 0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testMoveIngredientWithInvalidTargetIndex() {
        burger.setBuns(bunMock);
        burger.addIngredient(ingredient1);
        burger.addIngredient(ingredient2);
        burger.moveIngredient(0, 5);
    }

    @Test
    public void testGetPriceWithMockedBun() {
        Mockito.when(bunMock.getName()).thenReturn("test bun");
        Mockito.when(bunMock.getPrice()).thenReturn(50.0f);

        burger.setBuns(bunMock);
        burger.addIngredient(ingredient1); // 100
        burger.addIngredient(ingredient2); // 100

        float price = burger.getPrice();
        // Цена: 50 * 2 + 100 + 100 = 300
        assertEquals(300.0f, price, 0.001f);
    }

    @Test
    public void testGetReceipt() {
        Mockito.when(bunMock.getName()).thenReturn("black bun");
        Mockito.when(bunMock.getPrice()).thenReturn(100.0f);

        burger.setBuns(bunMock);
        burger.addIngredient(ingredient1);
        burger.addIngredient(ingredient2);

        String receipt = burger.getReceipt();

        String expected = String.format("(==== black bun ====)%n" +
                "= filling cutlet =%n" +
                "= sauce hot sauce =%n" +
                "(==== black bun ====)%n%n" +
                "Price: %f%n", burger.getPrice());

        assertEquals(expected, receipt);
    }
}
