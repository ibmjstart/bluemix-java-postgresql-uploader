# How to Run and Deploy the Java PostgreSQL Upload App #

## Overview of the app ##

This is a Java app that uses the following cloud services:

- PostgreSQL Database

This app demonstrates how to connect to a PostgreSQL database on BlueMix from a Java app. 
Simply upload a line-separated file of text (e.g. tweets), and it will add each line to Postgres.

## License ##
Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.

## Prerequisites ##

Before we begin, we first need to install the [**cf**](https://github.com/cloudfoundry/cli/releases) command line tool that will be used to upload and manage your application. If you've previously installed an older version of the cf tool, make sure you are now using v6 of cf by passing it the -v flag:

    cf -v
	
to see the version.
        
## Download the App ##

The source for this app is at GitHub so, for example, if you are using the command line you can clone the repository like this:

	git clone https://github.com/ibmjstart/bluemix-java-postgresql-upload.git
		
If you want to use Eclipse to work on it, there are two ways you can get the source into Eclipse:

1. Import the Eclipse project by following these instructions:
	1. Start by cloning the repository, as described above
	2. Open Eclipse
	3. Select File->Import
	4. Under the header labeled "General", click "Existing Projects Into Workspace" and click Next
	5. Click "Browse" next to the first text field, and navigate to the cloned repository and find the folder labeled "app" and click ok.
	6. Under Projects you should now see a project called "PostgreSQLUpload", make sure the checkbox next to the "PostgreSQLUpload" project is checked and then click Finish
	7. You should now see the "PostgreSQLUpload" project in your list of projects in Eclipse.

2. Import the WAR File
	1. Navigate to https://github.com/ibmjstart/bluemix-java-postgresql-upload/releases
	2. Click the green button labeled "PostgreSQLUpload.war" and that will download the WAR file.
	3. Open Eclipse
	4. Then File->Import
	5. Scroll down to the "Web" section, expand that section and click WAR File then click Next.
	6. Click next and then Finish and the project should be imported into Eclipse

## Deploying the App to BlueMix and Binding the PostgreSQL Service##

In order to deploy your app on BlueMix, it has to be wrapped in a WAR file.  You can export the project from Eclipse to a WAR file, or download the WAR file for this sample here: https://github.com/ibmjstart/bluemix-java-postgresql-upload/releases.  In the terminal, go in the directory of the app. 

1. Login to Bluemix.

   | *usage:*   | `$ cf login [-a API_URL] [-o ORG] [-s SPACE]`|
   |------------|----------------------------------------------|
   | *example:* | `$ cf login -a https://api.ng.bluemix.net`   |

2. Create an instance of the postgreSQL service, giving it a unique name in the last arguement.

   | *usage:*   | `$ cf create-service SERVICE PLAN SERVICE_INSTANCE`|
   |------------|----------------------------------------------------|
   | *example:* | `$ cf create-service postgresql 100 postgresql_JPU`|

3. From the directory you placed your WAR file in, push the app with the -p flag to specify the WAR file path and the --no-start option so we can bind our required service before starting our app.  Give your app a unique app name to be used as its hostname; for instance the example below would be hosted at http://jpu.ng.bluemix.net.

   | *usage:*   | `$ cf push APP [--no-manifest] [--no-start] [-p PATH]`       |
   |------------|-----------------------------------------------------------------|
   | *example:* | `$ cf push jpu --no-manifest --no-start -p PostgreSQLUpload.war`|

*Note* : `-p PostgreSQLUpload.war` assumes you are running these commands from within the same directory that this file resides.

4. Bind the postgreSQL service instance to the new app

   | *usage:*   | `$ cf bind-service APP SERVICE_INSTANCE`|
   |------------|-----------------------------------------|
   | *example:* | `$ cf bind-service jpu postgresql_JPU`|

5. Start the app

   | *usage:*   | `$ cf start APP`|
   |------------|-----------------|
   | *example:* | `$ cf start jpu`|

