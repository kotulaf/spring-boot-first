this is a very simple first spring boot project

what is used:
@RestController annotation - used to mark a class as a request handler to create RESTful web services using Spring MVC.
@GetMapping annotation - used to annotate methods in the @Controller to handle HTTP GET requests.
			 "we want to get something out of our server"
@RequestMapping annotation - path attribute: setting up what directory you'll be accessing in the webserver, therefore
			     mapping a directory i guess in the app/program :D
@Autowired annotation - allows us to immediately not specify the implementation of the item that we want using DI (Dependency Injection)
			in my opinion it works similarly to a setter method. the instance will be magically created for us
			and then injected into the method.
@Service annotation - its only purpose is to inform us that it holds the business logic, it is a Spring "bean" annotation
@Entity annotation - meaning the class is correlated with a table in the database (on a deep abstract level)
@Table annotation - specifying this table is the main table, or to specify its attributes, it's not totally necessary
@Id annotation - represents a primary key of an entity
@SequenceGenerator annotation - it has 3 parameters: name (pretty self explanatory), sequenceName (same thing) and allocationSize (basically what number will it use as an auto increment)
@GeneratedValue annotation - i used 2 parameters: strategy (GenerationType.SEQUENCE - defines the type of primary key generation strategies),
			     generator (name of the sequence generator)
@Repository annotation - is used to indicate that the class provides mechanism for storagem, retrieval, search, update and delete
			 operation on objects
@Bean annotation - makes sure all dependencies are satisfied, when it's created, it makes sure everything is instantiated in the right order,
		   and nothing is left out
@Transient annotation - object with this annotation will be ignored when it comes to a database, that object will not be associated with a column in the table in the database
@PostMapping annotation - getting an object (a json from the client)
@RequestBody annotation - indicates that a method parameter should be bound to the body of the HTTP request
@DeleteMapping annotation - the class will be executed when we want to delete ("drop") a row from table
@Query annotation - basically assigning a query to a class
@PathVariable annotation - is used to extract the value from the URL
@Transactional annotation - in the Service layer we sometimes need to have both database related and non-database related services. that's when this annotation comes in handy

final keyword - meaning the value cannot be changed

CommandLineRunner interface - an interface used to indicate that a bean should run when it is contained within a SpringApplication. a Spring Boot application can have multiple beans implementing CommandLineRunner. these can be ordered
			      with order.

// note: using List.of(content) to display content on the webserver is putting out a JSON, which is written as name/value
	 pairs, just like JavaScript object properties. 

I created my own module Student, where data will be "dealt with" :D

I'm using @AllArgsConstructor and @NoArgsConstructor at once, it is not a problem with lombok as it is supported.

I created an Override for the toString() method once again, this is a case im familiar with.

// IMPORTANT NOTE: if you want to use files in different directories in the main file/controller, the file must be added 
		   in the beggining with the import keyword

I created a StudentController class in order to maintain all the resources for my simple "API" 

An another class "StudentService" has been created in order for it to contain the "business logic" 

Creating and connecting to a database:
	1) either copy or write into application.properties file located in resources, and modify the properties accordingly
	2) create your database in your desired program (in PostgreSQL connect to your database with the "\c" command)
	3) run your code and check whether the database is connected

Creating an interface that extends JpaRepository:
	- an interface is an abstract "class" that contains group related methods with empty bodies
	- our interface is a repository and it extends a JPA Repository
	- we have to specify the type of object we want our repository to work with and the id for our object (its type)
	  in JpaRepository<Student, Long>
	- when we do that, we will create a "repo" variable in our "service" class and will be able to use sql commands after
	  we use Autowired obviously

I used return args -> {} to put students into my database, i dont put in ID since that will be generated for me when creating
	 	      new students
In order to save all the students into the database, we will use our repo (with database methods) and the method saveAll, which
needs an iterateable list, which is why we use List.of(.....) which returns an iterateable list of objects, in our case students

I don't want to bother with age in my database, therefore i'll remove it from constructors and create a function to calculate it from the date of birth provided and todays date
that gets realized thanks to the Period method, which we have to convert to years, that happens thanks to the .getYears() method

We take @RequestBody and map it into Student

For POST/GET requests we use the Thunder client
	IMPORTANT! check the code is running before sending a request to the server, obviously
	filling in a request: no need to fill out anything except for the content, in this case Json Content, which is formatted like this:
	
		{
    			"name": "David",
    			"email": "david.fiesta@gmail.com",
    			"dob": "2000-06-09"
		}

	the student (in this case) gets printed out into terminal, because that's what we set our @PostMapping to do

In Repo, we create Query and use a class Optional, which simply just says that the value in Optional<....> temp can be empty
We are not using clean SQL in @Query, but JPQL, meaning that we use the class we have created is used in the query, i.e. the code

Back in StudentService.java we utilize this, using an optional once again and assigning it the value of studentRepository.findStudentByEmail(student.getEmail())
it puts the value of the email into the optional, if it has been already used, or a null, if it hasn't. 
Then the isPresent() method checks whether our optional contains a value, if it does, we know the email has been used.

Instead of printing the student into the terminal we will save it to the database instead, using our repo variable 
studentRepository and the method save(student)

In order to have a sensical error message, in the application.properties we specify that it always gets included
we specify the property server.error.include-message=always

Next, we want to delete a student, meaning we will add a delete mapping in our controller and the path will be a studentId variable
in our method parameter we put @PathVariable("exampleId") Long exampleId) meaning we assign the variable in java for simpler work with the data  

next, we will implement a class to update student data, we are using the @PutMapping annotation and the path is the id again, in our method we set
the @PathVariable to id again but now we also request name and email (to edit), both are not required.

in service, we create a new method createStudent which accepts all the parameters and first and foremost assigns a student from the database,
checks if it exists to begin with (.findById(studentId).orElseThrow(() -> new IllegalStateException("example error")).

we dont have to use query from our repo at all, all is done thanks to the student object.
then we check whether the name provided is not null, has the length of 0 or already is in the database 
we do the same for the email provided by the user

to check if that works we will use a PUT request, but beware of not using the ID as a query parameter in this case, as it doesn't go through...
we will just put url/IDNUMBER?nameOrEmail=newvalue

in maven, you can install/package an app so it's executable, you can also change where it's running, for example, by default my app was running on localhost(port 8080)
but i manually changed it to port 8001 in the terminal: --server.port=8001