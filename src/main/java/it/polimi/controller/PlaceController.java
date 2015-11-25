
package it.polimi.controller;

import java.util.List;
import it.polimi.searchbean.PlaceSearchBean;
import it.polimi.service.PlaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/place")
public class PlaceController {

    @Autowired
    public PlaceService placeService;
    private final static Logger log = LoggerFactory.getLogger(it.polimi.domain.Place.class);

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        return "place";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        PlaceSearchBean place) {
        List<it.polimi.domain.Place> placeList;
        if (place.getPlaceId()!=null)
         log.info("Searching place like {}",place.toString());
        placeList=placeService.find(place);
        getRightMapping(placeList);
         log.info("Search: returning {} place.",placeList.size());
        return ResponseEntity.ok().body(placeList);
    }

    @ResponseBody
    @RequestMapping(value = "/{placeId}", method = RequestMethod.GET)
    public ResponseEntity getPlaceById(
        @PathVariable
        String placeId) {
        log.info("Searching place with id {}",placeId);
        List<it.polimi.domain.Place> placeList=placeService.findById(Integer.valueOf(placeId));
        getRightMapping(placeList);
         log.info("Search: returning {} place.",placeList.size());
        return ResponseEntity.ok().body(placeList);
    }

    @ResponseBody
    @RequestMapping(value = "/{placeId}", method = RequestMethod.DELETE)
    public ResponseEntity deletePlaceById(
        @PathVariable
        String placeId) {
        log.info("Deleting place with id {}",placeId);
        placeService.deleteById(Integer.valueOf(placeId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertPlace(
        @org.springframework.web.bind.annotation.RequestBody
        it.polimi.domain.Place place) {
        if (place.getPlaceId()!=null)
        log.info("Inserting place like {}",place.toString());
        it.polimi.domain.Place insertedPlace=placeService.insert(place);
        getRightMapping(insertedPlace);
        log.info("Inserted place with id {}",insertedPlace.getPlaceId());
        return ResponseEntity.ok().body(insertedPlace);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updatePlace(
        @org.springframework.web.bind.annotation.RequestBody
        it.polimi.domain.Place place) {
        log.info("Updating place with id {}",place.getPlaceId());
        it.polimi.domain.Place updatedPlace=placeService.update(place);
        getRightMapping(updatedPlace);
        return ResponseEntity.ok().body(updatedPlace);
    }

    private List<it.polimi.domain.Place> getRightMapping(List<it.polimi.domain.Place> placeList) {
        for (it.polimi.domain.Place place: placeList)
        {
        getRightMapping(place);
        }
        return placeList;
    }

    private void getRightMapping(it.polimi.domain.Place place) {
        if (place.getExample()!=null)
        {
        place.getExample().setPlaceList(null);
        }
    }

}
