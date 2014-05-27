# How to Run and Deploy the Java PostgreSQL Upload App #

## Overview of the app ##

This is a Java app that uses the following cloud services:

- PostgreSQL Database

This app demonstrates how to connect to a PostgreSQL database on BlueMix from a Java app. 
Simply upload a line-separated file of text (e.g. tweets), and it will add each line to PostgreSQL database.

## Prerequisites ##

Before we begin, we first need to install the [**cf**](https://github.com/cloudfoundry/cli/releases) command line tool that will be used to upload and manage your application. If you've previously installed an older version of the cf tool, make sure you are now using v6 of cf by passing it the -v flag:

    cf -v
        
## Download ##

Download the latest WAR file 'release' from https://github.com/ibmjstart/bluemix-java-postgresql-upload/releases

## Deploy to BlueMix and Binding the PostgreSQL Service via the Command Line Interface ##

From the command line, navigate to the directory of the app and run the following commands.

1. Login to Bluemix.

   | *usage:*   | `$ cf login [-a API_URL] [-o ORG] [-s SPACE]`|
   |------------|----------------------------------------------|
   | *example:* | `$ cf login -a https://api.ng.bluemix.net`   |

2. Create an instance of the PostgreSQL service, giving it a unique name in the last argument.  The application is written to assume that the service instance name will begin with "postgresql".

   | *usage:*   | `$ cf create-service SERVICE PLAN SERVICE_INSTANCE`|
   |------------|----------------------------------------------------|
   | *example:* | `$ cf create-service postgresql 100 postgresql_JPU`|

3. From the directory you placed your WAR file in, push the app with the -p flag to specify the WAR file path and the --no-start option so we can bind our required service before starting our app.  Give your app a unique app name to be used as its hostname; for instance, if you replace <YOUR-NAME> with the username 'user1' then the example below would be hosted at http://jpu-user1.ng.bluemix.net.

   | *usage:*   | `$ cf push APP [--no-manifest] [--no-start] [-p PATH]`       |
   |------------|:----------------------------------------------------------------|
   | *example:* | `$ cf push jpu-<YOUR-NAME> -b https://github.com/cloudfoundry/java-buildpack --no-manifest --no-start -p PostgreSQLUpload.war`|

 *Note* : `-p PostgreSQLUpload.war` assumes you are running these commands from within the same directory that this file resides.

4. Bind the postgreSQL service instance to the new app

   | *usage:*   | `$ cf bind-service APP SERVICE_INSTANCE`|
   |------------|-----------------------------------------|
   | *example:* | `$ cf bind-service jpu-<YOUR-NAME> postgresql_JPU`|

5. Start the app

   | *usage:*   | `$ cf start APP`|
   |------------|-----------------|
   | *example:* | `$ cf start jpu-<YOUR-NAME>`|
   
## Import the App into Eclipse ##

To view and edit the application, simply import the project into Eclipse.  If you don't have an Eclipse instance with Java EE support, you may download it from https://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/keplersr2

Once you have Eclipse running, you can Import the WAR file as a new project via
  1. File->Import...
  2. Scroll down to the "Web" section, expand that section and click WAR File then click Next.
  3. Deselect the 'Add project to an EAR' checkbox
  4. Click next and then Finish and the project should be imported into Eclipse

Now that you have the project in Eclipse, you can edit the source and re-deploy to BlueMix by either:
  
  A.  Exporting your updated project as a WAR file and push it from the command line as before
  
 -OR- 
  
  B.  Installing the [Eclipse Cloud Foundry Plugin](https://marketplace.eclipse.org/content/cloud-foundry-integration-eclipse), defining the BlueMix endpoint (https://api.ng.bluemix.net), and deploying your application right from Eclipse.

## License ##
Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
