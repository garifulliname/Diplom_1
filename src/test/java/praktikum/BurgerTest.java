package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class BurgerTest {

    private Burger burger;
    private Bun bunMock;
    private Ingredient cutlet;
    private Ingredient hotSauce;
    private Ingredient dinosaurMeat;

    @Before
    public void setUp() {
        burger = new Burger();
        bunMock = Mockito.mock(Bun.class);

        cutlet = Mockito.mock(Ingredient.class);
        hotSauce = Mockito.mock(Ingredient.class);
        dinosaurMeat = Mockito.mock(Ingredient.class);

        Mockito.when(cutlet.getType()).thenReturn(IngredientType.FILLING);
        Mockito.when(cutlet.getName()).thenReturn("cutlet");
        Mockito.when(cutlet.getPrice()).thenReturn(100f);

        Mockito.when(hotSauce.getType()).thenReturn(IngredientType.SAUCE);
        Mockito.when(hotSauce.getName()).thenReturn("hot sauce");
        Mockito.when(hotSauce.getPrice()).thenReturn(100f);

        Mockito.when(dinosaurMeat.getType()).thenReturn(IngredientType.FILLING);
        Mockito.when(dinosaurMeat.getName()).thenReturn("dinosaur");
        Mockito.when(dinosaurMeat.getPrice()).thenReturn(200f);
    }

    @Test
    public void testSetBuns() {
        burger.setBuns(bunMock);
        assertEquals(bunMock, burger.bun);
    }

    @Test
    public void testAddIngredientWithNullIncreasesSize() {
        burger.setBuns(bunMock);
        int initialSize = burger.ingredients.size();
        burger.addIngredient(null);
        assertEquals(initialSize + 1, burger.ingredients.size());
    }

    @Test
    public void testAddIngredientWithNullAddsNull() {
        burger.setBuns(bunMock);
        burger.addIngredient(null);
        assertNull(burger.ingredients.get(burger.ingredients.size() - 1));
    }

    @Test
    public void testAddIngredientsSizeAfterAdding() {
        burger.setBuns(bunMock);
        burger.addIngredient(cutlet);
        burger.addIngredient(hotSauce);
        burger.addIngredient(dinosaurMeat);
        assertEquals(3, burger.ingredients.size());
    }

    @Test
    public void testAddIngredientsContainsCutlet() {
        burger.setBuns(bunMock);
        burger.addIngredient(cutlet);
        assertTrue(burger.ingredients.contains(cutlet));
    }

    @Test
    public void testAddIngredientsContainsHotSauce() {
        burger.setBuns(bunMock);
        burger.addIngredient(hotSauce);
        assertTrue(burger.ingredients.contains(hotSauce));
    }

    @Test
    public void testAddIngredientsContainsDinosaurMeat() {
        burger.setBuns(bunMock);
        burger.addIngredient(dinosaurMeat);
        assertTrue(burger.ingredients.contains(dinosaurMeat));
    }
    @Test
    public void testRemoveIngredientSizeAfterRemoval() {
        burger.setBuns(bunMock);
        burger.addIngredient(cutlet);
        burger.addIngredient(hotSauce);
        burger.addIngredient(dinosaurMeat);

        burger.removeIngredient(1);
        assertEquals(2, burger.ingredients.size());
    }

    @Test
    public void testRemoveIngredientFirstElementRemainsUntouched() {
        burger.setBuns(bunMock);
        burger.addIngredient(cutlet);
        burger.addIngredient(hotSauce);
        burger.addIngredient(dinosaurMeat);

        burger.removeIngredient(1);
        assertEquals(cutlet, burger.ingredients.get(0));
    }

    @Test
    public void testRemoveIngredientSecondElementMovesCorrectly() {
        burger.setBuns(bunMock);
        burger.addIngredient(cutlet);
        burger.addIngredient(hotSauce);
        burger.addIngredient(dinosaurMeat);

        burger.removeIngredient(1);
        assertEquals(dinosaurMeat, burger.ingredients.get(1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveIngredientInvalidIndex() {
        burger.setBuns(bunMock);
        burger.addIngredient(cutlet);
        burger.removeIngredient(5);
    }

    @Test
    public void testMoveIngredientFromEndToStart() {
        burger.setBuns(bunMock);
        burger.addIngredient(cutlet);
        burger.addIngredient(hotSauce);
        burger.addIngredient(dinosaurMeat);

        burger.moveIngredient(2, 0);
        assertEquals(dinosaurMeat, burger.ingredients.get(0));
    }

    @Test
    public void testMoveIngredientFromStartToEnd() {
        burger.setBuns(bunMock);
        burger.addIngredient(cutlet);
        burger.addIngredient(hotSauce);
        burger.addIngredient(dinosaurMeat);

        burger.moveIngredient(0, 2);
        assertEquals(cutlet, burger.ingredients.get(2));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testMoveIngredientInvalidSourceIndex() {
        burger.setBuns(bunMock);
        burger.addIngredient(cutlet);
        burger.moveIngredient(5, 0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testMoveIngredientInvalidTargetIndex() {
        burger.setBuns(bunMock);
        burger.addIngredient(cutlet);
        burger.moveIngredient(0, 5);
    }

    @Test
    public void testGetPriceWithMockedBun() {
        Mockito.when(bunMock.getPrice()).thenReturn(50.0f);

        burger.setBuns(bunMock);
        burger.addIngredient(cutlet);
        burger.addIngredient(hotSauce);

        assertEquals(300.0f, burger.getPrice(), 0.001f);
    }

    @Test
    public void testGetReceiptContainsBun() {
        Mockito.when(bunMock.getName()).thenReturn("test bun");
        burger.setBuns(bunMock);
        assertTrue(burger.getReceipt().contains("test bun"));
    }

    @Test
    public void testGetReceiptContainsIngredients() {
        burger.setBuns(bunMock);
        burger.addIngredient(cutlet);
        assertTrue(burger.getReceipt().contains("cutlet"));
    }
}