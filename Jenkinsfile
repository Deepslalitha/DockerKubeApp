pipeline {
    agent {
        any {
            image 'maven:3.8.3-jdk-11-slim'
            args '-v /root/.m2:/root/.m2'
        }
    }
    environment {
        registry = "deepthylalithatech/mydemoapp"
        registryCredential = 'dockerhubcredentials'
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
            git([url: 'https://github.com/Deepslalitha/DockerKubeApp.git', branch: 'main'])

          }
        }

        stage('Build Docker Image') {
             steps{
             script {
                        dockerImage = docker.build registry + ":latest"

                        }
               echo 'Build Image Completed'
             }
           }

        stage('Push Image to Docker Hub') {
             steps{
             script {
                    docker.withRegistry( '', registryCredential ) {
                    dockerImage.push()
                   }
              echo 'Push Image Completed'
                } 

             }
           }
       stage("kubernetes deployment"){
          steps{

               sh 'kubectl apply -f deploy-to-minikube.yaml'

              echo 'kubernetes deployment Completed'
            }
       }

            stage(" minikube service test"){
                      steps{
                               sh 'minikube service list'
                               sh 'minikube service java-app --url'

                                echo "------------------opening the service------------------"
                                sh 'curl $(minikube service java-app --url)'

                          }
                        }

    }//stages

    post{
        always {
          sh 'docker logout'
        }
      }
}//pipeline