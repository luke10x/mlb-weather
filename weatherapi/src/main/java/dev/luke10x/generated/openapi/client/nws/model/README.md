# Why does this package exist?

Ideally, `openapi-generator-maven-plugin` could generate all required classes,
for the National Weather Service API using its 
[OpenApi schema](https://api.weather.gov/openapi.json).

However, generated client code normally depends on many files that are missing.
I normally follow the error messages to see what are the package names of the missing files,
and then I try to search for package containing them on
[maven.org](https://search.maven.org/search?q=g:org.openapitools).

Yet with this NWS API, this is not enough, some files are still missing,
but they seem not to belong to any external package, 
but instead those missing files are referred only by their class name
(without any imports or using fully qualified package names).
Seems like the generated code at package ~~`org.openapi.client.model`~~
`dev.luke10x.generated.openapi.client.nwm.model`,
expects those missing files to be in the same package.

The reason why they are not there is not quite clear,
- perhaps it is because of a fault in OpenApi schema provided by NWS;
- also maybe it is because incompatibilities of the maven plugin and dependency versions;
- or they might be missing for any other reason.

Regardless, as a **workaround** I manually provide the files in this package,
so that the code can compile. It is a quick and dirty solution,
without trying to reverse engineer every supposed functionality,
it is just providing minimum implementation required for build to compile,
and for the data we actually use in this app to be accessible.

Unfortunately at this moment I don't have much time to investigate
for the root cause, and finally this app can still leverage on
generated client code with these minor manual patches.