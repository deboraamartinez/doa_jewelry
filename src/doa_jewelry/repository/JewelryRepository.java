package doa_jewelry.repository;

import doa_jewelry.entity.*;
import doa_jewelry.exception.EntityAlreadyExistsException;
import doa_jewelry.exception.EntityNotFoundException;
import doa_jewelry.exception.RepositoryException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JewelryRepository extends MyCrudRepository<Jewelry> {

    private static final String FILE_PATH = "data/jewelry.csv";
    private final List<Jewelry> jewelries = new ArrayList<>();

    public JewelryRepository() {
        loadFromFile();
    }

    @Override
    public Jewelry save(Jewelry jewelry) throws RepositoryException {
        if (jewelry.getId() == null) {
            Long newId = jewelries.stream()
                    .mapToLong(j -> j.getId() != null ? j.getId() : 0L)
                    .max()
                    .orElse(0L) + 1;
            jewelry.setId(newId);
        }
        jewelries.add(jewelry);
        return jewelry;
    }

    @Override
    public Jewelry update(Jewelry jewelry) throws RepositoryException {
        if (jewelry.getId() == null) {
            throw new RepositoryException("Jewelry ID cannot be null for update.");
        }

        Optional<Jewelry> existingJewelryOpt = findById(jewelry.getId());
        if (existingJewelryOpt.isPresent()) {
            Jewelry existingJewelry = existingJewelryOpt.get();

            existingJewelry.setName(jewelry.getName());
            existingJewelry.setMaterial(jewelry.getMaterial());
            existingJewelry.setWeight(jewelry.getWeight());
            existingJewelry.setPrice(jewelry.getPrice());
            existingJewelry.setStockQuantity(jewelry.getStockQuantity());
            existingJewelry.setCategory(jewelry.getCategory());

            if (existingJewelry instanceof Necklace && jewelry instanceof Necklace) {
                ((Necklace) existingJewelry).setLength(((Necklace) jewelry).getLength());
            } else if (existingJewelry instanceof Ring && jewelry instanceof Ring) {
                ((Ring) existingJewelry).setSize(((Ring) jewelry).getSize());
            } else if (existingJewelry instanceof Earring && jewelry instanceof Earring) {
                ((Earring) existingJewelry).setClaspType(((Earring) jewelry).getClaspType());
            } else {
                throw new RepositoryException("Jewelry type does not match the existing one.");
            }

            // Removed saveToFile() call
            return existingJewelry;
        } else {
            throw new EntityNotFoundException("Jewelry not found with ID: " + jewelry.getId());
        }
    }

    @Override
    public void deleteById(Long id) throws RepositoryException {
        Jewelry jewelry = findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Jewelry not found with ID: " + id));
        jewelries.remove(jewelry);
        // Removed saveToFile() call
    }

    @Override
    public List<Jewelry> findAll() {
        return new ArrayList<>(jewelries);
    }

    @Override
    public Optional<Jewelry> findById(Long id) {
        return jewelries.stream().filter(j -> j.getId().equals(id)).findFirst();
    }

    private void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists())
            return;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 8) {
                    throw new RuntimeException("Insufficient data in line: " + line);
                }

                String type = data[0].trim();
                Long id = Long.parseLong(data[1].trim());
                String name = data[2].trim();
                MaterialType material = MaterialType.valueOf(data[3].trim().toUpperCase());
                double weight = Double.parseDouble(data[4].trim());
                double price = Double.parseDouble(data[5].trim());
                int stockQuantity = Integer.parseInt(data[6].trim());
                JewelryCategory category = JewelryCategory.valueOf(data[7].trim().toUpperCase());

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

                if (jewelry instanceof Necklace) {
                    Necklace necklace = (Necklace) jewelry;
                    sb.append(",").append(necklace.getLength());
                } else if (jewelry instanceof Ring) {
                    Ring ring = (Ring) jewelry;
                    sb.append(",").append(ring.getSize());
                } else if (jewelry instanceof Earring) {
                    Earring earring = (Earring) jewelry;
                    sb.append(",").append(earring.getClaspType());
                }

                bw.write(sb.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RepositoryException("Error saving jewelry to file", e);
        }
    }

    public void saveAll() throws RepositoryException {
        saveToFile();
    }

    public void deleteAll() {
        jewelries.clear();
        saveToFile();
    }

}
