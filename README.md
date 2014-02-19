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

Before we begin, we first need to install the command line tool that will be used to upload and manage your application. Cloud Foundry uses a tool called [**cf**](https://github.com/cloudfoundry/cli).  Make sure you are using v6 of the cf cli by using

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
 * `$ cf login -a https://api.ng.bluemix.net`
2. Create an instance of the postgreSQL service, giving it a unique name in the last arguement.
 * `$ cf create-service postgresql 100 unique_service_instance_name`
3. From the directory you placed your WAR file in, push the app with a -p flag to specify the WAR file path and the --no-start option so we can bind our required service before starting our app.  Give your app a unique app name to be used as its path.
 * `$ cf push unique_app_name --no-manifest --no-start -p /path/to/PostgreSQLUpload.war`
4. Bind the postgreSQL service instance to the new app
 * `$ cf bind-service unique_app_name unique_service_instance_name`
5. Start the app
 * `$ cf start unique_app_name`

*Note* : that you must add the flag -p to specify the WAR file you want to upload.

*Note* : Service instance names must be unique to your organization.

As an example invocation... 

 * `$ cf login -a https://api.ng.bluemix.net`
 * `$ cf create-service postgresql 100 postgresql_JPU`
 * `$ cf push jpu --no-manifest --no-start -p PostgreSQLUpload.war`
 * `$ cf bind-service jpu postgresql_JPU`
 * `$ cf start jpu`

*Note* : * '-p PostgreSQLUpload.war' assumes you are running these commands from within the same directory that this file resides.


## Troubleshooting ##

-   Sometimes your app may not work as expected and debugging needs to be done. The cf command line tool can be used to assist with debugging. With the cf you can check your app's logs by typing the command **cf logs [app_name]** 
-   When you first start using the cf tool, you may potentially have trouble logging in due to no target being set. To view the target that is set, type **cf target** and if you want to set a new target type **cf target [target_url]**. Note: The target URL will usually be in the form of http://api.xxx.tld
-   From time to time your app may stop working, this means it could require a restart. To do this you must first stop it by typing **cf stop**. Once the app has been stopped, you can type **cf start** and if there are no other problems your app should start. 
