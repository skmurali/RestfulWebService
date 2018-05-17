package com.rest.test;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.json.JSONArray ;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;


public class MongoCRUD {

		
	  private static void deleteMongodbItem(String colName, int itemID) 
	  {
		    
			try 
			{
			    DBCollection item =  getMongoDBConnection("item") ; 
			    BasicDBObject document = new BasicDBObject();
			    document.put(colName, itemID); //override above value 2
			    WriteResult wr =  item.remove(document);

			} 
			finally 
			{
			   //item.close();
			}
		    
		    
		    
	  }
	  public void storedInMongoDB(Map documentMap ) {
		  
		    DBCollection itemCollection =  getMongoDBConnection("item") ; 

		    itemCollection.insert(new BasicDBObject(documentMap));

		    System.out.println("JSON Object has been stored  Successfully");
			DBCursor cursor = null	;
					
			try 
			{
				cursor = itemCollection.find() ;
				
			   while(cursor.hasNext()) {

			       System.out.println(cursor.next());
			       
			   }
			} 
			finally 
			{
			   cursor.close();
		       System.out.println("JSON Object has been displayed Successfully ");
			}
	  }
	  
	  public static String getItemDetails(int itemID) 
	  {	  
			
		    DBCollection itemCollection =  getMongoDBConnection("item") ; 
			DBObject predicate = new BasicDBObject("id", itemID)  ;
			System.out.println( " quer2   "  +  predicate);
			
			DBCursor myCursor =  itemCollection.find(predicate) ;
			System.out.println(" cursor " + myCursor.count()); 
			String output ="";
			while (myCursor.hasNext()) 
			{
				DBObject dbobj = myCursor.next();
				output = JSON.serialize(dbobj) ;
				
			    System.out.println("retrived row " +output);
			}
			
		    System.out.println("output " +  output);
		    System.out.println("girlObj count " +  itemCollection.getCount());

	    	return output ;
		  
	  }
	  private static void updateItem(int itemID) {
		  
		    DBCollection itemCollection =  getMongoDBConnection("item") ; 
			DBObject predicate = new BasicDBObject("id", itemID)  ;
			System.out.println( " quer2   "  +  predicate);

			BasicDBObject newDocument = new BasicDBObject();
			newDocument.append("$set", new BasicDBObject().append("id", itemID));

			BasicDBObject searchQuery = new BasicDBObject().append("id", itemID);
			WriteResult db1 =  itemCollection.update(searchQuery, newDocument);

			
	  }
	  
	  private static void insertDocument() {
		  
		  DBCollection itemCollection =  getMongoDBConnection("item") ; 
		  BasicDBObject document = new BasicDBObject();
		  document.put("id", 3);
		  document.put("name", "shrits");
		  document.put("description", "boys T-Shirts");

		  document.put("qty", 3);
		  document.put("price", 5.50);
	      WriteResult wrt = itemCollection.insert(document);
		  
	  }
	  
	  
	  public static List retrieveItemDetails() throws Exception
	  {
	       System.out.println(" MongoDB  retrieveItemDetails   ") ;
		  
		    DBCollection coll =  getMongoDBConnection("item") ; 
	  		Gson gson = new GsonBuilder().setPrettyPrinting().create();
			DBCursor cursor = null	;
			List  itemList =  new ArrayList() ;
			String json = "";
			JSONArray productArray = null;
			
			DBObject dbj = null;  
			try 
			{
				cursor = coll.find() ;
//		  	     json = gson.toJson(cursor);
			    while(cursor.hasNext()) 
			    {
					   Item itemDress = new Item() ;
                       dbj = cursor.next() ;
				       System.out.println(Integer.parseInt(dbj.get("id")+""));
				       itemDress.setId(Integer.parseInt(dbj.get("id")+"")) ;	       
				       itemDress.setName(dbj.get("name")+"") ;	       
				       itemDress.setDescription(dbj.get("description")+"") ;	       
				       itemDress.setQty(Double.parseDouble(dbj.get("qty")+"")) ;	       
				       itemDress.setPrice(Double.parseDouble(dbj.get("price")+"")) ;	 
				       itemList.add(itemDress) ;
			    }

			       System.out.println(itemList.size()) ;

			
			} 
			finally 
			{

				cursor.close();
		        System.out.println("JSON Object has been displayed Successfully ");

			}

		  
			 return  itemList ;
	  }
	  
	  private static DBCollection getMongoDBConnection(String collectionName) 
	  {

		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );		
		DB db = mongoClient.getDB( "itemdb" );		 
		DBCollection girlCollection =db.getCollection(collectionName);
		
		return girlCollection ;
	  }

	  public static void main(String[] args) throws Exception 
	  {
	           insertDocument() ;
		       getItemDetails(3) ;
	           retrieveItemDetails() ;
		       updateItem(3) ;
	           deleteMongodbItem("id", 3) ;
		  
	  }


}



