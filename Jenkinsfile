pipeline {
    agent any

    tools{
        git 'Default Git'
        maven 'Maven'
    }
    
    environment {
        SONARQUBE_URL = 'http://172.26.103.136:9000'
        SONARQUBE_TOKEN = 'sqa_5fdb6f3207a6b2a0b918ffe1f3806bc5e901e6ab'
        SRC_DIR = "${WORKSPACE}"
        REPORT_DIR = "${WORKSPACE}/dependency-check-reports"
        TRIVY_IMAGE = 'aquasec/trivy:latest'  // Trivy Docker image
        IMAGE_NAME = 'ubuntu:latest'          // Container image to scan
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/yuvarajusaikumar/my-project.git'
            }
        }
        stage('Verify Docker Access') {
            steps {
                script {
                    // Run Docker command to check version
                    sh 'docker --version'
                }
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
                        sh 'mvn sonar:sonar -Dsonar.projectKey=sonarqube-token -Dsonar.host.url=$SONARQUBE_URL -Dsonar.login=$SONARQUBE_TOKEN'
                    }
                }
            }
        }
        /*
        stage('Run Dependency Check') {
            steps {
                script {
                    sh 'docker run --rm -v $(pwd):/src -v $(pwd)/dependency-check-reports:/report owasp/dependency-check --project "my-project" --scan /src/pom.xml --out /report'
                }
            }
        }
        */
        stage('Scan Container Image with Trivy') {
            steps {
                script {
                    sh 'docker pull $IMAGE_NAME'
                    sh "docker run --rm -v /var/run/docker.sock:/var/run/docker.sock $TRIVY_IMAGE image $IMAGE_NAME > trivy_scan_results.txt"
                    archiveArtifacts artifacts: 'trivy_scan_results.txt', allowEmptyArchive: true
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
