from pymongo import MongoClient
from datetime import datetime
import sys
import json
from pprint import pprint
##################################################################################
#                                                                                #
#   Before  running this code  start MangoDB server  on command prompt           #
#                                                                                #
#   This is working fine do not change much                                      #
#                                                                                #
#                                                                                #
##################################################################################
class connectDB:

    def __init__(self):
        pass

    def insertData(self, file):
        jsonfile = connectDB().readConvert(file)
        print('after reading json before insert')
        resultArray = connectDB().getConnection()
        db = resultArray[0]
        client = resultArray[1]
        result = db.fontmetrics.save(jsonfile)
    #    result = db.producturl.insert_one(jsonfile)
        client.close()
        return result


    def readConvert(self, file):
        try:
            print('Reading from input')
            with open(file, 'r') as f:
                data = json.load(f)
        finally:
            f.close()
            print('Done reading')
      #      print(data)

        return data

    def getConnection(self):
        try:
            print('connect 1 ')
            #ec2-18-220-219-226.us-east-2.compute.amazonaws.com
            client = MongoClient("mongodb://127.0.0.1:27017")
#            client = MongoClient("mongodb://ec2-18-220-219-226.us-east-2.compute.amazonaws.com:48434")
#            client = MongoClient("mongodb://ec2-18-219-32-189.us-east-2.compute.amazonaws.com:27017")

            print('databases -->', client.database_names())
            db = client.krishnadb
            print('db connected ')
            print('collections -->',  db.collection_names(include_system_collections=False))
        finally:
            print('Connection Succeeded')
        return db, client

    def retriveData(self,collName):
        print('retrieve data')
        retArray = connectDB().getConnection()
        db = retArray[0]
        client= retArray[1]
        db.collection_names(include_system_collections=False)
        print('before collection ')
#        db = resultArray[0]
        collection = db[collName]
        print('after collection ')

        cursor = collection.find({})
        print('after cursor ')

        if (collection.count() > 0) :
            for document in cursor:
                print(document)
        else:
            print(' collection empty')
        client.close()

    def updateData(self, key, data):
        print('db update 1')
        retArray = connectDB().getConnection()
        db = retArray[0]
        client = retArray[1]
        db.collection_names(include_system_collections=False)
        print('before update   ')
        collection = db.fontmetrics
        print(data)
 #       collection.update(data)
#        collection.find_one_and_update(key,data, upsert=False)
        db.fontmetrics.update(key,data,  upsert=False)
        client.close()

    def getSpecificData(self, key1, colName):
            print('get specific data')
            retArray = connectDB().getConnection()
            db = retArray[0]
            client = retArray[1]
            db.collection_names(include_system_collections=False)
            print(colName)

            print('before collection ')
            collection = db[colName]
            print('before find key ')

            print(key1)
            cursor = collection.find( key1 )
#{'data': 1, 'size': 1, 'style': 1, 'name': 1, 'hOffset': 1, 'vOffset': 1, 'alignment': 1, 'onMouseUp': 1}

            print('after cursor ')
#            print('collection count',  collection.count())

            if (collection.count() > 0):
                for document in cursor:
                    print(document)
            else:
                print(' collection empty')
            client.close()

    def deleteData(self,collName):
        resultArray = connectDB().getConnection()
        print("result array ")
        db = resultArray[0]
        client = resultArray[1]
        print("client component ", db)
#        db.fontmetrics.remove({ "widget": { "window.title" : "sample Konfabulator Widget" } })
        db[collName].remove({ })
        print("successfully deleted ")
        client.close()

if __name__ == "__main__":
     d = connectDB()

#    key = {'window.width' : 500}
#    key = { "debug": "on", "window.width": 500}
     key1 = {"name": "Panties"}  ;
     d.getSpecificData(key1, "girls")

#key = {"widget.window.title": "test", "widget.text.data": "Click Here", "widget.image.src": "Images/Sun.png"
#       }
#    data = {"$set": {"widget.window.width": 100, "widget.window.height": 100, "widget.text.hOffset": 200,
#                     "widget.text.vOffset": 200, "widget.image.hOffset": 555, "widget.image.vOffset": 555
#                     }}

#    data = {"$unset": {"widget.window": '', "widget.text": '',"widget.image": ''}}
#    data = {"$unset": {"widget": ''}}

#     d.insertData("json.txt")
#    d.retriveData("employee")
#    d.retriveData("fontmetrics")
#   d.updateData(key, data)
 #   d.deleteData("employee")
 #   d.retriveData("employee")
