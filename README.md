# URL Lookup
# Prerequisite

- Java 11 (Version - 11.0.11)
- Maven (Version - 3.6.3)

# Build & Run
Extract the zip file and go to the directory 'url-lookup'.

There is two ways to build & run the project.
#### 1st way:
Run the following command from projects root directory to build it-

`mvn clean install`

Then go to target directory & run the command to run it:

`java -jar url-lookup-0.0.1-SNAPSHOT.jar`

#### 2nd way:
Run the following command from projects root directory to build & run -

`mvn spring-boot:run`

NB - it may take time for dependency downloading.

# Test
After building, the application will run on port 8080.

I've used `curl` from `cygwin` terminal to test the project.
Two api is exposed - `/api/lookup/pretty-url` takes parameterized urls as input & return pretty urls as output
whereas `/api/lookup/param-url` takes pretty urls as input & return parameterized urls as output.

#### Test case 1:
```
$ curl -i -H "Accept: application/json" -H "Content-Type:application/json" -X GET --data '{"inputUrls":["/products", "/products?gender=female", "/products?tag=5678", "/products?gender=female&tag=123&tag=1234", "/products?brand=123"]}' "http://localhost:8080/api/lookup/pretty-url"
```
**Output:**
```
{"results":["/Fashion/","/Women/","/Boat-Shoes/","/Women/Shoes/","/Adidas/"]}
```

#### Test case 2:
```
$ curl -i -H "Accept: application/json" -H "Content-Type:application/json" -X GET --data '{"inputUrls":["/products", "/products?brand=123", "/products?gender=female&tag=123&tag=1234&tag=5678"]}' "http://localhost:8080/api/lookup/pretty-url"
```
**Output:**
```
{"results":["/Fashion/","/Adidas/","/Women/Shoes/?tag=5678"]}
```

#### Test case 3:
```
$ curl -i -H "Accept: application/json" -H "Content-Type:application/json" -X GET --data '{"inputUrls":["/products", "/notlisted?tag=456"]}' "http://localhost:8080/api/lookup/pretty-url"
```
**Output:**
```
{"results":["/Fashion/","/notlisted?tag=456"]}
```

#### Test case 4:
```
$ curl -i -H "Accept: application/json" -H "Content-Type:application/json" -X GET --data '{"inputUrls":["/Fashion/","/Women/","/Boat-Shoes/","/Women/Shoes/","/Adidas/"]}' "http://localhost:8080/api/lookup/param-url"
```
**Output:**
```
{"results":["/products","/products?gender=female","/products?tag=5678","/products?gender=female&tag=123&tag=1234","/products?brand=123"]}
```

#### Test case 5:
```
$ curl -i -H "Accept: application/json" -H "Content-Type:application/json" -X GET --data '{"inputUrls":["/Women/", "/Women/Shoes/","/Women/Shoes/Addidas"]}' "http://localhost:8080/api/lookup/param-url"
```
**Output:**
```
{"results":["/products?gender=female","/products?gender=female&tag=123&tag=1234","/products?gender=female&tag=123&tag=1234&tag=Addidas"]}
```
#### Test case 6:
```
$ curl -i -H "Accept: application/json" -H "Content-Type:application/json" -X GET --data '{"inputUrls":["/Fashion/","/NotAvailable"]}' "http://localhost:8080/api/lookup/param-url"
```
**Output:**
```
{"results":["/products","/NotAvailable"]}
```

# Framework & Libraries

- Spring framework
- H2 in-memory database (for simplicity I've used it, but in production environment, it must be RDBMS)
- Ehcache (for simplicity I've used it, but in production environment, I prefer Redis)
- JPA + Hibernate

# How it works?

- 1st of All, its a spring boot application, when it runs it first run script (`src/main/resources/data.sql`) to insert 
url mappings in database. I have assumed that in production, the mapped urls should be stored in any relational database 
like MySQL/PostgreSQL and don't need to run script.
- 2nd, it initialize ehcache and created a cache region with name `stylight_url_cache`, configuration located in `resources` directory.
In production environment it should be redis cache which run standalone and independently. And multiple instance of this
service will access same cache server if needed in scalable environment.
- 3rd, I've added a custom configuration which run once and load all mapped url from database to cache.
I didn't write much logic here as it's not main requirement but in reality may be data need to update, in that case cron
job will be added instead of this which will run as desired time. As both way mapping possible, so I put pretty as key and 
param urls as value & vice versa. 
- Now, system has data in cache which is very fast to access in terms of access from database.
- Next, when an api hit, it load desired services which handle mapping lookup separately.
- When list of url provides, it first check whole url in cache, if found then it load url in list. If not, then, it 
exclude the last part (for example pretty-url `/Women/Fashion/Addidas/` input, it should exclude `Addidas/` and try to 
find `/Women/Fashion/` in cache) and continue until find one. For param urls it exclude last tags (for example - 
`/products?tag=125&tag=589`, it exclude `tag=589` at first and try to find in cache `/products?tag=125` and continue 
until find one). System try to find best match, if not found, then original url should be return.

#### NB
- Important factor here, I have used cache for lookup, because for best matching I've to lookup many times for one
input url. So, cache server (redis, for simplicity I've used ehcache here) should have all url and will never expire.
Cache server will be center of data lookup and all instances will access it for faster delivery instead of searching 
in database. 
- Unit test - which is very important buy I didn't write unit test for time constraint. As time is very limited, so 
I didn't get chance to write any unit test.
