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
    public void testAddIngredientWithNull_IncreasesSize() {
        burger.setBuns(bunMock);
        int initialSize = burger.ingredients.size();
        burger.addIngredient(null);
        assertEquals(initialSize + 1, burger.ingredients.size());
    }

    @Test
    public void testAddIngredientWithNull_AddsNull() {
        burger.setBuns(bunMock);
        burger.addIngredient(null);
        assertNull(burger.ingredients.get(burger.ingredients.size() - 1));
    }

    @Test
    public void testAddIngredients_SizeAfterAdding() {
        burger.setBuns(bunMock);
        burger.addIngredient(cutlet);
        burger.addIngredient(hotSauce);
        burger.addIngredient(dinosaurMeat);
        assertEquals(3, burger.ingredients.size());
    }

    @Test
    public void testAddIngredients_ContainsCutlet() {
        burger.setBuns(bunMock);
        burger.addIngredient(cutlet);
        assertTrue(burger.ingredients.contains(cutlet));
    }

    @Test
    public void testAddIngredients_ContainsHotSauce() {
        burger.setBuns(bunMock);
        burger.addIngredient(hotSauce);
        assertTrue(burger.ingredients.contains(hotSauce));
    }

    @Test
    public void testAddIngredients_ContainsDinosaurMeat() {
        burger.setBuns(bunMock);
        burger.addIngredient(dinosaurMeat);
        assertTrue(burger.ingredients.contains(dinosaurMeat));
    }
    @Test
    public void testRemoveIngredient_SizeAfterRemoval() {
        burger.setBuns(bunMock);
        burger.addIngredient(cutlet);
        burger.addIngredient(hotSauce);
        burger.addIngredient(dinosaurMeat);

        burger.removeIngredient(1);
        assertEquals(2, burger.ingredients.size());
    }

    @Test
    public void testRemoveIngredient_FirstElementRemainsUntouched() {
        burger.setBuns(bunMock);
        burger.addIngredient(cutlet);
        burger.addIngredient(hotSauce);
        burger.addIngredient(dinosaurMeat);

        burger.removeIngredient(1);
        assertEquals(cutlet, burger.ingredients.get(0));
    }

    @Test
    public void testRemoveIngredient_SecondElementMovesCorrectly() {
        burger.setBuns(bunMock);
        burger.addIngredient(cutlet);
        burger.addIngredient(hotSauce);
        burger.addIngredient(dinosaurMeat);

        burger.removeIngredient(1);
        assertEquals(dinosaurMeat, burger.ingredients.get(1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveIngredient_InvalidIndex() {
        burger.setBuns(bunMock);
        burger.addIngredient(cutlet);
        burger.removeIngredient(5);
    }

    @Test
    public void testMoveIngredient_FromEndToStart() {
        burger.setBuns(bunMock);
        burger.addIngredient(cutlet);
        burger.addIngredient(hotSauce);
        burger.addIngredient(dinosaurMeat);

        burger.moveIngredient(2, 0);
        assertEquals(dinosaurMeat, burger.ingredients.get(0));
    }

    @Test
    public void testMoveIngredient_FromStartToEnd() {
        burger.setBuns(bunMock);
        burger.addIngredient(cutlet);
        burger.addIngredient(hotSauce);
        burger.addIngredient(dinosaurMeat);

        burger.moveIngredient(0, 2);
        assertEquals(cutlet, burger.ingredients.get(2));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testMoveIngredient_InvalidSourceIndex() {
        burger.setBuns(bunMock);
        burger.addIngredient(cutlet);
        burger.moveIngredient(5, 0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testMoveIngredient_InvalidTargetIndex() {
        burger.setBuns(bunMock);
        burger.addIngredient(cutlet);
        burger.moveIngredient(0, 5);
    }

    @Test
    public void testGetPrice_WithMockedBun() {
        Mockito.when(bunMock.getPrice()).thenReturn(50.0f);

        burger.setBuns(bunMock);
        burger.addIngredient(cutlet);
        burger.addIngredient(hotSauce);

        assertEquals(300.0f, burger.getPrice(), 0.001f);
    }

    @Test
    public void testGetReceipt_ContainsBun() {
        Mockito.when(bunMock.getName()).thenReturn("test bun");
        burger.setBuns(bunMock);
        assertTrue(burger.getReceipt().contains("test bun"));
    }

    @Test
    public void testGetReceipt_ContainsIngredients() {
        burger.setBuns(bunMock);
        burger.addIngredient(cutlet);
        assertTrue(burger.getReceipt().contains("cutlet"));
    }
}