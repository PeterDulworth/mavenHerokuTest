import static spark.Spark.*;

public class Server {

    /**
     * Server entry point.
     */
    public static void main(String[] args) {
        // Will serve all static file are under "/public" in classpath if the route isn't consumed by others routes.
        // When using Maven, the "/public" folder is assumed to be in "/main/resources"
        staticFileLocation("/public");

        port(getHerokuAssignedPort());

        get("/hello", (req, res) -> "Hello World");

        post("/hello", (request, response) ->
            "Hello World: " + request.body()
        );

        get("/private", (request, response) -> {
            response.status(401);
            return "Go Away!!!";
        });

        get("/users/:name", (request, response) -> "Selected user: " + request.params(":name"));

        get("/news/:section", (request, response) -> {
            response.type("text/xml");
            return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><news>" + request.params("section") + "</news>";
        });

        get("/protected", (request, response) -> {
            halt(403, "I don't think so!!!");
            return null;
        });

        get("/redirect", (request, response) -> {
            response.redirect("/news/world");
            return null;
        });

        get("/", (request, response) -> "root");

        get("/reqStats", (request, res) -> {
            System.out.println("attributes: " + request.attributes());             // the attributes list
//            System.out.println(request.attribute("foo"));         // value of foo attribute
//            System.out.println(request.attribute("A", "V"));      // sets value of attribute A to V
            System.out.println(request.body());                   // request body sent by the client
            System.out.println(request.bodyAsBytes());            // request body as bytes
            System.out.println(request.contentLength());          // length of request body
            System.out.println(request.contentType());            // content type of request.body
            System.out.println(request.contextPath());            // the context path, e.g. "/hello"
            System.out.println(request.cookies());                // request cookies sent by the client
            System.out.println(request.headers());                // the HTTP header list
            System.out.println(request.headers("BAR"));           // value of BAR header
            System.out.println(request.host());                   // the host, e.g. "example.com"
            System.out.println(request.ip());                     // client IP address
            System.out.println(request.params("foo"));            // value of foo path parameter
            System.out.println(request.params());                 // map with all parameters
            System.out.println(request.pathInfo());               // the path info
            System.out.println(request.port());                   // the server port
            System.out.println(request.protocol());               // the protocol, e.g. HTTP/1.1
            System.out.println(request.queryMap());               // the query map
            System.out.println(request.queryMap("foo"));          // query map for a certain parameter
            System.out.println(request.queryParams());            // the query param list
            System.out.println(request.queryParams("FOO"));       // value of FOO query param
            System.out.println(request.queryParamsValues("FOO")); // all values of FOO query param
            System.out.println(request.raw());                    // raw request handed in by Jetty
            System.out.println(request.requestMethod());          // The HTTP method (GET, ..etc)
            System.out.println(request.scheme());                 // "http"
            System.out.println(request.servletPath());            // the servlet path, e.g. /result.jsp
            System.out.println(request.session());                // session management
            System.out.println(request.splat());                  // splat (*) parameters
            System.out.println(request.uri());                    // the uri, e.g. "http://example.com/foo"
            System.out.println(request.url());                    // the url. e.g. "http://example.com/foo"
            System.out.println(request.userAgent());              // user agent
            return "check your log";
        });

        // matches "GET /hello/foo" and "GET /hello/bar"
        // request.params(":name") is 'foo' or 'bar'
        get("/hello/:name", (request, response) -> {
            return "Hello: " + request.params(":name");
        });

        // matches "GET /say/hello/to/world"
        // request.splat()[0] is 'hello' and request.splat()[1] 'world'
        get("/say/*/to/*", (request, response) -> {
            return "Number of splat parameters: " + request.splat().length;
        });

//        path("/api", () -> {
//            before("/*", (q, a) -> log.info("Received api call"));
//            path("/email", () -> {
//                post("/add",       (req, res) -> {
//                    return "";
//                });
//                put("/change",     EmailApi.changeEmail);
//                delete("/remove",  EmailApi.deleteEmail);
//            });
//            path("/username", () -> {
//                post("/add",       UserApi.addUsername);
//                put("/change",     UserApi.changeUsername);
//                delete("/remove",  UserApi.deleteUsername);
//            });
//        });
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}

