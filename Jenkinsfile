job("Maven_project_using_DSL"){
	description("Maven Project generated from DSL on ${ new Date()}")
	
	scm {
		git("https://github.com/issacrajan/test-memcache.git", "master")
	}
	
	triggers{
		scm("* * * * *")
	}	
	
	steps {
		maven("clean -DskipTests=true  package", "pom.xml")
	}
	
	publishers {
		archiveArtifacts("**/*.jar")
	}
}