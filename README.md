# How to Run and Deploy the Java ElephantSQL Upload App #

## Overview of the app ##

This is a Java app that uses the following cloud services:

- ElephantSQL Database

This app demonstrates how to connect to an ElephantSQL database on BlueMix from a Java app.
Simply upload a line-separated file of text (e.g. tweets), and it will add each line to the ElephantSQL database.
This tutorial is intended to deploy a pre-compiled warfile through the command line. If you would like to
use Eclipse and modify the source code, see [development](#development).

Give it a try! Click the button below to fork into IBM DevOps Services and deploy your own copy of this application on Bluemix.

[![Deploy to Bluemix](images/deploy-button.png)](https://bluemix.net/deploy?repository=https://github.com/ibmjstart/bluemix-java-postgresql-uploader.git)

Note it will take a minute or so to start up.

## Prerequisites ##

Before we begin, we first need to install the [**cf**](https://github.com/cloudfoundry/cli/releases) command line tool that will be used to upload and manage your application. If you've previously installed an older version of the cf tool, make sure you are now using v6 of cf by passing it the -v flag:

    cf -v

## Download ##

If you would like to modify the source code and work in development, see [development](#development).

Download the latest WAR file 'release' from https://github.com/ibmjstart/bluemix-java-postgresql-upload/releases

## Deploy to BlueMix and Binding the ElephantSQL Service via the Command Line Interface for Production##

From the command line, navigate to the directory of the app and run the following commands.

1. Login to Bluemix.

   | *usage:*   | `$ cf login [-a API_URL] [-o ORG] [-s SPACE]`|
   |------------|:---------------------------------------------|
   | *example:* | `$ cf login -a https://api.ng.bluemix.net`   |

2. Create an instance of the ElephantSQL service, giving it a unique name in the last argument.  The application is written to assume that the service instance name will begin with "elephantsql".

   | *usage:*   | `$ cf create-service SERVICE PLAN SERVICE_INSTANCE`|
   |------------|:---------------------------------------------------|
   | *example:* | `$ cf create-service elephantsql turtle elephantsql_JEU`|

3. From the directory you placed your WAR file in, push the app with the -p flag to specify the WAR file
path and the --no-start option so we can bind our required service before starting our app.  Give your
app a unique app name to be used as its hostname; for instance, if you replace `<YOUR-NAME>`
with 'user1' then the example below would be hosted at http://jeu-user1.mybluemix.net.

   | *usage:*   | `$ cf push APP [--no-manifest] [--no-start] [-p PATH]`       |
   |------------|:----------------------------------------------------------------|
   | *example:* | `$ cf push jeu-<YOUR-NAME> -b https://github.com/cloudfoundry/java-buildpack --no-manifest --no-start -p ElephantSQL-Uploader.war`|

 *Note* : `-p ElephantSQL-Uploader.war` assumes you are running these commands from within the same directory that
 this file resides.

4. Bind the elephantSQL service instance to the new app

   | *usage:*   | `$ cf bind-service APP SERVICE_INSTANCE`|
   |------------|:----------------------------------------|
   | *example:* | `$ cf bind-service jeu-<YOUR-NAME> elephantsql_JEU`|

5. Start the app


   | *usage:*   | `$ cf start APP`|
   |------------|:----------------|
   | *example:* | `$ cf start jeu-<YOUR-NAME>`|

## <a name="development"></a>Import the App into Eclipse for Development ##

To view and edit the application, simply import the project into Eclipse.  If you don't have an Eclipse instance with Java EE support, you may download it from http://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/lunar

### Download the War File Release ###

Once you have Eclipse running, you can Import the WAR file as a new project via
  1. At the top of the github repository, go to releases and download the latest war file (the green button). https://github.com/ibmjstart/bluemix-java-postgresql-upload/releases
  2. File->Import...
  3. Scroll down to the "Web" section, expand that section and click WAR File then click Next.
  4. Select the war file that you downloaded. Under target runtime, select IBM Bluemix. If the IBM Bluemix
  runtime is not present, hit `New...` IBM-> IBM Bluemix.
  5. Deselect the 'Add project to an EAR' checkbox
  6. Click next and then Finish and the project should be imported into Eclipse

### Modify the Source Code and Deploy to Bluemix ###

With access to the source code, you may modify the application, add functionality, or deploy it straight
to Bluemix. When you push source code to Bluemix, Eclipse first compiles it into a war file, and then
pushes that war file to Bluemix. Alternatively, you may select File->Export and export the war file. Then,
you may push the war file using the same methods above.

Now that you have the project in Eclipse, and have modified the source code, re-deploy to BlueMix by either:

  A.  Exporting your updated project as a WAR file and push it from the command line as before

 -OR-

  B.  Installing the [IBM Eclipse Tools for Bluemix Plugin](http://marketplace.eclipse.org/content/ibm-eclipse-tools-bluemix#.VAiRPbywIRI),
  defining the BlueMix endpoint (https://api.ng.bluemix.net), and deploying your application right from Eclipse. To deploy to Bluemix:


#### • Set up Bluemix Server ####

Make sure you are in the Java EE [perspective](http://help.eclipse.org/juno/index.jsp?topic=%2Forg.eclipse.platform.doc.user%2Fconcepts%2Fconcepts-4.htm) in Eclipse.  

  1. In the bottom window section, select the **Servers** tab.  (Alternatively, you can click: `Window > Show View > Servers`)
  2. Right-Click inside the Servers panel and select `New > Server`
  3. Select, `IBM > IBM Bluemix` and click `Next`.
  4. Enter your login information for Bluemix in the email and password sections.
  5. From the URL dropdown menu, choose: `IBM Bluemix`
  6. Hit `Next` and Bluemix will automatically validate your account credentials.
  7. Optional: Select the Organization within your Bluemix account that you would like to deploy to.
  8. Click: Finish

#### • Push the app ####
  1. Right-Click on the Bluemix server and click: `Connect`  (Optional)
  2. Right-Click on the Bluemix server and select: `Add and Remove...`
  3. Select your ElephantSQL project from the window on the left and click: `Add >`
  4. Click: `Finish`
  5. Enter a Name for your app and select: `Next`
  6. Specify a unique subdomain for your application. Similar to the command line tools, you can pick
  something like <jeu-yourUser> so that your app is located at `http://jeu-yourUser.mybluemix.net`. Hit `Next`
  7. Create and bind the elephantSQL service by selecting either an existing elephantSQL service or creating one by selecting the icon in the top right. Search for ElephantSQL, and you can bind the application through the plugin GUI.
  8. Hit `Next` and `Finish`. Your app should deploy to Bluemix and be available at the URL you gave it. This can be viewed in the ACE UI.

## License ##
Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
