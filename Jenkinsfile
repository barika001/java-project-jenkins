pipeline {
    agent any

    environment {
        JAVA_HOME = tool 'JDK8'
        MAVEN_HOME = tool 'Maven3'
        PATH = "${env.JAVA_HOME}/bin;${env.MAVEN_HOME}/bin;${env.PATH}"
    }

    options {
        timeout(time: 1, unit: 'HOURS')
        disableConcurrentBuilds()
    }

    stages {
        stage('Checkout') {
            steps {
                bat 'git clone https://github.com/barika001/java-project-jenkins'
            }
        }

        stage('Build and Test') {
            steps {
                script {
                    bat "${env.MAVEN_HOME}\\bin\\mvn clean compile test"
                }
            }
        }

        stage('Package') {
            steps {
                script {
                    bat "${env.MAVEN_HOME}\\bin\\mvn package"
                }
            }
        }

        stage('Run Integration Tests') {
            steps {
                script {
                    bat "${env.MAVEN_HOME}\\bin\\mvn verify"
                }
            }
        }

        stage('Deploy to Artifactory') {
            steps {
                script {
                    bat "${env.MAVEN_HOME}\\bin\\mvn deploy"
                }
            }
        }

        stage('Deploy to Production') {
            when {
                expression { currentBuild.resultIsBetterOrEqualTo('UNSTABLE') }
            }
            steps {
                script {
                    echo 'Deploying to production...'
                    // Add your deployment steps here
                }
            }
        }
    }

    post {
        success {
            echo 'Build and deployment successful!'
        }

        failure {
            echo 'Build or deployment failed. Please check the logs for details.'
        }
    }
}
