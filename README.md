This Repo is for setting up a CI-CD pipeline for a simple API.

1. A static API build in Spring Boot
2. Docker File - To create an image
3. Kube deployment and Service files
4. Jenkins file - That has different stages

         a. Build
   
         b. Test
   
         c. Create Docker Image
   
         d. Push to Docker Hub
   
         e. Deploy to MiniKube
   
         f. Test the Service

5. Git Hub Workflows that uses git hub actions for build, deploy and test.

**CONFIGURATION**

1. Set up a static API with Spring Boot for testing CI/CD.
2. Set up an AWS Instance.
3. Install JDK in the instance.
4. Install and set up Jenkins.
5. Install and set up Docker.
6. Install and set up Kubectl.
7. Install and set up minikube.
8. Configure Docker plugins in jenkins.
9. Configure Kubernetes Plugin in jenkins.
10. Add DockerHub Credentails in jenkins.
11. Configure Webhook in Git hub.
12. Configure Jenkins to Run pipeline whenever a commit is made to repo (GitScm Polling).
13. Create a pipeline.

 **Spring Boot App**
 
       For testing CI /CD flow, a spring boot app is build that has a static API which will return a text.
       The APP is build with JDK -11 and Spring Boot.
       The Class can be found at
         https://github.com/Deepslalitha/DockerKubeApp/blob/main/src/main/java/com/example/demo/DemoController.java.

       For carrying out this activity, I have made use of AWS ec2 instance  (Ubuntu) with 25 GB volume . The instance
       is of type medium as minikube installation required 20GB of free space and min 2 CPUs.
      
**Set up an AWS Instance**    

     An AWS instance was set up to be used as the Jenkins server. An instance with type as T2 Medium was created with storage as 25 GB.
     since Minikube needed about 2 CPUs and 20 GB memory.

     The security group of the instance is updated to add an  CUSTOM TCP inbound rule .
     Add  Custom TCP Port 8080, so that jenkins can be accessed using public IP address .
     if you do not add this port then you will not be able to access Jenkins using the public IP address of the AWS EC2 instance.

     The pem file having key-pair is downloaded to the local system so that the instance can be accessed using ssh from powershell.
     The pem file is naamed as HadoopLab.pem (ec2 is being used for hadoop labs s well, so I maintain a pem for connecting to the instances).
     ssh -i "HadoopLab.pem"  ubuntu@ec2-65-2-125-60.ap-south-1.compute.amazonaws.com.

 **Install JDK in the instance**
          
       From the local sysytem, using ssh connect to the ec2 instance and execute the below steps as java was not pre installed in the instance.
       sudo apt-get update
       sudo apt install openjdk-11-jre-headless
       Executing  java -version should give openjdk version "11.0.11" 2024-03-24.

**Install and set up Jenkins**
    
        Install jenkins by using the below commands:
        wget -q -O - https://pkg.jenkins.io/debian-stable/jenkins.io.key | sudo apt-key add
        sudo sh -c 'echo deb https://pkg.jenkins.io/debian-stable binary/ > /etc/apt/sources.list.d/jenkins.list'
        sudo apt  update
        sudo apt install jenkins
        Check whether jenkins is installed
        sudo service jenkins status

        After installing Jenkins, it can be  accessed from your local  system by giving the publicIp:8080.
        For eg
        http://ec2-***-***-**.ap-south-1.compute.amazonaws.com:8080/
        Log in to Jenkins by getting the initial password and install the default plugins.
        Create an admin user

        Now , go to the ec2 instance and assign admin privileges to jenkins user
        sudo vi /etc/sudoers 
        The following line needs to be added at the end:
        jenkins ALL=(ALL) NOPASSWD: ALL . Save the file.
        Now we can use Jenkins as root user and for that run the following command --- sudo su - jenkins  

