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
        PATH = "/snap/bin:/usr/local/bin:$PATH"
        IMAGE_NAME = 'hello-world:latest'
        MAVEN_OPTS = '-Dorg.jenkinsci.plugins.durabletask.BourneShellScript.HEARTBEAT_CHECK_INTERVAL=86400' // Set heartbeat check interval
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
                        sh 'mvn sonar:sonar -Dsonar.projectKey=sonarqube-token -Dsonar.host.url=$SONARQUBE_URL -Dsonar.login=$SONARQUBE_TOKEN'
                    }
                }
            }
        }

        stage('Dependency-Check Scan and Save Report') {
            steps {
                script {
                    sh 'mvn org.owasp:dependency-check-maven:check'
                    archiveArtifacts allowEmptyArchive: true, artifacts: 'target/dependency-check-report*.html', onlyIfSuccessful: true
                }
            }
        }
        /*
        this was usinf docker in docker so won't work
        stage('Run Dependency Check') {
            steps {
                script {
                    sh 'docker run --rm -v $(pwd):/src -v $(pwd)/dependency-check-reports:/report owasp/dependency-check --project "my-project" --scan /src/pom.xml --out /report'
                }
            }
        }
        
        stage('Scan Container Image with Trivy') {
            steps {
                script {
                    sh 'docker pull $IMAGE_NAME'
                    sh "docker run --rm -v /var/run/docker.sock:/var/run/docker.sock $TRIVY_IMAGE image $IMAGE_NAME > trivy_scan_results.txt"
                    archiveArtifacts artifacts: 'trivy_scan_results.txt', allowEmptyArchive: true
                }
            }
        }
        */
        
        stage('Scan Container with Trivy') {
            steps {
                script {
                    // Add '--scanners vuln' to disable secret scanning and speed up the scan
                    sh """
                        trivy image --scanners vuln ${env.IMAGE_NAME}
                    """
                }
            }
        }
    }
    post {
        success {
            echo 'Build completed successfully.'
        }
        failure {
            echo 'The build failed.'
        }
    }
}
