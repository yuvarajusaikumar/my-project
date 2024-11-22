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
    }
/*
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
                        sh 'mvn sonar:sonar -Dsonar.projectKey=sonarqube-token -Dsonar.host.url=$SONARQUBE_URL -Dsonar.login=$SONARQUBE_TOKEN'
                    }
                }
            }
        }

        stage('Run Dependency Check') {
            steps {
                script {
                    sh "mkdir -p ${REPORT_DIR}"
                    sh "echo 'Workspace: ${WORKSPACE}'"
                    sh "ls -al ${WORKSPACE}"
                    docker.image('owasp/dependency-check').inside {
                        sh """
                            dependency-check --project "my-project" --scan ${SRC_DIR}/pom.xml --out ${REPORT_DIR}
                        """
                    }
                }
            }
        }
        */
        stage('Dependency Check') {
            steps {
                script {
                    //sh 'docker run --rm -v $(pwd):/src -v $(pwd)/dependency-check-reports:/report owasp/dependency-check --project "my-project" --scan /src/pom.xml --out /report'
                    //dependency-check --project my-project --scan /home/yuvaraju/my-project --out /home/yuvaraju/dependency-check-reports
                
                    sh """
                        docker run --rm -v ${pwd()}: /src -v ${pwd()}/dependency-check-reports:/report owasp/dependency-check --project "my-project" --scan /src/pom.xml --out /report
                    """
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
