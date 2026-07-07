package praktikum;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class BurgerPriceParametrizedTest {

    private final float expectedPrice;
    private final int bunPrice;
    private final int[] ingredientPrices;

    public BurgerPriceParametrizedTest(float expectedPrice, int bunPrice, int[] ingredientPrices) {
        this.expectedPrice = expectedPrice;
        this.bunPrice = bunPrice;
        this.ingredientPrices = ingredientPrices;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { 300.0f, 50, new int[]{100, 100} },
                { 400.0f, 100, new int[]{50, 50, 100} },
                { 200.0f, 70, new int[]{60} }
        });
    }

    @Test
    public void testGetPriceWithParameters() {
        Burger burger = new Burger();
        Bun bunMock = Mockito.mock(Bun.class);
        Mockito.when(bunMock.getPrice()).thenReturn((float) bunPrice);
        Mockito.when(bunMock.getName()).thenReturn("test bun");

        burger.setBuns(bunMock);

        for (int price : ingredientPrices) {
            Ingredient ing = new Ingredient(
                    IngredientType.FILLING, "test", price
            );
            burger.addIngredient(ing);
        }

        float actual = burger.getPrice();
        assertEquals(expectedPrice, actual, 0.001f);
    }
}
