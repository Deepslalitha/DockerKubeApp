pipeline {
    agent {
        any {
            image 'maven:3.8.3-jdk-11-slim'
            args '-v /root/.m2:/root/.m2'
        }
    }
    environment {
        DOCKERHUB_CREDENTIALS= credentials('dockerhubcredentials')
        registry = "deepthylalithatech/mydemoapp"
        registryCredential = 'dockerhub_id'
        dockerImage = ''
    }
    stages {
    stage ('Initialize') {
                steps {
                    sh '''
                        echo "PATH = ${PATH}"
                        echo "M2_HOME = ${M2_HOME}"
                    '''
                }
            }
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
         stage('Test') {
            steps {
                sh 'mvn test'
                junit 'target/surefire-reports/*.xml'
                }
            }

    stage('Cloning Git') {
      steps {
        git([url: 'https://github.com/Deepslalitha/DockerKubeApp.git', branch: 'feature/DevopsAssignment'])

      }
    }
    stage('Build Docker Image') {
                 steps{
                sh  'sudo docker run hello-world'
                //	dockerImage = docker.build imagename
                   echo 'Build Image Completed'
                 }
               }
     /* stage('Build Docker Image') {
             steps{
             script {
                        dockerImage = docker.build registry + ":$BUILD_NUMBER"
                          echo 'Build Image Completed'
                        }
            //	dockerImage = docker.build imagename
               echo 'Build Image Completed'
             }
           }
        stage('Login to Docker Hub') {
             steps{
                sh 'echo $DOCKERHUB_CREDENTIALS_PSW | sudo docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
                echo 'Login Completed'
             }
           }
        stage('Push Image to Docker Hub') {
             steps{
            	sh ' docker push deepthylalithatech/mydemoapp:$BUILD_NUMBER'
       	        echo 'Push Image Completed'
             }
           }
*/

    }//stages

    post{
        always {
          sh 'docker logout'
        }
      }
}//pipeline