**Install and set Up Docker**

        The docker installation is  done as the Jenkins user as it has root user privileges.
        sudo apt install docker.io
        Run  docker --version o to see whether docker is installed
        Add Jenkins user to the Docker group -
        Jenkins will be accessing the Docker for building  Docker images,so Jenkins user should be added to the docker group by below command:
        sudo usermod -aG docker jenkins
   
   **Install and set up Kubectl**
   
       The command for installing kubectl
       curl -LO "https://storage.googleapis.com/kubernetes-release/release/$(curl -s https://storage.googleapis.com/kubernetes- 
       release/release/stable.txt)/bin/linux/amd64/kubectl"
       chmod +x ./kubectl
       sudo mv ./kubectl /usr/local/bin
    
       Verify it is installed correctly by kubectl version.

**Install and set up minikube**

      Minikube is installed on the ec2 insatnce ( User should be jenkins so that seamlessly our Docker pipeline can
      connect to mini kube clusters without the kubeconfig configurations from jenkins).
         
      curl -Lo minikube https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64 && 
      chmod +x minikube && sudo mv minikube /usr/local/bin/
      minikube status
      kubectl cluster-info

      Restart   the instance -> sudo reboot
     
**Configure Docker plugins in jenkins**

     In the Manage Configure screen -> Select Plugins and add Docker and Docker Pipeline Plugin.
     Enable the jenkins restart after plugin installation.
	       
**Configure Kubernetes Plugin in jenkins**

     In the Manage Configure screen -> Select Plugins and add Kubernetes Plugin.
     Enable the jenkins restart after plugin installation.
   
**Add DockerHub Credentails in jenkins**
  
     Since Jenkins pipeline has to push images to my dockerhub reop, configure the credentials in jenkins.
     In DockerHub ,  an access token is configured to be used for Jenkins .
     Go to 
      Manage Jenkins — — — — — -> Credentials — — — -> domains (global) — — — →Add Credentials — — — → Insert username and accesstoken
     of docker hub username inside username section and password  section . 
     In the ID section use any key. I gave 'dockerhubcredentials' that is referred in the jenkins file .
     Since Git Hub repo is public, the Git hub creadenetials is not configured.
     
**Configure Webhook in Git hub**

      Go to GitHub -> Settings -> WebHooks ->Add webhook
      Payload URL : http://ec2-**-***-***-***.ap-south-1.compute.amazonaws.com:8080/github-webhook/
      Connection Type: application/json
      Check 'Just the Push event'

**Configure Jenkins to Run pipeline whenever a commit is made to repo (GitScm Polling)**

      Check the GitScm polling from Configure-> General

**Create a Pipeline**

     A pipeline named 'DevOps Assignement Pipeline' is created in jenkins to use the JenkinsFile script from the Git Repo.
    'Pipeline Script from SCM' is selected. Git is given as SCM . Repository and branch are given in the pipeline .
     The script path  ' jenkinsfile' is also given so that  the script can be executed form the Jenkinsfile.
             

**Docker and Jenkin Files**
    
     Docker that is used to create an image is at -https://github.com/Deepslalitha/DockerKubeApp/blob/main/Dockerfile
     Jenkinsfile used to cretae different stages in piplein is at https://github.com/Deepslalitha/DockerKubeApp/blob/main/Jenkinsfile
     
     Screenshots of pipeline execution is at https://github.com/Deepslalitha/DockerKubeApp/blob/main/Assignment.docx
	
*********************************************************************************************************************************************************************************

**Monitroing**

   AWS cloud watch can be used to monitor how the system is behaving. Alerts can be cretaed for criteria like CPU utilization
   exceeding a configured value.
   
***********************************************************************************************************************************************************************************

**GitHub WorkFlows**

    A git hub workflow file is also available that makes use of git hub actions to build,
    push to docker and deploy to minikube cluster .
    
    https://github.com/Deepslalitha/DockerKubeApp/blob/main/.github/workflows/dockerBuildAndPush.yml.
    Workflow Actions : https://github.com/Deepslalitha/DockerKubeApp/actions

      
      
            
