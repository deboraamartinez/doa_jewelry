//package doa_jewelry.service;
//
//import doa_jewelry.entity.Jewelry;
//import doa_jewelry.exception.RepositoryException;
//import doa_jewelry.repository.JewelryRepository;
//
//import java.util.List;
//
//public class JewelryService {
//    private final JewelryRepository jewelryRepository = new JewelryRepository();
//
//    public Jewelry createJewelry(Jewelry jewelry) throws RepositoryException {
//        return jewelryRepository.save(jewelry);
//    }
//
//    public Jewelry getJewelryById(Long id) throws RepositoryException {
//        return jewelryRepository.findById(id)
//                .orElseThrow(() -> new doa_jewelry.exception.EntityNotFoundException(Jewelry.class));
//    }
//
//    public List<Jewelry> getAllJewelry() throws RepositoryException {
//        return jewelryRepository.findAll();
//    }
//
//    public void deleteJewelry(Long id) throws RepositoryException {
//        jewelryRepository.deleteById(id);
//    }
//
//    public Jewelry updateJewelry(Jewelry jewelry) throws RepositoryException {
//        return jewelryRepository.update(jewelry);
//    }
//}
