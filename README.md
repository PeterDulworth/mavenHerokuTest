# Maven / Heroku Test

simple spark API to test deploying with heroku / maven

1. configure the maven-assembly-plugin to build the JAR with all the dependencies
2. configure the proc file to run the jar
3. configure the git remote for heroku
4. 'git push heroku master'