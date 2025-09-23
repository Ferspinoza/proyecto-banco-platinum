pipeline {
  agent any

  tools {
    jdk   'JDK17'
    maven 'Maven-3.9'
  }

  options {
    timestamps()
    ansiColor('xterm')
  }

  environment {
    GIT_REPO = 'https://github.com/Ferspinoza/proyecto-banco-platinum.git'
    // Artifactory (configurado en Manage Jenkins → Configure System)
    ARTIFACTORY_SERVER_ID = 'artifactory'
  }

  stages {

    /* ============================
       3.1 Etapa 1: Preparación
       - Integración con repo remoto (GitHub)
       - (Inicializa cliente Artifactory para tenerlo listo)
       ============================ */
    stage('Preparación') {
      steps {
        script {
          // Si tu repo es privado, añade: credentialsId: 'github-token'
          checkout([$class: 'GitSCM',
            branches: [[name: '*/main']],
            userRemoteConfigs: [[url: GIT_REPO ]]
          ])

          // Prepara la instancia Artifactory (no publica aún)
          rtServer = Artifactory.server(ARTIFACTORY_SERVER_ID)
          rtMaven  = Artifactory.newMavenBuild()
          rtMaven.resolver server: rtServer,
                           releaseRepo: 'libs-release-local',
                           snapshotRepo: 'libs-snapshot-local'
          rtMaven.deployer server: rtServer,
                           releaseRepo: 'libs-release-local',
                           snapshotRepo: 'libs-snapshot-local'
        }
      }
    }

    /* ============================
       3.2 Etapa 2: Construcción
       - Compila el proyecto (WAR)
       ============================ */
    stage('Construcción') {
      steps {
        // -U actualiza dependencias; -B modo batch para logs limpios
        bat 'mvn -U -B clean package -DskipTests'
      }
      post {
        always {
          archiveArtifacts artifacts: 'target/*.war', fingerprint: true
        }
      }
    }

    /* ============================
       3.3 Etapa 3: Pruebas
       - Ejecuta tests y publica resultados XML en Jenkins
       ============================ */
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

  /* ===== (OPCIONAL) Publicar WAR en Artifactory =====
     Si el profe quiere evidencia de publicación, descomenta este bloque.
  post {
    success {
      script {
        // Publica build-info (si usaste rtMaven en las etapas)
        rtServer.publishBuildInfo()
        // Alternativa con spec (sube cualquier WAR de target):
        // rtUpload serverId: ARTIFACTORY_SERVER_ID, spec: """{
        //   "files": [{"pattern": "target/*.war", "target": "libs-release-local/CtaCorriente/"}]
        // }"""
      }
    }
  }
  */
}
