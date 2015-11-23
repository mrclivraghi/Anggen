
package it.polimi.service.mountain;

import java.util.List;
import it.polimi.model.mountain.Mountain;
import it.polimi.repository.mountain.MountainRepository;
import it.polimi.repository.mountain.SeedQueryRepository;
import it.polimi.searchbean.mountain.MountainSearchBean;
import it.polimi.service.mountain.MountainService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MountainServiceImpl
    implements MountainService
{

    @org.springframework.beans.factory.annotation.Autowired
    public MountainRepository mountainRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public SeedQueryRepository seedQueryRepository;

    @Override
    public List<Mountain> findById(Long mountainId) {
        return mountainRepository.findByMountainId(mountainId);
    }

    @Override
    public List<Mountain> find(MountainSearchBean mountain) {
        return mountainRepository.findByMountainIdAndNameAndHeightAndSeedQueryAndSeedKeyword(mountain.getMountainId(),mountain.getName(),mountain.getHeight(),mountain.getSeedQueryList()==null? null :mountain.getSeedQueryList().get(0),mountain.getSeedQuerySeedKeyword());
    }

    @Override
    public void deleteById(Long mountainId) {
        mountainRepository.delete(mountainId);
        return;
    }

    @Override
    public Mountain insert(Mountain mountain) {
        return mountainRepository.save(mountain);
    }

    @Override
    @Transactional
    public Mountain update(Mountain mountain) {
        if (mountain.getSeedQueryList()!=null)
        for (it.polimi.model.mountain.SeedQuery seedQuery: mountain.getSeedQueryList())
        {
        seedQuery.setMountain(mountain);
        }
        Mountain returnedMountain=mountainRepository.save(mountain);
         return returnedMountain;
    }

}
