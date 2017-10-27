package com.restful.boot;

import com.restful.boot.Country;
import com.restful.boot.ICountryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
public class CountryController {

    @Autowired
    private ICountryService countryService;

 //   @RequestMapping("/countries")
    
    @RequestMapping(value="/countries", method=RequestMethod.GET, 
    produces=MediaType.APPLICATION_XML_VALUE )
    
    
    public List<Country> listCountries() {
        int Id =  0;
        return countryService.findCountries(Id);
    }
}
