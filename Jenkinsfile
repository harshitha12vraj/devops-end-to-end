pipeline {

    agent any

    tools {
        jdk 'jdk25'
    }

    options {
        skipDefaultCheckout(true)
        timestamps()
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Verify Tools') {
            steps {
                bat 'echo JAVA_HOME=%JAVA_HOME%'
                bat 'java -version'
                bat 'git --version'

                dir('application/backend') {
                    bat 'call mvnw.cmd -version'
                }
            }
        }

        stage('Build and Test') {
            steps {
                dir('application/backend') {
                    bat 'call mvnw.cmd clean package'
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                dir('application/backend') {
                    withSonarQubeEnv('sonarqube') {
                        bat '''
                        call mvnw.cmd org.sonarsource.scanner.maven:sonar-maven-plugin:sonar ^
                        -Dsonar.projectKey=devops-backend ^
                        -Dsonar.projectName=devops-backend
                        '''
                    }
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Archive Artifact') {
            steps {
                archiveArtifacts(
                    artifacts: 'application/backend/target/backend-0.0.1-SNAPSHOT.jar',
                    fingerprint: true
                )
            }
        }
    }

    post {
        success {
            echo 'CI pipeline completed successfully.'
        }

        failure {
            echo 'CI pipeline failed. Check the build logs.'
        }

        always {
            echo 'Pipeline execution finished.'
        }
    }
}