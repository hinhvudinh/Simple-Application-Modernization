pipeline {
    agent any

    environment {
        module_name = "bookstore"
        dockerImage = "vdhinh/bookstore:0.0.4"  
    }

    stages {
        // Prepare 
        stage('Prepare') {
            steps {
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: 'gitCredential2', url: 'https://github.com/hinhvudinh/book-store']])
            }
            
            post {
                success {
                    echo 'Successfully prepare github repository'
                }
                failure {
                    error 'This pipeline stopped at Prepare stage...'
                }
            }
        }
        
        // docker build
        stage('Docker Build') {
            agent any
            steps {
                echo 'Docker Build'
                
                sh """
                docker image rm -f ${dockerImage}
                docker build -t ${dockerImage} .
                """
                
            }
            post {
                failure {
                    error 'This pipeline stopped at Bulid Docker stage...'
                }
            }
        }

        // docker push
        stage('Docker Push') {
            agent any
            steps {
                script {
                    withCredentials([string(credentialsId: 'dockerCredential', variable: 'dockerCredential')]) {
                        sh 'docker login -u vdhinh -p ${dockerCredential}'
                    }
                }
                
                echo 'Docker Push'
                sh """
                    docker push ${dockerImage}
                """
            }
            post {
                failure {
                    error 'This pipeline stopped at Push Docker stage...'
                }
            }
        }
    }
}
