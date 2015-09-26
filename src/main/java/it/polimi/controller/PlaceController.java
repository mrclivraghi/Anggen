
package it.polimi.controller;

import java.util.List;
import it.polimi.model.Place;
import it.polimi.service.PlaceService;
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

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        return "place";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @RequestBody
        Place place) {
        List<Place> placeList;
        placeList=placeService.find(place);
        return ResponseEntity.ok().body(placeList);
    }

    @ResponseBody
    @RequestMapping(value = "/{placeId}", method = RequestMethod.GET)
    public ResponseEntity getplaceById(
        @PathVariable
        String placeId) {
        return ResponseEntity.ok().body(placeService.findById(Long.valueOf(placeId)));
    }

    @ResponseBody
    @RequestMapping(value = "/{placeId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteplaceById(
        @PathVariable
        String placeId) {
        placeService.deleteById(Long.valueOf(placeId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertplace(
        @RequestBody
        Place place) {
        Place insertedplace=placeService.insert(place);
        return ResponseEntity.ok().body(insertedplace);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateplace(
        @RequestBody
        Place place) {
        Place updatedplace=placeService.update(place);
        return ResponseEntity.ok().body(updatedplace);
    }

}
