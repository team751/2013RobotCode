InsightLT_Java
==============

Java implementation of the InsightLT API

Welcome and thank you for your interest in the InsightLT robot diagnostic 
display.

This is the initial release of code and documentation and will probably be 
updated frequently. Please check the repository for the latest code updates and 
bug fixes.

To report a bug, please log an issue on git hub to have it resolved in a timely
manner. Make sure to include you email address so any details may be collected
to fix the issue.

How To Add the Code to your Java Project
----------------------------------------
The InsightLT project is built as a Java package.

The easiest way to currently add Insight to your project is as follows.
1)Create a new package in your project and make sure to name the package
"InsightLT"

2)Copy all of the .java files into this package

Alternatively you could just add the source files to your project in the
current wpi package.


To use the InsightLT objects you will need to import them like so...
import InsightLT.InsightLT;
import InsightLT.StringData;
import InsightLT.IntegerData;
import InsightLT.DecimalData;

An explanation of using the Zones and configurations is in the file
"DisplayConfigurations.txt"