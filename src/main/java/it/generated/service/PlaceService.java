
package it.generated.service;

import java.util.List;
import it.generated.searchbean.PlaceSearchBean;

public interface PlaceService {


    public List<it.generated.domain.Place> findById(Integer PlaceId);

    public List<it.generated.domain.Place> find(PlaceSearchBean Place);

    public void deleteById(Integer PlaceId);

    public it.generated.domain.Place insert(it.generated.domain.Place Place);

    public it.generated.domain.Place update(it.generated.domain.Place Place);

}
