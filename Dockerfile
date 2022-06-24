#the first line is always FORM that defines a base image 
FROM tomcat:8.0-jre8

#adding info about who created this this image 
LABEL maintainer="Leo Juarez"

#what did we do with the WAR file w/ respect to TOMCAT on the ec2? 
#we had to move WAR file into TOMCAT's directory
#we need to mock all of those commands all those commands here! 

#ADD  WAR file into TOMCAT's webapps dir, which contains the app that Tomcat serves
ADD target/employee-servlet-app.war /usr/local/tomcat/webapps

#next, need to EXPOSE the port we are working with (8080) from the container 
EXPOSE 8080

#final command
#CMD specifies waht to run when the container is run 
#in our case, the tomcat server is started by running a shell script named catalina.sh  
CMD ["catalina.sh", "run"]