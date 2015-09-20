
package it.polimi.service;

import java.util.List;
import it.polimi.model.Place;
import it.polimi.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaceServiceImpl
    implements PlaceService
{

    @Autowired
    public PlaceRepository placeRepository;

    @Override
    public Place findById(Long placeId) {
        return placeRepository.findByPlaceId(placeId);
    }

    @Override
    public List<Place> find(Place place) {
        return placeRepository.findByPlaceIdAndDescription(place.getPlaceId(),place.getDescription());
    }

    @Override
    public void deleteById(Long placeId) {
        placeRepository.delete(placeId);
        return;
    }

    @Override
    public Place insert(Place place) {
        return placeRepository.save(place);
    }

    @Override
    public Place update(Place place) {
        return placeRepository.save(place);
    }

}
