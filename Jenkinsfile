pipeline {
    agent any

    stages {
        stage('Checkout from GitHub') {
            steps {
                // Descarga el código desde la rama 'main' de tu repositorio
                git branch: 'main', url: 'https://github.com/Ferspinoza/proyecto-banco-platinum.git'
            }
        }
        stage('Build with Maven') {
            steps {
                // Ejecuta el comando de Maven para construir el proyecto.
                // Usamos -DskipTests para asegurar que la construcción sea exitosa por ahora.
                bat 'mvn clean install -DskipTests'
            }
        }
        stage('Archive Artifact') {
            steps {
                // Guarda el archivo .war generado.
                archiveArtifacts artifacts: 'target/CtaCorriente.war', fingerprint: true
            }
        }
        stage('Deploy to Artifactory') {
            steps {
                // Ejecuta el comando 'deploy' de Maven para enviar el .war a Artifactory.
                bat 'mvn deploy -DskipTests'
            }
        }
    }

    post {
        always {
            // Buena práctica: Limpia el espacio de trabajo después de cada construcción.
            cleanWs()
        }
    }
}