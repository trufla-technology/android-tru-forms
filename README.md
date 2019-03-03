# android-tru-forms

Step 1. Add the JitPack repository to your build file
[![](https://jitpack.io/v/trufla-technology/android-tru-forms.svg)](https://jitpack.io/#trufla-technology/android-tru-forms)

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}


Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.trufla-technology:android-tru-forms:v.1.4-alpha'
	}	
	
Step 3. Add Java 8 support	

	android {
		....
	        compileOptions {
        		sourceCompatibility JavaVersion.VERSION_1_8
        		targetCompatibility JavaVersion.VERSION_1_8
    		}
	}


