
package it.generated.service;

import java.util.List;
import it.generated.searchbean.MountainSearchBean;

public interface MountainService {


    public List<it.generated.domain.Mountain> findById(Integer MountainId);

    public List<it.generated.domain.Mountain> find(MountainSearchBean Mountain);

    public void deleteById(Integer MountainId);

    public it.generated.domain.Mountain insert(it.generated.domain.Mountain Mountain);

    public it.generated.domain.Mountain update(it.generated.domain.Mountain Mountain);

}
