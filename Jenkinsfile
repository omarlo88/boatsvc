pipeline {
    agent any
    tools {
        maven 'maven_3.8.6'
    }
    stages {
        stage('Build maven') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/omarlo88/boatsvc']]])
                sh 'mvn clean install'
            }
        }
        stage('Build docker image') {
          /* steps {
                script {
                  sh 'docker build -t boatsvc:latest .'
                }

          } */
            steps {
               sh 'docker build -t boatsvc:latest .'
            }
        }
         stage('Push image to docker Hub') {
            steps {
               withCredentials([string(credentialsId: 'dockerhubpwd', variable: 'dockerhubpwd')]) {
                   sh 'docker login -u omarlo -p ${dockerhubpwd}'
                }
                /* sh 'docker tag boatsvc:latest omarlo/first-repository:latest'
                sh 'docker push omarlo/first-repository:latest' */
                sh '''
                    docker tag boatsvc:latest omarlo/first-repository:latest
                    docker push omarlo/first-repository:latest
                '''
            }
        }
    }
}