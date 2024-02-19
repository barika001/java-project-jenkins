pipeline {
    agent any
    
    environment {
        JAVA_HOME = tool 'JDK8'
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/barika001/java-project-jenkins.git'
            }
        }

        stage('Build and Test') {
            steps {
                sh 'mvn clean compile test'
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package'
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying...'
            }
        }
    }

    post {
        always {
            // Cleanup actions
            deleteDir()
        }
        success {
            echo 'Build and deployment successful!'
        }

        failure {
            echo 'Build or deployment failed. Please check the logs for details.'
        }
    }
}
