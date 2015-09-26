
package it.polimi.service;

import java.util.List;
import it.polimi.model.Place;

public interface PlaceService {


    public List<Place> findById(Long placeId);

    public List<Place> find(Place place);

    public void deleteById(Long placeId);

    public Place insert(Place place);

    public Place update(Place place);

}
