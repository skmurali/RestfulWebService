package com.restful.boot;



public class Country {

	
	private int Id;
	private String name;
	private int population;

    public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}


    public Country() {
    }

    public Country(int Id, String name, int population) {
        this.Id  = Id;
        this.name = name;
        this.population = population;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }
}