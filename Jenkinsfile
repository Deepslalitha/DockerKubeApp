pipeline {
    agent {
        docker {
            image 'maven:3.8.3-jdk-11-slim'
            args '-v /root/.m2:/root/.m2'
        }
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

    }
}