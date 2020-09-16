# Datetimepicker

[![N|Solid](https://i.imgur.com/WagyObQ.gif)](https://nodesource.com/products/nsolid)

[![](https://jitpack.io/v/emrekilincarslan/Datetimepicker.svg)](https://jitpack.io/#emrekilincarslan/Datetimepicker)

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
    implementation 'com.github.emrekilincarslan:Datetimepicker:<latest-version>'
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
        
        
