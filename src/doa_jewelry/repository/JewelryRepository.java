//package doa_jewelry.repository;
//
//import doa_jewelry.entity.Jewelry;
//import doa_jewelry.exception.EntityAlreadyExistsException;
//import doa_jewelry.exception.EntityNotFoundException;
//import doa_jewelry.exception.RepositoryException;
//
//public class JewelryRepository extends MyCrudRepository<Jewelry> {
//
//    @Override
//    public Jewelry save(Jewelry jewelry) throws EntityAlreadyExistsException {
//        if (jewelry.getId() != null && existsById(jewelry.getId())) {
//            throw new EntityAlreadyExistsException(Jewelry.class);
//        }
//        Jewelry savedJewelry = super.save(jewelry);
//        saveToFile();
//        return savedJewelry;
//    }
//
//    @Override
//    public Jewelry update(Jewelry jewelry) throws EntityNotFoundException {
//        if (!existsById(jewelry.getId())) {
//            throw new EntityNotFoundException(Jewelry.class);
//        }
//        Jewelry updatedJewelry = super.update(jewelry);
//        saveToFile();
//        return updatedJewelry;
//    }
//
//    @Override
//    public void deleteById(Long id) throws EntityNotFoundException {
//        if (!existsById(id)) {
//            throw new EntityNotFoundException(Jewelry.class);
//        }
//        super.deleteById(id);
//        saveToFile();
//    }
//
//    // Implement loadFromFile() and saveToFile()
//}
