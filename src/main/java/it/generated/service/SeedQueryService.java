
package it.generated.service;

import java.util.List;
import it.generated.searchbean.SeedQuerySearchBean;

public interface SeedQueryService {


    public List<it.generated.domain.SeedQuery> findById(Integer SeedQueryId);

    public List<it.generated.domain.SeedQuery> find(SeedQuerySearchBean SeedQuery);

    public void deleteById(Integer SeedQueryId);

    public it.generated.domain.SeedQuery insert(it.generated.domain.SeedQuery SeedQuery);

    public it.generated.domain.SeedQuery update(it.generated.domain.SeedQuery SeedQuery);

}
