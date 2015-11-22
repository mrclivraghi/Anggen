
package it.generated.service;

import java.util.List;
import it.generated.repository.MountainRepository;
import it.generated.repository.SeedQueryRepository;
import it.generated.searchbean.MountainSearchBean;
import it.generated.service.MountainService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MountainServiceImpl
    implements MountainService
{

    @org.springframework.beans.factory.annotation.Autowired
    public MountainRepository mountainRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public SeedQueryRepository mountain_seedqueryRepository;

    @Override
    public List<it.generated.domain.Mountain> findById(Integer mountainId) {
        return mountainRepository.findByMountainId(mountainId);
    }

    @Override
    public List<it.generated.domain.Mountain> find(MountainSearchBean mountain) {
        return mountainRepository.findByMountainIdAndNameAndSeedQuery(mountain.getMountainId(),mountain.getName(),mountain.getSeedQueryList()==null? null :mountain.getSeedQueryList().get(0));
    }

    @Override
    public void deleteById(Integer mountainId) {
        mountainRepository.delete(mountainId);
        return;
    }

    @Override
    public it.generated.domain.Mountain insert(it.generated.domain.Mountain mountain) {
        return mountainRepository.save(mountain);
    }

    @Override
    @Transactional
    public it.generated.domain.Mountain update(it.generated.domain.Mountain mountain) {
        if (mountain.getSeedQueryList()!=null)
        for (it.generated.domain.SeedQuery seedQuery: mountain.getSeedQueryList())
        {
        seedQuery.setMountain(mountain);
        }
        it.generated.domain.Mountain returnedMountain=mountainRepository.save(mountain);
         return returnedMountain;
    }

}
