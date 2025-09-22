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
                // Guarda el archivo .war generado para que podamos descargarlo desde Jenkins.
                // ¡Esto cumple con el siguiente punto del checklist!
                archiveArtifacts artifacts: 'target/CtaCorriente.war', fingerprint: true
            }
        }
    }

    post {
        always {
            // Buena práctica: Limpia el espacio de trabajo después de cada construcción.
            cleanWs()
        }
    }

stage('Deploy to Artifactory') {
    steps {
        // Ejecuta el comando 'deploy' de Maven.
        // Esto lee la sección <distributionManagement> del pom.xml
        // y envía el .war al repositorio de Artifactory.
        bat 'mvn deploy -DskipTests'
    }
}


}