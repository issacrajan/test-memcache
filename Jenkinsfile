job("Maven_project_using_DSL"){
	description("Maven Project generated from DSL on ${ new Date()}")
	
	scm {
		git("git@github.com:issacrajan/test-memcache.git", "master")
	}
	
	triggers{
		scm("* * * * *")
	}	
	
	steps {
		maven("clean -DskipTests=true  package", "pom.xml")
	}
	
	publishers {
		archieveAtrifacts "**/*.jar"
	}
}