# vim_java
My attempt to do something similar to vim but in java. To learn more java, more than anything.

# Run

Do this:
` java -jar target/vim_java-1.0-SNAPSHOT.jar-jar-with-dependencies.jar `

# Find checkstyle violations

`mvn verify`

Will find target/pmd.xml file.


# Find code duplication.

`mvn site`


Will find target/cpd.xml file.
