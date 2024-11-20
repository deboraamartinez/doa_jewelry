package doa_jewelry.repository;

import doa_jewelry.entity.*;
import doa_jewelry.exception.EntityAlreadyExistsException;
import doa_jewelry.exception.EntityNotFoundException;
import doa_jewelry.exception.RepositoryException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Repository for managing jewelry data, including persistence to a CSV file
public class JewelryRepository extends MyCrudRepository<Jewelry> {

    private static final String FILE_PATH = "data/jewelry.csv"; // Path to the CSV file
    private final List<Jewelry> jewelries = new ArrayList<>(); // In-memory list of jewelry items

    // Constructor loads jewelry data from the CSV file into memory
    public JewelryRepository() {
        loadFromFile();
    }

    @Override
    public Jewelry save(Jewelry jewelry) throws RepositoryException {
        // Assign a new ID if the jewelry item doesn't have one
        if (jewelry.getId() == null) {
            Long newId = jewelries.stream()
                    .mapToLong(j -> j.getId() != null ? j.getId() : 0L)
                    .max()
                    .orElse(0L) + 1;
            jewelry.setId(newId);
        }

        // Add the jewelry item to the in-memory list
        jewelries.add(jewelry);
        return jewelry;
    }

    @Override
    public Jewelry update(Jewelry jewelry) throws RepositoryException {
        // Ensure the jewelry ID is not null
        if (jewelry.getId() == null) {
            throw new RepositoryException("Jewelry ID cannot be null for update.");
        }

        // Find the existing jewelry item by ID
        Optional<Jewelry> existingJewelryOpt = findById(jewelry.getId());
        if (existingJewelryOpt.isPresent()) {
            Jewelry existingJewelry = existingJewelryOpt.get();

            // Update basic properties
            existingJewelry.setName(jewelry.getName());
            existingJewelry.setMaterial(jewelry.getMaterial());
            existingJewelry.setWeight(jewelry.getWeight());
            existingJewelry.setPrice(jewelry.getPrice());
            existingJewelry.setStockQuantity(jewelry.getStockQuantity());
            existingJewelry.setCategory(jewelry.getCategory());

            // Update specific properties based on the type of jewelry
            if (existingJewelry instanceof Necklace && jewelry instanceof Necklace) {
                ((Necklace) existingJewelry).setLength(((Necklace) jewelry).getLength());
            } else if (existingJewelry instanceof Ring && jewelry instanceof Ring) {
                ((Ring) existingJewelry).setSize(((Ring) jewelry).getSize());
            } else if (existingJewelry instanceof Earring && jewelry instanceof Earring) {
                ((Earring) existingJewelry).setClaspType(((Earring) jewelry).getClaspType());
            } else {
                throw new RepositoryException("Jewelry type does not match the existing one.");
            }

            return existingJewelry;
        } else {
            throw new EntityNotFoundException("Jewelry not found with ID: " + jewelry.getId());
        }
    }

    @Override
    public void deleteById(Long id) throws RepositoryException {
        // Find and remove the jewelry item by ID
        Jewelry jewelry = findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Jewelry not found with ID: " + id));
        jewelries.remove(jewelry);
    }

    @Override
    public List<Jewelry> findAll() {
        // Return a copy of the in-memory list to prevent external modifications
        return new ArrayList<>(jewelries);
    }

    @Override
    public Optional<Jewelry> findById(Long id) {
        // Find a jewelry item by its ID
        return jewelries.stream().filter(j -> j.getId().equals(id)).findFirst();
    }

    // Load jewelry data from the CSV file into memory
    private void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 8) {
                    throw new RuntimeException("Insufficient data in line: " + line);
                }

                // Parse basic jewelry data
                String type = data[0].trim();
                Long id = Long.parseLong(data[1].trim());
                String name = data[2].trim();
                MaterialType material = MaterialType.valueOf(data[3].trim().toUpperCase());
                double weight = Double.parseDouble(data[4].trim());
                double price = Double.parseDouble(data[5].trim());
                int stockQuantity = Integer.parseInt(data[6].trim());
                JewelryCategory category = JewelryCategory.valueOf(data[7].trim().toUpperCase());

                // Create the appropriate jewelry object based on the type
                Jewelry jewelry;
                switch (type.toLowerCase()) {
                    case "necklace":
                        if (data.length < 9) {
                            throw new RuntimeException("Insufficient data for Necklace in line: " + line);
                        }
                        double length = Double.parseDouble(data[8].trim());
                        jewelry = new Necklace(id, name, material, weight, price, stockQuantity, category, length);
                        break;
                    case "ring":
                        if (data.length < 9) {
                            throw new RuntimeException("Insufficient data for Ring in line: " + line);
                        }
                        double size = Double.parseDouble(data[8].trim());
                        jewelry = new Ring(id, name, material, weight, price, stockQuantity, category, size);
                        break;
                    case "earring":
                        if (data.length < 9) {
                            throw new RuntimeException("Insufficient data for Earring in line: " + line);
                        }
                        String claspType = data[8].trim();
                        jewelry = new Earring(id, name, material, weight, price, stockQuantity, category, claspType);
                        break;
                    default:
                        throw new RuntimeException("Unknown jewelry type: " + type);
                }

                jewelries.add(jewelry);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading jewelry from file", e);
        }
    }

    // Save all jewelry data from memory to the CSV file
    private void saveToFile() throws RepositoryException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Jewelry jewelry : jewelries) {
                StringBuilder sb = new StringBuilder();
                sb.append(jewelry.getClass().getSimpleName()).append(",")
                        .append(jewelry.getId()).append(",")
                        .append(jewelry.getName()).append(",")
                        .append(jewelry.getMaterial().name()).append(",")
                        .append(jewelry.getWeight()).append(",")
                        .append(jewelry.getPrice()).append(",")
                        .append(jewelry.getStockQuantity()).append(",")
                        .append(jewelry.getCategory().name());

                // Append additional fields based on jewelry type
                if (jewelry instanceof Necklace) {
                    sb.append(",").append(((Necklace) jewelry).getLength());
                } else if (jewelry instanceof Ring) {
                    sb.append(",").append(((Ring) jewelry).getSize());
                } else if (jewelry instanceof Earring) {
                    sb.append(",").append(((Earring) jewelry).getClaspType());
                }

                bw.write(sb.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RepositoryException("Error saving jewelry to file", e);
        }
    }

    // Save all jewelry items to the CSV file
    public void saveAll() throws RepositoryException {
        saveToFile();
    }

    // Clear all jewelry data and save an empty CSV file
    public void deleteAll() {
        jewelries.clear();
        saveToFile();
    }
}
