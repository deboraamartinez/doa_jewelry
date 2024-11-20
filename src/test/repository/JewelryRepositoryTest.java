package test.repository;

import doa_jewelry.entity.*;
import doa_jewelry.repository.JewelryRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class JewelryRepositoryTest {

    private JewelryRepository jewelryRepository;

    @Before
    public void setUp() {
        jewelryRepository = new JewelryRepository();
        jewelryRepository.deleteAll();
    }

    @After
    public void tearDown() {
        jewelryRepository.deleteAll();
    }

    @Test
    public void testSaveJewelry() {
        Jewelry ring = new Ring("Diamond Ring", MaterialType.DIAMOND, 5.0, 1500.0, 10, JewelryCategory.LUXURY, 6.5);
        Jewelry savedRing = jewelryRepository.save(ring);

        assertNotNull(savedRing.getId());
        assertEquals(1, jewelryRepository.findAll().size());
    }

    @Test
    public void testFindById() {
        Jewelry necklace = new Necklace("Gold Necklace", MaterialType.GOLD, 10.0, 2000.0, 5, JewelryCategory.LUXURY, 18.0);
        Jewelry savedNecklace = jewelryRepository.save(necklace);

        Jewelry retrievedNecklace = jewelryRepository.findById(savedNecklace.getId()).orElse(null);

        assertNotNull(retrievedNecklace);
        assertEquals(savedNecklace.getId(), retrievedNecklace.getId());
    }

    @Test
    public void testDeleteJewelry() {
        Jewelry necklace = new Necklace("Gold Necklace", MaterialType.GOLD, 10.0, 2000.0, 5, JewelryCategory.LUXURY, 18.0);
        Jewelry savedNecklace = jewelryRepository.save(necklace);

        jewelryRepository.deleteById(savedNecklace.getId());

        assertTrue(jewelryRepository.findAll().isEmpty());
    }
}
