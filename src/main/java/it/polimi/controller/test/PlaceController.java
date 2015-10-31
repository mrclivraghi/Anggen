
package it.polimi.controller.test;

import java.util.List;
import it.polimi.model.test.Place;
import it.polimi.searchbean.test.PlaceSearchBean;
import it.polimi.service.test.PlaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/place")
public class PlaceController {

    @Autowired
    public PlaceService placeService;
    private final static Logger log = LoggerFactory.getLogger(Place.class);

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        return "place";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @RequestBody
        PlaceSearchBean place) {
        List<Place> placeList;
        if (place.getPlaceId()!=null)
         log.info("Searching place like {}",place.toString());
        placeList=placeService.find(place);
        getRightMapping(placeList);
         log.info("Search: returning {} place.",placeList.size());
        return ResponseEntity.ok().body(placeList);
    }

    @ResponseBody
    @RequestMapping(value = "/{placeId}", method = RequestMethod.GET)
    public ResponseEntity getplaceById(
        @PathVariable
        String placeId) {
        log.info("Searching place with id {}",placeId);
        List<Place> placeList=placeService.findById(java.lang.Long.valueOf(placeId));
        getRightMapping(placeList);
         log.info("Search: returning {} place.",placeList.size());
        return ResponseEntity.ok().body(placeList);
    }

    @ResponseBody
    @RequestMapping(value = "/{placeId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteplaceById(
        @PathVariable
        String placeId) {
        log.info("Deleting place with id {}",placeId);
        placeService.deleteById(java.lang.Long.valueOf(placeId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertplace(
        @RequestBody
        Place place) {
        log.info("Inserting place like {}",place.toString());
        Place insertedplace=placeService.insert(place);
        getRightMapping(insertedplace);
        log.info("Inserted place with id {}",insertedplace.getPlaceId());
        return ResponseEntity.ok().body(insertedplace);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateplace(
        @RequestBody
        Place place) {
        log.info("Updating place with id {}",place.getPlaceId());
        Place updatedplace=placeService.update(place);
        getRightMapping(updatedplace);
        return ResponseEntity.ok().body(updatedplace);
    }

    private List<Place> getRightMapping(List<Place> placeList) {
        for (Place place: placeList)
        {
        getRightMapping(place);
        }
        return placeList;
    }

    private void getRightMapping(Place place) {
        if (place.getOrder()!=null)
        {
        place.getOrder().setPerson(null);
        place.getOrder().setPlaceList(null);
        }
    }

}
