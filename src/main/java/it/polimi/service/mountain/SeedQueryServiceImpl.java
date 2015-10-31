
package it.polimi.service.mountain;

import java.util.List;
import it.polimi.model.mountain.SeedQuery;
import it.polimi.repository.mountain.PhotoRepository;
import it.polimi.repository.mountain.SeedQueryRepository;
import it.polimi.searchbean.mountain.SeedQuerySearchBean;
import it.polimi.service.mountain.SeedQueryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SeedQueryServiceImpl
    implements SeedQueryService
{

    @org.springframework.beans.factory.annotation.Autowired
    public SeedQueryRepository seedQueryRepository;
    @org.springframework.beans.factory.annotation.Autowired
    public PhotoRepository photoRepository;

    @Override
    public List<SeedQuery> findById(Long seedQueryId) {
        return seedQueryRepository.findBySeedQueryId(seedQueryId);
    }

    @Override
    public List<SeedQuery> find(SeedQuerySearchBean seedQuery) {
        return seedQueryRepository.findBySeedQueryIdAndSeedKeywordAndStatusAndMountainAndHeightAndPhoto(seedQuery.getSeedQueryId(),seedQuery.getSeedKeyword(),seedQuery.getStatus(),seedQuery.getMountain(),seedQuery.getMountainHeight(),seedQuery.getPhotoList()==null? null :seedQuery.getPhotoList().get(0));
    }

    @Override
    public void deleteById(Long seedQueryId) {
        seedQueryRepository.delete(seedQueryId);
        return;
    }

    @Override
    public SeedQuery insert(SeedQuery seedQuery) {
        return seedQueryRepository.save(seedQuery);
    }

    @Override
    @Transactional
    public SeedQuery update(SeedQuery seedQuery) {
        if (seedQuery.getPhotoList()!=null)
        for (it.polimi.model.mountain.Photo photo: seedQuery.getPhotoList())
        {
        photo.setSeedQuery(seedQuery);
        }
        SeedQuery returnedSeedQuery=seedQueryRepository.save(seedQuery);
        if (seedQuery.getMountain()!=null)
        {
        List<SeedQuery> seedQueryList = seedQueryRepository.findByMountain( seedQuery.getMountain());
        if (!seedQueryList.contains(returnedSeedQuery))
        seedQueryList.add(returnedSeedQuery);
        returnedSeedQuery.getMountain().setSeedQueryList(seedQueryList);
        }
         return returnedSeedQuery;
    }

}
