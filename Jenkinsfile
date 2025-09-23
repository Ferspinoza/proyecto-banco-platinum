pipeline {
  agent any

  stages {
    stage('Checkout from GitHub') {
      steps {
        git branch: 'main', url: 'https://github.com/Ferspinoza/proyecto-banco-platinum.git'
      }
    }

    stage('Build with Maven') {
      steps {
        bat 'mvn -U -B clean package -DskipTests'
      }
      post {
        always {
          archiveArtifacts artifacts: 'target/CtaCorriente.war', fingerprint: true
        }
      }
    }

    // 3.3 Etapa de pruebas: genera XML para JUnit en Jenkins
    stage('Run Unit Tests (JUnit XML)') {
      steps {
        // Ejecuta un test seguro para generar XML (ajusta o quita -Dtest cuando tengas tu suite)
        bat 'mvn -B -Dtest=SanityTest test'
      }
      post {
        always {
          junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'
        }
      }
    }

    // (Opcional) Deploy a Artifactory: descomenta cuando configures credenciales en Jenkins
    /*
    stage('Deploy to Artifactory') {
      steps {
        // Opción simple vía Maven (usa distributionManagement de tu pom + settings.xml)
        bat 'mvn -B deploy -DskipTests'
        // -- O BIEN --
        // Opción plugin JFrog (si configuraste "JFrog Platform Instances"):
        // rtUpload serverId: 'Artifactory Local', spec: '''{
        //   "files":[{"pattern":"target/*.war","target":"libs-release-local/CtaCorriente/"}]
        // }'''
        // rtPublishBuildInfo serverId: 'Artifactory Local'
      }
    }
    */
  }

  post {
    always { cleanWs() }
  }
}
