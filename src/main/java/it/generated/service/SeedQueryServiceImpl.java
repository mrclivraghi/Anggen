
package it.generated.service;

import java.util.List;
import it.generated.repository.SeedQueryRepository;
import it.generated.searchbean.SeedQuerySearchBean;
import it.generated.service.SeedQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SeedQueryServiceImpl
    implements SeedQueryService
{

    @Autowired
    public SeedQueryRepository seedQueryRepository;

    @Override
    public List<it.generated.domain.SeedQuery> findById(Integer seedQueryId) {
        return seedQueryRepository.findBySeedQueryId(seedQueryId);
    }

    @Override
    public List<it.generated.domain.SeedQuery> find(SeedQuerySearchBean seedQuery) {
        return seedQueryRepository.findBySeedQueryIdAndNameAndMountain(seedQuery.getSeedQueryId(),seedQuery.getName(),seedQuery.getMountain());
    }

    @Override
    public void deleteById(Integer seedQueryId) {
        seedQueryRepository.delete(seedQueryId);
        return;
    }

    @Override
    public it.generated.domain.SeedQuery insert(it.generated.domain.SeedQuery seedQuery) {
        return seedQueryRepository.save(seedQuery);
    }

    @Override
    @Transactional
    public it.generated.domain.SeedQuery update(it.generated.domain.SeedQuery seedQuery) {
        it.generated.domain.SeedQuery returnedSeedQuery=seedQueryRepository.save(seedQuery);
        if (seedQuery.getMountain()!=null)
        {
        List<it.generated.domain.SeedQuery> seedQueryList = seedQueryRepository.findByMountain( seedQuery.getMountain());
        if (!seedQueryList.contains(returnedSeedQuery))
        seedQueryList.add(returnedSeedQuery);
        returnedSeedQuery.getMountain().setSeedQueryList(seedQueryList);
        }
         return returnedSeedQuery;
    }

}
