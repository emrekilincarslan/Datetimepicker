# Datetimepicker

[![N|Solid](https://i.imgur.com/U3zTNa7.gif)](https://nodesource.com/products/nsolid)

[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://github.com/emrekilincarslan/Datetimepicker)

DateTimePicker is a cool dialog for getting date and time at the same time 
  - Easy To use
  - Easy To  implement
  - Magic :) 
  
  ### Installation
  
How to
To get a Git project into your build:

Step 1. Add the JitPack repository to your project level build.gradle file

	allprojects {
    repositories {
        google()
        jcenter()
        maven { url "https://www.jitpack.io" }
        
    }
}
  
Step 2. Add the dependency  app level build.gradle file 

	dependencies {
    implementation 'com.github.emrekilincarslan:Datetimepicker:0.1.0'
  }
  
  Thats It !!!
  
  Usage : 
  
   ```sh

 { val now = Calendar.getInstance()
        val mTimePicker = DateTimePickDialog(
            requireContext(), object : DateTimePickDialog.OnTimeSetListener {
                override fun onTimeSet(
                    view: TimePicker?,
                    hourOfDay: Int,
                    minute: Int,
                    year: Int,
                    month: Int,
                    day: Int
                ) {
                    Toast.makeText(requireContext(), getString(R.string.propertime, day, month, year, hourOfDay, minute), Toast.LENGTH_SHORT).show()
                }
            },
            now[Calendar.HOUR_OF_DAY],
            now[Calendar.MINUTE]
        )
        mTimePicker.show()
        } 
```
        
        
