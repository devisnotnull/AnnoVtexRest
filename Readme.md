# AnoTexRex - Java rs Pseudo implementation for Vert.x 3

[![CircleCI](https://circleci.com/gh/stump201/AnnoVtexRest/tree/master.svg?style=svg)](https://circleci.com/gh/stump201/AnnoVtexRest/tree/master)

### Version 0.1
#### VERY EARLY DEVELOPMENT
## What is AnoTexRex
AnoTexRex is a simple annotation layer layered on top of the standard Vertx3 web component, Having build a number of Vertx applications using the satandard web library i felt that the standard approach whilst not difficult to implement it was not very elegant.

Essentially this lib brings aspects of Java-rs to Vertx, All you need to do is implement your controller logic via the provided @Annotations, Add a classpath to the main AnoTexRex instance and your good to go.

## Pre-reqs

- Vertx 3
- Vertx 3 Web

## TODO

- Loads

```xml
<dependency>
     <groupId>org.fandanzle.mongi</groupId>
     <artifactId>anotexrex-core</artifactId>
     <version>0.1-BETA</version>
 </dependency>

```

## How to use Mongi

You define a controller by annotation a class with @Controller
```Java
@Controller(uri = "/api")
public class MockApiController {
}
```

You defined a route by annotation a class function with @RequestMapping, This contains the HTTP type, Mime consume and produce types and description
```Java
@RequestMapping(
        uri = "/users/:id/:id2/:id3",
        method = RequestMethods.GET,
        consumes = MimeType.APPLICATION_HTML,
        produces = MimeType.APPLICATION_JSON,
        description = "What does this route do ?!"
)
```

You can protecct a route, NOT YET IMPLEMENTED
```Java
@Guard(
        scopes= {
                "support_account.READ"
        }
)
```

You can pass in a number of @Context items, These relate to vertx context. E.g you can handle redirects directly from Vertx. You can also provide a void return for your function and directly send a resutl with vertx.
```Java
@Context Vertx vertx,
@Context HttpServerRequest request,
@Context HttpServerResponse response,
@Context RoutingContext context
```

You can grab a cookie value and pass it in to the funtion.
```Java
@CookieParam(name = "x-session-id") String cookie,
```

A Path param is aquired from the variables defined in your route, E.g uri = "/users/:id", You can also define the type of the varaible. Note if the priovided variable is not of the corrent type and exception willl be thrown. Currently only String and Integer supported.
```Java
@PathParam(name = "id") Integer id,
```

A Header param is aquired from the request. Note if the priovided variable is not of the corrent type and exception willl be thrown. Currently only String and Integer supported.
```Java
@HeaderParam(name = "h1") Integer h1 ,
```

A Query param is aquired from the request, E.g ?q1=wwjfwirug. Note if the priovided variable is not of the corrent type and exception willl be thrown.
```Java
@QueryParam(name = "q1") String q1
```

### How to setup


```Java
public class MainVerticle extends AbstractVerticle {

    private static final Logger logger = Logger.getLogger(MainVerticle.class);

    private Router router = null;

    protected AnnoVtexRest annoVtexRest;

    /**
     * Start verticle
     */
    @Override
    public void start() {
        try{
            annoVtexRest = new AnnoVtexRest(vertx);
            annoVtexRest.build("org.fandanzle.annovtexrest.example.controller");
            router = annoVtexRest.getRouter();
            vertx.createHttpServer().requestHandler(router::accept).listen(8000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
```

## Contacts
alexbrown201@googlemail.com
