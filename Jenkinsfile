pipeline {
    agent {
        docker {
            image 'maven:3.8-alpine'
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
                sh 'mvn test'
                junit 'target/surefire-reports/*.xml'
            }

    }
}