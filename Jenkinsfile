pipeline {
  agent any

  tools {
    // USA el nombre EXACTO que ves en Manage Jenkins > Tools
    maven 'Maven 3.9.11'
    // (sin jdk: usará el Java del agente)
  }

  options {
    timestamps()
  }

  environment {
    GIT_REPO = 'https://github.com/Ferspinoza/proyecto-banco-platinum.git'
  }

  stages {
    stage('Preparación') {
      steps {
        // si el repo es privado, agrega credentialsId
        checkout([$class: 'GitSCM',
          branches: [[name: '*/main']],
          userRemoteConfigs: [[url: GIT_REPO]]
        ])
      }
    }

    stage('Construcción') {
      steps {
        bat 'mvn -U -B clean package -DskipTests'
      }
      post {
        always {
          archiveArtifacts artifacts: 'target/*.war', fingerprint: true
        }
      }
    }

    stage('Pruebas') {
      steps {
        bat 'mvn -B test'
      }
      post {
        always {
          junit '**/target/surefire-reports/*.xml'
        }
      }
    }
  }
}
