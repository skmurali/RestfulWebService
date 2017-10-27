package com.restful.boot;

import com.opencsv.CSVReader;
import com.restful.boot.Country;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

@Service
public class CountryService implements ICountryService 
{

    private final ArrayList<Country> countries;

    
    FileInputStream fis = null;
    
    
    
    public CountryService() 
    {
        countries = new ArrayList();
    }

    @Override
    public ArrayList<Country> findCountries(int Id) {

      Id = 0 ;
    	
        FileInputStream fis = null;

        try {

            String fileName = "src/main/resources/countries.csv";

            fis = new FileInputStream(new File(fileName));
            CSVReader reader = new CSVReader(new InputStreamReader(fis));
            String[] nextLine;
            reader.readNext();
            
            while ((nextLine = reader.readNext()) != null) {

            	int id = Integer.valueOf(nextLine[0]) ;
            	if ((Id < 1) ) {
                    Country newCountry = new Country(id, nextLine[1],
                    		Integer.valueOf(nextLine[2]));
                    countries.add(newCountry);
            	}
            	else if (id == Id) {
                    Country newCountry = new Country(id, nextLine[1],
                    		Integer.valueOf(nextLine[2]));
                    countries.add(newCountry);

                    break ;
            	}
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(CountryService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CountryService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(CountryService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return countries;
   }    

}


