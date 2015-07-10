# jersey-handlebars

renders /index.template with the bean as params

```
@GET
@Produces({"text/html"})
public Bean get() {
  return new Bean();
}
```
