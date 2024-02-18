pipeline {
    agent any

    environment {
        JAVA_HOME = tool 'JDK8'
        MAVEN_HOME = tool 'Maven3' ?: ''
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout the source code from the 'main' branch
                checkout([$class: 'GitSCM', branches: [[name: 'main']], userRemoteConfigs: [[url: 'https://github.com/barika001/java-project-jenkins']]])
            }
        }

        stage('Build and Test') {
            steps {
                // Compile and run unit tests using Maven
                script {
                    try {
                        sh "${MAVEN_HOME}/bin/mvn clean compile test"
                    } catch (Exception ex) {
                        currentBuild.result = 'FAILURE'
                        error("Error: ${ex.message}")
                    }
                }
            }
        }

        stage('Package') {
            steps {
                // Package the Java application using Maven
                script {
                    try {
                        sh "${MAVEN_HOME}/bin/mvn package"
                    } catch (Exception ex) {
                        currentBuild.result = 'FAILURE'
                        error("Error: ${ex.message}")
                    }
                }
            }
        }

        stage('Run Integration Tests') {
            steps {
                // Run integration tests using Maven
                script {
                    try {
                        sh "${MAVEN_HOME}/bin/mvn verify"
                    } catch (Exception ex) {
                        currentBuild.result = 'FAILURE'
                        error("Error: ${ex.message}")
                    }
                }
            }
        }

        stage('Deploy to Artifactory') {
            steps {
                // Deploy artifacts to Artifactory
                script {
                    try {
                        sh "${MAVEN_HOME}/bin/mvn deploy"
                    } catch (Exception ex) {
                        currentBuild.result = 'FAILURE'
                        error("Error: ${ex.message}")
                    }
                }
            }
        }

        stage('Deploy to Production') {
            when {
                expression { currentBuild.resultIsBetterOrEqualTo('UNSTABLE') }
            }
            steps {
                script {
                    try {
                        // Your deployment script or commands to deploy to production
                        echo 'Deploying to production...'
                    } catch (Exception ex) {
                        currentBuild.result = 'FAILURE'
                        error("Error: ${ex.message}")
                    }
                }
            }
        }
    }

    post {
        always {
            // Cleanup actions
            deleteDir()
        }

        success {
            // Send a notification on successful build
            emailext attachLog: true, body: 'Build and deployment successful!', recipientProviders: [[$class: 'CulpritsRecipientProvider'], [$class: 'RequesterRecipientProvider']], subject: 'Build Successful', to: '001barika@gmail.com'
        }

        failure {
            // Send a notification on build failure
            emailext attachLog: true, body: 'Build or deployment failed. Please check the logs for details.', recipientProviders: [[$class: 'CulpritsRecipientProvider'], [$class: 'RequesterRecipientProvider']], subject: 'Build Failed', to: '001barika@gmail.com'
        }
    }
}
