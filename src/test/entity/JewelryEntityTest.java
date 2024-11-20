package test.entity;

import doa_jewelry.entity.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class JewelryEntityTest {

    @Test
    public void testRingInitialization() {
        Ring ring = new Ring("Diamond Ring", MaterialType.DIAMOND, 5.0, 1500.0, 10, JewelryCategory.LUXURY, 6.5);

        assertEquals("Diamond Ring", ring.getName());
        assertEquals(MaterialType.DIAMOND, ring.getMaterial());
        assertEquals(5.0, ring.getWeight(), 0.01);
        assertEquals(1500.0, ring.getPrice(), 0.01);
        assertEquals(10, ring.getStockQuantity());
        assertEquals(JewelryCategory.LUXURY, ring.getCategory());
        assertEquals(6.5, ring.getSize(), 0.01);
    }

    @Test
    public void testNecklaceInitialization() {
        Necklace necklace = new Necklace("Gold Necklace", MaterialType.GOLD, 10.0, 2000.0, 5, JewelryCategory.LUXURY, 18.0);

        assertEquals("Gold Necklace", necklace.getName());
        assertEquals(MaterialType.GOLD, necklace.getMaterial());
        assertEquals(10.0, necklace.getWeight(), 0.01);
        assertEquals(2000.0, necklace.getPrice(), 0.01);
        assertEquals(5, necklace.getStockQuantity());
        assertEquals(JewelryCategory.LUXURY, necklace.getCategory());
        assertEquals(18.0, necklace.getLength(), 0.01);
    }
}
