pipeline {
    agent any

    tools{
        git 'Default Git'
        maven 'Maven'
    }
    
    environment {
        SONARQUBE_URL = 'http://172.26.103.136:9000'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/yuvarajusaikumar/my-project.git'
            }
        }
        
        stage('Build') {
            steps {
                script {
                    sh 'mvn clean install'
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    withSonarQubeEnv('SonarQube') {
                        sh 'mvn sonar:sonar -Dsonar.projectKey=sonarqube-token  -Dsonar.host.url=$SONARQUBE_URL'
                    }
                }
            }
        }
    }

    post {
        success {
            echo 'Build and SonarQube analysis completed successfully.'
        }
        failure {
            echo 'The build or analysis failed.'
        }
    }
}
