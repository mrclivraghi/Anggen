
package it.polimi.service;

import java.util.List;
import it.polimi.searchbean.PlaceSearchBean;

public interface PlaceService {


    public List<it.polimi.domain.Place> findById(Integer PlaceId);

    public List<it.polimi.domain.Place> find(PlaceSearchBean Place);

    public void deleteById(Integer PlaceId);

    public it.polimi.domain.Place insert(it.polimi.domain.Place Place);

    public it.polimi.domain.Place update(it.polimi.domain.Place Place);

}
