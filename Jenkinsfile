pipeline {
    agent any

    tools {
        jdk 'jdk-11'
        }
    stages {
        stage('Build') {
            steps {
               sh 'mvn clean test-compile'
            }
        }
        stage('Testing') {
            steps {
                sh 'mvn -Dmaven.test.failure.ignore=true test'
            }
        }
    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml'
        }
    }
}