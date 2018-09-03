# Agoda

Source code is available at :
https://github.com/nkr28/Agoda



Language: JAVA
Application Type:SpringBoot

RateLimiting API is implemented using interceptor.
RateLimiting is implemented using Queue, Queue of timestamp hit is maintained for each endpoint. 
Once queue is full timestamp is maintained for that Queue, Api returns status 429 for defined suspension period. 



To execute
------------
1.Create folder<agoda>.
2.Place jar , hoteldb.csv and application.properties in same folder<agoda>.
3.Execute  command java -jar  rateLimitingApi-0.0.1-SNAPSHOT.jar from this folder.


Configuration
application.properties
---------------------
#Enable rate hit limit
rate.limit.enabled=true     

#city hit limit
city.hit.limit=5
#city hit duration
city.hit.duration=10
#city hit suspension period
city.suspension.duration=5

#ROOM hit limit
hotel.hit.limit=5
#ROOM hit duration
hotel.hit.duration=10
#ROOM hit suspension period
hotel.suspension.duration=5 




Test Cases:
---------
Agoda/agoda/src/test/java/com/agoda/AgodaApplicationTests.java

Two test cases:
1. Integration test:
Starts server at random port
testResultForCity(): 
Makes http call to application, to check results available for city.

2. Api Hit Rate Test
testRate()
make series of hits in defined timeWindow, to receive HTTP.status 429.




-----------------------------------------

Sample O/P
1.http://localhost:8400/agoda/city/Bangkok?sort=desc

[{"id":14,"city":"Bangkok","room":"Sweet Suite","price":25000},{"id":18,"city":"Bangkok","room":"Sweet Suite","price":5300},{"id":8,"city":"Bangkok","room":"Superior","price":2400},{"id":6,"city":"Bangkok","room":"Superior","price":2000},{"id":1,"city":"Bangkok","room":"Deluxe","price":1000},{"id":15,"city":"Bangkok","room":"Deluxe","price":900},{"id":11,"city":"Bangkok","room":"Deluxe","price":60}]


2. http://localhost:8400/agoda/room/Deluxe

[{"id":11,"city":"Bangkok","room":"Deluxe","price":60},{"id":15,"city":"Bangkok","room":"Deluxe","price":900},{"id":1,"city":"Bangkok","room":"Deluxe","price":1000},{"id":7,"city":"Ashburn","room":"Deluxe","price":1600},{"id":12,"city":"Ashburn","room":"Deluxe","price":1800},{"id":25,"city":"Ashburn","room":"Deluxe","price":1900},{"id":4,"city":"Amsterdam","room":"Deluxe","price":2200},{"id":26,"city":"Amsterdam","room":"Deluxe","price":2300},{"id":17,"city":"Ashburn","room":"Deluxe","price":2800},{"id":23,"city":"Amsterdam","room":"Deluxe","price":5000},{"id":21,"city":"Ashburn","room":"Deluxe","price":7000}]





